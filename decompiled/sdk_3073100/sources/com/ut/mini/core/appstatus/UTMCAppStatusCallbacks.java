package com.ut.mini.core.appstatus;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface UTMCAppStatusCallbacks {
    void onActivityCreated(Activity activity, Bundle bundle);

    void onActivityDestroyed(Activity activity);

    void onActivityPaused(Activity activity);

    void onActivityResumed(Activity activity);

    void onActivitySaveInstanceState(Activity activity, Bundle bundle);

    void onActivityStarted(Activity activity);

    void onActivityStopped(Activity activity);

    void onSwitchBackground();

    void onSwitchForeground();
}
