package com.fphoenixcorneae.baseui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 摇杆器
 * @param modifier               强制修改符，用于指定此可组合的大小策略
 * @param onLeftPressed          按压左箭头
 * @param onUpPressed            按压上箭头
 * @param onRightPressed         按压右箭头
 * @param onRightPressed         按压下箭头
 * @param onSteeringWheelChanged 方向盘变化监听
 */
@SuppressLint("UnusedBoxWithConstraintsScope", "ReturnFromAwaitPointerEventScope")
@Composable
fun Rocker(
    modifier: Modifier = Modifier,
    onLeftPressed: suspend CoroutineScope.() -> Unit = {},
    onUpPressed: suspend CoroutineScope.() -> Unit = {},
    onRightPressed: suspend CoroutineScope.() -> Unit = {},
    onDownPressed: suspend CoroutineScope.() -> Unit = {},
    onSteeringWheelChanged: (offset: Offset) -> Unit = {},
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f)
            .paint(
                painter = painterResource(id = R.mipmap.ic_rocker_bg),
                contentScale = ContentScale.FillBounds,
            ),
    ) {
        var isPressedLeft by remember { mutableStateOf(false) }
        Image(
            painter = painterResource(id = R.mipmap.ic_rocker_left),
            contentDescription = null,
            modifier = Modifier
                .padding(start = maxWidth / 20f)
                .size(maxWidth / 5f)
                .align(alignment = Alignment.CenterStart)
                .pointerInput(isPressedLeft) {
                    awaitPointerEventScope {
                        isPressedLeft = if (isPressedLeft) {
                            waitForUpOrCancellation()
                            coroutineScope.coroutineContext.cancelChildren()
                            false
                        } else {
                            awaitFirstDown(false)
                            coroutineScope.launch {
                                onLeftPressed()
                            }
                            true
                        }
                    }
                },
        )
        var isPressedTop by remember { mutableStateOf(false) }
        Image(
            painter = painterResource(id = R.mipmap.ic_rocker_up),
            contentDescription = null,
            modifier = Modifier
                .padding(top = maxWidth / 20f)
                .size(maxWidth / 5f)
                .align(alignment = Alignment.TopCenter)
                .pointerInput(isPressedTop) {
                    awaitPointerEventScope {
                        isPressedTop = if (isPressedTop) {
                            waitForUpOrCancellation()
                            coroutineScope.coroutineContext.cancelChildren()
                            false
                        } else {
                            awaitFirstDown(false)
                            coroutineScope.launch {
                                onUpPressed()
                            }
                            true
                        }
                    }
                },
        )
        var isPressedRight by remember { mutableStateOf(false) }
        Image(
            painter = painterResource(id = R.mipmap.ic_rocker_right),
            contentDescription = null,
            modifier = Modifier
                .padding(end = maxWidth / 20f)
                .size(maxWidth / 5f)
                .align(alignment = Alignment.CenterEnd)
                .pointerInput(isPressedRight) {
                    awaitPointerEventScope {
                        isPressedRight = if (isPressedRight) {
                            waitForUpOrCancellation()
                            coroutineScope.coroutineContext.cancelChildren()
                            false
                        } else {
                            awaitFirstDown(false)
                            coroutineScope.launch {
                                onRightPressed()
                            }
                            true
                        }
                    }
                },
        )
        var isPressedDown by remember { mutableStateOf(false) }
        Image(
            painter = painterResource(id = R.mipmap.ic_rocker_down),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = maxWidth / 20f)
                .size(maxWidth / 5f)
                .align(alignment = Alignment.BottomCenter)
                .pointerInput(isPressedDown) {
                    awaitPointerEventScope {
                        isPressedDown = if (isPressedDown) {
                            waitForUpOrCancellation()
                            coroutineScope.coroutineContext.cancelChildren()
                            false
                        } else {
                            awaitFirstDown(false)
                            coroutineScope.launch {
                                onDownPressed()
                            }
                            true
                        }
                    }
                },
        )
        // 中心摇杆
        // 拖拽
        var offset by remember { mutableStateOf(Offset.Zero) }
        LaunchedEffect(offset) {
            onSteeringWheelChanged(offset)
        }
        Box(
            modifier = Modifier
                .size(maxWidth / 3f)
                .align(alignment = Alignment.Center)
                // offset 要放在 background 之前
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .paint(
                    painterResource(id = R.mipmap.ic_rocker),
                    contentScale = ContentScale.FillBounds
                )
                .pointerInput(Unit) {
                    // 循环监听每一组事件序列
                    awaitEachGesture {
                        val downEvent = awaitFirstDown()
                        // 参照drag(pointerId, onDrag)
                        var pointer = downEvent.id
                        while (true) {
                            val change = awaitDragOrCancellation(pointer) ?: break
                            if (change.changedToUpIgnoreConsumed()) {
                                // 手指抬起
                                offset = Offset.Zero
                                break
                            }
                            // 位置发生了改变
                            offset += change.positionChange()
                            offset = Offset(
                                offset.x
                                    .coerceAtLeast(-density.run { (maxWidth / 3).toPx() } * cos(
                                        atan(abs(offset.y / offset.x))
                                    ))
                                    .coerceAtMost(density.run { (maxWidth / 3).toPx() } * cos(
                                        atan(abs(offset.y / offset.x))
                                    )),
                                offset.y
                                    .coerceAtLeast(-density.run { (maxWidth / 3).toPx() } * sin(
                                        atan(abs(offset.y / offset.x))
                                    ))
                                    .coerceAtMost(density.run { (maxWidth / 3).toPx() } * sin(
                                        atan(abs(offset.y / offset.x))
                                    )),
                            )
                            pointer = change.id
                        }
                    }
                },
        )
    }
}

@Preview(widthDp = 600, heightDp = 1300, backgroundColor = 0xFFF6F6F6, showBackground = true)
@Composable
fun PreviewRocker() {
    Rocker(
        modifier = Modifier
            .padding(horizontal = 108.dp)
            .fillMaxWidth()
    )
}