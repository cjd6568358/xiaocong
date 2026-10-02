package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.loader.http.interfaces.NotImplementedException;
import com.youzan.androidsdk.model.shop.ShopStatusModel;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class ShopStatusQuery extends b<ShopStatusModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.shop.status/1.0.0/get";
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<ShopStatusModel> getModel() {
        return ShopStatusModel.class;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.androidsdk.loader.http.Query
    public ShopStatusModel onParse(JSONObject data) throws JSONException, NotImplementedException {
        JSONObject item = data.optJSONObject("status");
        return new ShopStatusModel(item);
    }
}
