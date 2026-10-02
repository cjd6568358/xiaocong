package com.hzy.tvmao.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.hzy.tvmao.KookongSDK;
import com.tencent.android.tpush.common.Constants;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

/* JADX INFO: compiled from: SystemUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private static PackageInfo a;

    public static PackageInfo a() {
        if (a == null) {
            try {
                a = KookongSDK.getContext().getPackageManager().getPackageInfo(KookongSDK.getContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return a;
    }

    public static String b() {
        if (a() == null) {
            return null;
        }
        return a.versionName;
    }

    public static int c() {
        if (a() == null) {
            return 0;
        }
        return a.versionCode;
    }

    public static String d() {
        return KookongSDK.getContext().getPackageName();
    }

    public static String e() {
        String string = DataStoreUtil.i().getString(com.hzy.tvmao.a.a.a, null);
        if (TextUtils.isEmpty(string)) {
            if (!a(string)) {
                string = f();
                if (!a(string)) {
                    string = h();
                    if (!a(string)) {
                        string = UUID.randomUUID().toString();
                    }
                }
            }
            String strE = c.e(string);
            DataStoreUtil.i().putString(com.hzy.tvmao.a.a.a, strE);
            return strE;
        }
        return string;
    }

    public static String f() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return Constants.MAIN_VERSION_TAG;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        sb.append(String.valueOf(Integer.toHexString(b & Constants.NETWORK_TYPE_UNCONNECTED)) + ":");
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            LogUtil.write(Log.getStackTraceString(e));
        }
        return g();
    }

    public static String g() {
        String macAddress;
        try {
            WifiInfo connectionInfo = ((WifiManager) KookongSDK.getContext().getSystemService("wifi")).getConnectionInfo();
            if (connectionInfo == null) {
                LogUtil.write("info = null get mac address failed");
                macAddress = null;
            } else {
                macAddress = connectionInfo.getMacAddress();
            }
            return macAddress;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean a(String str) {
        return (TextUtils.isEmpty(str) || b(str) || str.equals("unknown") || str.equals("02:00:00:00:00:00")) ? false : true;
    }

    private static boolean b(String str) {
        char cCharAt = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != cCharAt) {
                return false;
            }
        }
        return true;
    }

    public static String h() {
        return Settings.Secure.getString(KookongSDK.getContext().getContentResolver(), "android_id");
    }

    public static String i() {
        try {
            Locale locale = KookongSDK.getContext().getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            String country = locale.getCountry();
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(language)) {
                sb.append(language);
                if (!TextUtils.isEmpty(country)) {
                    sb.append("_").append(country);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.MAIN_VERSION_TAG;
        }
    }
}
