package com.hzy.tvmao.interf;

import com.kookong.app.data.RcTestRemoteKeyV3;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ISingleMatchResult {
    void onError();

    void onMatchedIR(String str);

    void onNextGroupKey(List<RcTestRemoteKeyV3> list);

    void onNotMatchIR();
}
