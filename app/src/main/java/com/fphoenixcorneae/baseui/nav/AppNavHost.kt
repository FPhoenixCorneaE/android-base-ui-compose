package com.fphoenixcorneae.baseui.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fphoenixcorneae.baseui.ui.HomeScreen

@Composable
fun AppNavHost() {
    NavHost(
        navController = NavController.get(currentComposer),
        startDestination = Router.Home,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(200)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(200)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        }
    ) {
        composable(Router.Home) {
            HomeScreen()
        }
        addBasicComponentGraph()
        addSystemComponentGraph()
        addAdvancedComponentGraph()
    }
}

private fun NavGraphBuilder.addBasicComponentGraph() {
    Router.Basic.entries.forEach { enum ->
        composable(enum.router) {
            val composableFunc = enum.getComposable()
            composableFunc?.invoke()
        }
    }
}

private fun NavGraphBuilder.addSystemComponentGraph() {
    Router.System.entries.forEach { enum ->
        composable(enum.router) {
            val composableFunc = enum.getComposable()
            composableFunc?.invoke()
        }
    }
}

private fun NavGraphBuilder.addAdvancedComponentGraph() {
    Router.Advanced.entries.forEach { enum ->
        composable(enum.router) {
            val composableFunc = enum.getComposable()
            composableFunc?.invoke()
        }
    }
}
