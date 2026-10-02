package com.tencent.android.tpush.stat;

import java.util.Arrays;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class j implements e {
    final /* synthetic */ com.tencent.android.tpush.stat.event.d a;

    j(com.tencent.android.tpush.stat.event.d dVar) {
        this.a = dVar;
    }

    @Override // com.tencent.android.tpush.stat.e
    public void a() {
        h.g.h("send Event sucess:" + this.a.b());
    }

    @Override // com.tencent.android.tpush.stat.e
    public void b() {
        h.b(Arrays.asList(this.a));
    }
}
