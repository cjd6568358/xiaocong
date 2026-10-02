package com.alibaba.sdk.android.utils;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AlicloudTrackerManager {
    private static AlicloudTrackerManager a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private c f86a = new c();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.utils.crashdefend.c f87a;
    private Map<String, AlicloudTracker> c;

    private AlicloudTrackerManager(Application application) {
        this.f87a = null;
        HashMap map = new HashMap(4);
        map.put("kVersion", "1.1.3");
        map.put(Constants.FLAG_PACKAGE_NAME, application.getPackageName());
        this.f86a.a(application, map);
        this.c = new HashMap();
        this.f87a = com.alibaba.sdk.android.utils.crashdefend.c.a(application, this.f86a);
    }

    public static synchronized AlicloudTrackerManager getInstance(Application application) {
        AlicloudTrackerManager alicloudTrackerManager;
        if (application == null) {
            alicloudTrackerManager = null;
        } else {
            if (a == null) {
                a = new AlicloudTrackerManager(application);
            }
            alicloudTrackerManager = a;
        }
        return alicloudTrackerManager;
    }

    public AlicloudTracker getTracker(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Log.e("AlicloudTrackerManager", "sdkId or sdkVersion is null");
            return null;
        }
        String str3 = str + str2;
        if (this.c.containsKey(str3)) {
            return this.c.get(str3);
        }
        AlicloudTracker alicloudTracker = new AlicloudTracker(this.f86a, str, str2);
        this.c.put(str3, alicloudTracker);
        return alicloudTracker;
    }

    public boolean registerCrashDefend(String str, String str2, int i, int i2, SDKMessageCallback sDKMessageCallback) {
        if (this.f87a == null) {
            return false;
        }
        com.alibaba.sdk.android.utils.crashdefend.d dVar = new com.alibaba.sdk.android.utils.crashdefend.d();
        dVar.f99a = str;
        dVar.f101b = str2;
        dVar.a = i;
        dVar.b = i2;
        return this.f87a.m55a(dVar, sDKMessageCallback);
    }

    public void unregisterCrashDefend(String str, String str2) {
        this.f87a.d(str, str2);
    }
}
