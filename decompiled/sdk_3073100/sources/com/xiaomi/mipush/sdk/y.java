package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.channel.commonutils.android.e;
import com.xiaomi.channel.commonutils.android.h;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.xmpush.thrift.ae;
import com.xiaomi.xmpush.thrift.o;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class y implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ boolean b;

    y(Context context, boolean z) {
        this.a = context;
        this.b = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.channel.commonutils.logger.b.a("do sync info");
        ae aeVar = new ae(MiPushClient.generatePacketID(), false);
        a aVarA = a.a(this.a);
        aeVar.c(o.SyncInfo.N);
        aeVar.b(aVarA.c());
        aeVar.d(this.a.getPackageName());
        aeVar.h = new HashMap();
        h.a(aeVar.h, "app_version", com.xiaomi.channel.commonutils.android.b.a(this.a, this.a.getPackageName()));
        h.a(aeVar.h, "app_version_code", Integer.toString(com.xiaomi.channel.commonutils.android.b.b(this.a, this.a.getPackageName())));
        h.a(aeVar.h, "push_sdk_vn", "3_2_2");
        h.a(aeVar.h, "push_sdk_vc", Integer.toString(30202));
        h.a(aeVar.h, "token", aVarA.d());
        h.a(aeVar.h, "imei_md5", d.a(e.c(this.a)));
        h.a(aeVar.h, "reg_id", aVarA.e());
        h.a(aeVar.h, "reg_secret", aVarA.f());
        h.a(aeVar.h, "accept_time", MiPushClient.getAcceptTime(this.a).replace(",", "-"));
        if (this.b) {
            h.a(aeVar.h, "aliases_md5", x.c(MiPushClient.getAllAlias(this.a)));
            h.a(aeVar.h, "topics_md5", x.c(MiPushClient.getAllTopic(this.a)));
            h.a(aeVar.h, "accounts_md5", x.c(MiPushClient.getAllUserAccount(this.a)));
        } else {
            h.a(aeVar.h, "aliases", x.d(MiPushClient.getAllAlias(this.a)));
            h.a(aeVar.h, "topics", x.d(MiPushClient.getAllTopic(this.a)));
            h.a(aeVar.h, "user_accounts", x.d(MiPushClient.getAllUserAccount(this.a)));
        }
        u.a(this.a).a(aeVar, com.xiaomi.xmpush.thrift.a.Notification, false, null);
    }
}
