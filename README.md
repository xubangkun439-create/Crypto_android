# Crypto Android

一个面向加密货币用户的 Android 应用初始化项目，核心目标：

1. 获取加密货币资讯（News）。
2. 建立 Crypto 讨论论坛（Forum）。
3. 实时查看虚拟货币价格（Market）。

## 设计逻辑（高层）

- **用户价值优先级**：先满足“看价格”高频需求，再叠加资讯，再延展论坛互动。
- **模块化架构**：按 `core + feature` 拆分，降低耦合，方便团队并行开发。
- **可扩展后端协议**：通过 `CryptoApiService` 抽象行情/资讯/论坛接口，便于后续替换数据源。
- **状态驱动 UI**：ViewModel + UI State 统一页面状态，避免页面逻辑混乱。

详细设计见：`docs/architecture_zh.md`。

## 代码架构

```text
CryptoAndroid/
├── app/                     # 应用壳层（入口、导航、依赖组装）
├── core/
│   ├── model/               # 通用数据模型（CoinPrice、NewsItem、ForumPost）
│   └── network/             # API 抽象定义（Retrofit service）
└── feature/
    └── market/              # 行情功能（ViewModel + UI State + Repository 抽象）
```

## 后续建议

- 新增 `feature/news`、`feature/forum` 模块，并与 `core/database` 打通离线缓存。
- 增加 WebSocket 行情订阅，优化实时价格刷新体验。
- 为论坛增加发帖、评论、点赞与举报审核链路。

## 接口调用位置（你问的“调接口在哪”）

当前代码中，实时行情接口调用链路如下：

- `MainActivity.onCreate()` 调用 `marketViewModel.load(listOf("BTC", "ETH", "SOL"))`
- `MarketViewModel.load()` 调用 `marketRepository.getRealtimePrices(symbols)`
- `MarketRepositoryImpl.getRealtimePrices()` 调用 `CryptoApiService.getRealtimePrices()`

也就是说，真正发起网络请求的方法是 `CryptoApiService.getRealtimePrices()`，由 `NetworkFactory` 创建的 Retrofit Service 执行。

- 当前示例的 `baseUrl` 是 `https://api.example.com/`（占位域名，在 `MainActivity` 里配置）。
- 所以完整请求地址会是：`https://api.example.com/v1/market/prices`（实际项目请替换为你的真实后端域名）。
