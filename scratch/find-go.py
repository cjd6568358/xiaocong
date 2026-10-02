import os, paramiko
PW=os.environ["SSH_PW"]
cli=paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("10.0.0.4",22,username="root",password=PW,timeout=12,look_for_keys=False,allow_agent=False)
for t,c in [
 ("找 go","ls -la /usr/local/go/bin/go /usr/lib/go*/bin/go /opt/go/bin/go 2>&1; echo '--- which 登录shell ---'; bash -lc 'command -v go; go version' 2>&1 | head -5"),
 ("PATH","echo $PATH; echo '--- profile ---'; grep -rn 'go/bin' /etc/profile /etc/profile.d/*.sh /root/.bashrc /root/.profile 2>/dev/null | head -10"),
 ("/usr/local","ls -la /usr/local 2>&1 | head -20"),
]:
    print(f"\n=== {t} ===")
    _,o,e=cli.exec_command(c,timeout=30)
    print(o.read().decode('utf-8','replace').rstrip()[:2500])
    er=e.read().decode('utf-8','replace').strip()
    if er: print("[stderr]",er[:400])
cli.close()
