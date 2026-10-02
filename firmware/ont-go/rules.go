// rules.go —— 在光猫上自动铺/撤 iptables 规则与接口别名。
//
// 这台光猫（ZTE F4610U / OpenWrt 19.07.7 / 内核 4.1.25）的情况：
//   - netfilter 是活的（/proc/net/ip_tables_names = nat/mangle/filter）
//   - 但 userspace 没有 iptables 命令；好在 /f4610u/bin/iptables_upx 可用，
//     只是需要 LD_LIBRARY_PATH=/f4610u/lib 才能找到 libcommfun.so
//   - 可用 match 有 mac（所以能精确匹配插座 MAC），【没有 addrtype】
//   - 可用 target 有 REDIRECT / DNAT / SNAT / MASQUERADE
//
// 本程序负责的规则（都在 nat PREROUTING）：
//
//	① 插座(按 MAC)去 53 端口的包 → REDIRECT 到本机 DNS 应答器
//
// 本程序【默认不负责】的规则：
//
//	② 去 WAN IP 的 8188 → REDIRECT 回本机（回环规则，见 -hairpin）
//
// ② 为什么搬走了：它常驻在 /f4610u/user_init.sh 里，而且用的是
// 「目标不是内网段」的写法（`! -d 192.168.1.0/24`）而不是写死 WAN IP，
// 所以 PPPoE 重播换 IP 也不会失效 —— 放在开机脚本里比放在本程序里更合适。
// 加 -hairpin 仍然可以让本程序按 WAN IP 铺/撤（保留这条能力以备不时之需）。
package main

import (
	"os"
	"os/exec"
	"strings"
	"time"
)

type ruleMgr struct {
	iptables string
	ldPath   string
	lanIf    string
	plugMAC  string
	wanIf    string
	aliasIf  string
	hijackIP string
	dnsPort  string
	tlsPort  string

	// hairpin=true 时本程序才铺/撤「WAN IP 回环规则」。
	// 默认 false —— 该规则在 /f4610u/user_init.sh 里常驻。
	hairpin bool

	lastWANIP string
}

func runCmd(name string, args ...string) (string, error) {
	c := exec.Command(name, args...)
	out, err := c.CombinedOutput()
	return strings.TrimSpace(string(out)), err
}

func (r *ruleMgr) ipt(args ...string) (string, error) {
	c := exec.Command(r.iptables, args...)
	c.Env = append(os.Environ(), "LD_LIBRARY_PATH="+r.ldPath)
	out, err := c.CombinedOutput()
	return strings.TrimSpace(string(out)), err
}

// ensure 幂等安装一条 nat PREROUTING 规则
func (r *ruleMgr) ensure(spec ...string) {
	check := append([]string{"-t", "nat", "-C", "PREROUTING"}, spec...)
	if _, err := r.ipt(check...); err == nil {
		return // 已存在
	}
	ins := append([]string{"-t", "nat", "-I", "PREROUTING", "1"}, spec...)
	if out, err := r.ipt(ins...); err != nil {
		logf("⚠️  装规则失败: %s | %v", out, err)
	} else {
		logf("✔  已装规则: %s", strings.Join(spec, " "))
	}
}

// drop 删除一条 nat PREROUTING 规则
func (r *ruleMgr) drop(spec ...string) {
	del := append([]string{"-t", "nat", "-D", "PREROUTING"}, spec...)
	if out, err := r.ipt(del...); err != nil {
		if !strings.Contains(out, "No chain") {
			logf("   删规则: %s | %v", out, err)
		}
	} else {
		logf("✘  已删规则: %s", strings.Join(spec, " "))
	}
}

func (r *ruleMgr) dnsSpec() []string {
	s := []string{"-i", r.lanIf}
	if r.plugMAC != "" {
		s = append(s, "-m", "mac", "--mac-source", strings.ToUpper(r.plugMAC))
	}
	return append(s, "-p", "udp", "--dport", "53", "-j", "REDIRECT", "--to-ports", r.dnsPort)
}

func (r *ruleMgr) wanSpec(ip string) []string {
	return []string{"-i", r.lanIf, "-d", ip, "-p", "tcp",
		"--dport", r.tlsPort, "-j", "REDIRECT", "--to-ports", r.tlsPort}
}

// wanIPv4 从 ifconfig 输出里抠出 WAN 口地址（光猫 busybox 的格式：inet addr:1.2.3.4）
func wanIPv4(ifname string) string {
	out, err := runCmd("ifconfig", ifname)
	if err != nil {
		return ""
	}
	for _, line := range strings.Split(out, "\n") {
		if i := strings.Index(line, "inet addr:"); i >= 0 {
			f := strings.Fields(line[i+len("inet addr:"):])
			if len(f) > 0 {
				return f[0]
			}
		}
	}
	return ""
}

// ensureAlias 把劫持 IP 挂到 LAN 桥的别名接口上，让"公网地址"落到本地。
//
// 现在 user_init.sh 里那条「目标不是内网段 → REDIRECT」的回环规则已经能覆盖
// 同样的事情（REDIRECT 会在路由前把目的地址改成本机地址）。这里保留别名是
// **兜底**：万一回环规则没装上（比如有人手工删了、或改成了别的写法），
// 203.0.113.9 至少还是本机可达的，不至于彻底失联。
func (r *ruleMgr) ensureAlias() {
	if r.aliasIf == "" || r.hijackIP == "" {
		return
	}
	if out, err := runCmd("ifconfig", r.aliasIf); err == nil && strings.Contains(out, r.hijackIP) {
		return
	}
	if out, err := runCmd("ifconfig", r.aliasIf, r.hijackIP, "netmask", "255.255.255.255", "up"); err != nil {
		logf("⚠️  挂别名失败: %s | %v", out, err)
	} else {
		logf("✔  已挂别名 %s = %s", r.aliasIf, r.hijackIP)
	}
}

func (r *ruleMgr) dropAlias() {
	if r.aliasIf != "" {
		runCmd("ifconfig", r.aliasIf, "down")
		logf("✘  已撤别名 %s", r.aliasIf)
	}
}

// Setup 启动时铺齐所有规则
func (r *ruleMgr) Setup() {
	r.ensureAlias()
	r.ensure(r.dnsSpec()...)
	if r.hairpin {
		if ip := wanIPv4(r.wanIf); ip != "" {
			r.ensure(r.wanSpec(ip)...)
			r.lastWANIP = ip
		}
	}
	r.dump()
}

// Refresh 周期性跑：WAN IP 变了就把旧规则换掉（PPPoE 重播会换 IP）
func (r *ruleMgr) Refresh() {
	if !r.hairpin {
		return // 回环规则不归本程序管（在 user_init.sh 里，且写法不依赖 WAN IP）
	}
	ip := wanIPv4(r.wanIf)
	if ip == "" || ip == r.lastWANIP {
		return
	}
	if r.lastWANIP != "" {
		r.drop(r.wanSpec(r.lastWANIP)...)
	}
	r.ensure(r.wanSpec(ip)...)
	r.lastWANIP = ip
	logf("★ WAN IP 变为 %s，规则已跟随", ip)
}

// Teardown 退出时清理（别名与规则都撤掉）
func (r *ruleMgr) Teardown() {
	r.drop(r.dnsSpec()...)
	if r.hairpin && r.lastWANIP != "" {
		r.drop(r.wanSpec(r.lastWANIP)...)
	}
	r.dropAlias()
}

func (r *ruleMgr) dump() {
	if out, err := r.ipt("-t", "nat", "-L", "PREROUTING", "-n", "--line-numbers"); err == nil {
		for _, line := range strings.Split(out, "\n") {
			if strings.Contains(line, "REDIRECT") || strings.Contains(line, "Chain") {
				logf("   %s", strings.TrimSpace(line))
			}
		}
	}
}

// StartRefresher 后台每 30s 对齐一次 WAN IP
func (r *ruleMgr) StartRefresher() {
	go func() {
		for range time.Tick(30 * time.Second) {
			r.Refresh()
		}
	}()
}
