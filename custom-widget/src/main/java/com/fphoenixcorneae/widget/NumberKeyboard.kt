package com.fphoenixcorneae.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 数字键盘之默认键盘
 * @param visible         显示或隐藏
 * @param horizontalSpace 水平间隙
 * @param verticalSpace   垂直间隙
 * @param aspectRatio     长宽比
 */
@Preview
@Composable
fun BasicNumberKeyboard(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    backgroundColor: Color = Color.Gray.copy(alpha = 0.2f),
    contentPadding: PaddingValues = PaddingValues(4.dp),
    horizontalSpace: Dp = 4.dp,
    verticalSpace: Dp = 4.dp,
    aspectRatio: Float = 2.5f,
    cornerRadius: Dp = 4.dp,
    fontSize: TextUnit = 16.sp,
    fontColor: Color = Color.Black,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    normalColor: Color = Color.White,
    pressedColor: Color = Color.Gray.copy(alpha = 0.4f),
    painterHide: Painter = painterResource(id = R.drawable.ic_keyboard_hide),
    painterDelete: Painter = painterResource(id = R.drawable.ic_keyboard_delete),
    onKeyClick: (@ParameterName("key") String, @ParameterName("type") KeyboardKeyType) -> Unit = { _, _ -> },
) {
    val keys = mutableListOf(
        "1" to KeyboardKeyType.Number,
        "2" to KeyboardKeyType.Number,
        "3" to KeyboardKeyType.Number,
        "4" to KeyboardKeyType.Number,
        "5" to KeyboardKeyType.Number,
        "6" to KeyboardKeyType.Number,
        "7" to KeyboardKeyType.Number,
        "8" to KeyboardKeyType.Number,
        "9" to KeyboardKeyType.Number,
        "Hide" to KeyboardKeyType.Hide,
        "0" to KeyboardKeyType.Number,
        "Delete" to KeyboardKeyType.Delete,
    )
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically(animationSpec = tween(), initialOffsetY = { it }),
        exit = slideOutVertically(animationSpec = tween(), targetOffsetY = { it }),
    ) {
        BoxWithConstraints {
            val startPadding = contentPadding.calculateStartPadding(LayoutDirection.Ltr)
            val topPadding = contentPadding.calculateTopPadding()
            val endPadding = contentPadding.calculateEndPadding(LayoutDirection.Ltr)
            val bottomPadding = contentPadding.calculateBottomPadding()
            val keyWidth = (maxWidth - startPadding - endPadding - horizontalSpace * 2) / 3
            val keyboardHeight = topPadding + bottomPadding + keyWidth / aspectRatio * 4 + verticalSpace * 3
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .height(keyboardHeight)
                    .background(color = backgroundColor),
                contentPadding = contentPadding,
                horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
                verticalArrangement = Arrangement.spacedBy(verticalSpace),
                userScrollEnabled = false,
            ) {
                items(keys) { key ->
                    var isPressed by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(cornerRadius))
                            .background(color = if (isPressed) pressedColor else normalColor)
                            .aspectRatio(ratio = aspectRatio)
                            .pointerInput(isPressed) {
                                awaitPointerEventScope {
                                    isPressed = if (isPressed) {
                                        waitForUpOrCancellation()
                                        onKeyClick(key.first, key.second)
                                        false
                                    } else {
                                        awaitFirstDown(requireUnconsumed = false)
                                        true
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        when (key.second) {
                            KeyboardKeyType.Hide -> {
                                Image(
                                    painter = painterHide,
                                    contentDescription = key.first,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            KeyboardKeyType.Delete -> {
                                Image(
                                    painter = painterDelete,
                                    contentDescription = key.first,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            else -> {
                                Text(
                                    text = key.first,
                                    color = fontColor,
                                    fontSize = fontSize,
                                    fontWeight = fontWeight,
                                    fontFamily = fontFamily,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 键盘按键类型
 */
enum class KeyboardKeyType {
    Number,
    Hide,
    Delete,
}