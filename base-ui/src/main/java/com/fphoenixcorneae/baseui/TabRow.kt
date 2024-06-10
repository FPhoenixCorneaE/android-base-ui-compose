package com.fphoenixcorneae.baseui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.ext.clickableNoRipple
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * @desc：选项卡
 * @param modifier             布局修饰符
 * @param tabs                 选项内容
 * @param containerColor       容器背景色
 * @param contentColor         选项内容颜色
 * @param selectedContentColor 选中选项内容颜色
 * @param fontSize             内容文字大小
 * @param fontFamily           内容文字字体
 * @param isFixTabWidth        选项宽度是否固定，默认为true
 * @param tabWidth             选项宽度
 * @param indicatorWidth       指示器宽度
 * @param indicatorHeight      指示器高度
 * @param indicatorRadius      指示器圆角
 * @param indicatorColor       指示器颜色，默认是选中标签颜色
 * @param bottomSpace          底部内边距
 * @param dividerColor         分割线颜色
 * @param dividerThickness     分割线厚度
 * @param onTabClick           选项点击事件
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabRow(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { tabs.size },
    containerColor: Color = Color.White,
    contentColor: Color = Color.Gray,
    selectedContentColor: Color = Color.Black,
    fontSize: TextUnit = 12.sp,
    fontFamily: FontFamily? = null,
    isFixTabWidth: Boolean = true,
    tabWidth: Dp = 66.dp,
    indicatorWidth: Dp = 12.dp,
    indicatorHeight: Dp = 3.dp,
    indicatorRadius: Dp = 2.5.dp,
    indicatorColor: Color = selectedContentColor,
    bottomSpace: Dp = 4.dp,
    dividerColor: Color = Color.LightGray,
    dividerThickness: Dp = 0.2.dp,
    onTabClick: (Int) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    // 当前页面索引
    val currentPage by rememberUpdatedState(newValue = pagerState.currentPage)
    var tabWidth = tabWidth
    var tabOffset = 0.dp
    BoxWithConstraints(
        modifier = modifier.background(color = containerColor),
    ) {
        if (!isFixTabWidth && tabs.isNotEmpty()) {
            tabWidth = maxWidth / tabs.size
        } else {
            tabOffset = (maxWidth - tabWidth * tabs.size) / 2
        }
        Column {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(height = 0.dp)
                    .weight(weight = 1f)
                    .align(alignment = Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                tabs.forEachIndexed { index, s ->
                    Box(
                        modifier = Modifier
                            .width(width = tabWidth)
                            .fillMaxHeight()
                            .clickableNoRipple {
                                if (currentPage != index) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                    onTabClick(index)
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = s,
                            color = if (currentPage == index) selectedContentColor else contentColor,
                            fontSize = fontSize,
                            fontFamily = fontFamily,
                            fontWeight = if (currentPage == index) FontWeight.Bold else null,
                        )
                    }
                }
            }
            // 当前页面偏移比例
            val offsetFraction by rememberUpdatedState(newValue = pagerState.currentPageOffsetFraction)
            // 当前页面偏移值
            val currentIndicatorOffset = tabOffset + tabWidth * currentPage + (tabWidth - indicatorWidth) / 2f
            val indicatorOffset = if (offsetFraction > 0 && currentPage + 1 < tabs.size) {
                // 如果 fraction > 0 且存在下一标签,通过 lerp 计算指示器偏移值
                val nextIndicatorOffset = tabOffset + tabWidth * (currentPage + 1) + (tabWidth - indicatorWidth) / 2f
                lerp(start = currentIndicatorOffset, stop = nextIndicatorOffset, fraction = offsetFraction)
            } else if (offsetFraction < 0 && currentPage - 1 >= 0) {
                // 如果 fraction < 0 且存在前一标签,通过 lerp 计算指示器偏移值
                val previousIndicatorOffset =
                    tabOffset + tabWidth * (currentPage - 1) + (tabWidth - indicatorWidth) / 2f
                lerp(start = currentIndicatorOffset, stop = previousIndicatorOffset, fraction = -offsetFraction)
            } else {
                // 否则指示器偏移位置为当前标签的偏移值
                currentIndicatorOffset
            }
            // 绘制指示器
            Canvas(
                modifier = Modifier.height(height = indicatorHeight),
            ) {
                drawRoundRect(
                    color = indicatorColor,
                    topLeft = Offset(x = density.run { indicatorOffset.toPx() }, y = 0f),
                    size = Size(
                        width = density.run { (indicatorWidth + tabWidth * abs(offsetFraction)).toPx() },
                        height = density.run { indicatorHeight.toPx() }),
                    cornerRadius = CornerRadius(
                        x = density.run { indicatorRadius.toPx() },
                        y = density.run { indicatorRadius.toPx() }),
                )
            }
            // 指示器与分割线的间距
            Spacer(modifier = Modifier.height(height = bottomSpace))
            // 分割线
            Divider(color = dividerColor, thickness = dividerThickness)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewTabRow() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(Color.White),
            tabs = listOf(
                "推荐",
                "关注",
            ),
            containerColor = Color(0xFFFAFAFA),
            contentColor = Color(0xFF888888),
            selectedContentColor = Color(0xFF444444),
            dividerColor = Color(0xFFCCCCCC),
        )
    }
}