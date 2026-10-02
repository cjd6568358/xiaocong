# 06 · Ghidra 操作手册

> 怎么在本机跑通 Ghidra、踩过哪些坑、脚本怎么用。
> 反编译**结果**（native 层结论）见 [03-配网与native.md](03-配网与native.md)，
> 产物在 [`ghidra_out/`](../ghidra_out/)。
> 下次要分析新的 `.so`（比如固件）时照着本文做即可。

---

## 1. 环境要求

| 项 | 要求 | 说明 |
|---|---|---|
| JDK | **完整 JDK 21**（必须有 `bin/javac.exe`） | Ghidra 12 的 `application.java.min=21`，且 `-jdk_home` 模式强制检查 javac 存在 |
| 脚本语言 | **Java（`GhidraScript`）** | Ghidra 12 已弃用 Jython；`.py` 脚本需 PyGhidra |

本机可用 JDK：`C:/Users/caojiecn/Downloads/jdk-21.0.12.1+1`

> jadx 自带的 JRE 是**裁剪版**（只含 10 个模块，缺 `java.rmi`），
> 会让 Ghidra 初始化直接 `NoClassDefFoundError: java/rmi/Remote`，**不能用**。

---

## 2. 命令行跑通（headless，推荐）

```bash
export JAVA_HOME="C:/Users/caojiecn/Downloads/jdk-21.0.12.1+1"
export PATH="$JAVA_HOME/bin:$PATH"
export XIAOCONG_OUT="c:/workspace/xiaocong"

mkdir -p ghidra_proj

./ghidra_12.1.4_PUBLIC/support/analyzeHeadless.bat \
  "c:/workspace/xiaocong/ghidra_proj" XiaoCong \
  -import "c:/workspace/xiaocong/apk_extract/lib/armeabi-v7a/libsoftAp.so" \
  -scriptPath "c:/workspace/xiaocong/tools" \
  -postScript ghidra_dump.java \
  -postScript ghidra_disasm.java \
  -deleteProject
```

> `launch.properties` 里的 `JAVA_HOME_OVERRIDE` 已指向 JDK 21（备份在 `launch.properties.bak`）。

### 可选：图形界面

```bash
export JAVA_HOME="C:/Users/caojiecn/Downloads/jdk-21.0.12.1+1"
./ghidra_12.1.4_PUBLIC/ghidraRun.bat
```

- `File → Import File` → 选 `.so`
- Format 自动识别为 `ELF (ARM, 32-bit)`，Language `ARM:LE:32:v7`
- 双击打开 → 分析（默认选项）
- **两个库带 DWARF**，变量名 / 结构体都是现成的，直接看 Decompiler 窗口即可

---

## 3. 自研脚本

| 脚本 | 作用 | 输出 |
|---|---|---|
| [tools/ghidra_dump.java](../tools/ghidra_dump.java) | 按函数名批量导出 C 伪代码 + 全函数清单 | `<out>/<程序名>/*.c`、`_all_functions.txt` |
| [tools/ghidra_disasm.java](../tools/ghidra_disasm.java) | 反汇编 + 解引用字面量池指针 ★ | `<out>/<程序名>/_disasm.txt` |
| [tools/ghidra_strings.java](../tools/ghidra_strings.java) | 提取函数引用的字符串 | `<out>/<程序名>/_strings_refs.txt` |

### `ghidra_disasm.java` 为什么关键

ARM 上字符串是「字面量池 + `add rX, pc`」间接引用的，Ghidra 反编译里只显示
`DAT_00016464 + 0x16138`，看不出是哪个字符串。这个脚本把指针解引用出来：

```
00016134  add r1,pc         -> 0001c5f2 "type"
000161ec  ldr r3,[r0,#0x0]  -> 0001ee00  *(0001f148)=0x<unmapped>          ← AES KEY（.bss 全局）
000161ee  ldr r0,[r1,#0x0]  -> 0001ee20  *(0001f00c)="abcdefghijklmnop"   ← AES IV
```

**AES 的 key / iv 就是这样定位出来的。** 该方法对所有 ARM 逆向通用。

### 新增要分析的目标

编辑 `ghidra_dump.java` 顶部的 `TARGETS` 数组即可（两个库共用一份清单，
未找到的函数会被列出来提示）。

---

## 4. 踩过的坑

| # | 坑 | 现象 | 解法 |
|---|---|---|---|
| 1 | 用裁剪 JRE | `NoClassDefFoundError: java/rmi/Remote` | 换完整 JDK 21 |
| 2 | JDK 模式强制要 javac | 报 "unsupported java version"（误导） | 同上（真 JDK 自带 javac） |
| 3 | 项目目录不存在 | `FileNotFoundException: Directory not found: ghidra_proj` | 先 `mkdir -p ghidra_proj` |
| 4 | `tools/` 下有子目录 | `class could not be found: ghidra_dump`（错误信息误导） | 清理 `tools/`，只留 `.java` / `.js` / `.sh` |
| 5 | 同名函数互相覆盖 | `app_step0` / `generate_broadcast` 两库都有 | 脚本已按 `<程序名>/` 分目录输出 |
| 6 | `.py` 脚本 | `Ghidra was not started with PyGhidra` | 改用 `.java` |

---

## 5. 动态分析手段（验证固件行为）

反编译已给出确切报文格式，动态抓包现在主要用于**验证固件是否遵守 `domain`/`crt`**。

**方案一：抓包**

```
手机连上 smart-xxx 热点 → tcpdump / Wireshark 抓 UDP 5658
→ 直接看到 ch_pubk / ch_data 完整报文（明文 JSON + base64）
```

**方案二：LD_PRELOAD / Frida hook**

hook `sendto` / `recvfrom` / `AES128_CBC_encrypt_buffer`，打印参数。
（`libsoftAp.so` 里这些符号都未剥离，hook 很好定位。）
