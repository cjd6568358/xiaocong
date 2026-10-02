package com.baidu.mobstat;

import android.content.Context;
import android.content.Intent;
import com.baidu.bottom.service.BottomReceiver;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class at extends Thread {
    final /* synthetic */ Context a;
    final /* synthetic */ Intent b;
    final /* synthetic */ dd c;
    final /* synthetic */ BottomReceiver d;

    public at(BottomReceiver bottomReceiver, Context context, Intent intent, dd ddVar) {
        this.d = bottomReceiver;
        this.a = context;
        this.b = intent;
        this.c = ddVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            this.d.b(this.a, this.b);
            this.d.a(this.a, this.b);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - BottomReceiver.b < 30000) {
                bd.a("No need to handle receiver due to time strategy");
                this.c.b();
                dd unused = BottomReceiver.a = null;
            } else {
                long unused2 = BottomReceiver.b = jCurrentTimeMillis;
                ao.c.a(this.a);
                this.c.b();
                dd unused3 = BottomReceiver.a = null;
            }
        } catch (Throwable th) {
            this.c.b();
            dd unused4 = BottomReceiver.a = null;
            throw th;
        }
    }
}
