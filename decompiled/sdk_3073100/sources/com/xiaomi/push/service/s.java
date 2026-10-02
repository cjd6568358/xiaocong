package com.xiaomi.push.service;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class s {
    public static Intent a(byte[] bArr, long j) {
        com.xiaomi.xmpush.thrift.ab abVarA = a(bArr);
        if (abVarA == null) {
            return null;
        }
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.putExtra("mipush_payload", bArr);
        intent.putExtra("mrt", Long.toString(j));
        intent.setPackage(abVarA.f);
        return intent;
    }

    public static com.xiaomi.xmpush.thrift.ab a(Context context, com.xiaomi.xmpush.thrift.ab abVar) {
        return a(context, abVar, false, false, false);
    }

    public static com.xiaomi.xmpush.thrift.ab a(Context context, com.xiaomi.xmpush.thrift.ab abVar, boolean z, boolean z2, boolean z3) {
        com.xiaomi.xmpush.thrift.w wVar = new com.xiaomi.xmpush.thrift.w();
        wVar.b(abVar.h());
        com.xiaomi.xmpush.thrift.r rVarM = abVar.m();
        if (rVarM != null) {
            wVar.a(rVarM.b());
            wVar.a(rVarM.d());
            if (!TextUtils.isEmpty(rVarM.f())) {
                wVar.c(rVarM.f());
            }
        }
        wVar.a(com.xiaomi.xmpush.thrift.aq.a(context, abVar));
        wVar.b(com.xiaomi.xmpush.thrift.aq.a(z, z2, z3));
        com.xiaomi.xmpush.thrift.ab abVarA = aa.a(abVar.j(), abVar.h(), wVar, com.xiaomi.xmpush.thrift.a.AckMessage);
        com.xiaomi.xmpush.thrift.r rVarA = abVar.m().a();
        rVarA.a("mat", Long.toString(System.currentTimeMillis()));
        abVarA.a(rVarA);
        return abVarA;
    }

    public static com.xiaomi.xmpush.thrift.ab a(byte[] bArr) {
        com.xiaomi.xmpush.thrift.ab abVar = new com.xiaomi.xmpush.thrift.ab();
        try {
            com.xiaomi.xmpush.thrift.aq.a(abVar, bArr);
            return abVar;
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
            return null;
        }
    }

    private static void a(XMPushService xMPushService, com.xiaomi.xmpush.thrift.ab abVar) {
        xMPushService.a(new t(4, xMPushService, abVar));
    }

    private static void a(XMPushService xMPushService, com.xiaomi.xmpush.thrift.ab abVar, String str) {
        xMPushService.a(new x(4, xMPushService, abVar, str));
    }

    private static void a(XMPushService xMPushService, com.xiaomi.xmpush.thrift.ab abVar, String str, String str2) {
        xMPushService.a(new y(4, xMPushService, abVar, str, str2));
    }

    public static void a(XMPushService xMPushService, com.xiaomi.xmpush.thrift.ab abVar, boolean z, boolean z2, boolean z3) {
        xMPushService.a(new z(4, xMPushService, abVar, z, z2, z3));
    }

    public static void a(XMPushService xMPushService, String str, byte[] bArr, Intent intent, boolean z) {
        com.xiaomi.xmpush.thrift.ab abVarA = a(bArr);
        com.xiaomi.xmpush.thrift.r rVarM = abVarA.m();
        if (c(abVarA) && a(xMPushService, str)) {
            d(xMPushService, abVarA);
            return;
        }
        if (a(abVarA) && !a(xMPushService, str) && !b(abVarA)) {
            e(xMPushService, abVarA);
            return;
        }
        if ((!ac.b(abVarA) || !com.xiaomi.channel.commonutils.android.b.f(xMPushService, abVarA.f)) && !a(xMPushService, intent)) {
            if (com.xiaomi.channel.commonutils.android.b.f(xMPushService, abVarA.f)) {
                com.xiaomi.channel.commonutils.logger.b.a("receive a mipush message, we can see the app, but we can't see the receiver.");
                return;
            } else {
                a(xMPushService, abVarA);
                return;
            }
        }
        if (com.xiaomi.xmpush.thrift.a.Registration == abVarA.a()) {
            String strJ = abVarA.j();
            SharedPreferences.Editor editorEdit = xMPushService.getSharedPreferences("pref_registered_pkg_names", 0).edit();
            editorEdit.putString(strJ, abVarA.e);
            editorEdit.commit();
            aw.a().b("Registe Success, package name is " + strJ);
        }
        if (rVarM != null && !TextUtils.isEmpty(rVarM.h()) && !TextUtils.isEmpty(rVarM.j()) && rVarM.h != 1 && (ac.a(rVarM.s()) || !ac.a(xMPushService, abVarA.f))) {
            boolean zA = false;
            String strB = null;
            if (rVarM != null) {
                strB = rVarM.j != null ? rVarM.j.get("jobkey") : null;
                if (TextUtils.isEmpty(strB)) {
                    strB = rVarM.b();
                }
                zA = ad.a(xMPushService, abVarA.f, strB);
            }
            if (zA) {
                com.xiaomi.channel.commonutils.logger.b.a("drop a duplicate message, key=" + strB);
            } else {
                ac.b bVarA = ac.a(xMPushService, abVarA, bArr);
                if (bVarA.b > 0 && !TextUtils.isEmpty(bVarA.a)) {
                    com.xiaomi.smack.util.g.a(xMPushService, bVarA.a, bVarA.b, true, System.currentTimeMillis());
                }
                if (!ac.b(abVarA)) {
                    Intent intent2 = new Intent("com.xiaomi.mipush.MESSAGE_ARRIVED");
                    intent2.putExtra("mipush_payload", bArr);
                    intent2.setPackage(abVarA.f);
                    try {
                        List<ResolveInfo> listQueryBroadcastReceivers = xMPushService.getPackageManager().queryBroadcastReceivers(intent2, 0);
                        if (listQueryBroadcastReceivers != null && !listQueryBroadcastReceivers.isEmpty()) {
                            xMPushService.sendBroadcast(intent2, b.a(abVarA.f));
                        }
                    } catch (Exception e) {
                        xMPushService.sendBroadcast(intent2, b.a(abVarA.f));
                    }
                }
            }
            if (z) {
                a(xMPushService, abVarA, false, true, false);
            } else {
                c(xMPushService, abVarA);
            }
        } else if (!"com.xiaomi.xmsf".contains(abVarA.f) || abVarA.c() || rVarM == null || rVarM.s() == null || !rVarM.s().containsKey("ab")) {
            xMPushService.sendBroadcast(intent, b.a(abVarA.f));
        } else {
            c(xMPushService, abVarA);
            com.xiaomi.channel.commonutils.logger.b.c("receive abtest message. ack it." + rVarM.b());
        }
        if (abVarA.a() != com.xiaomi.xmpush.thrift.a.UnRegistration || "com.xiaomi.xmsf".equals(xMPushService.getPackageName())) {
            return;
        }
        xMPushService.stopSelf();
    }

    private static void a(XMPushService xMPushService, byte[] bArr, long j) {
        Map<String, String> mapS;
        com.xiaomi.xmpush.thrift.ab abVarA = a(bArr);
        if (abVarA == null) {
            return;
        }
        if (TextUtils.isEmpty(abVarA.f)) {
            com.xiaomi.channel.commonutils.logger.b.a("receive a mipush message without package name");
            return;
        }
        Long lValueOf = Long.valueOf(System.currentTimeMillis());
        Intent intentA = a(bArr, lValueOf.longValue());
        String strA = ac.a(abVarA);
        com.xiaomi.smack.util.g.a(xMPushService, strA, j, true, System.currentTimeMillis());
        com.xiaomi.xmpush.thrift.r rVarM = abVarA.m();
        if (rVarM != null) {
            rVarM.a("mrt", Long.toString(lValueOf.longValue()));
        }
        if (com.xiaomi.xmpush.thrift.a.SendMessage == abVarA.a() && p.a(xMPushService).a(abVarA.f) && !ac.b(abVarA)) {
            com.xiaomi.channel.commonutils.logger.b.a("Drop a message for unregistered, msgid=" + (rVarM != null ? rVarM.b() : ""));
            a(xMPushService, abVarA, abVarA.f);
            return;
        }
        if (com.xiaomi.xmpush.thrift.a.SendMessage == abVarA.a() && p.a(xMPushService).c(abVarA.f) && !ac.b(abVarA)) {
            com.xiaomi.channel.commonutils.logger.b.a("Drop a message for push closed, msgid=" + (rVarM != null ? rVarM.b() : ""));
            a(xMPushService, abVarA, abVarA.f);
            return;
        }
        if (com.xiaomi.xmpush.thrift.a.SendMessage == abVarA.a() && !TextUtils.equals(xMPushService.getPackageName(), "com.xiaomi.xmsf") && !TextUtils.equals(xMPushService.getPackageName(), abVarA.f)) {
            com.xiaomi.channel.commonutils.logger.b.a("Receive a message with wrong package name, expect " + xMPushService.getPackageName() + ", received " + abVarA.f);
            a(xMPushService, abVarA, "unmatched_package", "package should be " + xMPushService.getPackageName() + ", but got " + abVarA.f);
            return;
        }
        if (rVarM != null && rVarM.b() != null) {
            com.xiaomi.channel.commonutils.logger.b.a(String.format("receive a message, appid=%1$s, msgid= %2$s", abVarA.h(), rVarM.b()));
        }
        if (rVarM != null && (mapS = rVarM.s()) != null && mapS.containsKey("hide") && "true".equalsIgnoreCase(mapS.get("hide"))) {
            c(xMPushService, abVarA);
            return;
        }
        if (rVarM != null && rVarM.s() != null && rVarM.s().containsKey("__miid")) {
            String str = rVarM.s().get("__miid");
            Account accountA = com.xiaomi.channel.commonutils.android.f.a(xMPushService);
            if ((accountA == null) | (!TextUtils.equals(str, accountA.name))) {
                com.xiaomi.channel.commonutils.logger.b.a(new StringBuilder().append(str).append(" should be login, but got ").append(accountA).toString() == null ? "nothing" : accountA.name);
                a(xMPushService, abVarA, "miid already logout or anther already login", new StringBuilder().append(str).append(" should be login, but got ").append(accountA).toString() == null ? "nothing" : accountA.name);
                return;
            }
        }
        boolean z = rVarM != null && a(rVarM.s());
        if (z) {
            if (!b(xMPushService, abVarA)) {
                return;
            }
            boolean zA = a(xMPushService, rVarM, bArr);
            a(xMPushService, abVarA, true, false, false);
            if (!zA) {
                return;
            }
        }
        a(xMPushService, strA, bArr, intentA, z);
    }

    private static boolean a(Context context, Intent intent) {
        try {
            List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 32);
            return (listQueryBroadcastReceivers == null || listQueryBroadcastReceivers.isEmpty()) ? false : true;
        } catch (Exception e) {
            return true;
        }
    }

    private static boolean a(Context context, String str) {
        Intent intent = new Intent("com.xiaomi.mipush.miui.CLICK_MESSAGE");
        intent.setPackage(str);
        Intent intent2 = new Intent("com.xiaomi.mipush.miui.RECEIVE_MESSAGE");
        intent2.setPackage(str);
        PackageManager packageManager = context.getPackageManager();
        try {
            return (packageManager.queryBroadcastReceivers(intent2, 32).isEmpty() && packageManager.queryIntentServices(intent, 32).isEmpty()) ? false : true;
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            return false;
        }
    }

    private static boolean a(XMPushService xMPushService, com.xiaomi.xmpush.thrift.r rVar, byte[] bArr) {
        Map<String, String> mapS = rVar.s();
        String[] strArrSplit = mapS.get("__geo_ids").split(",");
        ArrayList<ContentValues> arrayList = new ArrayList<>();
        for (String str : strArrSplit) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("geo_id", str);
            contentValues.put("message_id", rVar.b());
            int i = Integer.parseInt(mapS.get("__geo_action"));
            contentValues.put("action", Integer.valueOf(i));
            contentValues.put("content", bArr);
            contentValues.put("deadline", Long.valueOf(Long.parseLong(mapS.get("__geo_deadline"))));
            if (TextUtils.equals(e.a(xMPushService).c(str), "Enter") && i == 1) {
                return true;
            }
            arrayList.add(contentValues);
        }
        if (!g.a(xMPushService).a(arrayList)) {
            com.xiaomi.channel.commonutils.logger.b.c("geofence added some new geofence message failed messagi_id:" + rVar.b());
        }
        return false;
    }

    private static boolean a(com.xiaomi.xmpush.thrift.ab abVar) {
        return "com.xiaomi.xmsf".equals(abVar.f) && abVar.m() != null && abVar.m().s() != null && abVar.m().s().containsKey("miui_package_name");
    }

    private static boolean a(Map<String, String> map) {
        return map != null && map.containsKey("__geo_ids");
    }

    private static boolean b(XMPushService xMPushService, com.xiaomi.xmpush.thrift.ab abVar) {
        if (h.a(xMPushService) && h.b(xMPushService)) {
            if (com.xiaomi.channel.commonutils.android.b.f(xMPushService, abVar.f)) {
                Map<String, String> mapS = abVar.m().s();
                return (mapS == null || !"12".contains(mapS.get("__geo_action")) || TextUtils.isEmpty(mapS.get("__geo_ids"))) ? false : true;
            }
            a(xMPushService, abVar);
            return false;
        }
        return false;
    }

    private static boolean b(com.xiaomi.xmpush.thrift.ab abVar) {
        Map<String, String> mapS = abVar.m().s();
        return mapS != null && mapS.containsKey("notify_effect");
    }

    private static void c(XMPushService xMPushService, com.xiaomi.xmpush.thrift.ab abVar) {
        xMPushService.a(new u(4, xMPushService, abVar));
    }

    private static boolean c(com.xiaomi.xmpush.thrift.ab abVar) {
        if (abVar.m() == null || abVar.m().s() == null) {
            return false;
        }
        return "1".equals(abVar.m().s().get("obslete_ads_message"));
    }

    private static void d(XMPushService xMPushService, com.xiaomi.xmpush.thrift.ab abVar) {
        xMPushService.a(new v(4, xMPushService, abVar));
    }

    private static void e(XMPushService xMPushService, com.xiaomi.xmpush.thrift.ab abVar) {
        xMPushService.a(new w(4, xMPushService, abVar));
    }

    public void a(Context context, ak.b bVar, boolean z, int i, String str) {
        n nVarA;
        if (z || (nVarA = o.a(context)) == null || !"token-expired".equals(str)) {
            return;
        }
        try {
            o.a(context, nVarA.d, nVarA.e, nVarA.f);
        } catch (IOException e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
        } catch (JSONException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    public void a(XMPushService xMPushService, com.xiaomi.slim.b bVar, ak.b bVar2) {
        try {
            a(xMPushService, bVar.d(bVar2.i), bVar.l());
        } catch (IllegalArgumentException e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
        }
    }

    public void a(XMPushService xMPushService, com.xiaomi.smack.packet.d dVar, ak.b bVar) {
        if (!(dVar instanceof com.xiaomi.smack.packet.c)) {
            com.xiaomi.channel.commonutils.logger.b.a("not a mipush message");
            return;
        }
        com.xiaomi.smack.packet.c cVar = (com.xiaomi.smack.packet.c) dVar;
        com.xiaomi.smack.packet.a aVarP = cVar.p("s");
        if (aVarP != null) {
            try {
                a(xMPushService, aq.b(aq.a(bVar.i, cVar.k()), aVarP.c()), com.xiaomi.smack.util.g.a(dVar.c()));
            } catch (IllegalArgumentException e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
            }
        }
    }
}
