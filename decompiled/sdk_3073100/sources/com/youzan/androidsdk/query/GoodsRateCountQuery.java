package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.model.reviews.ReviewsRateModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class GoodsRateCountQuery extends b<ReviewsRateModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.item.reviews/1.0.0/countgoodsrate";
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<ReviewsRateModel> getModel() {
        return ReviewsRateModel.class;
    }
}
