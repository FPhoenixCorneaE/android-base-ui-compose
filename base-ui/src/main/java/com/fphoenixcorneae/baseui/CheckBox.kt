package com.fphoenixcorneae.baseui

import android.view.Gravity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.ext.clickableNoRipple

@Composable
fun CheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    boxSize: Dp = 16.dp,
    boxShape: Shape = RoundedCornerShape(50),
    boxPadding: PaddingValues = PaddingValues(),
    boxSpace: Dp = 8.dp,
    boxGravity: Int = Gravity.START,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(),
    text: String? = null,
    textStyle: TextStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    onCheckedChange: ((Boolean) -> Unit)?,
) {
    val tickPath = Path()
    val tickPathMeasure = PathMeasure()
    val dstPath = Path()
    val tickPaint = Paint()
    var isChecked by remember { mutableStateOf(checked) }
    // 对勾动画进度
    val tickProgress by animateFloatAsState(
        targetValue = if (isChecked) 1f else 0f,
        animationSpec = tween(200, easing = LinearEasing),
        label = "tickProgress"
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isChecked) 0.dp else 1.dp,
        animationSpec = tween(200, easing = LinearEasing),
        label = "borderWidth"
    )
    val boxBackground by animateColorAsState(
        targetValue = if (checked) colors.checkedBoxColor else colors.uncheckedBoxColor,
        animationSpec = tween(200, easing = LinearEasing),
        label = "borderWidth"
    )
    Row(
        modifier = modifier
            .clickableNoRipple(enabled = enabled) {
                isChecked = !checked
                onCheckedChange?.invoke(!checked)
            }
            .alpha(if (enabled) 1f else 0.4f),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
    ) {
        if (boxGravity == Gravity.END) {
            text?.let {
                Text(text = it, style = textStyle)
                Spacer(Modifier.width(boxSpace))
            }
        }
        Box(
            Modifier
                .padding(boxPadding)
                .size(boxSize)
                .clip(boxShape)
                .border(
                    width = borderWidth,
                    color = if (enabled) (if (checked) colors.checkedBorderColor else colors.uncheckedBorderColor) else colors.disabledBorderColor,
                    shape = boxShape
                )
                .background(if (enabled) boxBackground else (if (checked) colors.disabledCheckedBoxColor else colors.disabledUncheckedBoxColor))
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                tickPath.apply {
                    reset()
                    moveTo(size.width * 0.1f, size.height * 0.5f)
                    lineTo(
                        size.width * 0.4f,
                        size.height * 0.75f
                    )
                    lineTo(
                        size.width * 0.85f,
                        size.height * 0.25f
                    )
                }
                tickPathMeasure.setPath(tickPath, false)
                tickPathMeasure.getSegment(0f, tickPathMeasure.length * tickProgress, dstPath, true)
                drawIntoCanvas { canvas ->
                    canvas.drawOutline(
                        outline = Outline.Generic(dstPath),
                        paint = tickPaint.apply {
                            color =
                                if (checked) colors.checkedCheckmarkColor else colors.uncheckedCheckmarkColor
                            style = PaintingStyle.Stroke
                            strokeCap = StrokeCap.Round
                            strokeJoin = StrokeJoin.Round
                            strokeWidth = (boxSize * 0.05f).toPx()
                        }
                    )
                }
            }
        }
        if (boxGravity == Gravity.START) {
            text?.let {
                Spacer(Modifier.width(boxSpace))
                Text(text = it, style = textStyle)
            }
        }
    }
}

@Preview
@Composable
fun PreviewCheckBox() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5))
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = true,
            enabled = true,
            boxShape = CircleShape,
            text = "记住密码",
        ) {}
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = false,
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
        ) {}
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = true,
            enabled = false,
            text = "不可选中"
        ) {}
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = true,
            enabled = true,
            boxShape = CircleShape,
            boxGravity = Gravity.END,
            text = "A",
        ) {
        }
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = false,
            enabled = true,
            boxShape = CircleShape,
            boxGravity = Gravity.END,
            text = "B",
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
        }
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = false,
            enabled = true,
            boxShape = CircleShape,
            boxGravity = Gravity.END,
            text = "C",
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
        }
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = false,
            enabled = true,
            boxShape = CircleShape,
            boxGravity = Gravity.END,
            text = "D",
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
        }
        CheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            checked = true,
            enabled = true,
            boxSize = 60.dp,
            boxShape = CircleShape,
            boxGravity = Gravity.END,
            horizontalArrangement = Arrangement.Center
        ) {
        }
    }
}

