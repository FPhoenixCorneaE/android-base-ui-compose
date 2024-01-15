package com.fphoenixcorneae.widget

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * 验证码
 * @param length          验证码长度
 * @param horizontalSpace 水平间隙
 * @param onComplete      输入完成回调
 * @param content         验证码字符内容
 */
@ExperimentalComposeUiApi
@Composable
fun BaseAuthCodeTextField(
    modifier: Modifier = Modifier,
    code: String = "",
    length: Int = 6,
    horizontalSpace: Dp = 8.dp,
    onComplete: (String) -> Unit = {},
    content: @Composable RowScope.(authCode: String, index: Int) -> Unit,
) {
    // 存储输入的文本
    var text by remember { mutableStateOf(code) }
    // 管理当前获得焦点的文本框
    val focusManager = LocalFocusManager.current
    // 用于请求焦点以显示软键盘
    val focusRequester = remember { FocusRequester() }
    // 软键盘控制器(显示和隐藏)
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        // 获得焦点
        focusRequester.requestFocus()
    }
    BasicTextField(
        value = text,
        singleLine = true,
        onValueChange = { newText ->
            // 限制最大长度且只能输入数字
            if (newText.length <= length && newText.all { it.isDigit() }) {
                text = newText
                if (newText.length == length) {
                    onComplete(newText)
                    focusManager.clearFocus()
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    keyboardController?.show()
                } else {
                    keyboardController?.hide()
                }
            },
        readOnly = false,
        decorationBox = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = horizontalSpace),
            ) {
                repeat(length) {
                    content(text, it)
                }
            }
        }
    )
}

/**
 * 验证码输入框
 * @param length          验证码长度
 * @param fontSize        字体大小
 * @param boxSize         单个验证码输入框大小
 * @param horizontalSpace 水平间隙
 * @param cornerRadius    圆角半径
 * @param enteredBoxColor 输入完成的输入框颜色
 * @param inputtingBoxColor 正在输入的输入框颜色
 * @param pendingBoxColor   尚未输入的输入框颜色
 * @param cursorCharacter   光标字符
 * @param cursorColor       光标颜色
 * @param cursorSize        光标大小
 * @param onComplete      输入完成回调
 */
@ExperimentalComposeUiApi
@Preview
@Composable
fun AuthCodeTextField(
    modifier: Modifier = Modifier,
    code: String = "",
    length: Int = 6,
    contentColor: Color = Color.White,
    fontSize: TextUnit = 24.sp,
    boxSize: Dp = 40.dp,
    horizontalSpace: Dp = 8.dp,
    cornerRadius: Dp = 5.dp,
    enteredBoxColor: Color = Color(0xFF466Eff),
    inputtingBoxColor: Color = Color.White,
    pendingBoxColor: Color = Color(0xFFF5F5F5),
    cursorCharacter: String = "_",
    cursorColor: Color = Color.Blue,
    cursorSize: TextUnit = fontSize,
    onComplete: (String) -> Unit = {},
) {

    BaseAuthCodeTextField(
        modifier = modifier,
        code = code,
        length = length,
        horizontalSpace = horizontalSpace,
        onComplete = onComplete,
    ) { authCode, index ->
        // 判断当前位置是否有字符
        val isHasCode = index < authCode.length
        val codeState = when {
            isHasCode -> CodeState.ENTERED
            (index == authCode.length) -> CodeState.INPUTTING
            else -> CodeState.PENDING
        }
        val boxColor = when (codeState) {
            CodeState.ENTERED -> enteredBoxColor
            CodeState.INPUTTING -> inputtingBoxColor
            CodeState.PENDING -> pendingBoxColor
        }
        val elevation = when (codeState) {
            CodeState.ENTERED -> 3.dp
            CodeState.INPUTTING -> 6.dp
            CodeState.PENDING -> 0.dp
        }
        // 光标闪烁动画, 1秒闪烁一次
        val blinkInterval = 1000L
        var isVisible by remember { mutableStateOf(true) }
        LaunchedEffect(blinkInterval) {
            while (true) {
                delay(blinkInterval)
                isVisible = !isVisible
            }
        }
        key(elevation) {
            Card(
                modifier = Modifier.size(boxSize),
                colors = CardDefaults.cardColors(containerColor = boxColor),
                elevation = CardDefaults.cardElevation(defaultElevation = elevation),
                shape = RoundedCornerShape(size = cornerRadius),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when (codeState) {
                        CodeState.ENTERED -> {
                            Text(
                                text = authCode[index].toString(),
                                fontSize = fontSize,
                                color = contentColor,
                                textAlign = TextAlign.Center,
                            )
                        }

                        CodeState.INPUTTING -> {
                            Crossfade(targetState = isVisible) {
                                if (it) {
                                    Text(
                                        text = cursorCharacter,
                                        fontSize = cursorSize,
                                        color = cursorColor,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }

                        CodeState.PENDING -> {
                        }
                    }
                }
            }
        }
    }
}

/**
 * 验证码状态
 */
private enum class CodeState {
    /** 验证码已经完全输入 */
    ENTERED,

    /** 验证码正在输入中 */
    INPUTTING,

    /** 验证码尚未开始输入 */
    PENDING,
}

