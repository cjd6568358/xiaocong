package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.Query;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class UploadQiniuQuery extends Query<String> {
    @Override // com.youzan.androidsdk.loader.http.Query
    public int getAuthType() {
        return 0;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<String> getModel() {
        return String.class;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.youzan.androidsdk.loader.http.Query
    public String onParse(JSONObject data) throws Exception {
        return data.optString("attachment_url");
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    public String attachTo() {
        return "https://up.qbox.me/";
    }
}
