package com.ixiaocong.smarthome.phone.softap.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.ixiaocong.log.XConfigLog;
import com.ixiaocong.smarthome.phone.softap.callback.SoftApCallback;
import com.ixiaocong.smarthome.phone.softap.callback.XConfigSoftApCallback;
import com.ixiaocong.smarthome.phone.softap.link.XcLinkNetwork;
import com.ixiaocong.smarthome.phone.softap.sdk.SoftApSDK;
import com.ixiaocong.smarthome.phone.softap.timer.XcDeviceScanner;
import com.ixiaocong.smarthome.phone.softap.timer.XcSoftApConfigTimer;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mid.api.MidEntity;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoftApManager implements SoftApCallback {
    private XcDeviceScanner mCoapScanner;
    private Context mContext;
    private String mDeviceId;
    private String mMac;
    private String mProductId;
    private XcSoftApConfigTimer mSoftApConfig;
    private XConfigSoftApCallback mXConfigCallback;
    private boolean isSendAp = false;
    private int mReconnectNum = 0;

    public static SoftApManager getInstance() {
        return SoftApManagerHolder.INSTANCE;
    }

    public void startSoftAp(Context context, String productId, String deviceId, XConfigSoftApCallback configCallback) {
        this.mProductId = productId;
        this.mDeviceId = deviceId;
        this.mXConfigCallback = configCallback;
        this.mContext = context;
        SoftApSDK.getInstance().setCallback(this);
    }

    public void sendSoftApTimer(String broadAddress, String ssid, String password, String domain, String crt, String clientId, String checkCode) {
        if (!TextUtils.isEmpty(ssid) && !TextUtils.isEmpty(password)) {
            if (password.length() >= 8) {
                if (this.mSoftApConfig == null) {
                    XConfigLog.w("SoftAp", "10---sendSoftAp,发送配置信息给当前ap,ssid=" + ssid + "//password=" + password);
                    this.mSoftApConfig = new XcSoftApConfigTimer(this.mContext, broadAddress, ssid, password, domain, crt, clientId, checkCode);
                    this.mSoftApConfig.startDeviceConfig();
                    this.isSendAp = true;
                    return;
                }
                return;
            }
            Toast.makeText(this.mContext, "Wi-Fi密码长度不能小于8位", 0).show();
            return;
        }
        Toast.makeText(this.mContext, "Wi-Fi名称和密码不能为空", 0).show();
    }

    public void startCoapTimer(String broadAddress) {
        if (this.mCoapScanner == null) {
            XConfigLog.w("SoftAp", "18---启动coap发现设备");
            this.mCoapScanner = new XcDeviceScanner(this.mContext, this.mXConfigCallback, broadAddress, getProductId(), getMac(), SoftApStage.getInstance().getCheckCode());
            this.mCoapScanner.startDeviceScan();
            this.mCoapScanner.startHttpDeviceScan();
        }
    }

    public void reconnectNetAp() {
        this.mReconnectNum++;
        if (this.mXConfigCallback != null && !this.isSendAp) {
            if (this.mReconnectNum < 4) {
                XConfigLog.e("SoftApSDK", "error --- 重新尝试连接ap网络//");
                this.mXConfigCallback.xconfigErrorCallback(HttpStatus.SC_NOT_FOUND, "重新尝试连接ap网络");
            } else {
                XConfigLog.e("SoftApSDK", "error --- 连接失败,取消本次添加");
                this.mXConfigCallback.xconfigErrorCallback(HttpStatus.SC_METHOD_NOT_ALLOWED, "连接失败,取消本次添加");
            }
        }
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.SoftApCallback
    public void softApConfigCallback(String mac, String productId) {
        XConfigLog.w("SoftAp", "12---startSoftAp,发现配网的设备,mac=" + mac + "//productId=" + productId);
        if (!TextUtils.isEmpty(this.mProductId)) {
            if (productId.equals(PushConstants.PUSH_TYPE_NOTIFY)) {
                XConfigLog.e("SoftAp", "13---startSoftAp,设备配网超时");
                if (SoftApStage.getInstance().getStage() == 3) {
                    this.mXConfigCallback.xconfigErrorCallback(HttpStatus.SC_FORBIDDEN, "设备配网超时");
                    return;
                }
                return;
            }
            if (productId.equals(this.mProductId)) {
                XConfigLog.w("SoftAp", "13---startSoftAp,设备配网成功");
                if (this.mSoftApConfig != null) {
                    this.mSoftApConfig.stopDeviceConfig();
                }
                SoftApStage.getInstance().setStage(4);
                XcLinkNetwork.linkHomeNetwork(this.mContext);
                setMac(mac);
                setProductId(productId);
                this.mXConfigCallback.xconfigDeviceCallback(mac, productId);
            }
        }
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.SoftApCallback
    public void coapCallback(int type, String value) {
        if (type == -1) {
            XConfigLog.w("SoftAp", "22 --- sendSoftAp--再次发送coap进行设备发现--");
            return;
        }
        if (type == 0) {
            if (!TextUtils.isEmpty(value)) {
                try {
                    JSONObject jsonObj = new JSONObject(value);
                    String deviceId = jsonObj.optString(Constants.FLAG_DEVICE_ID);
                    String mac = jsonObj.optString(MidEntity.TAG_MAC);
                    String productId = jsonObj.optString("productId");
                    if (!TextUtils.isEmpty(getMac()) && !TextUtils.isEmpty(getProductId()) && !TextUtils.isEmpty(deviceId) && productId.equals(this.mProductId)) {
                        XConfigLog.e("SoftAp", "23 --- coap接收----" + getMac() + "---" + value + "---");
                        if (getMac().toUpperCase().equals(mac.toUpperCase())) {
                            XConfigLog.e("SoftAp", "24 --- coap接收对比成功----" + getMac() + "---" + value + "---");
                            this.mXConfigCallback.xconfigCoapCallback(type, deviceId, mac);
                            stop();
                            return;
                        }
                        return;
                    }
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
            return;
        }
        if (type == 1 && SoftApStage.getInstance().isStartHttp()) {
            XConfigLog.e("SoftAp", "24 --- http轮询发现设备----" + getMac() + "---" + value + "---");
            this.mXConfigCallback.xconfigCoapCallback(type, value, getMac());
            stop();
        }
    }

    public void stop() {
        setMac(Constants.MAIN_VERSION_TAG);
        setProductId(Constants.MAIN_VERSION_TAG);
        this.mReconnectNum = 0;
        if (this.mSoftApConfig != null) {
            this.mSoftApConfig.stopDeviceConfig();
            this.mSoftApConfig = null;
        }
        if (this.mCoapScanner != null) {
            this.mCoapScanner.stopDeviceScan();
            this.mCoapScanner = null;
        }
    }

    private String getMac() {
        return this.mMac;
    }

    private void setMac(String mMac) {
        this.mMac = mMac;
    }

    private String getProductId() {
        return this.mProductId;
    }

    private void setProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    private static final class SoftApManagerHolder {
        private static final SoftApManager INSTANCE = new SoftApManager();
    }
}
