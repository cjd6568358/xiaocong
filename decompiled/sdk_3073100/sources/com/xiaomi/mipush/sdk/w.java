package com.xiaomi.mipush.sdk;

import android.database.ContentObserver;
import android.os.Handler;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.push.service.ao;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class w extends ContentObserver {
    final /* synthetic */ u a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    w(u uVar, Handler handler) {
        super(handler);
        this.a = uVar;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        this.a.h = Integer.valueOf(ao.a(this.a.c).b());
        if (this.a.h.intValue() != 0) {
            this.a.c.getContentResolver().unregisterContentObserver(this);
            if (d.d(this.a.c)) {
                this.a.d();
            }
        }
    }
}
