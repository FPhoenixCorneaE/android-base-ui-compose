package com.fphoenixcorneae.baseui.ui.basic

import android.util.Log
import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CheckboxDefaults
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
import com.fphoenixcorneae.baseui.CheckBox

@Preview
@Composable
fun CheckBoxScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5))
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        var rememberPassword by remember { mutableStateOf(true) }
        CheckBox(
            modifier = Modifier.padding(horizontal = 16.dp),
            checked = rememberPassword,
            enabled = true,
            boxShape = CircleShape,
            text = "记住密码",
        ) {
            rememberPassword = it
        }
        Spacer(modifier = Modifier.height(12.dp))
        var agreeTermsOfService by remember { mutableStateOf(false) }
        Log.d("agreeTermsOfService", "CheckBoxScreen: agreeTermsOfService: $agreeTermsOfService")
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = agreeTermsOfService,
            enabled = true,
            boxSize = 14.dp,
            boxShape = RoundedCornerShape(4.dp),
            boxPadding = PaddingValues(vertical = 4.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red,
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White,
            ),
            text = "我已阅读并同意《用户服务协议》、《平台基本功能隐私政策》，并授权平台该账号信息（如昵称、头像）",
            textStyle = TextStyle(color = Color.Gray, fontSize = 14.sp),
            verticalAlignment = Alignment.Top,
        ) {
            agreeTermsOfService = it
        }
        Spacer(modifier = Modifier.height(12.dp))
        val disabledSelected by remember { mutableStateOf(true) }
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = disabledSelected,
            enabled = false,
            text = "不可选中"
        ) {}
        Spacer(modifier = Modifier.height(12.dp))
        var optionA by remember { mutableStateOf(true) }
        CheckBox(
            modifier = Modifier.padding(horizontal = 16.dp),
            checked = optionA,
            enabled = true,
            boxShape = CircleShape,
            boxGravity = Gravity.END,
            text = "A",
        ) {
            optionA = it
        }
        Spacer(modifier = Modifier.height(12.dp))
        var optionB by remember { mutableStateOf(true) }
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = optionB,
            enabled = true,
            boxShape = CircleShape,
            boxGravity = Gravity.END,
            text = "B",
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            optionB = it
        }
        Spacer(modifier = Modifier.height(12.dp))
        var optionC by remember { mutableStateOf(true) }
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = optionC,
            enabled = true,
            boxShape = CircleShape,
            boxGravity = Gravity.END,
            text = "C",
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            optionC = it
        }
        Spacer(modifier = Modifier.height(12.dp))
        var optionD by remember { mutableStateOf(true) }
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = optionD,
            enabled = true,
            boxShape = CircleShape,
            boxGravity = Gravity.END,
            text = "D",
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            optionD = it
        }
    }

    Column(modifier = Modifier.padding(25.dp)) {
        Text("CheckBox", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("复选框", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}