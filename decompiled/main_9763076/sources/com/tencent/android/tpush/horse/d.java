package com.tencent.android.tpush.horse;

import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.StrategyItem;
import com.tencent.android.tpush.service.cache.CacheManager;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d implements o {
    final /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
    }

    @Override // com.tencent.android.tpush.horse.o
    public void a(StrategyItem strategyItem) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.a("BaseTask", "Horse run onSuccess(" + strategyItem + "," + this.a.d + "," + this.a.b.f + ")");
        }
        synchronized (a.a) {
            this.a.b.b.clear();
            if (!this.a.b.f || strategyItem.j()) {
                this.a.b.f = true;
                if (strategyItem.d() != 0 || strategyItem.f() != 1) {
                    this.a.b.a(this.a.d);
                }
                if (strategyItem.d() == 0) {
                    this.a.b.e();
                    this.a.b.f();
                }
                CacheManager.addOptStrategy(strategyItem);
                this.a.b.a();
                if (this.a.b.d != null) {
                    this.a.b.d.a(this.a.a().a(), strategyItem);
                }
            }
        }
    }

    @Override // com.tencent.android.tpush.horse.o
    public void a(StrategyItem strategyItem, StrategyItem strategyItem2) {
        com.tencent.android.tpush.a.a.a("BaseTask", "Horse run onRedirect(org:" + strategyItem + ",redirect:" + strategyItem2 + ")");
        synchronized (a.a) {
            this.a.b.b.clear();
            if (!this.a.b.f || strategyItem.j()) {
                this.a.b.f = true;
                this.a.b.a(this.a.d);
                if (strategyItem.d() == 0) {
                    this.a.b.e();
                    this.a.b.f();
                }
                CacheManager.addOptStrategy(strategyItem);
                if (strategyItem.equals(strategyItem2)) {
                    this.a.b.a();
                    if (this.a.b.d != null) {
                        this.a.b.d.a(this.a.a().a(), strategyItem);
                        return;
                    }
                    return;
                }
                if (strategyItem.f() == 0) {
                    this.a.b.a();
                    if (strategyItem2.g()) {
                        this.a.b.b.add(strategyItem2);
                    }
                    this.a.b.d.a(this.a.a().a(), strategyItem);
                    return;
                }
                this.a.b.a();
                this.a.b.d.a(this.a.a().a(), strategyItem);
                return;
            }
            com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, ">> hasSuccessCallback && !strategyItem.isRedirected()");
        }
    }

    @Override // com.tencent.android.tpush.horse.o
    public void b(StrategyItem strategyItem) {
        com.tencent.android.tpush.a.a.i("BaseTask", "Horse onFail(" + strategyItem + ")");
        if (strategyItem.f() == 1) {
            if (!this.a.b.f) {
                this.a.b.e.decrementAndGet();
                if (this.a.b.d != null && !this.a.b.b()) {
                    this.a.b.d.a(strategyItem);
                    return;
                }
                return;
            }
            return;
        }
        this.a.b.e.decrementAndGet();
        if (this.a.b.d != null && !this.a.b.b()) {
            this.a.b.d.a(strategyItem);
        }
    }
}
