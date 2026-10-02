package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.model.shop.ShopMultiStoreSettingModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class ShopMultipleStoreSettingQuery extends b<ShopMultiStoreSettingModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<ShopMultiStoreSettingModel> getModel() {
        return ShopMultiStoreSettingModel.class;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "youzan.multistore.setting/3.0.0/getbykdtid";
    }
}
