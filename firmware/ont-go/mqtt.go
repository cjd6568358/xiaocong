// mqtt.go —— 冒充官方云端的那一半：TLS 1.1 + 静态 RSA 的极简 MQTT。
//
// 插座只支持 TLS 1.1 + 静态 RSA 密钥交换（AES256-SHA），且**不校验证书**。
// Go 的 crypto/tls 仍保留这些套件（归在 InsecureCipherSuites 里，默认不启用），
// 显式写进 CipherSuites 即可。
//
// 另外：插座**从不发 SUBSCRIBE** —— 这套"MQTT"只是借用报文框架做点对点，
// 服务端直接往同一条 TLS 连接写 PUBLISH 就行。
package main

import (
	"bufio"
	"crypto/tls"
	"encoding/binary"
	"encoding/json"
	"fmt"
	"io"
	"net"
	"sync"
	"time"
)

const (
	deviceID    = "6587529711423210"
	appClientID = "f9ee07acc3412b0f538d778ab05e5e80"
)

var (
	mu       sync.Mutex
	seq      uint32   = 100
	sessions sync.Map // remote -> *session，供命令文件下发
)

// MQTT 剩余长度是变长整数（每字节 7 位，最高位是续位）
func encodeRemLen(n int) []byte {
	var out []byte
	for {
		d := byte(n % 128)
		n /= 128
		if n > 0 {
			d |= 0x80
		}
		out = append(out, d)
		if n == 0 {
			return out
		}
	}
}

func decodeRemLen(r *bufio.Reader) (int, error) {
	mult, val := 1, 0
	for i := 0; i < 4; i++ {
		b, err := r.ReadByte()
		if err != nil {
			return 0, err
		}
		val += int(b&0x7F) * mult
		if b&0x80 == 0 {
			return val, nil
		}
		mult *= 128
	}
	return 0, fmt.Errorf("剩余长度非法")
}

// buildPublish 组装一个 PUBLISH 报文（QoS0）
func buildPublish(topic string, payload []byte) []byte {
	body := make([]byte, 0, 2+len(topic)+len(payload))
	body = append(body, byte(len(topic)>>8), byte(len(topic)))
	body = append(body, topic...)
	body = append(body, payload...)
	pkt := []byte{0x30}
	pkt = append(pkt, encodeRemLen(len(body))...)
	return append(pkt, body...)
}

// buildControl 组装官方那套控制 JSON
func buildControl(field string, value interface{}) []byte {
	seq++
	cmd := map[string]interface{}{}
	cmd[field] = value
	msg := map[string]interface{}{
		"messageId":       seq,
		"protocolVersion": "1.0.0",
		"receiveId":       deviceID,
		"senderId":        appClientID,
		"command":         cmd,
	}
	b, _ := json.Marshal(msg)
	return b
}

type session struct {
	conn   net.Conn
	remote string
	write  sync.Mutex
}

func (s *session) send(pkt []byte) {
	s.write.Lock()
	defer s.write.Unlock()
	if _, err := s.conn.Write(pkt); err != nil {
		logf("   写失败: %v", err)
	}
}

func (s *session) publishRaw(raw string) {
	s.send(buildPublish(*cmdTopic, []byte(raw)))
	logf("   → PUBLISH %s %s", *cmdTopic, raw)
	rec("control_sent", map[string]interface{}{"raw": raw})
}

func (s *session) publishControl(field string, value interface{}) {
	payload := buildControl(field, value)
	s.send(buildPublish(*cmdTopic, payload))
	logf("   → PUBLISH %s %s", *cmdTopic, string(payload))
	rec("control_sent", map[string]interface{}{"field": field, "value": value, "payload": string(payload)})
}

// PublishToAll 给所有在线会话下发一行（命令文件与 HTTP 接口共用）
func PublishToAll(raw string) int {
	n := 0
	sessions.Range(func(_, v interface{}) bool {
		v.(*session).publishRaw(raw)
		n++
		return true
	})
	return n
}

// handleConn 处理一条来自插座的 TLS 连接
func handleConn(c net.Conn) {
	defer c.Close()
	remote := c.RemoteAddr().String()
	logf("★ 有连接进来 %s", remote)

	tc, ok := c.(*tls.Conn)
	if !ok {
		logf("  ❌ 非 TLS 连接")
		return
	}
	tc.SetDeadline(time.Now().Add(20 * time.Second))
	if err := tc.Handshake(); err != nil {
		logf("  ❌ TLS 握手失败: %v", err)
		rec("tls_handshake", map[string]interface{}{"result": "rejected", "error": err.Error(), "remote": remote})
		return
	}
	st := tc.ConnectionState()
	logf("  ✅ TLS 握手成功  version=0x%04x  cipher=%s", st.Version, tls.CipherSuiteName(st.CipherSuite))
	rec("tls_handshake", map[string]interface{}{
		"result": "accepted", "version": fmt.Sprintf("0x%04x", st.Version),
		"cipher": tls.CipherSuiteName(st.CipherSuite), "remote": remote,
	})
	tc.SetDeadline(time.Time{})

	s := &session{conn: tc, remote: remote}
	sessions.Store(remote, s)
	defer sessions.Delete(remote)
	if *onConnect != "" {
		s.publishRaw(*onConnect)
	}

	r := bufio.NewReader(tc)
	for {
		hdr, err := r.ReadByte()
		if err != nil {
			logf("   连接结束 (%v)", err)
			return
		}
		ptype := hdr >> 4
		rem, err := decodeRemLen(r)
		if err != nil {
			logf("   读剩余长度失败: %v", err)
			return
		}
		body := make([]byte, rem)
		if _, err := io.ReadFull(r, body); err != nil {
			logf("   读报文体失败: %v", err)
			return
		}

		switch ptype {
		case 1: // CONNECT
			logf("  ← CONNECT (%d 字节)", rem)
			s.send([]byte{0x20, 0x02, 0x00, 0x00}) // CONNACK 接受
			rec("mqtt", map[string]interface{}{"type": "CONNECT", "len": rem})

		case 3: // PUBLISH
			if len(body) < 2 {
				continue
			}
			tl := int(binary.BigEndian.Uint16(body[:2]))
			if 2+tl > len(body) {
				continue
			}
			topic := string(body[2 : 2+tl])
			payload := body[2+tl:]
			logf("  ← PUBLISH topic=%s payload=%s", topic, string(payload))
			rec("mqtt", map[string]interface{}{
				"type": "PUBLISH", "topic": topic, "payload": string(payload),
				"hex": fmt.Sprintf("%x", payload),
			})
			qos := (hdr >> 1) & 0x03
			if qos >= 1 && len(body) >= 4+tl {
				pid := body[2+tl : 4+tl]
				s.send([]byte{0x40, 0x02, pid[0], pid[1]})
			}

		case 8: // SUBSCRIBE
			logf("  ← SUBSCRIBE")
			if len(body) >= 2 {
				s.send([]byte{0x90, 0x03, body[0], body[1], 0x00})
			}
			rec("mqtt", map[string]interface{}{"type": "SUBSCRIBE"})

		case 12: // PINGREQ
			s.send([]byte{0xD0, 0x00})
			logf("  ← PINGREQ → PINGRESP")

		case 14: // DISCONNECT
			logf("  ← DISCONNECT，连接结束")
			return

		default:
			logf("  ← 其它报文 type=%d (%d 字节)", ptype, rem)
			rec("mqtt", map[string]interface{}{"type": ptype, "len": rem})
		}
	}
}
