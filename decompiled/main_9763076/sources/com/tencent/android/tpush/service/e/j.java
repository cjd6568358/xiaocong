package com.tencent.android.tpush.service.e;

import android.content.Context;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class j implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ JSONObject b;

    j(Context context, JSONObject jSONObject) {
        this.a = context;
        this.b = jSONObject;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.tencent.android.tpush.service.d.a.b(this.a, "location", this.b);
    }
}
