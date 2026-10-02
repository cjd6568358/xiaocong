package com.xiaomi.mipush.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class v extends Handler {
    final /* synthetic */ u a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    v(u uVar, Looper looper) {
        super(looper);
        this.a = uVar;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        String str = (String) message.obj;
        int i = message.arg1;
        synchronized (p.class) {
            if (p.a(this.a.c).e(str)) {
                if (p.a(this.a.c).c(str) < 10) {
                    if (1 == i && "disable_syncing".equals(p.a(this.a.c).a())) {
                        this.a.a(str, true);
                    } else if (i == 0 && "enable_syncing".equals(p.a(this.a.c).a())) {
                        this.a.a(str, false);
                    }
                    p.a(this.a.c).b(str);
                } else {
                    p.a(this.a.c).d(str);
                }
            }
        }
    }
}
