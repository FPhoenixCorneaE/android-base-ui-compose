package com.fphoenixcorneae.baseui.chart

import android.graphics.PointF
import android.view.MotionEvent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.input.pointer.RequestDisallowInterceptTouchEvent
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.angle
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * 饼状图
 * @param datas           图表数据，
 *                        first：颜色；
 *                        second：数值；
 *                        third：文字属性：first：文字；second：文字颜色；third：文字大小
 * @param enabled         是否可以触摸/点击图表
 * @param startAngle      开始角度
 * @param intervalDegree  间隔度数
 * @param backgroundColor 背景颜色
 * @param innerRadius     内圆半径
 * @param distance        选中移动距离
 * @param durationMillis  动画时长
 */
@Stable
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    datas: List<Triple<Color, Float, Triple<String, Color, TextUnit>>>,
    enabled: Boolean = true,
    startAngle: Float = 0f,
    intervalDegree: Float = 0f,
    backgroundColor: Color = Color.Transparent,
    innerRadius: Dp = 25.dp,
    distance: Dp = 10.dp,
    durationMillis: Int = 1000,
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val view = LocalView.current
    var targetFraction by remember { mutableFloatStateOf(if (view.isInEditMode) 1f else 0f) }
    val currentFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(durationMillis = durationMillis, easing = LinearEasing),
        visibilityThreshold = 0.01f,
        label = "",
    )
    val clipPath = Path()
    // 总数
    val totalNum = datas.map { it.second }.fold(0f) { a, b -> a + b }
    // 每一格的大小
    val each = (360f - intervalDegree * totalNum) / totalNum
    var centerOffset: Offset? = null
    val requestDisallowInterceptTouchEvent = RequestDisallowInterceptTouchEvent()
    // 点击图表
    var clickPosition by remember { mutableIntStateOf(-1) }
    var offsetAngle by remember { mutableFloatStateOf(0f) }
    var downAngle by remember { mutableFloatStateOf(0f) }
    var originAngle by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(key1 = Unit) {
        // 开始动画
        targetFraction = 1f
    }
    Canvas(
        modifier = modifier.pointerInteropFilter(requestDisallowInterceptTouchEvent) { event ->
            if (!enabled) {
                return@pointerInteropFilter false
            }
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    centerOffset?.let {
                        downAngle = PointF(event.x, event.y).angle(PointF(it.x, it.y))
                        originAngle = offsetAngle
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    centerOffset?.let {
                        requestDisallowInterceptTouchEvent.invoke(true)
                        offsetAngle = PointF(event.x, event.y).angle(PointF(it.x, it.y)) - downAngle + originAngle
                    }
                }

                MotionEvent.ACTION_UP -> {
                    centerOffset?.let {
                        // 当前角度
                        var angle = (PointF(event.x, event.y)).angle2(PointF(it.x, it.y))
                        // 当前偏移量
                        angle = getNormalizedAngle(angle)
                        // 当前滑动距离
                        val offset = getNormalizedAngle(offsetAngle)
                        // 位移后的距离
                        val a = getNormalizedAngle(angle - offset)
                        var startAngle = startAngle
                        datas.forEachIndexed { position, value ->
                            // 每一格的占比
                            val ration = each * value.second

                            val start = startAngle
                            val end = startAngle + ration

                            if (a in start..end) {
                                // 如果当前选中的重复按下，那么就让当前选中的关闭
                                clickPosition = if (clickPosition == position && clickPosition != -1) {
                                    -1
                                } else {
                                    // 否则重新赋值
                                    position
                                }
                                return@pointerInteropFilter true
                            }
                            startAngle = end
                        }
                    }
                }

                else -> {}
            }
            return@pointerInteropFilter true
        },
    ) {
        centerOffset = center
        // 裁剪掉中间圆形区域
        clipPath(
            clipPath.apply {
                reset()
                addOval(
                    Rect(
                        left = center.x - density.run { innerRadius.toPx() },
                        top = center.y - density.run { innerRadius.toPx() },
                        right = center.x + density.run { innerRadius.toPx() },
                        bottom = center.y + density.run { innerRadius.toPx() },
                    )
                )
            },
            clipOp = ClipOp.Difference,
        ) {
            // 绘制背景
            drawRect(color = backgroundColor)
            var startAngle = startAngle
            datas.forEachIndexed { position, value ->
                // 每一格的占比
                val ration = each * value.second
                val isSave = position == clickPosition % datas.size
                if (isSave) {
                    with(drawContext) {
                        canvas.save()
                        canvas.rotate(offsetAngle, center.x, center.y)
                        val angle = startAngle.toDouble() + ration / 2f
                        val dx = density.run { distance.toPx() } * cos(Math.toRadians(angle)).toFloat()
                        val dy = density.run { distance.toPx() } * sin(Math.toRadians(angle)).toFloat()
                        canvas.translate(dx, dy)
                        canvas.rotate(-offsetAngle, center.x, center.y)
                    }
                }
                startAngle *= currentFraction
                // 旋转
                rotate(offsetAngle) {
                    // 绘制扇形
                    drawArc(
                        color = value.first,
                        startAngle = startAngle,
                        sweepAngle = ration * currentFraction,
                        useCenter = true,
                    )
                }
                // 再转回来
                rotate(-offsetAngle) {}
                // 绘制文字
                drawText(
                    textMeasurer = textMeasurer,
                    startAngle = startAngle,
                    endAngle = startAngle + ration,
                    offsetAngle = offsetAngle,
                    radius = density.run { innerRadius.toPx() } + (center.x - density.run { innerRadius.toPx() } - if (isSave) density.run { distance.toPx() } else 0f) / 2f,
                    center = center,
                    text = datas[position].third,
                    currentFraction = currentFraction,
                )
                startAngle += ration + intervalDegree
                if (isSave) {
                    with(drawContext) {
                        canvas.restore()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawText(
    textMeasurer: TextMeasurer,
    startAngle: Float,
    endAngle: Float,
    offsetAngle: Float,
    radius: Float,
    center: Offset,
    text: Triple<String, Color, TextUnit>,
    currentFraction: Float,
) {
    val ration = (endAngle - startAngle) / 2f + startAngle + offsetAngle
    val dx = radius * cos(Math.toRadians(ration.toDouble())).toFloat() + center.x
    val dy = radius * sin(Math.toRadians(ration.toDouble())).toFloat() + center.y
    val textStyle = TextStyle(color = text.second.copy(currentFraction), fontSize = text.third * currentFraction)
    val textLayoutResult = textMeasurer.measure(text = text.first, style = textStyle)
    val textWidth = textLayoutResult.size.width
    val textHeight = textLayoutResult.size.height
    drawText(
        textMeasurer = textMeasurer,
        text = text.first,
        topLeft = Offset(x = dx - textWidth / 2f, y = dy - textHeight / 2f),
        style = textStyle,
        softWrap = false,
        maxLines = 1,
    )
}

private fun getNormalizedAngle(angle: Float): Float {
    var a = angle
    while (a < 0f) a += 360f
    return a % 360f
}

fun PointF.angle2(endP: PointF): Float {
    val startP = this
    val cx = endP.x
    val cy = endP.y
    val tx = startP.x - cx
    val ty = startP.y - cy
    val length = sqrt((tx * tx + ty * ty).toDouble())

    val r = acos(ty / length)

    var angle = Math.toDegrees(r).toFloat()

    if (startP.x > cx) angle = 360f - angle

    // add 90° because chart starts EAST
    angle += 90f

    // neutralize overflow
    if (angle > 360f) angle -= 360f
    return angle
}

@Preview
@Composable
fun PreviewPieChart() {
    PieChart(
        modifier = Modifier.size(120.dp),
        datas = listOf(
            Triple(Color.Red, 2f, Triple("红色", Color.Black, 10.sp)),
            Triple(Color.Green, 3f, Triple("绿色", Color.Black, 10.sp)),
            Triple(Color.Blue, 5f, Triple("蓝色", Color.Black, 10.sp)),
            Triple(Color.Yellow, 7f, Triple("黄色", Color.Black, 10.sp)),
        ),
    )
}