package com.fphoenixcorneae.baseui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.graphics.path.SimplePath
import java.math.RoundingMode

/**
 * 自定义拖动条
 * @param modifier                强制修改符，用于指定此可组合的大小策略
 * @param progress                当前进度
 * @param progressTextStyle       进度值文字样式
 * @param progressBackgroundColor 进度背景颜色
 * @param progressColor           进度颜色
 * @param thickness               拖动条厚度
 * @param thumbSize               拖动条滑块大小
 * @param thumbColor              拖动条滑块颜色
 * @param thumbShadowColor        拖动条滑块阴影颜色
 * @param thumbShadowRadius       拖动条滑块阴影半径
 * @param thumbShadowOffsetX      拖动条滑块阴影偏移量x
 * @param thumbShadowOffsetY      拖动条滑块阴影偏移量y
 * @param onProgressChanged       进度变化回调
 */
@Composable
fun Seekbar(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    progressRange: ClosedFloatingPointRange<Float> = 0f..100f,
    showProgressText: Boolean = true,
    progressTextStyle: TextStyle = TextStyle(
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.W500
    ),
    progressBackgroundColor: Color = Color(0xFFE0E0E0),
    progressColor: Color = Color(0xFF3C7AF3),
    thickness: Dp = 6.dp,
    enabled: Boolean = true,
    thumbSize: Dp = 32.dp,
    thumbColor: Color = Color.White,
    thumbShadowColor: Color = Color.Black.copy(0.4f),
    thumbShadowRadius: Dp = 6.dp,
    thumbShadowOffsetX: Dp = 0.dp,
    thumbShadowOffsetY: Dp = 0.dp,
    onProgressChanged: (progress: Float, fromUser: Boolean) -> Unit = { _, _ -> },
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    // 当前进度占总进度百分比
    var progressPercent by remember { mutableFloatStateOf(0f) }
    var currentProgress by remember { mutableFloatStateOf(progress) }
    // bar是否被按下
    var barPressed by remember { mutableStateOf(false) }
    // 锚点的半径, 根据barPressed的状态'平滑'地改变自身的大小
    val thumbRadius by animateFloatAsState(
        if (barPressed) density.run { (thumbSize + 4.dp).toPx() } / 2 else density.run { thumbSize.toPx() } / 2,
        label = ""
    )
    var fromUser by remember { mutableStateOf(false) }
    LaunchedEffect(progress, progressRange) {
        fromUser = false
        val totalProgress = progressRange.endInclusive - progressRange.start
        progressPercent =
            if (totalProgress > 0f) progress.coerceIn(progressRange) / totalProgress else 0f
    }
    LaunchedEffect(progressPercent) {
        val totalProgress = progressRange.endInclusive - progressRange.start
        currentProgress = progressRange.start + totalProgress * progressPercent
        onProgressChanged.invoke(currentProgress, fromUser)
    }
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thumbSize + thumbShadowRadius * 2)
            .pointerInput(Unit) {
                // 响应滑动事件
                if (enabled) {
                    detectDragGestures(
                        onDragStart = { barPressed = true },
                        onDragCancel = { barPressed = false },
                        onDragEnd = {
                            // 滑动结束时， 恢复锚点大小，并回调onProgressChanged函数
                            barPressed = false
                        },
                        onDrag = { change, dragAmount ->
                            // 滑动过程中， 实时刷新progress的值(注意左右边界的问题)，
                            // 此值一旦改变， 整个Seekbar就会重组(刷新)
                            fromUser = true
                            progressPercent = (change.position.x / size.width).coerceIn(0f, 1f)
                        })
                }
            }
            .pointerInput(Unit) {
                // 响应点击事件，直接跳到该进度处
                if (enabled) {
                    detectTapGestures(
                        onTap = {
                            barPressed = false
                            fromUser = true
                            progressPercent = (it.x / size.width)
                        },
                    )
                }
            },
        onDraw = {
            // 进度条背景
            drawLine(
                color = progressBackgroundColor,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = density.run { thickness.toPx() },
                cap = StrokeCap.Round,
            )
            // 进度
            drawLine(
                color = progressColor,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width * progressPercent, size.height / 2),
                strokeWidth = density.run { thickness.toPx() },
                cap = StrokeCap.Round,
            )
            // 调用 Canvas 绘制锚点阴影
            drawIntoCanvas {
                val paint = Paint()
                paint.color = Color.Transparent
                // 调用底层 framework Paint绘制
                val frameworkPaint = paint.asFrameworkPaint()
                frameworkPaint.color = Color.Transparent.toArgb()
                // 绘制阴影
                frameworkPaint.setShadowLayer(
                    /* radius = */ density.run { thumbShadowRadius.toPx() },
                    /* dx = */ density.run { thumbShadowOffsetX.toPx() },
                    /* dy = */ density.run { thumbShadowOffsetY.toPx() },
                    /* shadowColor = */ thumbShadowColor.toArgb(),
                )
                // 形状绘制
                it.drawCircle(
                    Offset(size.width * progressPercent, size.height / 2),
                    thumbRadius,
                    paint
                )
            }
            // 锚点
            drawCircle(
                color = thumbColor,
                radius = thumbRadius,
                center = Offset(size.width * progressPercent, size.height / 2)
            )
            if (showProgressText) {
                // 进度值背景
                val progressBgColor = Color(0xFF464646)
                val progressBgWidth = density.run { 54.dp.toPx() }
                val progressBgHeight = density.run { 40.dp.toPx() }
                val progressBgTriangleWidth = density.run { 18.dp.toPx() }
                val progressBgTriangleHeight = density.run { 9.dp.toPx() }
                val progressBgCornerRadius = density.run { 4.dp.toPx() }
                val thumbSizePx = density.run { thumbSize.toPx() }
                val thumbShadowRadiusPx = density.run { thumbShadowRadius.toPx() }
                drawPath(
                    path = SimplePath.Builder().apply {
                        // 左上角
                        moveTo(
                            x = size.width * progressPercent - progressBgWidth / 2,
                            y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgHeight,
                            startRadius = progressBgCornerRadius,
                            endRadius = progressBgCornerRadius,
                        )
                        // 右上角
                        lineTo(
                            x = size.width * progressPercent + progressBgWidth / 2,
                            y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgHeight,
                            startRadius = progressBgCornerRadius,
                            endRadius = progressBgCornerRadius,
                        )
                        // 右下角
                        lineTo(
                            x = size.width * progressPercent + progressBgWidth / 2,
                            y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgTriangleHeight,
                            startRadius = progressBgCornerRadius,
                            endRadius = progressBgCornerRadius,
                        )
                        // 三角形右顶角
                        lineTo(
                            x = size.width * progressPercent + progressBgTriangleWidth / 2,
                            y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgTriangleHeight,
                        )
                        // 三角形下顶角
                        lineTo(
                            x = size.width * progressPercent,
                            y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx,
                            startRadius = progressBgCornerRadius,
                            endRadius = progressBgCornerRadius,
                        )
                        // 三角形左顶角
                        lineTo(
                            x = size.width * progressPercent - progressBgTriangleWidth / 2,
                            y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgTriangleHeight,
                        )
                        // 左下角
                        lineTo(
                            x = size.width * progressPercent - progressBgWidth / 2,
                            y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgTriangleHeight,
                            startRadius = progressBgCornerRadius,
                            endRadius = progressBgCornerRadius,
                        )
                        close()
                    }.build().asComposePath(),
                    color = progressBgColor,
                )
                // 进度值
                val progressText = currentProgress.toBigDecimal()
                    .setScale(1, RoundingMode.HALF_UP)
                    .toFloat()
                    .toString()
                val textLayoutResult =
                    textMeasurer.measure(text = progressText, style = progressTextStyle)
                drawText(
                    textLayoutResult = textLayoutResult,
                    color = progressTextStyle.color,
                    topLeft = Offset(
                        x = size.width * progressPercent - textLayoutResult.size.width / 2,
                        y = -density.run { 35.dp.toPx() },
                    )
                )
            }
        })
}

@Preview(widthDp = 600, backgroundColor = 0xFFF6F6F6, showBackground = true)
@Composable
fun PreviewSeekbar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 20.dp)
        ) {
            Seekbar(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterStart),
                thumbSize = 32.dp,
            )
        }
    }
}