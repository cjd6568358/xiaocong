package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.model.trade.TradePaidModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class TradePaidQuery extends b<TradePaidModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<TradePaidModel> getModel() {
        return TradePaidModel.class;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.trade.payresult/3.0.0/get";
    }
}
