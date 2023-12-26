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

#### 2023-12-26 添加自定义 [Triangle](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/Triangle.kt)

#### 2023-12-26 添加自定义 [Switch](https://github.com/FPhoenixCorneaE/compose-custom-widget/blob/main/custom-widget/src/main/java/com/fphoenixcorneae/widget/Switch.kt)
致谢：[Compose制作一个“IOS”效果的SwitchButton](https://juejin.cn/post/7134702107742961701)