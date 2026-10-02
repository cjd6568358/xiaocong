package com.alibaba.sdk.android.httpdns.probe;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class d {
    private static IPProbeService a = null;

    public static synchronized IPProbeService a(b bVar) {
        if (a == null) {
            a = new e();
            a.setIPListUpdateCallback(bVar);
        }
        return a;
    }
}
