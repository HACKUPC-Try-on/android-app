package com.tryon.app.features.dashboard.ui

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tryon.app.R
import com.tryon.app.components.ButtonIcon

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GalleryLauncherComponent(
    onSuccessGallery: (Uri) -> Unit,
    onErrorGallery: (String) -> Unit
) {

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
            contract = ActivityResultContracts.GetContent(),
        ) { imageUri ->
            Log.d("GalleryLauncher: ", "$imageUri")
            imageUri?.let {
                onSuccessGallery(it)
            } ?: onErrorGallery("Gallery image not loaded")
        }

    val hasMediaPermission = mediaPermissionState.allPermissionsGranted

    ButtonIcon(iconRes = R.drawable.ic_portrait, onClick = {
        if (hasMediaPermission) {
            galleryLauncher.launch("image/*")
        } else {
            mediaPermissionState.launchMultiplePermissionRequest()
        }
    })
}