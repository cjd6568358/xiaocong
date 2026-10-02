import os, sys, paramiko
PW = os.environ.get("SSH_PW","")
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
try:
    cli.connect("10.0.0.4", 22, username="root", password=PW, timeout=12, look_for_keys=False, allow_agent=False)
except Exception as e:
    print("❌ 10.0.0.4 连接失败:", type(e).__name__, e); sys.exit(1)
print("✅ 编译服务器登录成功")
for t,c in [
    ("系统","uname -a; echo; cat /etc/os-release 2>/dev/null | head -4"),
    ("Go","go version; echo; go env GOOS GOARCH GOROOT GOPATH GOCACHE"),
    ("资源","nproc; free -h | head -3; df -h /tmp"),
    ("交叉编译目标","go tool dist list 2>/dev/null | grep -E '^linux/(arm64|arm|amd64|mips)' "),
    ("/tmp 权限","ls -ld /tmp; touch /tmp/.wtest && echo '/tmp 可写' && rm -f /tmp/.wtest"),
]:
    print(f"\n=== {t} ===")
    _,o,e = cli.exec_command(c, timeout=25)
    print(o.read().decode('utf-8','replace').rstrip()[:2000])
    er = e.read().decode('utf-8','replace').strip()
    if er: print("[stderr]", er[:500])
cli.close()
