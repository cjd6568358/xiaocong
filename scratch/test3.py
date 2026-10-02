import os, paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=12, look_for_keys=False, allow_agent=False)
print("OK 登录光猫\n")
script = '''
echo "[A] 别名前 ping 203.0.113.9:"
ping -c 1 -W 2 203.0.113.9 >/dev/null 2>&1 && echo "   通" || echo "   不通(正常)"
echo "[B] ifconfig br0:0 203.0.113.9 netmask 255.255.255.255 up"
ifconfig br0:0 203.0.113.9 netmask 255.255.255.255 up; echo "   rc=$?"
sleep 1
echo "[C] 别名后 ping 203.0.113.9:"
ping -c 2 -W 2 203.0.113.9 2>&1 | tail -4
echo "[D] fib_trie 里有没有 203.0.113.9:"
cat /proc/net/fib_trie 2>/dev/null | grep -c "203.0.113.9"
echo "[E] ifconfig br0:0 down"
ifconfig br0:0 down; echo "   rc=$?"
sleep 1
ping -c 1 -W 2 203.0.113.9 >/dev/null 2>&1 && echo "   仍通(异常!)" || echo "   已恢复"
'''
_, o, e = cli.exec_command(script, timeout=45)
print(o.read().decode("utf-8", "replace"))
er = e.read().decode("utf-8", "replace")
if er.strip():
    print("[stderr]", er[:1000])
cli.close()
