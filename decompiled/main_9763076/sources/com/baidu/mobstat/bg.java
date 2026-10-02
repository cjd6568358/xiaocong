package com.baidu.mobstat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class bg implements Application.ActivityLifecycleCallbacks {
    final /* synthetic */ bf a;

    bg(bf bfVar) {
        this.a = bfVar;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        ch.a().a(activity.getApplicationContext(), System.currentTimeMillis());
        if (this.a.c) {
            this.a.a(activity, true);
            this.a.a(activity);
        }
        ch.a().f();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        ch.a().b(activity.getApplicationContext(), System.currentTimeMillis());
        if (this.a.c) {
            this.a.a(activity, false);
        }
        ch.a().a(activity.getApplicationContext());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        String name = activity.getClass().getName();
        synchronized (this.a.a) {
            this.a.a.remove(name);
        }
    }
}
