package com.xiaocong.smarthome.network.interfaces;

import android.content.Context;
import com.xiaocong.smarthome.network.bean.CommonHttpSetting;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface IHttpRequest {
    void cancelRequest();

    IHttpRequest downloadFile(Context context, CommonHttpSetting commonHttpSetting);

    IHttpRequest sendHttpRequest(Context context, CommonHttpSetting commonHttpSetting);

    IHttpRequest uploadFile(Context context, CommonHttpSetting commonHttpSetting);
}
