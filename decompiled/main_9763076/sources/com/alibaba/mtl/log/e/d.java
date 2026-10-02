package com.alibaba.mtl.log.e;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.alibaba.mtl.log.model.LogField;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.bugly.Bugly;
import com.ut.device.UTDevice;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: compiled from: DeviceUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private static Map<String, String> w = null;

    public static synchronized Map<String, String> a(Context context) {
        Map<String, String> map = null;
        synchronized (d.class) {
            if (w != null) {
                w.put(LogField.CHANNEL.toString(), b.l());
                w.put(LogField.APPKEY.toString(), b.getAppkey());
                String imei = m.getImei(context);
                String imsi = m.getImsi(context);
                if (TextUtils.isEmpty(imei) || TextUtils.isEmpty(imsi)) {
                    imei = Constants.MAIN_VERSION_TAG;
                    imsi = Constants.MAIN_VERSION_TAG;
                }
                w.put(LogField.IMEI.toString(), imei);
                w.put(LogField.IMSI.toString(), imsi);
                a(w, context);
                map = w;
            } else {
                w = new HashMap();
                if (context != null) {
                    if (w != null) {
                        try {
                            String imei2 = m.getImei(context);
                            String imsi2 = m.getImsi(context);
                            if (TextUtils.isEmpty(imei2) || TextUtils.isEmpty(imsi2)) {
                                imei2 = Constants.MAIN_VERSION_TAG;
                                imsi2 = Constants.MAIN_VERSION_TAG;
                            }
                            w.put(LogField.IMEI.toString(), imei2);
                            w.put(LogField.IMSI.toString(), imsi2);
                            w.put(LogField.BRAND.toString(), Build.BRAND);
                            w.put(LogField.DEVICE_MODEL.toString(), Build.MODEL);
                            w.put(LogField.RESOLUTION.toString(), c(context));
                            w.put(LogField.CHANNEL.toString(), b.l());
                            w.put(LogField.APPKEY.toString(), b.getAppkey());
                            w.put(LogField.APPVERSION.toString(), d(context));
                            w.put(LogField.LANGUAGE.toString(), b(context));
                            w.put(LogField.OS.toString(), p());
                            w.put(LogField.OSVERSION.toString(), o());
                            w.put(LogField.SDKVERSION.toString(), "2.6.0_for_bc");
                            w.put(LogField.SDKTYPE.toString(), "mini");
                            try {
                                w.put(LogField.UTDID.toString(), UTDevice.getUtdid(context));
                            } catch (Throwable th) {
                                Log.e("DeviceUtil", "utdid4all jar doesn't exist, please copy the libs folder.");
                                th.printStackTrace();
                            }
                            a(w, context);
                        } catch (Exception e) {
                        }
                    }
                    map = w;
                }
            }
        }
        return map;
    }

    private static String o() {
        String strS = Build.VERSION.RELEASE;
        if (i()) {
            System.getProperty("ro.yunos.version");
            strS = s();
            if (!TextUtils.isEmpty(strS)) {
            }
        }
        return strS;
    }

    private static String p() {
        if (!i() || j()) {
            return "a";
        }
        return "y";
    }

    private static void a(Map<String, String> map, Context context) {
        String networkOperatorName;
        try {
            String[] networkState = l.getNetworkState(context);
            map.put(LogField.ACCESS.toString(), networkState[0]);
            if (networkState[0].equals("2G/3G")) {
                map.put(LogField.ACCESS_SUBTYPE.toString(), networkState[1]);
            } else {
                map.put(LogField.ACCESS_SUBTYPE.toString(), "Unknown");
            }
        } catch (Exception e) {
            map.put(LogField.ACCESS.toString(), "Unknown");
            map.put(LogField.ACCESS_SUBTYPE.toString(), "Unknown");
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null || telephonyManager.getSimState() != 5) {
                networkOperatorName = Constants.MAIN_VERSION_TAG;
            } else {
                networkOperatorName = telephonyManager.getNetworkOperatorName();
            }
            if (TextUtils.isEmpty(networkOperatorName)) {
                networkOperatorName = "Unknown";
            }
            map.put(LogField.CARRIER.toString(), networkOperatorName);
        } catch (Exception e2) {
        }
    }

    private static String b(Context context) {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Throwable th) {
            return "Unknown";
        }
    }

    private static String c(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            if (i > i2) {
                int i3 = i ^ i2;
                i2 ^= i3;
                i = i3 ^ i2;
            }
            return i2 + "*" + i;
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public static String d(Context context) {
        String appVersion = com.alibaba.mtl.log.b.a().getAppVersion();
        if (TextUtils.isEmpty(appVersion)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                if (packageInfo == null) {
                    return "Unknown";
                }
                w.put(LogField.APPVERSION.toString(), packageInfo.versionName);
                return packageInfo.versionName;
            } catch (Throwable th) {
                return "Unknown";
            }
        }
        return appVersion;
    }

    public static boolean i() {
        if ((System.getProperty("java.vm.name") == null || !System.getProperty("java.vm.name").toLowerCase().contains("lemur")) && System.getProperty("ro.yunos.version") == null && TextUtils.isEmpty(q.get("ro.yunos.build.version"))) {
            return j();
        }
        return true;
    }

    private static boolean j() {
        return (TextUtils.isEmpty(c("ro.yunos.product.chip")) && TextUtils.isEmpty(c("ro.yunos.hardware"))) ? false : true;
    }

    public static String c(String str) {
        try {
            Class<?> cls = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            return (String) cls.getMethod("get", String.class).invoke(cls.newInstance(), str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String q() {
        String str = q.get("ro.aliyun.clouduuid", Bugly.SDK_IS_DEV);
        if (Bugly.SDK_IS_DEV.equals(str)) {
            str = q.get("ro.sys.aliyun.clouduuid", Bugly.SDK_IS_DEV);
        }
        if (TextUtils.isEmpty(str)) {
            return r();
        }
        return str;
    }

    private static String r() {
        try {
            return (String) Class.forName("com.yunos.baseservice.clouduuid.CloudUUID").getMethod("getCloudUUID", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    private static String s() {
        try {
            Field declaredField = Build.class.getDeclaredField("YUNOS_BUILD_VERSION");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                return (String) declaredField.get(new String());
            }
        } catch (Exception e) {
        }
        return null;
    }
}
