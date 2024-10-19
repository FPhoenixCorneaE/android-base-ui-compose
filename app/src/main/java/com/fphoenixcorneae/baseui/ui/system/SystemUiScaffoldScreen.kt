package com.fphoenixcorneae.baseui.ui.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.GradientButton
import com.fphoenixcorneae.baseui.Switch
import com.fphoenixcorneae.baseui.SystemUiScaffold
import com.fphoenixcorneae.baseui.randomColor

@Preview
@Composable
fun SystemUiScaffoldScreen() {
    var isFitsSystemWindows by remember { mutableStateOf(false) }
    var statusBarsPadding by remember { mutableStateOf(false) }
    var navigationBarsPadding by remember { mutableStateOf(false) }
    var isDarkFont by remember { mutableStateOf(false) }
    var statusBarColor by remember { mutableStateOf(Color.Transparent) }
    var navigationBarColor by remember { mutableStateOf(Color.Transparent) }
    SystemUiScaffold(
        modifier = Modifier
            .background(Color(0xfff5f5f5)),
        isFitsSystemWindows = isFitsSystemWindows,
        statusBarsPadding = statusBarsPadding,
        navigationBarsPadding = navigationBarsPadding,
        isDarkFont = isDarkFont,
        statusBarColor = statusBarColor,
        navigationBarColor = navigationBarColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("isFitsSystemWindows", color = Color(0xff222222), fontSize = 14.sp)
                Switch(checked = isFitsSystemWindows) {
                    isFitsSystemWindows = it
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("statusBarsPadding", color = Color(0xff222222), fontSize = 14.sp)
                Switch(checked = statusBarsPadding) {
                    statusBarsPadding = it
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("navigationBarsPadding", color = Color(0xff222222), fontSize = 14.sp)
                Switch(checked = navigationBarsPadding) {
                    navigationBarsPadding = it
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("isDarkFont", color = Color(0xff222222), fontSize = 14.sp)
                Switch(checked = isDarkFont) {
                    isDarkFont = it
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("statusBarColor", color = Color(0xff222222), fontSize = 14.sp)
                GradientButton(
                    modifier = Modifier
                        .width(66.dp)
                        .height(24.dp),
                    text = "Change",
                    textStyle = TextStyle(color = Color.White, fontSize = 11.sp)
                ) {
                    statusBarColor = randomColor
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("navigationBarColor", color = Color(0xff222222), fontSize = 14.sp)
                GradientButton(
                    modifier = Modifier
                        .width(66.dp)
                        .height(24.dp),
                    text = "Change",
                    textStyle = TextStyle(color = Color.White, fontSize = 11.sp)
                ) {
                    navigationBarColor = randomColor
                }
            }
        }
        Column(modifier = Modifier.padding(25.dp)) {
            Text("SystemUiScaffold", color = Color.Black.copy(0.8f), fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("系统ui脚手架", color = Color.Black.copy(0.55f), fontSize = 14.sp)
        }
    }
}