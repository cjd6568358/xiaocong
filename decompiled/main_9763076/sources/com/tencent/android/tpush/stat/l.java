package com.tencent.android.tpush.stat;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class l implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ long c;

    l(String str, Context context, long j) {
        this.a = str;
        this.b = context;
        this.c = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            synchronized (h.b) {
                try {
                    if (h.b.size() >= c.g()) {
                        h.g.e("The number of page events exceeds the maximum value " + Integer.toString(c.g()));
                    } else {
                        String unused = h.e = this.a;
                        if (h.b.containsKey(h.e)) {
                            h.g.f("Duplicate PageID : " + h.e + ", onResume() repeated?");
                        } else {
                            h.b.put(h.e, Long.valueOf(System.currentTimeMillis()));
                            h.b(this.b, this.c);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Throwable th2) {
            h.g.b(th2);
        }
    }
}
