package com.fphoenixcorneae.baseui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 渐变背景按钮
 * @param gradientBrush 渐变画笔
 */
@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = TextStyle.Default,
    gradientBrush: Brush = Brush.linearGradient(
        listOf(
            Color(0xFF4185F2),
            Color(0xFF3362F7)
        )
    ),
    shape: Shape = ButtonDefaults.shape,
    enabled: Boolean = true,
    onClick: () -> Unit = { },
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(brush = gradientBrush)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = if (enabled) rememberRipple() else null,
                enabled = enabled
            ) {
                onClick()
            }
            .alpha(if (enabled) 1f else 0.7f),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = textStyle,
        )
    }
}

@Preview
@Composable
fun PreviewGradientButton() {
    Column {
        GradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            text = "Gradient Button",
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500
            ),
            gradientBrush = Brush.linearGradient(
                listOf(
                    Color(0xFF4185F2),
                    Color(0xFF3362F7)
                )
            ),
        )
        Spacer(Modifier.height(16.dp))
        GradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            text = "禁用状态",
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500
            ),
            gradientBrush = Brush.linearGradient(
                listOf(
                    Color(0xFF4185F2),
                    Color(0xFF3362F7)
                )
            ),
            shape = RoundedCornerShape(6.dp),
            enabled = false,
        )
    }
}