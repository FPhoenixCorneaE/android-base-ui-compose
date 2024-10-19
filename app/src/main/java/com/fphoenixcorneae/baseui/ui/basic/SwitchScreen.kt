package com.fphoenixcorneae.baseui.ui.basic

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.Switch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private val switchEnabled = MutableStateFlow(true)

@Preview
@Composable
fun SwitchScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val coroutineScope = rememberCoroutineScope()
        val isChecked by switchEnabled.collectAsState()
        Switch(
            checked = isChecked,
            width = 40.dp,
            height = 20.dp,
        ) { checked ->
            Log.d("BaseUi", "Switch: $checked")
            coroutineScope.launch {
                switchEnabled.emit(checked)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Switch(
            checked = isChecked,
            enabled = false,
            width = 40.dp,
            height = 20.dp,
        )
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("Switch", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("开关", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}