package com.fphoenixcorneae.widget

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

/**
 * 根据心形函数画心❤
 * 参数方程式：
 * x(t) = 16sin³(t)
 * y(t) = 13cos(t) - 5cos(2t) - 2cos(3t) - cos(4t)
 * 在[0, 2π]上绘制
 * @param size   大小
 * @param color  颜色
 * @param rate   半径变化率
 * @param isFill 是否实心
 */
@Composable
fun Heart(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = Color.Red,
    rate: Float = 2f,
    isFill: Boolean = true,
) {
    Canvas(modifier = modifier.size(size)) {
        drawIntoCanvas {
            it.drawPath(
                path = Path().apply {
                    reset()
                    moveTo(center.x, center.y - 5 * rate)
                    var t = 0.0
                    while (t <= 2 * Math.PI) {
                        var x = 16 * sin(t).pow(3.0)
                        var y = 13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t)
                        x *= rate
                        y *= rate
                        x = center.x - x
                        y = center.y - y
                        lineTo(x = x.toFloat(), y = y.toFloat())
                        t += 0.001
                    }
                },
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

@Preview
@Composable
fun PreviewHeart() {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Heart()
        Spacer(modifier = Modifier.width(8.dp))
        Heart(color = Color.Magenta)
        Spacer(modifier = Modifier.width(8.dp))
        Heart(isFill = false)
    }
}