# -*- coding: utf-8 -*-
"""判定：桥上流量到底该用 -i br0 还是 -i wlan0/wlan4。

做法：对 PC 自己插一条临时 REDIRECT，然后从 PC 发一次 DNS 查询，
     看 ixc-go 的假 DNS 有没有接到（接到 → 该接口名有效）。
     每个接口试完立刻删，不留残留。
"""
import os, sys, time, subprocess, re
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko

cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()
IPT = "LD_LIBRARY_PATH=/f4610u/lib:$LD_LIBRARY_PATH /f4610u/bin/iptables_upx"

PC = sh("grep -i 'a0:b3:39:80:da:8b' /proc/net/arp | awk '{print $1}'").strip()
print("PC 当前 IP =", PC)
if not PC:
    print("❌ 没在 ARP 里找到 PC，退出"); sys.exit(1)

def ask():
    """从 PC 发一次 DNS 查询，返回解析到的 IP（或 None）"""
    try:
        out = subprocess.run(["nslookup", "iot.ixiaocong.com", "8.8.8.8"],
                             capture_output=True, text=True, timeout=20).stdout
    except Exception as e:
        return None, str(e)
    ips = re.findall(r"Address:/s+([0-9.]+)", out)
    # 过滤掉 DNS 服务器自己那行
    ips = [i for i in ips if i != "8.8.8.8"]
    return (ips[0] if ips else None), out.strip()

print("\n基线（无临时规则）:", ask()[0])
print()

for ifname in ["br0", "wlan0", "wlan4"]:
    ins = sh(f"{IPT} -t nat -I PREROUTING 1 -i {ifname} -s {PC} -p udp --dport 53 -j REDIRECT --to-ports 5353 2>&1")
    if "Error" in ins or "error" in ins or "not" in ins.lower():
        print(f"[{ifname}] 插入失败: {ins}")
        continue
    time.sleep(1)
    got, raw = ask()
    cnt = sh(f"{IPT} -t nat -L PREROUTING -n -v --line-numbers | sed -n '3p'")
    pkts = cnt.split()[0] if cnt else "?"
    verdict = "✅ 命中（劫持生效）" if got == "203.0.113.9" else f"❌ 没命中（拿到 {got}）"
    print(f"[{ifname}] 解析结果={got}  规则包数={pkts}  → {verdict}")
    sh(f"{IPT} -t nat -D PREROUTING -i {ifname} -s {PC} -p udp --dport 53 -j REDIRECT --to-ports 5353 2>&1")
    time.sleep(0.5)

print()
print("清理后 PREROUTING:")
print(sh(f"{IPT} -t nat -L PREROUTING -n --line-numbers | sed -n '3,6p'"))
cli.close()
