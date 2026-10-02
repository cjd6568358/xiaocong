package com.tencent.mm.opensdk.openapi;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface IWXAPIEventHandler {
    void onReq(BaseReq baseReq);

    void onResp(BaseResp baseResp);
}
