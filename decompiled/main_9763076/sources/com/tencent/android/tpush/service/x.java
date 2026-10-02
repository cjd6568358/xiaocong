package com.tencent.android.tpush.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class x {
    private static x a = new x();
    private static AlarmManager b = null;

    private x() {
    }

    public static x a() {
        if (b == null) {
            b();
        }
        return a;
    }

    public void a(int i, long j, PendingIntent pendingIntent) {
        if (b != null) {
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    Method declaredMethod = b.getClass().getDeclaredMethod("setAndAllowWhileIdle", Integer.class, Long.class, PendingIntent.class);
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(b, Integer.valueOf(i), Long.valueOf(j), pendingIntent);
                    return;
                } catch (Throwable th) {
                    com.tencent.android.tpush.a.a.g(Constants.LogTag, th.getMessage());
                }
            }
            b.set(i, j, pendingIntent);
        }
    }

    private static synchronized void b() {
        if (b == null && n.f() != null) {
            b = (AlarmManager) n.f().getSystemService("alarm");
        }
    }
}
