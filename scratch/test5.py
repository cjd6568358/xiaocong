import os, socket, time, paramiko
FAKE="203.0.113.9"; PORT=8188
cli=paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1",10022,username="root",password=os.environ["SSH_PW"],
            timeout=12,look_for_keys=False,allow_agent=False)
def sh(c,t=20):
    _,o,e=cli.exec_command(c,timeout=t)
    return o.read().decode("utf-8","replace").strip(), e.read().decode("utf-8","replace").strip()
def cleanup():
    sh(f"killall nc 2>/dev/null; ifconfig br0:0 down 2>/dev/null; echo cleaned")
    print("    已清理")
try:
    print("[1] 加别名 + 起监听 (busybox nc, 完全脱离会话)")
    print("   ", sh(f"ifconfig br0:0 {FAKE} netmask 255.255.255.255 up; echo rc=$?")[0])
    print("   ", sh(f"rm -f /tmp/nct.log; nohup busybox nc -l -p {PORT} </dev/null >/tmp/nct.log 2>&1 & sleep 1; echo started")[0])
    print("   ", sh(f"netstat -ltn 2>/dev/null | grep {PORT} || echo '(netstat 未见)'")[0])

    print(f"\n[2] 从 PC(192.168.1.20) 连 {FAKE}:{PORT} …")
    ok=False
    for attempt in range(3):
        try:
            s=socket.create_connection((FAKE,PORT),timeout=5)
            s.sendall(b"HELLO-FROM-PC-192.168.1.20\n")
            s.close()
            print(f"   ✅ PC 连接成功（第 {attempt+1} 次）")
            ok=True
            break
        except Exception as e:
            print(f"   ✗ 第 {attempt+1} 次: {type(e).__name__} {e}")
            time.sleep(1.5)

    print(f"\n[3] 光猫监听端收到了什么:")
    print("   ", sh("sleep 1; cat /tmp/nct.log 2>/dev/null | head -5")[0] or "(空)")

    print(f"\n[4] 结论: {'✅ 公网 IP 落到光猫本地端口 = 可行' if ok else '❌ 不通'}")
finally:
    print("\n[5] 清理")
    cleanup()
    print("   fib_trie 残留:", sh("cat /proc/net/fib_trie | grep -c '203\\.0\\.113\\.9'")[0])
    cli.close()
