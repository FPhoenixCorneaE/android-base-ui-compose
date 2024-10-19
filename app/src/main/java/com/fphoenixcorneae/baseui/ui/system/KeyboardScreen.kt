package com.fphoenixcorneae.baseui.ui.system

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.baseui.BasicNumberKeyboard
import com.fphoenixcorneae.baseui.IdCardNumberKeyboard
import com.fphoenixcorneae.baseui.KeyboardKeyType
import com.fphoenixcorneae.baseui.NumberKeyboardWithKeys
import com.fphoenixcorneae.baseui.RandomNumberKeyboard
import com.fphoenixcorneae.baseui.SidebarNumberKeyboard
import com.fphoenixcorneae.baseui.TitleNumberKeyboard
import com.fphoenixcorneae.baseui.ext.clickableNoRipple

@Preview
@Composable
fun KeyboardScreen() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var visibleBasicNumberKeyboard by remember { mutableStateOf(false) }
            var visibleSidebarNumberKeyboard by remember { mutableStateOf(false) }
            var visibleIdCardNumberKeyboard by remember { mutableStateOf(false) }
            var visibleTitleNumberKeyboard by remember { mutableStateOf(false) }
            var visibleNumberKeyboardWithKeys by remember { mutableStateOf(false) }
            var visibleRandomNumberKeyboard by remember { mutableStateOf(false) }
            Text(
                text = "弹出默认键盘",
                modifier = Modifier.clickableNoRipple {
                    visibleBasicNumberKeyboard = true
                    visibleSidebarNumberKeyboard = false
                    visibleIdCardNumberKeyboard = false
                    visibleTitleNumberKeyboard = false
                    visibleNumberKeyboardWithKeys = false
                    visibleRandomNumberKeyboard = false
                },
            )
            Text(
                text = "弹出带右侧栏的键盘",
                modifier = Modifier.clickableNoRipple {
                    visibleBasicNumberKeyboard = false
                    visibleSidebarNumberKeyboard = true
                    visibleIdCardNumberKeyboard = false
                    visibleTitleNumberKeyboard = false
                    visibleNumberKeyboardWithKeys = false
                    visibleRandomNumberKeyboard = false
                },
            )
            Text(
                text = "弹出身份证号键盘",
                modifier = Modifier.clickableNoRipple {
                    visibleBasicNumberKeyboard = false
                    visibleSidebarNumberKeyboard = false
                    visibleIdCardNumberKeyboard = true
                    visibleTitleNumberKeyboard = false
                    visibleNumberKeyboardWithKeys = false
                    visibleRandomNumberKeyboard = false
                },
            )
            Text(
                text = "弹出带标题的键盘",
                modifier = Modifier.clickableNoRipple {
                    visibleBasicNumberKeyboard = false
                    visibleSidebarNumberKeyboard = false
                    visibleIdCardNumberKeyboard = false
                    visibleTitleNumberKeyboard = true
                    visibleNumberKeyboardWithKeys = false
                    visibleRandomNumberKeyboard = false
                },
            )
            Text(
                text = "弹出配置多个按键的键盘",
                modifier = Modifier.clickableNoRipple {
                    visibleBasicNumberKeyboard = false
                    visibleSidebarNumberKeyboard = false
                    visibleIdCardNumberKeyboard = false
                    visibleTitleNumberKeyboard = false
                    visibleNumberKeyboardWithKeys = true
                    visibleRandomNumberKeyboard = false
                },
            )
            Text(
                text = "弹出随机数字键盘",
                modifier = Modifier.clickableNoRipple {
                    visibleBasicNumberKeyboard = false
                    visibleSidebarNumberKeyboard = false
                    visibleIdCardNumberKeyboard = false
                    visibleTitleNumberKeyboard = false
                    visibleNumberKeyboardWithKeys = false
                    visibleRandomNumberKeyboard = true
                },
            )
            Box {
                BasicNumberKeyboard(
                    modifier = Modifier.fillMaxWidth(),
                    visible = visibleBasicNumberKeyboard
                ) { key ->
                    Toast.makeText(context, "$key", Toast.LENGTH_SHORT).show()
                    if (key.second == KeyboardKeyType.Hide) {
                        visibleBasicNumberKeyboard = false
                    }
                }
                SidebarNumberKeyboard(
                    modifier = Modifier.fillMaxWidth(),
                    visible = visibleSidebarNumberKeyboard
                ) { key ->
                    Toast.makeText(context, "$key", Toast.LENGTH_SHORT).show()
                    if (key.second == KeyboardKeyType.Complete) {
                        visibleSidebarNumberKeyboard = false
                    }
                }
                IdCardNumberKeyboard(
                    modifier = Modifier.fillMaxWidth(),
                    visible = visibleIdCardNumberKeyboard
                ) { key ->
                    Toast.makeText(context, "$key", Toast.LENGTH_SHORT).show()
                    if (key.second == KeyboardKeyType.Complete) {
                        visibleIdCardNumberKeyboard = false
                    }
                }
                TitleNumberKeyboard(
                    modifier = Modifier.fillMaxWidth(),
                    visible = visibleTitleNumberKeyboard
                ) { key ->
                    Toast.makeText(context, "$key", Toast.LENGTH_SHORT).show()
                    if (key.second == KeyboardKeyType.Complete) {
                        visibleTitleNumberKeyboard = false
                    }
                }
                NumberKeyboardWithKeys(
                    modifier = Modifier.fillMaxWidth(),
                    visible = visibleNumberKeyboardWithKeys
                ) { key ->
                    Toast.makeText(context, "$key", Toast.LENGTH_SHORT).show()
                    if (key.second == KeyboardKeyType.Complete) {
                        visibleNumberKeyboardWithKeys = false
                    }
                }
                RandomNumberKeyboard(
                    modifier = Modifier.fillMaxWidth(),
                    visible = visibleRandomNumberKeyboard
                ) { key ->
                    Toast.makeText(context, "$key", Toast.LENGTH_SHORT).show()
                    if (key.second == KeyboardKeyType.Hide) {
                        visibleRandomNumberKeyboard = false
                    }
                }
            }
        }
    }
}