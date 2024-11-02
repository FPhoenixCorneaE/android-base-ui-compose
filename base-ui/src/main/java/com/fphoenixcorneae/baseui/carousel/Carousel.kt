package com.fphoenixcorneae.baseui.carousel

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.SnapperFlingBehavior
import dev.chrisbanes.snapper.SnapperFlingBehaviorDefaults
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * 旋转木马
 * @param items 轮播图数据
 * @param modifier 组件修饰符
 * @param roundedCorner 圆角
 * @param placeholder 轮播图占位符
 * @param contentPadding 页面周围间距
 * @param pageSpacing 相邻页面之间的间隙
 * @param autoScrollEnabled 是否启用自动滚动，默认true
 * @param autoScrollInterval 自动轮播间隔
 * @param autoScrollDuration 自动轮播翻页时长
 * @param graphicsLayer 页面动画，页面视觉过渡效果
 */
@OptIn(ExperimentalSnapperApi::class, ExperimentalSnapperApi::class)
@Composable
fun Carousel(
    items: List<Any?>?,
    modifier: Modifier = Modifier,
    roundedCorner: Dp = 8.dp,
    placeholder: Drawable? = ColorDrawable(Color.LightGray.toArgb()),
    contentPadding: PaddingValues = PaddingValues(horizontal = 32.dp),
    pageSpacing: Dp = 16.dp,
    autoScrollEnabled: Boolean = true,
    autoScrollInterval: Long = 3000,
    autoScrollDuration: Int = 1000,
    graphicsLayer: (GraphicsLayerScope.(Float) -> Unit)? = { pageOffset ->
        val transformation = lerp(
            start = 0.8f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
        alpha = transformation
        scaleY = transformation
    },
    indicator: @Composable BoxScope.(PagerState, Int) -> Unit = { pagerState, realCount ->
        CarouselIndicator(
            pagerState = pagerState,
            activeColor = Color.Red,
            inactiveColor = Color.White,
            count = realCount,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter)
        )
    }
) {
    val realPageCount = items?.size ?: 0
    val loopingCount = Int.MAX_VALUE
    val pageCount = if (realPageCount > 1) loopingCount else realPageCount
    val initialPage = pageCount / 2
    val pagerState = rememberPagerState(initialPage = initialPage)
    // 手势按压或者拖动时，关闭自动轮播
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    if (autoScrollEnabled && realPageCount > 1) {
        var currentTimeMillis by remember { mutableLongStateOf(0L) }
        LaunchedEffect(key1 = isDragged, key2 = currentTimeMillis) {
            if (!isDragged) {
                launch {
                    delay(timeMillis = autoScrollInterval)
                    if (pagerState.currentPage == loopingCount - 1) {
                        pagerState.animateScrollToPage(0)
                    } else {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage + 1,
                            animationSpec = tween(durationMillis = autoScrollDuration),
                        )
                    }
                    currentTimeMillis = System.currentTimeMillis()
                }
            }
        }
    }

    val flingBehavior = rememberSnapperFlingBehavior(
        lazyListState = pagerState.lazyListState,
        snapOffsetForItem = SnapOffsets.Start, // pages are full width, so we use the simplest
        decayAnimationSpec = rememberSplineBasedDecay(),
        springAnimationSpec = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
        endContentPadding = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
        snapIndex = { layoutInfo, startIndex, targetIndex ->
            targetIndex
                .coerceIn(startIndex - 1, startIndex + 1)
                .coerceIn(0, layoutInfo.totalItemsCount - 1)
        },
    )
    pagerState.flingAnimationTarget = {
        (flingBehavior as? SnapperFlingBehavior)?.animationTarget
    }

    LaunchedEffect(loopingCount) {
        pagerState.currentPage = minOf(loopingCount - 1, pagerState.currentPage).coerceAtLeast(0)
    }

    // Once a fling (scroll) has finished, notify the state
    LaunchedEffect(pagerState) {
        // When a 'scroll' has finished, notify the state
        snapshotFlow { pagerState.isScrollInProgress }
            .filter { !it }
            // initially isScrollInProgress is false as well and we want to start receiving
            // the events only after the real scroll happens.
            .drop(1)
            .collect { pagerState.onScrollFinished() }
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.mostVisiblePageLayoutInfo?.index }
            .distinctUntilChanged()
            .collect { pagerState.updateCurrentPageBasedOnLazyListState() }
    }
    val density = LocalDensity.current
    LaunchedEffect(density, pagerState, pageSpacing) {
        with(density) { pagerState.itemSpacing = pageSpacing.roundToPx() }
    }

    // We only consume nested flings in the main-axis, allowing cross-axis flings to propagate
    // as normal
    val consumeFlingNestedScrollConnection = remember {
        ConsumeFlingNestedScrollConnection(
            consumeHorizontal = true,
            consumeVertical = false,
            pagerState = pagerState,
        )
    }

    Box {
        LazyRow(
            state = pagerState.lazyListState,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(pageSpacing, Alignment.CenterHorizontally),
            flingBehavior = flingBehavior,
            reverseLayout = false,
            contentPadding = contentPadding,
            userScrollEnabled = true,
            modifier = modifier.clip(RoundedCornerShape(size = roundedCorner)),
        ) {
            items(
                count = pageCount,
                key = null,
            ) { page ->
                Box(
                    Modifier
                        // We don't any nested flings to continue in the pager, so we add a
                        // connection which consumes them.
                        // See: https://github.com/google/accompanist/issues/347
                        .nestedScroll(connection = consumeFlingNestedScrollConnection)
                        // Constraint the content width to be <= than the width of the pager.
                        .fillParentMaxWidth()
                        .wrapContentSize()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                val pageOffset =
                                    (pagerState.currentPage - page + pagerState.currentPageOffset).absoluteValue
                                graphicsLayer?.invoke(this, pageOffset)
                            },
                        shape = RoundedCornerShape(size = roundedCorner),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent,
                        )
                    ) {
                        val actualIndex = (page - initialPage).floorMod(realPageCount)
                        CarouselItem(
                            data = items?.getOrNull(actualIndex),
                            placeholder = placeholder
                        )
                    }
                }
            }
        }
        indicator(pagerState, realPageCount)
    }
}

@Composable
fun CarouselItem(data: Any?, placeholder: Drawable?) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context).data(data).placeholder(placeholder).build(),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
}

internal fun Int.floorMod(other: Int) = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}
