package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.model.ump.PromotionModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class GoodsPromotionQuery extends b<PromotionModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<PromotionModel> getModel() {
        return PromotionModel.class;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.ump.promotion/1.0.0/get";
    }
}
