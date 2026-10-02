package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.model.trade.TradeCartFormatModel;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class TradeCartListQuery extends b<TradeCartFormatModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<TradeCartFormatModel> getModel() {
        return TradeCartFormatModel.class;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.trade.cart/1.0.0/list";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.androidsdk.loader.http.Query
    public TradeCartFormatModel onParse(JSONObject data) throws Exception {
        JSONArray array = data.getJSONArray("data");
        return (array == null || array.length() <= 0) ? new TradeCartFormatModel(null) : new TradeCartFormatModel(array.getJSONObject(0));
    }
}
