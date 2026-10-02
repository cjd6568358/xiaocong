package com.youzan.jsbridge.method;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Keep
public class JsMethod implements Method {

    @SerializedName("callback_id")
    public String callbackId;

    @SerializedName(PushConstants.MZ_PUSH_MESSAGE_METHOD)
    public String name;

    @SerializedName("data")
    public Map<String, Object> params;

    public String getName() {
        return this.name;
    }

    public String getCallback() {
        return this.callbackId;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public String getCallbackId() {
        return this.callbackId;
    }
}
