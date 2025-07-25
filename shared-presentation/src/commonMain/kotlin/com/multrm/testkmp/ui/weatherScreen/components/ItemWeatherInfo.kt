package com.multrm.testkmp.ui.weatherScreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multrm.shared.domain.weatherScreenEntity.ItemInfo
import com.multrm.testkmp.ui.utils.StringFinder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import weatherkmp.shared_presentation.generated.resources.Res
import weatherkmp.shared_presentation.generated.resources.c
import weatherkmp.shared_presentation.generated.resources.temperature

@Composable
fun ItemWeatherInfo(info: ItemInfo, isLast: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .padding(12.dp)
            .wrapContentHeight()
    ) {
        StringFinder.findStringResource(info.titleResId)?.let {
            Text(
                text = stringResource(it),
                fontSize = 20.sp,
                color = Color.DarkGray
            )
        }

        Spacer(Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            val text = if (isLast) {
                val res = StringFinder.findStringResource(info.value)
                res?.let { stringResource(it) } ?: ""
            } else info.value
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                softWrap = true,
            )
            Spacer(Modifier.width(4.dp))
            val measurementResId = info.measurementResId
            val measurementText = if (measurementResId != "0") {
                val res = StringFinder.findStringResource(measurementResId)
                res?.let { stringResource(it) } ?: ""
            } else ""
            Text(
                text = measurementText  ,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun ItemWeatherInfoPreview() {
    ItemWeatherInfo(ItemInfo(Res.string.temperature.key, "35", Res.string.c.key), isLast = false)
}