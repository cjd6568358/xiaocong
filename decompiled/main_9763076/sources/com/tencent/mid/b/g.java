package com.tencent.mid.b;

import android.content.Context;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mid.api.MidEntity;
import com.tencent.mid.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static g h = null;
    private Map<Integer, f> e;
    private Context f;
    private com.tencent.mid.util.f g = Util.getLogger();
    Map<Integer, f> a = null;
    MidEntity b = null;
    Map<Integer, f> c = null;
    private MidEntity i = null;
    boolean d = true;

    private g(Context context) {
        this.e = null;
        this.f = null;
        this.f = context.getApplicationContext();
        this.e = new HashMap(3);
        this.e.put(1, new e(context, 3));
        this.e.put(2, new c(context, 3));
        this.e.put(4, new d(context, 3));
    }

    private MidEntity a(int i, Map<Integer, f> map) {
        f fVar;
        if (this.e == null || (fVar = map.get(Integer.valueOf(i))) == null) {
            return null;
        }
        return fVar.i();
    }

    public static synchronized g a(Context context) {
        if (h == null) {
            h = new g(context);
        }
        return h;
    }

    private Map<Integer, f> m() {
        if (this.a == null) {
            this.a = new HashMap(3);
            this.a.put(1, new e(this.f, 1000001));
            this.a.put(2, new c(this.f, 1000001));
            this.a.put(4, new d(this.f, 1000001));
        }
        return this.a;
    }

    private Map<Integer, f> n() {
        if (this.c == null) {
            this.c = new HashMap(3);
            this.c.put(1, new e(this.f, 0));
            this.c.put(2, new c(this.f, 0));
            this.c.put(4, new d(this.f, 0));
        }
        return this.c;
    }

    public MidEntity a() {
        m();
        if (!Util.isMidValid(this.b)) {
            this.b = a(new ArrayList(Arrays.asList(4, 1, 2)), this.a);
        }
        this.g.h("readNewVersionMidEntity:" + this.b);
        return this.b;
    }

    public MidEntity a(List<Integer> list) {
        return a(list, this.e);
    }

    public MidEntity a(List<Integer> list, Map<Integer, f> map) {
        MidEntity midEntityI;
        if (list == null || list.size() == 0 || map == null || map.size() == 0) {
            return null;
        }
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            f fVar = map.get(it.next());
            if (fVar != null && (midEntityI = fVar.i()) != null && midEntityI.isMidValid()) {
                return midEntityI;
            }
        }
        return null;
    }

    public void a(int i, int i2) {
        a aVarL = l();
        if (i > 0) {
            aVarL.c(i);
        }
        if (i2 > 0) {
            aVarL.a(i2);
        }
        aVarL.a(System.currentTimeMillis());
        aVarL.b(0);
        a(aVarL);
    }

    public void a(MidEntity midEntity) {
        a(midEntity, true);
    }

    public void a(MidEntity midEntity, boolean z) {
        if (midEntity.getTimestamps() <= 0) {
            midEntity.setTimestamps(System.currentTimeMillis());
        }
        this.g.h("writeNewVersionMidEntity midEntity:" + midEntity);
        Iterator<Map.Entry<Integer, f>> it = m().entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().a(midEntity);
        }
        if (!z || this.f == null) {
            return;
        }
        Util.insertMid2Provider(this.f, this.f.getPackageName(), midEntity.toString());
    }

    public void a(a aVar) {
        if (aVar.b() <= 0) {
            aVar.a(System.currentTimeMillis());
        }
        Iterator<Map.Entry<Integer, f>> it = this.e.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().b(aVar);
        }
    }

    public a b(List<Integer> list) {
        a aVarJ;
        if (list == null || list.size() == 0) {
            return null;
        }
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            f fVar = this.e.get(it.next());
            if (fVar != null && (aVarJ = fVar.j()) != null) {
                return aVarJ;
            }
        }
        return null;
    }

    public String b() {
        a();
        return Util.isMidValid(this.b) ? this.b.getMid() : Constants.MAIN_VERSION_TAG;
    }

    public void b(MidEntity midEntity) {
        m();
        f fVar = this.a.get(4);
        if (fVar != null) {
            fVar.a(midEntity);
        }
    }

    public void b(MidEntity midEntity, boolean z) {
        if (midEntity.getTimestamps() <= 0) {
            midEntity.setTimestamps(System.currentTimeMillis());
        }
        Iterator<Map.Entry<Integer, f>> it = this.e.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().a(midEntity);
        }
        if (!z || this.f == null) {
            return;
        }
        Util.insertMid2OldProvider(this.f, this.f.getPackageName(), midEntity.toString());
    }

    public MidEntity c() {
        return a(4, m());
    }

    public void c(MidEntity midEntity) {
        m();
        f fVar = this.a.get(1);
        if (fVar != null) {
            fVar.a(midEntity);
        }
        f fVar2 = this.a.get(2);
        if (fVar2 != null) {
            fVar2.a(midEntity);
        }
    }

    public MidEntity d() {
        return a(1, m());
    }

    public void d(MidEntity midEntity) {
        f fVar = this.e.get(4);
        if (fVar != null) {
            fVar.a(midEntity);
        }
    }

    public MidEntity e() {
        return a(2, m());
    }

    public void e(MidEntity midEntity) {
        f fVar = this.e.get(1);
        if (fVar != null) {
            fVar.a(midEntity);
        }
        f fVar2 = this.e.get(2);
        if (fVar2 != null) {
            fVar2.a(midEntity);
        }
    }

    public String f() {
        try {
            h();
            if (this.i != null) {
                return this.i.getMid();
            }
        } catch (Throwable th) {
            this.g.f("readMidString " + th);
        }
        return PushConstants.PUSH_TYPE_NOTIFY;
    }

    public void f(MidEntity midEntity) {
        if (midEntity.getTimestamps() <= 0) {
            midEntity.setTimestamps(System.currentTimeMillis());
        }
        Iterator<Map.Entry<Integer, f>> it = this.e.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().a(midEntity);
        }
    }

    public long g() {
        try {
            h();
            if (this.i != null) {
                return this.i.getGuid();
            }
        } catch (Throwable th) {
            this.g.f("readMidString " + th);
        }
        return 0L;
    }

    public void g(MidEntity midEntity) {
        if (midEntity.getTimestamps() <= 0) {
            midEntity.setTimestamps(System.currentTimeMillis());
        }
        Iterator<Map.Entry<Integer, f>> it = n().entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().a(midEntity);
        }
    }

    public MidEntity h() {
        if (!Util.isMidValid(this.i)) {
            this.g.h("read the new one");
            this.i = a(new ArrayList(Arrays.asList(4)), this.e);
        }
        if (!Util.isMidValid(this.i)) {
            this.g.h("load from the old one");
            MidEntity midEntityA = a(new ArrayList(Arrays.asList(4)), n());
            if (Util.isMidValid(midEntityA)) {
                this.g.d("copy old mid:" + midEntityA.getMid() + " to new version.");
                this.i = midEntityA;
                f(this.i);
            }
        }
        if (!Util.isMidValid(this.i)) {
            this.g.h("mid query other app");
            Map<String, MidEntity> midsByApps = Util.getMidsByApps(this.f, 2);
            if (midsByApps != null && midsByApps.size() > 0) {
                Iterator<Map.Entry<String, MidEntity>> it = midsByApps.entrySet().iterator();
                while (it.hasNext()) {
                    MidEntity value = it.next().getValue();
                    if (value != null && value.isMidValid()) {
                        this.i = value;
                        break;
                    }
                }
            }
        }
        if (!Util.isMidValid(this.i)) {
            this.g.h("read the new one");
            this.i = a(new ArrayList(Arrays.asList(4, 1, 2)), this.e);
        }
        if (!Util.isMidValid(this.i)) {
            this.g.h("load from the old one");
            MidEntity midEntityA2 = a(new ArrayList(Arrays.asList(1, 2, 4)), n());
            if (Util.isMidValid(midEntityA2)) {
                this.g.d("copy old mid:" + midEntityA2.getMid() + " to new version.");
                this.i = midEntityA2;
                f(this.i);
            }
        }
        if (this.d) {
            this.g.h("firstRead");
            MidEntity midEntityI = i();
            if (midEntityI == null || !midEntityI.isMidValid()) {
                d(this.i);
            }
            this.d = false;
        }
        return this.i != null ? this.i : new MidEntity();
    }

    public MidEntity i() {
        return a(4, this.e);
    }

    public MidEntity j() {
        return a(1, this.e);
    }

    public MidEntity k() {
        return a(2, this.e);
    }

    public a l() {
        return b(new ArrayList(Arrays.asList(1, 4)));
    }
}
