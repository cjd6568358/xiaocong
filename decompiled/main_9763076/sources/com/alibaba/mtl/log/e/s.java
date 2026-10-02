package com.alibaba.mtl.log.e;

import java.util.Map;

/* JADX INFO: compiled from: UTAdapter.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class s {
    public static void send(Map<String, String> args) {
        Object objA;
        try {
            Object objA2 = o.a("com.ut.mini.UTAnalytics", "getInstance");
            if (objA2 != null && (objA = o.a(objA2, "getDefaultTracker")) != null) {
                o.a(objA, "send", new Object[]{args}, Map.class);
            }
        } catch (Exception e) {
        }
    }
}
