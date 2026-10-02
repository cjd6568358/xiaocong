package com.tencent.android.tpush.service.cache;

import android.content.Context;
import java.util.Map;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a implements Runnable {
    private Context a;
    private Map b;

    public a(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a(com.tencent.android.tpush.a.e(CacheManager.getContext()));
        } catch (Exception e) {
        }
    }

    public synchronized void a(Map map) {
        this.b = map;
    }

    public synchronized Map a() {
        return this.b;
    }
}
