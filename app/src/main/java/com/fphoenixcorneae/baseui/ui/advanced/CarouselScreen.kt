package com.fphoenixcorneae.baseui.ui.advanced

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.carousel.Carousel
import com.fphoenixcorneae.baseui.carousel.CarouselIndicator

@Preview
@Composable
fun CarouselScreen() {
    val items = listOf(
        "https://picsum.photos/360/180",
        "https://picsum.photos/id/413/360/180",
        "https://picsum.photos/id/520/360/180",
        "https://picsum.photos/id/984/360/180",
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Carousel(
            items = items,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(120.dp),
            contentPadding = PaddingValues(),
            graphicsLayer = null,
            autoScrollEnabled = false,
        )
        Spacer(Modifier.height(16.dp))
        Carousel(
            items = items,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(Modifier.height(16.dp))
        Carousel(
            items = items,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            indicator = { pagerState, realCount ->
                CarouselIndicator(
                    pagerState = pagerState,
                    activeColor = Color.Red,
                    inactiveColor = Color.White,
                    count = realCount,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.BottomCenter),
                    spacing = 6.dp,
                    indicatorWidth = 12.dp,
                    indicatorHeight = 3.dp,
                    indicatorShape = RoundedCornerShape(2.dp),
                )
            }
        )
    }

    Column(modifier = Modifier.padding(25.dp)) {
        Text("Carousel", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("旋转木马", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}