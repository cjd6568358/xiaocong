package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class v implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ com.tencent.wxop.stat.event.c b;
    final /* synthetic */ Context c;

    v(String str, com.tencent.wxop.stat.event.c cVar, Context context) {
        this.a = str;
        this.b = cVar;
        this.c = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (StatServiceImpl.a(this.a)) {
                StatServiceImpl.q.error("The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
                return;
            }
            if (StatConfig.isDebugEnable()) {
                StatServiceImpl.q.i("add begin key:" + this.b.toString());
            }
            if (StatServiceImpl.e.containsKey(this.b)) {
                StatServiceImpl.q.error("Duplicate CustomEvent key: " + this.b.toString() + ", trackCustomBeginEvent() repeated?");
            } else if (StatServiceImpl.e.size() <= StatConfig.getMaxParallelTimmingEvents()) {
                StatServiceImpl.e.put(this.b, Long.valueOf(System.currentTimeMillis()));
            } else {
                StatServiceImpl.q.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
            }
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
            StatServiceImpl.a(this.c, th);
        }
    }
}
