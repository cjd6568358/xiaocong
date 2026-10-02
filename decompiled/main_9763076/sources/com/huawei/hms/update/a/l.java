package com.huawei.hms.update.a;

import android.os.Handler;
import android.os.Looper;
import java.io.File;

/* JADX INFO: compiled from: ThreadWrapper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class l implements com.huawei.hms.update.a.a.b {
    final /* synthetic */ com.huawei.hms.update.a.a.b a;

    l(com.huawei.hms.update.a.a.b bVar) {
        this.a = bVar;
    }

    @Override // com.huawei.hms.update.a.a.b
    public void a(int i, com.huawei.hms.update.a.a.c cVar) {
        new Handler(Looper.getMainLooper()).post(new m(this, i, cVar));
    }

    @Override // com.huawei.hms.update.a.a.b
    public void a(int i, int i2, int i3, File file) {
        new Handler(Looper.getMainLooper()).post(new n(this, i, i2, i3, file));
    }
}
