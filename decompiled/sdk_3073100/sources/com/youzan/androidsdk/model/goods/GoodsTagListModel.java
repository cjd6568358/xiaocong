package com.youzan.androidsdk.model.goods;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class GoodsTagListModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private List<GoodsTagModel> f217;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private int f218;

    public GoodsTagListModel(JSONObject o) throws JSONException {
        if (o != null) {
            JSONArray itemsArray = o.optJSONArray("tags");
            if (itemsArray != null && itemsArray.length() > 0) {
                this.f217 = new ArrayList(itemsArray.length());
                for (int i = 0; i < itemsArray.length(); i++) {
                    this.f217.add(new GoodsTagModel(itemsArray.optJSONObject(i)));
                }
            }
            this.f218 = o.optInt("total_results");
        }
    }

    public List<GoodsTagModel> getItems() {
        return this.f217;
    }

    public void setItems(List<GoodsTagModel> items) {
        this.f217 = items;
    }

    public int getTotalResults() {
        return this.f218;
    }

    public void setTotalResults(int totalResults) {
        this.f218 = totalResults;
    }
}
