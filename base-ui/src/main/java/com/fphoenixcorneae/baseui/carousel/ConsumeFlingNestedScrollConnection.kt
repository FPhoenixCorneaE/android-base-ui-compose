package com.fphoenixcorneae.baseui.carousel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

internal class ConsumeFlingNestedScrollConnection(
    private val consumeHorizontal: Boolean,
    private val consumeVertical: Boolean,
    private val pagerState: PagerState,
) : NestedScrollConnection {
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset = when (source) {
        // We can consume all resting fling scrolls so that they don't propagate up to the
        // Pager
        NestedScrollSource.Fling -> available.consume(consumeHorizontal, consumeVertical)
        else -> Offset.Zero
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        return if (pagerState.currentPageOffset != 0f) {
            // The Pager is already scrolling. This means that a nested scroll child was
            // scrolled to end, and the Pager can use this fling
            Velocity.Zero
        } else {
            // A nested scroll child is still scrolling. We can consume all post fling
            // velocity on the main-axis so that it doesn't propagate up to the Pager
            available.consume(consumeHorizontal, consumeVertical)
        }
    }
}

private fun Offset.consume(
    consumeHorizontal: Boolean,
    consumeVertical: Boolean,
): Offset = Offset(
    x = if (consumeHorizontal) this.x else 0f,
    y = if (consumeVertical) this.y else 0f,
)

private fun Velocity.consume(
    consumeHorizontal: Boolean,
    consumeVertical: Boolean,
): Velocity = Velocity(
    x = if (consumeHorizontal) this.x else 0f,
    y = if (consumeVertical) this.y else 0f,
)