# android-base-ui-compose

## compose 版 Android 基本ui 自定义View

[![latest](https://jitpack.io/v/FPhoenixCorneaE/android-base-ui-compose.svg)](https://jitpack.io/#FPhoenixCorneaE/android-base-ui-compose)

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

```groovy
dependencies {
    implementation 'com.github.FPhoenixCorneaE:android-base-ui-compose:latest'
}
```

### 更新日志

#### 2024-10-23

* feat: 添加滑动操作组件[Swiper](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/Swiper.kt)

#### 2024-02-18

* feat: 添加自定义拖动条[CustomSeekbar](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/CustomSeekbar.kt)
* feat: 添加自定义摇杆器[Rocker](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/Rocker.kt)

#### 2024-02-05

* feat: 添加自定义渐变背景按钮[GradientButton](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/GradientButton.kt)
* feat: 添加自定义阴影绘制[Shadow](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/Shadow.kt)
  <br>
  致谢：[Jetpack Compose：自定义阴影绘制，支持阴影颜色、圆角、偏移量](https://blog.csdn.net/lalallallalla/article/details/121502260)

#### 2024-01-26

* feat: 使用[SimplePath](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/graphics/path/SimplePath.kt)导五角星圆角
  <br>
  致谢：[Android 多边形导圆角（Path画折线导圆角）](https://blog.csdn.net/liuyu0915/article/details/131721872)

#### 2024-01-25

* feat: 添加自定义饼状图[PieChart](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/chart/PieChart.kt)
  <br>
  致谢：[CustomViewProject](https://gitee.com/lanyangyangzzz/custom-view-project)

#### 2024-01-23

* feat: 添加自定义水平进度条[HorizontalProgressBar](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/progressbar/HorizontalProgressBar.kt)
* feat: 添加自定义垂直进度条[VerticalProgressBar](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/progressbar/VerticalProgressBar.kt)

#### 2024-01-15

* feat: 添加自定义验证码输入框[AuthCodeTextField](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/AuthCodeTextField.kt)
  <br>
  致谢：[Jetpack Compose实现验证码输入框](https://juejin.cn/post/7249585135799697468?searchId=20240112173131CA2803BCAA277339FB3D)

#### 2024-01-10

* feat: 添加自定义环形进度条[CircleProgressBar](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/progressbar/CircleProgressBar.kt)：支持渐变进度，支持动画，支持逆时针增加进度，支持非满圆

#### 2024-01-08

* feat: 添加自定义数字键盘之带右侧栏的键盘[SidebarNumberKeyboard](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/NumberKeyboard.kt)
* feat: 添加自定义数字键盘之身份证号键盘[IdCardNumberKeyboard](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/NumberKeyboard.kt)
* feat: 添加自定义数字键盘之带标题的键盘[TitleNumberKeyboard](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/NumberKeyboard.kt)
* feat: 添加自定义数字键盘之配置多个按键的键盘[NumberKeyboardWithKeys](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/NumberKeyboard.kt)
* feat: 添加自定义数字键盘之随机数字键盘[RandomNumberKeyboard](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/NumberKeyboard.kt)

#### 2024-01-05

* feat: 添加自定义数字键盘之默认键盘[BasicNumberKeyboard](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/NumberKeyboard.kt)

#### 2024-01-02

* feat: 添加自定义辉光呼吸圆圈[GlowCircle](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/GlowCircle.kt)
* feat: 添加自定义评分组件[RatingBar](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/RatingBar.kt)
* perf: 五角星以Shape的形式剪切展现
* fix: Switch初始化checked为true时状态不对

#### 2023-12-28

* feat: 添加自定义五角星[Star](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/Star.kt)
  <br>
  致谢：[Android 自定义View 绘制五角星](https://www.jianshu.com/p/24efb605098b)
* feat: 添加自定义红心[Heart](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/Heart.kt)
* feat: 添加自定义跑马灯光圈[MarqueeAperture](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/MarqueeAperture.kt)
  <br>
  致谢：[android 自定义view 跑马灯-光圈效果](https://juejin.cn/post/7171030095866363934)

#### 2023-12-27

* feat: 添加自定义随机滚动小球布局[RandomlyRollBallLayout](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/RandomlyRollBallLayout.kt)
* feat: 添加自定义系统ui脚手架，适配状态栏、底部导航栏[SystemUiScaffold](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/SystemUiScaffold.kt)
* feat: 添加自定义选项卡[TabRow](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/TabRow.kt)

#### 2023-12-26

* feat: 添加自定义输入框[CustomEditText](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/CustomEditText.kt)
* feat: 添加自定义正方形布局[Square](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/Square.kt)
* feat: 添加自定义垂直分割线[VerticalDivider](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/VerticalDivider.kt)
* feat: 添加自定义打字机效果Text[TypewriterText](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/TypewriterText.kt)
* feat: 添加自定义三角形[Triangle](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/Triangle.kt)
* feat: 添加自定义开关[Switch](https://github.com/FPhoenixCorneaE/android-base-ui-compose/blob/main/base-ui/src/main/java/com/fphoenixcorneae/baseui/Switch.kt)
  <br>
  致谢：[Compose制作一个“IOS”效果的SwitchButton](https://juejin.cn/post/7134702107742961701)