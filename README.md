# compose-custom-widget

## compose版自定义小部件

[![latest](https://jitpack.io/v/FPhoenixCorneaE/compose-custom-widget.svg)](https://jitpack.io/#FPhoenixCorneaE/compose-custom-widget)

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
    implementation 'com.github.FPhoenixCorneaE:compose-custom-widget:latest'
}
```

### 更新日志

#### 2024-01-08 
* feat: 添加自定义数字键盘之带右侧栏的键盘[SidebarNumberKeyboard](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/NumberKeyboard.kt)
* feat: 添加自定义数字键盘之身份证号键盘[IdCardNumberKeyboard](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/NumberKeyboard.kt)
* feat: 添加自定义数字键盘之带标题的键盘[TitleNumberKeyboard](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/NumberKeyboard.kt)

#### 2024-01-02 
* feat: 添加自定义数字键盘之默认键盘[BasicNumberKeyboard](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/NumberKeyboard.kt)

#### 2024-01-02 
* feat: 添加自定义辉光呼吸圆圈[GlowCircle](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/GlowCircle.kt)
* feat: 添加自定义评分组件[RatingBar](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/RatingBar.kt)
* perf: 五角星以Shape的形式剪切展现
* fix: Switch初始化checked为true时状态不对

#### 2023-12-28 
* feat: 添加自定义五角星[Star](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/Star.kt)
  致谢：[Android 自定义View 绘制五角星](https://www.jianshu.com/p/24efb605098b)
* feat: 添加自定义红心[Heart](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/Heart.kt)
* feat: 添加自定义跑马灯光圈[MarqueeAperture](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/MarqueeAperture.kt)
  致谢：[android 自定义view 跑马灯-光圈效果](https://juejin.cn/post/7171030095866363934)

#### 2023-12-27 
* feat: 添加自定义随机滚动小球布局[RandomlyRollBallLayout](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/RandomlyRollBallLayout.kt)
* feat: 添加自定义系统ui脚手架，适配状态栏、底部导航栏[SystemUiScaffold](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/SystemUiScaffold.kt)
* feat: 添加自定义选项卡[TabRow](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/TabRow.kt)

#### 2023-12-26 
* feat: 添加自定义输入框[CustomEditText](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/CustomEditText.kt)
* feat: 添加自定义正方形布局[Square](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/Square.kt)
* feat: 添加自定义垂直分割线[VerticalDivider](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/VerticalDivider.kt)
* feat: 添加自定义打字机效果Text[TypewriterText](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/TypewriterText.kt)
* feat: 添加自定义三角形[Triangle](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/Triangle.kt)
* feat: 添加自定义开关[Switch](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/Switch.kt)
  致谢：[Compose制作一个“IOS”效果的SwitchButton](https://juejin.cn/post/7134702107742961701)