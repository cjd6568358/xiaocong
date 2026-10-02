package com.youzan.androidsdk.model.trade;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeCartListModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private List<TradeCartShopModel> f336;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private boolean f337;

    public TradeCartListModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f337 = o.optBoolean("is_success");
            JSONArray array = o.optJSONArray("data");
            if (array != null && array.length() > 0) {
                this.f336 = new ArrayList(array.length());
                for (int i = 0; i < array.length(); i++) {
                    this.f336.add(new TradeCartShopModel(array.optJSONObject(i)));
                }
            }
        }
    }

    public List<TradeCartShopModel> getData() {
        return this.f336;
    }

    public boolean isSuccess() {
        return this.f337;
    }
}
