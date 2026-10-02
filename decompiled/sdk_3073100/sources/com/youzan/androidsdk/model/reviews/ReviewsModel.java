package com.youzan.androidsdk.model.reviews;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ReviewsModel {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private PaginatorModel f255;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private List<ReviewItemModel> f256;

    public ReviewsModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f255 = new PaginatorModel(o.optJSONObject("paginator"));
            JSONArray arrayObj = o.optJSONArray("items");
            if (arrayObj != null && arrayObj.length() > 0) {
                int length = arrayObj.length();
                this.f256 = new ArrayList(length);
                for (int i = 0; i < length; i++) {
                    this.f256.add(new ReviewItemModel(arrayObj.optJSONObject(i)));
                }
            }
        }
    }

    public List<ReviewItemModel> getItems() {
        return this.f256;
    }

    public PaginatorModel getPaginator() {
        return this.f255;
    }
}
