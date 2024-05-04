package com.tryon.app.features.dashboard.domain

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

interface DashboardUseCase {
    fun createImageFile(): Uri
}
class DashboardUseCaseImpl(
    private val context: Context
): DashboardUseCase {
    override fun createImageFile(): Uri {
            val file = context.createImageFile()

    return  FileProvider.getUriForFile(
        context,
        "com.app.id.fileProvider", file
        )
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir /* directory */
    )
}
