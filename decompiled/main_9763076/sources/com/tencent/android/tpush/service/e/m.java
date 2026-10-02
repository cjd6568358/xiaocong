package com.tencent.android.tpush.service.e;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.common.t;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.XGPushServiceV3;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.sqlcipher.database.SQLiteDatabase;
import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class m {
    private static long b = 0;
    private static long c = 0;
    private static int d = -1;
    static List a = new ArrayList();
    private static int e = -1;
    private static String f = null;

    public static String a(Context context) {
        return context != null ? context.getPackageName() : Constants.MAIN_VERSION_TAG;
    }

    public static boolean a(JSONObject jSONObject) {
        return jSONObject == null || jSONObject.length() <= 0;
    }

    public static boolean a(JSONArray jSONArray) {
        return jSONArray == null || jSONArray.length() <= 0;
    }

    public static void b(Context context) {
        if (b <= 0) {
            b = h.a(context, "last_reportAppList_time", -1L);
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - b > 259200000) {
            b = jCurrentTimeMillis;
            JSONArray jSONArrayR = r(context);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("ap_ls", jSONArrayR);
                com.tencent.android.tpush.service.d.a.a(context, "app_list", jSONObject);
            } catch (JSONException e2) {
            }
            h.b(context, "last_reportAppList_time", b);
        }
    }

    public static void c(Context context) {
        try {
            int i = d.a(context) ? 1 : 0;
            if (d < 0) {
                d = f.b(context, "notification_st", -1);
            }
            if (c <= 0) {
                c = h.a(context, "last_reportNotification_time", -1L);
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (i != d || jCurrentTimeMillis - c <= 259200000) {
                d = i;
                c = jCurrentTimeMillis;
                h.b(context, "last_reportNotification_time", c);
                f.a(context, "notification_st", i);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("nf_st", d);
                com.tencent.android.tpush.service.d.a.a(context, "notification_st", jSONObject);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "reportNotificationStatus", th);
        }
    }

    public static String a() {
        try {
            return TpnsSecurity.generateLocalSocketServieName(com.tencent.android.tpush.service.n.f());
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getSocketName", e2);
            return null;
        }
    }

    public static List d(Context context) {
        if (context != null) {
            try {
                HashMap map = new HashMap();
                PackageManager packageManager = context.getPackageManager();
                List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(new Intent("android.intent.action"), 32);
                listQueryIntentActivities.addAll(packageManager.queryIntentActivities(new Intent(Constants.MAIN_VERSION_TAG), 32));
                listQueryIntentActivities.addAll(packageManager.queryBroadcastReceivers(new Intent(Constants.ACTION_SDK_INSTALL), WXMediaMessage.TITLE_LENGTH_LIMIT));
                for (ResolveInfo resolveInfo : listQueryIntentActivities) {
                    map.put(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo);
                }
                return new ArrayList(map.values());
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getLocalPushAppsInfo", e2);
            }
        }
        return null;
    }

    public static List b() {
        if (a.isEmpty()) {
            a.add("com.jingdong.app.mall");
            a.add("com.ifeng.news2");
        }
        return a;
    }

    public static boolean a(String str) {
        return b().contains(str);
    }

    public static void e(Context context) {
        int iWaitFor;
        Process processExec;
        String str;
        try {
            Map map = com.tencent.android.tpush.service.a.a.a(context).J;
            if (map != null) {
                for (Map.Entry entry : map.entrySet()) {
                    try {
                        String str2 = "am startservice -n " + ((String) entry.getKey()) + "/" + ((String) entry.getValue());
                        Process processExec2 = Runtime.getRuntime().exec(str2);
                        int iWaitFor2 = processExec2.waitFor();
                        if (iWaitFor2 != 0) {
                            str = "am startservice --user 0 -n " + ((String) entry.getKey()) + "/" + ((String) entry.getValue());
                            processExec = Runtime.getRuntime().exec(str);
                            iWaitFor = processExec.waitFor();
                        } else {
                            iWaitFor = iWaitFor2;
                            processExec = processExec2;
                            str = str2;
                        }
                        if (iWaitFor != 0) {
                            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "pullUpServerConfigPkgs error exec cmd:" + str + ",exitValud:" + processExec.exitValue());
                        }
                    } catch (Throwable th) {
                        com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "pullUpServerConfigPkgs error exec cmd:" + th);
                    }
                }
            }
        } catch (Throwable th2) {
        }
    }

    static boolean a(Context context, String str) {
        if (str == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo != null && runningAppProcessInfo.processName != null && runningAppProcessInfo.processName.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    public static void f(Context context) {
        try {
            JSONArray jSONArray = com.tencent.android.tpush.service.a.a.a(context).I;
            if (jSONArray == null || jSONArray.length() == 0) {
                com.tencent.android.tpush.a.a.g("Util", "pullupOtherServiceByProviderAndActivity no running");
            } else {
                for (int i = 0; i < jSONArray.length(); i++) {
                    a(context, jSONArray.optJSONObject(i));
                }
            }
            if (com.tencent.android.tpush.service.a.a.a(context).H != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(RNMessageModule.NAME, "com.tencent.qgame:wns");
                jSONObject.put("intent", "com.tencent.qgame.XINGEPUSH");
                jSONObject.put("url", "com.tencent.qgame.keepalive/keepalive");
                com.tencent.android.tpush.a.a.g("Util", "pullupOtherServiceByProviderAndActivity  qgame");
                a(context, jSONObject);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "pullupOtherServiceByProviderAndActivity" + th);
        }
    }

    public static void a(Context context, JSONObject jSONObject) {
        if (jSONObject != null) {
            String strOptString = jSONObject.optString(RNMessageModule.NAME, Constants.MAIN_VERSION_TAG);
            if (!b(strOptString) && !a(context, strOptString)) {
                com.tencent.android.tpush.a.a.g("Util", "pullUpOtherServiceByProviderAndActivityJSONOject " + strOptString);
                String strOptString2 = jSONObject.optString("intent", Constants.MAIN_VERSION_TAG);
                if (!b(strOptString2)) {
                    try {
                        Intent intent = new Intent(strOptString2);
                        intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                        context.startActivity(intent);
                    } catch (Throwable th) {
                    }
                }
                String strOptString3 = jSONObject.optString("url", Constants.MAIN_VERSION_TAG);
                if (!b(strOptString3)) {
                    com.tencent.android.tpush.common.g.a().a(new n(context, strOptString, strOptString3), 2000L);
                }
            }
        }
    }

    public static void g(Context context) {
        com.tencent.android.tpush.a.b(context);
        if (a(context.getPackageName())) {
            com.tencent.android.tpush.a.a.g("Util", context.getPackageName() + " ingore.");
            return;
        }
        try {
            if (h(context) >= 2) {
                com.tencent.android.tpush.a.a.g("Util", "more than two XGV3 service running");
            } else {
                z(context);
                A(context);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "pullUpXGServiceByRemoteService" + th);
        }
        f(context);
        e(context);
    }

    private static void z(Context context) {
        List<ResolveInfo> listD = d(context);
        if (h(context) < 2) {
            if (listD != null) {
                int i = 0;
                for (ResolveInfo resolveInfo : listD) {
                    i++;
                    if ("oppo".equals(t.b())) {
                        if (i > 2) {
                            return;
                        }
                    } else if (i > 4) {
                        return;
                    }
                    String str = resolveInfo.activityInfo.applicationInfo.packageName;
                    if (!b(str) && !context.getPackageName().equals(str) && !b(context, str)) {
                        if (h(context) < 2) {
                            try {
                                String str2 = "am startservice -n " + str + "/" + XGPushServiceV3.class.getName();
                                Process processExec = Runtime.getRuntime().exec(str2);
                                int iWaitFor = processExec.waitFor();
                                if (iWaitFor != 0) {
                                    str2 = "am startservice --user 0 -n " + str + "/" + XGPushServiceV3.class.getName();
                                    processExec = Runtime.getRuntime().exec(str2);
                                    iWaitFor = processExec.waitFor();
                                }
                                if (iWaitFor != 0) {
                                    com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "pull up error exec cmd:" + str2 + ",exitValud:" + processExec.exitValue());
                                }
                            } catch (Throwable th) {
                                com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "pull up error exec cmd:" + th);
                            }
                        } else {
                            return;
                        }
                    }
                }
                return;
            }
            com.tencent.android.tpush.a.a.f(Constants.ServiceLogTag, "pullupXGServices  with null content");
        }
    }

    private static void A(Context context) {
        if (h(context) < 2) {
            com.tencent.android.tpush.common.g.a().a(new o(context), 2000L);
        }
    }

    public static int h(Context context) {
        int i = 0;
        try {
            List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
            if (runningServices != null && runningServices.size() > 0) {
                String name = XGPushServiceV3.class.getName();
                Iterator<ActivityManager.RunningServiceInfo> it = runningServices.iterator();
                while (it.hasNext()) {
                    i = name.equals(it.next().service.getClassName()) ? i + 1 : i;
                }
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "checkXGServiceV3IsRunningByPkgName", th);
        }
        return i;
    }

    public static boolean b(Context context, String str) {
        try {
            List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
            if (runningServices != null && runningServices.size() > 0) {
                String name = XGPushServiceV3.class.getName();
                for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
                    if (name.equals(runningServiceInfo.service.getClassName())) {
                        String packageName = runningServiceInfo.service.getPackageName();
                        if (com.tencent.android.tpush.stat.a.e.b(packageName) && packageName.equals(str)) {
                            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "isSurvive srvPkg :" + packageName);
                            return true;
                        }
                    }
                }
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "checkXGServiceV3IsRunningByPkgName", th);
        }
        return false;
    }

    public static List c(Context context, String str) {
        if (context != null) {
            try {
                return context.getPackageManager().queryIntentServices(new Intent(str), WXMediaMessage.TITLE_LENGTH_LIMIT);
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getLocalPushServicesInfo", e2);
                return null;
            }
        }
        com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "getLocalPushServicesInfo the context == null");
        return null;
    }

    public static boolean d(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("empty params");
        }
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }

    public static boolean e(Context context, String str) {
        if (t.c(str)) {
            return false;
        }
        if (context != null) {
            try {
                List listD = d(context);
                if (listD != null) {
                    Iterator it = listD.iterator();
                    while (it.hasNext()) {
                        if (str.equals(((ResolveInfo) it.next()).activityInfo.packageName)) {
                            return true;
                        }
                    }
                }
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "isLocalApp", e2);
            }
        }
        return false;
    }

    public static boolean f(Context context, String str) {
        if (t.c(str) || context == null) {
            return false;
        }
        try {
            List listC = c(context, str + Constants.RPC_SUFFIX);
            return listC == null || listC.size() > 0;
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "isPkgHasRemoteService", e2);
            return false;
        }
    }

    public static boolean a(Context context, String str, long j) {
        return a(context, str, j, false);
    }

    private static boolean a(Context context, String str, long j, boolean z) {
        boolean z2;
        boolean z3 = false;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(str, 0);
            z3 = true;
        } catch (Exception e2) {
            if (e(context, str) || f(context, str)) {
                return true;
            }
            if (z) {
                try {
                    List registerInfo = CacheManager.getRegisterInfo(context);
                    if (registerInfo == null) {
                        z2 = false;
                        break;
                    }
                    Iterator it = registerInfo.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z2 = false;
                            break;
                        }
                        RegisterEntity registerEntity = (RegisterEntity) it.next();
                        if (registerEntity.accessId == j) {
                            try {
                                packageManager.getPackageInfo(registerEntity.packageName, 0);
                                z2 = true;
                                break;
                            } catch (Exception e3) {
                            }
                        }
                    }
                    z3 = z2;
                } catch (Exception e4) {
                    com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "isAppInstalled", e2);
                }
            }
        }
        return z3;
    }

    public static boolean g(Context context, String str) {
        List registerInfos;
        if (context != null && (registerInfos = CacheManager.getRegisterInfos(context)) != null) {
            Iterator it = registerInfos.iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals(str) && !context.getPackageName().equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean b(String str) {
        return str == null || str.length() == 0 || str.trim().length() == 0;
    }

    public static boolean c() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "isSDCardMounted", e2);
            return false;
        }
    }

    public static int d() {
        return Build.VERSION.SDK_INT;
    }

    public static String e() {
        return Build.MODEL;
    }

    public static String i(Context context) {
        if (context != null) {
            try {
                return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c("Util", ">>get imei err: ", e2.getCause());
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static boolean j(Context context) {
        List registerInfos = CacheManager.getRegisterInfos(context);
        return registerInfos != null && registerInfos.size() > 0;
    }

    public static byte k(Context context) {
        if (context != null) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager == null) {
                    return (byte) 0;
                }
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    return (byte) -1;
                }
                if (!activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected()) {
                    return (byte) -1;
                }
                if (activeNetworkInfo.getType() == 1) {
                    return (byte) 1;
                }
                if (activeNetworkInfo.getType() != 0) {
                    return (byte) 0;
                }
                switch (activeNetworkInfo.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return (byte) 2;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 15:
                        return (byte) 3;
                    case 12:
                    case 14:
                    default:
                        return (byte) 0;
                    case 13:
                        return (byte) 4;
                }
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getNetworkType", e2);
            }
        }
        return (byte) -1;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0061  */
    public static byte l(Context context) {
        byte b2;
        if (context == null) {
            return (byte) 0;
        }
        try {
            String simOperator = ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
            if (simOperator == null) {
                b2 = 0;
            } else if (simOperator.equals("46000") || simOperator.equals("46002") || simOperator.equals("46007") || simOperator.equals("46020")) {
                b2 = 3;
            } else if (simOperator.equals("46001") || simOperator.equals("46006")) {
                b2 = 2;
            } else if (!simOperator.equals("46003") && !simOperator.equals("46005")) {
                b2 = 0;
            } else {
                b2 = 1;
            }
            return b2;
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getIsp", e2);
            return (byte) 0;
        }
    }

    public static String m(Context context) {
        String strN;
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.getType() == 1) {
                    strN = n(context);
                } else {
                    strN = Constants.MAIN_VERSION_TAG + ((int) l(context)) + ((int) k(context));
                }
                return strN;
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getKey", e2);
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static String n(Context context) {
        String strO = o(context);
        if (strO == null || strO.equals(PushConstants.PUSH_TYPE_NOTIFY)) {
            return f();
        }
        return strO;
    }

    public static String f() {
        try {
            if (NetworkInterface.getNetworkInterfaces() == null) {
                return PushConstants.PUSH_TYPE_NOTIFY;
            }
            Iterator it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
            while (it.hasNext()) {
                for (InetAddress inetAddress : Collections.list(((NetworkInterface) it.next()).getInetAddresses())) {
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        if (InetAddressUtils.isIPv4Address(hostAddress)) {
                            return hostAddress;
                        }
                    }
                }
            }
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getLocalIpAddress", e2);
        }
        return PushConstants.PUSH_TYPE_NOTIFY;
    }

    public static String o(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            if (connectionInfo == null) {
                return PushConstants.PUSH_TYPE_NOTIFY;
            }
            return connectionInfo.getBSSID();
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getRouteMac", th);
            return PushConstants.PUSH_TYPE_NOTIFY;
        }
    }

    public static long c(String str) {
        if (str == null || str.equals(PushConstants.PUSH_TYPE_NOTIFY)) {
            return 0L;
        }
        String strTrim = str.trim();
        long[] jArr = new long[4];
        int iIndexOf = strTrim.indexOf(".");
        int iIndexOf2 = strTrim.indexOf(".", iIndexOf + 1);
        int iIndexOf3 = strTrim.indexOf(".", iIndexOf2 + 1);
        try {
            jArr[3] = Long.parseLong(strTrim.substring(0, iIndexOf));
            jArr[2] = Long.parseLong(strTrim.substring(iIndexOf + 1, iIndexOf2));
            jArr[1] = Long.parseLong(strTrim.substring(iIndexOf2 + 1, iIndexOf3));
            jArr[0] = Long.parseLong(strTrim.substring(iIndexOf3 + 1));
        } catch (Throwable th) {
            for (int i = 0; i < jArr.length; i++) {
                jArr[i] = 0;
            }
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "service Util@@parseIpAddress(" + strTrim + ")", th);
        }
        return (jArr[0] << 24) + (jArr[1] << 16) + (jArr[2] << 8) + jArr[3];
    }

    public static String a(long j) {
        StringBuffer stringBuffer = new StringBuffer(Constants.MAIN_VERSION_TAG);
        stringBuffer.append(String.valueOf(255 & j));
        stringBuffer.append(".");
        stringBuffer.append(String.valueOf((65535 & j) >>> 8));
        stringBuffer.append(".");
        stringBuffer.append(String.valueOf((16777215 & j) >>> 16));
        stringBuffer.append(".");
        stringBuffer.append(String.valueOf(j >>> 24));
        return stringBuffer.toString();
    }

    public static String d(String str) {
        if (com.tencent.android.tpush.service.n.f() != null) {
            try {
                return TpnsSecurity.getEncryptAPKSignature(com.tencent.android.tpush.service.n.f().createPackageContext(str, 0));
            } catch (PackageManager.NameNotFoundException e2) {
                com.tencent.android.tpush.a.a.c(Constants.LogTag, "+++ getAppCert exception.", e2);
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static Intent a(int i, String str, int i2) {
        Intent intent = new Intent(Constants.ACTION_FEEDBACK);
        if (str != null && str.length() != 0) {
            intent.setPackage(str);
        }
        intent.putExtra(Constants.FEEDBACK_TAG, i2);
        intent.putExtra(Constants.FEEDBACK_ERROR_CODE, i);
        return intent;
    }

    public static boolean a(Intent intent) {
        try {
            JSONObject jSONObject = new JSONObject(Rijndael.decrypt(intent.getStringExtra("content")));
            if (jSONObject.isNull(MessageKey.MSG_ACCEPT_TIME)) {
                return true;
            }
            String string = jSONObject.getString(MessageKey.MSG_ACCEPT_TIME);
            JSONArray jSONArray = new JSONArray(string);
            if (jSONArray.length() == 0) {
                return true;
            }
            Calendar calendar = Calendar.getInstance();
            long longExtra = intent.getLongExtra(MessageKey.MSG_SERVER_TIME, 0L);
            long longExtra2 = intent.getLongExtra(MessageKey.MSG_TIME_GAP, 0L);
            if (longExtra != 0 && longExtra2 != 0 && longExtra == 0) {
                calendar.setTimeInMillis(System.currentTimeMillis() - longExtra2);
            }
            int i = (calendar.get(11) * 60) + calendar.get(12);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject2 = new JSONObject(jSONArray.getString(i2));
                JSONObject jSONObject3 = new JSONObject(jSONObject2.getString(MessageKey.MSG_ACCEPT_TIME_START));
                int iIntValue = Integer.valueOf(jSONObject3.getString(MessageKey.MSG_ACCEPT_TIME_MIN)).intValue() + (Integer.valueOf(jSONObject3.getString(MessageKey.MSG_ACCEPT_TIME_HOUR)).intValue() * 60);
                JSONObject jSONObject4 = new JSONObject(jSONObject2.getString(MessageKey.MSG_ACCEPT_TIME_END));
                int iIntValue2 = (Integer.valueOf(jSONObject4.getString(MessageKey.MSG_ACCEPT_TIME_HOUR)).intValue() * 60) + Integer.valueOf(jSONObject4.getString(MessageKey.MSG_ACCEPT_TIME_MIN)).intValue();
                if (iIntValue <= i && i <= iIntValue2) {
                    return true;
                }
            }
            com.tencent.android.tpush.a.a.i("Utils", " discurd the msg due to time not accepted! acceptTime = " + string + " , curTime= " + i);
            return false;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "checkAcceptTime", th);
            return true;
        }
    }

    public static long b(Intent intent) {
        int i = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            String stringExtra = intent.getStringExtra(MessageKey.MSG_DATE);
            if (b(stringExtra)) {
                stringExtra = simpleDateFormat.format(new Date());
            }
            long time = simpleDateFormat.parse(stringExtra).getTime();
            JSONObject jSONObject = new JSONObject(Rijndael.decrypt(intent.getStringExtra("content")));
            if (jSONObject.isNull(MessageKey.MSG_ACCEPT_TIME)) {
                return time;
            }
            String string = jSONObject.getString(MessageKey.MSG_ACCEPT_TIME);
            JSONArray jSONArray = new JSONArray(string);
            if (jSONArray.length() == 0) {
                return time;
            }
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject2 = new JSONObject(new JSONObject(jSONArray.getString(i2)).getString(MessageKey.MSG_ACCEPT_TIME_START));
                int iIntValue = (Integer.valueOf(jSONObject2.getString(MessageKey.MSG_ACCEPT_TIME_HOUR)).intValue() * 60) + Integer.valueOf(jSONObject2.getString(MessageKey.MSG_ACCEPT_TIME_MIN)).intValue();
                if (iIntValue < i || i == 0) {
                    i = iIntValue;
                }
            }
            long j = (((long) (i * 60)) * 1000) + time;
            long longExtra = intent.getLongExtra(MessageKey.MSG_SERVER_TIME, 0L);
            long longExtra2 = intent.getLongExtra(MessageKey.MSG_TIME_GAP, 0L);
            if (longExtra != 0 && longExtra2 != 0 && longExtra == 0) {
                j += longExtra2;
            }
            com.tencent.android.tpush.a.a.e("Utils", "get acceptTime = " + string + " , acceptBeginTime= " + j);
            return j;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "getAcceptBeginTime", th);
            return 0L;
        }
    }

    public static String p(Context context) {
        if (context == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveInfoResolveActivity == null || resolveInfoResolveActivity.activityInfo == null || resolveInfoResolveActivity.activityInfo.packageName.equals("android")) {
            return null;
        }
        return resolveInfoResolveActivity.activityInfo.packageName;
    }

    public static int q(Context context) {
        if (e != -1) {
            return e;
        }
        try {
            if (p.a()) {
                e = 1;
            }
        } catch (Throwable th) {
        }
        e = 0;
        return e;
    }

    private static Map B(Context context) {
        HashMap map = new HashMap();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            for (String str : runningAppProcessInfo.pkgList) {
                map.put(str, runningAppProcessInfo);
            }
        }
        return map;
    }

    private static boolean e(String str) {
        if (b(str)) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        return lowerCase.contains(".lbe.") || lowerCase.contains(".qihoo360.") || lowerCase.contains("jinshan.") || lowerCase.contains(".qqpimsecure") || lowerCase.contains(".phonoalbumshoushou") || lowerCase.contains(".netqin.") || lowerCase.contains(".kms.") || lowerCase.contains(".avg.") || lowerCase.contains(".am321.") || lowerCase.contains("safe") || lowerCase.contains("security") || lowerCase.contains("clean");
    }

    public static JSONArray r(Context context) {
        JSONArray jSONArray = new JSONArray();
        try {
            Map mapS = s(context);
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                Map mapB = B(context);
                List<ResolveInfo> listD = d(context);
                HashMap map = new HashMap();
                if (listD != null && listD.size() > 0) {
                    for (ResolveInfo resolveInfo : listD) {
                        if (resolveInfo.activityInfo != null) {
                            map.put(resolveInfo.activityInfo.packageName, 1);
                        }
                    }
                }
                for (PackageInfo packageInfo : packageManager.getInstalledPackages(0)) {
                    JSONObject jSONObject = new JSONObject();
                    ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                    if ((packageInfo.applicationInfo.flags & 1) != 0) {
                        if (e(applicationInfo.packageName)) {
                            jSONObject.put(NotifyType.SOUND, "1");
                        }
                    }
                    String string = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                    if (string != null) {
                        jSONObject.put("n", string);
                    }
                    if (applicationInfo.packageName != null) {
                        jSONObject.put("pn", applicationInfo.packageName);
                    }
                    if (packageInfo.versionName != null) {
                        jSONObject.put("av", packageInfo.versionName);
                    }
                    if (mapB.containsKey(applicationInfo.packageName)) {
                        jSONObject.put("rn", "1");
                    }
                    if (map.containsKey(applicationInfo.packageName)) {
                        jSONObject.put("xg", "1");
                    }
                    jSONObject.put("fit", packageInfo.firstInstallTime / 1000);
                    jSONObject.put("lut", packageInfo.lastUpdateTime / 1000);
                    jSONObject.put("fg", packageInfo.applicationInfo.flags);
                    if (mapS.containsKey(applicationInfo.packageName)) {
                        jSONObject.put("rt", 1);
                    }
                    jSONArray.put(jSONObject);
                }
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "failed to get app.", th);
        }
        return jSONArray;
    }

    public static Map s(Context context) {
        HashMap map = new HashMap();
        try {
            Iterator<ActivityManager.RecentTaskInfo> it = ((ActivityManager) context.getSystemService("activity")).getRecentTasks(64, 1).iterator();
            while (it.hasNext()) {
                ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(it.next().baseIntent, 0);
                if (resolveInfoResolveActivity != null) {
                    map.put(resolveInfoResolveActivity.resolvePackageName, 1);
                }
            }
        } catch (Throwable th) {
        }
        return map;
    }

    public static JSONArray t(Context context) {
        JSONArray jSONArray = new JSONArray();
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                Map mapB = B(context);
                List<ResolveInfo> listD = d(context);
                HashMap map = new HashMap();
                if (listD != null && listD.size() > 0) {
                    for (ResolveInfo resolveInfo : listD) {
                        if (resolveInfo.activityInfo != null) {
                            map.put(resolveInfo.activityInfo.packageName, 1);
                        }
                    }
                }
                for (PackageInfo packageInfo : packageManager.getInstalledPackages(0)) {
                    JSONObject jSONObject = new JSONObject();
                    ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                    if (mapB.containsKey(applicationInfo.packageName) || map.containsKey(applicationInfo.packageName)) {
                        if ((packageInfo.applicationInfo.flags & 1) != 0) {
                            if (e(applicationInfo.packageName)) {
                                jSONObject.put(NotifyType.SOUND, "1");
                            }
                        }
                        String string = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                        if (string != null) {
                            jSONObject.put("n", string);
                        }
                        if (applicationInfo.packageName != null) {
                            jSONObject.put("p", applicationInfo.packageName);
                        }
                        if (packageInfo.versionName != null) {
                            jSONObject.put(NotifyType.VIBRATE, packageInfo.versionName);
                        }
                        if (mapB.containsKey(applicationInfo.packageName)) {
                            jSONObject.put("r", "1");
                        }
                        if (map.containsKey(applicationInfo.packageName)) {
                            jSONObject.put("xg", "1");
                        }
                        jSONArray.put(jSONObject);
                    }
                }
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "failed to get app.", th);
        }
        return jSONArray;
    }

    public static String a(String str, int i) {
        int length = str.length();
        if (length < i) {
            for (int i2 = 0; i2 < i - length; i2++) {
                str = str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
            }
        }
        return str;
    }

    public static boolean g() {
        try {
            boolean zEquals = Environment.getExternalStorageState().equals("mounted");
            if (!zEquals) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "SDCard is not mounted");
                return zEquals;
            }
            return zEquals;
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "SDCard is not mounted", e2);
            return false;
        }
    }

    public static boolean u(Context context) {
        boolean z = false;
        try {
            ApplicationInfo applicationInfoV = v(context);
            if (applicationInfoV == null) {
                com.tencent.android.tpush.a.a.j(Constants.LogTag, "Failed to init due to null ApplicationInfo.");
            } else if (applicationInfoV.icon == 0) {
                com.tencent.android.tpush.a.a.j(Constants.LogTag, "Failed to get Application icon in AndroidManifest.xml, You App maybe can not show notification, Please add Application icon in AndroidManifest.xml");
            } else {
                z = true;
            }
        } catch (Throwable th) {
        }
        return z;
    }

    public static ApplicationInfo v(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.d(Constants.LogTag, "Failed to get Application info", e2);
            return null;
        }
    }

    public static boolean w(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.processName.equals(context.getPackageName()) && runningAppProcessInfo.importance == 100) {
                return true;
            }
        }
        return false;
    }

    public static boolean b(Context context, String str, long j) {
        return a(context, str, j, false);
    }

    public static String x(Context context) {
        if (TextUtils.isEmpty(f)) {
            int iMyPid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (iMyPid == runningAppProcessInfo.pid) {
                    f = runningAppProcessInfo.processName;
                    break;
                }
            }
        }
        return f;
    }

    public static void y(Context context) {
        try {
            String strX = x(context);
            if (strX.contains(":xg_service_v")) {
                if (!"huawei".equalsIgnoreCase(Build.MANUFACTURER)) {
                    Process.killProcess(Process.myPid());
                } else {
                    com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "serviceSafeExit @ " + strX);
                    XGPushServiceV3.b().stopSelf();
                }
            }
        } catch (Throwable th) {
        }
    }
}
