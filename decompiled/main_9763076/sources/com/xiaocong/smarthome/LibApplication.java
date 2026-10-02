package com.xiaocong.smarthome;

import android.app.Application;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LibApplication {
    private static Application mApplication;
    private static LibApplication mInstance;

    public static Application getApplication() {
        return mApplication;
    }

    private LibApplication() {
    }

    public static synchronized LibApplication getInstance() {
        if (mInstance == null) {
            mInstance = new LibApplication();
        }
        return mInstance;
    }

    public void init(Application application) {
        mApplication = application;
    }
}
