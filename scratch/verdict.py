import os, sys, time
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=60): return sshutil.run(cli, c, timeout=t).strip()

print("配网时刻 03:27:15，观察 150 秒（插座要重新关联 WiFi 再建 TLS）\n")
for i in range(10):
    ts = time.strftime('%H:%M:%S')
    arp = sh("grep -i 'b4:e6:2d:3a:6e:7c' /proc/net/arp | awk '{print $1, $3}' || echo '-'")
    ct  = sh("grep '192.168.1.23' /proc/net/nf_conntrack 2>/dev/null | head -4 || true")
    dns = sh("grep -c '192.168.1.23' /tmp/ixc-go.log 2>/dev/null || echo 0")
    new = sh("awk '$0 > \"[03:27:00]\"' /tmp/ixc-go.log 2>/dev/null | tail -6 || true")
    print(f"--- {ts} ---  ARP: {arp}")
    if ct: print("    conntrack:", ct.replace("\n", "\n              "))
    if new.strip(): print("    新日志:", new.replace("\n", "\n             "))
    if i < 9: time.sleep(15)

print("\n" + "="*60)
print("★ 判决")
print("="*60)
print("conntrack 全部（含 8188）:")
print(sh("grep -E '8188' /proc/net/nf_conntrack 2>/dev/null | head -8 || echo '(无)'"))
print("\nDNS 劫持记录（来自插座 192.168.1.23 的）:")
print(sh("grep '192.168.1.23' /tmp/ixc-go.log | grep -i 'DNS 劫持' | tail -10 || echo '(无 —— 说明插座没查 iot.ixiaocong.com)'"))
print("\n规则命中计数（看第1条 hairpin 是否被触发）:")
print(sh("LD_LIBRARY_PATH=/f4610u/lib /f4610u/bin/iptables_upx -t nat -L PREROUTING -n -v --line-numbers 2>/dev/null | sed -n '3,4p'"))
cli.close()
