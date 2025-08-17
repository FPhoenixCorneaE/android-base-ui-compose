package com.fphoenixcorneae.baseui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Badge
 *
 * @param number 提示数，传 null 则只显示小红点
 * @param fontSize 文本大小
 * @param maxCharacterCount 最多显示几位数，包含超出后显示的 +
 * @param backgroundColor 背景色
 * @param contentColor 内容色
 * @param contentPadding 内间距
 * @param content 自己决定显示什么
 */
@Composable
fun Badge(
    number: Int?,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    maxCharacterCount: Int = 3,
    backgroundColor: Color = MaterialTheme.colorScheme.error,
    contentColor: Color = contentColorFor(backgroundColor),
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    content: (@Composable (String) -> Unit)? = null
) {
    val isSingleDigitNumber = number != null && number.toString().length == 1
    Badge(
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentPadding = if (isSingleDigitNumber) PaddingValues(horizontal = 6.dp) else contentPadding,
        content = if (number == null) null else {
            {
                val text = remember(number) {
                    var numberText = number.toString()
                    if (numberText.length >= maxCharacterCount) {
                        var temp = ""
                        repeat(maxCharacterCount - 1) {
                            temp += "9"
                        }
                        numberText = "$temp+"
                    }
                    numberText
                }
                if (content != null) {
                    content(text)
                } else {
                    Text(text = text)
                }
            }
        }
    )
}

/**
 * 红点提示，不设置内容则显示一个小红点
 *
 * @param backgroundColor 背景色
 * @param contentPadding 内间距
 * @param content 如果没内容将显示小红点
 */
@Composable
fun Badge(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.error,
    contentPadding: PaddingValues = PaddingValues(all = 3.dp),
    content: @Composable (BoxScope.() -> Unit)? = null
) {
    // Draw badge container.
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        if (content != null) {
            content()
        }
    }
}