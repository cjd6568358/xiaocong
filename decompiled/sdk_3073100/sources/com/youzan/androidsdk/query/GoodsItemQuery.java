package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.loader.http.interfaces.NotImplementedException;
import com.youzan.androidsdk.model.goods.GoodsDetailModel;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class GoodsItemQuery extends b<GoodsDetailModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<GoodsDetailModel> getModel() {
        return GoodsDetailModel.class;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.item/1.0.0/get";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.androidsdk.loader.http.Query
    public GoodsDetailModel onParse(JSONObject data) throws JSONException, NotImplementedException {
        return new GoodsDetailModel(data.optJSONObject("item"));
    }
}
