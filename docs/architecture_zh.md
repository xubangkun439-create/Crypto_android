# Android Crypto App 设计逻辑与代码架构

## 1. 产品目标

### 1.1 核心能力
- **资讯聚合**：聚合主流媒体、项目公告、监管新闻。
- **论坛社区**：围绕币种/赛道（DeFi、Layer2、Meme）进行讨论。
- **实时行情**：支持主流币实时价格、涨跌幅、成交量、K 线入口。

### 1.2 目标用户
- 日内交易用户：关注实时价格变化。
- 中长期持有者：关注项目资讯与社区情绪。
- 新手用户：需要结构化资讯和问答讨论。

## 2. 功能设计逻辑

### 2.1 信息架构（App 底部导航）
1. **Market**：价格列表、币种详情、价格提醒。
2. **News**：热点新闻、快讯流、专题聚合。
3. **Forum**：帖子流、话题圈子、发帖评论。
4. **Profile**：用户资产偏好、收藏、通知设置。

### 2.2 关键业务流程

#### A. 行情流程
- App 启动后请求默认币种（BTC/ETH/SOL）价格。
- 前台页面每 3~5 秒拉取或 WebSocket 推送更新。
- 涨跌变化通过颜色和动画提示（红涨绿跌或可配置）。

#### B. 资讯流程
- 按时间和热度双排序。
- 用户可选择关注标签（如 BTC、ETF、监管）。
- 资讯详情内可跳转“相关讨论帖”。

#### C. 论坛流程
- 游客可浏览，登录后可发帖/评论/点赞。
- 帖子支持标签（币种、主题）和排序（最新、最热）。
- 内容治理：敏感词过滤、举报、管理员审核。

## 3. 推荐技术架构（Android）

## 3.1 分层
- **UI 层**：Jetpack Compose + Navigation。
- **Domain 层**：UseCase（可后续补齐）。
- **Data 层**：Repository + Remote/Local DataSource。

## 3.2 模块化
- `app`：应用入口与依赖注入。
- `core:model`：全局数据模型。
- `core:network`：网络协议与 API。
- `feature:market`：行情业务。
- 后续建议：`feature:news`、`feature:forum`、`core:database`、`core:common`。

## 4. 数据与接口建议

### 4.1 行情接口
- `GET /v1/market/prices?symbols=BTC,ETH&currency=USD`
- 返回：symbol、priceUsd、change24h、volume24h、timestamp。

### 4.2 资讯接口
- `GET /v1/news/latest?page=1&pageSize=20`
- 返回：title、summary、source、publishedAt、tags。

### 4.3 论坛接口
- `GET /v1/forum/posts?page=1&pageSize=20`
- `GET /v1/forum/posts/{id}`
- 后续扩展：发帖、评论、点赞、收藏、举报。

## 5. 非功能需求

- **性能**：首屏 < 2s，行情页刷新帧率稳定。
- **稳定性**：网络失败自动重试 + 回退缓存。
- **安全**：用户鉴权、接口签名、基础风控。
- **合规**：增加风险提示，不提供投资建议声明。

## 6. 开发里程碑（建议）

### M1（1~2 周）
- 搭建多模块工程 + 行情列表 MVP。
- 接入一个行情 API，展示实时价格。

### M2（2~3 周）
- 资讯列表与详情。
- 基础论坛（帖子列表 + 详情）。

### M3（2~3 周）
- 登录体系、发帖评论、消息通知。
- 埋点监控、崩溃监控、灰度发布。
