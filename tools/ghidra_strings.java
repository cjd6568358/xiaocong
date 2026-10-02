// 解析函数中引用的字符串指针（ARM .data.rel.ro 经 R_ARM_RELATIVE 重定位）
// 输出：func @addr  ->  slot -> *slot(ptr) -> "string"
//@category XiaoCong

import ghidra.app.script.GhidraScript;
import ghidra.program.model.address.Address;
import ghidra.program.model.listing.Function;
import ghidra.program.model.listing.Instruction;
import ghidra.program.model.listing.InstructionIterator;
import ghidra.program.model.mem.Memory;
import ghidra.program.model.scalar.Scalar;
import ghidra.program.model.symbol.Reference;

import java.io.PrintWriter;
import java.io.File;

public class ghidra_strings extends GhidraScript {

    private static final String[] TARGETS = {
        "app_step0", "app_step1", "app_step2", "start_softap_app",
        "generate_broadcast", "create_network", "setskt", "softap_callback",
        "cmdforward", "cmdwait", "sendpacket", "wait_packet",
        "xconfig_init", "xconfig_start", "getGenKeyPara", "generate_getlocalkey",
    };

    private String readCStr(Memory mem, Address a) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 256; i++) {
                byte b = mem.getByte(a.add(i));
                if (b == 0) break;
                if (b < 0x20 || b > 0x7e) { sb.append('.'); continue; }
                sb.append((char) b);
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /** 若是指针槽，解引用后读字符串；否则直接读字符串 */
    private String resolve(Memory mem, Address a) {
        String s = readCStr(mem, a);
        if (s != null && s.length() >= 2 && isPrintable(s)) return s;
        // 当作 4 字节小端指针
        try {
            int p = mem.getInt(a);
            Address target = toAddr(p & 0xffffffffL);
            String s2 = readCStr(mem, target);
            if (s2 != null && s2.length() >= 2 && isPrintable(s2)) {
                return s2 + "  (via *" + a + ")";
            }
        } catch (Exception e) { /* ignore */ }
        return null;
    }

    private boolean isPrintable(String s) {
        int ok = 0;
        for (char c : s.toCharArray()) {
            if (c >= 0x20 && c <= 0x7e) ok++;
        }
        return ok >= s.length() * 0.8;
    }

    @Override
    public void run() throws Exception {
        String outDir = System.getenv("XIAOCONG_OUT");
        if (outDir == null || outDir.isEmpty()) outDir = ".";
        File out = new File(outDir, "ghidra_out");
        out.mkdirs();

        Memory mem = currentProgram.getMemory();
        PrintWriter pw = new PrintWriter(new File(out, "_strings_refs.txt"), "UTF-8");

        for (String name : TARGETS) {
            Function f = getFunction(name);
            if (f == null) {
                pw.println("### " + name + " : NOT FOUND");
                pw.println();
                continue;
            }
            pw.println("### " + name + " @ " + f.getEntryPoint());
            InstructionIterator it = currentProgram.getListing().getInstructions(f.getBody(), true);
            java.util.LinkedHashMap<String, String> seen = new java.util.LinkedHashMap<>();
            while (it.hasNext() && !monitor.isCancelled()) {
                Instruction ins = it.next();
                for (int op = 0; op < ins.getNumOperands(); op++) {
                    for (Object o : ins.getOpObjects(op)) {
                        Address a = null;
                        if (o instanceof Address) {
                            a = (Address) o;
                        } else if (o instanceof Scalar) {
                            long v = ((Scalar) o).getUnsignedValue();
                            if (v > 0x10000 && v < 0x400000) a = toAddr(v);
                        }
                        if (a == null) continue;
                        String s = resolve(mem, a);
                        if (s != null) {
                            seen.putIfAbsent(ins.getAddress() + "  " + s, s);
                        }
                    }
                }
                // 显式引用
                for (Reference r : ins.getReferencesFrom()) {
                    Address a = r.getToAddress();
                    String s = resolve(mem, a);
                    if (s != null) seen.putIfAbsent(ins.getAddress() + "  " + s, s);
                }
            }
            for (String line : seen.keySet()) pw.println("  " + line);
            pw.println();
        }
        pw.close();
        println("[strings] -> " + new File(out, "_strings_refs.txt"));
    }
}
