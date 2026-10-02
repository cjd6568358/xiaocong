package com.ixiaocong.smarthome.phone.softap.timer;

import android.content.Context;
import android.text.TextUtils;
import com.ixiaocong.log.XConfigLog;
import com.ixiaocong.smarthome.phone.softap.callback.XConfigSoftApCallback;
import com.ixiaocong.smarthome.phone.softap.sdk.SoftApSDK;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XcDeviceScanner extends DeviceScanner {
    private boolean isStart;
    private String mAddr;
    private XConfigSoftApCallback mCallback;
    private String mCheckCode;
    private String mMac;
    private String mProductId;

    public XcDeviceScanner(Context context, XConfigSoftApCallback callback, String addr, String productId, String mac, String checkCode) {
        super(context);
        this.isStart = true;
        this.mCallback = callback;
        this.mProductId = productId;
        this.mMac = mac;
        this.mCheckCode = checkCode;
        if (!TextUtils.isEmpty(addr)) {
            this.mAddr = addr;
        } else {
            this.mAddr = "255.255.255.255";
        }
    }

    public void startDeviceScan() {
        if (this.isStart) {
            if (this.mTimer == null) {
                this.mTimer = new Timer();
            }
            this.mTimer.schedule(new TimerTask() { // from class: com.ixiaocong.smarthome.phone.softap.timer.XcDeviceScanner.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    try {
                        if (XcDeviceScanner.this.mTimer != null && XcDeviceScanner.this.isStart) {
                            SoftApSDK.getInstance().startCoap(XcDeviceScanner.this.mAddr, XcDeviceScanner.this.mProductId, XcDeviceScanner.this.mMac);
                            XConfigLog.w("SoftAp", "19---sendSoftAp,发送coap进行设备发现,mAddr=" + XcDeviceScanner.this.mAddr + "//mProductId=" + XcDeviceScanner.this.mProductId + "//mMac=" + XcDeviceScanner.this.mMac);
                        }
                    } catch (Error e) {
                    } catch (Throwable e2) {
                        e2.printStackTrace();
                    }
                }
            }, 0L, 2500L);
        }
    }

    @Override // com.ixiaocong.smarthome.phone.softap.timer.DeviceScanner
    public void startHttpDeviceScan() {
        if (this.isStart) {
            if (this.mHttpTimer == null) {
                this.mHttpTimer = new Timer();
            }
            this.mHttpTimer.schedule(new TimerTask() { // from class: com.ixiaocong.smarthome.phone.softap.timer.XcDeviceScanner.2
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    try {
                        if (XcDeviceScanner.this.mHttpTimer != null && XcDeviceScanner.this.isStart) {
                            XConfigLog.w("softAp", "19---sendSoftAp,通过接口查询到,mCheckCode=" + XcDeviceScanner.this.mCheckCode);
                            XcDeviceScanner.this.mCallback.httpScanDevice(XcDeviceScanner.this.mCheckCode, XcDeviceScanner.this.mProductId, XcDeviceScanner.this.mMac);
                        }
                    } catch (Error e) {
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }, 0L, 3000L);
        }
    }

    public void stopDeviceScan() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
        if (this.mHttpTimer != null) {
            this.mHttpTimer.cancel();
            this.mHttpTimer = null;
        }
        this.isStart = false;
    }
}
