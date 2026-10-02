package com.xiaomi.slim;

import android.text.TextUtils;
import com.xiaomi.push.protobuf.b;
import com.xiaomi.push.service.ak;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class a {
    public static void a(ak.b bVar, String str, com.xiaomi.smack.a aVar) {
        String strA;
        b.c cVar = new b.c();
        if (!TextUtils.isEmpty(bVar.c)) {
            cVar.a(bVar.c);
        }
        if (!TextUtils.isEmpty(bVar.f)) {
            cVar.d(bVar.f);
        }
        if (!TextUtils.isEmpty(bVar.g)) {
            cVar.e(bVar.g);
        }
        cVar.b(bVar.e ? "1" : "0");
        if (TextUtils.isEmpty(bVar.d)) {
            cVar.c("XIAOMI-SASL");
        } else {
            cVar.c(bVar.d);
        }
        b bVar2 = new b();
        bVar2.c(bVar.b);
        bVar2.a(Integer.parseInt(bVar.h));
        bVar2.b(bVar.a);
        bVar2.a("BIND", (String) null);
        bVar2.a(bVar2.h());
        com.xiaomi.channel.commonutils.logger.b.a("[Slim]: bind id=" + bVar2.h());
        HashMap map = new HashMap();
        map.put("challenge", str);
        map.put("token", bVar.c);
        map.put("chid", bVar.h);
        map.put("from", bVar.b);
        map.put("id", bVar2.h());
        map.put("to", "xiaomi.com");
        if (bVar.e) {
            map.put("kick", "1");
        } else {
            map.put("kick", "0");
        }
        if (TextUtils.isEmpty(bVar.f)) {
            map.put("client_attrs", "");
        } else {
            map.put("client_attrs", bVar.f);
        }
        if (TextUtils.isEmpty(bVar.g)) {
            map.put("cloud_attrs", "");
        } else {
            map.put("cloud_attrs", bVar.g);
        }
        if (bVar.d.equals("XIAOMI-PASS") || bVar.d.equals("XMPUSH-PASS")) {
            strA = com.xiaomi.channel.commonutils.string.b.a(bVar.d, null, map, bVar.i);
        } else {
            if (bVar.d.equals("XIAOMI-SASL")) {
            }
            strA = null;
        }
        cVar.f(strA);
        bVar2.a(cVar.c(), (String) null);
        aVar.b(bVar2);
    }

    public static void a(String str, String str2, com.xiaomi.smack.a aVar) {
        b bVar = new b();
        bVar.c(str2);
        bVar.a(Integer.parseInt(str));
        bVar.a("UBND", (String) null);
        aVar.b(bVar);
    }
}
