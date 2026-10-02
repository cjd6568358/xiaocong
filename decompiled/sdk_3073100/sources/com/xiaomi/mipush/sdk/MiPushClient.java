package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.push.service.k;
import com.xiaomi.xmpush.thrift.ae;
import com.xiaomi.xmpush.thrift.af;
import com.xiaomi.xmpush.thrift.am;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class MiPushClient {
    private static z mSyncMIIDHelper;
    private static long sCurMsgId = System.currentTimeMillis();

    static synchronized void addAcceptTime(Context context, String str, String str2) {
        context.getSharedPreferences("mipush_extra", 0).edit().putString("accept_time", str + "," + str2).commit();
    }

    static synchronized void addAccount(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("account_" + str, System.currentTimeMillis()).commit();
    }

    static synchronized void addAlias(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("alias_" + str, System.currentTimeMillis()).commit();
    }

    static synchronized void addTopic(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().putLong("topic_" + str, System.currentTimeMillis()).commit();
    }

    public static long aliasSetTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("alias_" + str, -1L);
    }

    public static void awakeApps(Context context, String[] strArr) {
        new Thread(new n(strArr, context)).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void awakePushServiceByPackageInfo(Context context, PackageInfo packageInfo) {
        ServiceInfo[] serviceInfoArr = packageInfo.services;
        if (serviceInfoArr != null) {
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                if (serviceInfo.exported && serviceInfo.enabled && "com.xiaomi.mipush.sdk.PushMessageHandler".equals(serviceInfo.name) && !context.getPackageName().equals(serviceInfo.packageName)) {
                    try {
                        Thread.sleep(((long) ((Math.random() * 2.0d) + 1.0d)) * 1000);
                        Intent intent = new Intent();
                        intent.setClassName(serviceInfo.packageName, serviceInfo.name);
                        intent.setAction("com.xiaomi.mipush.sdk.WAKEUP");
                        intent.putExtra("waker_pkgname", context.getPackageName());
                        context.startService(intent);
                        return;
                    } catch (Throwable th) {
                        return;
                    }
                }
            }
        }
    }

    protected static void clearExtras(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 0).edit();
        editorEdit.clear();
        editorEdit.commit();
    }

    public static void clearLocalNotificationType(Context context) {
        u.a(context).f();
    }

    public static void clearNotification(Context context) {
        u.a(context).a(-1);
    }

    public static void clearNotification(Context context, int i) {
        u.a(context).a(i);
    }

    public static void clearNotification(Context context, String str, String str2) {
        u.a(context).a(str, str2);
    }

    public static void disablePush(Context context) {
        u.a(context).a(true);
    }

    public static void enablePush(Context context) {
        u.a(context).a(false);
    }

    protected static synchronized String generatePacketID() {
        String str;
        str = d.a(4) + sCurMsgId;
        sCurMsgId++;
        return str;
    }

    protected static String getAcceptTime(Context context) {
        return context.getSharedPreferences("mipush_extra", 0).getString("accept_time", "00:00-23:59");
    }

    public static List<String> getAllAlias(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("alias_")) {
                arrayList.add(str.substring("alias_".length()));
            }
        }
        return arrayList;
    }

    public static List<String> getAllTopic(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("topic_") && !str.contains("**ALL**")) {
                arrayList.add(str.substring("topic_".length()));
            }
        }
        return arrayList;
    }

    public static List<String> getAllUserAccount(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (str.startsWith("account_")) {
                arrayList.add(str.substring("account_".length()));
            }
        }
        return arrayList;
    }

    public static String getRegId(Context context) {
        if (a.a(context).i()) {
            return a.a(context).e();
        }
        return null;
    }

    static void reInitialize(Context context, com.xiaomi.xmpush.thrift.t tVar) {
        if (a.a(context).i()) {
            String strA = d.a(6);
            String strC = a.a(context).c();
            String strD = a.a(context).d();
            a.a(context).h();
            a.a(context).a(strC, strD, strA);
            af afVar = new af();
            afVar.a(generatePacketID());
            afVar.b(strC);
            afVar.e(strD);
            afVar.f(strA);
            afVar.d(context.getPackageName());
            afVar.c(com.xiaomi.channel.commonutils.android.b.a(context, context.getPackageName()));
            afVar.a(tVar);
            u.a(context).a(afVar, false);
        }
    }

    static synchronized void removeAcceptTime(Context context) {
        context.getSharedPreferences("mipush_extra", 0).edit().remove("accept_time").commit();
    }

    static synchronized void removeAccount(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().remove("account_" + str).commit();
    }

    static synchronized void removeAlias(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().remove("alias_" + str).commit();
    }

    static synchronized void removeAllAccounts(Context context) {
        Iterator<String> it = getAllUserAccount(context).iterator();
        while (it.hasNext()) {
            removeAccount(context, it.next());
        }
    }

    static synchronized void removeAllAliases(Context context) {
        Iterator<String> it = getAllAlias(context).iterator();
        while (it.hasNext()) {
            removeAlias(context, it.next());
        }
    }

    static synchronized void removeAllTopics(Context context) {
        Iterator<String> it = getAllTopic(context).iterator();
        while (it.hasNext()) {
            removeTopic(context, it.next());
        }
    }

    static synchronized void removeTopic(Context context, String str) {
        context.getSharedPreferences("mipush_extra", 0).edit().remove("topic_" + str).commit();
    }

    static void reportIgnoreRegMessageClicked(Context context, String str, com.xiaomi.xmpush.thrift.r rVar, String str2, String str3) {
        ae aeVar = new ae();
        if (TextUtils.isEmpty(str3)) {
            com.xiaomi.channel.commonutils.logger.b.d("do not report clicked message");
            return;
        }
        aeVar.b(str3);
        aeVar.c("bar:click");
        aeVar.a(str);
        aeVar.a(false);
        u.a(context).a(aeVar, com.xiaomi.xmpush.thrift.a.Notification, false, true, rVar, true, str2, str3);
    }

    static void reportMessageClicked(Context context, String str, com.xiaomi.xmpush.thrift.r rVar, String str2) {
        ae aeVar = new ae();
        if (!TextUtils.isEmpty(str2)) {
            aeVar.b(str2);
        } else {
            if (!a.a(context).b()) {
                com.xiaomi.channel.commonutils.logger.b.d("do not report clicked message");
                return;
            }
            aeVar.b(a.a(context).c());
        }
        aeVar.c("bar:click");
        aeVar.a(str);
        aeVar.a(false);
        u.a(context).a(aeVar, com.xiaomi.xmpush.thrift.a.Notification, false, rVar);
    }

    public static long topicSubscribedTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("topic_" + str, -1L);
    }

    public static void unregisterPush(Context context) {
        if (a.a(context).b()) {
            am amVar = new am();
            amVar.a(generatePacketID());
            amVar.b(a.a(context).c());
            amVar.c(a.a(context).e());
            amVar.e(a.a(context).d());
            amVar.d(context.getPackageName());
            u.a(context).a(amVar);
            PushMessageHandler.a();
            a.a(context).k();
            clearExtras(context);
            clearLocalNotificationType(context);
            clearNotification(context);
            if (mSyncMIIDHelper != null) {
                k.a(context).b(mSyncMIIDHelper);
            }
        }
    }
}
