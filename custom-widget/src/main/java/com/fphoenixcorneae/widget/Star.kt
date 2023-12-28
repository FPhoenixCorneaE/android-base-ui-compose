package com.fphoenixcorneae.widget

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * 五角星
 * 以五角星中心为圆心，五角星端点在外接圆上，五角星内部小角在内接圆上
 */
@Composable
fun Star(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = Color.Yellow,
    isFill: Boolean = true,
) {
    // 计算平均角度, 72°
    val averageAngle = 360f / 5
    // 计算大圆的外角的角度, 90°-72°=18°
    val outerCircleAngle = 90 - averageAngle
    // 计算出小圆内角的角度, 72°÷2+18°=54°
    val innerCircleAngle = averageAngle / 2 + outerCircleAngle
    // 创建2个点
    val outerPoint = PointF()
    val innerPoint = PointF()
    val path = Path()
    Canvas(modifier = modifier.size(size)) {
        translate(left = center.x, top = center.y) {
            drawIntoCanvas {
                val outerR = this.size.width / 2
                val innerR = outerR / 2
                path.reset()
                (0 until 5).forEach {
                    // 计算大圆上的点坐标，从右上角开始，逆时针连接
                    // x = Math.cos((18° + 72° * i) / 180f * Math.PI) * 大圆半径
                    // y = -Math.sin((18° + 72° * i) / 180f * Math.PI) * 大圆半径
                    outerPoint.x =
                        (cos(Math.toRadians((outerCircleAngle + it * averageAngle).toDouble())) * outerR).toFloat()
                    outerPoint.y =
                        (-sin(Math.toRadians((outerCircleAngle + it * averageAngle).toDouble())) * outerR).toFloat()
                    // 计算小圆上的点坐标
                    // x = Math.cos((54° + 72° * i) / 180f * Math.PI) * 小圆半径
                    // y = -Math.sin((54° + 72° * i) / 180f * Math.PI) * 小圆半径
                    innerPoint.x =
                        (cos(Math.toRadians((innerCircleAngle + it * averageAngle).toDouble())) * innerR).toFloat()
                    innerPoint.y =
                        (-sin(Math.toRadians((innerCircleAngle + it * averageAngle).toDouble())) * innerR).toFloat()
                    // 先移动到第一个大圆上的点
                    if (it == 0) {
                        path.moveTo(x = outerPoint.x, y = outerPoint.y)
                    }
                    // 坐标连接，先大圆角上的点，再到小圆角上的点
                    path.lineTo(x = outerPoint.x, y = outerPoint.y)
                    path.lineTo(x = innerPoint.x, y = innerPoint.y)
                }
                path.close()
                it.drawPath(
                    path = path,
                    paint = Paint().apply {
                        isAntiAlias = true
                        this.color = color
                        if (isFill) {
                            style = PaintingStyle.Fill
                        } else {
                            style = PaintingStyle.Stroke
                            strokeWidth = 2f
                        }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewStar() {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Star()
        Spacer(modifier = Modifier.width(8.dp))
        Star(color = Color.Magenta)
        Spacer(modifier = Modifier.width(8.dp))
        Star(isFill = false)
    }
}