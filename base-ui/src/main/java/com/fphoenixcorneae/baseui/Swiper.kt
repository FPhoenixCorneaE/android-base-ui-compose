package com.fphoenixcorneae.baseui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class DragAnchor {
    Start,
    Center,
    End,
}

@OptIn(ExperimentalFoundationApi::class)
data class SwiperState(
    val draggableState: AnchoredDraggableState<DragAnchor>,
    val actionItemWidthPx: Float,
    val actionItemWidth: Dp
)

data class SwiperAction(
    val backgroundColor: Color? = null,
    val label: String? = null,
    val icon: ImageVector? = null,
    val iconTint: Color? = null,
) {
    enum class Style {
        LABEL,
        ICON,
    }
}

/**
 * 滑动操作
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Swiper(
    startOptions: List<SwiperAction>? = null,
    endOptions: List<SwiperAction>? = null,
    style: SwiperAction.Style = SwiperAction.Style.LABEL,
    swiperState: SwiperState = rememberSwiperState(
        actionItemWidth = if (style == SwiperAction.Style.LABEL) 80.dp else 66.dp,
        startActionCount = startOptions?.size ?: 0,
        endActionCount = endOptions?.size ?: 0
    ),
    onStartTap: ((index: Int) -> Unit)? = null,
    onEndTap: ((index: Int) -> Unit)? = null,
    height: Dp = 60.dp,
    contentColor: Color = Color.White,
    content: @Composable (BoxScope.() -> Unit),
) {
    val state = swiperState.draggableState
    val isLabelStyle = style == SwiperAction.Style.LABEL
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clipToBounds()
    ) {
        // 内容
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        x = -state
                            .requireOffset()
                            .roundToInt(),
                        y = 0
                    )
                }
                .background(
                    color = contentColor,
                    shape = if (!isLabelStyle) RoundedCornerShape(8.dp) else RectangleShape
                )
                .padding(horizontal = 16.dp)
                .anchoredDraggable(state, Orientation.Horizontal, reverseDirection = true),
            contentAlignment = Alignment.CenterStart
        ) {
            content()
        }
        // 左侧按钮组
        startOptions?.let {
            Box(modifier = Modifier.align(Alignment.CenterStart)) {
                startOptions.forEachIndexed { index, item ->
                    val fraction = (1f / startOptions.size) * (index + 1)
                    SwiperActionItem(
                        width = swiperState.actionItemWidth,
                        offset = IntOffset(
                            x = (((-state.requireOffset() * fraction) - swiperState.actionItemWidthPx)).roundToInt(),
                            y = 0
                        ),
                        isLabelStyle,
                        item
                    ) {
                        onStartTap?.invoke(index)
                        coroutineScope.launch {
                            state.animateTo(DragAnchor.Center)
                        }
                    }
                }
            }
        }
        // 右侧按钮组
        endOptions?.let {
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                endOptions.forEachIndexed { index, item ->
                    val fraction = 1f - (1f / endOptions.size) * index
                    SwiperActionItem(
                        width = swiperState.actionItemWidth,
                        offset = IntOffset(
                            x = ((-state.requireOffset() * fraction) + swiperState.actionItemWidthPx)
                                .roundToInt(),
                            y = 0
                        ),
                        isLabelStyle,
                        item
                    ) {
                        onEndTap?.invoke(index)
                        coroutineScope.launch {
                            state.animateTo(DragAnchor.Center)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SwiperActionItem(
    width: Dp,
    offset: IntOffset,
    isLabelStyle: Boolean,
    item: SwiperAction,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(width)
            .fillMaxHeight()
            .offset { offset }
            .then(
                if (isLabelStyle && item.backgroundColor != null) {
                    Modifier
                        .background(item.backgroundColor)
                        .clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isLabelStyle || item.icon == null) {
            Text(text = item.label.orEmpty(), color = Color.White)
        } else {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .then(
                        if (item.backgroundColor != null) {
                            Modifier
                                .background(item.backgroundColor)
                        } else {
                            Modifier
                        }
                    )
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = item.iconTint ?: LocalContentColor.current,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberSwiperState(
    initialValue: DragAnchor = DragAnchor.Center,
    actionItemWidth: Dp = 80.dp,
    startActionCount: Int = 0,
    endActionCount: Int = 0
): SwiperState {
    val density = LocalDensity.current
    val actionItemWidthPx = density.run { (actionItemWidth).toPx() }
    val startActionWidthPx = density.run { (actionItemWidth * startActionCount).toPx() }
    val endActionWidthPx = density.run { (actionItemWidth * endActionCount).toPx() }

    val state = remember {
        AnchoredDraggableState(
            // 初始状态
            initialValue,
            // 设置每个锚点对应的位置（偏移量）
            anchors = DraggableAnchors {
                DragAnchor.Start at -startActionWidthPx
                DragAnchor.Center at 0f
                DragAnchor.End at endActionWidthPx
            },
            // 位置阀值：滑动多远距离自动进入该锚点
            positionalThreshold = { totalDistance -> totalDistance * 0.5f },
            // 速度阀值：即使没有超过位置阀值，一秒钟滑动多少个像素也能自动进入下一个锚点
            velocityThreshold = { density.run { 100.dp.toPx() } },
            // 切换状态的动画
            animationSpec = tween()
        )
    }

    return SwiperState(
        state,
        actionItemWidthPx,
        actionItemWidth
    )
}

