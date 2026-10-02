package com.tencent.android.tpush.horse;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.ServerItem;
import com.tencent.android.tpush.horse.data.StrategyItem;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.exception.NullReturnException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class p {
    private static StrategyItem a(String str, int i, int i2) {
        if (str == null || i == 0) {
            return null;
        }
        return new StrategyItem(str, i, Constants.MAIN_VERSION_TAG, 80, i2, 0);
    }

    private static List a(List list, short s, String str) throws NullReturnException {
        StrategyItem strategyItem;
        if (list == null) {
            throw new NullReturnException("getStrategyItems return null, because [items] is null");
        }
        ArrayList arrayList = new ArrayList();
        StrategyItem strategyItemE = null;
        try {
            strategyItemE = CacheManager.getOptStrategyList(com.tencent.android.tpush.service.n.f(), str).e();
            strategyItemE.a(0);
            if (strategyItemE.d() == s) {
                arrayList.add(strategyItemE);
            }
            strategyItem = strategyItemE;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "getStrategyItems is null");
            strategyItem = strategyItemE;
        }
        for (int i = 0; i < list.size(); i++) {
            StrategyItem strategyItemA = a(((ServerItem) list.get(i)).a(), ((ServerItem) list.get(i)).b(), s);
            if (strategyItemA != null && !strategyItemA.equals(strategyItem)) {
                arrayList.add(strategyItemA);
            }
        }
        return arrayList;
    }

    public static List a(List list, String str) {
        return a(list, (short) 0, str);
    }

    public static List b(List list, String str) {
        return a(list, (short) 1, str);
    }
}
