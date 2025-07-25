package com.multrm.testkmp.ui.weatherScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import weatherkmp.shared_presentation.generated.resources.Res
import weatherkmp.shared_presentation.generated.resources.try_again

@Composable
fun ErrorBox(errorText: String, modifier: Modifier = defaultModifier, onRetryClick: () -> Unit) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorText,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            TextButton(
                onClick = { onRetryClick() }
            ) {
                Text(
                    text = stringResource(Res.string.try_again),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

private val defaultModifier = Modifier.fillMaxSize()