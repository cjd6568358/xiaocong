package com.tencent.android.tpush;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class g implements Application.ActivityLifecycleCallbacks {
    g() {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (activity != null && activity.getComponentName().getClassName() != null && activity.getApplicationContext() != null) {
            com.tencent.android.tpush.stat.h.b(activity.getApplicationContext(), activity.getComponentName().getClassName(), XGPushConfig.getAccessId(activity.getApplicationContext()));
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        if (activity != null && activity.getComponentName().getClassName() != null && activity.getApplicationContext() != null) {
            if (activity.getComponentName().getClassName().equals(XGPushActivity.activityName)) {
                com.tencent.android.tpush.stat.h.c(activity.getApplicationContext(), activity.getComponentName().getClassName(), XGPushConfig.getAccessId(activity.getApplicationContext()));
            } else {
                com.tencent.android.tpush.stat.h.a(activity.getApplicationContext(), activity.getComponentName().getClassName(), XGPushConfig.getAccessId(activity.getApplicationContext()), XGPushActivity.msgId, XGPushActivity.msgBuildId);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }
}
