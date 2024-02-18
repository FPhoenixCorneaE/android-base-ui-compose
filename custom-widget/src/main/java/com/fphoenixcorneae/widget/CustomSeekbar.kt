package com.fphoenixcorneae.widget

import androidx.annotation.FloatRange
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
import com.fphoenixcorneae.graphics.path.SimplePath
import kotlin.math.roundToInt

/**
 * 自定义拖动条
 * @param modifier                强制修改符，用于指定此可组合的大小策略
 * @param initialProgress         初始化进度
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
fun CustomSeekbar(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 1.0) initialProgress: Float = 0.5f,
    progressTextStyle: TextStyle = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.W500),
    progressBackgroundColor: Color = Color(0xFFE0E0E0),
    progressColor: Color = Color(0xFF3C7AF3),
    thickness: Dp = 6.dp,
    thumbSize: Dp = 32.dp,
    thumbColor: Color = Color.White,
    thumbShadowColor: Color = Color.Black.copy(0.4f),
    thumbShadowRadius: Dp = 6.dp,
    thumbShadowOffsetX: Dp = 0.dp,
    thumbShadowOffsetY: Dp = 0.dp,
    onProgressChanged: (progress: Float) -> Unit = {},
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    // 当前进度，范围0-1之间， 初始为0
    var progress by remember { mutableFloatStateOf(initialProgress) }
    // bar是否被按下
    var barPressed by remember { mutableStateOf(false) }
    // 锚点的半径, 根据barPressed的状态'平滑'地改变自身的大小
    val thumbRadius by animateFloatAsState(
        if (barPressed) density.run { (thumbSize + 4.dp).toPx() } / 2 else density.run { thumbSize.toPx() } / 2,
        label = ""
    )
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thumbSize + thumbShadowRadius * 2)
            .pointerInput(Unit) {
                // 响应滑动事件
                detectDragGestures(
                    onDragStart = { barPressed = true },
                    onDragCancel = { barPressed = false },
                    onDragEnd = {
                        // 滑动结束时， 恢复锚点大小，并回调onProgressChanged函数
                        barPressed = false
                        onProgressChanged.invoke(progress)
                    },
                    onDrag = { change, dragAmount ->
                        // 滑动过程中， 实时刷新progress的值(注意左右边界的问题)，
                        // 此值一旦改变， 整个Seekbar就会重组(刷新)
                        progress = if (change.position.x < 0) {
                            0f
                        } else if (change.position.x > size.width) {
                            1f
                        } else {
                            (change.position.x / size.width)
                        }
                    })
            }
            .pointerInput(Unit) {
                // 响应点击事件， 直接跳到该进度处
                detectTapGestures(
                    onTap = {
                        progress = (it.x / size.width)
                        barPressed = false
                    },
                )
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
                end = Offset(size.width * progress, size.height / 2),
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
                    Offset(size.width * progress, size.height / 2),
                    thumbRadius,
                    paint
                )
            }
            // 锚点
            drawCircle(
                color = thumbColor,
                radius = thumbRadius,
                center = Offset(size.width * progress, size.height / 2)
            )
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
                        x = size.width * progress - progressBgWidth / 2,
                        y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgHeight,
                        startRadius = progressBgCornerRadius,
                        endRadius = progressBgCornerRadius,
                    )
                    // 右上角
                    lineTo(
                        x = size.width * progress + progressBgWidth / 2,
                        y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgHeight,
                        startRadius = progressBgCornerRadius,
                        endRadius = progressBgCornerRadius,
                    )
                    // 右下角
                    lineTo(
                        x = size.width * progress + progressBgWidth / 2,
                        y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgTriangleHeight,
                        startRadius = progressBgCornerRadius,
                        endRadius = progressBgCornerRadius,
                    )
                    // 三角形右顶角
                    lineTo(
                        x = size.width * progress + progressBgTriangleWidth / 2,
                        y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgTriangleHeight,
                    )
                    // 三角形下顶角
                    lineTo(
                        x = size.width * progress,
                        y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx,
                        startRadius = progressBgCornerRadius,
                        endRadius = progressBgCornerRadius,
                    )
                    // 三角形左顶角
                    lineTo(
                        x = size.width * progress - progressBgTriangleWidth / 2,
                        y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgTriangleHeight,
                    )
                    // 左下角
                    lineTo(
                        x = size.width * progress - progressBgWidth / 2,
                        y = center.y - thumbSizePx / 2 - thumbShadowRadiusPx - progressBgTriangleHeight,
                        startRadius = progressBgCornerRadius,
                        endRadius = progressBgCornerRadius,
                    )
                    close()
                }.build().asComposePath(),
                color = progressBgColor,
            )
            // 进度值
            val progressText = (progress * 100).roundToInt().toString()
            val textLayoutResult = textMeasurer.measure(text = progressText, style = progressTextStyle)
            drawText(
                textLayoutResult = textLayoutResult,
                color = progressTextStyle.color,
                topLeft = Offset(
                    x = size.width * progress - textLayoutResult.size.width / 2,
                    y = -density.run { 38.dp.toPx() },
                )
            )
        })
}

@Preview(widthDp = 600, backgroundColor = 0xFFF6F6F6, showBackground = true)
@Composable
fun PreviewCustomSeekbar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 20.dp)
        ) {
            CustomSeekbar(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterStart)
            )
        }
    }
}