package com.fphoenixcorneae.baseui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Material Design divider.
 * A vertical divider is a thin line that groups content in lists and layouts.
 * !Divider image
 * Params:
 * @param modifier - the Modifier to be applied to this divider line.
 * @param thickness - thickness of this divider line. Using Dp.Hairline will produce a single pixel divider regardless of screen density.
 * @param color - color of this divider line.
 */
@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) {
    val targetThickness = if (thickness == Dp.Hairline) {
        (1f / LocalDensity.current.density).dp
    } else {
        thickness
    }
    Box(
        modifier
            .width(targetThickness)
            .fillMaxHeight()
            .background(color = color)
    )
}

@Preview
@Composable
fun PreviewVerticalDivider() {
    VerticalDivider(thickness = 2.dp, color = Color.Red)
}