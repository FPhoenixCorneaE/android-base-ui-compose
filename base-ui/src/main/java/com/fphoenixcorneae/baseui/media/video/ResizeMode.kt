package com.fphoenixcorneae.baseui.media.video

import androidx.compose.runtime.Stable
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout

/**
 * VideoPlayer resize mode.
 */
@Stable
enum class ResizeMode() {
    /**
     * Either the width or height is decreased to obtain the desired aspect ratio.
     */
    FIT,

    /**
     * The width is fixed and the height is increased or decreased to obtain the desired aspect ratio.
     */
    FIXED_WIDTH,

    /**
     * The height is fixed and the width is increased or decreased to obtain the desired aspect ratio.
     */
    FIXED_HEIGHT,

    /**
     * The specified aspect ratio is ignored.
     */
    FILL,

    /**
     * Either the width or height is increased to obtain the desired aspect ratio.
     */
    ZOOM;

    @UnstableApi
    fun toPlayerViewResizeMode(): Int =
        when (this) {
            FIT -> AspectRatioFrameLayout.RESIZE_MODE_FIT
            FIXED_WIDTH -> AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
            FIXED_HEIGHT -> AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
            FILL -> AspectRatioFrameLayout.RESIZE_MODE_FILL
            ZOOM -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        }
}