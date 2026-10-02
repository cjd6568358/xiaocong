package com.hzy.tvmao.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.hzy.tvmao.KookongSDK;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DataStoreUtil {
    public static final String FILE_USERGUIDE_CONFIG = "USERGUIDE_CONFIG";
    private static DataStoreUtil sDataStoreUtil;
    public final String DATASTORE = "datastore";
    protected SharedPreferences mSharedPreferences;

    public static DataStoreUtil i() {
        return i(null);
    }

    public static DataStoreUtil i(String str) {
        if (sDataStoreUtil == null) {
            sDataStoreUtil = new DataStoreUtil();
        }
        sDataStoreUtil.setPath(str);
        return sDataStoreUtil;
    }

    protected void setPath(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mSharedPreferences = KookongSDK.getContext().getSharedPreferences("datastore", 0);
        } else {
            this.mSharedPreferences = KookongSDK.getContext().getSharedPreferences("datastore_" + str, 0);
        }
    }

    public void putBoolean(String str, boolean z) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        editorEdit.putBoolean(str, z);
        editorEdit.commit();
    }

    public boolean getBoolean(String str, Boolean bool) {
        return this.mSharedPreferences.getBoolean(str, bool.booleanValue());
    }

    public void putFloat(String str, float f) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        editorEdit.putFloat(str, f);
        editorEdit.commit();
    }

    public float getFloat(String str, float f) {
        return this.mSharedPreferences.getFloat(str, f);
    }

    public void putInt(String str, int i) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        editorEdit.putInt(str, i);
        editorEdit.commit();
    }

    public int getInt(String str, int i) {
        return this.mSharedPreferences.getInt(str, i);
    }

    public boolean putString(String str, String str2) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        editorEdit.putString(str, str2);
        return editorEdit.commit();
    }

    public String getString(String str, String str2) {
        return this.mSharedPreferences.getString(str, str2);
    }

    public String[] getStringArray(String str) {
        return this.mSharedPreferences.getString(str, Constants.MAIN_VERSION_TAG).split("#~");
    }

    public void putStringArray(String str, String[] strArr) {
        if (strArr != null && strArr.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String str2 : strArr) {
                sb.append(str2).append("#~");
            }
            SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
            editorEdit.putString(str, sb.toString());
            editorEdit.commit();
        }
    }

    public boolean remove(String str) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        editorEdit.remove(str);
        return editorEdit.commit();
    }

    public void clear() {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        editorEdit.clear();
        editorEdit.commit();
    }
}
