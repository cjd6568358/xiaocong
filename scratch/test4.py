import os, paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=12, look_for_keys=False, allow_agent=False)
def sh(c, t=20):
    _, o, e = cli.exec_command(c, timeout=t)
    return o.read().decode("utf-8","replace"), e.read().decode("utf-8","replace")

print("ifconfig 是什么:", sh("ls -la /sbin/ifconfig /bin/ifconfig 2>&1; command -v ifconfig")[0])
print("\n[1] 加别名前 fib_trie 里 203.0.113.9 出现次数:")
print(sh("cat /proc/net/fib_trie | grep -c '203\\.0\\.113\\.9'")[0])
print("[2] 加别名:")
print(sh("ifconfig br0:0 203.0.113.9 netmask 255.255.255.255 up; echo rc=$?")[0])
print("[3] 加别名后 fib_trie 上下文:")
print(sh("cat /proc/net/fib_trie | grep -B4 -A4 '203\\.0\\.113\\.9' | head -30")[0])
print("    出现次数:", sh("cat /proc/net/fib_trie | grep -c '203\\.0\\.113\\.9'")[0].strip())
print("[4] ifconfig -a 输出 br0 段:")
print(sh("ifconfig -a 2>&1 | grep -A4 '^br0' | head -12")[0])
print("[5] 清理:")
print(sh("ifconfig br0:0 down; echo rc=$?")[0])
print("    清理后出现次数:", sh("cat /proc/net/fib_trie | grep -c '203\\.0\\.113\\.9'")[0].strip())
cli.close()
