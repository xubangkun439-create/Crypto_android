# Coinlore 联通性验证报告

本次对 `https://api.coinlore.net/api/tickers/?start=0&limit=3` 做了完整联通性检查，分为三层：

1. **代码配置层**（静态）
   - `MainActivity` 的 `baseUrl` 为 `https://api.coinlore.net/`
   - `CryptoApiService` 的 endpoint 为 `@GET("api/tickers/")`

2. **网络可达层**（动态）
   - 通过 `curl -I` 验证外网可达性
   - 当前执行环境返回 `CONNECT tunnel failed, response 403`

3. **响应结构层**（动态）
   - 通过 `curl` + Python JSON 解析，检查 `data` 及关键字段
   - 当前因网络被代理拦截未能执行成功

## 结论

- **项目内部调用链已正确接到 Coinlore API**。
- **当前容器环境对该外网地址的出站访问被代理策略拦截（403）**，因此无法在本环境完成真实响应验证。

## 复现实验命令

```bash
./scripts/verify_coinlore_connectivity.sh
```

若在你本地网络可访问外网，此脚本会继续校验返回 JSON 是否包含：
- `data`
- `symbol`
- `name`
- `price_usd`
- `percent_change_24h`
