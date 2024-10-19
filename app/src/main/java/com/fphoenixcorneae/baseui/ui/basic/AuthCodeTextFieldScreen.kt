package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.baseui.AuthCodeTextField

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun AuthCodeTextFieldScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffffffff)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AuthCodeTextField(
            length = 4,
            cursorCharacter = "|",
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthCodeTextField(
            length = 6,
            cursorCharacter = "_",
        )
    }
}