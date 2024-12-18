package com.fphoenixcorneae.baseui.media.video

import androidx.compose.runtime.Stable
import androidx.media3.common.Player

/**
 * VideoPlayer repeat mode.
 */
@Stable
enum class RepeatMode() {
    /**
     * No repeat.
     */
    NONE,

    /**
     * Repeat current media only.
     */
    ONE,

    /**
     * Repeat all track.
     */
    ALL;

    /**
     * Convert [RepeatMode] to exoplayer repeat mode.
     *
     * @return [Player.REPEAT_MODE_ALL] or [Player.REPEAT_MODE_OFF] or [Player.REPEAT_MODE_ONE] or
     */
    internal fun toExoPlayerRepeatMode(): Int =
        when (this) {
            NONE -> Player.REPEAT_MODE_OFF
            ALL -> Player.REPEAT_MODE_ALL
            ONE -> Player.REPEAT_MODE_ONE
        }
}