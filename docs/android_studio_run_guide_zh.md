# Android Studio 运行指南（解决“没有 Run”）

如果你看到顶部是 `Add Configuration` 且没有 `app` 可运行项，一般是 **Gradle Sync 未成功** 或 **没有创建 Android App 配置**。

## 1. 先确认你打开的是项目根目录

应打开包含以下文件的目录：
- `settings.gradle.kts`
- `build.gradle.kts`
- `app/build.gradle.kts`

## 2. 手动触发 Gradle 同步

- 菜单：`File -> Sync Project with Gradle Files`
- 等待右下角完成。

> 只有同步成功，Android Studio 才会识别 `app` 模块并自动生成可运行配置。

## 3. 手动添加 Run Configuration

如果同步后仍没有：
1. 点击顶部 `Add Configuration...`
2. 左上角 `+`
3. 选择 **Android App**（不是 Gradle）
4. Name 填 `app`
5. Module 选择 `app`
6. Launch 选择 `Default Activity`
7. Apply / OK

此时顶部下拉应出现 `app`，右侧三角形按钮可运行。

## 4. 设备与 SDK 检查

- 使用 `Device Manager` 创建或启动模拟器（API >= 26）。
- 确保安装 Android SDK Platform 34 与 Build-Tools。
- 确保 Gradle JDK = 17。

## 5. 常见原因

- 网络受限导致 Gradle 依赖下载失败（会导致同步失败，也就没有 Run）。
- 打开的不是项目根目录。
- 误建了 `Gradle` 类型运行配置而非 `Android App`。

## 6. 代码入口确认

本项目启动 Activity 为 `MainActivity`，已配置为 LAUNCHER：
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/example/cryptoapp/MainActivity.kt`
