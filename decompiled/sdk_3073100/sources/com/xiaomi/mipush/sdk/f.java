package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.service.e;
import com.xiaomi.push.service.h;
import com.xiaomi.xmpush.thrift.ae;
import com.xiaomi.xmpush.thrift.aq;
import com.xiaomi.xmpush.thrift.o;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class f {
    private static volatile f a;
    private final String b = "GeoFenceRegMessageProcessor.";
    private Context c;

    private f(Context context) {
        this.c = context;
    }

    public static f a(Context context) {
        if (a == null) {
            synchronized (f.class) {
                if (a == null) {
                    a = new f(context);
                }
            }
        }
        return a;
    }

    private com.xiaomi.xmpush.thrift.s a() {
        ArrayList<com.xiaomi.xmpush.thrift.j> arrayListA = e.a(this.c).a();
        com.xiaomi.xmpush.thrift.s sVar = new com.xiaomi.xmpush.thrift.s();
        TreeSet treeSet = new TreeSet();
        Iterator<com.xiaomi.xmpush.thrift.j> it = arrayListA.iterator();
        while (it.hasNext()) {
            treeSet.add(it.next());
        }
        sVar.a(treeSet);
        return sVar;
    }

    private void a(com.xiaomi.xmpush.thrift.j jVar) {
        byte[] bArrA = aq.a(jVar);
        ae aeVar = new ae("-1", false);
        aeVar.c(o.GeoPackageUninstalled.N);
        aeVar.a(bArrA);
        u.a(this.c).a(aeVar, com.xiaomi.xmpush.thrift.a.Notification, true, null);
        com.xiaomi.channel.commonutils.logger.b.a("GeoFenceRegMessageProcessor.report package not exist geo_fencing id:" + jVar.a());
    }

    private void a(com.xiaomi.xmpush.thrift.j jVar, boolean z) {
        byte[] bArrA = aq.a(jVar);
        ae aeVar = new ae("-1", false);
        aeVar.c(z ? o.GeoRegsiterResult.N : o.GeoUnregsiterResult.N);
        aeVar.a(bArrA);
        u.a(this.c).a(aeVar, com.xiaomi.xmpush.thrift.a.Notification, true, null);
        com.xiaomi.channel.commonutils.logger.b.a("GeoFenceRegMessageProcessor.report geo_fencing id:" + jVar.a() + " " + (z ? "geo_reg" : "geo_unreg"));
    }

    private com.xiaomi.xmpush.thrift.j d(ae aeVar) {
        if (!h.a(this.c) || !h.b(this.c)) {
            return null;
        }
        try {
            com.xiaomi.xmpush.thrift.j jVar = new com.xiaomi.xmpush.thrift.j();
            aq.a(jVar, aeVar.m());
            return jVar;
        } catch (org.apache.thrift.f e) {
            e.printStackTrace();
            return null;
        }
    }

    public void a(ae aeVar) {
        com.xiaomi.xmpush.thrift.j jVarD = d(aeVar);
        if (jVarD == null) {
            com.xiaomi.channel.commonutils.logger.b.d("registration convert geofence object failed notification_id:" + aeVar.c());
            return;
        }
        if (!com.xiaomi.channel.commonutils.android.b.f(this.c, jVarD.g())) {
            a(jVarD);
            return;
        }
        if (e.a(this.c).a(jVarD) == -1) {
            com.xiaomi.channel.commonutils.logger.b.a("GeoFenceRegMessageProcessor. insert a new geofence failed about geo_id:" + jVarD.a());
        }
        new g(this.c).a(jVarD);
        a(jVarD, true);
        com.xiaomi.channel.commonutils.logger.b.a("receive geo reg notification");
    }

    public void b(ae aeVar) {
        com.xiaomi.xmpush.thrift.j jVarD = d(aeVar);
        if (jVarD == null) {
            com.xiaomi.channel.commonutils.logger.b.d("unregistration convert geofence object failed notification_id:" + aeVar.c());
            return;
        }
        if (!com.xiaomi.channel.commonutils.android.b.f(this.c, jVarD.g())) {
            a(jVarD);
            return;
        }
        if (e.a(this.c).d(jVarD.a()) == 0) {
            com.xiaomi.channel.commonutils.logger.b.a("GeoFenceRegMessageProcessor. delete a geofence about geo_id:" + jVarD.a() + " falied");
        }
        if (com.xiaomi.push.service.g.a(this.c).b(jVarD.a()) == 0) {
            com.xiaomi.channel.commonutils.logger.b.a("GeoFenceRegMessageProcessor. delete all geofence messages about geo_id:" + jVarD.a() + " failed");
        }
        new g(this.c).a(jVarD.a());
        a(jVarD, false);
        com.xiaomi.channel.commonutils.logger.b.a("receive geo unreg notification");
    }

    public void c(ae aeVar) {
        if (h.a(this.c) && h.b(this.c) && com.xiaomi.channel.commonutils.android.b.f(this.c, aeVar.i)) {
            com.xiaomi.xmpush.thrift.s sVarA = a();
            byte[] bArrA = aq.a(sVarA);
            ae aeVar2 = new ae("-1", false);
            aeVar2.c(o.GeoUpload.N);
            aeVar2.a(bArrA);
            u.a(this.c).a(aeVar2, com.xiaomi.xmpush.thrift.a.Notification, true, null);
            com.xiaomi.channel.commonutils.logger.b.c("GeoFenceRegMessageProcessor.sync_geo_data. geos size:" + sVarA.a().size());
        }
    }
}
