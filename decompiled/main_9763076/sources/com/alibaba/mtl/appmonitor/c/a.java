package com.alibaba.mtl.appmonitor.c;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: BalancedPool.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a a = new a();
    private Map<Class<? extends b>, c<? extends b>> p = new HashMap();

    public static a a() {
        return a;
    }

    private a() {
    }

    public <T extends b> T a(Class<T> cls, Object... objArr) {
        T tNewInstance;
        b bVarA = a(cls).a();
        if (bVarA == null) {
            try {
                tNewInstance = cls.newInstance();
            } catch (Exception e) {
                com.alibaba.mtl.appmonitor.b.b.m16a((Throwable) e);
                tNewInstance = (T) bVarA;
            }
        } else {
            tNewInstance = (T) bVarA;
        }
        if (tNewInstance != null) {
            tNewInstance.fill(objArr);
        }
        return tNewInstance;
    }

    public <T extends b> void a(T t) {
        if (t != null && !(t instanceof e) && !(t instanceof d)) {
            a(t.getClass()).a(t);
        }
    }

    private synchronized <T extends b> c<T> a(Class<T> cls) {
        c<T> cVar;
        cVar = (c) this.p.get(cls);
        if (cVar == null) {
            cVar = new c<>();
            this.p.put(cls, cVar);
        }
        return cVar;
    }
}
