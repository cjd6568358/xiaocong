package com.alibaba.mtl.appmonitor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.d.j;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;

/* JADX INFO: compiled from: BackgroundTrigger.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a implements Runnable {
    private static boolean j = false;
    private static boolean l = false;
    private Application b;
    private boolean k = true;

    @TargetApi(14)
    public static void init(Application application) {
        if (!j) {
            i.a("BackgroundTrigger", "init BackgroundTrigger");
            l = a(application.getApplicationContext());
            a aVar = new a(application);
            if (l) {
                r.a().a(4, aVar, 60000L);
            } else if (Build.VERSION.SDK_INT >= 14) {
                aVar.getClass();
                application.registerActivityLifecycleCallbacks(aVar.new C0000a(aVar));
            }
            j = true;
        }
    }

    public a(Application application) {
        this.b = application;
    }

    @Override // java.lang.Runnable
    public void run() {
        int i = 0;
        i.a("BackgroundTrigger", "[bg check]");
        boolean zB = com.alibaba.mtl.log.e.b.b(this.b.getApplicationContext());
        if (this.k != zB) {
            this.k = zB;
            if (zB) {
                j.a().k();
                f[] fVarArrValues = f.values();
                int length = fVarArrValues.length;
                while (i < length) {
                    f fVar = fVarArrValues[i];
                    AppMonitorDelegate.setStatisticsInterval(fVar, fVar.c());
                    i++;
                }
                com.alibaba.mtl.log.a.m();
            } else {
                f[] fVarArrValues2 = f.values();
                int length2 = fVarArrValues2.length;
                while (i < length2) {
                    f fVar2 = fVarArrValues2[i];
                    AppMonitorDelegate.setStatisticsInterval(fVar2, fVar2.d());
                    i++;
                }
                AppMonitorDelegate.triggerUpload();
                com.alibaba.mtl.log.a.l();
            }
        }
        if (l) {
            r.a().a(4, this, 60000L);
        }
    }

    /* JADX INFO: renamed from: com.alibaba.mtl.appmonitor.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: BackgroundTrigger.java */
    @TargetApi(14)
    class C0000a implements Application.ActivityLifecycleCallbacks {

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private Runnable f12a;

        C0000a(Runnable runnable) {
            this.f12a = runnable;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            r.a().f(4);
            r.a().a(4, this.f12a, 60000L);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            r.a().f(4);
            r.a().a(4, this.f12a, 60000L);
        }
    }

    private static boolean a(Context context) {
        String strA = com.alibaba.mtl.log.e.b.a(context);
        i.a("BackgroundTrigger", "[checkRuningProcess]:", strA);
        return (TextUtils.isEmpty(strA) || strA.indexOf(":") == -1) ? false : true;
    }
}
