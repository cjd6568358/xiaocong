package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.loader.http.interfaces.NotImplementedException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class TradeCartCountQuery extends b<Integer> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.androidsdk.loader.http.Query
    public Integer onParse(JSONObject data) throws JSONException, NotImplementedException {
        return Integer.valueOf(data.optInt("data", 0));
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.trade.cart/1.0.0/count";
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<Integer> getModel() {
        return Integer.class;
    }
}
