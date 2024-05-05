package com.tryon.app.features.dashboard

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date

interface FileManager {
    fun createImageFile(): Uri
    fun uriToByteArray(uri: Uri): ByteArray?

    fun uriToBitmap(uri: Uri): Bitmap
    fun byteArrayToUri(byteArray: ByteArray): Uri?

}

class FileManagerImpl(
    private val context: Context
) : FileManager {
    override fun createImageFile(): Uri = getFileUri(createFile())

    override fun uriToByteArray(uri: Uri): ByteArray? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val byteBuffer = ByteArrayOutputStream()
            val bufferSize = 1024
            val buffer = ByteArray(bufferSize)
            var len: Int
            while (inputStream?.read(buffer).also { len = it ?: -1 } != -1) {
                byteBuffer.write(buffer, 0, len)
            }
            byteBuffer.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun uriToBitmap(uri: Uri): Bitmap {
        val source = ImageDecoder
            .createSource(context.contentResolver, uri)
        return ImageDecoder.decodeBitmap(source)
    }

    override fun byteArrayToUri(byteArray: ByteArray): Uri? {
        val file = createFile()
        try {
            FileOutputStream(file).use { output ->
                output.write(byteArray)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return getFileUri(file)
    }

    private fun getFileUri(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileProvider", file
        )
    }

    private fun createFile(): File {
        val path: File? = context.getExternalFilesDir("images/")
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        path?.mkdirs()
        return File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            path /* directory */
        )
    }
}

fun Uri.toMultipart(context: Context, fileName: String): MultipartBody.Part {
    val inputStream = context.contentResolver.openInputStream(this)
    val requestFile = inputStream?.readBytes()?.toRequestBody("image/jpg".toMediaTypeOrNull())
    return requestFile?.let {
        MultipartBody.Part.createFormData(fileName, "image.jpg", it)
    } ?: throw Throwable(message = "Could not send image, missing path")
}
