package main

import (
	"crypto/tls"
	"fmt"
	"os"
)

func main() {
	fmt.Printf("VersionTLS10=0x%04x  VersionTLS11=0x%04x  VersionTLS12=0x%04x\n",
		tls.VersionTLS10, tls.VersionTLS11, tls.VersionTLS12)

	want := map[uint16]bool{
		tls.TLS_RSA_WITH_AES_256_CBC_SHA:   true,
		tls.TLS_RSA_WITH_AES_128_CBC_SHA:   true,
		tls.TLS_RSA_WITH_3DES_EDE_CBC_SHA:  true,
	}
	fmt.Println("--- 静态 RSA 密钥交换套件是否仍可用 ---")
	for _, cs := range tls.CipherSuites() {
		if want[cs.ID] {
			fmt.Printf("  secure   0x%04x %s\n", cs.ID, cs.Name)
		}
	}
	for _, cs := range tls.InsecureCipherSuites() {
		if want[cs.ID] {
			fmt.Printf("  insecure 0x%04x %s\n", cs.ID, cs.Name)
		}
	}

	fmt.Println("--- 启动测试服务端 127.0.0.1:18999 (Min=TLS1.0 Max=TLS1.1) ---")
	cert, err := tls.LoadX509KeyPair(os.Args[1], os.Args[2])
	if err != nil {
		fmt.Println("加载证书失败:", err)
		return
	}
	cfg := &tls.Config{
		Certificates: []tls.Certificate{cert},
		MinVersion:   tls.VersionTLS10,
		MaxVersion:   tls.VersionTLS11,
		CipherSuites: []uint16{tls.TLS_RSA_WITH_AES_256_CBC_SHA, tls.TLS_RSA_WITH_AES_128_CBC_SHA},
	}
	ln, err := tls.Listen("tcp", "127.0.0.1:18999", cfg)
	if err != nil {
		fmt.Println("监听失败:", err)
		return
	}
	defer ln.Close()
	conn, err := ln.Accept()
	if err != nil {
		fmt.Println("Accept 失败:", err)
		return
	}
	tc := conn.(*tls.Conn)
	if err := tc.Handshake(); err != nil {
		fmt.Println("握手失败:", err)
		os.Exit(2)
	}
	st := tc.ConnectionState()
	fmt.Printf("握手成功  version=0x%04x  cipher=0x%04x %s\n",
		st.Version, st.CipherSuite, tls.CipherSuiteName(st.CipherSuite))
	buf := make([]byte, 128)
	n, _ := conn.Read(buf)
	fmt.Printf("收到明文 %d 字节: %q\n", n, buf[:n])
}
