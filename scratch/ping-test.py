import os, paramiko, time
cli = paramiko.SSHClient(); cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("192.168.1.1", 10022, username="root", password=os.environ["SSH_PW"],
            timeout=12, look_for_keys=False, allow_agent=False)
print("OK 登录")
for cmd in ["echo hello", "date", "ifconfig br0 | head -2", "ping -c 1 -W 2 203.0.113.9; echo pingrc=$?"]:
    t0=time.time()
    try:
        _, o, e = cli.exec_command(cmd, timeout=15)
        out = o.read().decode("utf-8","replace")
        err = e.read().decode("utf-8","replace")
        print(f"\n$ {cmd}\n  ({time.time()-t0:.1f}s) rc-out:/n{out.rstrip()}")
        if err.strip(): print("  [err]", err.rstrip()[:300])
    except Exception as ex:
        print(f"\n$ {cmd}\n  ❌ {type(ex).__name__}: {ex}  ({time.time()-t0:.1f}s)")
cli.close()
