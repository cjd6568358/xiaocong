package com.baidu.mobstat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class au {
    private static l a;

    /* JADX WARN: Code duplicated, block: B:23:0x0048  */
    public static synchronized l a(Context context) {
        l awVar;
        bd.a("getBPStretegyController begin");
        l lVar = a;
        if (lVar == null) {
            try {
                Class<?> clsA = ax.a(context, "com.baidu.bottom.remote.BPStretegyController2");
                if (clsA != null) {
                    awVar = new aw(clsA.newInstance());
                    try {
                        bd.a("Get BPStretegyController load remote class v2");
                    } catch (Exception e) {
                        lVar = awVar;
                        e = e;
                        bd.a(e);
                        awVar = lVar;
                    }
                } else {
                    awVar = lVar;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } else {
            awVar = lVar;
        }
        if (awVar == null) {
            awVar = new av();
            bd.a("Get BPStretegyController load local class");
        }
        a = awVar;
        ax.a(context, awVar);
        bd.a("getBPStretegyController end");
        return awVar;
    }

    public static synchronized void a() {
        a = null;
    }
}
