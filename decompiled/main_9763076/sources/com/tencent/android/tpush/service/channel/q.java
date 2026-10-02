package com.tencent.android.tpush.service.channel;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class q implements Runnable {
    final /* synthetic */ b a;

    private q(b bVar) {
        this.a = bVar;
    }

    /* synthetic */ q(b bVar, c cVar) {
        this(bVar);
    }

    @Override // java.lang.Runnable
    public void run() {
        a aVar;
        boolean z;
        long j;
        boolean z2;
        long j2;
        boolean z3;
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j3 = Long.MAX_VALUE;
            long j4 = com.tencent.android.tpush.service.a.a.a(com.tencent.android.tpush.service.n.f()).f;
            boolean z4 = false;
            long j5 = j4 < 15000 ? 15000L : j4;
            ChannelException channelException = new ChannelException(Constants.CODE_NETWORK_TIMEOUT_WAITING_FOR_RESPONSE, "TpnsMessage wait for response timeout!");
            for (com.tencent.android.tpush.service.channel.a.a aVar2 : this.a.v.keySet()) {
                ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.a.v.get(aVar2);
                if (concurrentHashMap == null || concurrentHashMap.size() == 0) {
                    z2 = z4;
                    j2 = j3;
                } else {
                    Iterator it = concurrentHashMap.entrySet().iterator();
                    a aVarF = aVar2.f();
                    z2 = z4;
                    j2 = j3;
                    while (it.hasNext()) {
                        s sVar = (s) ((Map.Entry) it.next()).getValue();
                        if (sVar != null) {
                            long j6 = jCurrentTimeMillis - sVar.c;
                            aVarF.a(3, Long.valueOf(j6));
                            if (j6 >= 0) {
                                if (j6 > j5) {
                                    t tVar = sVar.f;
                                    if (tVar != null) {
                                        tVar.a(sVar.e, channelException, aVarF);
                                        sVar.f = null;
                                    }
                                    it.remove();
                                    z3 = true;
                                } else if (j5 - j6 < j2) {
                                    j2 = j5 - j6;
                                    z3 = z2;
                                }
                                z2 = z3;
                            }
                        } else {
                            it.remove();
                        }
                        z3 = z2;
                        z2 = z3;
                    }
                }
                j3 = j2;
                z4 = z2;
            }
            ChannelException channelException2 = new ChannelException(Constants.CODE_NETWORK_TIMEOUT_WAITING_TO_SEND, "TpnsMessage wait for response timeout!");
            a aVar3 = null;
            synchronized (this.a) {
                Iterator it2 = this.a.u.iterator();
                while (it2.hasNext()) {
                    s sVar2 = (s) it2.next();
                    if (sVar2 != null) {
                        long j7 = jCurrentTimeMillis - sVar2.b;
                        if (j7 >= 0) {
                            if (j7 > j5) {
                                t tVar2 = sVar2.f;
                                if (tVar2 != null) {
                                    if (aVar3 == null) {
                                        if (this.a.x != null) {
                                            aVar3 = this.a.x.f();
                                        } else {
                                            aVar3 = new a();
                                        }
                                        aVar3.a(3, Long.valueOf(j7));
                                    }
                                    tVar2.a(sVar2.e, channelException2, aVar3);
                                    sVar2.f = null;
                                }
                                aVar = aVar3;
                                it2.remove();
                                z = true;
                                j = j3;
                            } else if (j5 - j7 < j3) {
                                aVar = aVar3;
                                z = z4;
                                j = j5 - j7;
                            }
                            j3 = j;
                            z4 = z;
                            aVar3 = aVar;
                        }
                    } else {
                        it2.remove();
                    }
                    aVar = aVar3;
                    z = z4;
                    j = j3;
                    j3 = j;
                    z4 = z;
                    aVar3 = aVar;
                }
            }
            if (z4) {
                this.a.d();
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("TpnsChannel", "TimeoutRunnable.run", e);
        }
    }
}
