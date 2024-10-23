package com.fphoenixcorneae.baseui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 三角形
 * @param cornerRadius 圆角半径
 * @param rotateDegree 旋转角度
 */
@Composable
fun Triangle(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    width: Dp = 3.dp,
    height: Dp = 2.dp,
    cornerRadius: Dp = 0.dp,
    rotateDegree: Float = 0f,
) {
    val density = LocalDensity.current
    Canvas(
        modifier = Modifier
            .then(other = modifier)
            .size(width = width, height = height)
            .rotate(degrees = rotateDegree),
    ) {
        val rect = Rect(Offset.Zero, size)
        val trianglePath = Path().apply {
            moveTo(rect.topCenter)
            lineTo(rect.bottomRight)
            lineTo(rect.bottomLeft)
            // note that two more point repeats needed to round all corners
            close()
        }

        drawIntoCanvas { canvas ->
            canvas.drawOutline(
                outline = Outline.Generic(trianglePath),
                paint = Paint().apply {
                    this.color = color
                    pathEffect = PathEffect.cornerPathEffect(density.run { cornerRadius.toPx() })
                }
            )
        }
    }
}

fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)

@Preview
@Composable
fun PreviewTriangle() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Triangle(width = 30.dp, height = 20.dp, color = Color.Black, cornerRadius = 0.dp)
        Triangle(
            width = 30.dp,
            height = 20.dp,
            color = Color.White,
            cornerRadius = 4.dp,
            rotateDegree = 180f
        )
    }
}