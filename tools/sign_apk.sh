#!/bin/sh
# 给 build/xiaocong-fixed-unsigned.apk 签名
#
# 用法：  sh tools/sign_apk.sh
# 产物：  build/xiaocong-fixed.apk（已签名，可直接 adb install）
#
# 说明：
#   - 原 APK 是乐固加固包，重打包后必须重签，否则装不上。
#   - 本机没有 android build-tools（无 apksigner/zipalign），改用 JDK 自带的
#     keytool + jarsigner 做 v1(JAR) 签名。v1 签名对 minSdk 16 有效；
#     resources.arsc 必须保持 STORED（不压缩），否则 Android 7.0+ 会拒绝安装。
set -e

JDK="/c/Users/caojiecn/Downloads/jdk-21.0.12.1+1"
KEYSTORE="build/keystore/xiaocong.jks"
STOREPASS="xiaocong123"
ALIAS="xiaocong"

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

UNSIGNED="build/xiaocong-fixed-unsigned.apk"
ALIGNED="build/xiaocong-aligned.apk"
OUT="build/xiaocong-fixed.apk"

[ -f "$UNSIGNED" ] || { echo "缺少 $UNSIGNED，先跑 tools/build_apk.sh"; exit 1; }

# 首次运行：生成自签名证书（有效期 30 年）
if [ ! -f "$KEYSTORE" ]; then
  echo "==> 生成签名证书 $KEYSTORE"
  mkdir -p build/keystore
  "$JDK/bin/keytool.exe" -genkeypair -v -keystore "$KEYSTORE" \
    -storepass "$STOREPASS" -keypass "$STOREPASS" -alias "$ALIAS" \
    -keyalg RSA -keysize 2048 -validity 10950 \
    -dname "CN=Xiaocong Mod, OU=Dev, O=Xiaocong, L=Unknown, ST=Unknown, C=CN"
fi

echo "==> 1/3 重打包：resources.arsc 用 STORED（不压缩）"
rm -f "$ALIGNED" "$OUT"
cp "$UNSIGNED" "$ALIGNED"
7z d "$ALIGNED" resources.arsc -tzip > /dev/null
( cd build/work && 7z a -tzip -mx=0 "$ROOT/$ALIGNED" resources.arsc > /dev/null )

echo "==> 2/3 jarsigner 签名（v1 / SHA256withRSA）"
"$JDK/bin/jarsigner.exe" -keystore "$KEYSTORE" -storepass "$STOREPASS" \
  -keypass "$STOREPASS" -sigalg SHA256withRSA -digestalg SHA-256 \
  -sigfile CERT -signedjar "$OUT" "$ALIGNED" "$ALIAS"

echo "==> 3/3 校验"
"$JDK/bin/jarsigner.exe" -verify "$OUT"
"$JDK/bin/keytool.exe" -J-Duser.language=en -printcert -jarfile "$OUT" | head -6
ls -la "$OUT"
