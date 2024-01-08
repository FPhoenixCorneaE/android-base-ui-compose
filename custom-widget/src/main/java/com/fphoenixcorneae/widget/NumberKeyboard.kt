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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
    backgroundColor: Color = Color(0xFFF2F3F5),
    contentPadding: PaddingValues = PaddingValues(4.dp),
    horizontalSpace: Dp = 4.dp,
    verticalSpace: Dp = 4.dp,
    aspectRatio: Float = 2.5f,
    cornerRadius: Dp = 4.dp,
    fontSize: TextUnit = 20.sp,
    fontColor: Color = Color.Black,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    normalColor: Color = Color.White,
    pressedColor: Color = Color(0xFFEBEDF0),
    painterHide: Painter = painterResource(id = R.drawable.ic_keyboard_hide),
    painterDelete: Painter = painterResource(id = R.drawable.ic_keyboard_delete),
    onKeyClick: (Pair<String, KeyboardKeyType>) -> Unit = { },
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
                items(
                    items = keys,
                    contentType = {
                        it.second
                    },
                ) { key ->
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
                                        onKeyClick(key)
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
                                    modifier = Modifier.size(keyWidth / 4)
                                )
                            }

                            KeyboardKeyType.Delete -> {
                                Image(
                                    painter = painterDelete,
                                    contentDescription = key.first,
                                    modifier = Modifier.size(keyWidth / 4)
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
 * 数字键盘之身份证号键盘
 * @param visible         显示或隐藏
 * @param horizontalSpace 水平间隙
 * @param verticalSpace   垂直间隙
 * @param aspectRatio     长宽比
 */
@Preview
@Composable
fun IdCardNumberKeyboard(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    backgroundColor: Color = Color(0xFFF2F3F5),
    contentPadding: PaddingValues = PaddingValues(4.dp),
    horizontalSpace: Dp = 4.dp,
    verticalSpace: Dp = 4.dp,
    aspectRatio: Float = 2.5f,
    cornerRadius: Dp = 4.dp,
    fontSize: TextUnit = 20.sp,
    fontColor: Color = Color.Black,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    normalColor: Color = Color.White,
    pressedColor: Color = Color(0xFFEBEDF0),
    completeNormalColor: Color = Color(0xFF576B95),
    completePressedColor: Color = Color(0xFF8593B2),
    completeFontSize: TextUnit = 16.sp,
    painterDelete: Painter = painterResource(id = R.drawable.ic_keyboard_delete),
    onKeyClick: (Pair<String, KeyboardKeyType>) -> Unit = { },
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
        "X" to KeyboardKeyType.Number,
        "0" to KeyboardKeyType.Number,
        "Delete" to KeyboardKeyType.Delete,
        "完成" to KeyboardKeyType.Complete,
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
            Column(modifier = Modifier.background(color = backgroundColor)) {
                var isCompletePressed by remember { mutableStateOf(false) }
                Text(
                    text = keys.last().first,
                    color = if (isCompletePressed) completePressedColor else completeNormalColor,
                    fontSize = completeFontSize,
                    fontWeight = fontWeight,
                    fontFamily = fontFamily,
                    modifier = Modifier
                        .padding(top = topPadding, end = endPadding)
                        .align(Alignment.End)
                        .pointerInput(isCompletePressed) {
                            awaitPointerEventScope {
                                isCompletePressed = if (isCompletePressed) {
                                    waitForUpOrCancellation()
                                    onKeyClick(keys.last())
                                    false
                                } else {
                                    awaitFirstDown(requireUnconsumed = false)
                                    true
                                }
                            }
                        },
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .height(keyboardHeight),
                    contentPadding = contentPadding,
                    horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
                    verticalArrangement = Arrangement.spacedBy(verticalSpace),
                    userScrollEnabled = false,
                ) {
                    items(
                        items = keys.subList(0, keys.size - 1),
                        contentType = {
                            it.second
                        },
                    ) { key ->
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
                                            onKeyClick(key)
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
                                KeyboardKeyType.Delete -> {
                                    Image(
                                        painter = painterDelete,
                                        contentDescription = key.first,
                                        modifier = Modifier.size(keyWidth / 4)
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
}

/**
 * 数字键盘之带标题的键盘
 * @param visible         显示或隐藏
 * @param horizontalSpace 水平间隙
 * @param verticalSpace   垂直间隙
 * @param aspectRatio     长宽比
 */
@Preview
@Composable
fun TitleNumberKeyboard(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    backgroundColor: Color = Color(0xFFF2F3F5),
    contentPadding: PaddingValues = PaddingValues(4.dp),
    horizontalSpace: Dp = 4.dp,
    verticalSpace: Dp = 4.dp,
    aspectRatio: Float = 2.5f,
    cornerRadius: Dp = 4.dp,
    fontSize: TextUnit = 20.sp,
    fontColor: Color = Color.Black,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    normalColor: Color = Color.White,
    pressedColor: Color = Color(0xFFEBEDF0),
    title: String = "键盘标题",
    titleFontSize: TextUnit = 14.sp,
    titleColor: Color = Color(0xFF646566),
    completeNormalColor: Color = Color(0xFF576B95),
    completePressedColor: Color = Color(0xFF8593B2),
    completeFontSize: TextUnit = 16.sp,
    painterDelete: Painter = painterResource(id = R.drawable.ic_keyboard_delete),
    onKeyClick: (Pair<String, KeyboardKeyType>) -> Unit = { },
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
        "." to KeyboardKeyType.DecimalPoint,
        "0" to KeyboardKeyType.Number,
        "Delete" to KeyboardKeyType.Delete,
        "完成" to KeyboardKeyType.Complete,
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
            Column(modifier = Modifier.background(color = backgroundColor)) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = title,
                        color = titleColor,
                        fontSize = titleFontSize,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    var isCompletePressed by remember { mutableStateOf(false) }
                    Text(
                        text = keys.last().first,
                        color = if (isCompletePressed) completePressedColor else completeNormalColor,
                        fontSize = completeFontSize,
                        fontWeight = fontWeight,
                        fontFamily = fontFamily,
                        modifier = Modifier
                            .padding(top = topPadding, end = endPadding)
                            .align(Alignment.TopEnd)
                            .pointerInput(isCompletePressed) {
                                awaitPointerEventScope {
                                    isCompletePressed = if (isCompletePressed) {
                                        waitForUpOrCancellation()
                                        onKeyClick(keys.last())
                                        false
                                    } else {
                                        awaitFirstDown(requireUnconsumed = false)
                                        true
                                    }
                                }
                            },
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .height(keyboardHeight),
                    contentPadding = contentPadding,
                    horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
                    verticalArrangement = Arrangement.spacedBy(verticalSpace),
                    userScrollEnabled = false,
                ) {
                    items(
                        items = keys.subList(0, keys.size - 1),
                        contentType = {
                            it.second
                        },
                    ) { key ->
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
                                            onKeyClick(key)
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
                                KeyboardKeyType.Delete -> {
                                    Image(
                                        painter = painterDelete,
                                        contentDescription = key.first,
                                        modifier = Modifier.size(keyWidth / 4)
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
}

/**
 * 数字键盘之带右侧栏的键盘
 * @param visible         显示或隐藏
 * @param horizontalSpace 水平间隙
 * @param verticalSpace   垂直间隙
 * @param aspectRatio     长宽比
 */
@Preview
@Composable
fun SidebarNumberKeyboard(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    backgroundColor: Color = Color(0xFFF2F3F5),
    contentPadding: PaddingValues = PaddingValues(4.dp),
    horizontalSpace: Dp = 4.dp,
    verticalSpace: Dp = 4.dp,
    aspectRatio: Float = 1.8f,
    cornerRadius: Dp = 4.dp,
    fontSize: TextUnit = 20.sp,
    fontColor: Color = Color.Black,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    normalColor: Color = Color.White,
    pressedColor: Color = Color(0xFFEBEDF0),
    completeNormalColor: Color = Color(0xFF1989FA),
    completePressedColor: Color = Color(0xFF5AA8F8),
    completeFontSize: TextUnit = 16.sp,
    painterDelete: Painter = painterResource(id = R.drawable.ic_keyboard_delete),
    onKeyClick: (Pair<String, KeyboardKeyType>) -> Unit = { },
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
        "0" to KeyboardKeyType.Number,
        "." to KeyboardKeyType.DecimalPoint,
        "Delete" to KeyboardKeyType.Delete,
        "完成" to KeyboardKeyType.Complete,
    )
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically(animationSpec = tween(), initialOffsetY = { it }),
        exit = slideOutVertically(animationSpec = tween(), targetOffsetY = { it }),
    ) {
        BoxWithConstraints(modifier = Modifier.background(color = backgroundColor)) {
            val startPadding = contentPadding.calculateStartPadding(LayoutDirection.Ltr)
            val topPadding = contentPadding.calculateTopPadding()
            val endPadding = contentPadding.calculateEndPadding(LayoutDirection.Ltr)
            val bottomPadding = contentPadding.calculateBottomPadding()
            val keyWidth = (maxWidth - startPadding - endPadding - horizontalSpace * 3) / 4
            val keyboardHeight = topPadding + bottomPadding + keyWidth / aspectRatio * 4 + verticalSpace * 3
            Row(
                modifier = Modifier
                    .padding(end = endPadding)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .width(startPadding + keyWidth * 3 + horizontalSpace * 2)
                        .height(keyboardHeight),
                    contentPadding = PaddingValues(
                        start = startPadding,
                        top = topPadding,
                        end = 0.dp,
                        bottom = bottomPadding,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
                    verticalArrangement = Arrangement.spacedBy(verticalSpace),
                    userScrollEnabled = false,
                ) {
                    items(
                        items = keys.subList(0, keys.size - 2),
                        span = {
                            if ("0" == it.first) {
                                GridItemSpan(2)
                            } else {
                                GridItemSpan(1)
                            }
                        },
                        contentType = {
                            it.second
                        },
                    ) { key ->
                        var isPressed by remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(cornerRadius))
                                .background(color = if (isPressed) pressedColor else normalColor)
                                .width(width = keyWidth)
                                .height(height = keyWidth / aspectRatio)
                                .pointerInput(isPressed) {
                                    awaitPointerEventScope {
                                        isPressed = if (isPressed) {
                                            waitForUpOrCancellation()
                                            onKeyClick(key)
                                            false
                                        } else {
                                            awaitFirstDown(requireUnconsumed = false)
                                            true
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center,
                        ) {
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
                Column(
                    modifier = Modifier
                        .width(keyWidth)
                        .height(keyboardHeight)
                        .padding(top = topPadding, bottom = bottomPadding),
                    verticalArrangement = Arrangement.spacedBy(verticalSpace),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    keys.subList(keys.size - 2, keys.size).forEach { key ->
                        var isPressed by remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(cornerRadius))
                                .background(color = if (key.second == KeyboardKeyType.Delete) if (isPressed) pressedColor else normalColor else if (isPressed) completePressedColor else completeNormalColor)
                                .fillMaxWidth()
                                .weight(1f)
                                .pointerInput(isPressed) {
                                    awaitPointerEventScope {
                                        isPressed = if (isPressed) {
                                            waitForUpOrCancellation()
                                            onKeyClick(key)
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
                                KeyboardKeyType.Delete -> {
                                    Image(
                                        painter = painterDelete,
                                        contentDescription = key.first,
                                        modifier = Modifier.size(keyWidth / 2.5f)
                                    )
                                }

                                else -> {
                                    Text(
                                        text = key.first,
                                        color = Color.White,
                                        fontSize = completeFontSize,
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
}

/**
 * 键盘按键类型
 */
enum class KeyboardKeyType {
    /** 数字 */
    Number,

    /** 隐藏 */
    Hide,

    /** 删除 */
    Delete,

    /** 小数点 */
    DecimalPoint,

    /** 完成 */
    Complete,
}