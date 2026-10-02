package com.huawei.hms.support.api.push;

import com.huawei.hms.api.Api;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HuaweiPush {
    public static final HuaweiPushApi HuaweiPushApi = new HuaweiPushApiImp();
    public static final Api<Api.ApiOptions.NoOptions> PUSH_API = new Api<>("HuaweiPush.API");
}
