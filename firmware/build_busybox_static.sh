#!/bin/bash
#
# 交叉编译【静态 · 全功能】busybox for aarch64
# 目标机：中兴 F4610U 光猫（ZX279131 Cortex-A53×2 / 内核 4.1.25 aarch64）
#
# 背景：
#   光猫自带的 busybox 是 1.34.1，只编进去 120 个 applet。
#   日常想用的 sha256sum / base64 / xxd / od / bc / flock / nsenter / setpriv
#   一概没有。本脚本编出 418 个 applet 的完整版，静态链接，丢上去就能跑。
#
#   实测（2026-10-02，真机 CU_UFW / 内核 4.1.25）：
#     系统自带 120 个 → 自编 418 个（新增 300）
#     105 条 applet 调用全部返回 0，光猫 load average 无异常
#     只有 2 个 applet 缺失：lock / netmsg —— 这两个是中兴在厂商 busybox 里
#     自己加的（usage 文案都不是 busybox 风格），上游没有，编不出来也不影响。
#
#   用法提醒：新二进制只有静态链接、没有 interpreter 依赖，直接
#     scp 上去 → chmod +x → ./busybox-full <applet>
#   想按裸命令名用就先 `./busybox-full --install -s /tmp/bbin` 造软链。
#
# 与 build_dropbear_static.sh 同一套路：musl 交叉工具链 + 静态 + readelf 校验。
#
# 为什么必须是 musl 而不是 gcc-aarch64-linux-gnu：
#   光猫用户态是 armhf + glibc 2.26 的容器，位数/ABI 都对不上；
#   静态二进制没有 interpreter，aarch64 内核直接加载，绕开整个用户态。
#
# ---------------------------------------------------------------------------
# ⚠️ musl 编译 busybox 的 6 个坑（本脚本已全部绕开，改动前请先读）
#
#   1. musl 没有 glibc 的 GNU regex 扩展 → 必须关 CONFIG_EXTRA_COMPAT，
#      否则 findutils/grep.c 与 editors/vi.c 报 RE_TRANSLATE_TYPE /
#      re_syntax_options 未定义。
#   2. 同上 → 关 CONFIG_FEATURE_VI_REGEX_SEARCH。
#   3. musl 没有 rpc/rpc.h → 关 CONFIG_FEATURE_INETD_RPC。
#   4. GCC 11 的 -Werror=format-truncation / -Werror=deprecated-declarations
#      会打断 miscutils/devfsd.c、networking/udhcp/d6_dhcpc.c
#      → CONFIG_EXTRA_CFLAGS="-Wno-error"。
#   5. musl 的 <sys/user.h> 不定义 PAGE_SIZE，scripts/generate_BUFSIZ.sh --post
#      会以 rc=1 退出 → 关 CONFIG_FEATURE_USE_BSS_TAIL。
#   6. 链接期找不到 libsanitizer.spec → 关 CONFIG_DEBUG_SANITIZE。
#
#   ※ 顺带纠正一个常见误解：busybox **没有** CONFIG_OPTIMIZE_FOR_SIZE 这个符号。
#     优化级别由 Makefile.flags 按 CONFIG_DEBUG 二选一：
#       DEBUG=n → -Oz（不支持则退 -Os，再退 -O2）
#       DEBUG=y → -O0 -g   ← 产物又大又慢，别开
#     所以本脚本显式写 CONFIG_DEBUG=n，并在编译前断言实际用的是 -Oz/-Os。
#
# ⚠️ 绝对不要用 `make CFLAGS=...` 传参：那会把 busybox 自己的一组编译开关
#    整个冲掉。要加编译选项用 CONFIG_EXTRA_CFLAGS，交叉前缀用
#    CONFIG_CROSS_COMPILER_PREFIX。
# ---------------------------------------------------------------------------
#
# 用法：./build_busybox_static.sh
# 产出：./busybox-full-aarch64          （未压缩）
#       ./busybox-full-aarch64_upx      （UPX 压缩，★ 上光猫就用这个）
#
# 部署（注意 -O）：
#   scp -O busybox-full-aarch64_upx root@<光猫>:/tmp/busybox-full_upx
#   ssh root@<光猫> 'chmod 755 /tmp/busybox-full_upx && /tmp/busybox-full_upx --list | wc -l'
#
#   ⚠️ 为什么要 scp -O：光猫上跑的是 dropbear，【没有 sftp-server】。
#      OpenSSH 9.0 起默认改用 SFTP 协议走 scp，连这种机器会直接
#      "subsystem request failed / EOF during negotiation"。
#      -O 强制回退到老式的 SCP 协议（远端 exec 一个 scp -t/-f），才能传。
#
# 为什么必须 UPX：光猫 rootfs 是 overlay，实测只剩 8.5 MB（已用 89%）。
#   busybox 未压缩 1.25 MB → UPX 后 ~0.56 MB，省一半以上。
#   光猫 /f4610u/bin 里所有第三方二进制都带 _upx 后缀（iptables_upx、
#   dropbear-aarch64-static_upx…），跟着这个命名习惯走。
#
# 踩过的坑（写给下一个改这个脚本的人）：
#   * 不要用 `head` 之类会提前关管道的命令去截 build 脚本自己的输出——
#     一旦给 bash 发 SIGPIPE，脚本会当场死掉，看起来像构建失败。
#   * 用 SSH 库读远端命令输出时，不要用「远端已退出且当前无数据」当结束条件，
#     最后一块数据常常还在路上，会【静默截断】。要等 EOF。
#     （我就因此误判过「busybox 源码树缺了所有子目录 Config.in」，实际有 25 个。）
#   * UPX 会拒压【没有可执行位】的文件：CantPackException: file not executable。
#     从 Windows / 某些 umask 下拷过来的二进制常常是 644，必须先 chmod 755。
#   * UPX 压完一定要 `upx -t` 自检。压缩本身返回 0 不代表产物能跑。
#
set -euo pipefail

BUSYBOX_VER="1.36.1"
TRIPLET="aarch64-linux-musl"
TOOLCHAIN_DIR="$HOME/.cache/musl-cross"

log() { printf '[build] %s\n' "$*"; }
die() { printf '[build] 错误: %s\n' "$*" >&2; exit 1; }

# 产物写到【调用者所在目录】。脚本中段会 cd 进源码树，
# 相对路径会落到 mktemp 临时目录里、随 trap 一起被删。
CALLER_PWD="$PWD"
OUT="$CALLER_PWD/busybox-full-aarch64"
OUT_UPX="${OUT}_upx"
BUILD_LOGS="$CALLER_PWD/busybox-build-logs"

for c in curl tar make bzip2; do
    command -v "$c" >/dev/null || die "缺 $c：apt install curl tar make bzip2"
done

WORK="$(mktemp -d)"
trap 'rm -rf "$WORK"' EXIT

# ---------- 1. 工具链 ----------
TC_BIN="$TOOLCHAIN_DIR/$TRIPLET-cross/bin"
CC="$TC_BIN/$TRIPLET-gcc"

if [ ! -x "$CC" ]; then
    log "下载 musl 交叉工具链（约 100MB，仅首次）..."
    mkdir -p "$TOOLCHAIN_DIR"
    ok=0
    for url in \
        "https://musl.cc/$TRIPLET-cross.tgz" \
        "https://more.musl.cc/11.2.1/x86_64-linux-musl/$TRIPLET-cross.tgz"
    do
        log "  尝试 $url"
        if curl -fL --max-time 900 -o "$TOOLCHAIN_DIR/tc.tgz" "$url" 2>/dev/null \
           && tar xzf "$TOOLCHAIN_DIR/tc.tgz" -C "$TOOLCHAIN_DIR" 2>/dev/null; then
            ok=1; log "  ✅ 完成"; break
        fi
    done
    [ "$ok" = 1 ] || die "工具链下载失败。
手工下载 $TRIPLET-cross.tgz 解压到 $TOOLCHAIN_DIR 后重跑：
  mkdir -p $TOOLCHAIN_DIR && tar xzf $TRIPLET-cross.tgz -C $TOOLCHAIN_DIR"
fi

[ -x "$CC" ] || die "工具链不完整：$CC 不存在"
log "编译器: $("$CC" --version | head -1)"

# ---------- 2. 源码 ----------
log "下载 busybox $BUSYBOX_VER 源码..."
cd "$WORK"
ok=0
for url in \
    "https://busybox.net/downloads/busybox-$BUSYBOX_VER.tar.bz2" \
    "https://busybox.net/downloads/busybox-$BUSYBOX_VER.tar.gz"
do
    case "$url" in
        *.bz2) fn=bb.tar.bz2; dec="tar xjf bb.tar.bz2" ;;
        *.gz)  fn=bb.tar.gz;  dec="tar xzf bb.tar.gz"  ;;
    esac
    log "  尝试 $url"
    if curl -fL --max-time 300 -o "$fn" "$url" 2>/dev/null && $dec 2>/dev/null; then
        ok=1; break
    fi
done
[ "$ok" = 1 ] || die "源码下载失败"
cd "busybox-$BUSYBOX_VER"

# ---------- 3. 配置 ----------
log "make defconfig ..."
make defconfig >/dev/null

# 幂等改写 .config：先删掉该符号的旧行，再按需追加新行。
cfg() {
    local sym="$1" val="$2"
    sed -i -e "/^CONFIG_${sym}=/d" -e "/^# CONFIG_${sym} is not set\$/d" .config
    if [ "$val" = "n" ]; then
        printf '# CONFIG_%s is not set\n' "$sym" >> .config
    else
        printf 'CONFIG_%s=%s\n' "$sym" "$val" >> .config
    fi
}

# 开关类
cfg STATIC y
cfg DEBUG n              # ← 关掉才会走 -Oz/-Os（见文件头 ※）
cfg EXTRA_COMPAT n
cfg FEATURE_VI_REGEX_SEARCH n
cfg FEATURE_INETD_RPC n
cfg DEBUG_SANITIZE n
cfg FEATURE_USE_BSS_TAIL n
# 字符串类（注意值里带引号，写进 .config 才合法）
cfg CROSS_COMPILER_PREFIX "\"$TRIPLET-\""
cfg EXTRA_CFLAGS '"-Wno-error"'

# defconfig 里默认是 n、但对一台光猫/路由器确实有用的 applet，显式打开。
# 符号名从各 .c 文件里的 `//config:config XXX` 块确认过，不是猜的。
#   闪存工具（MTD）—— 备份/写回固件分区要用，这是光猫上最值钱的一组
#   NANDWRITE NANDDUMP  → nanddump/nandwrite（NAND 读/写）
#   FLASHCP FLASH_ERASEALL FLASH_LOCK FLASH_UNLOCK → NOR 擦写锁
#   其它
#   BBCONFIG  → 打印本二进制的编译配置，事后审计用
#   AR        → 打包/解包 .a/.deb
#   INOTIFYD  → 文件变化触发命令（盯配置文件的利器）
#   RFKILL    → 开关 WiFi 射频
#   NETCAT    → nc 的经典别名
#   READAHEAD FBSET TUNE2FS MKFS_REISER LZOPCAT UNLZOP UNCOMPRESS NUKE MINIPS
EXTRA_APPLETS="
NANDWRITE NANDDUMP FLASHCP FLASH_ERASEALL FLASH_LOCK FLASH_UNLOCK
BBCONFIG AR INOTIFYD RFKILL NETCAT READAHEAD FBSET TUNE2FS
MKFS_REISER LZOPCAT UNLZOP UNCOMPRESS NUKE MINIPS
"
for a in $EXTRA_APPLETS; do cfg "$a" y; done

# 让 Kconfig 收敛依赖（关掉某项后它的子项也会自动跟着关）
log "make oldconfig（收敛依赖）..."
make oldconfig < /dev/null > "$WORK/oldconfig.log" 2>&1 \
    || { tail -20 "$WORK/oldconfig.log" >&2; die "oldconfig 失败"; }

# 复核：把关键项的真实落地情况打出来（非致命，但必须看得见）
log "关键配置落地情况："
miss=0
for s in STATIC DEBUG CROSS_COMPILER_PREFIX EXTRA_CFLAGS \
         EXTRA_COMPAT FEATURE_VI_REGEX_SEARCH FEATURE_INETD_RPC \
         DEBUG_SANITIZE FEATURE_USE_BSS_TAIL; do
    line="$(grep -m1 -E "^(CONFIG_$s=|# CONFIG_$s is not set\$)" .config || true)"
    if [ -z "$line" ]; then
        printf '  ⚠️  %-28s 不存在（该版本没这个符号，忽略）\n' "CONFIG_$s"
        miss=$((miss+1))
    else
        printf '  ✅ %-28s %s\n' "CONFIG_$s" "${line#CONFIG_}"
    fi
done
grep -q '^CONFIG_STATIC=y$' .config            || die "CONFIG_STATIC 没写进去，会编出动态链接的产物"
grep -q "^CONFIG_CROSS_COMPILER_PREFIX=\"$TRIPLET-\"\$" .config \
                                               || die "交叉前缀没写进去"
grep -q '^# CONFIG_DEBUG is not set$' .config  || die "CONFIG_DEBUG 没关掉，会退化成 -O0 -g"
log "配置就绪（$miss 项在本版本中不存在，属正常）"

# 复核追加的 applet 是否真的启用了。
# 这里不能只看 cfg 写过就算数：符号名写错、或依赖项没满足，oldconfig 都会悄悄丢掉它。
noton=""
for a in $EXTRA_APPLETS; do
    grep -q "^CONFIG_$a=y\$" .config || noton="$noton $a"
done
if [ -n "$noton" ]; then
    log "⚠️ 以下追加项未启用（该版本无此符号 / 依赖未满足）:$noton"
    log "   不影响构建，但产物里会少这几个 applet"
else
    log "追加 applet 全部启用 ✅（共 $(printf '%s' "$EXTRA_APPLETS" | wc -w) 个）"
fi

# ---------- 4. 编译 ----------
export PATH="$TC_BIN:$PATH"
mkdir -p "$BUILD_LOGS"
BUILD_LOG="$BUILD_LOGS/make.log"

JOBS="$(nproc 2>/dev/null || echo 4)"

# 先单独编译一个目标文件并打印完整命令行，断言优化级别真的是 -Oz/-Os。
# （busybox 的构建默认是静默的，日志里只有 "CC xxx.o"，看不到 -O 级别，
#   所以必须显式用 V=1 探一次，否则 CONFIG_DEBUG 没关也发现不了。）
#
# ⚠️ 必须先把范围限定在交叉编译器的命令行上再取 -O。
#    直接 grep -m1 -oE '\-O[0-9sz]+' 会抓到 HOSTCC（x86 宿主工具）那行的 -O2，
#    得到假阳性——这个坑踩过一次。
OPTDUMP="$(make -j1 V=1 applets/applets.o 2>&1 || true)"
OPTLINE="$(printf '%s\n' "$OPTDUMP" | grep -- "$TRIPLET-gcc" | grep -m1 -oE -- '-O[0-9sz]+' || true)"
case "$OPTLINE" in
    -Oz|-Os) log "  优化级别: $OPTLINE ✅" ;;
    "")      printf '%s\n' "$OPTDUMP" | tail -20 | sed 's/^/    /' >&2
             die "交叉编译 applets/applets.o 失败，取不到优化级别" ;;
    *)       printf '%s\n' "$OPTDUMP" | grep -- "$TRIPLET-gcc" | head -1 | sed 's/^/    /' >&2
             die "优化级别异常: $OPTLINE（应为 -Oz/-Os，出现 -O0 说明 CONFIG_DEBUG 没关掉）" ;;
esac

log "编译中（-j$JOBS）..."
if ! make -j"$JOBS" > "$BUILD_LOG" 2>&1; then
    echo "--- 错误行 ---" >&2
    grep -nE 'error:|Error [0-9]|undefined reference' "$BUILD_LOG" | head -30 | sed 's/^/  /' >&2
    echo "--- 日志尾部 ---" >&2
    tail -25 "$BUILD_LOG" >&2
    log "完整日志: $BUILD_LOG" >&2
    die "编译失败"
fi
log "  编译日志: $BUILD_LOG"
[ -x busybox ] || die "make 返回 0 但没产出 ./busybox"

# ---------- 5. 校验 ----------
# 全部用 readelf 判断（musl 工具链自带）。不用 strings——它在 binutils 包里，
# 目标机器上不一定装了，缺了会让校验静默短路、形同虚设。
READELF="$TC_BIN/$TRIPLET-readelf"
[ -x "$READELF" ] || die "找不到 $READELF"

log "校验 busybox ($(du -h busybox | cut -f1))"
"$READELF" -h busybox | grep -E 'Class|Machine|Type:' | sed 's/^/  /'

# 静态的判定标准：没有 PT_INTERP 段。
# 注意 Type 是 DYN 不代表动态链接——构建带 -pie，静态 PIE 同样无 interpreter。
if "$READELF" -l busybox | grep -q 'INTERP'; then
    die "❌ 存在 INTERP 段 → 动态链接"
fi
NEEDED="$("$READELF" -d busybox 2>/dev/null | grep -c 'NEEDED' || true)"
if [ "${NEEDED:-0}" -gt 0 ]; then
    "$READELF" -d busybox | grep 'NEEDED' | sed 's/^/  /' >&2
    die "❌ 依赖 $NEEDED 个共享库 → 动态链接"
fi
log "  ✅ 无 INTERP 段、无 NEEDED 依赖 → 静态链接"

if ./busybox --list >/dev/null 2>&1; then
    log "  applet 数: $(./busybox --list | wc -l)"
else
    log "  applet 数: 本机(x86)跑不了这个二进制，到光猫上执行"
    log "            /tmp/busybox-full --list | wc -l 确认"
fi

# ---------- 6. 输出 ----------
cp busybox "$OUT"
chmod 755 "$OUT"
log "产物: $OUT ($(du -h "$OUT" | cut -f1))"

# ---------- 7. UPX 压缩 ----------
# 光猫 rootfs 是 overlay，实测只剩 8.5 MB（已用 89%），未压缩的 busybox 太占地。
# 1.25 MB → ~0.56 MB。/f4610u/bin 里所有第三方二进制都带 _upx 后缀，跟着走。
#
# 没有装 upx 不算失败 —— 静默跳过，只留未压缩版。
if command -v upx >/dev/null 2>&1; then
    log "UPX 版本: $(upx --version 2>&1 | head -1 | grep -oE '[0-9]+\.[0-9]+.*' || echo unknown)"

    # ★ 不要就地压（upx in-place 容易和 -o 打架），先拷一份再压到目标名
    SRC_UPX="$WORK/busybox-for-upx"
    cp "$OUT" "$SRC_UPX"
    # ★ UPX 拒压【没有可执行位】的文件：CantPackException: file not executable。
    #   cp 之后权限可能继承成 644（从 Windows 拷过来时尤其常见），必须补 755。
    chmod 755 "$SRC_UPX"

    log "UPX 压缩中（--best --lzma）..."
    if ! upx --best --lzma -o "$OUT_UPX" "$SRC_UPX" 2>&1 | sed 's/^/  /'; then
        log "  ⚠️ --lzma 失败，回退到 upx --best"
        cp "$OUT" "$SRC_UPX"; chmod 755 "$SRC_UPX"
        if ! upx --best -o "$OUT_UPX" "$SRC_UPX" 2>&1 | sed 's/^/  /'; then
            log "  ⚠️ UPX 彻底失败，保留未压缩版 $OUT"
            rm -f "$OUT_UPX"
        fi
    fi

    if [ -f "$OUT_UPX" ]; then
        chmod 755 "$OUT_UPX"
        # ★ 压缩返回 0 不代表产物能跑，必须自检
        if upx -t "$OUT_UPX" 2>&1 | sed 's/^/  /'; then
            S0=$(stat -c %s "$OUT")
            S1=$(stat -c %s "$OUT_UPX")
            RATIO=$(awk -v a="$S1" -v b="$S0" 'BEGIN{printf "%.2f", a*100/b}')
            log "  ✅ UPX 完成: $S0 → $S1 字节（${RATIO}%，省了 $(du -h "$OUT" | cut -f1) → $(du -h "$OUT_UPX" | cut -f1)）"
            log "产物(推荐): $OUT_UPX"
        else
            log "  ❌ UPX 自检没过，丢弃压缩版，只用未压缩版"
            rm -f "$OUT_UPX"
        fi
    fi
else
    log "⚠️ 系统里没有 upx，跳过压缩（apt install upx-ucl 或 upx）"
    log "   （本机 upx 4.2.4 在 /usr/bin/upx）"
fi

log "部署: scp -O $(basename "${OUT_UPX:-$OUT}") root@<光猫>:/tmp/ && chmod 755 /tmp/<文件名>"
log "      注意 scp 要带 -O：光猫 dropbear 没有 sftp-server，新版 OpenSSH 默认协议连不上"
