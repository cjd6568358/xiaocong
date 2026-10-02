package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import com.xiaomi.smack.l;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class r {
    private static final Map<String, byte[]> a = new HashMap();
    private static ArrayList<Pair<String, byte[]>> b = new ArrayList<>();

    public static void a(Context context, int i, String str) {
        synchronized (a) {
            for (String str2 : a.keySet()) {
                a(context, str2, a.get(str2), i, str);
            }
            a.clear();
        }
    }

    public static void a(Context context, String str, byte[] bArr, int i, String str2) {
        Intent intent = new Intent("com.xiaomi.mipush.ERROR");
        intent.setPackage(str);
        intent.putExtra("mipush_payload", bArr);
        intent.putExtra("mipush_error_code", i);
        intent.putExtra("mipush_error_msg", str2);
        context.sendBroadcast(intent, b.a(str));
    }

    public static void a(XMPushService xMPushService) {
        try {
            synchronized (a) {
                for (String str : a.keySet()) {
                    aa.a(xMPushService, str, a.get(str));
                }
                a.clear();
            }
        } catch (l e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            xMPushService.a(10, e);
        }
    }

    public static void a(String str, byte[] bArr) {
        synchronized (a) {
            a.put(str, bArr);
        }
    }

    public static void b(XMPushService xMPushService) {
        ArrayList<Pair<String, byte[]>> arrayList;
        try {
            synchronized (b) {
                arrayList = b;
                b = new ArrayList<>();
            }
            for (Pair<String, byte[]> pair : arrayList) {
                aa.a(xMPushService, (String) pair.first, (byte[]) pair.second);
            }
        } catch (l e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            xMPushService.a(10, e);
        }
    }

    public static void b(String str, byte[] bArr) {
        synchronized (b) {
            b.add(new Pair<>(str, bArr));
            if (b.size() > 50) {
                b.remove(0);
            }
        }
    }
}
