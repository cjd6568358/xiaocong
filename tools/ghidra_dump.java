// Ghidra headless 批量导出重点函数的 C 伪代码（Java 版，Ghidra 12 无 Jython）
// 用法：
//   analyzeHeadless <projdir> <projname> -import <so> -scriptPath tools -postScript ghidra_dump.java
//@category XiaoCong

import ghidra.app.decompiler.DecompileResults;
import ghidra.app.decompiler.DecompInterface;
import ghidra.app.script.GhidraScript;
import ghidra.program.model.listing.Function;
import ghidra.program.model.listing.FunctionManager;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ghidra_dump extends GhidraScript {

    private static final String[] TARGETS = {
        // libsoftAp.so —— 配网
        "app_step0", "app_step1", "app_step2", "start_softap_app",
        "generate_broadcast", "create_network", "setskt", "softap_callback",
        "create_ecdh_key", "make_key", "base64_encode", "base64_decode",
        "Java_com_ixiaocong_smarthome_phone_softap_sdk_SoftApSDK_startSoftAp",
        "Java_com_ixiaocong_smarthome_phone_softap_sdk_SoftApSDK_startCoap",
        "uECC_make_key", "uECC_shared_secret", "uECC_compress", "uECC_decompress",
        "AES128_CBC_encrypt_buffer", "AES128_CBC_decrypt_buffer",
        // libxcsdk.so —— 协议层 / 局域网 / JNI
        "xconfig_init", "xconfig_start", "xconfig_stop",
        "getGenKeyPara", "getScanPara", "generate_getlocalkey",
        "cmdforward", "cmdwait", "cmdExec",
        "sendpacket", "wait_packet", "wait_socket",
        "device_aes_encrypt", "device_aes_decrypt", "AesSetKeyLocal",
        "easylinkLoop", "payloadBroadcast", "payloadMuticast",
        "json2device", "device2mallocjson", "getDeviceById", "polling",
        "network_newudp", "network_create", "parse_packet", "sdkInit",
        "isTimeOut", "javaEventNotify", "JNI_OnLoad", "tcppipe", "dump8",
        "broadcast", "wait_packet", "makeKey", "uncompress", "hitBeyond",
        "Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_cmdExec",
        "Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_polling",
        "Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_XConfigStart",
        "Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_XConfigStop",
        "Java_com_ixiaocong_smarthome_phone_softap_sdk_SoftApSDK_startSoftAp",
        "Java_com_ixiaocong_smarthome_phone_softap_sdk_SoftApSDK_startCoap",
    };

    @Override
    public void run() throws Exception {
        String outDir = System.getenv("XIAOCONG_OUT");
        if (outDir == null || outDir.isEmpty()) {
            outDir = ".";
        }
        // 按库名分目录，避免同名函数（app_step0 / generate_broadcast 等）互相覆盖
        File out = new File(new File(outDir, "ghidra_out"), currentProgram.getName());
        if (!out.isDirectory() && !out.mkdirs()) {
            println("[dump] 无法创建输出目录: " + out);
            return;
        }

        DecompInterface decomp = new DecompInterface();
        decomp.openProgram(currentProgram);

        FunctionManager fm = currentProgram.getFunctionManager();
        Set<String> targets = new HashSet<>(Arrays.asList(TARGETS));
        Set<String> found = new TreeSet<>();

        // 完整函数清单，便于后续定位
        PrintWriter list = new PrintWriter(new File(out, "_all_functions.txt"), "UTF-8");
        int dumped = 0;
        for (Function f : fm.getFunctions(true)) {
            list.println(f.getEntryPoint() + "\t" + f.getName());
            if (!targets.contains(f.getName())) {
                continue;
            }
            found.add(f.getName());
            dumped++;

            DecompileResults res = decomp.decompileFunction(f, 120, monitor);
            String code;
            if (res != null && res.decompileCompleted()) {
                code = res.getDecompiledFunction().getC();
            } else {
                code = "// 反编译失败: " + (res == null ? "null" : res.getErrorMessage());
            }

            File cf = new File(out, f.getName() + ".c");
            PrintWriter pw = new PrintWriter(cf, "UTF-8");
            pw.println("// " + f.getName() + "  @ " + f.getEntryPoint());
            pw.println();
            pw.println(code);
            pw.close();
            println("[dump] " + f.getName() + " -> " + cf);
        }
        list.close();

        Set<String> missing = new TreeSet<>(targets);
        missing.removeAll(found);
        if (!missing.isEmpty()) {
            println("[dump] 未找到的目标函数: " + missing);
        }
        println("[dump] 共导出 " + dumped + " 个函数到 " + out);
    }
}
