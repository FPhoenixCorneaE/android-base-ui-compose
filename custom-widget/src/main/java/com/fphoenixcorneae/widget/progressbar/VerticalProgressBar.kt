package com.fphoenixcorneae.widget.progressbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 垂直进度条，支持渐变颜色，支持显示动画，支持圆角
 * @param progress           当前进度
 * @param max                最大进度
 * @param radius             圆角半径
 * @param color              进度颜色
 * @param colorStops         渐变颜色
 * @param backgroundColor    背景色
 * @param showAnim           是否显示动画
 * @param animDurationMillis 动画时长
 */
@Composable
fun VerticalProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    max: Float = 100f,
    radius: Dp = 4.dp,
    color: Color = Color.Blue,
    colorStops: List<Pair<Float, Color>>? = null,
    backgroundColor: Color = Color(0xFFF2F3F5),
    showAnim: Boolean = true,
    animDurationMillis: Int = 1000,
) {
    val density = LocalDensity.current
    val targetProgress = progress.coerceAtLeast(0f).coerceAtMost(max)
    var targetValue by remember { mutableStateOf(if (showAnim) 0f else targetProgress) }
    val animatedProgress by animateFloatAsState(
        targetValue = targetValue,
        visibilityThreshold = 0.001f,
        animationSpec = tween(durationMillis = animDurationMillis),
    )
    LaunchedEffect(key1 = showAnim) {
        if (showAnim) {
            targetValue = targetProgress
        }
    }
    Canvas(modifier = modifier) {
        rotate(180f) {
            drawRoundRect(
                color = backgroundColor,
                cornerRadius = CornerRadius(x = density.run { radius.toPx() }, y = density.run { radius.toPx() }),
            )
            if (colorStops != null) {
                drawRoundRect(
                    brush = Brush.linearGradient(colorStops = colorStops.toTypedArray()),
                    cornerRadius = CornerRadius(x = density.run { radius.toPx() }, y = density.run { radius.toPx() }),
                    size = Size(width = size.width, height = size.height * (animatedProgress / max))
                )
            } else {
                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(x = density.run { radius.toPx() }, y = density.run { radius.toPx() }),
                    size = Size(width = size.width, height = size.height * (animatedProgress / max))
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewVerticalProgressBar() {
    Row {
        VerticalProgressBar(
            modifier = Modifier
                .width(5.dp)
                .fillMaxHeight(),
            progress = 80f,
            colorStops = listOf(0f to Color.Blue, 0.5f to Color.Magenta, 1f to Color.Red),
        )
        Spacer(modifier = Modifier.width(8.dp))
        VerticalProgressBar(
            modifier = Modifier
                .width(5.dp)
                .fillMaxHeight(),
            progress = 80f,
            showAnim = false,
        )
        Spacer(modifier = Modifier.width(8.dp))
        VerticalProgressBar(
            modifier = Modifier
                .width(5.dp)
                .fillMaxHeight(),
            progress = 100f,
            colorStops = listOf(0f to Color.Blue, 0.5f to Color.Magenta, 1f to Color.Red),
            showAnim = false,
        )
    }
}