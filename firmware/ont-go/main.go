// ixc-go —— 在光猫（或任意 Linux/arm64 主机）上常驻，把小葱插座重新接管回来。
//
// 一个进程干三件事：
//  1. TLS 1.1 + 静态 RSA 的假云端，监听 8188（冒充官方 MQTT）
//  2. DNS 应答器，把 iot.ixiaocong.com 劫持到我们的公网 IP（其余转发上游）
//  3. 铺 iptables 规则（DNS 劫持 + br0:0 别名），-manage-rules 开启
//
// ⚠️ 「WAN IP 回环规则」默认【不由本程序管理】，见 -hairpin。
//    它常驻在 /f4610u/user_init.sh 里（理由写在那个文件的注释里）。
//
// 编译（产物名按光猫 /f4610u/bin 的命名习惯，带 _upx 后缀）：
//
//	GOOS=linux GOARCH=arm64 CGO_ENABLED=0 go build -trimpath -ldflags "-s -w" \
//	    -o ixc-go-arm64 .
//	upx --best --lzma -o ixc-go-arm64_upx ixc-go-arm64
//
// 光猫上跑（ZTE F4610U / OpenWrt 19.07.7 / arm64）：
//
//	/f4610u/bin/ixc-go-arm64_upx -cert /f4610u/etc/server.crt -key /f4610u/etc/server.key \
//	    -dns :5353 -manage-rules -cmdfile /tmp/xc.cmd
//
// 控制：
//
//	echo '{"command":{"switch":1}}' >> /tmp/xc.cmd      # 开
//	echo '{"command":{"switch":0}}' >> /tmp/xc.cmd      # 关
//	curl 'http://192.168.1.1:8080/on'                   # 或用内置 HTTP 接口
//
// 帮助：ixc-go-arm64_upx -h
package main

import (
	"crypto/tls"
	"encoding/json"
	"flag"
	"fmt"
	"net/http"
	"os"
	"os/signal"
	"strings"
	"syscall"
	"time"
)

var (
	listenAddr = flag.String("listen", ":8188", "TLS 假云端监听地址")
	certFile   = flag.String("cert", "", "RSA 证书（必须是 RSA，插座不支持 ECDHE）")
	keyFile    = flag.String("key", "", "私钥")
	cmdFile    = flag.String("cmdfile", "", "命令文件：追加一行 JSON 即下发")
	cmdTopic   = flag.String("topic", "control", "下发用的 MQTT 主题")
	jsonlPath  = flag.String("jsonl", "", "结构化样本落盘路径（可选）")
	onConnect  = flag.String("on-connect", "", "每次连接建立后自动下发的 JSON（可选）")
	httpAddr   = flag.String("http", "", "控制用 HTTP 监听地址（如 192.168.1.1:8080，留空关闭）")
	verbose    = flag.Bool("v", true, "打印详细日志")

	dnsListen   = flag.String("dns", "", "DNS 应答器监听地址（如 :5353，留空关闭）")
	dnsDomains  = flag.String("dns-domains", "ixiaocong.com", "要劫持的域名（逗号分隔，含子域）")
	dnsUpstream = flag.String("dns-upstream", "223.5.5.5:53", "其余查询转发的上游")
	hijackIP    = flag.String("hijack-ip", "203.0.113.9", "劫持应答用的公网 IP（固件拒绝私有网段）")

	manageRules = flag.Bool("manage-rules", false, "自动铺/撤 iptables 规则与接口别名")
	hairpin     = flag.Bool("hairpin", false,
		"由本程序铺/撤「WAN IP 回环规则」。默认关：该规则常驻在 /f4610u/user_init.sh 里，"+
			"因为它用「目标不是内网段」的写法，PPPoE 重播换 IP 也不会失效")
	iptablesBin = flag.String("iptables", "/f4610u/bin/iptables_upx", "iptables 可执行文件")
	ldPath      = flag.String("ldpath", "/f4610u/lib", "iptables 依赖库路径（libcommfun.so）")
	lanIf       = flag.String("lan-if", "br0", "局域网桥接口")
	wanIf       = flag.String("wan-if", "ppp0", "WAN 接口（只有 -hairpin 时才用到）")
	aliasIf     = flag.String("alias-if", "br0:0", "挂劫持 IP 的别名接口")
	plugMAC     = flag.String("plug-mac", "B4:E6:2D:3A:6E:7C", "插座 MAC（只劫持它，不影响其他设备）")
)

// usage 是 -h / -help / 参数不全时的帮助文本。
// 光猫上没有 man/README 可查，帮助必须能自解释 —— 直接给一条可复制的完整命令行。
func usage() {
	w := flag.CommandLine.Output()
	fmt.Fprintf(w, `ixc-go —— 在光猫上冒充小葱插座的原厂云端（DNS 劫持 + TLS 1.1 假云端）

用法:
  ixc-go-arm64_upx -cert <crt> -key <key> [选项]

必填:
  -cert <文件>    RSA 证书（必须是 RSA；插座只支持静态 RSA，不支持 ECDHE）
  -key  <文件>    对应的私钥

光猫上的标准姿势（可直接复制）:
  /f4610u/bin/ixc-go-arm64_upx \
      -cert /f4610u/etc/server.crt -key /f4610u/etc/server.key \
      -listen :8188 \
      -dns :5353 -hijack-ip 203.0.113.9 \
      -dns-domains ixiaocong.com \
      -plug-mac B4:E6:2D:3A:6E:7C \
      -manage-rules -lan-if br0 -alias-if br0:0 \
      -http 192.168.1.1:8080 -jsonl /tmp/ixc.jsonl

控制插座:
  curl http://192.168.1.1:8080/on           开
  curl http://192.168.1.1:8080/off          关
  curl http://192.168.1.1:8080/status       看插座在不在线
  curl 'http://192.168.1.1:8080/raw?c=<JSON>'   原样下发
  echo '{"command":{"switch":1}}' >> /tmp/xc.cmd   # 追加一行即下发（需 -cmdfile）

两个容易踩的点:
  * LAN 设备访问【光猫自己的 WAN IP】在光猫上不回环（no hairpin NAT），
    必须在 nat PREROUTING 里 REDIRECT 回本机，否则插座永远连不上。
    这条规则默认【不由本程序管理】，它常驻在 /f4610u/user_init.sh 里；
    想让本程序自己铺/撤就加 -hairpin。
  * DNS 劫持规则按 -plug-mac 只匹配插座那一台设备，其他设备零影响。

选项:
`)
	flag.PrintDefaults()
}

var jsonlFH *os.File

func logf(format string, a ...interface{}) {
	if !*verbose {
		return
	}
	fmt.Printf("[%s] %s\n", time.Now().Format("15:04:05"), fmt.Sprintf(format, a...))
}

func rec(kind string, kv map[string]interface{}) {
	if jsonlFH == nil {
		return
	}
	mu.Lock()
	defer mu.Unlock()
	m := map[string]interface{}{"wall": time.Now().Format("2006-01-02T15:04:05"), "kind": kind}
	for k, v := range kv {
		m[k] = v
	}
	b, _ := json.Marshal(m)
	jsonlFH.Write(append(b, '\n'))
	jsonlFH.Sync()
}

// ---------------------------------------------------------------- 命令文件轮询

func watchCmdFile() {
	var offset int64
	for {
		time.Sleep(time.Second)
		fi, err := os.Stat(*cmdFile)
		if err != nil {
			continue
		}
		if fi.Size() <= offset {
			if fi.Size() < offset {
				offset = 0
			}
			continue
		}
		f, err := os.Open(*cmdFile)
		if err != nil {
			continue
		}
		f.Seek(offset, 0)
		data := make([]byte, fi.Size()-offset)
		n, _ := f.Read(data)
		f.Close()
		offset = fi.Size()
		for _, line := range strings.Split(string(data[:n]), "\n") {
			line = strings.TrimSpace(line)
			if line == "" {
				continue
			}
			logf("◆ 命令文件新行: %s", line)
			if PublishToAll(line) == 0 {
				logf("   (当前没有连接，命令被丢弃)")
			}
		}
	}
}

// ---------------------------------------------------------------- 控制 HTTP

func startHTTP(addr string) {
	mux := http.NewServeMux()
	handler := func(raw string) {
		n := PublishToAll(raw)
		logf("◆ HTTP 下发 %s（%d 个会话）", raw, n)
	}
	mux.HandleFunc("/on", func(w http.ResponseWriter, r *http.Request) {
		handler(`{"command":{"switch":1}}`)
		fmt.Fprintln(w, "switch=1 已下发")
	})
	mux.HandleFunc("/off", func(w http.ResponseWriter, r *http.Request) {
		handler(`{"command":{"switch":0}}`)
		fmt.Fprintln(w, "switch=0 已下发")
	})
	mux.HandleFunc("/raw", func(w http.ResponseWriter, r *http.Request) {
		c := r.URL.Query().Get("c")
		if c == "" {
			http.Error(w, "缺少 c 参数", 400)
			return
		}
		handler(c)
		fmt.Fprintf(w, "已下发: %s\n", c)
	})
	mux.HandleFunc("/status", func(w http.ResponseWriter, r *http.Request) {
		var remotes []string
		sessions.Range(func(k, _ interface{}) bool {
			remotes = append(remotes, k.(string))
			return true
		})
		fmt.Fprintf(w, "在线会话 %d 个: %s\n", len(remotes), strings.Join(remotes, ", "))
	})
	go func() {
		logf("控制 HTTP 就绪  http://%s/on  /off  /status", addr)
		if err := http.ListenAndServe(addr, mux); err != nil {
			logf("HTTP 失败: %v", err)
		}
	}()
}

// ---------------------------------------------------------------- main

func main() {
	flag.Usage = usage
	flag.Parse()

	if *jsonlPath != "" {
		if f, err := os.OpenFile(*jsonlPath, os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0644); err == nil {
			jsonlFH = f
			defer f.Close()
		}
	}

	if *certFile == "" || *keyFile == "" {
		usage()
		os.Exit(2)
	}
	cert, err := tls.LoadX509KeyPair(*certFile, *keyFile)
	if err != nil {
		fmt.Fprintf(os.Stderr, "加载证书失败: %v\n", err)
		os.Exit(1)
	}

	cfg := &tls.Config{
		Certificates: []tls.Certificate{cert},
		MinVersion:   tls.VersionTLS10,
		MaxVersion:   tls.VersionTLS11,
		// 插座只提供静态 RSA 套件；Go 把它们归入 InsecureCipherSuites，必须显式列出
		CipherSuites: []uint16{
			tls.TLS_RSA_WITH_AES_256_CBC_SHA,
			tls.TLS_RSA_WITH_AES_128_CBC_SHA,
			tls.TLS_RSA_WITH_3DES_EDE_CBC_SHA,
		},
	}

	// ---- 网络规则
	var rm *ruleMgr
	if *manageRules {
		rm = &ruleMgr{
			iptables: *iptablesBin, ldPath: *ldPath, lanIf: *lanIf, plugMAC: *plugMAC,
			wanIf: *wanIf, aliasIf: *aliasIf, hijackIP: *hijackIP,
			dnsPort: strings.TrimPrefix(*dnsListen, ":"), tlsPort: strings.TrimPrefix(*listenAddr, ":"),
			hairpin: *hairpin,
		}
		if *dnsListen == "" {
			rm.dnsPort = "53"
		}
		rm.Setup()
		rm.StartRefresher()
		defer rm.Teardown()
		if !*hairpin {
			logf("   回环规则不由本程序管理（常驻在 /f4610u/user_init.sh，加 -hairpin 可改回来）")
		}
	}

	// ---- DNS 劫持
	if *dnsListen != "" {
		ds, err := newDNS(*dnsListen, *hijackIP, *dnsDomains, *dnsUpstream)
		if err != nil {
			fmt.Fprintf(os.Stderr, "DNS 启动失败: %v\n", err)
			os.Exit(1)
		}
		go ds.Run()
		defer ds.Close()
	}

	// ---- TLS 假云端
	ln, err := tls.Listen("tcp", *listenAddr, cfg)
	if err != nil {
		fmt.Fprintf(os.Stderr, "监听失败: %v\n", err)
		os.Exit(1)
	}
	logf("ixc-go 就绪  TLS=%s  证书=%s", *listenAddr, *certFile)
	logf("   设备ID=%s  AppClientID=%s", deviceID, appClientID)

	if *cmdFile != "" {
		go watchCmdFile()
		logf("   命令文件=%s（追加一行 JSON 即下发）", *cmdFile)
	}
	if *httpAddr != "" {
		startHTTP(*httpAddr)
	}

	// ---- 优雅退出：撤规则
	sig := make(chan os.Signal, 1)
	signal.Notify(sig, syscall.SIGINT, syscall.SIGTERM)
	go func() {
		<-sig
		logf("收到退出信号，清理…")
		if rm != nil {
			rm.Teardown()
		}
		os.Exit(0)
	}()

	for {
		conn, err := ln.Accept()
		if err != nil {
			logf("accept 失败: %v", err)
			time.Sleep(200 * time.Millisecond)
			continue
		}
		go handleConn(conn)
	}
}
