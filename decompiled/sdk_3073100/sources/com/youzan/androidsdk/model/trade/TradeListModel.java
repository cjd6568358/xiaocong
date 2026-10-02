package com.youzan.androidsdk.model.trade;

import com.youzan.androidsdk.SDKUtil;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TradeListModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private int f455;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private List<TradeDetailModel> f456;

    public TradeListModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f455 = o.optInt("total_results");
            this.f456 = SDKUtil.jsonToList(o, "trades", TradeDetailModel.class);
        }
    }

    public int getTotalResults() {
        return this.f455;
    }

    public List<TradeDetailModel> getTrades() {
        return this.f456;
    }
}
