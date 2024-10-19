package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.baseui.Heart

@Preview
@Composable
fun HeartScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Heart()
            Spacer(modifier = Modifier.width(8.dp))
            Heart(color = Color.Magenta)
            Spacer(modifier = Modifier.width(8.dp))
            Heart(isFill = false)
        }
    }
}