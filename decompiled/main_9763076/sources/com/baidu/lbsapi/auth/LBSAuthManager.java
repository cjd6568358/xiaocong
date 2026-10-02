package com.baidu.lbsapi.auth;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import bsh.ParserConstants;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LBSAuthManager {
    private static Context a;
    private static l d = null;
    private static int e = 0;
    private static Hashtable<String, LBSAuthManagerListener> f = new Hashtable<>();
    private static LBSAuthManager g;
    private c b = null;
    private e c = null;
    private final Handler h = new h(this, Looper.getMainLooper());

    private LBSAuthManager(Context context) {
        a = context;
        if (d != null && !d.isAlive()) {
            d = null;
        }
        a.b("BaiduApiAuth SDK Version:1.0.20");
        d();
    }

    private int a(String str) {
        int i = -1;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("status")) {
                jSONObject.put("status", -1);
            }
            i = jSONObject.getInt("status");
            if (jSONObject.has("current") && i == 0) {
                long j = jSONObject.getLong("current");
                long jCurrentTimeMillis = System.currentTimeMillis();
                if ((jCurrentTimeMillis - j) / 3600000.0d >= 24.0d) {
                    i = 601;
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    if (!simpleDateFormat.format(Long.valueOf(jCurrentTimeMillis)).equals(simpleDateFormat.format(Long.valueOf(j)))) {
                        i = 601;
                    }
                }
            }
            if (jSONObject.has("current") && i == 602) {
                if ((System.currentTimeMillis() - jSONObject.getLong("current")) / 1000 > 180.0d) {
                    return 601;
                }
            }
            return i;
        } catch (JSONException e2) {
            int i2 = i;
            e2.printStackTrace();
            return i2;
        }
    }

    private String a(int i) throws Throwable {
        InputStreamReader inputStreamReader;
        FileInputStream fileInputStream;
        BufferedReader bufferedReader;
        Throwable th;
        String line = null;
        try {
            fileInputStream = new FileInputStream(new File("/proc/" + i + "/cmdline"));
            try {
                inputStreamReader = new InputStreamReader(fileInputStream);
                try {
                    bufferedReader = new BufferedReader(inputStreamReader);
                    try {
                        line = bufferedReader.readLine();
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (FileNotFoundException e2) {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (IOException e3) {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e4) {
                    bufferedReader = null;
                } catch (IOException e5) {
                    bufferedReader = null;
                } catch (Throwable th3) {
                    bufferedReader = null;
                    th = th3;
                }
            } catch (FileNotFoundException e6) {
                bufferedReader = null;
                inputStreamReader = null;
            } catch (IOException e7) {
                bufferedReader = null;
                inputStreamReader = null;
            } catch (Throwable th4) {
                inputStreamReader = null;
                th = th4;
                bufferedReader = null;
            }
        } catch (FileNotFoundException e8) {
            bufferedReader = null;
            inputStreamReader = null;
            fileInputStream = null;
        } catch (IOException e9) {
            bufferedReader = null;
            inputStreamReader = null;
            fileInputStream = null;
        } catch (Throwable th5) {
            inputStreamReader = null;
            fileInputStream = null;
            bufferedReader = null;
            th = th5;
        }
        return line;
    }

    private String a(Context context) throws Throwable {
        int iMyPid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == iMyPid) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        String strA = null;
        try {
            strA = a(iMyPid);
        } catch (IOException e2) {
        }
        return strA == null ? a.getPackageName() : strA;
    }

    private String a(Context context, String str) {
        LBSAuthManagerListener lBSAuthManagerListener;
        String string = Constants.MAIN_VERSION_TAG;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), ParserConstants.LSHIFTASSIGN);
            if (applicationInfo.metaData == null) {
                LBSAuthManagerListener lBSAuthManagerListener2 = f.get(str);
                if (lBSAuthManagerListener2 != null) {
                    lBSAuthManagerListener2.onAuthResult(101, ErrorMessage.a(101, "AndroidManifest.xml的application中没有meta-data标签"));
                }
                return Constants.MAIN_VERSION_TAG;
            }
            string = applicationInfo.metaData.getString("com.baidu.lbsapi.API_KEY");
            if ((string == null || string.equals(Constants.MAIN_VERSION_TAG)) && (lBSAuthManagerListener = f.get(str)) != null) {
                lBSAuthManagerListener.onAuthResult(101, ErrorMessage.a(101, "无法在AndroidManifest.xml中获取com.baidu.android.lbs.API_KEY的值"));
            }
            return string;
        } catch (PackageManager.NameNotFoundException e2) {
            LBSAuthManagerListener lBSAuthManagerListener3 = f.get(str);
            if (lBSAuthManagerListener3 != null) {
                lBSAuthManagerListener3.onAuthResult(101, ErrorMessage.a(101, "无法在AndroidManifest.xml中获取com.baidu.android.lbs.API_KEY的值"));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:12:0x002b A[Catch: JSONException -> 0x00a2, all -> 0x00c2, TryCatch #0 {JSONException -> 0x00a2, blocks: (B:7:0x000e, B:9:0x001c, B:10:0x0023, B:12:0x002b, B:13:0x0034, B:15:0x0043, B:16:0x0048), top: B:33:0x000e, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:15:0x0043 A[Catch: JSONException -> 0x00a2, all -> 0x00c2, TryCatch #0 {JSONException -> 0x00a2, blocks: (B:7:0x000e, B:9:0x001c, B:10:0x0023, B:12:0x002b, B:13:0x0034, B:15:0x0043, B:16:0x0048), top: B:33:0x000e, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:19:0x0078 A[Catch: all -> 0x00c2, TryCatch #1 {, blocks: (B:5:0x0004, B:6:0x0008, B:7:0x000e, B:9:0x001c, B:10:0x0023, B:12:0x002b, B:13:0x0034, B:15:0x0043, B:16:0x0048, B:17:0x0069, B:19:0x0078, B:20:0x0090, B:22:0x0094, B:24:0x009d, B:28:0x00a3), top: B:35:0x0004, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:22:0x0094 A[Catch: all -> 0x00c2, TryCatch #1 {, blocks: (B:5:0x0004, B:6:0x0008, B:7:0x000e, B:9:0x001c, B:10:0x0023, B:12:0x002b, B:13:0x0034, B:15:0x0043, B:16:0x0048, B:17:0x0069, B:19:0x0078, B:20:0x0090, B:22:0x0094, B:24:0x009d, B:28:0x00a3), top: B:35:0x0004, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:24:0x009d A[Catch: all -> 0x00c2, TRY_LEAVE, TryCatch #1 {, blocks: (B:5:0x0004, B:6:0x0008, B:7:0x000e, B:9:0x001c, B:10:0x0023, B:12:0x002b, B:13:0x0034, B:15:0x0043, B:16:0x0048, B:17:0x0069, B:19:0x0078, B:20:0x0090, B:22:0x0094, B:24:0x009d, B:28:0x00a3), top: B:35:0x0004, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:9:0x001c A[Catch: JSONException -> 0x00a2, all -> 0x00c2, TryCatch #0 {JSONException -> 0x00a2, blocks: (B:7:0x000e, B:9:0x001c, B:10:0x0023, B:12:0x002b, B:13:0x0034, B:15:0x0043, B:16:0x0048), top: B:33:0x000e, outer: #1 }] */
    public synchronized void a(String str, String str2) {
        Message messageObtainMessage;
        JSONObject jSONObject;
        int i = -1;
        synchronized (this) {
            if (str == null) {
                str = e();
                messageObtainMessage = this.h.obtainMessage();
                try {
                    jSONObject = new JSONObject(str);
                    if (!jSONObject.has("status")) {
                        jSONObject.put("status", -1);
                    }
                    if (!jSONObject.has("current")) {
                        jSONObject.put("current", System.currentTimeMillis());
                    }
                    c(jSONObject.toString());
                    if (jSONObject.has("current")) {
                        jSONObject.remove("current");
                    }
                    i = jSONObject.getInt("status");
                    messageObtainMessage.what = i;
                    messageObtainMessage.obj = jSONObject.toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("listenerKey", str2);
                    messageObtainMessage.setData(bundle);
                    this.h.sendMessage(messageObtainMessage);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    messageObtainMessage.what = i;
                    messageObtainMessage.obj = new JSONObject();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("listenerKey", str2);
                    messageObtainMessage.setData(bundle2);
                    this.h.sendMessage(messageObtainMessage);
                }
                d.c();
                e--;
                if (a.a) {
                    a.a("httpRequest called mAuthCounter-- = " + e);
                }
                if (e == 0) {
                    d.a();
                    if (d != null) {
                        d = null;
                    }
                }
            } else {
                messageObtainMessage = this.h.obtainMessage();
                jSONObject = new JSONObject(str);
                if (!jSONObject.has("status")) {
                    jSONObject.put("status", -1);
                }
                if (!jSONObject.has("current")) {
                    jSONObject.put("current", System.currentTimeMillis());
                }
                c(jSONObject.toString());
                if (jSONObject.has("current")) {
                    jSONObject.remove("current");
                }
                i = jSONObject.getInt("status");
                messageObtainMessage.what = i;
                messageObtainMessage.obj = jSONObject.toString();
                Bundle bundle3 = new Bundle();
                bundle3.putString("listenerKey", str2);
                messageObtainMessage.setData(bundle3);
                this.h.sendMessage(messageObtainMessage);
                d.c();
                e--;
                if (a.a) {
                    a.a("httpRequest called mAuthCounter-- = " + e);
                }
                if (e == 0) {
                    d.a();
                    if (d != null) {
                        d = null;
                    }
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z, String str, Hashtable<String, String> hashtable, String str2) {
        String strA = a(a, str2);
        if (strA == null || strA.equals(Constants.MAIN_VERSION_TAG)) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("url", "https://api.map.baidu.com/sdkcs/verify");
        a.a("url:https://api.map.baidu.com/sdkcs/verify");
        map.put("output", "json");
        map.put("ak", strA);
        a.a("ak:" + strA);
        map.put("mcode", b.a(a));
        map.put("from", "lbs_yunsdk");
        if (hashtable != null && hashtable.size() > 0) {
            for (Map.Entry<String, String> entry : hashtable.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }
            }
        }
        String strA2 = Constants.MAIN_VERSION_TAG;
        try {
            strA2 = CommonParam.a(a);
        } catch (Exception e2) {
        }
        a.a("cuid:" + strA2);
        if (TextUtils.isEmpty(strA2)) {
            map.put("cuid", Constants.MAIN_VERSION_TAG);
        } else {
            map.put("cuid", strA2);
        }
        map.put("pcn", a.getPackageName());
        map.put(ClientCookie.VERSION_ATTR, "1.0.20");
        String strC = Constants.MAIN_VERSION_TAG;
        try {
            strC = b.c(a);
        } catch (Exception e3) {
        }
        if (TextUtils.isEmpty(strC)) {
            map.put("macaddr", Constants.MAIN_VERSION_TAG);
        } else {
            map.put("macaddr", strC);
        }
        String strA3 = Constants.MAIN_VERSION_TAG;
        try {
            strA3 = b.a();
        } catch (Exception e4) {
        }
        if (TextUtils.isEmpty(strA3)) {
            map.put("language", Constants.MAIN_VERSION_TAG);
        } else {
            map.put("language", strA3);
        }
        if (z) {
            map.put("force", z ? "1" : PushConstants.PUSH_TYPE_NOTIFY);
        }
        if (str == null) {
            map.put("from_service", Constants.MAIN_VERSION_TAG);
        } else {
            map.put("from_service", str);
        }
        this.b = new c(a);
        this.b.a(map, new j(this, str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z, String str, Hashtable<String, String> hashtable, String[] strArr, String str2) {
        String strA = a(a, str2);
        if (strA == null || strA.equals(Constants.MAIN_VERSION_TAG)) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("url", "https://api.map.baidu.com/sdkcs/verify");
        map.put("output", "json");
        map.put("ak", strA);
        map.put("from", "lbs_yunsdk");
        if (hashtable != null && hashtable.size() > 0) {
            for (Map.Entry<String, String> entry : hashtable.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }
            }
        }
        String strA2 = Constants.MAIN_VERSION_TAG;
        try {
            strA2 = CommonParam.a(a);
        } catch (Exception e2) {
        }
        if (TextUtils.isEmpty(strA2)) {
            map.put("cuid", Constants.MAIN_VERSION_TAG);
        } else {
            map.put("cuid", strA2);
        }
        map.put("pcn", a.getPackageName());
        map.put(ClientCookie.VERSION_ATTR, "1.0.20");
        String strC = Constants.MAIN_VERSION_TAG;
        try {
            strC = b.c(a);
        } catch (Exception e3) {
        }
        if (TextUtils.isEmpty(strC)) {
            map.put("macaddr", Constants.MAIN_VERSION_TAG);
        } else {
            map.put("macaddr", strC);
        }
        String strA3 = Constants.MAIN_VERSION_TAG;
        try {
            strA3 = b.a();
        } catch (Exception e4) {
        }
        if (TextUtils.isEmpty(strA3)) {
            map.put("language", Constants.MAIN_VERSION_TAG);
        } else {
            map.put("language", strA3);
        }
        if (z) {
            map.put("force", z ? "1" : PushConstants.PUSH_TYPE_NOTIFY);
        }
        if (str == null) {
            map.put("from_service", Constants.MAIN_VERSION_TAG);
        } else {
            map.put("from_service", str);
        }
        this.c = new e(a);
        this.c.a(map, strArr, new k(this, str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(String str) {
        String string;
        String strA = a(a, str);
        try {
            JSONObject jSONObject = new JSONObject(e());
            if (!jSONObject.has("ak")) {
                return true;
            }
            string = jSONObject.getString("ak");
        } catch (JSONException e2) {
            e2.printStackTrace();
            string = Constants.MAIN_VERSION_TAG;
        }
        return (strA == null || string == null || strA.equals(string)) ? false : true;
    }

    private void c(String str) {
        a.getSharedPreferences("authStatus_" + a(a), 0).edit().putString("status", str).commit();
    }

    private void d() {
        synchronized (LBSAuthManager.class) {
            if (d == null) {
                d = new l("auth");
                d.start();
                while (d.a == null) {
                    try {
                        if (a.a) {
                            a.a("wait for create auth thread.");
                        }
                        Thread.sleep(3L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    private String e() {
        return a.getSharedPreferences("authStatus_" + a(a), 0).getString("status", "{\"status\":601}");
    }

    public static LBSAuthManager getInstance(Context context) {
        if (g == null) {
            synchronized (LBSAuthManager.class) {
                if (g == null) {
                    g = new LBSAuthManager(context);
                }
            }
        } else if (context != null) {
            a = context;
        } else if (a.a) {
            a.c("input context is null");
            new RuntimeException("here").printStackTrace();
        }
        return g;
    }

    public int authenticate(boolean z, String str, Hashtable<String, String> hashtable, LBSAuthManagerListener lBSAuthManagerListener) {
        int iA;
        synchronized (LBSAuthManager.class) {
            String str2 = System.currentTimeMillis() + Constants.MAIN_VERSION_TAG;
            if (lBSAuthManagerListener != null) {
                f.put(str2, lBSAuthManagerListener);
            }
            String strA = a(a, str2);
            if (strA == null || strA.equals(Constants.MAIN_VERSION_TAG)) {
                iA = 101;
            } else {
                e++;
                if (a.a) {
                    a.a(" mAuthCounter  ++ = " + e);
                }
                String strE = e();
                if (a.a) {
                    a.a("getAuthMessage from cache:" + strE);
                }
                iA = a(strE);
                if (iA == 601) {
                    try {
                        c(new JSONObject().put("status", 602).toString());
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
                d();
                if (a.a) {
                    a.a("mThreadLooper.mHandler = " + d.a);
                }
                if (d == null || d.a == null) {
                    iA = -1;
                } else {
                    d.a.post(new i(this, iA, z, str2, str, hashtable));
                }
            }
        }
        return iA;
    }

    public String getMCode() {
        return a == null ? Constants.MAIN_VERSION_TAG : b.a(a);
    }

    public String getPublicKey(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(context.getPackageName(), ParserConstants.LSHIFTASSIGN).metaData.getString("com.baidu.lbsapi.API_KEY");
    }
}
