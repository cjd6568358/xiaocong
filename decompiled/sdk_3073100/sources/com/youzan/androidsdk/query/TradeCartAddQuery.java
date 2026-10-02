package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.loader.http.interfaces.NotImplementedException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class TradeCartAddQuery extends b<Boolean> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.androidsdk.loader.http.Query
    public Boolean onParse(JSONObject data) throws JSONException, NotImplementedException {
        return Boolean.valueOf(data.optBoolean("is_success", false));
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.trade.cart/1.0.0/add";
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<Boolean> getModel() {
        return Boolean.class;
    }
}
