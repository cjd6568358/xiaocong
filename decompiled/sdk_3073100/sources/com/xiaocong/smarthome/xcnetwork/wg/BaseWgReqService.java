package com.xiaocong.smarthome.xcnetwork.wg;

import com.xiaocong.smarthome.xcnetwork.NetWork;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class BaseWgReqService<T> {
    protected WGApi wgApi;

    public BaseWgReqService(String baseUrl) {
        this.wgApi = (WGApi) new NetWork.Builder(baseUrl).build().getApi(WGApi.class);
    }
}
