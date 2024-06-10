package com.fphoenixcorneae.baseui

import android.graphics.PointF
import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.baseui.graphics.path.SimplePath
import kotlin.math.cos
import kotlin.math.sin

/**
 * 五角星
 * 以五角星中心为圆心，五角星端点在外接圆上，五角星内部小角在内接圆上
 */
@Composable
fun Star(
    modifier: Modifier = Modifier,
    starSize: Dp = 24.dp,
    cornerRadius: Dp = 2.dp,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Transparent,
    backgroundColor: Color = Color.Transparent,
    filledColor: Color = Color.Red,
    @FloatRange(from = 0.0, to = 1.0) filledFraction: Float = 1f,
) {
    Box(
        modifier = modifier
            .size(size = starSize)
            .clip(StarShape(cornerRadius))
            .background(color = backgroundColor)
            .border(width = borderWidth, color = borderColor, shape = StarShape(cornerRadius))
            .drawWithContent {
                drawContent()
                drawRect(color = filledColor, size = size.copy(size.width * filledFraction))
            },
    )
}

class StarShape(val cornerRadius: Dp = 2.dp) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val startRadius = density.run { cornerRadius.toPx() }
        val endRadius = density.run { cornerRadius.toPx() }
        return Outline.Generic(
            path = SimplePath.Builder().apply {
                // 外圆半径
                val outerR = size.minDimension / 2
                // 内圆半径
                val innerR = outerR / 2
                // 中心坐标
                val centerX = size.width / 2
                val centerY = size.height / 2

                // 计算平均角度, 72°
                val averageAngle = 360f / 5
                // 计算大圆的外角的角度, 90°-72°=18°
                val outerCircleAngle = 90 - averageAngle
                // 计算出小圆内角的角度, 72°÷2+18°=54°
                val innerCircleAngle = averageAngle / 2 + outerCircleAngle
                // 创建2个点
                val outerPoint = PointF()
                val innerPoint = PointF()

                (0 until 5).forEach {
                    // 计算大圆上的点坐标，从右上角开始，逆时针连接
                    // x = Math.cos((18° + 72° * i) / 180f * Math.PI) * 大圆半径
                    // y = -Math.sin((18° + 72° * i) / 180f * Math.PI) * 大圆半径
                    outerPoint.x =
                        centerX + (cos(Math.toRadians((outerCircleAngle + it * averageAngle).toDouble())) * outerR).toFloat()
                    outerPoint.y =
                        centerY + (-sin(Math.toRadians((outerCircleAngle + it * averageAngle).toDouble())) * outerR).toFloat()
                    // 计算小圆上的点坐标
                    // x = Math.cos((54° + 72° * i) / 180f * Math.PI) * 小圆半径
                    // y = -Math.sin((54° + 72° * i) / 180f * Math.PI) * 小圆半径
                    innerPoint.x =
                        centerX + (cos(Math.toRadians((innerCircleAngle + it * averageAngle).toDouble())) * innerR).toFloat()
                    innerPoint.y =
                        centerY + (-sin(Math.toRadians((innerCircleAngle + it * averageAngle).toDouble())) * innerR).toFloat()
                    if (it == 0) {
                        // 先移动到第一个大圆上的点
                        moveTo(x = outerPoint.x, y = outerPoint.y, startRadius = startRadius, endRadius = endRadius)
                    } else {
                        // 坐标连接，先大圆角上的点，再到小圆角上的点
                        lineTo(x = outerPoint.x, y = outerPoint.y, startRadius = startRadius, endRadius = endRadius)
                    }
                    lineTo(x = innerPoint.x, y = innerPoint.y, startRadius = startRadius, endRadius = endRadius)
                }
                close()
            }.build().asComposePath(),
        )
    }
}

@Preview
@Composable
fun PreviewStar() {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Star()
        Spacer(modifier = Modifier.width(8.dp))
        Star(borderWidth = 1.dp, borderColor = Color.Magenta, filledColor = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Star(backgroundColor = Color.Gray, filledFraction = 0.5f)
    }
}