package com.tencent.android.tpush.common;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.SettingsContentProvider;
import com.tencent.android.tpush.XGPush4Msdk;
import com.tencent.android.tpush.XGPushActivity;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushProvider;
import com.tencent.android.tpush.XGPushReceiver;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.XGPushServiceV3;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;
import com.tencent.android.tpush.service.y;
import com.tencent.mid.api.MidConstants;
import com.tencent.mid.api.MidProvider;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.security.auth.x500.X500Principal;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class t {
    private static AtomicBoolean a = new AtomicBoolean(false);
    private static boolean b = false;
    private static final X500Principal c = new X500Principal("CN=Android Debug,O=Android,C=US");

    public static String a(String str) {
        if (str == null) {
            return PushConstants.PUSH_TYPE_NOTIFY;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b2 : bArrDigest) {
                int i = b2 & Constants.NETWORK_TYPE_UNCONNECTED;
                if (i < 16) {
                    stringBuffer.append(PushConstants.PUSH_TYPE_NOTIFY);
                }
                stringBuffer.append(Integer.toHexString(i));
            }
            return stringBuffer.toString();
        } catch (Throwable th) {
            return PushConstants.PUSH_TYPE_NOTIFY;
        }
    }

    public static void a(JSONObject jSONObject, String str, String str2) {
        if (str2 != null) {
            try {
                if (str2.length() > 0) {
                    jSONObject.put(str, str2);
                }
            } catch (Throwable th) {
            }
        }
    }

    public static void a(JSONObject jSONObject, String str, long j) {
        if (str != null && j > 0) {
            try {
                jSONObject.put(str, j);
            } catch (Throwable th) {
            }
        }
    }

    public static int a(Context context) {
        if (a.get()) {
            return 0;
        }
        try {
            if (XGPushManager.getContext() == null) {
                XGPushManager.setContext(context);
            }
            if (com.tencent.android.tpush.service.n.f() == null) {
                com.tencent.android.tpush.service.n.d(context);
            }
            if (!h(context)) {
                com.tencent.android.tpush.a.a.j("Util", "XG is disable");
                return Constants.CODE_SERVICE_DISABLED;
            }
            if (!TpnsSecurity.checkTpnsSecurityLibSo(context)) {
                com.tencent.android.tpush.a.a.j("Util", "can not load library from so file");
                return Constants.CODE_SO_ERROR;
            }
            if (!l.a()) {
                return Constants.CODE_PERMISSIONS_ERROR;
            }
            if (!a(context, XGPushProvider.class.getName(), XGPushProvider.AUTH_PRIX)) {
                com.tencent.android.tpush.a.a.h("Util", "Maybe have not contentprovider: " + XGPushProvider.class.getName());
            }
            if (!a(context, SettingsContentProvider.class.getName(), ".TPUSH_PROVIDER")) {
                com.tencent.android.tpush.a.a.h("Util", "Maybe have not contentprovider: " + SettingsContentProvider.class.getName());
            }
            if (!a(context, MidProvider.class.getName(), MidConstants.PROVIDER_AUTH_SUFFIX)) {
                com.tencent.android.tpush.a.a.h("Util", "Maybe have not contentprovider: " + MidProvider.class.getName());
            }
            if (!b("com.qq.taf.jce.JceStruct")) {
                com.tencent.android.tpush.a.a.j("Util", "please add wup-1.0.0.E-SNAPSHOT.jar in your libs");
                return Constants.CODE_JCE_ERROR;
            }
            com.tencent.android.tpush.service.e.m.u(context);
            a.set(true);
            return 0;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("Util", "Util -> initGlobal", th);
            return -1;
        }
    }

    public static boolean b(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean a(Context context, String str, String str2) {
        try {
            for (ProviderInfo providerInfo : context.getPackageManager().queryContentProviders((String) null, 0, 0)) {
                if (providerInfo.name.equals(str) && providerInfo.authority.equals(context.getPackageName() + str2)) {
                    return true;
                }
            }
            com.tencent.android.tpush.a.a.g("Util", "Util -> initGlobal can not find provider " + str + " with authority " + context.getPackageName() + str2);
            return false;
        } catch (Throwable th) {
            throw new RuntimeException("Package manager has died", th);
        }
    }

    public static boolean b(Context context) {
        try {
            List listC = com.tencent.android.tpush.service.e.m.c(context, context.getPackageName() + Constants.RPC_SUFFIX);
            if (listC != null && listC.size() > 0) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("Util", "Util -> isAIDLConfiged", th);
        }
    }

    public static boolean c(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static int c(Context context) {
        if (context != null) {
            try {
                List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
                if (runningServices != null && runningServices.size() > 0) {
                    String name = XGPushServiceV3.class.getName();
                    for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
                        String className = runningServiceInfo.service.getClassName();
                        if (name.equals(className) || "com.tencent.android.tpush.service.XGPushServiceV3".equals(className)) {
                            return runningServiceInfo.pid != 0 ? 1 : 2;
                        }
                    }
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c("Util", "getServiceStatus", th);
            }
        }
        return 0;
    }

    public static void d(Context context) {
        com.tencent.android.tpush.a.a.c(Constants.LogTag, "startCurrentAppService " + context.getPackageName());
        context.startService(new Intent(context, (Class<?>) XGPushServiceV3.class));
    }

    public static void e(Context context) {
        if (context != null) {
            try {
                if (com.tencent.android.tpush.service.e.m.a(context.getPackageName())) {
                    d(context);
                    return;
                }
                com.tencent.android.tpush.service.n.d(context.getApplicationContext());
                List listD = com.tencent.android.tpush.service.e.m.d(context);
                if (listD == null || s.a(context).a() || listD.size() < 1 || (listD.size() < 2 && ((ResolveInfo) listD.get(0)).activityInfo.packageName.equals(context.getPackageName()))) {
                    com.tencent.android.tpush.service.n.a(context);
                    com.tencent.android.tpush.a.a.a("Util", "Action -> start Local Service()");
                } else {
                    context.sendBroadcast(new Intent(Constants.ACTION_SDK_INSTALL));
                }
                g.a().a(new u(context), 1500L);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public static boolean a(Context context, BroadcastReceiver broadcastReceiver) {
        try {
            context.unregisterReceiver(broadcastReceiver);
            return true;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("Util", "safeUnregisterReceiver error", e);
            return false;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String f(Context context) {
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str == null) {
                return Constants.MAIN_VERSION_TAG;
            }
            return str;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("Util", "get app version error", th);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    private static void a(Context context, String str) {
        PackageManager packageManager;
        if (context != null && str != null && str.trim().length() != 0 && (packageManager = context.getPackageManager()) != null) {
            ComponentName componentName = new ComponentName(context.getPackageName(), str);
            if (packageManager.getComponentEnabledSetting(componentName) != 1) {
                packageManager.setComponentEnabledSetting(componentName, 1, 1);
            }
        }
    }

    public static void g(Context context) {
        if (context != null && !b) {
            try {
                a(context, XGPushServiceV3.class.getName());
                a(context, XGPushActivity.class.getName());
                a(context, XGPushProvider.class.getName());
                a(context, SettingsContentProvider.class.getName());
                a(context, MidProvider.class.getName());
                for (ActivityInfo activityInfo : context.getPackageManager().getPackageInfo(context.getPackageName(), 2).receivers) {
                    String str = activityInfo.name;
                    try {
                        Class<?> clsLoadClass = context.getClassLoader().loadClass(str);
                        if (XGPushBaseReceiver.class.isAssignableFrom(clsLoadClass) || clsLoadClass.getName().equals(XGPushReceiver.class.getName())) {
                            a(context, str);
                        }
                    } catch (ClassNotFoundException e) {
                    }
                }
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c("Util", "enableComponents", e2);
            }
            b = true;
        }
    }

    public static String a(long j) {
        try {
            return new SimpleDateFormat("yyyyMMdd").format(Long.valueOf(j));
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("Util", "getDateString", e);
            return "20141111";
        }
    }

    public static boolean h(Context context) {
        if (context == null) {
            return true;
        }
        XGPushManager.enableService = m.b(context, context.getPackageName() + XGPushManager.ENABLE_SERVICE_SUFFIX, XGPushManager.enableService);
        if (XGPushManager.enableService == -1) {
            XGPushManager.enableService = m.b(context, context.getPackageName() + XGPushManager.ENABLE_SERVICE_SUFFIX, 2);
        }
        if (XGPushManager.enableService == 2 && TpnsSecurity.checkTpnsSecurityLibSo(context)) {
            String str = com.tencent.android.tpush.service.a.a.a(context).x;
            if (!c(str)) {
                String[] strArrSplit = Rijndael.decrypt(str).split(",");
                HashMap map = new HashMap();
                for (String str2 : strArrSplit) {
                    try {
                        map.put(Long.valueOf(str2), 0L);
                    } catch (NumberFormatException e) {
                    }
                }
                if (map.size() > 0) {
                    if (XGPushConfig.getAccessId(context) > 0 && map.containsKey(Long.valueOf(XGPushConfig.getAccessId(context)))) {
                        XGPushManager.enableService(context, false);
                        return false;
                    }
                    if (XGPush4Msdk.getQQAccessId(context) > 0 && map.containsKey(Long.valueOf(XGPush4Msdk.getQQAccessId(context)))) {
                        XGPushManager.enableService(context, false);
                        return false;
                    }
                }
            }
        }
        return XGPushManager.enableService != 0;
    }

    public static boolean i(Context context) {
        try {
            return ((PowerManager) context.getSystemService("power")).isScreenOn();
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("Util", "Util -> isScreenOn", e);
            return false;
        }
    }

    public static int j(Context context) {
        try {
            Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = intentRegisterReceiver.getIntExtra("status", -1);
            if (intExtra == 2 || intExtra == 5) {
                return intentRegisterReceiver.getIntExtra("plugged", -1);
            }
            return -1;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("Util", "Util -> getChangedStatus", e);
            return -1;
        }
    }

    public static void k(Context context) {
        if (context == null) {
            com.tencent.android.tpush.a.a.i("Util", "Util -> getWakeCpu error null context");
            return;
        }
        try {
            y.a().a(((PowerManager) context.getSystemService("power")).newWakeLock(1, "TPUSH"));
            if (!y.a().b().isHeld()) {
                y.a().b().acquire();
            }
            com.tencent.android.tpush.a.a.c("Util", "get Wake Cpu ");
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("Util", "get Wake cpu", th);
        }
    }

    public static void a() {
        try {
            PowerManager.WakeLock wakeLockB = y.a().b();
            if (wakeLockB != null) {
                if (wakeLockB.isHeld()) {
                    wakeLockB.release();
                }
                com.tencent.android.tpush.a.a.c("Util", "stop WakeLock CPU");
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("Util", "stopWakeLock", th);
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0042 A[Catch: IOException -> 0x006f, TRY_LEAVE, TryCatch #7 {IOException -> 0x006f, blocks: (B:15:0x003d, B:17:0x0042), top: B:53:0x003d }] */
    /* JADX WARN: Code duplicated, block: B:24:0x0051 A[Catch: IOException -> 0x0055, TRY_LEAVE, TryCatch #0 {IOException -> 0x0055, blocks: (B:22:0x004c, B:24:0x0051), top: B:49:0x004c }] */
    /* JADX WARN: Code duplicated, block: B:32:0x005e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:33:0x0060 A[Catch: IOException -> 0x0064, TRY_LEAVE, TryCatch #2 {IOException -> 0x0064, blocks: (B:31:0x005b, B:33:0x0060), top: B:51:0x005b }] */
    /* JADX WARN: Code duplicated, block: B:51:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static boolean a(File file, File file2) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        FileInputStream fileInputStream = null;
        boolean z = false;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int i = fileInputStream2.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        bufferedOutputStream.write(bArr, 0, i);
                    }
                    bufferedOutputStream.flush();
                    z = true;
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                            if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                        } catch (IOException e) {
                        }
                    } else if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                } catch (IOException e2) {
                    e = e2;
                    fileInputStream = fileInputStream2;
                    try {
                        com.tencent.android.tpush.a.a.i("Util", "copyFile IOException: " + e);
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                                if (bufferedOutputStream != null) {
                                    bufferedOutputStream.close();
                                }
                            } catch (IOException e3) {
                            }
                        } else if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                                if (bufferedOutputStream != null) {
                                    bufferedOutputStream.close();
                                }
                            } catch (IOException e4) {
                                throw th;
                            }
                        } else if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                        if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                    } else if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e = e5;
                bufferedOutputStream = null;
                fileInputStream = fileInputStream2;
            } catch (Throwable th3) {
                th = th3;
                bufferedOutputStream = null;
                fileInputStream = fileInputStream2;
            }
        } catch (IOException e6) {
            e = e6;
            bufferedOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            bufferedOutputStream = null;
        }
        return z;
    }

    public static boolean a(com.tencent.android.tpush.stat.b.d dVar) {
        return dVar != null && dVar.c();
    }

    public static void a(String str, Context context) throws Throwable {
        if (XGPushConfig.isHuaweiDebug()) {
            d(str);
        }
    }

    private static void d(String str) throws Throwable {
        FileWriter fileWriter;
        FileWriter fileWriter2 = null;
        try {
            fileWriter = new FileWriter(Environment.getExternalStorageDirectory() + "/huawei.txt", true);
            try {
                fileWriter.write(str + "\r\n");
                fileWriter.flush();
                fileWriter.close();
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (Exception e) {
                    }
                }
            } catch (Throwable th) {
                fileWriter2 = fileWriter;
                th = th;
                if (fileWriter2 != null) {
                    try {
                        fileWriter2.close();
                    } catch (Exception e2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void l(Context context) {
        try {
            if ("oppo".equals(b())) {
                Intent intent = new Intent("oppo.safecenter.intent.action.CHANGE_NOTIFICATION_STATE");
                intent.putExtra("package_name", context.getPackageName());
                intent.putExtra("allow_notify", true);
                context.sendBroadcast(intent);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("openNotification", "openNotification", th);
        }
    }

    public static void m(Context context) {
        String strB = b();
        if (!"meizu".equals(strB) && "oppo".equals(strB)) {
            try {
                Intent intent = new Intent();
                intent.setClassName("com.coloros.notificationmanager", "com.coloros.notificationmanager.AppDetailPreferenceActivity");
                intent.setAction("com.coloros.notificationmanager.app.detail");
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.putExtra("pkg_name", context.getPackageName());
                intent.putExtra("app_name", n(context));
                intent.putExtra("class_name", context.getPackageName());
                context.startActivity(intent);
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c("Util", "openNotificationSettings", th);
            }
        }
    }

    public static String n(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            int i = applicationInfo.labelRes;
            return i == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(i);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("Util", Constants.MAIN_VERSION_TAG, th);
            return null;
        }
    }

    public static String b() {
        String str = Build.MANUFACTURER;
        if (!TextUtils.isEmpty(str)) {
            return str.trim().toLowerCase();
        }
        return str;
    }
}
