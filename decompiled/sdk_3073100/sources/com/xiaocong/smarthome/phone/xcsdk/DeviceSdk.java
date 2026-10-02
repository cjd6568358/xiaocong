package com.xiaocong.smarthome.phone.xcsdk;

import com.ixiaocong.log.XConfigLog;
import com.ixiaocong.xconfig.callback.XConfigCallback;
import java.util.HashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DeviceSdk {
    private static DeviceSdk mDeviceSdk;
    private HashSet<String> listeners;
    private XConfigCallback mCallback;

    public native void XConfigStart(String str, String str2, String str3, byte[] bArr);

    public native void XConfigStop();

    public native String cmdExec(int i, String str);

    public native int polling();

    public static DeviceSdk getInstance() {
        if (mDeviceSdk == null) {
            mDeviceSdk = new DeviceSdk();
        }
        return mDeviceSdk;
    }

    public void addListener(int listener) {
        if (this.listeners == null) {
            this.listeners = new HashSet<>();
        }
        this.listeners.add("");
    }

    public void removeListener(int listener) {
        if (this.listeners != null) {
            this.listeners.remove("");
        }
    }

    private void deviceEvent(int type, String value) {
        XConfigLog.e("deviceSdk---Event---", type + "---" + value);
        if (type == 0 && this.mCallback != null) {
            this.mCallback.xconfigCallback(type, value);
        }
        if (this.listeners == null) {
        }
    }

    public void setCallback(XConfigCallback mCallback) {
        this.mCallback = mCallback;
    }

    static {
        System.loadLibrary("xcsdk");
    }
}
