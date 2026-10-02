package com.baidu.mobstat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class bm {
    private static Handler c;
    HashMap<String, Object> a = new HashMap<>();
    private static HandlerThread b = new HandlerThread("EventHandleThread");
    private static bm d = new bm();

    private bm() {
        b.start();
        b.setPriority(10);
        c = new Handler(b.getLooper());
    }

    public static bm a() {
        return d;
    }

    public void a(Context context, String str, String str2, int i, long j, long j2, String str3, String str4, int i2, boolean z) {
        DataCore.instance().putEvent(context, str, str2, i, j, j2, str3, str4, i2, z, null, null);
        DataCore.instance().flush(context);
    }

    public void a(Context context, String str, String str2, int i, long j, String str3, String str4, int i2, boolean z) {
        c.post(new bn(this, context, str, str2, i, j, str3, str4, i2, z));
    }
}
