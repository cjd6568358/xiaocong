package com.xiaomi.push.service.timers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import com.xiaomi.channel.commonutils.android.j;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class a {
    private static InterfaceC0014a a;

    /* JADX INFO: renamed from: com.xiaomi.push.service.timers.a$a, reason: collision with other inner class name */
    interface InterfaceC0014a {
        void a();

        void a(boolean z);

        boolean b();
    }

    public static synchronized void a() {
        if (a != null) {
            a.a();
        }
    }

    public static void a(Context context) {
        boolean z = false;
        Context applicationContext = context.getApplicationContext();
        if ("com.xiaomi.xmsf".equals(applicationContext.getPackageName())) {
            a = new b(applicationContext);
            return;
        }
        try {
            PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 4);
            if (packageInfo.services != null) {
                ServiceInfo[] serviceInfoArr = packageInfo.services;
                for (ServiceInfo serviceInfo : serviceInfoArr) {
                    if ("com.xiaomi.push.service.XMJobService".equals(serviceInfo.name) && "android.permission.BIND_JOB_SERVICE".equals(serviceInfo.permission)) {
                        z = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("check service err : " + e.getMessage());
        }
        if (!z && j.b(applicationContext)) {
            throw new RuntimeException("Should export service: com.xiaomi.push.service.XMJobService with permission android.permission.BIND_JOB_SERVICE in AndroidManifest.xml file");
        }
        if (Build.VERSION.SDK_INT < 21 || !z) {
            a = new b(applicationContext);
            return;
        }
        try {
            if (Class.forName("android.app.job.JobService").getDeclaredField("mBinder") != null) {
                a = new c(applicationContext);
            }
        } catch (Exception e2) {
            a = new b(applicationContext);
        }
    }

    public static synchronized void a(boolean z) {
        if (a == null) {
            com.xiaomi.channel.commonutils.logger.b.a("timer is not initialized");
        } else {
            a.a(z);
        }
    }

    public static synchronized boolean b() {
        return a == null ? false : a.b();
    }
}
