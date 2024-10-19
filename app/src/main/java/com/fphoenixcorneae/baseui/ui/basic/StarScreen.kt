package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.baseui.Star

@Preview
@Composable
fun StarScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Star()
            Spacer(modifier = Modifier.width(8.dp))
            Star(borderWidth = 1.dp, borderColor = Color.Magenta, filledColor = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Star(backgroundColor = Color.Gray, filledFraction = 0.5f)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Star(starSize = 48.dp, cornerRadius = 4.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Star(
                starSize = 48.dp,
                cornerRadius = 4.dp,
                borderWidth = 2.dp,
                borderColor = Color.Magenta,
                filledColor = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Star(
                starSize = 48.dp,
                cornerRadius = 8.dp,
                backgroundColor = Color.Gray,
                filledFraction = 0.5f
            )
        }
    }
}