package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.model.shop.ShopBasicModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class ShopBasicQuery extends b<ShopBasicModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<ShopBasicModel> getModel() {
        return ShopBasicModel.class;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.shop.basic/1.0.0/get";
    }
}
