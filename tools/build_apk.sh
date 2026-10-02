#!/bin/sh
# 重建 APK：去掉乐固壳，换成脱壳后的真实 dex + 打补丁的 manifest
#
# 用法：  sh tools/build_apk.sh
# 产物：  build/xiaocong-fixed-unsigned.apk
#
# 依赖：7z（本机有）、openssl（本机有）、node
set -e

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

WORK="build/work"
OUT="build/xiaocong-fixed-unsigned.apk"
rm -rf build/work "$OUT"
mkdir -p "$WORK"

echo "==> 1/6 解包原始 APK"
7z x -y -o"$WORK" xiaocong.apk > /dev/null

echo "==> 2/6 移除签名与乐固壳"
rm -rf "$WORK/META-INF"
rm -f "$WORK/classes.dex"
find "$WORK/lib" -type f \( -name 'libshella*' -o -name 'libshellx*' -o -name 'libxguardian*' \) -delete -print
# 壳常把真实 dex 藏在 assets，一并清掉可能的残留
find "$WORK/assets" -type f -name '*.dex' -delete 2>/dev/null || true

echo "==> 3/6 放入脱壳后的真实 dex"
i=1
for d in dex/com.ixiaocong.smarthome.phone9763076.dex \
         dex/com.ixiaocong.smarthome.phone3073100.dex \
         dex/com.ixiaocong.smarthome.phone1799620.dex \
         dex/com.ixiaocong.smarthome.phone13313100.dex ; do
  if [ "$i" = "1" ]; then
    cp "$d" "$WORK/classes.dex"
  else
    cp "$d" "$WORK/classes$i.dex"
  fi
  echo "    classes$([ "$i" = 1 ] && echo "" || echo "$i").dex  <- $(basename "$d")"
  i=$((i+1))
done

echo "==> 4/6 打补丁 AndroidManifest.xml（application 指向 XcApplication）"
node tools/patch_axml.js "$WORK/AndroidManifest.xml" "$WORK/AndroidManifest.xml.new"
mv "$WORK/AndroidManifest.xml.new" "$WORK/AndroidManifest.xml"

echo "==> 5/6 重新打包"
( cd "$WORK" && 7z a -tzip -mx=9 "$ROOT/$OUT" . > /dev/null )
echo "    $OUT"

echo "==> 6/6 完成（尚未签名）"
ls -la "$OUT"
