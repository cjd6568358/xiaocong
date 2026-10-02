// 反汇编目标函数，并把字面量池指针解引用成实际地址 + 内容
//@category XiaoCong

import ghidra.app.script.GhidraScript;
import ghidra.program.model.address.Address;
import ghidra.program.model.listing.CodeUnit;
import ghidra.program.model.listing.Function;
import ghidra.program.model.listing.Instruction;
import ghidra.program.model.listing.InstructionIterator;
import ghidra.program.model.mem.Memory;
import ghidra.program.model.symbol.Reference;

import java.io.File;
import java.io.PrintWriter;

public class ghidra_disasm extends GhidraScript {

    private static final String[] TARGETS = {
        "app_step0", "app_step1", "app_step2", "create_ecdh_key",
        "start_softap_app", "generate_broadcast", "setskt", "create_network",
        "xconfig_start", "xconfig_init", "cmdExec", "sendpacket", "wait_packet",
        "easylinkLoop", "device_aes_encrypt", "generate_getlocalkey",
        "Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_cmdExec",
        "Java_com_xiaocong_smarthome_phone_xcsdk_DeviceSdk_polling",
    };

    private String hexDump(Memory mem, Address a, int n) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(String.format("%02x", mem.getByte(a.add(i))));
            }
            return sb.toString();
        } catch (Exception e) {
            return "<unmapped>";
        }
    }

    private String tryStr(Memory mem, Address a) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 64; i++) {
                byte b = mem.getByte(a.add(i));
                if (b == 0) break;
                if (b < 0x20 || b > 0x7e) return null;
                sb.append((char) b);
            }
            return sb.length() >= 2 ? sb.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void run() throws Exception {
        String outDir = System.getenv("XIAOCONG_OUT");
        if (outDir == null || outDir.isEmpty()) outDir = ".";
        File out = new File(new File(outDir, "ghidra_out"), currentProgram.getName());
        out.mkdirs();

        Memory mem = currentProgram.getMemory();
        PrintWriter pw = new PrintWriter(new File(out, "_disasm.txt"), "UTF-8");

        for (String name : TARGETS) {
            Function f = getFunction(name);
            if (f == null) continue;
            pw.println("======== " + name + " @ " + f.getEntryPoint() + " ========");
            InstructionIterator it = currentProgram.getListing().getInstructions(f.getBody(), true);
            while (it.hasNext() && !monitor.isCancelled()) {
                Instruction ins = it.next();
                StringBuilder line = new StringBuilder();
                line.append(String.format("%s  %-40s", ins.getAddress(), ins.toString()));

                // 目标地址（引用）
                for (Reference r : ins.getReferencesFrom()) {
                    Address t = r.getToAddress();
                    if (t == null) continue;
                    String s = tryStr(mem, t);
                    line.append("   -> ").append(t);
                    if (s != null) {
                        line.append(" \"").append(s).append("\"");
                    } else {
                        // 可能是指针槽：解引用 4 字节
                        try {
                            int v = mem.getInt(t);
                            Address t2 = toAddr(v & 0xffffffffL);
                            String s2 = tryStr(mem, t2);
                            if (s2 != null) {
                                line.append("  *(").append(t2).append(")=\"").append(s2).append("\"");
                            } else if (t2 != null && mem.contains(t2)) {
                                line.append("  *(").append(t2).append(")=0x").append(hexDump(mem, t2, 32));
                            }
                        } catch (Exception e) { /* not a pointer */ }
                    }
                }
                pw.println(line);
            }
            pw.println();
        }
        pw.close();
        println("[disasm] -> " + new File(out, "_disasm.txt"));
    }
}
