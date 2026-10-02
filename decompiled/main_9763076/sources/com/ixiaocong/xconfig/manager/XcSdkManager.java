package com.ixiaocong.xconfig.manager;

import android.content.Context;
import com.ixiaocong.log.XConfigLog;
import com.ixiaocong.xconfig.callback.XConfigCallback;
import com.xiaocong.smarthome.phone.xcsdk.DeviceSdk;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XcSdkManager implements Runnable {
    private volatile boolean mIsStart;

    public static XcSdkManager getInstance() {
        return XcSdkManagerHolder.INSTANCE;
    }

    public void startConfig(Context context, XConfigCallback callback, String productId) {
        DeviceSdk.getInstance().setCallback(callback);
        initPolling(true);
        findDeviceSDK(productId);
    }

    public void stopConfig() {
        initPolling(false);
        DeviceSdk.getInstance().XConfigStop();
    }

    private void findDeviceSDK(String productId) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("scanType", 1);
            obj.put("productId", productId);
            String cmdExec = DeviceSdk.getInstance().cmdExec(2, obj.toString());
            XConfigLog.e("deviceSdk---cmdExec---", cmdExec + "---" + obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initPolling(boolean isStart) {
        if (isStart) {
            new Thread(getInstance()).start();
        }
        this.mIsStart = isStart;
    }

    @Override // java.lang.Runnable
    public void run() {
        do {
            int polling = DeviceSdk.getInstance().polling();
            if (polling != 0) {
                XConfigLog.w("deviceSdk--", "polling" + polling);
            }
            XConfigLog.w("deviceSdk--", "Thread.sleep(1000)-----" + polling + "----" + this.mIsStart);
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (this.mIsStart);
    }

    private static final class XcSdkManagerHolder {
        private static final XcSdkManager INSTANCE = new XcSdkManager();
    }
}
