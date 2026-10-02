package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.loader.http.interfaces.NotImplementedException;
import com.youzan.androidsdk.model.reviews.ReviewsModel;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class GoodsReviewsQuery extends b<ReviewsModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.item.reviews/1.0.0/queryreview";
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<ReviewsModel> getModel() {
        return ReviewsModel.class;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.androidsdk.loader.http.Query
    public ReviewsModel onParse(JSONObject data) throws JSONException, NotImplementedException {
        JSONObject body = data.optJSONObject("itemReviewsModels");
        if (body != null) {
            return new ReviewsModel(body.optJSONObject("data"));
        }
        throw new IllegalArgumentException("Unsupported json structures");
    }
}
