package com.fphoenixcorneae.baseui.model

import com.fphoenixcorneae.baseui.nav.Router

object HomeMenuData {
    val groups = listOf(
        MenuGroup(
            name = "基础组件",
            children = Router.Basic.toMenus()
        ),
        MenuGroup(
            name = "媒体组件",
            children = Router.Media.toMenus()
        ),
        MenuGroup(
            name = "系统组件",
            children = Router.System.toMenus()
        ),
        MenuGroup(
            name = "进阶组件",
            children = Router.Advanced.toMenus()
        ),
    )
}