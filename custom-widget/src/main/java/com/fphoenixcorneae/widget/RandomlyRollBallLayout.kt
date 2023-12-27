package com.fphoenixcorneae.widget

import android.graphics.PointF
import android.view.MotionEvent
import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.RequestDisallowInterceptTouchEvent
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.lang.Float.max
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

val randomColor: Color
    get() = Color(
        red = (0 until 255).random(),
        green = (0 until 255).random(),
        blue = (0 until 255).random(),
        alpha = 255,
    )

/**
 * 判断当前PointF是否在 bPointF内
 * @param bPadding 外边距
 */
fun PointF.contains(b: PointF, bPadding: Float = 0f): Boolean {
    val isX = this.x <= b.x + bPadding && this.x >= b.x - bPadding
    val isY = this.y <= b.y + bPadding && this.y >= b.y - bPadding
    return isX && isY
}

/**
 * 通过勾股定理算两个PointF之间的距离
 */
fun PointF.distance(b: PointF): Float = let {
    val a = this
    // 这里 * 1.0 是为了转Double
    val dx = b.x - a.x * 1.0
    val dy = b.y - a.y * 1.0
    return@let sqrt(dx.pow(2) + dy.pow(2)).toFloat()
}

/**
 * 计算角度
 */
fun PointF.angle(endP: PointF): Float {
    val startP = this
    // 原始位置
    val angle = when {
        // end在start右下角
        startP.x >= endP.x && startP.y >= endP.y -> 0
        // end在start右上角
        startP.x >= endP.x && startP.y <= endP.y -> 270
        // end在start左上角
        startP.x <= endP.x && startP.y <= endP.y -> 180
        // end在start左下角
        startP.x <= endP.x && startP.y >= endP.y -> 90
        else -> 0
    }
    // 计算距离
    val dx = startP.x - endP.x
    val dy = startP.y - endP.y
    // 弧度
    val radian = abs(atan(dy / dx))

    // 弧度转角度
    var a = Math.toDegrees(radian.toDouble()).toFloat()

    if (startP.x <= endP.x && startP.y >= endP.y) {
        // 左下角
        a = 90 - a
    } else if (startP.x >= endP.x && startP.y <= endP.y) {
        // 右上角
        a = 90 - a
    }
    return a + angle
}

/**
 * 随机滚动小球布局
 * @param count  总显示个数
 * @param radius 连接半径
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RandomlyRollBallLayout(
    modifier: Modifier = Modifier,
    count: Int = 120,
    radius: Dp = 100.dp,
) {
    val density = LocalDensity.current
    val recomposeScope = currentRecomposeScope
    val speeds = remember { mutableStateListOf<SpeedBean>() }
    val movePointF by remember {
        mutableStateOf(
            PointF(/* x = */ -density.run { radius.toPx() }, /* y = */ -density.run { radius.toPx() })
        )
    }
    val requestDisallowInterceptTouchEvent = RequestDisallowInterceptTouchEvent()
    Canvas(modifier = modifier.pointerInteropFilter(
        requestDisallowInterceptTouchEvent = requestDisallowInterceptTouchEvent
    ) { event ->
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                requestDisallowInterceptTouchEvent.invoke(true)
                movePointF.x = x
                movePointF.y = y
                val a = PointF(x, y)
                speeds.forEach {
                    val b = PointF(it.x, it.y)
                    if (a.contains(b, density.run { radius.toPx() })) {
                        if (it.angle == 0f) {
                            it.angle = a.angle(b)
                        }
                        it.x = (x + density.run { radius.toPx() } * cos(Math.toRadians(it.angle.toDouble()))).toFloat()
                        it.x = (x + density.run { radius.toPx() } * sin(Math.toRadians(it.angle.toDouble()))).toFloat()
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                movePointF.x = -density.run { radius.toPx() }
                movePointF.y = -density.run { radius.toPx() }
                speeds.forEach { it.angle = 0f }
            }

            else -> {}
        }
        return@pointerInteropFilter true
    }) {
        if (speeds.isEmpty()) {
            repeat((0..count).count()) {
                speeds.add(
                    SpeedBean(
                        x = (0 until size.width.toInt()).random().toFloat(),
                        y = (0 until size.height.toInt()).random().toFloat(),
                    )
                )
            }
        }
        speeds.forEach {
            drawCircle(it.color, it.radius, center = Offset(it.x, it.y))
            val b = PointF(it.x, it.y)
            speeds.forEach { value ->
                val a = PointF(value.x, value.y)
                if (a.contains(b, density.run { radius.toPx() })) {
                    // 计算距离
                    val d = a.distance(b)
                    val alpha = max(0f, min(1f, d / density.run { radius.toPx() }))
                    if (PointF(movePointF.x, movePointF.y).contains(b, density.run { radius.toPx() })) {
                        drawLine(
                            color = value.color,
                            start = Offset(value.x, value.y),
                            end = Offset(movePointF.x, movePointF.y), alpha = alpha,
                        )
                    } else {
                        // 每个点之间的连接线
                        drawLine(
                            color = value.color,
                            start = Offset(value.x, value.y),
                            end = Offset(it.x, it.y),
                            alpha = alpha,
                        )
                    }
                }
            }
            // 判断是否触摸中，如果没有触摸中一直移动
            if (!PointF(movePointF.x, movePointF.y).contains(PointF(it.x, it.y), density.run { radius.toPx() })
            ) {
                it.x += it.vX
                it.y += it.vY
            }
            drawCircle(it.color, it.radius, center = Offset(it.x, it.y))
            when {
                it.x >= size.width -> it.vX = -it.randomV.toFloat()
                it.x <= 0 -> it.vX = it.randomV.toFloat()
                it.y <= 0 -> it.vY = it.randomV.toFloat()
                it.y >= size.height -> it.vY = -it.randomV.toFloat()
            }
        }
        recomposeScope.invalidate()
    }
}

internal data class SpeedBean(
    /** 坐标 */
    var x: Float,
    var y: Float,
    /** 半径 */
    var radius: Float = (5 until 20).random().toFloat(),
    /** 颜色 */
    var color: Color = randomColor,
    /** 速度 */
    var vX: Float = (-5 until 5).random().toFloat(),
    var vY: Float = (-5 until 5).random().toFloat(),
    /** 随机速度 */
    val randomV: Int = (1..5).random(),
    /** 相对于按下时候的角度 */
    var angle: Float = 0f,
    /** 碰撞磨损 */
    @FloatRange(from = 0.0, to = 1.0)
    var collisionWear: Float = (5 until 9).random() / 10f,
)