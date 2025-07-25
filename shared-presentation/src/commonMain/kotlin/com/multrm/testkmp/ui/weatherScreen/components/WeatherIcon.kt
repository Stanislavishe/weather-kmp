package com.multrm.testkmp.ui.weatherScreen.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import org.jetbrains.compose.resources.vectorResource
import weatherkmp.shared_presentation.generated.resources.Res
import weatherkmp.shared_presentation.generated.resources.warning_icon

@Composable
fun WeatherIcon(modifier: Modifier = Modifier, imageUrl: String) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "Weather Icon",
        modifier = modifier
    ) {
        val state by painter.state.collectAsState()
        when (state) {
            is AsyncImagePainter.State.Error -> {
                Icon(vectorResource(Res.drawable.warning_icon), null, modifier, Color.Red)
            }
            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
            }
            else -> {}
        }
    }
}