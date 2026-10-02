package com.ixiaocong.smarthome.phone.softap.sdk;

import android.text.TextUtils;
import com.ixiaocong.log.XConfigLog;
import com.ixiaocong.smarthome.phone.softap.callback.SoftApCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoftApSDK {
    private SoftApCallback mCallback;

    public native void startCoap(String str, String str2, String str3);

    public native void startSoftAp(String str, String str2, String str3, String str4, String str5, String str6, String str7);

    static {
        System.loadLibrary("softAp");
    }

    public static SoftApSDK getInstance() {
        return SoftApSDKHolder.INSTANCE;
    }

    public void softApEvent(String mac, String productId) {
        if (!TextUtils.isEmpty(mac) && !TextUtils.isEmpty(productId)) {
            this.mCallback.softApConfigCallback(mac, productId);
            XConfigLog.w("SoftAp", "softApEvent,mac=" + mac + "//productId=" + productId);
        }
    }

    public void coapEvent(int type, String value) {
        this.mCallback.coapCallback(type, value);
        XConfigLog.w("SoftAp", "21---coapEvent,type=" + type + "//value=" + value);
    }

    public void setCallback(SoftApCallback mCallback) {
        this.mCallback = mCallback;
    }

    private static final class SoftApSDKHolder {
        private static final SoftApSDK INSTANCE = new SoftApSDK();
    }
}
