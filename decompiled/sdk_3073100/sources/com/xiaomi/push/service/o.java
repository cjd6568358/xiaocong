package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.text.TextUtils;
import java.util.TreeMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class o {
    private static n a;
    private static a b;

    public interface a {
        void a();
    }

    public static synchronized n a(Context context) {
        n nVar = null;
        synchronized (o.class) {
            if (a != null) {
                nVar = a;
            } else {
                SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_account", 0);
                String string = sharedPreferences.getString("uuid", null);
                String string2 = sharedPreferences.getString("token", null);
                String string3 = sharedPreferences.getString("security", null);
                String string4 = sharedPreferences.getString("app_id", null);
                String string5 = sharedPreferences.getString("app_token", null);
                String string6 = sharedPreferences.getString("package_name", null);
                String string7 = sharedPreferences.getString("device_id", null);
                int i = sharedPreferences.getInt("env_type", 1);
                if (!TextUtils.isEmpty(string7) && string7.startsWith("a-")) {
                    string7 = com.xiaomi.channel.commonutils.android.e.e(context);
                    sharedPreferences.edit().putString("device_id", string7).commit();
                }
                if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
                    String strE = com.xiaomi.channel.commonutils.android.e.e(context);
                    if ("com.xiaomi.xmsf".equals(context.getPackageName()) || TextUtils.isEmpty(strE) || TextUtils.isEmpty(string7) || string7.equals(strE)) {
                        a = new n(string, string2, string3, string4, string5, string6, i);
                        nVar = a;
                    } else {
                        com.xiaomi.channel.commonutils.logger.b.d("erase the old account.");
                        b(context);
                    }
                }
            }
        }
        return nVar;
    }

    public static synchronized n a(Context context, String str, String str2, String str3) {
        PackageInfo packageInfo;
        n nVar = null;
        synchronized (o.class) {
            TreeMap treeMap = new TreeMap();
            treeMap.put("devid", com.xiaomi.channel.commonutils.android.e.a(context));
            String str4 = c(context) ? "1000271" : str2;
            String str5 = c(context) ? "420100086271" : str3;
            String str6 = c(context) ? "com.xiaomi.xmsf" : str;
            treeMap.put("appid", str4);
            treeMap.put("apptoken", str5);
            try {
                packageInfo = context.getPackageManager().getPackageInfo(str6, 16384);
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
                packageInfo = null;
            }
            treeMap.put("appversion", packageInfo != null ? String.valueOf(packageInfo.versionCode) : "0");
            treeMap.put("sdkversion", Integer.toString(26));
            treeMap.put("packagename", str6);
            treeMap.put("model", Build.MODEL);
            treeMap.put("imei_md5", com.xiaomi.channel.commonutils.string.d.a(com.xiaomi.channel.commonutils.android.e.c(context)));
            treeMap.put("os", Build.VERSION.RELEASE + "-" + Build.VERSION.INCREMENTAL);
            int iB = com.xiaomi.channel.commonutils.android.e.b();
            if (iB >= 0) {
                treeMap.put("space_id", Integer.toString(iB));
            }
            String strA = com.xiaomi.channel.commonutils.string.d.a(com.xiaomi.channel.commonutils.android.e.g(context));
            if (!TextUtils.isEmpty(strA)) {
                treeMap.put("mac_address", strA);
            }
            treeMap.put("android_id", com.xiaomi.channel.commonutils.android.e.b(context));
            com.xiaomi.channel.commonutils.network.b bVarA = com.xiaomi.channel.commonutils.network.d.a(context, a(), treeMap);
            String strA2 = bVarA != null ? bVarA.a() : "";
            if (!TextUtils.isEmpty(strA2)) {
                JSONObject jSONObject = new JSONObject(strA2);
                if (jSONObject.getInt("code") == 0) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    nVar = new n(jSONObject2.getString("userId") + "@xiaomi.com/an" + com.xiaomi.channel.commonutils.string.d.a(6), jSONObject2.getString("token"), jSONObject2.getString("ssecurity"), str4, str5, str6, com.xiaomi.channel.commonutils.misc.a.c());
                    a(context, nVar);
                    a = nVar;
                } else {
                    r.a(context, jSONObject.getInt("code"), jSONObject.optString("description"));
                    com.xiaomi.channel.commonutils.logger.b.a(strA2);
                }
            }
        }
        return nVar;
    }

    public static String a() {
        if (com.xiaomi.channel.commonutils.misc.a.b()) {
            return "http://" + com.xiaomi.smack.b.c + ":9085/pass/register";
        }
        return "https://" + (com.xiaomi.channel.commonutils.misc.a.a() ? "sandbox.xmpush.xiaomi.com" : "register.xmpush.xiaomi.com") + "/pass/register";
    }

    public static void a(Context context, n nVar) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_account", 0).edit();
        editorEdit.putString("uuid", nVar.a);
        editorEdit.putString("security", nVar.c);
        editorEdit.putString("token", nVar.b);
        editorEdit.putString("app_id", nVar.d);
        editorEdit.putString("package_name", nVar.f);
        editorEdit.putString("app_token", nVar.e);
        editorEdit.putString("device_id", com.xiaomi.channel.commonutils.android.e.e(context));
        editorEdit.putInt("env_type", nVar.g);
        editorEdit.commit();
        b();
    }

    public static void b() {
        if (b != null) {
            b.a();
        }
    }

    public static void b(Context context) {
        context.getSharedPreferences("mipush_account", 0).edit().clear().commit();
        a = null;
        b();
    }

    private static boolean c(Context context) {
        return context.getPackageName().equals("com.xiaomi.xmsf");
    }
}
