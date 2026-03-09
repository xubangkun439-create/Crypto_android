#!/usr/bin/env bash
set -euo pipefail

URL="https://api.coinlore.net/api/tickers/?start=0&limit=3"

echo "[1/3] Verify configured baseUrl and endpoint path in source..."
rg -n 'baseUrl = "https://api.coinlore.net/"|@GET\("api/tickers/"\)' app/src/main/java core/network/src/main/java

echo "[2/3] Try outbound HEAD request to Coinlore..."
set +e
HEAD_OUTPUT=$(curl -I --max-time 20 "$URL" 2>&1)
HEAD_CODE=$?
set -e
echo "$HEAD_OUTPUT"

if [[ $HEAD_CODE -ne 0 ]]; then
  echo "[WARN] HEAD request failed in current environment (likely outbound proxy restriction)."
else
  echo "[OK] HEAD request succeeded."
fi

echo "[3/3] Try outbound GET request and parse JSON keys..."
set +e
BODY=$(curl -sS --max-time 20 "$URL")
GET_CODE=$?
set -e

if [[ $GET_CODE -ne 0 ]]; then
  echo "[WARN] GET request failed in current environment."
  exit 2
fi

python - <<'PY' "$BODY"
import json, sys
raw = sys.argv[1]
obj = json.loads(raw)
assert 'data' in obj, 'missing data key'
assert isinstance(obj['data'], list), 'data is not list'
print(f"[OK] Parsed JSON. data size={len(obj['data'])}")
if obj['data']:
    first = obj['data'][0]
    needed = ['symbol', 'name', 'price_usd', 'percent_change_24h']
    miss = [k for k in needed if k not in first]
    assert not miss, f"missing keys in first ticker: {miss}"
    print(f"[OK] First ticker sample symbol={first.get('symbol')}")
PY
