package com.ta.utdid2.b.a;

import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: DebugUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private static Class<?> a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static Method f110a;
    private static Method b;
    public static boolean e;

    public static int getInt(String key, int def) {
        a();
        try {
            return ((Integer) b.invoke(a, key, Integer.valueOf(def))).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return def;
        }
    }

    static {
        e = getInt("alidebug", 0) == 1;
        a = null;
        f110a = null;
        b = null;
    }

    private static void a() {
        try {
            if (a == null) {
                a = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
                f110a = a.getDeclaredMethod("get", String.class);
                b = a.getDeclaredMethod("getInt", String.class, Integer.TYPE);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
