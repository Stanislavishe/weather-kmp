package com.multrm.testkmp.ui.weatherScreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multrm.shared.domain.weatherScreenEntity.ItemForecast
import com.multrm.testkmp.ui.utils.StringFinder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import weatherkmp.shared_presentation.generated.resources.Res
import weatherkmp.shared_presentation.generated.resources.forecastTemp

@Composable
fun ItemForecastInfo(itemForecast: ItemForecast) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            itemForecast.date,
            fontSize = 22.sp
        )
        Spacer(Modifier.width(6.dp))
        WeatherIcon(Modifier.size(25.dp), itemForecast.icon)
        Spacer(Modifier.weight(1f))
        StringFinder.findStringResource(itemForecast.temp.resId)?.let {
            Text(
                stringResource(
                    it,
                    itemForecast.temp.minTemp,
                    itemForecast.temp.maxTemp
                ),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}


@Preview
@Composable
private fun ItemForecastInfoPreview() {
    ItemForecastInfo(
        ItemForecast(
            "22.05.2006",
            "",
            ItemForecast.Temperature(Res.string.forecastTemp.key, 25, 40)
        )
    )
}