package com.huawei.hms.support.log;

import android.content.Context;

/* JADX INFO: compiled from: LogAdaptor.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static final c a = new com.huawei.hms.support.log.a.a.C0020a(new com.huawei.hms.support.log.a.a());
    private int b = 4;
    private String c;

    public void a(Context context, int i, String str) {
        this.b = i;
        this.c = str;
        a.a(context, "HMSCore");
    }

    public boolean a(int i) {
        return i >= this.b;
    }

    public void a(int i, String str, String str2) {
        if (a(i)) {
            d dVarA = a(i, str, str2, null);
            a.a(dVarA.a() + dVarA.b(), i, str, str2);
        }
    }

    public void a(String str, String str2) {
        d dVarA = a(4, str, str2, null);
        a.a(dVarA.a() + '\n' + dVarA.b(), 4, str, str2);
    }

    private d a(int i, String str, String str2, Throwable th) {
        d dVar = new d(8, this.c, i, str);
        dVar.a(str2);
        dVar.a(th);
        return dVar;
    }
}
