package com.tencent.android.tpush.service.channel;

import com.tencent.android.tpush.service.ad;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class h implements ad {
    final /* synthetic */ b a;

    h(b bVar) {
        this.a = bVar;
    }

    @Override // com.tencent.android.tpush.service.ad
    public void a(String str) {
        if (str == null) {
            b.j++;
        } else {
            b.j = 0;
        }
    }
}
