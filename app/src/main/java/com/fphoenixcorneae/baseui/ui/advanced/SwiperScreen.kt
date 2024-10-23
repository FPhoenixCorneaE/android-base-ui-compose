package com.fphoenixcorneae.baseui.ui.advanced

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.DragAnchor
import com.fphoenixcorneae.baseui.Swiper
import com.fphoenixcorneae.baseui.SwiperAction
import com.fphoenixcorneae.baseui.ext.clickableNoRipple
import com.fphoenixcorneae.baseui.rememberSwiperState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun SwiperScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val context = LocalContext.current
        val labelActions = listOf(
            SwiperAction(
                backgroundColor = Color.LightGray,
                label = "点赞",
            ),
            SwiperAction(
                backgroundColor = Color.DarkGray,
                label = "收藏",
            ),
            SwiperAction(
                backgroundColor = Color.Red,
                label = "举报",
            )
        )
        val iconActions = listOf(
            SwiperAction(
                backgroundColor = Color.White,
                label = "点赞",
                icon = Icons.Outlined.FavoriteBorder,
            ),
            SwiperAction(
                backgroundColor = Color.White,
                label = "收藏",
                icon = Icons.Outlined.StarOutline,
            ),
            SwiperAction(
                backgroundColor = Color.White,
                label = "举报",
                icon = Icons.Outlined.Report
            )
        )
        Swiper(
            startOptions = labelActions,
            endOptions = labelActions,
            style = SwiperAction.Style.LABEL,
            onStartTap = {
                Toast.makeText(
                    context,
                    "点击了左边的${labelActions[it].label}标签",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onEndTap = {
                Toast.makeText(
                    context,
                    "点击了右边的${labelActions[it].label}标签",
                    Toast.LENGTH_SHORT
                ).show()
            },
        ) {
            Text("文字标签（左右滑动）", color = Color.Black, fontSize = 16.sp)
        }
        Spacer(Modifier.height(16.dp))
        Swiper(
            startOptions = iconActions,
            endOptions = iconActions,
            style = SwiperAction.Style.ICON,
            onStartTap = {
                Toast.makeText(
                    context,
                    "点击了左边的${labelActions[it].label}Icon",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onEndTap = {
                Toast.makeText(
                    context,
                    "点击了右边的${labelActions[it].label}Icon",
                    Toast.LENGTH_SHORT
                ).show()
            },
            height = 70.dp,
            contentColor = Color(0xffe7e7e7),
        ) {
            Text("Icon按钮（左右滑动）", color = Color.Black, fontSize = 16.sp)
        }
        Spacer(Modifier.height(16.dp))
        val swiperState =
            rememberSwiperState(actionItemWidth = 66.dp, endActionCount = iconActions.size)
        val coroutineScope = rememberCoroutineScope()
        Swiper(
            endOptions = iconActions,
            style = SwiperAction.Style.ICON,
            swiperState = swiperState,
            onEndTap = {
                Toast.makeText(
                    context,
                    "点击了右边的${labelActions[it].label}Icon",
                    Toast.LENGTH_SHORT
                ).show()
            },
            height = 70.dp,
            contentColor = Color(0xffe7e7e7),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickableNoRipple {
                        coroutineScope.launch {
                            Log.d(
                                "SwiperScreen",
                                "SwiperScreen: ${swiperState.draggableState.currentValue}"
                            )
                            if (swiperState.draggableState.currentValue == DragAnchor.End) {
                                swiperState.draggableState.animateTo(DragAnchor.Center)
                            } else {
                                swiperState.draggableState.animateTo(DragAnchor.End)
                            }
                        }
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Icon按钮（仅右滑动，点击可切换状态）",
                    color = Color.Black,
                    fontSize = 16.sp,
                )
            }
        }
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("Swiper", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("滑动操作", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}