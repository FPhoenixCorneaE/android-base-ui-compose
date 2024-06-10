package com.fphoenixcorneae.baseui.progressbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 水平进度条，支持渐变颜色，支持显示动画，支持圆角
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
fun HorizontalProgressBar(
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
    var targetValue by remember { mutableFloatStateOf(if (showAnim) 0f else targetProgress) }
    val animatedProgress by animateFloatAsState(
        targetValue = targetValue,
        visibilityThreshold = 0.001f,
        animationSpec = tween(durationMillis = animDurationMillis),
        label = "",
    )
    LaunchedEffect(key1 = showAnim) {
        if (showAnim) {
            targetValue = targetProgress
        }
    }
    Canvas(modifier = modifier) {
        drawRoundRect(
            color = backgroundColor,
            cornerRadius = CornerRadius(x = density.run { radius.toPx() }, y = density.run { radius.toPx() }),
        )
        if (colorStops != null) {
            drawRoundRect(
                brush = Brush.linearGradient(colorStops = colorStops.toTypedArray()),
                cornerRadius = CornerRadius(x = density.run { radius.toPx() }, y = density.run { radius.toPx() }),
                size = Size(width = size.width * (animatedProgress / max), height = size.height)
            )
        } else {
            drawRoundRect(
                color = color,
                cornerRadius = CornerRadius(x = density.run { radius.toPx() }, y = density.run { radius.toPx() }),
                size = Size(width = size.width * (animatedProgress / max), height = size.height)
            )
        }
    }
}

@Preview
@Composable
fun PreviewHorizontalProgressBar() {
    Column {
        HorizontalProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
            progress = 80f,
            colorStops = listOf(0f to Color.Blue, 0.5f to Color.Magenta, 1f to Color.Red),
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
            colorStops = listOf(0f to Color.Blue, 0.5f to Color.Magenta, 1f to Color.Red),
            showAnim = false,
        )
    }
}