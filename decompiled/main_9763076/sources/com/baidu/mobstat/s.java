package com.baidu.mobstat;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class s {
    static s a = new s();
    private String b = Constants.MAIN_VERSION_TAG;

    s() {
    }

    public synchronized void a(Context context, boolean z) {
        a(context, z, z ? 1 : 20);
    }

    private void a(Context context, boolean z, int i) {
        ArrayList<t> arrayListA = a(context, i);
        if (arrayListA != null && arrayListA.size() != 0) {
            if (z) {
                String strB = arrayListA.get(0).b();
                if (a(strB, this.b)) {
                    this.b = strB;
                }
            }
            a(context, arrayListA, z);
        }
    }

    private ArrayList<t> a(Context context, int i) {
        return Build.VERSION.SDK_INT >= 21 ? c(context, i) : b(context, i);
    }

    private ArrayList<t> b(Context context, int i) {
        List<ActivityManager.RunningTaskInfo> runningTasks;
        try {
            runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(50);
        } catch (Exception e) {
            bd.b(e);
            runningTasks = null;
        }
        if (runningTasks == null) {
            return new ArrayList<>();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            if (linkedHashMap.size() > i) {
                break;
            }
            ComponentName componentName = runningTaskInfo.topActivity;
            if (componentName != null) {
                String packageName = componentName.getPackageName();
                if (!TextUtils.isEmpty(packageName) && !b(context, packageName) && !linkedHashMap.containsKey(packageName)) {
                    linkedHashMap.put(packageName, new t(packageName, a(context, packageName), Constants.MAIN_VERSION_TAG));
                }
            }
        }
        return new ArrayList<>(linkedHashMap.values());
    }

    private ArrayList<t> c(Context context, int i) {
        String[] strArr;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return new ArrayList<>();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i2 = 0; i2 < runningAppProcesses.size() && linkedHashMap.size() <= i; i2++) {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(i2);
            if (a(runningAppProcessInfo.importance) && (strArr = runningAppProcessInfo.pkgList) != null && strArr.length != 0) {
                String str = runningAppProcessInfo.pkgList[0];
                if (!TextUtils.isEmpty(str) && !b(context, str) && !linkedHashMap.containsKey(str)) {
                    linkedHashMap.put(str, new t(str, a(context, str), String.valueOf(runningAppProcessInfo.importance)));
                }
            }
        }
        return new ArrayList<>(linkedHashMap.values());
    }

    private boolean a(int i) {
        if (i != 100 && i != 200 && i != 130) {
            return false;
        }
        return true;
    }

    private boolean a(String str, String str2) {
        return (TextUtils.isEmpty(str) || str.equals(this.b)) ? false : true;
    }

    private String a(Context context, String str) {
        String str2 = Constants.MAIN_VERSION_TAG;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            str2 = packageManager.getPackageInfo(str, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            bd.b(e);
        }
        return str2 == null ? Constants.MAIN_VERSION_TAG : str2;
    }

    private boolean b(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        try {
            ApplicationInfo applicationInfo = packageManager.getPackageInfo(str, 0).applicationInfo;
            return (applicationInfo == null || (applicationInfo.flags & 1) == 0) ? false : true;
        } catch (PackageManager.NameNotFoundException e) {
            bd.b(e);
            return false;
        }
    }

    private void a(Context context, ArrayList<t> arrayList, boolean z) {
        String strA;
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis() + "|");
        sb.append(z ? 1 : 0);
        try {
            JSONArray jSONArray = new JSONArray();
            Iterator<t> it = arrayList.iterator();
            while (it.hasNext()) {
                JSONObject jSONObjectA = it.next().a();
                if (jSONObjectA != null) {
                    jSONArray.put(jSONObjectA);
                }
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("app_trace", jSONArray);
            jSONObject.put("meta-data", sb.toString());
            strA = cs.a(jSONObject.toString().getBytes());
        } catch (Exception e) {
            bd.b(e);
            strA = Constants.MAIN_VERSION_TAG;
        }
        if (!TextUtils.isEmpty(strA)) {
            y.c.a(System.currentTimeMillis(), strA);
        }
    }
}
