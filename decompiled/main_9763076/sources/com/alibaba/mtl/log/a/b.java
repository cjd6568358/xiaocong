package com.alibaba.mtl.log.a;

import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.l;
import com.alibaba.mtl.log.e.r;
import com.alibaba.mtl.log.e.t;
import java.util.HashMap;

/* JADX INFO: compiled from: GcConfigChannelMgr.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static b a = new b();
    private static String P = "https://adashxgc.ut.taobao.com/rest/gc2";

    public static b a() {
        return a;
    }

    public void r() {
        r.a().b(new a());
    }

    /* JADX INFO: compiled from: GcConfigChannelMgr.java */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            int i = 0;
            if (!l.isConnected()) {
                return;
            }
            while (true) {
                int i2 = i;
                if (i2 < 8) {
                    HashMap map = new HashMap();
                    String strM18b = com.alibaba.mtl.log.a.a.m18b("b01n15");
                    String strM18b2 = com.alibaba.mtl.log.a.a.m18b("b01na");
                    map.put("_b01n15", strM18b);
                    map.put("_b01na", strM18b2);
                    try {
                        String strB = t.b(b.P, map, null);
                        i.a("ConfigMgr", "config:" + strB);
                        com.alibaba.mtl.log.e.e.a aVarA = com.alibaba.mtl.log.e.e.a(1, strB, null, false);
                        if (aVarA.e != null) {
                            com.alibaba.mtl.log.a.a.h(new String(aVarA.e, 0, aVarA.e.length));
                            com.alibaba.mtl.log.a.a.q();
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(10000L);
                    } catch (Exception e2) {
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }
}
