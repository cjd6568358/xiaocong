package com.baidu.mobstat;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class r {
    static final r a = new r();

    r() {
    }

    public synchronized void a(Context context, boolean z) {
        b(context, z);
    }

    private void b(Context context, boolean z) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            List<PackageInfo> arrayList = new ArrayList<>(1);
            try {
                arrayList = packageManager.getInstalledPackages(0);
            } catch (Exception e) {
                bd.b(e);
            }
            JSONArray jSONArray = new JSONArray();
            for (PackageInfo packageInfo : arrayList) {
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                if (applicationInfo != null) {
                    boolean z2 = (applicationInfo.flags & 1) != 0;
                    String string = applicationInfo.loadLabel(packageManager).toString();
                    String str = applicationInfo.sourceDir;
                    if (z == z2) {
                        a(z, string, str, packageInfo, jSONArray);
                    }
                }
            }
            if (jSONArray.length() != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(System.currentTimeMillis() + "|");
                sb.append(z ? 1 : 0);
                String strA = Constants.MAIN_VERSION_TAG;
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("app_list", jSONArray);
                    jSONObject.put("meta-data", sb.toString());
                    strA = cs.a(jSONObject.toString().getBytes());
                } catch (Exception e2) {
                }
                if (!TextUtils.isEmpty(strA)) {
                    y.b.a(System.currentTimeMillis(), strA);
                }
            }
        }
    }

    private void a(boolean z, String str, String str2, PackageInfo packageInfo, JSONArray jSONArray) {
        long j;
        long j2 = 0;
        if (!z || !packageInfo.packageName.startsWith("com.android.")) {
            try {
                j = packageInfo.firstInstallTime;
            } catch (Throwable th) {
                bd.b(th);
                j = 0;
            }
            try {
                j2 = packageInfo.lastUpdateTime;
            } catch (Throwable th2) {
                bd.b(th2);
            }
            long jA = a(str2);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("n", packageInfo.packageName);
                jSONObject.put("a", str);
                jSONObject.put(NotifyType.VIBRATE, String.valueOf(packageInfo.versionName));
                jSONObject.put("f", j);
                jSONObject.put(NotifyType.LIGHTS, j2);
                jSONObject.put("m", jA);
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                bd.b(e);
            }
        }
    }

    private long a(String str) {
        File file;
        if (str == null || (file = new File(str)) == null || !file.exists()) {
            return 0L;
        }
        return file.lastModified();
    }
}
