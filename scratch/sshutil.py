import os, socket, time, paramiko

def connect(host="192.168.1.1", port=10022):
    cli = paramiko.SSHClient()
    cli.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    cli.connect(host, port, username="root", password=os.environ["SSH_PW"],
                timeout=12, look_for_keys=False, allow_agent=False)
    return cli

def run(cli, cmd, timeout=30):
    """执行命令并【完整】读回输出。

    曾经踩过的坑：老版本用
        if ch.exit_status_ready() and not ch.recv_ready(): break
    作为退出条件。远端命令一旦结束、而最后一块数据尚在途中时，这个条件就成立，
    于是输出被【静默截断】——表现为 `find ... | wc -l` 报 1、`tar tjf | grep` 只出一条，
    我因此一度误判「源码树缺了所有子目录 Config.in」。实际是 25 个。

    正确做法：以 EOF 为准（paramiko 收到 SSH_MSG_CHANNEL_EOF 会置 ch.eof_received），
    并在退出前把残留数据排空。
    """
    ch = cli.get_transport().open_session()
    ch.settimeout(2.0)
    ch.exec_command(cmd)
    buf = b""
    t0 = time.time()
    while True:
        try:
            data = ch.recv(65536)
        except socket.timeout:
            data = None
        if data:
            buf += data
            continue
        # 没数据可读：先确认是不是真的到头了
        if ch.eof_received:
            while True:
                try:
                    more = ch.recv(65536)
                except socket.timeout:
                    break
                if not more:
                    break
                buf += more
            break
        if time.time() - t0 > timeout:
            buf += b"\n[!! read timeout, giving up]"
            break
        time.sleep(0.05)
    try:
        ch.close()
    except Exception:
        pass
    return buf.decode("utf-8", "replace")

def run_to_file(cli, cmd, path="/tmp/_out.txt", timeout=600):
    """更保险的方式：让远端把结果写进文件，再单独 cat 回来。
    输出很大、或管道里带 head/grep（SIGPIPE 会提前掐断生产者）时优先用这个。"""
    run(cli, "{ %s ; } > %s 2>&1; echo RC=$?" % (cmd, path), timeout=timeout)
    return run(cli, "cat %s" % path, timeout=timeout)
