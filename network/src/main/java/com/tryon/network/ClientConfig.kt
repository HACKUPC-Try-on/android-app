package com.tryon.network

import okhttp3.Interceptor

data class NetworkConfig(
    val environment: Environment,
    val interceptors: List<Interceptor>
)

data class Environment(
    val host: String,
    val forceHttps: Boolean,
    val networkTimeout: Int
)
