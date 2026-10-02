package com.ixiaocong.smarthome.phone.softap.timer;

import android.content.Context;
import android.text.TextUtils;
import com.ixiaocong.log.XConfigLog;
import com.ixiaocong.smarthome.phone.softap.sdk.SoftApSDK;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XcSoftApConfigTimer extends DeviceConfig {
    private String mAddr;
    private String mCheckCode;
    private String mClientId;
    private String mCrt;
    private String mDomain;
    private String mPassword;
    private String mSSID;

    public XcSoftApConfigTimer(Context context, String addr, String ssid, String password, String domain, String crt, String clientId, String checkCode) {
        super(context);
        if (!TextUtils.isEmpty(addr)) {
            this.mAddr = addr;
        } else {
            this.mAddr = "255.255.255.255";
        }
        this.mSSID = ssid;
        this.mPassword = password;
        this.mDomain = domain;
        this.mCrt = crt;
        this.mCheckCode = checkCode;
        this.mClientId = clientId;
    }

    public void startDeviceConfig() {
        if (this.timer == null) {
            this.timer = new Timer();
        }
        this.timer.schedule(new TimerTask() { // from class: com.ixiaocong.smarthome.phone.softap.timer.XcSoftApConfigTimer.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                try {
                    if (XcSoftApConfigTimer.this.timer != null) {
                        XConfigLog.w("SoftAp", "11----sendSoftAp,发送softAP进行设备入网mAddr=" + XcSoftApConfigTimer.this.mAddr + "//checkCode=" + XcSoftApConfigTimer.this.mCheckCode);
                        SoftApSDK.getInstance().startSoftAp(XcSoftApConfigTimer.this.mAddr, XcSoftApConfigTimer.this.mSSID, XcSoftApConfigTimer.this.mPassword, XcSoftApConfigTimer.this.mDomain, XcSoftApConfigTimer.this.mCrt, XcSoftApConfigTimer.this.mClientId, XcSoftApConfigTimer.this.mCheckCode);
                    }
                } catch (Error e) {
                } catch (Throwable e2) {
                    e2.printStackTrace();
                }
            }
        }, 0L, 4500L);
    }

    public void stopDeviceConfig() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
    }
}
