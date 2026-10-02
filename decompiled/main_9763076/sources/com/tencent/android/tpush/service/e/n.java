package com.tencent.android.tpush.service.e;

import android.content.Context;
import android.net.Uri;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class n implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;

    n(Context context, String str, String str2) {
        this.a = context;
        this.b = str;
        this.c = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (!m.a(this.a, this.b)) {
                this.a.getContentResolver().getType(Uri.parse("content://" + this.c));
            }
        } catch (Throwable th) {
        }
    }
}
