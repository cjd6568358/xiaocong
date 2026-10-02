# tools/ont —— 光猫（ZTE F4610U）运维与诊断脚本

这里的脚本都是**对光猫只读或可精确回滚**的，用来诊断"接管为什么不生效"。
要一键安装/卸载接管本身，请用 `firmware/ont-go/ixc-ont.sh`。

## 运行前提

```bash
# 带 paramiko 的 Python（系统 python 没装 paramiko）
PY="C:/Users/caojiecn/.workbuddy-ai/binaries/python/envs/ssh/Scripts/python.exe"
cd tools/ont
SSH_PW=<光猫root密码> "$PY" <脚本>.py
```

光猫 SSH：`root@192.168.1.1:10022`。

## 脚本清单

### 基础设施（几乎所有脚本都依赖）

| 脚本 | 作用 |
|---|---|
| `sshutil.py` | SSH 执行封装。**里面那个 `run()` 的注释必须看**：老版本用 `exit_status_ready() and not recv_ready()` 当退出条件，会在远端命令刚结束、最后一块数据还在途中时**静默截断输出**，我因此一度误判 busybox 源码树缺子目录。正确做法是以 EOF 为准。 |
| `putfile.py` | 写文件到光猫。**唯一可靠的方式**：分块 heredoc 追加，每块 ≤3000 字符。 |

### 诊断（按"排查顺序"排）

| 脚本 | 回答什么问题 | 关键结论 |
|---|---|---|
| `mtchtest.py` | iptables 的 match 到底哪些能用 | 必须**真插一条**才准。`-C` 探测和 `--help` 都不可靠 |
| `cap2.py` | 各表是否存在、进程是谁 | 没有 raw 表；53 端口是 `/sbin/proxy` |
| `iftest2.py` | DNS 包的入接口到底是 `br0` 还是物理口 | 是 `br0`（`PHYSIN=wlan0`） |
| `diag5.py` | 是不是硬件快转把包旁路了 | **不是**，已建流照样被劫持 |
| `diag8.py` | 每个候选接口各命中多少 | `br0` 114 次，其余全 0 |
| `diag10.py` | 插座的包到底有没有过 CPU | **过了**：filter INPUT 19 次 / FORWARD 12 次（60 秒） |

### 部署与验证

| 脚本 | 作用 |
|---|---|
| `apply2.py` | 执行「劫持光猫 DNS 上游」方案并当场验证 |
| `ctl.py` | 验证控制面：`/status` `/on` `/off`，看插座是否真的跟着动 |
| `sweep-ont.py` | 清点光猫上属于本项目的产物 |
| `fetch-ont.py` | 把光猫上【重启即丢失】的东西拉回本机归档 |
| `upx-busybox.py` | busybox 静态编译产物的 UPX 压缩与上传 |

## 这台光猫的三条硬约束（脚本里都已处理）

1. **单次 SSH 命令内容超过约 8KB → EOFError，连接直接断**。60000 字符单行同样断。
2. **没有 sftp-server**，paramiko SFTP 报 `EOF during negotiation`；`scp` 必须带 `-O`。
3. **busybox 没有 base64**（编码、解码都不行）。所以既不能用 base64 传文件上去，
   也不能用它把文件取回来 —— 取回只能用 `cat`（好在证书 PEM、日志、规则都是文本）。

## 完整归档

`scratch/`（144 个文件）保留了全部一次性诊断脚本和中间输出，包括这里没提升的
`test*.py`、`diag*.py`、`t*.txt`、`bb*.txt`。不要删——它们是本次结论的原始证据。
