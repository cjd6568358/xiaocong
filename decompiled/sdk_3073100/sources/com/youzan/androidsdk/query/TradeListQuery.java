package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.model.trade.TradeListModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class TradeListQuery extends b<TradeListModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<TradeListModel> getModel() {
        return TradeListModel.class;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "kdt.trade.buyer.search/1.0.0/get";
    }
}
