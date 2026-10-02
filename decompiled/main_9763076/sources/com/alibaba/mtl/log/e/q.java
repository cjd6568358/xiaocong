package com.alibaba.mtl.log.e;

import android.util.Log;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: SystemProperties.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class q {
    private static final String TAG = q.class.getSimpleName();

    public static String get(String key) {
        try {
            Class<?> cls = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            return (String) cls.getMethod("get", String.class).invoke(cls.newInstance(), key);
        } catch (Exception e) {
            Log.e(TAG, "get() ERROR!!! Exception!", e);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String get(String key, String defaultValue) {
        try {
            Class<?> cls = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls.newInstance(), key, defaultValue);
        } catch (Exception e) {
            Log.e(TAG, "get() ERROR!!! Exception!", e);
            return Constants.MAIN_VERSION_TAG;
        }
    }
}
