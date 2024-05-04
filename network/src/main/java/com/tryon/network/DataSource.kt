package com.tryon.network

abstract class DataSource<API, T, DTO>(
    private val networkClient: NetworkClient
) {
    protected suspend fun retrieveData(
        predicate: Predicate<API, T, DTO>,
    ): DataResponse<T> {
        return try {
            val service = networkClient.createService(predicate.service())
            val endpoint = predicate.endpoint()
            val dto = endpoint(service)
            DataResponse.Success(predicate.mapper().toDomainModel(dto))
        } catch (e: Throwable) {
            val error = errorHandler(e)
            DataResponse.Failure(error)
        }
    }

    open fun errorHandler(e: Throwable) = e
}

interface Predicate<API, T, DTO> {
    fun service(): Class<API>
    fun endpoint(): suspend (API) -> DTO
    fun mapper(): Mapper<T, DTO>
}

interface Mapper<T, DTO> {
    fun toDomainModel(dto: DTO): T
}

sealed class DataResponse<out T> {
    data class Success<out T>(val data: T): DataResponse<T>()
    data class Failure(val error: Throwable): DataResponse<Nothing>()
}

fun<T> DataResponse<T>.parseResult(
    dataSuccess: (T) -> Unit,
    dataError: (Throwable) -> Unit
) {
    when(this) {
        is DataResponse.Success -> dataSuccess(data)
        is DataResponse.Failure -> dataError(error)
    }
}

suspend fun <T, D> DataResponse<T>.map(
    newResponse: suspend (T) -> DataResponse<D>
): DataResponse<D> = when(this) {
        is DataResponse.Success -> newResponse(data)
        is DataResponse.Failure -> this
    }
