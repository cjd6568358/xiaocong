package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.loader.http.interfaces.NotImplementedException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class UploadTokenQuery extends b<String> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.androidsdk.loader.http.Query
    public String onParse(JSONObject data) throws JSONException, NotImplementedException {
        return data.optString("upload_token");
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<String> getModel() {
        return String.class;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.kdt.picture.uploadtoken/1.0.0/get";
    }
}
