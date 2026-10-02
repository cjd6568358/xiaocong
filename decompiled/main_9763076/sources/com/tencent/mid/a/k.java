package com.tencent.mid.a;

import android.content.Context;
import com.tencent.mid.api.MidCallback;
import com.tencent.mid.api.MidConstants;
import com.tencent.mid.api.MidEntity;
import com.tencent.mid.util.Util;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class k implements Runnable {
    private Context a;
    private MidCallback b;
    private int c;
    private com.tencent.mid.util.f d;

    public k(Context context, int i, MidCallback midCallback) {
        this.a = null;
        this.b = null;
        this.c = 0;
        this.d = null;
        this.a = context;
        this.c = i;
        this.b = midCallback;
        this.d = Util.getLogger();
    }

    private void a() {
        MidEntity midEntityA = com.tencent.mid.b.g.a(this.a).a(new ArrayList(Arrays.asList(2)));
        MidEntity midEntityA2 = com.tencent.mid.b.g.a(this.a).a(new ArrayList(Arrays.asList(4)));
        if (Util.equal(midEntityA2, midEntityA)) {
            this.d.d("local mid check passed.");
            return;
        }
        MidEntity newerMidEntity = Util.getNewerMidEntity(midEntityA2, midEntityA);
        this.d.d("local mid check failed, redress with mid:" + newerMidEntity.toString());
        if (com.tencent.mid.util.i.a(this.a).b("ten.mid.allowCheckAndRewriteLocal.bool", 0) == 1) {
            com.tencent.mid.b.g.a(this.a).f(newerMidEntity);
        }
    }

    private void b() {
        com.tencent.mid.b.a aVarL = com.tencent.mid.b.g.a(this.a).l();
        if (aVarL == null) {
            this.d.d("CheckEntity is null");
            return;
        }
        int iC = aVarL.c() + 1;
        long jCurrentTimeMillis = System.currentTimeMillis() - aVarL.b();
        if (jCurrentTimeMillis < 0) {
            jCurrentTimeMillis = -jCurrentTimeMillis;
        }
        this.d.b("check entity: " + aVarL.toString() + ",duration:" + jCurrentTimeMillis);
        if ((iC > aVarL.d() && jCurrentTimeMillis > a.a) || jCurrentTimeMillis > ((long) aVarL.a()) * a.a) {
            a();
            c();
            aVarL.b(iC);
            aVarL.a(System.currentTimeMillis());
            com.tencent.mid.b.g.a(this.a).a(aVarL);
        }
        MidEntity midEntityA = com.tencent.mid.b.g.a(this.a).a();
        this.d.b("midNewEntity:" + midEntityA);
        if (Util.isMidValid(midEntityA)) {
            return;
        }
        this.d.b("request mid_new ");
        d.a(this.a).a(3, new g(this.a), new l(this));
    }

    private void c() {
        this.d.b("checkServer");
        d.a(this.a).a(2, new g(this.a), new m(this));
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (k.class) {
            this.d.d("ServiceRunnable begin, type:" + this.c + ",ver:4.06");
            try {
                switch (this.c) {
                    case 1:
                        MidEntity midEntityA = h.a(this.a);
                        if (Util.isMidValid(midEntityA)) {
                            this.b.onSuccess(midEntityA);
                        } else if (!Util.isNetworkAvailable(this.a)) {
                            this.b.onFail(MidConstants.ERROR_NETWORK, "network not available.");
                        } else {
                            d.a(this.a).a(1, new g(this.a), this.b);
                        }
                        break;
                    case 2:
                        b();
                        break;
                    default:
                        this.d.d("wrong type:" + this.c);
                        break;
                }
            } catch (Throwable th) {
                this.d.f(th);
            }
            this.d.d("ServiceRunnable end");
        }
    }
}
