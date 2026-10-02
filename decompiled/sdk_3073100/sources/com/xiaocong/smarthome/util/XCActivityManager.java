package com.xiaocong.smarthome.util;

import android.app.Activity;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCActivityManager {
    private static XCActivityManager sInstance = new XCActivityManager();
    private WeakReference<Activity> sCurrentActivityWeakRef;

    private XCActivityManager() {
    }

    public static XCActivityManager getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        if (this.sCurrentActivityWeakRef == null) {
            return null;
        }
        Activity currentActivity = this.sCurrentActivityWeakRef.get();
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        this.sCurrentActivityWeakRef = new WeakReference<>(activity);
    }
}
