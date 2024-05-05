package com.tryon.app.features.dashboard.ui

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.tryon.app.R
import com.tryon.app.components.ButtonIcon

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraLauncherComponent(
    uri: Uri,
    onSuccessCamera: (Uri) -> Unit,
    onFailure: (String) -> Unit
) {

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    val hasCameraPermission = cameraPermissionState.status.isGranted

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) onSuccessCamera(uri)
            else onFailure("Failed to load camera image, try again please")
        }
    )

    ButtonIcon(iconRes = R.drawable.ic_camera, onClick = {
        if (hasCameraPermission) {
            cameraLauncher.launch(uri)
        } else {
            cameraPermissionState.launchPermissionRequest()
        }
    })
}