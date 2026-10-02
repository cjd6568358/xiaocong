package com.hzy.tvmao;

import com.hzy.tvmao.interf.ISingleMatchResult;
import com.hzy.tvmao.utils.LogUtil;
import com.kookong.app.data.RcTestRemoteKeyListV3;

/* JADX INFO: compiled from: KKSingleMatchManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a implements com.hzy.tvmao.b.a.c {
    final /* synthetic */ KKSingleMatchManager a;
    private final /* synthetic */ ISingleMatchResult b;

    a(KKSingleMatchManager kKSingleMatchManager, ISingleMatchResult iSingleMatchResult) {
        this.a = kKSingleMatchManager;
        this.b = iSingleMatchResult;
    }

    @Override // com.hzy.tvmao.b.a.c
    public void a(com.hzy.tvmao.b.a.a aVar) {
        if (aVar.d() && aVar.c() != null) {
            RcTestRemoteKeyListV3 rcTestRemoteKeyListV3 = (RcTestRemoteKeyListV3) aVar.c();
            LogUtil.d("group key size：" + rcTestRemoteKeyListV3.remoteKeyList.size());
            LogUtil.d("defaultRemoteId：" + rcTestRemoteKeyListV3.defaultRemoteId);
            if (rcTestRemoteKeyListV3.defaultRemoteId != 0) {
                int i = rcTestRemoteKeyListV3.defaultRemoteId;
                LogUtil.d("匹配到remoteId：" + i);
                this.b.onMatchedIR(new StringBuilder(String.valueOf(i)).toString());
                return;
            } else if (rcTestRemoteKeyListV3.remoteKeyList.size() == 0) {
                LogUtil.d("没有匹配到对应的遥控器");
                this.b.onNotMatchIR();
                return;
            } else {
                this.b.onNextGroupKey(rcTestRemoteKeyListV3.remoteKeyList);
                return;
            }
        }
        LogUtil.d("接口错误：" + aVar.b());
        this.b.onError();
    }
}
