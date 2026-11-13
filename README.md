# 野菜岛演示

在 Kotlin + Jetpack Compose 起步工程里，演示野菜桌面「野菜岛」的天气和限行提醒。

## 功能

屏幕顶部正中是野菜岛胶囊。演示场景可切换：

- **普通**：胶囊显示闲置状态（歌名）
- **下雨**：今晚或明天有雨时提一声
- **降温**：明天最高温比今天至少低 6°C 时提一声
- **限行**：限行日前一天提一声（示例：明日限行 3 和 8）
- **多条同时**：按优先级展示，限行 > 下雨 > 降温；点胶囊展开看全部

提醒规则在 `AlertRules` 里，由单元测试覆盖。数据来自本地 `AlertRepository`，方便以后接真实天气和限行接口。

## 编译

需要 JDK 17 和 Android SDK。

```bash
./gradlew assembleDebug test
```

Debug APK：`app/build/outputs/apk/debug/app-debug.apk`
