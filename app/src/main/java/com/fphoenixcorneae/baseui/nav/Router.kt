package com.fphoenixcorneae.baseui.nav

import androidx.compose.runtime.Composable
import com.fphoenixcorneae.baseui.model.MenuItem
import com.fphoenixcorneae.baseui.ui.advanced.CarouselScreen
import com.fphoenixcorneae.baseui.ui.advanced.GlowCircleScreen
import com.fphoenixcorneae.baseui.ui.advanced.MarqueeApertureScreen
import com.fphoenixcorneae.baseui.ui.advanced.PieChartScreen
import com.fphoenixcorneae.baseui.ui.advanced.RandomlyRollBallLayoutScreen
import com.fphoenixcorneae.baseui.ui.advanced.RockerScreen
import com.fphoenixcorneae.baseui.ui.advanced.ShadowScreen
import com.fphoenixcorneae.baseui.ui.advanced.SwiperScreen
import com.fphoenixcorneae.baseui.ui.basic.AuthCodeTextFieldScreen
import com.fphoenixcorneae.baseui.ui.basic.AutoSizeTextScreen
import com.fphoenixcorneae.baseui.ui.basic.CheckBoxScreen
import com.fphoenixcorneae.baseui.ui.basic.EditTextScreen
import com.fphoenixcorneae.baseui.ui.basic.GradientButtonScreen
import com.fphoenixcorneae.baseui.ui.basic.HeartScreen
import com.fphoenixcorneae.baseui.ui.basic.ProgressBarScreen
import com.fphoenixcorneae.baseui.ui.basic.RatingBarScreen
import com.fphoenixcorneae.baseui.ui.basic.SeekbarScreen
import com.fphoenixcorneae.baseui.ui.basic.SquareScreen
import com.fphoenixcorneae.baseui.ui.basic.StarScreen
import com.fphoenixcorneae.baseui.ui.basic.SwitchScreen
import com.fphoenixcorneae.baseui.ui.basic.TabRowScreen
import com.fphoenixcorneae.baseui.ui.basic.TriangleScreen
import com.fphoenixcorneae.baseui.ui.basic.TypewriterTextScreen
import com.fphoenixcorneae.baseui.ui.basic.VerticalDividerScreen
import com.fphoenixcorneae.baseui.ui.media.AudioPlayerScreen
import com.fphoenixcorneae.baseui.ui.media.VideoPlayerScreen
import com.fphoenixcorneae.baseui.ui.system.KeyboardScreen
import com.fphoenixcorneae.baseui.ui.system.SystemUiScaffoldScreen

object Router {
    const val HOME = "home"

    enum class Basic(val router: String, val composable: @Composable () -> Unit) {
        EditText(
            router = "Basic_EditText",
            composable = @Composable { EditTextScreen() },
        ),
        AutoSizeText(
            router = "Basic_AutoSizeText",
            composable = @Composable { AutoSizeTextScreen() },
        ),
        TypewriterText(
            router = "Basic_TypewriterText",
            composable = @Composable { TypewriterTextScreen() },
        ),
        AuthCodeTextField(
            router = "Basic_AuthCodeTextField",
            composable = @Composable { AuthCodeTextFieldScreen() },
        ),
        Switch(
            router = "Basic_Switch",
            composable = @Composable { SwitchScreen() },
        ),
        ProgressBar(
            router = "Basic_ProgressBar",
            composable = @Composable { ProgressBarScreen() },
        ),
        Seekbar(
            router = "Basic_Seekbar",
            composable = @Composable { SeekbarScreen() },
        ),
        GradientButton(
            router = "Basic_GradientButton",
            composable = @Composable { GradientButtonScreen() },
        ),
        RatingBar(
            router = "Basic_RatingBar",
            composable = @Composable { RatingBarScreen() },
        ),
        Star(
            router = "Basic_Star",
            composable = @Composable { StarScreen() }
        ),
        Heart(
            router = "Basic_Heart",
            composable = @Composable { HeartScreen() }
        ),
        TabRow(
            router = "Basic_TabRow",
            composable = @Composable { TabRowScreen() }
        ),
        Triangle(
            router = "Basic_Triangle",
            composable = @Composable { TriangleScreen() }
        ),
        VerticalDivider(
            router = "Basic_VerticalDivider",
            composable = @Composable { VerticalDividerScreen() }
        ),
        Square(
            router = "Basic_Square",
            composable = @Composable { SquareScreen() }
        ),
        CheckBox(
            router = "Basic_CheckBox",
            composable = @Composable { CheckBoxScreen() }
        ),
        ;

        /**
         * 根据名称获取 Composable 函数
         */
        @Composable
        fun getComposable(): @Composable (() -> Unit)? =
            mapping[name]?.composable

        companion object {
            /** 创建一个映射，将枚举成员名称映射到 Composable 函数 */
            private val mapping = entries.associateBy(Basic::name)

            fun toMenus(): List<MenuItem> =
                entries.flatMap {
                    listOf(MenuItem(name = it.name, router = it.router))
                }
        }
    }

    enum class Media(val router: String, val composable: @Composable () -> Unit) {
        AudioPlayer(
            router = "Media_AudioPlayer",
            composable = @Composable { AudioPlayerScreen() },
        ),
        VideoPlayer(
            router = "Media_VideoPlayer",
            composable = @Composable { VideoPlayerScreen() },
        ),
        ;

        /**
         * 根据名称获取 Composable 函数
         */
        @Composable
        fun getComposable(): @Composable (() -> Unit)? =
            mapping[name]?.composable

        companion object {
            /** 创建一个映射，将枚举成员名称映射到 Composable 函数 */
            private val mapping = entries.associateBy(Media::name)

            fun toMenus(): List<MenuItem> =
                entries.flatMap {
                    listOf(MenuItem(name = it.name, router = it.router))
                }
        }
    }

    enum class System(val router: String, val composable: @Composable () -> Unit) {
        Keyboard(
            router = "System_Keyboard",
            composable = @Composable { KeyboardScreen() },
        ),
        SystemUiScaffold(
            router = "System_SystemUiScaffold",
            composable = @Composable { SystemUiScaffoldScreen() },
        ),
        ;

        /**
         * 根据名称获取 Composable 函数
         */
        @Composable
        fun getComposable(): @Composable (() -> Unit)? =
            mapping[name]?.composable

        companion object {
            /** 创建一个映射，将枚举成员名称映射到 Composable 函数 */
            private val mapping = entries.associateBy(System::name)

            fun toMenus(): List<MenuItem> =
                entries.flatMap {
                    listOf(MenuItem(name = it.name, router = it.router))
                }
        }
    }

    enum class Advanced(val router: String, val composable: @Composable () -> Unit) {
        PieChart(
            router = "Advanced_PieChart",
            composable = @Composable { PieChartScreen() },
        ),
        Rocker(
            router = "Advanced_Rocker",
            composable = @Composable { RockerScreen() },
        ),
        Shadow(
            router = "Advanced_Shadow",
            composable = @Composable { ShadowScreen() },
        ),
        RandomlyRollBallLayout(
            router = "Advanced_RandomlyRollBallLayout",
            composable = @Composable { RandomlyRollBallLayoutScreen() },
        ),
        GlowCircle(
            router = "Advanced_GlowCircle",
            composable = @Composable { GlowCircleScreen() },
        ),
        MarqueeAperture(
            router = "Advanced_MarqueeAperture",
            composable = @Composable { MarqueeApertureScreen() },
        ),
        Swiper(
            router = "Advanced_Swiper",
            composable = @Composable { SwiperScreen() },
        ),
        Carousel(
            router = "Advanced_Carousel",
            composable = @Composable { CarouselScreen() },
        ),
        ;

        /**
         * 根据名称获取 Composable 函数
         */
        @Composable
        fun getComposable(): @Composable (() -> Unit)? =
            mapping[name]?.composable

        companion object {
            /** 创建一个映射，将枚举成员名称映射到 Composable 函数 */
            private val mapping = entries.associateBy(Advanced::name)

            fun toMenus(): List<MenuItem> =
                entries.flatMap {
                    listOf(MenuItem(name = it.name, router = it.router))
                }
        }
    }
}