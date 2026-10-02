package com.squareup.leakcanary;

import android.app.Application;
import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class LeakCanary {
    public static RefWatcher install(Application application) {
        return RefWatcher.DISABLED;
    }

    public static boolean isInAnalyzerProcess(Context context) {
        return false;
    }
}
