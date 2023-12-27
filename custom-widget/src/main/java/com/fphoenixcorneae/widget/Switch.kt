package com.fphoenixcorneae.widget

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * iOS 效果的 Switch
 */
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun Switch(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    width: Dp = 48.dp,
    height: Dp = 24.dp,
    edgeGap: Dp = 2.dp,
    trackColor: Color = Color(0xFFCCCCCC),
    checkedTrackColor: Color = Color.Blue,
    thumbColor: Color = Color.Gray,
    checkedThumbColor: Color = Color.White,
    onCheckedChanged: (@ParameterName("checked") Boolean) -> Unit = {},
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    // 滑动状态
    val swipeableState = rememberSwipeableState(initialValue = 0, animationSpec = tween())
    // 是否选中状态
    var isChecked by remember { mutableStateOf(checked) }
    // Thumb 半径大小
    val thumbRadius = height / 2f - edgeGap
    // Track 的 scale 大小（0f - 1f）
    val trackScale = rememberSaveable(inputs = arrayOf(checked)) { mutableStateOf(1f) }
    // Track 颜色
    val checkedTrackLerpColor by remember {
        derivedStateOf {
            lerp(
                // 开始颜色
                start = trackColor,
                // 结束颜色
                stop = checkedTrackColor,
                // 选中的Track颜色值，根据缩放值计算颜色【转换的渐变进度】
                fraction = minOf((1 - trackScale.value) * 2, 1f),
            )
        }
    }
    // Thumb 颜色
    val checkedThumbLerpColor by remember {
        derivedStateOf {
            lerp(
                // 开始颜色
                start = thumbColor,
                // 结束颜色
                stop = checkedThumbColor,
                // 选中的Track颜色值，根据缩放值计算颜色【转换的渐变进度】
                fraction = minOf((1 - trackScale.value) * 2, 1f),
            )
        }
    }
    // 开始锚点位置
    val startAnchor = density.run { (thumbRadius + edgeGap).toPx() }
    // 结束锚点位置
    val endAnchor = density.run { (width - thumbRadius - edgeGap).toPx() }
    val anchors = mapOf(startAnchor to 0, endAnchor to 1)
    LaunchedEffect(swipeableState.offset.value) {
        val swipeOffset = swipeableState.offset.value
        // Track 缩放大小更新
        trackScale.value = 1f - ((swipeOffset - startAnchor) / endAnchor).run { if (this < 0) 0f else this }
        // 更新状态
        val stateChecked = swipeOffset >= endAnchor
        val stateUnchecked = swipeOffset <= startAnchor
        if (stateChecked && !isChecked) {
            isChecked = true
            // 回调状态
            onCheckedChanged(true)
        } else if (stateUnchecked && isChecked) {
            isChecked = false
            // 回调状态
            onCheckedChanged(false)
        }
    }
    Canvas(
        modifier = Modifier
            .then(modifier)
            .size(width = width, height = height)
            .swipeAdsorb(
                anchors = anchors,
                swipeableState = swipeableState
            ) {
                // 点击
                coroutineScope.launch { swipeableState.animateTo(targetValue = if (isChecked) 0 else 1) }
            },
    ) {
        // 绘制 Track
        drawRoundRect(color = checkedTrackLerpColor, cornerRadius = CornerRadius(x = size.height, y = size.height))
        // 绘制 Thumb
        drawCircle(
            color = checkedThumbLerpColor,
            radius = thumbRadius.toPx(),
            center = Offset(x = swipeableState.offset.value, y = size.height / 2f),
        )
    }
}


/**
 * 滑动吸附效果
 * https://developer.android.google.cn/jetpack/compose/gestures?hl=zh-cn#swiping
 */
@SuppressLint("UnnecessaryComposedModifier")
@OptIn(ExperimentalMaterialApi::class)
internal fun Modifier.swipeAdsorb(
    anchors: Map<Float, Int>,
    swipeableState: SwipeableState<Int>,
    onClick: () -> Unit,
) = composed {
    then(
        other = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        // 点击回调
                        onClick()
                    }
                )
            }
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ ->
                    // 锚点间吸附效果的临界阈值
                    FractionalThreshold(0.3f)
                },
                // 水平方向
                orientation = Orientation.Horizontal,
            )
    )
}
