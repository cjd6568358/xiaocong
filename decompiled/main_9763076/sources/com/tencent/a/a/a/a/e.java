package com.tencent.a.a.a.a;

import android.content.Context;
import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class e extends f {
    public e(Context context) {
        super(context);
    }

    @Override // com.tencent.a.a.a.a.f
    protected final void a(String str) {
        synchronized (this) {
            Log.i("MID", "write mid to Settings.System");
            com.tencent.wxop.stat.common.g.a(this.a).a(h.f("4kU71lN96TJUomD1vOU9lgj9Tw=="), str);
        }
    }

    @Override // com.tencent.a.a.a.a.f
    protected final boolean a() {
        return h.a(this.a, "android.permission.WRITE_SETTINGS");
    }

    @Override // com.tencent.a.a.a.a.f
    protected final String b() {
        String strA;
        synchronized (this) {
            Log.i("MID", "read mid from Settings.System");
            strA = com.tencent.wxop.stat.common.g.a(this.a).a(h.f("4kU71lN96TJUomD1vOU9lgj9Tw=="));
        }
        return strA;
    }
}
