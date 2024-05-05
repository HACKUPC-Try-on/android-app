package com.tryon.app.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import com.tryon.app.R
import com.tryon.theme.TryOnTheme

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    image: Bitmap? = null
) {
    val borderColor = TryOnTheme.Colors.highlight
    val cornerWidth = TryOnTheme.Dimen.cornerRadius.card
    Box(
        modifier = modifier
            .drawBehind {
                drawRoundRect(
                    color = borderColor,
                    style = stroke,
                    cornerRadius = CornerRadius(cornerWidth.toPx())
                )
            }
            .clip(shape = RoundedCornerShape(cornerWidth)),
        contentAlignment = Alignment.Center
    ) {
        image?.let {
            Image(bitmap = it.asImageBitmap(), contentDescription = null)
        } ?: EmptyImageComponent()
    }
}

@Composable
fun EmptyImageComponent() {
    Box(
        modifier = Modifier
            .padding(all = TryOnTheme.Dimen.spacing.M)
            .background(
                color = TryOnTheme.Colors.tertiary,
                shape = CircleShape
            )
            .padding(
                all = TryOnTheme.Dimen.spacing.M
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_upload_photo),
            contentDescription = null
        )
    }
}

val stroke = Stroke(
    width = 8f,
    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
)
