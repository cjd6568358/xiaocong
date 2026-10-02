package com.tencent.mid.util;

import android.content.Context;
import com.tencent.mid.api.MidEntity;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class p implements Runnable {
    private int a;
    private Context b;
    private Map<String, MidEntity> c;

    public p(Context context, int i) {
        this.a = i;
        this.b = context;
    }

    public synchronized Map<String, MidEntity> a() {
        return this.c;
    }

    public synchronized void a(Map<String, MidEntity> map) {
        this.c = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a(Util.b(this.b, this.a));
        } catch (Exception e) {
        }
    }
}
