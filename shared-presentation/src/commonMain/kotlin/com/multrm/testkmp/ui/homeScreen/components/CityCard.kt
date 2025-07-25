package com.multrm.testkmp.ui.homeScreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CityCard(modifier: Modifier = Modifier, cityName: String, onClick: (String) -> Unit) {
    Box(
        modifier = modifier.fillMaxWidth()
            .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(12.dp)
        )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick(cityName) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = cityName,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}