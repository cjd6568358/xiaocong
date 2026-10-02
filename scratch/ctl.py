# -*- coding: utf-8 -*-
"""验证控制面：HTTP 8080 的 /status /on /off，看插座是否真的跟着动。"""
import os, sys, time, urllib.request, json
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil
os.environ.setdefault("SSH_PW", "e3eb773F")
import paramiko
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)
def sh(c, t=120): return sshutil.run(cli, c, timeout=t).strip()

def http(path):
    try:
        with urllib.request.urlopen("http://192.168.1.1:8080" + path, timeout=8) as r:
            return r.read().decode("utf-8", "replace").strip()
    except Exception as e:
        return "ERR: %s" % e

print("=== /status ===")
print(http("/status"))
print()
print("=== 下发 /on ===")
print(http("/on"))
time.sleep(12)
print("12 秒后日志:")
print(sh("tail -6 /tmp/ixc-go.log"))
print()
print("=== /status ===")
print(http("/status"))
print()
print("=== 下发 /off ===")
print(http("/off"))
time.sleep(12)
print("12 秒后日志:")
print(sh("tail -6 /tmp/ixc-go.log"))
print()
print("=== /status ===")
print(http("/status"))
print()
print("=== 插座 reboot_count 是否还在涨（判断是否在重启循环）===")
print(sh("grep -o '\"reboot_count\":[0-9]*' /tmp/ixc-go.log | tail -6"))
print()
print("=== 插座连接条目 ===")
print(sh("cat /proc/net/nf_conntrack | grep '192.168.1.11' | grep -E '8188|203.0.113.9'"))
cli.close()
