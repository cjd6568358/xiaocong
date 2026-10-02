package com.xiaocong.smarthome.sdk.openapi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.xiaocong.smarthome.sdk.http.util.SaveIdUtils;
import com.xiaocong.smarthome.util.DeviceInfoUtils;
import com.xiaocong.smarthome.util.PackageInfoUtil;
import com.xiaocong.smarthome.util.log.XCLog;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCHelp {
    public static String PACKAGE_NAME;
    public static int VERSION_CODE;
    public static String VERSION_NAME;
    public static String mAppMetaData;
    public static String mClientId;
    public static String mClientKey;
    private static SharedPreferences.Editor mEditor;
    public static String mLive;
    private static SharedPreferences mPrefs;
    public static String mUUID;

    public static void init(Context context) {
        initPrefAndGson(context);
        initVersionInfo(context);
        initConfig();
        getPackageName(context);
    }

    private static void initConfig() {
        mClientId = getString("vic_jastion_dd", "");
        mLive = getString("vic_klmn_jast_like", "");
        mClientKey = getString("NLC_ahe_key", "");
    }

    private static void initUUID() {
        mUUID = getString("device_UUID", "");
        if (TextUtils.isEmpty(PACKAGE_NAME)) {
            PACKAGE_NAME = "ixiaocong";
        }
        String filePath = "/" + PACKAGE_NAME + "/file/";
        if (TextUtils.isEmpty(mUUID) && SaveIdUtils.checkFileExists(filePath + "fda23d0")) {
            String uuid = SaveIdUtils.getFileValue("fdd2dcmd0", filePath);
            if (!TextUtils.isEmpty(uuid)) {
                XCLog.i("XCHelp", "get UUID:" + uuid);
                mUUID = uuid;
                putString("device_UUID", mUUID);
            }
        }
        if (TextUtils.isEmpty(mUUID)) {
            mUUID = UUID.randomUUID().toString().replaceAll("-", "").toString();
            putString("device_UUID", mUUID);
            XCLog.i("XCHelp", "make UUID:" + mUUID);
        }
        if (SaveIdUtils.checkFileExists(filePath + "fda23d0")) {
            if (TextUtils.isEmpty(SaveIdUtils.getFileValue("fdd2dcmd0", filePath))) {
                Map<String, String> fileContent = new HashMap<>();
                fileContent.put("fdd2dcmd0", mUUID);
                String data = JSON.toJSONString(fileContent);
                XCLog.i("XCHelp", "UUID 1:" + data);
                SaveIdUtils.saveCrashInfo2File(data, filePath, "fda23d0");
                return;
            }
            return;
        }
        if (!TextUtils.isEmpty(mUUID)) {
            Map<String, String> fileContent2 = new HashMap<>();
            fileContent2.put("fdd2dcmd0", mUUID);
            String data2 = JSON.toJSONString(fileContent2);
            XCLog.i("XCHelp", "UUID 2:" + data2);
            SaveIdUtils.saveCrashInfo2File(data2, filePath, "fda23d0");
        }
    }

    private static void initPrefAndGson(Context context) {
        mPrefs = context.getSharedPreferences("xiao_cong_config", 0);
        mEditor = mPrefs.edit();
    }

    private static void initVersionInfo(Context context) {
        VERSION_CODE = getVersionCode(context);
        VERSION_NAME = getVersionName(context);
        PACKAGE_NAME = getPackageName(context);
    }

    private static int getVersionCode(Context context) {
        try {
            return PackageInfoUtil.getVersionCode(context);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

    private static String getVersionName(Context context) {
        try {
            return PackageInfoUtil.getVersionName(context);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getPackageName(Context context) {
        try {
            return context.getPackageName().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void initAppMetaData(Context context, String Key) {
        String appMetaDate = DeviceInfoUtils.getAppMetaData(context, Key);
        if (TextUtils.isEmpty(appMetaDate)) {
            mAppMetaData = "unknown channel";
        } else {
            mAppMetaData = appMetaDate;
        }
    }

    public static void updateConfig() {
        initConfig();
    }

    public static void putString(String key, String value) {
        mEditor.putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }

    public static String getUUID() {
        if (!TextUtils.isEmpty(mUUID)) {
            return mUUID;
        }
        initUUID();
        return mUUID;
    }

    public static String getAppMetaData(Context context, String key) {
        if (!TextUtils.isEmpty(mAppMetaData)) {
            return mAppMetaData;
        }
        initAppMetaData(context, key);
        return mAppMetaData;
    }
}
