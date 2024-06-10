package com.fphoenixcorneae.baseui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

/**
 * 跑马灯光圈效果
 * @param backgroundColor 光圈背景颜色
 * @param thickness       光圈厚度
 * @param radius          圆角半径
 * @param color1Stops     左上角颜色及其在渐变区域中的偏移量
 * @param color2Stops     右下角颜色及其在渐变区域中的偏移量
 * @param durationMillis  动画转一圈时长(毫秒值)
 */
@Composable
fun MarqueeAperture(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Gray,
    thickness: Dp = 4.dp,
    radius: Dp = 8.dp,
    color1Stops: List<Pair<Float, Color>>? = listOf(0f to Color.Transparent, 1f to Color.Blue),
    color2Stops: List<Pair<Float, Color>>? = listOf(0f to Color.Transparent, 1f to Color.Red),
    durationMillis: Int = 3000,
) {
    val density = LocalDensity.current
    val rotateDegree by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "",
    )
    val clipPath = Path()
    Canvas(modifier = modifier) {
        // 裁剪矩形区域，绘制在裁剪好的矩形区域内
        clipPath(
            path = clipPath.apply {
                reset()
                addRoundRect(
                    RoundRect(
                        left = 0f,
                        top = 0f,
                        right = size.width,
                        bottom = size.height,
                        cornerRadius = CornerRadius(
                            x = density.run { radius.toPx() },
                            y = density.run { radius.toPx() },
                        )
                    ),
                )
            },
            clipOp = ClipOp.Intersect,
        ) {
            // 裁剪掉中间区域
            clipPath(
                path = clipPath.apply {
                    reset()
                    addRoundRect(
                        RoundRect(
                            left = density.run { thickness.toPx() },
                            top = density.run { thickness.toPx() },
                            right = size.width - density.run { thickness.toPx() },
                            bottom = size.height - density.run { thickness.toPx() },
                            cornerRadius = CornerRadius(
                                x = (size.width - density.run { thickness.toPx() * 2 }) * (density.run { radius.toPx() } / size.width),
                                y = (size.height - density.run { thickness.toPx() * 2 }) * (density.run { radius.toPx() } / size.height),
                            )
                        ),
                    )
                },
                clipOp = ClipOp.Difference,
            ) {
                // 绘制背景
                drawRect(color = backgroundColor)
                // 旋转
                rotate(degrees = rotateDegree) {
                    // 绘制左上角矩形
                    if (!color1Stops.isNullOrEmpty()) {
                        drawRect(
                            brush = Brush.linearGradient(
                                colorStops = color1Stops.toTypedArray(),
                                start = Offset(x = size.width / 2, y = size.height / 2),
                                end = Offset(x = size.width / 2, y = 0f),
                            ),
                            topLeft = Offset(x = -size.width / 2, y = -size.height / 2),
                            size = Size(width = max(size.width, size.height), height = max(size.width, size.height)),
                        )
                    }
                    // 绘制右下角矩形
                    if (!color2Stops.isNullOrEmpty()) {
                        drawRect(
                            brush = Brush.linearGradient(
                                colorStops = color2Stops.toTypedArray(),
                                start = Offset(x = size.width, y = size.height / 2),
                                end = Offset(x = size.width, y = size.height),
                            ),
                            topLeft = Offset(x = size.width / 2, y = size.height / 2),
                            size = Size(width = max(size.width, size.height), height = max(size.width, size.height)),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMarqueeAperture() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        MarqueeAperture(modifier = Modifier.size(60.dp), radius = 8.dp)
        Spacer(modifier = Modifier.width(8.dp))
        MarqueeAperture(modifier = Modifier.size(60.dp), radius = 30.dp, color2Stops = null)
        Spacer(modifier = Modifier.width(8.dp))
        MarqueeAperture(
            modifier = Modifier
                .width(80.dp)
                .height(60.dp),
            radius = 0.dp,
            color1Stops = null
        )
    }
}