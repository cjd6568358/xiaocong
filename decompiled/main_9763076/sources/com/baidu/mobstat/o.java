package com.baidu.mobstat;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class o {
    static o a = new o();

    public synchronized void a(Context context) {
        b(context);
    }

    private void b(Context context) {
        a(context, c(context));
    }

    private ArrayList<p> c(Context context) {
        String string;
        ArrayList<p> arrayList = new ArrayList<>();
        for (PackageInfo packageInfo : d(context)) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (applicationInfo != null) {
                String str = packageInfo.packageName;
                String str2 = packageInfo.versionName;
                Signature[] signatureArr = packageInfo.signatures;
                if (signatureArr == null || signatureArr.length == 0) {
                    string = Constants.MAIN_VERSION_TAG;
                } else {
                    string = signatureArr[0].toChars().toString();
                }
                String strA = cz.a(string.getBytes());
                String strA2 = Constants.MAIN_VERSION_TAG;
                String str3 = applicationInfo.sourceDir;
                if (!TextUtils.isEmpty(str3)) {
                    strA2 = cz.a(new File(str3));
                }
                arrayList.add(new p(str, str2, strA, strA2));
            }
        }
        return arrayList;
    }

    private ArrayList<PackageInfo> d(Context context) {
        ArrayList<PackageInfo> arrayList = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return arrayList;
        }
        List<PackageInfo> arrayList2 = new ArrayList<>(1);
        try {
            arrayList2 = packageManager.getInstalledPackages(64);
        } catch (Exception e) {
            bd.b(e);
        }
        for (PackageInfo packageInfo : arrayList2) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (applicationInfo != null && (applicationInfo.flags & 1) == 0) {
                arrayList.add(packageInfo);
            }
        }
        return arrayList;
    }

    private void a(Context context, ArrayList<p> arrayList) {
        String strA;
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        try {
            JSONArray jSONArray = new JSONArray();
            Iterator<p> it = arrayList.iterator();
            while (it.hasNext()) {
                JSONObject jSONObjectA = it.next().a();
                if (jSONObjectA != null) {
                    jSONArray.put(jSONObjectA);
                }
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("app_apk", jSONArray);
            jSONObject.put("meta-data", sb.toString());
            strA = cs.a(jSONObject.toString().getBytes());
        } catch (Exception e) {
            bd.b(e);
            strA = Constants.MAIN_VERSION_TAG;
        }
        if (!TextUtils.isEmpty(strA)) {
            y.e.a(System.currentTimeMillis(), strA);
        }
    }
}
