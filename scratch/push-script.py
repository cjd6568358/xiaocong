import os, sys, time
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sshutil, paramiko
os.environ.setdefault("SSH_PW", "e3eb773F")
cli = paramiko.SSHClient()
cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
cli.connect("10.0.0.4", 22, username="root", password=os.environ["SSH_PW"],
            timeout=15, look_for_keys=False, allow_agent=False)

def sh(c, t=180):
    return sshutil.run(cli, c, timeout=t).strip()

SRC = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
                   "firmware", "build_busybox_static.sh")
sf = paramiko.SFTPClient.from_transport(cli.get_transport())
sf.put(SRC, "/media/disk/backup/script/build_busybox_static.sh"); sf.close()
sh("chmod 755 /media/disk/backup/script/build_busybox_static.sh")
print("== 已上传，服务器上的状态 ==")
print(sh("ls -l /media/disk/backup/script/build_busybox_static.sh"))
print("\n== bash -n 语法检查 ==")
print(sh("bash -n /media/disk/backup/script/build_busybox_static.sh && echo '✅ 语法 OK'"))
print("\n== UPX 阶段 diff（确认真的加进去了）==")
print(sh("grep -n 'UPX\\|upx\\|scp -O' /media/disk/backup/script/build_busybox_static.sh | head -25"))

# 后台跑完整构建验证（nohup，免得本机切 WiFi 时把连接掐了）
sh("rm -rf /tmp/bbtest && mkdir -p /tmp/bbtest")
sh("cd /tmp/bbtest && nohup /media/disk/backup/script/build_busybox_static.sh > /tmp/bbtest/run.log 2>&1 & echo 已在后台启动")
time.sleep(3)
print("\n== 后台任务状态 ==")
print(sh("ps w | grep -v grep | grep -c build_busybox; tail -5 /tmp/bbtest/run.log 2>/dev/null"))
cli.close()
