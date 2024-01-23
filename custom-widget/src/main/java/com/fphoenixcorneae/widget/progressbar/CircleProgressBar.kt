package com.fphoenixcorneae.widget.progressbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 环形进度条，支持渐变进度，支持动画，支持逆时针增加进度，支持非满圆
 * @param progress         圆环当前进度
 * @param max              圆环最大进度
 * @param radius           圆环内环半径
 * @param color            圆环进度颜色
 * @param colorStops       圆环渐变颜色
 * @param backgroundColor  圆环背景色
 * @param ringThickness    圆环的厚度
 * @param clockwise        是否顺时针增加，默认为true
 * @param centerText       中心文字
 * @param centerTextStyle  中心文字样式
 * @param startAngle       开始角度，右边为0°，左边为-180°
 * @param maxAngle         最大角度，默认360°
 * @param drawInitialPoint 是否绘制初始点
 * @param durationMillis   动画时长
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun CircleProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    max: Float = 100f,
    radius: Dp = 60.dp,
    color: Color = Color.Blue,
    colorStops: List<Pair<Float, Color>>? = null,
    backgroundColor: Color = Color(0xFFF2F3F5),
    ringThickness: Dp = 3.dp,
    clockwise: Boolean = true,
    centerText: AnnotatedString? = null,
    centerTextStyle: TextStyle? = null,
    startAngle: Float = -90f,
    maxAngle: Float = 360f,
    drawInitialPoint: Boolean = true,
    durationMillis: Int = 1000,
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    var animState by remember { mutableStateOf(AnimState.Ready) }
    val degree = (maxAngle * progress.coerceAtLeast(0f).coerceAtMost(max) / max)
    val animatedProgress by animateFloatAsState(
        targetValue = if (animState == AnimState.Ready) 0f else degree,
        animationSpec = tween(durationMillis = durationMillis),
        finishedListener = {
            animState = AnimState.Stop
        },
    )
    LaunchedEffect(key1 = Unit) {
        animState = AnimState.Start
    }
    Canvas(
        modifier = Modifier
            .then(modifier)
            .size(radius * 2 + ringThickness * 2),
    ) {
        rotate(degrees = startAngle) {
            // 绘制圆环背景
            drawArc(
                color = backgroundColor,
                startAngle = 0f,
                sweepAngle = if (clockwise) maxAngle else -maxAngle,
                useCenter = false,
                topLeft = Offset(
                    x = density.run { ringThickness.toPx() / 2 },
                    y = density.run { ringThickness.toPx() / 2 }),
                size = Size(
                    width = size.width - density.run { ringThickness.toPx() },
                    height = size.height - density.run { ringThickness.toPx() }),
                style = Stroke(
                    width = density.run { ringThickness.toPx() - 0.5f },
                    miter = 4f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round,
                ),
            )
            if (drawInitialPoint) {
                // 绘制初始点
                drawCircle(
                    color = color,
                    radius = density.run { ringThickness.toPx() / 2 },
                    center = center.getCircleOffset(radius = density.run { (ringThickness / 2 + radius).toPx() }, 0f),
                )
            }
            // 绘制进度
            val stops = colorStops?.map { it.first }?.flatMap { listOf(maxAngle * it) }
            val colors = colorStops?.map { it.second }
            repeat(times = animatedProgress.roundToInt()) {
                var progressColor = color
                if (!colors.isNullOrEmpty()) {
                    var colorFraction = 0f
                    var i = 0
                    stops?.run {
                        forEachIndexed { index, fl ->
                            if (index + 1 < stops.size && it.toFloat() in fl..stops[index + 1]) {
                                i = index
                                colorFraction = (it - fl) / (stops[index + 1] - fl)
                                return@run
                            }
                        }
                    }
                    progressColor = lerp(start = colors[i], stop = colors[i + 1], fraction = colorFraction)
                }
                drawCircle(
                    color = progressColor,
                    radius = density.run { ringThickness.toPx() / 2 },
                    center = center.getCircleOffset(
                        radius = density.run { (ringThickness / 2 + radius).toPx() },
                        if (clockwise) it.toFloat() else -it.toFloat(),
                    ),
                )
            }
        }
        centerText?.let {
            val centerTextLayoutResult = textMeasurer.measure(text = it, style = centerTextStyle ?: TextStyle.Default)
            // 绘制中心文字
            drawText(
                textMeasurer = textMeasurer,
                text = it,
                style = centerTextStyle ?: TextStyle.Default,
                topLeft = center.minus(
                    Offset(
                        centerTextLayoutResult.size.width / 2f,
                        centerTextLayoutResult.size.height / 2f,
                    )
                ),
            )
        }
    }
}

internal enum class AnimState {
    Ready,
    Start,
    Stop,
}

/**
 * 根据圆心偏移获取圆上的偏移
 * @param radius 半径
 * @param angle  角度
 */
fun Offset.getCircleOffset(radius: Float, angle: Float): Offset = run {
    val x = x + radius * cos(Math.toRadians(angle.toDouble()))
    val y = y + radius * sin(Math.toRadians(angle.toDouble()))
    Offset(x.toFloat(), y.toFloat())
}

@Preview
@Composable
fun PreviewCircleProgressBar() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CircleProgressBar(
            radius = 30.dp,
            progress = 100f,
            colorStops = listOf(0f to Color.Blue, 0.5f to Color.Yellow, 1f to Color.Red),
        )
        Spacer(modifier = Modifier.width(8.dp))
        CircleProgressBar(
            modifier = Modifier.background(Color.White),
            progress = 100f,
            radius = 30.dp,
            color = Color.Red,
            colorStops = listOf(0f to Color.Blue, 0.5f to Color.Yellow, 1f to Color.Red),
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
}