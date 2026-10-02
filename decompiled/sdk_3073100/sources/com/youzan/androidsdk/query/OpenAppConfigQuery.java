package com.youzan.androidsdk.query;

import com.youzan.androidsdk.loader.http.b;
import com.youzan.androidsdk.model.config.OpenAppConfigModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class OpenAppConfigQuery extends b<OpenAppConfigModel> {
    @Override // com.youzan.androidsdk.loader.http.Query
    protected Class<OpenAppConfigModel> getModel() {
        return OpenAppConfigModel.class;
    }

    @Override // com.youzan.androidsdk.loader.http.Query
    protected String attachTo() {
        return "appsdk.open.appconfig/3.0.0/get";
    }
}
