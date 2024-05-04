package com.tryon.app.features.tryon.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.tryon.app.R
import com.tryon.app.components.ButtonIcon
import com.tryon.app.components.ImageContainer
import com.tryon.app.features.tryon.TryOnViewModel
import com.tryon.theme.TryOnTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TryOnScreen(
    viewModel: TryOnViewModel = hiltViewModel()
) {

    var imageUri = remember { mutableStateOf<Uri?>(null) }

    val file = LocalContext.current.createImageFile()

    val uri = FileProvider.getUriForFile(
        LocalContext.current,
        "com.app.id.fileProvider", file
    )

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) imageUri.value = uri
            if (imageUri.toString().isNotEmpty()) {
                Log.d("myImageUri", "$imageUri ")
            }
        }
    )

    val mediaPermissionState = rememberMultiplePermissionsState(
        permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) listOf(
            Manifest.permission.READ_MEDIA_IMAGES
        ) else listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    )

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) {
            imageUri.value = it
        }

    val hasCameraPermission = cameraPermissionState.status.isGranted
    val hasMediaPermission = mediaPermissionState.allPermissionsGranted



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TryOnTheme.Dimen.Spacing.M),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(TryOnTheme.Dimen.Spacing.M)
    ) {
        Text(
            text = "TryOn!",
            style = TryOnTheme.Typography.headline
        )
        ImageContainer(
            modifier = Modifier
                .padding(horizontal = TryOnTheme.Dimen.Spacing.L)
                .fillMaxWidth()
                .height(400.dp),
            image = imageUri.value
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ButtonIcon(iconRes = R.drawable.ic_camera, onClick = {
                if(hasCameraPermission){
                    cameraLauncher.launch(uri)
                } else {
                    cameraPermissionState.launchPermissionRequest()
                }
            })
            ButtonIcon(iconRes = R.drawable.ic_portrait, onClick = {
                if(hasMediaPermission){
                    galleryLauncher.launch("image/*")
                } else {
                    mediaPermissionState.launchMultiplePermissionRequest()
                }
            })
        }
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
