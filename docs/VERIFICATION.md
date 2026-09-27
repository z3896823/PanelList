# 本地验证记录

## 当前版本：target SDK 36（2026-09-27）

- App 的 targetSdk 已由 26 升级为 36；compileSdk 保持 36，minSdk 保持 21。
- 新增 `DemoActivity`，在 Android 15 及以上处理 AppCompat 内容区域的窗口边距，修复 Android 16 上标题栏遮挡表格的问题；状态栏改用适合浅色背景的深色图标。
- `:app:assembleDebug :app:assembleDebugAndroidTest testDebugUnitTest lintDebug` 成功。两个模块的 2 项模板单元测试通过，App lint 为 0 错误 / 26 警告。
- 最终 APK 已覆盖安装至 vivo V2307A（Android 16 / API 36），设备包信息确认 `targetSdk=36`。主页面冷启动成功，主表格和房态页面均已截图检查，未出现旧目标版本警告。
- App 真机自动化测试完整重跑：**4 项通过**（上下文、数据增删更新、横纵向滚动同步、房态页面跳转）。首次运行首个用例在 Activity 启动阶段超时；先将应用打开到前台后完整重跑通过。日志保留，首次超时不能算作通过。
- APK SHA-256：`3cfe8df9ede4f5a07a66c582c3cda4d8902de794fa65af546f71da239f1a8d7f`，APK 签名验证通过。
- 本轮日志与截图：`.local/verification/target36/`。最终测试日志为 `device-tests-retry.log`。
- 以上为单台 Android 16 真机的 Demo 验证，尚未覆盖所有 Android 版本、横屏和所有交互。仍是调试签名 APK。

## 历史记录：构建工具链迁移（target SDK 26）

以下结果对应升级 target SDK 前的历史构建，旧版本提示和 lint 降级配置已在当前版本移除。

> 后续状态：用户已确认 vivo V2307A 真机安装正常。按用户要求，已删除 PanelList_API_35、Android Emulator 和 Android 15 ARM64 系统镜像；今后默认真机调试。下文模拟器结果为迁移时的历史验证记录。

日期：2026-09-27。基线提交：`c4ea921`，迁移分支：`modernize-android-build`。

## 环境

- macOS 15.6.1 / Apple Silicon arm64，Homebrew OpenJDK 17.0.20.1。
- AGP 8.13.2、Gradle Wrapper 8.13、compileSdk 36、Build Tools 35.0.0。
- Android Command-line Tools 22.0、Platform Tools 37.0.1、Emulator 37.1.11。
- AVD：`PanelList_API_35`，Android 15 / API 35 / Google APIs ARM64，系统镜像 revision 9。
- 模拟器使用 Hypervisor.Framework，配置为 4 核、4096 MB RAM。
- 官方系统镜像分段下载、合并后通过 Google 仓库 XML 提供的 SHA-1 校验：`16f5bceca236b2737008977c4aaf826e46a8de7d`。临时分段和压缩包已清理。

## 验证结果

| 检查 | 结果 |
| --- | --- |
| `:app:assembleDebug` | 通过，产出调试 APK |
| APK 签名验证 | 通过，v1 / v2 调试签名 |
| `testDebugUnitTest` | 两个模块合计 2 项通过；均为原有模板测试 |
| `lintDebug` | 通过，App 0 错误 / 27 警告，库 0 错误 / 4 警告 |
| `connectedDebugAndroidTest` | Android 15 模拟器上 5 项通过，0 失败 / 0 错误 |
| `tools/run-demo.sh emulator-5554` | 构建、安装及启动成功，`am start -W` 返回 `Status: ok` |

设备测试的 5 项中，2 项是原有应用上下文检查，3 项为新增 Demo 回归：

1. 主表格初始 50 行，插入后 51 行、删除后 50 行、更新后 499 行；行表头数量始终一致。
2. 表头与内容水平滚动双向同步；模拟真实手指向上滑动后，内容与行表头的首行位置和顶部偏移一致。
3. 从主页面菜单打开房态页面，房间内容和行表头均为 20 行。

## APK 与证据

- APK：[`app-debug.apk`](../app/build/outputs/apk/debug/app-debug.apk)，约 3.2 MiB。
- 包名：`sysu.zyb.panellisttest`，版本 `1.0`，minSdk 21，targetSdk 26。
- APK SHA-256：`84974a30e1a9b69bb8d3805d1c2246881c8e01729e77e29881eaaefbfb3a58b2`。
- 主页面截图：[main.png](../.local/verification/main.png)。
- 房态页面截图：[room.png](../.local/verification/room.png)。
- 构建、安装、设备测试及 SDK 下载校验日志保存在 `.local/verification/`（不纳入 Git）。
- 设备测试报告：`app/build/reports/androidTests/connected/debug/index.html` 与 `panellistlibrary/build/reports/androidTests/connected/debug/index.html`。

## 范围与限制

- Demo 保留 targetSdk 26，首次启动会出现旧目标版本提示。发布前需要单独进行 targetSdk 行为适配和正式签名；当前包用于本地开发。
- 仅将该有意保留的 `ExpiredTargetSdkVersion` 发布限制降为 lint 警告。其余警告包括旧代码的硬编码文字、未使用资源、布局提示及固定依赖存在新版；没有全局关闭 lint。
- 本次只实测 Android 15 ARM64 模拟器，自动化回归未覆盖其他 Android 版本；后续已在 vivo V2307A 真机完成安装、启动，并由用户确认安装正常。控件库仍声明 minSdk 19，但本次没有实测 Android 4.4。
- 首次使用 2 核 / 2 GB 启动 Google APIs 镜像时出现系统服务初始化超时及 System UI ANR；提高至 4 核 / 4 GB 并重启后完成所有设备测试。该异常发生在系统服务，不能据此归因为 PanelList 应用错误。
- 构建仍提示部分 Gradle API 将在 Gradle 9 中移除；当前固定 Gradle 8.13 下验证通过。未迁入 Gradle 9。
