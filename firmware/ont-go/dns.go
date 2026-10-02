// dns.go —— 极简 DNS 应答器：命中劫持域名就回我们的公网 IP，其余原样转发上游。
//
// 为什么要自己写：插座固件把 DNS 硬编码成 223.5.5.5，改光猫/路由的 DNS 毫无作用，
// 只能在链路上把它去 53 端口的包 REDIRECT 到本程序（见 rules.go）。
// 只转发不做缓存，够用且不易出错。
package main

import (
	"encoding/binary"
	"fmt"
	"net"
	"strings"
	"time"
)

type dnsServer struct {
	hijackIP net.IP
	domains  []string
	upstream string
	pc       net.PacketConn
}

func newDNS(listen, hijackIP, domainsCSV, upstream string) (*dnsServer, error) {
	ip := net.ParseIP(hijackIP)
	if ip == nil || ip.To4() == nil {
		return nil, fmt.Errorf("非法劫持 IP: %q", hijackIP)
	}
	var ds []string
	for _, d := range strings.Split(domainsCSV, ",") {
		d = strings.ToLower(strings.TrimSuffix(strings.TrimSpace(d), "."))
		if d != "" {
			ds = append(ds, d)
		}
	}
	pc, err := net.ListenPacket("udp", listen)
	if err != nil {
		return nil, err
	}
	return &dnsServer{hijackIP: ip.To4(), domains: ds, upstream: upstream, pc: pc}, nil
}

// match：精确相等或子域都算命中
func (d *dnsServer) match(name string) bool {
	name = strings.ToLower(strings.TrimSuffix(name, "."))
	for _, dom := range d.domains {
		if name == dom || strings.HasSuffix(name, "."+dom) {
			return true
		}
	}
	return false
}

// parseQuestion 解析首条问题，返回 qname / qtype / 问题区结束偏移
func parseQuestion(q []byte) (string, uint16, int, bool) {
	if len(q) < 12 || binary.BigEndian.Uint16(q[4:6]) == 0 {
		return "", 0, 0, false
	}
	var sb strings.Builder
	i := 12
	for {
		if i >= len(q) {
			return "", 0, 0, false
		}
		l := int(q[i])
		i++
		if l == 0 {
			break
		}
		if l&0xC0 != 0 || i+l > len(q) { // 查询里不该出现压缩指针
			return "", 0, 0, false
		}
		if sb.Len() > 0 {
			sb.WriteByte('.')
		}
		sb.Write(q[i : i+l])
		i += l
	}
	if i+4 > len(q) {
		return "", 0, 0, false
	}
	return sb.String(), binary.BigEndian.Uint16(q[i : i+2]), i + 4, true
}

// buildA 造一个 A 记录应答：保留原问题、单条应答、丢掉 EDNS 附加段
func buildA(q []byte, qend int, ip net.IP) []byte {
	resp := make([]byte, 0, qend+16)
	resp = append(resp, q[:qend]...)
	resp[2] = 0x80 | (q[2] & 0x01)             // QR=1，保留 RD
	resp[3] = 0x80                             // RA=1，rcode=0
	binary.BigEndian.PutUint16(resp[4:6], 1)   // QDCOUNT
	binary.BigEndian.PutUint16(resp[6:8], 1)   // ANCOUNT
	binary.BigEndian.PutUint16(resp[8:10], 0)  // NSCOUNT
	binary.BigEndian.PutUint16(resp[10:12], 0) // ARCOUNT
	ans := []byte{0xC0, 0x0C, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00, 0x1E, 0x00, 0x04}
	return append(append(resp, ans...), ip.To4()...)
}

func (d *dnsServer) forward(q []byte, addr net.Addr) {
	c, err := net.DialTimeout("udp", d.upstream, 3*time.Second)
	if err != nil {
		return
	}
	defer c.Close()
	c.SetDeadline(time.Now().Add(3 * time.Second))
	if _, err := c.Write(q); err != nil {
		return
	}
	buf := make([]byte, 1500)
	n, err := c.Read(buf)
	if err != nil {
		return
	}
	d.pc.WriteTo(buf[:n], addr)
}

// Run 阻塞式服务循环
func (d *dnsServer) Run() {
	logf("DNS 应答器就绪  监听=%s  劫持域=%v  →%s  上游=%s",
		d.pc.LocalAddr(), d.domains, d.hijackIP, d.upstream)
	for {
		buf := make([]byte, 1500)
		n, addr, err := d.pc.ReadFrom(buf)
		if err != nil {
			logf("DNS 读失败: %v", err)
			continue
		}
		q := buf[:n]
		name, qtype, qend, ok := parseQuestion(q)
		if ok && qtype == 1 && d.match(name) {
			d.pc.WriteTo(buildA(q, qend, d.hijackIP), addr)
			logf("  DNS 劫持  %s A → %s   (来自 %s)", name, d.hijackIP, addr)
			rec("dns", map[string]interface{}{
				"action": "hijack", "name": name, "ip": d.hijackIP.String(), "from": addr.String(),
			})
			continue
		}
		go d.forward(q, addr)
	}
}

// Close 供退出时释放端口
func (d *dnsServer) Close() { d.pc.Close() }
