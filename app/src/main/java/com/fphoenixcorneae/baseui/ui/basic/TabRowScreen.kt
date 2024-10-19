package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.baseui.TabRow

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun TabRowScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState(
            initialPage = 1,
            initialPageOffsetFraction = 0f
        ) { 3 }
        val tabs = listOf("发现", "推荐", "关注")
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color.White),
            tabs = tabs,
            pagerState = pagerState,
            containerColor = Color(0xFFFAFAFA),
            contentColor = Color(0xFF888888),
            selectedContentColor = Color(0xFF444444),
            dividerColor = Color(0xFFCCCCCC),
        )
        HorizontalPager(
            modifier = Modifier,
            state = pagerState,
            pageSpacing = 0.dp,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = PaddingValues(0.dp),
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
            key = null,
            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                pagerState,
                Orientation.Horizontal
            ),
            pageContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = tabs[it])
                }
            }
        )
    }
}