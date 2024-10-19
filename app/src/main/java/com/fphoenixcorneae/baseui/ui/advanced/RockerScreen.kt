package com.fphoenixcorneae.baseui.ui.advanced

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.Rocker
import kotlinx.coroutines.delay

@Preview
@Composable
fun RockerScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Rocker(
            modifier = Modifier.padding(horizontal = 50.dp),
            onLeftPressed = {
                repeat(Int.MAX_VALUE) {
                    println("BaseUi: Rocker: onLeftPressed: $it")
                    delay(500L)
                }
            },
            onUpPressed = {
                repeat(Int.MAX_VALUE) {
                    println("BaseUi: Rocker: onUpPressed: $it")
                    delay(500L)
                }
            },
            onRightPressed = {
                repeat(Int.MAX_VALUE) {
                    println("BaseUi: Rocker: onRightPressed: $it")
                    delay(500L)
                }
            },
            onDownPressed = {
                repeat(Int.MAX_VALUE) {
                    println("BaseUi: Rocker: onDownPressed: $it")
                    delay(500L)
                }
            },
            onSteeringWheelChanged = {
                println("BaseUi: Rocker: onSteeringWheelChanged: $it")
            },
        )
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("Rocker", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("摇杆器", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}