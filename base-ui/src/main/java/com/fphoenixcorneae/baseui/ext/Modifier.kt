package com.fphoenixcorneae.baseui.ext

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 无涟漪效果的点击事件
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.clickableNoRipple(
    enabled: Boolean = true,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled,
    ) {
        onClick()
    }
}

/**
 * 按蓝湖上的阴影参数来绘制阴影
 *
 * @param blurRadius 阴影模糊半径，模糊值增加，阴影会更加模糊
 * @param cornerRadius 阴影圆角半径
 * @param color 阴影颜色
 * @param offsetX x轴偏移值
 * @param offsetY y轴偏移值
 * @param spread 点差值，如果点差值增加，阴影的大小越大
 */
fun Modifier.shadow(
    blurRadius: Dp = 4.dp,
    cornerRadius: Dp = 0.dp,
    color: Color = Color.Black.copy(alpha = 0.16f),
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    spread: Dp = 0.dp
) = if (blurRadius <= 0.dp) this else this.drawWithCache {
    val paint = Paint()
    val frameworkPaint = paint.asFrameworkPaint()
    val cornerRadiusPixel = cornerRadius.toPx()
    val blurRadiusPixel = blurRadius.toPx()
    val spreadPixel = spread.toPx()
    val leftPixel = (0f - spreadPixel) + offsetX.toPx()
    val topPixel = (0f - spreadPixel) + offsetY.toPx()
    val rightPixel = (this.size.width + spreadPixel)
    val bottomPixel = (this.size.height + spreadPixel)
    val argbColor = color.toArgb()
    onDrawBehind {
        drawIntoCanvas {
            if (blurRadiusPixel != 0f) {
                /*
                 * The feature maskFilter used below to apply the blur effect only works
                 * with hardware acceleration disabled.
                 */
                frameworkPaint.maskFilter =
                    BlurMaskFilter(blurRadiusPixel, BlurMaskFilter.Blur.NORMAL)
            }

            frameworkPaint.color = argbColor
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = cornerRadiusPixel,
                radiusY = cornerRadiusPixel,
                paint
            )
        }
    }
}

/**
 * 综合点击，单击防抖
 *
 * @param enabled 是否启用点击
 * @param enableRipple 是否支持水波纹效果
 * @param rippleColor 水波纹颜色
 * @param rippleBounded 如果为真，波纹会被目标布局的边界截断。无界波纹总是从目标布局中心开始动画，有界波纹总是从触摸位置开始动画
 * @param rippleRadius 波纹的半径。如果设置 [Dp.Unspecified] 则大小将根据目标布局大小计算。
 * @param onSingleClick 点击回调
 */
fun Modifier.onCombinedSingleClick(
    enabled: Boolean = true,
    enableRipple: Boolean = true,
    singleClickIntervalMs: Long = 800,
    rippleColor: Color = Color.Unspecified,
    rippleBounded: Boolean = true,
    rippleRadius: Dp = Dp.Unspecified,
    interactionSource: MutableInteractionSource? = null,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onSingleClick: () -> Unit
) = composed {
    combinedClickable(
        enabled = enabled,
        interactionSource = interactionSource,
        indication = if (!enableRipple) null else {
            ripple(rippleBounded, rippleRadius, rippleColor)
        },
        onLongClick = onLongClick,
        onDoubleClick = onDoubleClick,
        onClick = run {
            var clickTime by remember {
                mutableLongStateOf(0L)
            }
            val block = {
                onClick?.invoke()
                val curTime = System.currentTimeMillis()
                if (curTime - clickTime > singleClickIntervalMs) {
                    clickTime = curTime
                    onSingleClick()
                }
            }
            block
        }
    )
}

/**
 * 带水波纹点击事件
 *
 * @param enabled 是否启用点击
 * @param enableRipple 是否支持水波纹效果
 * @param rippleColor 水波纹颜色
 * @param rippleBounded 如果为真，波纹会被目标布局的边界截断。无界波纹总是从目标布局中心开始动画，有界波纹总是从触摸位置开始动画
 * @param rippleRadius 波纹的半径。如果设置 [Dp.Unspecified] 则大小将根据目标布局大小计算。
 * @param onClick 点击回调
 */
fun Modifier.onClick(
    enabled: Boolean = true,
    enableRipple: Boolean = true,
    rippleColor: Color = Color.Unspecified,
    rippleBounded: Boolean = true,
    rippleRadius: Dp = Dp.Unspecified,
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit
) = clickable(
    enabled = enabled,
    interactionSource = interactionSource,
    indication = if (!enableRipple) null else {
        ripple(rippleBounded, rippleRadius, rippleColor)
    },
    onClick = onClick
)

/**
 * 带水波纹点击事件
 *
 * @param enabled 是否启用点击
 * @param enableRipple 是否支持水波纹效果
 * @param clickIntervalMs 点击防抖间隔
 * @param rippleColor 水波纹颜色
 * @param rippleBounded 如果为真，波纹会被目标布局的边界截断。无界波纹总是从目标布局中心开始动画，有界波纹总是从触摸位置开始动画
 * @param rippleRadius 波纹的半径。如果设置 [Dp.Unspecified] 则大小将根据目标布局大小计算。
 * @param onClick 点击回调
 */
fun Modifier.onSingleClick(
    enabled: Boolean = true,
    enableRipple: Boolean = true,
    clickIntervalMs: Long = 800,
    rippleColor: Color = Color.Unspecified,
    rippleBounded: Boolean = true,
    rippleRadius: Dp = Dp.Unspecified,
    isAutoClearFocus: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit
) = composed {
    val focusManager = LocalFocusManager.current
    clickable(
        enabled = enabled,
        interactionSource = interactionSource,
        indication = if (!enableRipple) null else {
            ripple(rippleBounded, rippleRadius, rippleColor)
        },
        onClick = run {
            var clickTime by remember {
                mutableLongStateOf(0L)
            }
            val block = {
                if (isAutoClearFocus) {
                    focusManager.clearFocus(false)
                }

                val curTime = System.currentTimeMillis()
                if (curTime - clickTime > clickIntervalMs) {
                    clickTime = curTime
                    onClick()
                }
            }
            block
        }
    )
}
