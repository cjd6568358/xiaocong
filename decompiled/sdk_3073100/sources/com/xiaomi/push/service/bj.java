package com.xiaomi.push.service;

import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bj extends XMPushService.h {
    final /* synthetic */ ArrayList b;
    final /* synthetic */ bi c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    bj(bi biVar, int i, ArrayList arrayList) {
        super(i);
        this.c = biVar;
        this.b = arrayList;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        String packageName = this.c.a.getPackageName();
        String strA = this.c.a(packageName);
        ArrayList<com.xiaomi.xmpush.thrift.ae> arrayListA = av.a(this.b, packageName, strA);
        if (arrayListA == null) {
            com.xiaomi.channel.commonutils.logger.b.d("Get a null XmPushActionNotification when TinyDataHelper.transToTriftObj() in XMPushService.");
            return;
        }
        for (com.xiaomi.xmpush.thrift.ae aeVar : arrayListA) {
            aeVar.a("uploadWay", "longXMPushService");
            this.c.a.a(packageName, com.xiaomi.xmpush.thrift.aq.a(aa.a(packageName, strA, aeVar, com.xiaomi.xmpush.thrift.a.Notification)), true);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "Send tiny data.";
    }
}
