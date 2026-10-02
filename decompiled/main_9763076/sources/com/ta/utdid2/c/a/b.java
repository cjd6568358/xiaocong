package com.ta.utdid2.c.a;

import java.util.Map;

/* JADX INFO: compiled from: MySharedPreferences.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface b {

    /* JADX INFO: compiled from: MySharedPreferences.java */
    public interface a {
        a a(String str);

        a a(String str, float f);

        a a(String str, int i);

        a a(String str, long j);

        a a(String str, String str2);

        a a(String str, boolean z);

        a b();

        boolean commit();
    }

    /* JADX INFO: renamed from: com.ta.utdid2.c.a.b$b, reason: collision with other inner class name */
    /* JADX INFO: compiled from: MySharedPreferences.java */
    public interface InterfaceC0038b {
        void a(b bVar, String str);
    }

    a a();

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    boolean mo100a();

    Map<String, ?> getAll();

    long getLong(String str, long j);

    String getString(String str, String str2);
}
