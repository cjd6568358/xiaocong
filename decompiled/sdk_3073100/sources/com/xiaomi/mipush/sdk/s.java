package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.push.service.ac;
import com.xiaomi.push.service.ah;
import com.xiaomi.push.service.ai;
import com.xiaomi.push.service.am;
import com.xiaomi.xmpush.thrift.aa;
import com.xiaomi.xmpush.thrift.ab;
import com.xiaomi.xmpush.thrift.ad;
import com.xiaomi.xmpush.thrift.ae;
import com.xiaomi.xmpush.thrift.ag;
import com.xiaomi.xmpush.thrift.aj;
import com.xiaomi.xmpush.thrift.al;
import com.xiaomi.xmpush.thrift.an;
import com.xiaomi.xmpush.thrift.ap;
import com.xiaomi.xmpush.thrift.aq;
import com.xiaomi.xmpush.thrift.o;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class s {
    private static Queue<String> c;
    private Context b;
    private static s a = null;
    private static Object d = new Object();

    private s(Context context) {
        this.b = context.getApplicationContext();
        if (this.b == null) {
            this.b = context;
        }
    }

    /* JADX WARN: Code duplicated, block: B:65:0x0196  */
    public static Intent a(Context context, String str, Map<String, String> map) {
        Intent launchIntentForPackage;
        String str2;
        Intent intent;
        URISyntaxException e;
        if (map == null || !map.containsKey("notify_effect")) {
            return null;
        }
        String str3 = map.get("notify_effect");
        if (am.a.equals(str3)) {
            try {
                launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.d("Cause: " + e2.getMessage());
                launchIntentForPackage = null;
            }
        } else if (am.b.equals(str3)) {
            if (map.containsKey("intent_uri")) {
                String str4 = map.get("intent_uri");
                if (str4 != null) {
                    try {
                        launchIntentForPackage = Intent.parseUri(str4, 1);
                        try {
                            launchIntentForPackage.setPackage(str);
                        } catch (URISyntaxException e3) {
                            e = e3;
                            com.xiaomi.channel.commonutils.logger.b.d("Cause: " + e.getMessage());
                        }
                    } catch (URISyntaxException e4) {
                        e = e4;
                        launchIntentForPackage = null;
                    }
                } else {
                    launchIntentForPackage = null;
                }
            } else if (map.containsKey("class_name")) {
                String str5 = map.get("class_name");
                Intent intent2 = new Intent();
                intent2.setComponent(new ComponentName(str, str5));
                try {
                    if (map.containsKey("intent_flag")) {
                        intent2.setFlags(Integer.parseInt(map.get("intent_flag")));
                    }
                } catch (NumberFormatException e5) {
                    com.xiaomi.channel.commonutils.logger.b.d("Cause by intent_flag: " + e5.getMessage());
                }
                launchIntentForPackage = intent2;
            } else {
                launchIntentForPackage = null;
            }
        } else if (!am.c.equals(str3) || (str2 = map.get("web_uri")) == null) {
            launchIntentForPackage = null;
        } else {
            String strTrim = str2.trim();
            String str6 = (strTrim.startsWith("http://") || strTrim.startsWith("https://")) ? strTrim : "http://" + strTrim;
            try {
                String protocol = new URL(str6).getProtocol();
                if ("http".equals(protocol) || "https".equals(protocol)) {
                    launchIntentForPackage = new Intent("android.intent.action.VIEW");
                    try {
                        launchIntentForPackage.setData(Uri.parse(str6));
                    } catch (MalformedURLException e6) {
                        intent = launchIntentForPackage;
                        e = e6;
                        com.xiaomi.channel.commonutils.logger.b.d("Cause: " + e.getMessage());
                        launchIntentForPackage = intent;
                    }
                } else {
                    launchIntentForPackage = null;
                }
            } catch (MalformedURLException e7) {
                e = e7;
                intent = null;
            }
        }
        if (launchIntentForPackage == null) {
            return null;
        }
        launchIntentForPackage.addFlags(268435456);
        try {
            if (context.getPackageManager().resolveActivity(launchIntentForPackage, 65536) != null) {
                return launchIntentForPackage;
            }
            return null;
        } catch (Exception e8) {
            com.xiaomi.channel.commonutils.logger.b.d("Cause: " + e8.getMessage());
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:284:0x0709  */
    private PushMessageHandler.a a(ab abVar, boolean z, byte[] bArr) {
        int i;
        List<String> listA;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        PushMessageHandler.a aVarGenerateCommandMessage = null;
        try {
            org.apache.thrift.a aVarA = q.a(this.b, abVar);
            if (aVarA != null) {
                com.xiaomi.channel.commonutils.logger.b.c("receive a message." + aVarA);
                com.xiaomi.xmpush.thrift.a aVarA2 = abVar.a();
                com.xiaomi.channel.commonutils.logger.b.a("processing a message, action=" + aVarA2);
                switch (t.a[aVarA2.ordinal()]) {
                    case 1:
                        if (a.a(this.b).l() && !z) {
                            com.xiaomi.channel.commonutils.logger.b.a("receive a message in pause state. drop it");
                        } else {
                            aj ajVar = (aj) aVarA;
                            com.xiaomi.xmpush.thrift.q qVarL = ajVar.l();
                            if (qVarL != null) {
                                if (z) {
                                    if (ac.b(abVar)) {
                                        MiPushClient.reportIgnoreRegMessageClicked(this.b, qVarL.b(), abVar.m(), abVar.f, qVarL.d());
                                    } else {
                                        MiPushClient.reportMessageClicked(this.b, qVarL.b(), abVar.m(), qVarL.d());
                                    }
                                }
                                if (!z) {
                                    if (!TextUtils.isEmpty(ajVar.j()) && MiPushClient.aliasSetTime(this.b, ajVar.j()) < 0) {
                                        MiPushClient.addAlias(this.b, ajVar.j());
                                    } else if (!TextUtils.isEmpty(ajVar.h()) && MiPushClient.topicSubscribedTime(this.b, ajVar.h()) < 0) {
                                        MiPushClient.addTopic(this.b, ajVar.h());
                                    }
                                }
                                String str = (abVar.h == null || abVar.h.s() == null) ? null : abVar.h.j.get("jobkey");
                                String strB = TextUtils.isEmpty(str) ? qVarL.b() : str;
                                if (z || !a(this.b, strB)) {
                                    MiPushMessage miPushMessageGenerateMessage = PushMessageHelper.generateMessage(ajVar, abVar.m(), z);
                                    if (miPushMessageGenerateMessage.getPassThrough() == 0 && !z && ac.a(miPushMessageGenerateMessage.getExtra())) {
                                        ac.a(this.b, abVar, bArr);
                                    } else {
                                        com.xiaomi.channel.commonutils.logger.b.a("receive a message, msgid=" + qVarL.b() + ", jobkey=" + strB);
                                        if (z && miPushMessageGenerateMessage.getExtra() != null && miPushMessageGenerateMessage.getExtra().containsKey("notify_effect")) {
                                            Map<String, String> extra = miPushMessageGenerateMessage.getExtra();
                                            String str2 = extra.get("notify_effect");
                                            if (!ac.b(abVar)) {
                                                Intent intentA = a(this.b, this.b.getPackageName(), extra);
                                                if (intentA != null) {
                                                    if (!str2.equals(am.c)) {
                                                        intentA.putExtra("key_message", miPushMessageGenerateMessage);
                                                    }
                                                    this.b.startActivity(intentA);
                                                }
                                            } else {
                                                Intent intentA2 = a(this.b, abVar.f, extra);
                                                if (intentA2 != null) {
                                                    String strF = qVarL.f();
                                                    if (!TextUtils.isEmpty(strF)) {
                                                        intentA2.putExtra("payload", strF);
                                                    }
                                                    this.b.startActivity(intentA2);
                                                } else {
                                                    com.xiaomi.channel.commonutils.logger.b.a("Getting Intent fail from ignore reg message. ");
                                                }
                                            }
                                        } else {
                                            aVarGenerateCommandMessage = miPushMessageGenerateMessage;
                                        }
                                    }
                                } else {
                                    com.xiaomi.channel.commonutils.logger.b.a("drop a duplicate message, key=" + strB);
                                }
                                if (abVar.m() == null && !z) {
                                    a(ajVar, abVar);
                                }
                            } else {
                                com.xiaomi.channel.commonutils.logger.b.d("receive an empty message without push content, drop it");
                            }
                        }
                        break;
                    case 2:
                        ag agVar = (ag) aVarA;
                        if (agVar.f == 0) {
                            a.a(this.b).b(agVar.h, agVar.i);
                        }
                        if (TextUtils.isEmpty(agVar.h)) {
                            arrayList3 = null;
                        } else {
                            arrayList3 = new ArrayList();
                            arrayList3.add(agVar.h);
                        }
                        aVarGenerateCommandMessage = PushMessageHelper.generateCommandMessage("register", arrayList3, agVar.f, agVar.g, null);
                        u.a(this.b).e();
                        break;
                    case 3:
                        if (((an) aVarA).f == 0) {
                            a.a(this.b).h();
                            MiPushClient.clearExtras(this.b);
                        }
                        PushMessageHandler.a();
                        break;
                    case 4:
                        al alVar = (al) aVarA;
                        if (alVar.f == 0) {
                            MiPushClient.addTopic(this.b, alVar.h());
                        }
                        if (TextUtils.isEmpty(alVar.h())) {
                            arrayList2 = null;
                        } else {
                            arrayList2 = new ArrayList();
                            arrayList2.add(alVar.h());
                        }
                        aVarGenerateCommandMessage = PushMessageHelper.generateCommandMessage("subscribe-topic", arrayList2, alVar.f, alVar.g, alVar.k());
                        break;
                    case 5:
                        ap apVar = (ap) aVarA;
                        if (apVar.f == 0) {
                            MiPushClient.removeTopic(this.b, apVar.h());
                        }
                        if (TextUtils.isEmpty(apVar.h())) {
                            arrayList = null;
                        } else {
                            arrayList = new ArrayList();
                            arrayList.add(apVar.h());
                        }
                        aVarGenerateCommandMessage = PushMessageHelper.generateCommandMessage("unsubscibe-topic", arrayList, apVar.f, apVar.g, apVar.k());
                        break;
                    case 6:
                        aa aaVar = (aa) aVarA;
                        String strE = aaVar.e();
                        List<String> listK = aaVar.k();
                        if (aaVar.g != 0) {
                            listA = listK;
                        } else if (TextUtils.equals(strE, "accept-time") && listK != null && listK.size() > 1) {
                            MiPushClient.addAcceptTime(this.b, listK.get(0), listK.get(1));
                            if ("00:00".equals(listK.get(0)) && "00:00".equals(listK.get(1))) {
                                a.a(this.b).a(true);
                            } else {
                                a.a(this.b).a(false);
                            }
                            listA = a(TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault(), listK);
                        } else if (TextUtils.equals(strE, "set-alias") && listK != null && listK.size() > 0) {
                            MiPushClient.addAlias(this.b, listK.get(0));
                            listA = listK;
                        } else if (TextUtils.equals(strE, "unset-alias") && listK != null && listK.size() > 0) {
                            MiPushClient.removeAlias(this.b, listK.get(0));
                            listA = listK;
                        } else if (!TextUtils.equals(strE, "set-account") || listK == null || listK.size() <= 0) {
                            if (TextUtils.equals(strE, "unset-account") && listK != null && listK.size() > 0) {
                                MiPushClient.removeAccount(this.b, listK.get(0));
                            }
                            listA = listK;
                        } else {
                            MiPushClient.addAccount(this.b, listK.get(0));
                            listA = listK;
                        }
                        aVarGenerateCommandMessage = PushMessageHelper.generateCommandMessage(strE, listA, aaVar.g, aaVar.h, aaVar.m());
                        break;
                    case 7:
                        if (aVarA instanceof com.xiaomi.xmpush.thrift.x) {
                            com.xiaomi.xmpush.thrift.x xVar = (com.xiaomi.xmpush.thrift.x) aVarA;
                            String strC = xVar.c();
                            if (!o.DisablePushMessage.N.equalsIgnoreCase(xVar.e)) {
                                if (o.EnablePushMessage.N.equalsIgnoreCase(xVar.e)) {
                                    if (xVar.g == 0) {
                                        synchronized (p.class) {
                                            if (p.a(this.b).e(strC)) {
                                                p.a(this.b).d(strC);
                                                if ("enable_syncing".equals(p.a(this.b).a())) {
                                                    p.a(this.b).f("enable_synced");
                                                    p.a(this.b).d(strC);
                                                }
                                            }
                                        }
                                    } else if (!"enable_syncing".equals(p.a(this.b).a())) {
                                        p.a(this.b).d(strC);
                                    } else {
                                        synchronized (p.class) {
                                            if (p.a(this.b).e(strC)) {
                                                if (p.a(this.b).c(strC) < 10) {
                                                    p.a(this.b).b(strC);
                                                    u.a(this.b).a(false, strC);
                                                } else {
                                                    p.a(this.b).d(strC);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (xVar.g == 0) {
                                synchronized (p.class) {
                                    if (p.a(this.b).e(strC)) {
                                        p.a(this.b).d(strC);
                                        if ("disable_syncing".equals(p.a(this.b).a())) {
                                            p.a(this.b).f("disable_synced");
                                            MiPushClient.clearNotification(this.b);
                                            MiPushClient.clearLocalNotificationType(this.b);
                                            PushMessageHandler.a();
                                            u.a(this.b).b();
                                        }
                                    }
                                }
                            } else if (!"disable_syncing".equals(p.a(this.b).a())) {
                                p.a(this.b).d(strC);
                            } else {
                                synchronized (p.class) {
                                    if (p.a(this.b).e(strC)) {
                                        if (p.a(this.b).c(strC) < 10) {
                                            p.a(this.b).b(strC);
                                            u.a(this.b).a(true, strC);
                                        } else {
                                            p.a(this.b).d(strC);
                                        }
                                    }
                                }
                            }
                        } else if (aVarA instanceof ae) {
                            ae aeVar = (ae) aVarA;
                            if ("registration id expired".equalsIgnoreCase(aeVar.e)) {
                                MiPushClient.reInitialize(this.b, com.xiaomi.xmpush.thrift.t.RegIdExpired);
                            } else if (!"client_info_update_ok".equalsIgnoreCase(aeVar.e)) {
                                if (!"awake_app".equalsIgnoreCase(aeVar.e)) {
                                    if (o.NormalClientConfigUpdate.N.equalsIgnoreCase(aeVar.e)) {
                                        ad adVar = new ad();
                                        try {
                                            aq.a(adVar, aeVar.m());
                                            ai.a(ah.a(this.b), adVar);
                                        } catch (org.apache.thrift.f e) {
                                            com.xiaomi.channel.commonutils.logger.b.a(e);
                                        }
                                    } else if (!o.CustomClientConfigUpdate.N.equalsIgnoreCase(aeVar.e)) {
                                        if (o.SyncInfoResult.N.equalsIgnoreCase(aeVar.e)) {
                                            x.a(this.b, aeVar);
                                        } else if (o.ForceSync.N.equalsIgnoreCase(aeVar.e)) {
                                            com.xiaomi.channel.commonutils.logger.b.a("receive force sync notification");
                                            x.a(this.b, false);
                                        } else if (o.GeoRegsiter.N.equalsIgnoreCase(aeVar.e)) {
                                            f.a(this.b).a(aeVar);
                                        } else if (o.GeoUnregsiter.N.equalsIgnoreCase(aeVar.e)) {
                                            f.a(this.b).b(aeVar);
                                        } else if (o.GeoSync.N.equalsIgnoreCase(aeVar.e)) {
                                            f.a(this.b).c(aeVar);
                                        } else if (o.CancelPushMessage.N.equals(aeVar.e) && aeVar.i() != null) {
                                            if (aeVar.i().containsKey(am.H)) {
                                                String str3 = aeVar.i().get(am.H);
                                                if (TextUtils.isEmpty(str3)) {
                                                    i = -2;
                                                } else {
                                                    try {
                                                        i = Integer.parseInt(str3);
                                                    } catch (NumberFormatException e2) {
                                                        e2.printStackTrace();
                                                        i = -2;
                                                    }
                                                }
                                            } else {
                                                i = -2;
                                            }
                                            if (i < -1) {
                                                MiPushClient.clearNotification(this.b, aeVar.i().containsKey(am.F) ? aeVar.i().get(am.F) : "", aeVar.i().containsKey(am.G) ? aeVar.i().get(am.G) : "");
                                            } else {
                                                MiPushClient.clearNotification(this.b, i);
                                            }
                                        }
                                        break;
                                    } else {
                                        com.xiaomi.xmpush.thrift.ac acVar = new com.xiaomi.xmpush.thrift.ac();
                                        try {
                                            aq.a(acVar, aeVar.m());
                                            ai.a(ah.a(this.b), acVar);
                                        } catch (org.apache.thrift.f e3) {
                                            com.xiaomi.channel.commonutils.logger.b.a(e3);
                                        }
                                    }
                                } else if (aeVar.i() != null && aeVar.i().containsKey("packages")) {
                                    MiPushClient.awakeApps(this.b, aeVar.i().get("packages").split(","));
                                }
                            } else if (aeVar.i() != null && aeVar.i().containsKey("app_version")) {
                                a.a(this.b).a(aeVar.i().get("app_version"));
                            }
                        }
                        break;
                }
            } else {
                com.xiaomi.channel.commonutils.logger.b.d("receiving an un-recognized message. " + abVar.a);
            }
        } catch (c e4) {
            com.xiaomi.channel.commonutils.logger.b.a(e4);
            a(abVar);
        } catch (org.apache.thrift.f e5) {
            com.xiaomi.channel.commonutils.logger.b.a(e5);
            com.xiaomi.channel.commonutils.logger.b.d("receive a message which action string is not valid. is the reg expired?");
        }
        return aVarGenerateCommandMessage;
    }

    private PushMessageHandler.a a(ab abVar, byte[] bArr) {
        MiPushMessage miPushMessage = null;
        miPushMessage = null;
        miPushMessage = null;
        str = null;
        String str = null;
        miPushMessage = null;
        miPushMessage = null;
        try {
            org.apache.thrift.a aVarA = q.a(this.b, abVar);
            if (aVarA != null) {
                com.xiaomi.channel.commonutils.logger.b.c("message arrived: receive a message." + aVarA);
                com.xiaomi.xmpush.thrift.a aVarA2 = abVar.a();
                com.xiaomi.channel.commonutils.logger.b.a("message arrived: processing an arrived message, action=" + aVarA2);
                switch (t.a[aVarA2.ordinal()]) {
                    case 1:
                        aj ajVar = (aj) aVarA;
                        com.xiaomi.xmpush.thrift.q qVarL = ajVar.l();
                        if (qVarL != null) {
                            if (abVar.h != null && abVar.h.s() != null) {
                                str = abVar.h.j.get("jobkey");
                            }
                            MiPushMessage miPushMessageGenerateMessage = PushMessageHelper.generateMessage(ajVar, abVar.m(), false);
                            miPushMessageGenerateMessage.setArrivedMessage(true);
                            com.xiaomi.channel.commonutils.logger.b.a("message arrived: receive a message, msgid=" + qVarL.b() + ", jobkey=" + str);
                            miPushMessage = miPushMessageGenerateMessage;
                        } else {
                            com.xiaomi.channel.commonutils.logger.b.d("message arrived: receive an empty message without push content, drop it");
                        }
                        break;
                }
            } else {
                com.xiaomi.channel.commonutils.logger.b.d("message arrived: receiving an un-recognized message. " + abVar.a);
            }
        } catch (c e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            com.xiaomi.channel.commonutils.logger.b.d("message arrived: receive a message but decrypt failed. report when click.");
        } catch (org.apache.thrift.f e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            com.xiaomi.channel.commonutils.logger.b.d("message arrived: receive a message which action string is not valid. is the reg expired?");
        }
        return miPushMessage;
    }

    public static s a(Context context) {
        if (a == null) {
            a = new s(context);
        }
        return a;
    }

    private void a() {
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("mipush_extra", 0);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (Math.abs(jCurrentTimeMillis - sharedPreferences.getLong("last_reinitialize", 0L)) > 1800000) {
            MiPushClient.reInitialize(this.b, com.xiaomi.xmpush.thrift.t.PackageUnregistered);
            sharedPreferences.edit().putLong("last_reinitialize", jCurrentTimeMillis).commit();
        }
    }

    private void a(ab abVar) {
        com.xiaomi.channel.commonutils.logger.b.a("receive a message but decrypt failed. report now.");
        ae aeVar = new ae(abVar.m().a, false);
        aeVar.c(o.DecryptMessageFail.N);
        aeVar.b(abVar.h());
        aeVar.d(abVar.f);
        aeVar.h = new HashMap();
        aeVar.h.put("regid", MiPushClient.getRegId(this.b));
        u.a(this.b).a(aeVar, com.xiaomi.xmpush.thrift.a.Notification, false, null);
    }

    private void a(aj ajVar, ab abVar) {
        com.xiaomi.xmpush.thrift.r rVarM = abVar.m();
        com.xiaomi.xmpush.thrift.w wVar = new com.xiaomi.xmpush.thrift.w();
        wVar.b(ajVar.e());
        wVar.a(ajVar.c());
        wVar.a(ajVar.l().h());
        if (!TextUtils.isEmpty(ajVar.h())) {
            wVar.c(ajVar.h());
        }
        if (!TextUtils.isEmpty(ajVar.j())) {
            wVar.d(ajVar.j());
        }
        wVar.a(aq.a(this.b, abVar));
        u.a(this.b).a(wVar, com.xiaomi.xmpush.thrift.a.AckMessage, rVarM);
    }

    private static boolean a(Context context, String str) {
        boolean z = false;
        synchronized (d) {
            SharedPreferences sharedPreferencesJ = a.a(context).j();
            if (c == null) {
                String[] strArrSplit = sharedPreferencesJ.getString("pref_msg_ids", "").split(",");
                c = new LinkedList();
                for (String str2 : strArrSplit) {
                    c.add(str2);
                }
            }
            if (c.contains(str)) {
                z = true;
            } else {
                c.add(str);
                if (c.size() > 25) {
                    c.poll();
                }
                String strA = d.a(c, ",");
                SharedPreferences.Editor editorEdit = sharedPreferencesJ.edit();
                editorEdit.putString("pref_msg_ids", strA);
                editorEdit.commit();
            }
        }
        return z;
    }

    private void b(ab abVar) {
        com.xiaomi.xmpush.thrift.r rVarM = abVar.m();
        com.xiaomi.xmpush.thrift.w wVar = new com.xiaomi.xmpush.thrift.w();
        wVar.b(abVar.h());
        wVar.a(rVarM.b());
        wVar.a(rVarM.d());
        if (!TextUtils.isEmpty(rVarM.f())) {
            wVar.c(rVarM.f());
        }
        wVar.a(aq.a(this.b, abVar));
        u.a(this.b).a(wVar, com.xiaomi.xmpush.thrift.a.AckMessage, false, abVar.m());
    }

    public PushMessageHandler.a a(Intent intent) {
        String action = intent.getAction();
        com.xiaomi.channel.commonutils.logger.b.a("receive an intent from server, action=" + action);
        String stringExtra = intent.getStringExtra("mrt");
        if (stringExtra == null) {
            stringExtra = Long.toString(System.currentTimeMillis());
        }
        if ("com.xiaomi.mipush.RECEIVE_MESSAGE".equals(action)) {
            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
            boolean booleanExtra = intent.getBooleanExtra("mipush_notified", false);
            if (byteArrayExtra == null) {
                com.xiaomi.channel.commonutils.logger.b.d("receiving an empty message, drop");
                return null;
            }
            ab abVar = new ab();
            try {
                aq.a(abVar, byteArrayExtra);
                a aVarA = a.a(this.b);
                com.xiaomi.xmpush.thrift.r rVarM = abVar.m();
                if (abVar.a() == com.xiaomi.xmpush.thrift.a.SendMessage && rVarM != null && !aVarA.l() && !booleanExtra) {
                    if (rVarM != null) {
                        abVar.m().a("mrt", stringExtra);
                        abVar.m().a("mat", Long.toString(System.currentTimeMillis()));
                    }
                    b(abVar);
                }
                if (abVar.a() == com.xiaomi.xmpush.thrift.a.SendMessage && !abVar.c()) {
                    if (!ac.b(abVar)) {
                        Object[] objArr = new Object[2];
                        objArr[0] = abVar.j();
                        objArr[1] = rVarM != null ? rVarM.b() : "";
                        com.xiaomi.channel.commonutils.logger.b.a(String.format("drop an un-encrypted messages. %1$s, %2$s", objArr));
                        return null;
                    }
                    if (!booleanExtra || rVarM.s() == null || !rVarM.s().containsKey("notify_effect")) {
                        com.xiaomi.channel.commonutils.logger.b.a(String.format("drop an un-encrypted messages. %1$s, %2$s", abVar.j(), rVarM.b()));
                        return null;
                    }
                }
                if (aVarA.i() || abVar.a == com.xiaomi.xmpush.thrift.a.Registration) {
                    if (!aVarA.i() || !aVarA.n()) {
                        return a(abVar, booleanExtra, byteArrayExtra);
                    }
                    if (abVar.a == com.xiaomi.xmpush.thrift.a.UnRegistration) {
                        aVarA.h();
                        MiPushClient.clearExtras(this.b);
                        PushMessageHandler.a();
                    } else {
                        MiPushClient.unregisterPush(this.b);
                    }
                } else {
                    if (ac.b(abVar)) {
                        return a(abVar, booleanExtra, byteArrayExtra);
                    }
                    com.xiaomi.channel.commonutils.logger.b.d("receive message without registration. need re-register!");
                    a();
                }
            } catch (org.apache.thrift.f e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        } else {
            if ("com.xiaomi.mipush.ERROR".equals(action)) {
                MiPushCommandMessage miPushCommandMessage = new MiPushCommandMessage();
                ab abVar2 = new ab();
                try {
                    byte[] byteArrayExtra2 = intent.getByteArrayExtra("mipush_payload");
                    if (byteArrayExtra2 != null) {
                        aq.a(abVar2, byteArrayExtra2);
                    }
                } catch (org.apache.thrift.f e3) {
                }
                miPushCommandMessage.setCommand(String.valueOf(abVar2.a()));
                miPushCommandMessage.setResultCode(intent.getIntExtra("mipush_error_code", 0));
                miPushCommandMessage.setReason(intent.getStringExtra("mipush_error_msg"));
                com.xiaomi.channel.commonutils.logger.b.d("receive a error message. code = " + intent.getIntExtra("mipush_error_code", 0) + ", msg= " + intent.getStringExtra("mipush_error_msg"));
                return miPushCommandMessage;
            }
            if ("com.xiaomi.mipush.MESSAGE_ARRIVED".equals(action)) {
                byte[] byteArrayExtra3 = intent.getByteArrayExtra("mipush_payload");
                if (byteArrayExtra3 == null) {
                    com.xiaomi.channel.commonutils.logger.b.d("message arrived: receiving an empty message, drop");
                    return null;
                }
                ab abVar3 = new ab();
                try {
                    aq.a(abVar3, byteArrayExtra3);
                    a aVarA2 = a.a(this.b);
                    if (ac.b(abVar3)) {
                        com.xiaomi.channel.commonutils.logger.b.d("message arrived: receive ignore reg message, ignore!");
                    } else if (!aVarA2.i()) {
                        com.xiaomi.channel.commonutils.logger.b.d("message arrived: receive message without registration. need unregister or re-register!");
                    } else {
                        if (!aVarA2.i() || !aVarA2.n()) {
                            return a(abVar3, byteArrayExtra3);
                        }
                        com.xiaomi.channel.commonutils.logger.b.d("message arrived: app info is invalidated");
                    }
                } catch (org.apache.thrift.f e4) {
                    com.xiaomi.channel.commonutils.logger.b.a(e4);
                } catch (Exception e5) {
                    com.xiaomi.channel.commonutils.logger.b.a(e5);
                }
            }
        }
        return null;
    }

    public List<String> a(TimeZone timeZone, TimeZone timeZone2, List<String> list) {
        if (timeZone.equals(timeZone2)) {
            return list;
        }
        long rawOffset = ((timeZone.getRawOffset() - timeZone2.getRawOffset()) / 1000) / 60;
        long j = ((((Long.parseLong(list.get(0).split(":")[0]) * 60) + Long.parseLong(list.get(0).split(":")[1])) - rawOffset) + 1440) % 1440;
        long j2 = (((Long.parseLong(list.get(1).split(":")[1]) + (60 * Long.parseLong(list.get(1).split(":")[0]))) - rawOffset) + 1440) % 1440;
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j / 60), Long.valueOf(j % 60)));
        arrayList.add(String.format("%1$02d:%2$02d", Long.valueOf(j2 / 60), Long.valueOf(j2 % 60)));
        return arrayList;
    }
}
