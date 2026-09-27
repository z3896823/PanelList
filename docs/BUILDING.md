# PanelList 本地构建与运行

## 版本选择（2026-09-27）

本次选择偏保守的稳定组合，而非最新大版本：

| 组件 | 固定版本 |
| --- | --- |
| JDK | OpenJDK 17（本机 Homebrew 17.0.20.1，Apple Silicon） |
| Android Gradle Plugin | 8.13.2 |
| Gradle Wrapper | 8.13，分发包附 SHA-256 校验 |
| compileSdk | 36 |
| SDK Build Tools | 35.0.0 |
| AppCompat | 1.7.1 |
| SwipeRefreshLayout | 1.1.0 |
| minSdk | Demo 为 21（Android 5.0）；控件库保留 19 |
| App targetSdk | 36（Android 16） |
| 调试设备 | USB 连接的实体安卓手机（已在 vivo V2307A 安装启动） |

调研范围是 2026-06-27 至 2026-09-27。没有足够证据证明社区存在唯一推荐组合；选择依据是当前官方兼容表和 SDK 团队仍在使用的推荐组合。

- [Android 官方 AGP 8.13 兼容表](https://developer.android.com/build/releases/agp-8-13-0-release-notes)：Gradle 8.13、JDK 17、Build Tools 35.0.0，最高支持 API 36.1。
- [Google Navigation SDK 推荐矩阵](https://developers.google.com/maps/documentation/navigation/android-sdk/kotlin-compatibility-migration)：7.7+ 推荐 AGP 8.13.2 与 Gradle 8.13。这是该 SDK 的推荐，不代表 Android 社区统一结论。
- [Navigation SDK 发布记录](https://developers.google.com/maps/documentation/navigation/android-sdk/release-notes)：上述推荐首次列于 2026-06-18，略早于三个月范围；7 月、8 月仍有该系列版本发布。
- [2026 年 9 月官方发布记录](https://androidstudio.googleblog.com/2026/09/)：当前已进入 AGP 9.4 系列，本项目此次先不迁入 9.x。

## 安装环境（macOS / Homebrew）

```bash
brew install openjdk@17
brew install --cask android-commandlinetools
source tools/android-env.sh
sdkmanager --sdk_root="$ANDROID_HOME" \
  'cmdline-tools;22.0' 'platform-tools' 'platforms;android-36' 'build-tools;35.0.0'
```

SDK 安装时按提示接受许可。命令行工具提供 `sdkmanager`；22.0 版本提示它将由 `android sdk` 替代，当前仍可正常使用。

本机 Command-line Tools 22.0 已从 Homebrew 官方下载包复制到 SDK 的 `cmdline-tools/22.0` 并登记，后续可直接使用上述 SDK Manager 命令管理。

本机 SDK 位于 `~/Library/Android/sdk`。`local.properties` 记录本机路径，不纳入 Git。`tools/android-env.sh` 设置当前终端的 JDK/SDK，不修改全局 shell 配置；已有 `JAVA_HOME` 和 `ANDROID_HOME` 优先。

不需要安装全局 Gradle、NDK 或 Android Studio。以后若使用 Android Studio，将 Gradle JDK 指向 JDK 17、SDK 指向上述目录即可。

## 构建

在仓库根目录执行：

```bash
source tools/android-env.sh
./gradlew :app:assembleDebug
```

APK：`app/build/outputs/apk/debug/app-debug.apk`，自动使用本地调试密钥签名。

## 真机调试

后续开发默认使用实体手机，不安装或创建模拟器。

1. 手机开启开发者选项及 USB 调试，使用支持数据传输的线直连 Mac。
2. 解锁手机，在手机上允许本机的 USB 调试授权。
3. 在仓库根目录执行：

```bash
source tools/android-env.sh
adb devices -l
./tools/run-demo.sh
```

脚本默认用 `adb -d` 选择唯一的 USB 真机，自动构建、安装并启动 Demo。多设备或无线调试时，显式指定设备：`./tools/run-demo.sh <设备序列号>`。

本次将 targetSdk 升级到 36，以适配 Android 16 并消除旧目标版本提示。两个 Demo 页面通过 `DemoActivity` 处理 Android 15 及以上的窗口边距，避免标题栏遮挡表格。此包仍使用调试签名，尚未配置正式发布签名。

## 验证

```bash
source tools/android-env.sh
./gradlew testDebugUnitTest lintDebug
ANDROID_SERIAL=<adb设备序列号> ./gradlew connectedDebugAndroidTest
```

`DemoSmokeTest` 检查原始 Demo 的增删、更新数据时表格和行表头数量同步，以及从菜单进入房态页面。另一个回归检查验证横向滚动双向同步与纵向滚动行对齐。模板中的加法测试只说明测试管线可用，不构成功能验证。

targetSdk 升级后已移除 `ExpiredTargetSdkVersion` 的降级配置，静态检查恢复默认规则。

## 本次迁移范围

- JCenter 改为 Google Maven 和 Maven Central，依赖与插件版本固定。
- Support Library 改为 AndroidX；移除未使用的 ConstraintLayout 依赖。
- 为模块配置 namespace，声明启动 Activity 的 exported 属性。
- 资源 ID 的 switch 改为 if/else，以适配新工具链默认的非 final 资源 ID。
- 保留 Java、现有布局和 Demo 功能；AppCompat 1.7.1 要求 minSdk 21，因此 Demo 不再支持 Android 4.4；控件库仍保留 minSdk 19。
- targetSdk 已升级至 36；正式签名和发布配置留待单独任务。

本地构建与运行记录见 `VERIFICATION.md`。
