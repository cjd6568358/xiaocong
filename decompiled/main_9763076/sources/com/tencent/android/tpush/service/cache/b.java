package com.tencent.android.tpush.service.cache;

import java.util.HashMap;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static volatile HashMap a = new HashMap(10);

    public static synchronized void a(Object obj, Object obj2) {
        a.put(obj, obj2);
    }

    public static synchronized Object a(Object obj) {
        return a.get(obj);
    }
}
