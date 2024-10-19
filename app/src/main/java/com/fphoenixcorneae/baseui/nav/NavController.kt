package com.fphoenixcorneae.baseui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fphoenixcorneae.baseui.ext.invokeCompose

object NavController {
    private var navHostController = ThreadLocal<NavHostController>()

    fun set(controller: NavHostController) {
        navHostController.set(controller)
    }

    fun get(composer: Composer) = navHostController.get() ?: run {
        val navController = @Composable {
            rememberNavController()
        }
        invokeCompose(composer, navController).also {
            set(it)
        }
    }
}