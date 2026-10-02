package com.xiaocong.smarthome.sdk.openapi.interfaces;

import android.content.Context;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface XCRequestService {
    void okRequest(Context context, XCHttpSetting xCHttpSetting, XCDataCallback<XCResponseBean> xCDataCallback);

    void openRequest(Context context, XCHttpSetting xCHttpSetting, XCDataCallback<String> xCDataCallback);

    void removeRequstClient(Context context);

    void request(Context context, XCHttpSetting xCHttpSetting, XCDataCallback<XCResponseBean> xCDataCallback);
}
