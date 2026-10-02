package com.tencent.android.tpush.b;

import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class i {
    static ArrayList a;
    private static final String b = i.class.getSimpleName();
    private static volatile i c = null;
    private static long e = 0;
    private Context d = null;

    public static i a(Context context) {
        if (c == null) {
            synchronized (i.class) {
                if (c == null) {
                    c = new i();
                    c.d = context.getApplicationContext();
                    com.tencent.android.tpush.service.n.d(c.d);
                }
            }
        }
        return c;
    }

    public void a(Intent intent) {
        com.tencent.android.tpush.common.g.a().a(new m(this, this.d, intent, null));
    }

    protected static synchronized boolean a(Long l) {
        boolean z = false;
        synchronized (i.class) {
            try {
                if (a == null) {
                    a = new ArrayList();
                }
                if (!a.contains(l)) {
                    a.add(l);
                    if (a.size() > 200) {
                        a.remove(0);
                    }
                    z = true;
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c("PushMessageHandler", "addCachedmsgID", th);
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Intent intent) {
        com.tencent.android.tpush.common.g.a().a(new j(this, intent));
    }

    public void b(Intent intent) {
        com.tencent.android.tpush.common.g.a().a(new k(this, intent));
    }

    public void a(boolean z) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - e > 120000 || z) {
            e = jCurrentTimeMillis;
            com.tencent.android.tpush.common.g.a().a(new l(this));
        }
    }
}
