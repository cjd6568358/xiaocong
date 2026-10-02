package com.tencent.android.tpush.service;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.text.TextUtils;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class aa {
    private static final String a = aa.class.getSimpleName();
    private static volatile aa c = null;
    private Context b;
    private boolean d;
    private Handler e;
    private volatile boolean f = false;
    private long g = 0;

    private aa(Context context) {
        this.b = null;
        this.d = true;
        this.e = null;
        this.b = context.getApplicationContext();
        this.d = c();
        HandlerThread handlerThread = new HandlerThread(aa.class.getName());
        handlerThread.start();
        this.e = new Handler(handlerThread.getLooper());
    }

    public static aa a(Context context) {
        if (c == null) {
            synchronized (aa.class) {
                if (c == null) {
                    c = new aa(context);
                }
            }
        }
        return c;
    }

    private String b() {
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) this.b.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    private boolean c() {
        String strB = b();
        if (!TextUtils.isEmpty(strB) && strB.contains("xg_service")) {
            com.tencent.android.tpush.a.a.e(a, "is xg_service");
            return true;
        }
        com.tencent.android.tpush.a.a.e(a, "not xg_service");
        return false;
    }

    public void a() {
    }
}
