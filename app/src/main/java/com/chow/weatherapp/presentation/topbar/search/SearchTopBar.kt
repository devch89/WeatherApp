package com.chow.weatherapp.presentation.topbar.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SearchTopBar() {
    Text(
        text = "Search", textAlign = TextAlign.Center,
        modifier = Modifier.padding(12.dp),
        color = Color.Black
    )
}