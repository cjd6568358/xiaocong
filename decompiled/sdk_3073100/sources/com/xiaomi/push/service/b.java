package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    private s a = new s();

    public static String a(String str) {
        return str + ".permission.MIPUSH_RECEIVE";
    }

    private static void a(Context context, Intent intent, String str) {
        if ("com.xiaomi.xmsf".equals(context.getPackageName())) {
            context.sendBroadcast(intent);
        } else {
            context.sendBroadcast(intent, a(str));
        }
    }

    ak.b a(com.xiaomi.slim.b bVar) {
        Collection<ak.b> collectionC = ak.a().c(Integer.toString(bVar.c()));
        if (collectionC.isEmpty()) {
            return null;
        }
        Iterator<ak.b> it = collectionC.iterator();
        if (collectionC.size() == 1) {
            return it.next();
        }
        String strJ = bVar.j();
        while (it.hasNext()) {
            ak.b next = it.next();
            if (TextUtils.equals(strJ, next.b)) {
                return next;
            }
        }
        return null;
    }

    ak.b a(com.xiaomi.smack.packet.d dVar) {
        Collection<ak.b> collectionC = ak.a().c(dVar.l());
        if (collectionC.isEmpty()) {
            return null;
        }
        Iterator<ak.b> it = collectionC.iterator();
        if (collectionC.size() == 1) {
            return it.next();
        }
        String strN = dVar.n();
        String strM = dVar.m();
        while (it.hasNext()) {
            ak.b next = it.next();
            if (TextUtils.equals(strN, next.b) || TextUtils.equals(strM, next.b)) {
                return next;
            }
        }
        return null;
    }

    public void a(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.service_started");
        context.sendBroadcast(intent);
    }

    public void a(Context context, ak.b bVar, int i) {
        if ("5".equalsIgnoreCase(bVar.h)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.channel_closed");
        intent.setPackage(bVar.a);
        intent.putExtra(am.q, bVar.h);
        intent.putExtra("ext_reason", i);
        intent.putExtra(am.p, bVar.b);
        intent.putExtra(am.B, bVar.j);
        a(context, intent, bVar.a);
    }

    public void a(Context context, ak.b bVar, String str, String str2) {
        if ("5".equalsIgnoreCase(bVar.h)) {
            com.xiaomi.channel.commonutils.logger.b.d("mipush kicked by server");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.kicked");
        intent.setPackage(bVar.a);
        intent.putExtra("ext_kick_type", str);
        intent.putExtra("ext_kick_reason", str2);
        intent.putExtra("ext_chid", bVar.h);
        intent.putExtra(am.p, bVar.b);
        intent.putExtra(am.B, bVar.j);
        a(context, intent, bVar.a);
    }

    public void a(Context context, ak.b bVar, boolean z, int i, String str) {
        if ("5".equalsIgnoreCase(bVar.h)) {
            this.a.a(context, bVar, z, i, str);
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.push.channel_opened");
        intent.setPackage(bVar.a);
        intent.putExtra("ext_succeeded", z);
        if (!z) {
            intent.putExtra("ext_reason", i);
        }
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("ext_reason_msg", str);
        }
        intent.putExtra("ext_chid", bVar.h);
        intent.putExtra(am.p, bVar.b);
        intent.putExtra(am.B, bVar.j);
        a(context, intent, bVar.a);
    }

    public void a(XMPushService xMPushService, String str, com.xiaomi.slim.b bVar) {
        ak.b bVarA = a(bVar);
        if (bVarA == null) {
            com.xiaomi.channel.commonutils.logger.b.d("error while notify channel closed! channel " + str + " not registered");
        } else if ("5".equalsIgnoreCase(str)) {
            this.a.a(xMPushService, bVar, bVarA);
        } else {
            com.xiaomi.channel.commonutils.logger.b.a("don't support binary yet");
        }
    }

    public void a(XMPushService xMPushService, String str, com.xiaomi.smack.packet.d dVar) {
        String str2;
        ak.b bVarA = a(dVar);
        if (bVarA == null) {
            com.xiaomi.channel.commonutils.logger.b.d("error while notify channel closed! channel " + str + " not registered");
            return;
        }
        if ("5".equalsIgnoreCase(str)) {
            this.a.a(xMPushService, dVar, bVarA);
            return;
        }
        String str3 = bVarA.a;
        if (dVar instanceof com.xiaomi.smack.packet.c) {
            str2 = "com.xiaomi.push.new_msg";
        } else if (dVar instanceof com.xiaomi.smack.packet.b) {
            str2 = "com.xiaomi.push.new_iq";
        } else {
            if (!(dVar instanceof com.xiaomi.smack.packet.f)) {
                com.xiaomi.channel.commonutils.logger.b.d("unknown packet type, drop it");
                return;
            }
            str2 = "com.xiaomi.push.new_pres";
        }
        Intent intent = new Intent();
        intent.setAction(str2);
        intent.setPackage(str3);
        intent.putExtra("ext_chid", str);
        intent.putExtra("ext_packet", dVar.b());
        intent.putExtra(am.B, bVarA.j);
        intent.putExtra(am.u, bVarA.i);
        a(xMPushService, intent, str3);
    }
}
