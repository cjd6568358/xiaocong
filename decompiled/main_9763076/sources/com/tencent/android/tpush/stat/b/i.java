package com.tencent.android.tpush.stat.b;

import android.content.Context;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.t;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class i {
    private static i h = null;
    private Map e;
    private Context f;
    private com.tencent.android.tpush.stat.a.f g = com.tencent.android.tpush.stat.a.e.b();
    Map a = null;
    d b = null;
    Map c = null;
    private d i = null;
    boolean d = true;

    private i(Context context) {
        this.e = null;
        this.f = null;
        this.f = context.getApplicationContext();
        this.e = new HashMap(3);
        this.e.put(1, new g(context, 3));
        this.e.put(2, new b(context, 3));
        this.e.put(4, new f(context, 3));
    }

    public static synchronized i a(Context context) {
        if (h == null) {
            h = new i(context);
        }
        return h;
    }

    private Map e() {
        if (this.a == null) {
            this.a = new HashMap(3);
            this.a.put(1, new g(this.f, 1000001));
            this.a.put(2, new b(this.f, 1000001));
            this.a.put(4, new f(this.f, 1000001));
        }
        return this.a;
    }

    public void a(d dVar) {
        a(dVar, true);
    }

    public void a(d dVar, boolean z) {
        if (dVar.b() <= 0) {
            dVar.b(System.currentTimeMillis());
        }
        com.tencent.android.tpush.a.a.c(Constants.LogTag, "writeNewVersionMidEntity midEntity:" + dVar);
        for (Map.Entry entry : e().entrySet()) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "writeMidEntity new ver:" + dVar);
            ((h) entry.getValue()).a(dVar);
        }
        if (z) {
            com.tencent.android.tpush.a.b(this.f, this.f.getPackageName(), dVar.toString());
        }
    }

    public void b(d dVar) {
        b(dVar, true);
    }

    public void b(d dVar, boolean z) {
        if (dVar.b() <= 0) {
            dVar.b(System.currentTimeMillis());
        }
        com.tencent.android.tpush.a.a.c(Constants.LogTag, "writeOldVersionMidEntity midEntity:" + dVar);
        for (Map.Entry entry : f().entrySet()) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "writeMidEntity old ver:" + dVar);
            ((h) entry.getValue()).a(dVar);
        }
        if (z) {
            com.tencent.android.tpush.a.c(this.f, this.f.getPackageName(), dVar.toString());
        }
    }

    public d a() {
        return a(4, e());
    }

    private Map f() {
        if (this.c == null) {
            this.c = new HashMap(3);
            this.c.put(1, new g(this.f, 3));
            this.c.put(2, new b(this.f, 3));
            this.c.put(4, new f(this.f, 3));
        }
        return this.c;
    }

    public d b() {
        if (!t.a(this.i)) {
            this.i = a();
            if (this.i == null || !this.i.c()) {
                this.i = d();
            }
        }
        if (!t.a(this.i)) {
            String strD = com.tencent.android.tpush.a.d(this.f);
            if (c.a(strD)) {
                this.i = new d();
                this.i.b(strD);
            }
        }
        if (this.d) {
            this.g.h("firstRead");
            d dVarD = d();
            if (dVarD == null || !dVarD.c()) {
                c(this.i);
            }
            this.d = false;
        }
        return this.i != null ? this.i : new d();
    }

    public void c(d dVar) {
        h hVar = (h) this.e.get(4);
        if (hVar != null) {
            hVar.a(dVar);
        }
    }

    public String c() {
        d dVarA = a();
        if (dVarA == null || !dVarA.c()) {
            dVarA = d();
        }
        return (dVarA == null || !dVarA.c()) ? Constants.MAIN_VERSION_TAG : dVarA.e();
    }

    public d d() {
        return a(4, this.e);
    }

    private d a(int i, Map map) {
        h hVar;
        if (this.e == null || (hVar = (h) map.get(Integer.valueOf(i))) == null) {
            return null;
        }
        return hVar.g();
    }

    public void d(d dVar) {
        Log.d(Constants.LogTag, "writeMidEntity:" + dVar);
        a(dVar);
        b(dVar);
    }
}
