package com.xiaocong.smarthome.xcnetwork.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SPUtil {
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferences mPreferences;
    private static SPUtil mSharedPreferencesUtil;

    public SPUtil(Context context) {
        mPreferences = context.getSharedPreferences("XCNetWorkSP", 0);
        mEditor = mPreferences.edit();
    }

    public static SPUtil getInstance(Context context) {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SPUtil(context);
        }
        return mSharedPreferencesUtil;
    }

    public void putSP(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public String getSP(String key) {
        return mPreferences.getString(key, "");
    }

    public void removeSP(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }
}
