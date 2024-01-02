package com.fphoenixcorneae.widget

import android.view.MotionEvent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.floor

/**
 * 评分组件
 * @param starSize        星星大小
 * @param numStars        最大个数
 * @param space           间距
 * @param rating          默认评分, 最高评分就是星星个数
 * @param backgroundColor 星星背景颜色
 * @param filledColor     星星填充颜色
 * @param borderWidth     星星边框宽度
 * @param borderColor     星星边框颜色
 * @param touchEnable     是否可触摸/点击，修改评分
 * @param onRatingChanged 评分修改后回调
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starSize: Dp = 24.dp,
    numStars: Int = 5,
    space: Dp = 8.dp,
    rating: Float = 3f,
    backgroundColor: Color = Color.Gray,
    filledColor: Color = Color.Red,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Transparent,
    touchEnable: Boolean = true,
    onRatingChanged: ((Float) -> Unit)? = null,
) {
    val density = LocalDensity.current
    val recomposeScope = currentRecomposeScope
    val starSizePx = density.run { starSize.toPx() }
    val spacePx = density.run { space.toPx() }
    val width = starSize * numStars + space * (numStars - 1)
    val widthPx = density.run { width.toPx() }
    var mutableRating by remember { mutableStateOf(rating) }
    Row(
        modifier = modifier
            .width(width)
            .height(starSize)
            .pointerInteropFilter {
                if (touchEnable) {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                            val x = it.x
                                .coerceAtLeast(0f)
                                .coerceAtMost(widthPx)
                            val deviateRating = x / (starSizePx + spacePx)
                            var realRating = if (deviateRating > floor(deviateRating)) {
                                (x - spacePx * floor(deviateRating)) / starSizePx
                            } else {
                                deviateRating
                            }
                            realRating = BigDecimal(realRating.toString())
                                .setScale(/* newScale = */ 1, /* roundingMode = */ RoundingMode.HALF_UP)
                                .toFloat()
                            mutableRating = realRating
                            recomposeScope.invalidate()
                            onRatingChanged?.invoke(realRating)
                        }

                        else -> {}
                    }
                }
                return@pointerInteropFilter touchEnable
            },
    ) {
        repeat(numStars) {
            val filledFraction = if (mutableRating - it >= 1f) {
                1f
            } else {
                mutableRating - it
            }
            Star(
                starSize = starSize,
                borderWidth = borderWidth,
                borderColor = borderColor,
                backgroundColor = backgroundColor,
                filledColor = filledColor,
                filledFraction = filledFraction,
            )
            if (it < numStars - 1) {
                Spacer(modifier = Modifier.width(space))
            }
        }
    }
}

@Preview
@Composable
fun PreviewRatingBar() {
    RatingBar()
}