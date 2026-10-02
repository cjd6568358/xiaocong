package com.tencent.android.tpush.stat.b;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g extends h {
    public g(Context context, int i) {
        super(context, i);
    }

    @Override // com.tencent.android.tpush.stat.b.h
    public int a() {
        return 1;
    }

    @Override // com.tencent.android.tpush.stat.b.h
    protected boolean b() {
        return com.tencent.android.tpush.stat.a.h.a(this.b, "android.permission.WRITE_SETTINGS");
    }

    @Override // com.tencent.android.tpush.stat.b.h
    protected String c() {
        String strA;
        synchronized (this) {
            strA = com.tencent.android.tpush.service.channel.c.f.a(this.b).a(f());
        }
        return strA;
    }

    @Override // com.tencent.android.tpush.stat.b.h
    protected void a(String str) {
        synchronized (this) {
            this.a.b("write mid to Settings.System");
            com.tencent.android.tpush.service.channel.c.f.a(this.b).a(f(), str);
        }
    }
}
