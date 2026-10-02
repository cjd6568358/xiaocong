#!/bin/sh
# ============================================================================
# ixc-ont.sh —— 小葱智能插座接管（ixc-go）：安装 / 卸载 / 自检
#
# 用法（在光猫上以 root 执行）：
#     sh ixc-ont.sh install    铺规则 + 启动守护（重启光猫后失效）
#     sh ixc-ont.sh status     自检：规则 / 进程 / 插座是否在线
#     sh ixc-ont.sh uninstall  撤销全部改动
#     sh ixc-ont.sh resync     PPPoE 重播、/etc/resolv.conf 变了之后重新对齐
#
# ★ 本脚本【不会】自己去改任何开机脚本 —— 持久化由你自己来，脚本不代劳。
#   要开机自启就把下面这段加进 /f4610u/user_init.sh 的 `exit 0` 之前
#   ★ 注意路径是 /f4610u/user_init.sh，【不是】/f4610u/etc/user_init.sh
#     （/f4610u/etc/ 下确实有 server.crt/server.key，容易看混）
#   （路径按你实际放置脚本的位置改）：
#
#       if [ -x /f4610u/app/ixc-ont.sh ]; then
#           /f4610u/app/ixc-ont.sh install >> /tmp/ixc-ont.log 2>&1 &
#       fi
#
# ============================================================================
# ★★ 本脚本对光猫做的【全部】改动 —— 就这 4 类，除此之外什么都不碰 ★★
# ============================================================================
#
# 【1】网络接口：给 LAN 桥挂一个别名接口
#         ifconfig br0:0 203.0.113.9 netmask 255.255.255.255 up
#      用途：兜底。劫持应答必须用"公网"IP（插座固件拒绝私有网段），
#            挂上别名后这个地址在本机一定可达。
#      撤销：ifconfig br0:0 down
#
# 【2】iptables nat OUTPUT —— 本次接管的【主路径】，按 /etc/resolv.conf 动态生成
#         -p udp -d <光猫自己的每一个 DNS 上游> --dport 53 -j REDIRECT --to-ports 5353
#      用途：插座问光猫要 DNS，光猫自带的 /sbin/proxy 再向上游转发；
#            把"proxy 发给上游的查询"转到本机假 DNS，假 DNS 对
#            ixiaocong.com 应答 203.0.113.9，其余域名照常转发真实上游。
#      ★ 为什么是这条而不是在 PREROUTING 上按插座 MAC 匹配：
#        nat 表只对【新建连接】的首包遍历规则。插座的 DNS 流一旦建好就会
#        一直被刷新（源端口恒为 49153、UDP conntrack 300s 超时），永远等不到
#        "新流"，PREROUTING 上那条规则的计数器就永远不动 —— 实测 9 小时 0 命中。
#        而 proxy 每次向上游查询都是新流，所以走 OUTPUT 这条路必然命中。
#      ★ 副作用可控：只影响"光猫自己发往上游的 DNS 查询"。
#        普通设备的正常域名由 ixc-go 转发到真实上游，解析结果不变。
#      撤销：同规格 -D（本脚本记录在 /tmp/ixc-ont.upstreams，可精确回收）
#
# 【3】iptables nat PREROUTING —— 回环规则，1 条
#         -i br0 ! -d 192.168.1.0/24 -p tcp --dport 8188 -j REDIRECT --to-ports 8188
#      用途：插座拿到 203.0.113.9 后会去连 203.0.113.9:8188，而这台光猫对
#            "LAN 访问自己的公网地址"【不回环】(no hairpin NAT) —— 包在进
#            netfilter 之前就被 ZTE 的转发引擎吃掉，实测连 WAN IP 的
#            80/10022/8188 全部超时。必须 REDIRECT 回本机。
#      ★ 为什么写 "! -d 192.168.1.0/24" 而不是写死 WAN IP：
#        PPPoE 重播会换 WAN IP，写死会让规则静默失效且极难排查。
#        这台光猫的 iptables 1.4.13【内核侧没有 addrtype 匹配】，
#        所以用"目标不是内网段"来等价表达"目标是公网地址"。
#      撤销：同规格 -D
#
# 【4】进程：后台启动 /f4610u/app/ixc-go-arm64_upx，日志 /tmp/ixc-go.log
#      ★ 带守护兜底：进程一旦退出，自动撤销上面【2】的规则。
#        这样即使 ixc-go 挂了，全家 DNS 也立刻恢复正常，只损失插座接管，
#        不会出现"程序死了、规则还在，全家上不了网"。
#
# ============================================================================
# ★★ 明确【不会】碰的东西 ★★
# ============================================================================
#   - 不改 /sbin/proxy（光猫自带 DNS 代理）、不改 /etc/resolv.conf、不改 /etc/hosts
#   - 【不改任何开机脚本】（user_init.sh 等）—— 持久化由你自己来，脚本不代劳
#   - 不重启任何系统服务，不动上面 3 条之外的任何 iptables 链
#   - 不写死插座的 MAC 或 IP：靠"光猫 DNS 上游"这条路径，家里有几台小葱插座
#     就自动接管几台，其它设备完全不受影响
#
# ============================================================================

# ---------------------------- 配置（按需改这里） ----------------------------
IPT=/f4610u/bin/iptables_upx        # 光猫自带 iptables，不在 PATH 里
LD=/f4610u/lib                      # 它依赖 libcommfun.so，必须给这个路径
BIN=/f4610u/app/ixc-go-arm64_upx    # 正式版二进制（调试版放 /tmp）
ETC=/f4610u/etc                     # 证书 / 私钥目录
LOG=/tmp/ixc-go.log                 # rootfs overlay 只剩 8MB，日志一律写 /tmp

LAN_IF=br0                          # LAN 桥
ALIAS_IF=br0:0                      # 兜底别名接口
HIJACK_IP=203.0.113.9               # 劫持应答 IP（RFC 5737 保留段，固件拒绝私有段）
DNS_PORT=5353                       # 假 DNS 监听端口（53 被 /sbin/proxy 占着）
TLS_PORT=8188                       # 假云端 TLS 端口
HTTP_ADDR=192.168.1.1:8080          # 控制用 HTTP（/on /off /status）
CMDFILE=/tmp/xc.cmd                 # 追加一行 JSON 即下发

# ixc-go 转发"其余域名"用的上游。必须与 /etc/resolv.conf 里的 nameserver 不同，
# 否则 ixc-go 自己的转发也会被【2】劫持回来 → 死循环。脚本启动时会自动检查。
IXC_UPSTREAM=223.5.5.5

# 要劫持的域名（逗号分隔，含子域）。
# ★ 只写 ixiaocong.com。曾经这里还带着 allin.dns.army（早期实验用的探针域），
#   但实测日志里出现了 "DNS 劫持 wpad.allin.dns.army" —— 家里有设备在查
#   wpad.*（Web 代理自动发现），被我们劫到 203.0.113.9 就是实实在在的误伤。
#   实验用的探针域绝不能留在生产配置里。
HIJACK_DOMAINS=ixiaocong.com

# LAN 网段（回环规则里的 "! -d"）。留空则自动从 LAN_IF 推算 /24。
LAN_CIDR=

STATE=/tmp/ixc-ont.upstreams        # 记录本脚本加过哪些上游规则，供精确回收
PIDFILE=/tmp/ixc-ont.pid            # 守护进程 pid

# --------------------------------- 工具 ------------------------------------
ipt() { LD_LIBRARY_PATH=$LD:$LD_LIBRARY_PATH "$IPT" "$@"; }

nat_add() { # nat_add <链> <规则...>   —— 幂等插入（已存在则跳过）
    c=$1; shift
    if ipt -t nat -C "$c" "$@" >/dev/null 2>&1; then return 0; fi
    if ipt -t nat -I "$c" 1 "$@" >/dev/null 2>&1; then
        echo "  ✔ 已加 nat $c: $*"
    else
        echo "  ✘ 加规则失败 nat $c: $*" >&2
        return 1
    fi
}

nat_del() { # nat_del <链> <规则...>   —— 删干净（可能有多条重复）
    c=$1; shift
    while ipt -t nat -C "$c" "$@" >/dev/null 2>&1; do
        ipt -t nat -D "$c" "$@" >/dev/null 2>&1 || break
        echo "  ✘ 已删 nat $c: $*"
    done
    return 0
}

lan_cidr() { # 推算 LAN 网段，形如 192.168.1.0/24
    if [ -n "$LAN_CIDR" ]; then echo "$LAN_CIDR"; return; fi
    ifconfig "$LAN_IF" 2>/dev/null | tr '\n' ' ' \
      | sed -n 's/.*inet addr:\([0-9.]*\).*Mask:\([0-9.]*\).*/\1 \2/p' \
      | awk '{split($1,a,"."); split($2,b,".");
              if (b[4]==0 && b[3]==0 && b[2]==0)      printf "%d.0.0.0/8\n",  a[1];
              else if (b[4]==0 && b[3]==0)            printf "%d.%d.0.0/16\n", a[1],a[2];
              else if (b[4]==0)                       printf "%d.%d.%d.0/24\n",a[1],a[2],a[3];
              else                                    printf "%s/%s\n",$1,$2 }'
}

dns_upstreams() { # 光猫自己的 DNS 上游（读 /etc/resolv.conf，跳过 IPv6）
    awk '$1=="nameserver" && $2 !~ /:/ {print $2}' /etc/resolv.conf 2>/dev/null
}

# ------------------------- 【1】别名接口（兜底） ----------------------------
alias_up() {
    if ifconfig "$ALIAS_IF" 2>/dev/null | grep -q "$HIJACK_IP"; then
        echo "  ✔ 别名 $ALIAS_IF = $HIJACK_IP 已存在"
        return 0
    fi
    if ifconfig "$ALIAS_IF" "$HIJACK_IP" netmask 255.255.255.255 up >/dev/null 2>&1; then
        echo "  ✔ 已挂别名 $ALIAS_IF = $HIJACK_IP"
    else
        echo "  ✘ 挂别名失败（不影响主路径，先跳过）" >&2
    fi
}
alias_down() {
    if ifconfig "$ALIAS_IF" 2>/dev/null | grep -q "$HIJACK_IP"; then
        ifconfig "$ALIAS_IF" down 2>/dev/null && echo "  ✘ 已撤别名 $ALIAS_IF"
    fi
}

# ------------------ 【2】nat OUTPUT：劫持光猫的 DNS 上游 --------------------
upstream_rules_del() { # 精确回收本脚本上次加过的
    [ -f "$STATE" ] || return 0
    while read -r ns; do
        [ -n "$ns" ] && nat_del OUTPUT -p udp -d "$ns" --dport 53 \
            -j REDIRECT --to-ports "$DNS_PORT"
    done < "$STATE"
    : > "$STATE"
}

upstream_rules_sync() { # 重读 resolv.conf，先全撤再全加
    upstream_rules_del

    # 防环自检：ixc-go 自己的上游不能出现在劫持名单里
    if dns_upstreams | grep -qx "$IXC_UPSTREAM"; then
        echo "  ⚠ 警告：/etc/resolv.conf 里有 $IXC_UPSTREAM，与 ixc-go 上游相同会死循环。"
        echo "     请把上面的 IXC_UPSTREAM 改成别的（例如 119.29.29.29），该上游已跳过。"
    fi

    for ns in $(dns_upstreams); do
        case "$ns" in
            127.0.0.1|"$IXC_UPSTREAM") echo "  · 跳过 $ns（本地/自身上游，防环）"; continue ;;
        esac
        if nat_add OUTPUT -p udp -d "$ns" --dport 53 -j REDIRECT --to-ports "$DNS_PORT"; then
            echo "$ns" >> "$STATE"
        fi
    done
}

# -------------------- 【3】nat PREROUTING：回环规则 -------------------------
hairpin_up()   { nat_add PREROUTING -i "$LAN_IF" ! -d "$(lan_cidr)" -p tcp \
                     --dport "$TLS_PORT" -j REDIRECT --to-ports "$TLS_PORT"; }
hairpin_down() { nat_del PREROUTING -i "$LAN_IF" ! -d "$(lan_cidr)" -p tcp \
                     --dport "$TLS_PORT" -j REDIRECT --to-ports "$TLS_PORT"; }

# --------------------------- 【4】进程 + 守护 -------------------------------
daemon_loop() {
    while :; do
        upstream_rules_sync                       # 每次（重）启动都重新对齐上游
        # 注意：这里【故意】不写 -plug-mac、不开 -manage-rules。
        #   -plug-mac：按 MAC 在 PREROUTING 铺 DNS 劫持，只在"新建连接"的首包生效，
        #              实测对插座那条早已建立的流 9 小时 0 命中；而且会写死单台 MAC。
        #   -manage-rules：让程序自己管规则，会和本脚本两套机制打架。
        # 规则统一由本脚本管（OUTPUT 主路径 + 回环），天然支持多台插座。
        "$BIN" \
            -cert "$ETC/server.crt" -key "$ETC/server.key" \
            -listen ":$TLS_PORT" -dns ":$DNS_PORT" \
            -hijack-ip "$HIJACK_IP" -dns-domains "$HIJACK_DOMAINS" \
            -dns-upstream "$IXC_UPSTREAM:53" \
            -http "$HTTP_ADDR" -cmdfile "$CMDFILE" \
            -jsonl /tmp/ixc.jsonl \
            >>"$LOG" 2>&1 &
        pid=$!
        echo "[ixc-ont] $(date '+%F %T') ixc-go 启动 pid=$pid"
        wait "$pid"
        rc=$?
        echo "[ixc-ont] $(date '+%F %T') ixc-go 退出 rc=$rc —— 撤销 DNS 上游规则，保全家 DNS"
        upstream_rules_del                        # 关键兜底：程序死了不留坑
        sleep 5
    done
}

start() {
    if [ -f "$PIDFILE" ] && kill -0 "$(cat "$PIDFILE")" 2>/dev/null; then
        echo "  ✔ 守护已在运行 pid=$(cat "$PIDFILE")"; return 0
    fi
    ( daemon_loop >>"$LOG.ont" 2>&1 & echo $! > "$PIDFILE" )
    sleep 2
    echo "  ✔ 守护已启动 pid=$(cat "$PIDFILE" 2>/dev/null)"
}

stop() {
    [ -f "$PIDFILE" ] && { kill "$(cat "$PIDFILE")" 2>/dev/null; rm -f "$PIDFILE"; }
    for p in $(ps 2>/dev/null | grep '[i]xc-go-arm64_upx' | awk '{print $1}'); do
        kill "$p" 2>/dev/null && echo "  ✘ 已停 ixc-go pid=$p"
    done
}

# --------------------------------- 自检 ------------------------------------
status() {
    echo "── 1. 别名接口 ──────────────────────────────"
    ifconfig "$ALIAS_IF" 2>/dev/null | grep 'inet addr' || echo "  （未挂，仅影响兜底）"
    echo
    echo "── 2. nat OUTPUT（主路径：DNS 上游劫持）──────"
    ipt -t nat -L OUTPUT -n -v --line-numbers 2>/dev/null | sed -n '1,6p'
    echo
    echo "── 3. nat PREROUTING（回环 8188）────────────"
    ipt -t nat -L PREROUTING -n -v --line-numbers 2>/dev/null | sed -n '1,4p'
    echo
    echo "── 4. 进程 ──────────────────────────────────"
    ps 2>/dev/null | grep '[i]xc-go-arm64_upx' || echo "  ✘ ixc-go 没在跑"
    [ -f "$PIDFILE" ] && echo "  守护 pid=$(cat "$PIDFILE" 2>/dev/null)"
    echo
    echo "── 5. 插座是否在线 ──────────────────────────"
    n=$(grep -c '有连接进来' "$LOG" 2>/dev/null)
    echo "  累计连接次数: ${n:-0}"
    grep 'DNS 劫持' "$LOG" 2>/dev/null | tail -2
    grep 'snapshot' "$LOG" 2>/dev/null | tail -1 | cut -c1-160
    echo
    echo "── 6. 日志尾 ────────────────────────────────"
    tail -5 "$LOG" 2>/dev/null
}

# --------------------------------- 主流程 ----------------------------------
case "$1" in
    install)
        echo "== 安装 ixc-go 接管 =="
        alias_up
        hairpin_up
        upstream_rules_sync
        start
        echo "== 完成。用 'sh $0 status' 自检 =="
        echo "== 注意：重启光猫后会失效；开机自启请自己加，见本脚本头部注释 =="
        ;;
    uninstall)
        echo "== 撤销 ixc-go 接管 =="
        stop
        upstream_rules_del
        hairpin_down
        alias_down
        echo "== 已全部撤销。若你自己在 user_init.sh 里加过启动行，记得手工删掉 =="
        ;;
    resync)
        echo "== 重新对齐 DNS 上游规则 =="
        upstream_rules_sync
        ;;
    status)
        status
        ;;
    *)
        sed -n '2,60p' "$0"
        ;;
esac
