package com.fphoenixcorneae.baseui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fphoenixcorneae.baseui.nav.AppNavHost
import com.fphoenixcorneae.baseui.ui.theme.ComposeCustomWidgetTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCustomWidgetTheme {
                AppNavHost()
            }
        }
    }
}