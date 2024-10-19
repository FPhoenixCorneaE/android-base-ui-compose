package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.baseui.Square

@Preview
@Composable
fun SquareScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5)),
        contentAlignment = Alignment.Center
    ) {
        Square(modifier = Modifier.width(60.dp)) {
            Box(modifier = Modifier.background(Color.Blue))
        }
    }
}