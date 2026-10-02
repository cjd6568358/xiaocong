package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.push.service.am;
import com.xiaomi.push.service.ao;
import com.xiaomi.xmpush.thrift.ab;
import com.xiaomi.xmpush.thrift.ae;
import com.xiaomi.xmpush.thrift.af;
import com.xiaomi.xmpush.thrift.aq;
import com.xiaomi.xmpush.thrift.o;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class u {
    private static u b;
    private static final ArrayList<a> e = new ArrayList<>();
    private boolean a;
    private Context c;
    private Handler f;
    private Intent g = null;
    private Integer h = null;
    private String d = null;

    static class a<T extends org.apache.thrift.a<T, ?>> {
        T a;
        com.xiaomi.xmpush.thrift.a b;
        boolean c;

        a() {
        }
    }

    private u(Context context) {
        this.a = false;
        this.f = null;
        this.c = context.getApplicationContext();
        this.a = h();
        this.f = new v(this, Looper.getMainLooper());
    }

    public static u a(Context context) {
        if (b == null) {
            b = new u(context);
        }
        return b;
    }

    private void a(Intent intent) {
        try {
            this.c.startService(intent);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a(String str, boolean z) {
        ae aeVar;
        if (com.xiaomi.mipush.sdk.a.a(this.c).b() && d.d(this.c)) {
            ae aeVar2 = new ae();
            Intent intentI = i();
            if (TextUtils.isEmpty(str)) {
                str = MiPushClient.generatePacketID();
                aeVar2.a(str);
                aeVar = new ae(str, true);
                synchronized (p.class) {
                    p.a(this.c).a(str);
                }
            } else {
                aeVar2.a(str);
                aeVar = new ae(str, true);
            }
            if (z) {
                aeVar2.c(o.DisablePushMessage.N);
                aeVar.c(o.DisablePushMessage.N);
                intentI.setAction("com.xiaomi.mipush.DISABLE_PUSH_MESSAGE");
            } else {
                aeVar2.c(o.EnablePushMessage.N);
                aeVar.c(o.EnablePushMessage.N);
                intentI.setAction("com.xiaomi.mipush.ENABLE_PUSH_MESSAGE");
            }
            aeVar2.b(com.xiaomi.mipush.sdk.a.a(this.c).c());
            aeVar2.d(this.c.getPackageName());
            a(aeVar2, com.xiaomi.xmpush.thrift.a.Notification, false, null);
            aeVar.b(com.xiaomi.mipush.sdk.a.a(this.c).c());
            aeVar.d(this.c.getPackageName());
            byte[] bArrA = aq.a(q.a(this.c, aeVar, com.xiaomi.xmpush.thrift.a.Notification, false, this.c.getPackageName(), com.xiaomi.mipush.sdk.a.a(this.c).c()));
            if (bArrA != null) {
                intentI.putExtra("mipush_payload", bArrA);
                intentI.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
                intentI.putExtra("mipush_app_id", com.xiaomi.mipush.sdk.a.a(this.c).c());
                intentI.putExtra("mipush_app_token", com.xiaomi.mipush.sdk.a.a(this.c).d());
                a(intentI);
            }
            Message messageObtain = Message.obtain();
            int i = z ? 1 : 0;
            messageObtain.obj = str;
            messageObtain.arg1 = i;
            this.f.sendMessageDelayed(messageObtain, 5000L);
        }
    }

    private boolean h() {
        try {
            PackageInfo packageInfo = this.c.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
            return packageInfo != null && packageInfo.versionCode >= 105;
        } catch (Exception e2) {
            return false;
        }
    }

    private Intent i() {
        Intent intent = new Intent();
        String packageName = this.c.getPackageName();
        if (!c() || "com.xiaomi.xmsf".equals(packageName)) {
            l();
            intent.setComponent(new ComponentName(this.c, "com.xiaomi.push.service.XMPushService"));
            intent.putExtra("mipush_app_package", packageName);
        } else {
            intent.setPackage("com.xiaomi.xmsf");
            intent.setClassName("com.xiaomi.xmsf", j());
            intent.putExtra("mipush_app_package", packageName);
            k();
        }
        return intent;
    }

    private String j() {
        try {
            return this.c.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4).versionCode >= 106 ? "com.xiaomi.push.service.XMPushService" : "com.xiaomi.xmsf.push.service.XMPushService";
        } catch (Exception e2) {
        }
    }

    private void k() {
        try {
            this.c.getPackageManager().setComponentEnabledSetting(new ComponentName(this.c, "com.xiaomi.push.service.XMPushService"), 2, 1);
        } catch (Throwable th) {
        }
    }

    private void l() {
        try {
            this.c.getPackageManager().setComponentEnabledSetting(new ComponentName(this.c, "com.xiaomi.push.service.XMPushService"), 1, 1);
        } catch (Throwable th) {
        }
    }

    private boolean m() {
        String packageName = this.c.getPackageName();
        return packageName.contains("miui") || packageName.contains("xiaomi") || (this.c.getApplicationInfo().flags & 1) != 0;
    }

    public void a() {
        a(i());
    }

    public void a(int i) {
        Intent intentI = i();
        intentI.setAction("com.xiaomi.mipush.CLEAR_NOTIFICATION");
        intentI.putExtra(am.y, this.c.getPackageName());
        intentI.putExtra(am.z, i);
        a(intentI);
    }

    public final void a(af afVar, boolean z) {
        this.g = null;
        Intent intentI = i();
        byte[] bArrA = aq.a(q.a(this.c, afVar, com.xiaomi.xmpush.thrift.a.Registration));
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.a("register fail, because msgBytes is null.");
            return;
        }
        intentI.setAction("com.xiaomi.mipush.REGISTER_APP");
        intentI.putExtra("mipush_app_id", com.xiaomi.mipush.sdk.a.a(this.c).c());
        intentI.putExtra("mipush_payload", bArrA);
        intentI.putExtra("mipush_session", this.d);
        intentI.putExtra("mipush_env_chanage", z);
        intentI.putExtra("mipush_env_type", com.xiaomi.mipush.sdk.a.a(this.c).m());
        if (d.d(this.c) && g()) {
            a(intentI);
        } else {
            this.g = intentI;
        }
    }

    public final void a(com.xiaomi.xmpush.thrift.am amVar) {
        Intent intentI = i();
        byte[] bArrA = aq.a(q.a(this.c, amVar, com.xiaomi.xmpush.thrift.a.UnRegistration));
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.a("unregister fail, because msgBytes is null.");
            return;
        }
        intentI.setAction("com.xiaomi.mipush.UNREGISTER_APP");
        intentI.putExtra("mipush_app_id", com.xiaomi.mipush.sdk.a.a(this.c).c());
        intentI.putExtra("mipush_payload", bArrA);
        a(intentI);
    }

    public void a(String str, String str2) {
        Intent intentI = i();
        intentI.setAction("com.xiaomi.mipush.CLEAR_NOTIFICATION");
        intentI.putExtra(am.y, this.c.getPackageName());
        intentI.putExtra(am.D, str);
        intentI.putExtra(am.E, str2);
        a(intentI);
    }

    public final <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, com.xiaomi.xmpush.thrift.r rVar) {
        a(t, aVar, !aVar.equals(com.xiaomi.xmpush.thrift.a.Registration), rVar);
    }

    public <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, boolean z) {
        a aVar2 = new a();
        aVar2.a = t;
        aVar2.b = aVar;
        aVar2.c = z;
        synchronized (e) {
            e.add(aVar2);
            if (e.size() > 10) {
                e.remove(0);
            }
        }
    }

    public final <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, boolean z, com.xiaomi.xmpush.thrift.r rVar) {
        a(t, aVar, z, true, rVar, true);
    }

    public final <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, boolean z, boolean z2, com.xiaomi.xmpush.thrift.r rVar, boolean z3) {
        a(t, aVar, z, z2, rVar, z3, this.c.getPackageName(), com.xiaomi.mipush.sdk.a.a(this.c).c());
    }

    public final <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, boolean z, boolean z2, com.xiaomi.xmpush.thrift.r rVar, boolean z3, String str, String str2) {
        if (!com.xiaomi.mipush.sdk.a.a(this.c).i()) {
            if (z2) {
                a(t, aVar, z);
                return;
            } else {
                com.xiaomi.channel.commonutils.logger.b.a("drop the message before initialization.");
                return;
            }
        }
        Intent intentI = i();
        ab abVarA = q.a(this.c, t, aVar, z, str, str2);
        if (rVar != null) {
            abVarA.a(rVar);
        }
        byte[] bArrA = aq.a(abVarA);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.a("send message fail, because msgBytes is null.");
            return;
        }
        intentI.setAction("com.xiaomi.mipush.SEND_MESSAGE");
        intentI.putExtra("mipush_payload", bArrA);
        intentI.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", z3);
        a(intentI);
    }

    public final void a(boolean z) {
        a(z, (String) null);
    }

    public final void a(boolean z, String str) {
        if (z) {
            p.a(this.c).f("disable_syncing");
        } else {
            p.a(this.c).f("enable_syncing");
        }
        a(str, z);
    }

    public final void b() {
        Intent intentI = i();
        intentI.setAction("com.xiaomi.mipush.DISABLE_PUSH");
        a(intentI);
    }

    public boolean c() {
        return this.a && 1 == com.xiaomi.mipush.sdk.a.a(this.c).m();
    }

    public void d() {
        if (this.g != null) {
            a(this.g);
            this.g = null;
        }
    }

    public void e() {
        synchronized (e) {
            for (a aVar : e) {
                a(aVar.a, aVar.b, aVar.c, false, null, true);
            }
            e.clear();
        }
    }

    public void f() {
        Intent intentI = i();
        intentI.setAction("com.xiaomi.mipush.SET_NOTIFICATION_TYPE");
        intentI.putExtra(am.y, this.c.getPackageName());
        intentI.putExtra(am.C, com.xiaomi.channel.commonutils.string.c.b(this.c.getPackageName()));
        a(intentI);
    }

    public boolean g() {
        if (!c() || !m()) {
            return true;
        }
        if (this.h == null) {
            this.h = Integer.valueOf(ao.a(this.c).b());
            if (this.h.intValue() == 0) {
                this.c.getContentResolver().registerContentObserver(ao.a(this.c).c(), false, new w(this, new Handler(Looper.getMainLooper())));
            }
        }
        return this.h.intValue() != 0;
    }
}
