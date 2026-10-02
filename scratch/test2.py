import os, paramiko, sys
PW=os.environ["SSH_PW"]
cli=paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1",10022,username="root",password=PW,timeout=12,look_for_keys=False,allow_agent=False)
print("✅ 登录光猫")
script = r'''
echo "===== A. 加别名前 ====="
ping -c 1 -W 2 203.0.113.9 >/dev/null 2>&1 && echo "ping 203.0.113.9 = 通" || echo "ping 203.0.113.9 = 不通"
echo "===== B. 加别名 ====="
ifconfig br0:0 203.0.113.9 netmask 255.255.255.255 up; echo "ifconfig rc=$?"
sleep 1
echo "===== C. 加别名后 ping ====="
ping -c 1 -W 2 203.0.113.9 2>&1 | tail -3
echo "===== D. netstat 看地址 ====="
netstat -ln 2>/dev/null | head -5
cat /proc/net/fib_trie 2>/dev/null | grep -B2 -A2 "203.0.113.9" | head -20
echo "===== E. 起监听 + 自连 ====="
rm -f /tmp/nct.log
busybox nc -l -p 8188 > /tmp/nct.log 2>&1 &
NCPID=$!
sleep 2
netstat -ltn 2>/dev/null | grep 8188 || echo "(netstat 没看到 8188 监听)"
echo "TEST-PAYLOAD-12345" | busybox nc -w 3 203.0.113.9 8188; echo "自连 rc=$?"
sleep 1
echo "[监听端收到]"; cat /tmp/nct.log 2>/dev/null
kill $NCPID 2>/dev/null
echo "===== F. 清理 ====="
ifconfig br0:0 down; echo "down rc=$?"
sleep 1
ping -c 1 -W 2 203.0.113.9 >/dev/null 2>&1 && echo "清理后 ping 仍通(异常!)" || echo "清理后 ping 不通(已恢复)"
busybox ifconfig br0 | head -4
'''
_,o,e = cli.exec_command(script, timeout=60)
print(o.read().decode('utf-8','replace'))
er=e.read().decode('utf-8','replace')
if er.strip(): print("[stderr]", er[:1500])
cli.close()
