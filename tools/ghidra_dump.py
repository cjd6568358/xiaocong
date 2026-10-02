# Ghidra headless 批量导出重点函数的 C 伪代码
# 用法见 docs/06-Ghidra操作手册.md
#
# analyzeHeadless <projdir> <projname> -import <so> -postScript ghidra_dump.py -scriptPath tools
#@category XiaoCong

import os

TARGETS = [
    # libsoftAp.so
    "app_step0", "app_step1", "app_step2", "start_softap_app",
    "generate_broadcast", "create_network",
    "uECC_make_key", "uECC_shared_secret", "uECC_compress", "uECC_decompress",
    "AES128_CBC_encrypt_buffer", "AES128_CBC_decrypt_buffer",
    # libxcsdk.so
    "xconfig_init", "xconfig_start", "xconfig_stop",
    "getGenKeyPara", "generate_getlocalkey",
    "cmdforward", "cmdwait", "sendpacket", "wait_packet",
    "device_aes_encrypt", "device_aes_decrypt",
    "easylinkLoop", "payloadBroadcast", "payloadMuticast",
    "json2device", "device2mallocjson", "getDeviceById", "polling",
]

OUT = os.path.join(os.environ.get("XIAOCONG_OUT", "."), "ghidra_out")
if not os.path.isdir(OUT):
    os.makedirs(OUT)

fm = currentProgram.getFunctionManager()
decomp = ghidra.app.decompiler.DecompInterface()
decomp.openProgram(currentProgram)

found = 0
for f in fm.getFunctions(True):
    name = f.getName()
    if name not in TARGETS:
        continue
    found += 1
    res = decomp.decompileFunction(f, 60, monitor)
    if res and res.decompileCompleted():
        code = res.getDecompiledFunction().getC()
    else:
        code = "// 反编译失败: %s" % res.getErrorMessage()
    path = os.path.join(OUT, "%s.c" % name)
    with open(path, "w") as fh:
        fh.write("// %s  @ %s\n\n%s\n" % (name, f.getEntryPoint(), code))
    print("[dump] %s -> %s" % (name, path))

print("[dump] 共导出 %d 个函数到 %s" % (found, OUT))
