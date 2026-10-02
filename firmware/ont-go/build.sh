#!/bin/sh
# =============================================================================
#  build.sh —— 编译 + UPX 压缩 ixc-go
#
#  在 x86_64 编译机上跑（本项目用 10.0.0.4，Debian 13 / 8 核 / 15G）。
#  交叉编译到 arm64，纯静态（CGO_ENABLED=0），不依赖目标机任何库。
#
#  产物（落到 ../dist/ 目录，绝不自动上传光猫）：
#    dist/ixc-go-arm64        未压缩（约 6.7MB，排障时用这个）
#    dist/ixc-go-arm64_upx    UPX 压缩（约 2.5MB，【最终部署用这个】）
#
#  【为什么必须 UPX】
#    光猫根分区是 overlay，实测只剩十几 MB 可用（df -h / → 85%）。
#    Go 静态二进制原样 6.7MB，UPX 后约 2.5MB —— 省下的 4MB 对光猫很实在。
#    /f4610u/bin 里 dropbear、scp 也都是 *_upx 后缀，沿这个习惯命名。
#
#  【为什么产物名是 ixc-go-arm64_upx 而不是 ixc-go-arm64】
#    光猫上 /f4610u/bin 是"一眼能看出压过没压过"的约定：
#    dropbear-aarch64-static_upx / scp-aarch64-static_upx / iptables_upx。
#    名字带 _upx，下次谁来看都知道要动它得先 upx -d。
#
#  【UPX 压过的 Go 二进制有什么代价】
#    - 不能用于 pprof / delve 调试（要调就用未压缩版）
#    - 某些加固/杀软会误报加壳
#    光猫上这两件事都不会发生，所以代价为零。
#
#  用法：
#    ./build.sh              # 就地构建，产物落到 ../dist/
#    GO=/usr/local/go/bin/go ./build.sh
# =============================================================================
set -e

# ---------------------------------------------------------------------------
# Go 工具链策略（与同机其他 build.sh 保持一致）
#
# 统一走 /root/build-env.sh —— 一次给齐 PATH + 版本锁定 + 缓存 + 代理：
#     GOTOOLCHAIN=local               禁止 go 自动下载新工具链（版本漂移头号来源）
#     GOCACHE=/root/.cache/go-build   ★ 在磁盘上，不放 /tmp
#     GOPATH=/root/go                 模块只下载一次，全机共享
#     GOPROXY=https://goproxy.cn      国内源
#
# ★ 这里以前是本脚本自己 export 的两个变量：
#       export GOCACHE="${GOCACHE:-/tmp/gocache}"
#       export GOPATH="${GOPATH:-/tmp/gopath}"
#   /tmp 在这台机器上是 tmpfs（内存盘）—— 缓存既占内存、重启即丢，
#   还和其他四个项目分裂成两份 cache。已删除，改由 build-env.sh 统一。
#   ⚠️ 注意：脚本里 export 的优先级【高于】`go env -w`，所以不能靠 go env 兜底。
#
# ★ 本机 sh 是 dash，`.` 内建命令【不】传位置参数 —— 别写
#   `. /root/build-env.sh arm64`（脚本里的 $1 会静默继承调用者的 $1）。
#   要换架构用环境变量形式：`BUILD_ENV_ARCH=amd64 ./build.sh`。
#
# ★ 不要在本脚本里下载/安装 Go：`tar -C /usr/local -xzf ...` 会【跟随
#   /usr/local/go 软链】，静默替换掉整台机器的 Go，影响其他所有项目。
# ---------------------------------------------------------------------------
BUILD_ENV_SH=/root/build-env.sh
if [ -f "$BUILD_ENV_SH" ]; then
    . "$BUILD_ENV_SH"
else
    echo "[build] ERROR: 找不到 $BUILD_ENV_SH" >&2
    echo "[build]        它提供统一的 Go 环境（PATH / GOTOOLCHAIN / GOCACHE / GOPROXY）" >&2
    exit 1
fi

GO="${GO:-/usr/local/go/bin/go}"
[ -x "$GO" ] || GO="$(command -v go)" || true
[ -x "$GO" ] || { echo "[build] 找不到 go，用 GO=/path/to/go ./build.sh 指定" >&2; exit 1; }
command -v upx >/dev/null || { echo "[build] 缺 upx：apt install upx-ucl" >&2; exit 1; }

# 目标平台：本项目只出 arm64（build-env.sh 默认也是 arm64，这里显式写死 = 自文档）
export GOOS=linux GOARCH=arm64 CGO_ENABLED=0

cd "$(dirname "$0")"
mkdir -p ../dist

echo "[build] go   : $("$GO" version)"
echo "[build] upx  : $(upx --version | head -1)"
echo "[build] 目标 : $GOOS/$GOARCH  静态(CGO_ENABLED=0)"
echo "[build] 环境 : GOCACHE=$GOCACHE  GOPATH=$GOPATH  GOTOOLCHAIN=$GOTOOLCHAIN"

echo "[build] go vet …"
"$GO" vet ./... || echo "[build] ⚠️ vet 有告警，不影响构建，继续"

echo "[build] 编译 …"
"$GO" build -trimpath -ldflags "-s -w" -o ../dist/ixc-go-arm64 .

echo "[build] 未压缩: $(ls -l ../dist/ixc-go-arm64 | awk '{print $5}') 字节"

echo "[build] UPX 压缩（--best --lzma）…"
# ★ upx 的 -o 输出【不覆盖】已存在文件：重跑时会报
#   "FileAlreadyExistsException: ../dist/ixc-go-arm64_upx: File exists"，退出码 1。
#   所以每次压缩前必须先删掉旧产物（server-go 的 build.sh 也做了这一步）。
rm -f ../dist/ixc-go-arm64_upx
if ! upx --best --lzma -o ../dist/ixc-go-arm64_upx ../dist/ixc-go-arm64; then
    echo "[build] lzma 不行，退回 --best …" >&2
    rm -f ../dist/ixc-go-arm64_upx
    upx --best -o ../dist/ixc-go-arm64_upx ../dist/ixc-go-arm64
fi
chmod 755 ../dist/ixc-go-arm64_upx

A=$(stat -c%s ../dist/ixc-go-arm64)
B=$(stat -c%s ../dist/ixc-go-arm64_upx)
echo "[build] 压缩后: $B 字节  （压到 $((B * 100 / A))%，省了 $((A - B)) 字节）"

echo "[build] 自检：解压后能否还原（UPX 完整性）"
upx -t ../dist/ixc-go-arm64_upx >/dev/null && echo "[build]   ✅ UPX 自检通过"

echo "[build] 产物:"
ls -l ../dist/ixc-go-arm64 ../dist/ixc-go-arm64_upx
echo
echo "[build] 部署："
echo "  scp ../dist/ixc-go-arm64_upx root@192.168.1.1:/f4610u/bin/ixc-go-arm64_upx"
echo "  （回环规则与开机自启在 /f4610u/user_init.sh 里，用 deploy.py 装）"
