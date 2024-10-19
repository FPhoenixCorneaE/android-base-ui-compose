package com.fphoenixcorneae.baseui.model

import androidx.annotation.Keep

@Keep
data class MenuGroup(
    val name: String? = null,
    val children: List<MenuItem>? = null,
)

@Keep
data class MenuItem(
    val name: String? = null,
    val router: String? = null,
)
