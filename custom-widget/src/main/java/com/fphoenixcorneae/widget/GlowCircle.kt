package com.fphoenixcorneae.widget

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePaint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 辉光呼吸圆圈
 * @param glowColor      辉光颜色
 * @param blurRadius     模糊范围
 * @param blurStyle      NORMAL: 模糊内部和外部的原始边界
 *                       SOLID:  在边界内画实线，在边界外模糊
 *                       INNER:  边界内模糊，边界外不画
 *                       OUTER:  边界内什么都不画，边界外模糊
 * @param startAlpha     开始的透明值
 * @param durationMillis 呼吸时长毫秒值
 */
@Composable
fun GlowCircle(
    modifier: Modifier = Modifier,
    glowColor: Color = Color.Yellow,
    blurRadius: Dp = 10.dp,
    blurStyle: BlurMaskFilter.Blur = BlurMaskFilter.Blur.OUTER,
    startAlpha: Float = 0.5f,
    durationMillis: Int = 1000,
) {
    val density = LocalDensity.current
    val alpha by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = startAlpha,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            // 反向执行
            repeatMode = RepeatMode.Reverse,
        ),
        label = "",
    )
    val paint = Paint()
    Box(
        modifier = modifier.drawWithContent {
            drawContent()
            drawIntoCanvas {
                paint.color = glowColor.copy(alpha = alpha).toArgb()
                paint.strokeWidth = 2f
                paint.maskFilter = BlurMaskFilter(
                    /* radius = */ density.run { blurRadius.toPx() },
                    /* style = */ blurStyle,
                )
                val radius = size.minDimension / 2 - density.run { blurRadius.toPx() }
                it.drawCircle(center = center, radius = radius, paint = paint.asComposePaint())
            }
        },
    )
}

@Preview
@Composable
fun PreviewGlowCircle() {
    GlowCircle(
        modifier = Modifier.size(60.dp)
    )
}