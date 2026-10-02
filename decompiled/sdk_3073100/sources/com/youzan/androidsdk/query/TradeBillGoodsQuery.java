package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.model.trade.TradeBillModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class TradeBillGoodsQuery extends b<TradeBillModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<TradeBillModel> getModel() {
        return TradeBillModel.class;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.trade.bill.goods.url/1.0.0/get";
    }
}
