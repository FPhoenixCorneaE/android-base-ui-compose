package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.progressbar.CircleProgressBar
import com.fphoenixcorneae.baseui.progressbar.HorizontalProgressBar
import com.fphoenixcorneae.baseui.progressbar.VerticalProgressBar

@Preview
@Composable
fun ProgressBarScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "HorizontalProgressBar：")
        HorizontalProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
            progress = 0f,
            colorStops = listOf(
                0f to Color.Blue,
                0.5f to Color.Magenta,
                1f to Color.Red
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
            progress = 80f,
            colorStops = listOf(
                0f to Color.Blue,
                0.5f to Color.Magenta,
                1f to Color.Red
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
            progress = 80f,
            showAnim = false,
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
            progress = 100f,
            colorStops = listOf(
                0f to Color.Blue,
                0.5f to Color.Magenta,
                1f to Color.Red
            ),
            showAnim = true,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "VerticalProgressBar：")
            Row(
                modifier = Modifier.fillMaxWidth(0.3f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                VerticalProgressBar(
                    modifier = Modifier
                        .width(5.dp)
                        .fillMaxHeight(),
                    progress = 0f,
                    colorStops = listOf(
                        0f to Color.Blue,
                        0.5f to Color.Magenta,
                        1f to Color.Red
                    ),
                )
                Spacer(modifier = Modifier.width(8.dp))
                VerticalProgressBar(
                    modifier = Modifier
                        .width(5.dp)
                        .fillMaxHeight(),
                    progress = 30f,
                    colorStops = listOf(
                        0f to Color.Blue,
                        0.5f to Color.Magenta,
                        1f to Color.Red
                    ),
                )
                Spacer(modifier = Modifier.width(8.dp))
                VerticalProgressBar(
                    modifier = Modifier
                        .width(5.dp)
                        .fillMaxHeight(),
                    progress = 50f,
                    showAnim = false,
                )
                Spacer(modifier = Modifier.width(8.dp))
                VerticalProgressBar(
                    modifier = Modifier
                        .width(5.dp)
                        .fillMaxHeight(),
                    progress = 100f,
                    colorStops = listOf(
                        0f to Color.Blue,
                        0.5f to Color.Magenta,
                        1f to Color.Red
                    ),
                    showAnim = true,
                )
            }
        }
        Text(text = "CircleProgressBar：")
        Row(verticalAlignment = Alignment.CenterVertically) {
            CircleProgressBar(
                radius = 30.dp,
                progress = 100f,
                colorStops = listOf(
                    0f to Color.Blue,
                    0.5f to Color.Yellow,
                    1f to Color.Red
                ),
            )
            Spacer(modifier = Modifier.width(8.dp))
            CircleProgressBar(
                modifier = Modifier.background(Color.White),
                progress = 100f,
                radius = 30.dp,
                color = Color.Red,
                colorStops = listOf(
                    0f to Color.Blue,
                    0.5f to Color.Yellow,
                    1f to Color.Red
                ),
                startAngle = -210f,
                maxAngle = 240f,
                centerText = buildAnnotatedString { append("环形进度条") },
                centerTextStyle = TextStyle(fontSize = 12.sp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            CircleProgressBar(
                radius = 30.dp,
                progress = 75f,
                clockwise = false,
                colorStops = listOf(0f to Color.Blue, 0.5f to Color.Yellow, 1f to Color.Red)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CircleProgressBar(
                modifier = Modifier.background(Color.White),
                progress = 100f,
                radius = 30.dp,
                color = Color.Red,
                startAngle = -150f,
                maxAngle = 240f,
                centerText = buildAnnotatedString { append("环形进度条") },
                centerTextStyle = TextStyle(fontSize = 12.sp),
                clockwise = false,
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            CircleProgressBar(
                radius = 30.dp,
                progress = 0f,
                colorStops = listOf(
                    0f to Color.Blue,
                    0.5f to Color.Yellow,
                    1f to Color.Red
                ),
            )
            Spacer(modifier = Modifier.width(8.dp))
            CircleProgressBar(
                modifier = Modifier.background(Color.White),
                progress = 0f,
                radius = 30.dp,
                color = Color.Red,
                colorStops = listOf(
                    0f to Color.Blue,
                    0.5f to Color.Yellow,
                    1f to Color.Red
                ),
                startAngle = -210f,
                maxAngle = 240f,
                centerText = buildAnnotatedString { append("环形进度条") },
                centerTextStyle = TextStyle(fontSize = 12.sp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            CircleProgressBar(
                radius = 30.dp,
                progress = 50f,
                clockwise = false,
                colorStops = listOf(0f to Color.Blue, 0.5f to Color.Yellow, 1f to Color.Red)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CircleProgressBar(
                modifier = Modifier.background(Color.White),
                progress = 0f,
                radius = 30.dp,
                color = Color.Red,
                startAngle = -150f,
                maxAngle = 240f,
                centerText = buildAnnotatedString { append("环形进度条") },
                centerTextStyle = TextStyle(fontSize = 12.sp),
                clockwise = false,
            )
        }
    }
}