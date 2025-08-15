package com.fphoenixcorneae.baseui.ui.basic

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.AutoSizeText
import com.fphoenixcorneae.baseui.CustomEditText

@Preview
@Composable
fun AutoSizeTextScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        var text by remember { mutableStateOf("") }
        CustomEditText(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(8.dp)),
            text = text,
            onValueChange = {
                text = it
                Log.d("BaseUi", "EditText: $it")
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
            backgroundColor = Color(0xFFffffff),
            paddingStart = 8.dp,
            paddingEnd = 8.dp,
            hint = "请输入",
            hintColor = Color(0xff444444),
        )

        AutoSizeText(
            text = text,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White),
            maxLines = 3,
        )
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("AutoSizeText", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("自动缩放字体大小", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}