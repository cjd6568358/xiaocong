package com.tencent.android.tpush.horse;

import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.OptStrategyList;
import com.tencent.android.tpush.horse.data.StrategyItem;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.exception.HorseIgnoreException;
import com.tencent.android.tpush.service.channel.exception.NullReturnException;
import java.util.ArrayList;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class h implements Runnable {
    final /* synthetic */ g a;

    h(g gVar) {
        this.a = gVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this) {
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "Action ->  createOptimalSocketChannel run");
            }
            if (XGPushConfig.isForeiginPush(com.tencent.android.tpush.service.n.f())) {
                try {
                    com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "Using the isForeiginPush");
                    n nVar = new n();
                    nVar.a((StrategyItem) null);
                    if (nVar.a().isConnected()) {
                        if (this.a.h != null) {
                            this.a.h.a(nVar.a(), nVar.a);
                        }
                    } else if (this.a.h != null) {
                        this.a.h.a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create foreigin tcp channel fail!");
                    }
                } catch (Throwable th) {
                    com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "createOptimalSocketChannel isForeiginPush error", th);
                    if (this.a.h != null) {
                        this.a.h.a(Constants.CODE_NETWORK_CREATE_OPTIOMAL_SC_FAILED, "create foreigin tcp channel fail!");
                    }
                }
                return;
            }
            if (!q.i().b() && !f.i().b()) {
                try {
                    String strM = com.tencent.android.tpush.service.e.m.m(com.tencent.android.tpush.service.n.f());
                    OptStrategyList optStrategyList = CacheManager.getOptStrategyList(com.tencent.android.tpush.service.n.f(), strM);
                    StrategyItem strategyItemE = optStrategyList.e();
                    if (strategyItemE.d() == 1 || strategyItemE == null || e.a(optStrategyList.g())) {
                        this.a.a(strM);
                        return;
                    }
                    this.a.g = System.currentTimeMillis();
                    if (strategyItemE.d() == 0) {
                        if (XGPushConfig.enableDebug) {
                            com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "Using the optStrategyItem" + strategyItemE.toString());
                        }
                        this.a.f = true;
                        ArrayList arrayList = new ArrayList();
                        strategyItemE.a(0);
                        arrayList.add(strategyItemE);
                        q.i().a(this.a.p);
                        q.i().a(arrayList);
                        q.i().g();
                    } else {
                        if (XGPushConfig.enableDebug) {
                            com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "Using Http chanel http:" + strategyItemE.toString());
                        }
                        n nVar2 = new n();
                        nVar2.a(strategyItemE);
                        if (nVar2.a().isConnected() && this.a.h != null) {
                            this.a.h.a(nVar2.a(), strategyItemE);
                            return;
                        }
                    }
                } catch (HorseIgnoreException e) {
                    com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "createOptimalSocketChannel error", e);
                    this.a.b();
                } catch (NullReturnException e2) {
                    com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "createOptimalSocketChannel error", e2);
                    this.a.a(Constants.MAIN_VERSION_TAG);
                } catch (Exception e3) {
                    com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "createOptimalSocketChannel error", e3);
                    this.a.b();
                }
            } else {
                com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, ">> horse task running");
            }
            return;
            throw th;
        }
    }
}
