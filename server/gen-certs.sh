#!/bin/sh
# 生成自建服务端要用的证书。
#
# 背景：原 App 的 assets/test.crt 是「只含证书、无私钥」的自签证书，
#       所以无法直接拿来对外提供 TLS。
#       正确做法是自签一套，然后：
#         1) 把 ca.crt 放进 App 的 assets/test.crt（我们要改 App，顺便替换）
#         2) 服务端用 server.key + server.crt
#         3) 设备端若也 pin 了证书，需另行处理（见 docs/04-路线与建议.md）
#
# 需要在 server/ 目录下运行：  sh gen-certs.sh
set -e
DIR="$(cd "$(dirname "$0")" && pwd)/certs"
mkdir -p "$DIR"
cd "$DIR"

echo "==> 生成 CA"
openssl req -x509 -newkey rsa:2048 -nodes -keyout ca.key -out ca.crt \
  -days 3650 -subj "/C=CN/ST=beijing/O=ixiaocong.com/OU=ixiaocong.com/CN=ixiaocong.com"

echo "==> 生成服务端证书（同时用于 MQTT 和 HTTPS）"
openssl req -newkey rsa:2048 -nodes -keyout server.key -out server.csr \
  -subj "/C=CN/ST=beijing/O=ixiaocong.com/OU=ixiaocong.com/CN=ixiaocong.com"

cat > san.cnf <<'EOF'
[v3_req]
subjectAltName = @alt
[alt]
DNS.1 = ixiaocong.com
DNS.2 = gw.ixiaocong.com
DNS.3 = localhost
IP.1  = 127.0.0.1
IP.2  = 192.168.1.20
EOF

openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial \
  -out server.crt -days 3650 -sha256 -extfile san.cnf -extensions v3_req

# 合并成 Paho/OkHttp 能用的 PEM 链
cat server.crt ca.crt > chain.crt

echo
echo "生成完成："
ls -1 "$DIR"
echo
echo "App 侧：把 assets/test.crt 替换为 ca.crt"
echo "服务端：node server/server.js --tls"
