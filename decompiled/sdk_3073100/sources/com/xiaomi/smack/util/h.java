package com.xiaomi.smack.util;

import android.content.Context;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class h extends com.xiaomi.channel.commonutils.misc.h.b {
    final /* synthetic */ Context a;

    h(Context context) {
        this.a = context;
    }

    @Override // com.xiaomi.channel.commonutils.misc.h.b
    public void b() {
        ArrayList arrayList;
        synchronized (g.c) {
            arrayList = new ArrayList(g.d);
            g.d.clear();
        }
        g.b(this.a, arrayList);
    }
}
