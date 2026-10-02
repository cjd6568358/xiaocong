package com.tencent.android.tpush.stat.b;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class h {
    protected com.tencent.android.tpush.stat.a.f a = com.tencent.android.tpush.stat.a.e.b();
    protected Context b;
    protected int c;

    public abstract int a();

    protected abstract void a(String str);

    protected abstract boolean b();

    protected abstract String c();

    public String d() {
        return this.c == 0 ? com.tencent.android.tpush.stat.a.h.a("6X8Y4XdM2Vhvn0I=") : com.tencent.android.tpush.stat.a.h.a("6X8Y4XdM2Vhvn0I=") + this.c;
    }

    public String e() {
        return this.c == 0 ? com.tencent.android.tpush.stat.a.h.a("6X8Y4XdM2Vhvn0KfzcEatGnWaNU=") : com.tencent.android.tpush.stat.a.h.a("6X8Y4XdM2Vhvn0KfzcEatGnWaNU=") + this.c;
    }

    protected String f() {
        return this.c == 0 ? com.tencent.android.tpush.stat.a.h.a("4kU71lN96TJUomD1vOU9lgj9Tw==") : com.tencent.android.tpush.stat.a.h.a("4kU71lN96TJUomD1vOU9lgj9Tw==") + this.c;
    }

    protected h(Context context, int i) {
        this.b = null;
        this.c = 0;
        this.b = context;
        this.c = i;
    }

    private String h() {
        if (b()) {
            return d(c());
        }
        return null;
    }

    public d g() {
        String strH = h();
        if (strH != null) {
            return d.a(strH);
        }
        return null;
    }

    private void b(String str) {
        if (b()) {
            a(c(str));
        }
    }

    public void a(d dVar) {
        if (dVar != null) {
            if (a() == 4) {
                e.a(this.b).a(dVar.e());
            }
            b(dVar.toString());
        }
    }

    protected String c(String str) {
        return com.tencent.android.tpush.stat.a.h.b(str);
    }

    protected String d(String str) {
        return com.tencent.android.tpush.stat.a.h.a(str);
    }
}
