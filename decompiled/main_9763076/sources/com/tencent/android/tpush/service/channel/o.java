package com.tencent.android.tpush.service.channel;

import com.tencent.android.tpush.service.channel.exception.ChannelException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class o implements Runnable {
    final /* synthetic */ b a;
    private com.tencent.android.tpush.service.channel.a.a b;
    private ChannelException c;
    private boolean d;

    public o(b bVar, com.tencent.android.tpush.service.channel.a.a aVar, ChannelException channelException, boolean z) {
        this.a = bVar;
        this.b = null;
        this.c = null;
        this.d = false;
        this.b = aVar;
        this.c = channelException;
        this.d = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.b == null) {
            com.tencent.android.tpush.a.a.i("TpnsChannel", "@@RequestFailRunnable currentClient == null");
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        a aVarF = this.b.f();
        int i = this.c.errorCode;
        boolean z = this.d && (i == 10109 || i == 10108);
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.a.v.get(this.b);
        if (concurrentHashMap != null) {
            Iterator it = concurrentHashMap.entrySet().iterator();
            while (it.hasNext()) {
                s sVar = (s) ((Map.Entry) it.next()).getValue();
                t tVar = sVar.f;
                if (tVar != null) {
                    if (z && sVar.a < 5) {
                        if (sVar.b()) {
                            this.a.a(sVar);
                        }
                    } else {
                        aVarF.a(3, Long.valueOf(jCurrentTimeMillis - sVar.c));
                        this.a.t.removeCallbacks((q) this.a.w.remove(sVar));
                        tVar.a(sVar.e, this.c, aVarF);
                    }
                }
            }
            concurrentHashMap.clear();
        }
        if (!this.b.e()) {
            ArrayList arrayList = new ArrayList();
            synchronized (this.a) {
                for (s sVar2 : this.a.u) {
                    t tVar2 = sVar2.f;
                    if (tVar2 != null) {
                        if (z && sVar2.a < 5) {
                            sVar2.a++;
                            System.err.println("++++ tpnsMessages message.retryTime " + sVar2);
                        } else {
                            aVarF.a(3, Long.valueOf(jCurrentTimeMillis - sVar2.c));
                            this.a.t.removeCallbacks((q) this.a.w.get(sVar2));
                            tVar2.a(sVar2.e, this.c, aVarF);
                            arrayList.add(sVar2);
                        }
                    }
                }
                if (z) {
                    this.a.u.removeAll(arrayList);
                    System.err.println("+++ tpnsMessages size = " + this.a.u.size());
                } else {
                    this.a.u.clear();
                }
            }
        }
        b.a = 0;
        if (b.n > b.l) {
            b.n = (b.n / 10) * 9;
        } else {
            b.n = b.l;
        }
        this.a.c();
        if (!this.a.u.isEmpty()) {
            this.a.e();
        }
        if (this.d) {
            this.a.f();
        }
    }
}
