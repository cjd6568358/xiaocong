package com.xiaocong.smarthome.sdk;

import android.app.Application;
import android.content.IntentFilter;
import com.xiaocong.smarthome.sdk.common.receiver.XCNetworkReceiver;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XCSuperSDK {
    private static Application mApplication;
    private static volatile XCSuperSDK mInstance;
    private XCNetworkReceiver mNetworkReceiver;

    private XCSuperSDK() {
    }

    public static XCSuperSDK getInstance() {
        if (mInstance == null) {
            synchronized (XCSuperSDK.class) {
                if (mInstance == null) {
                    mInstance = new XCSuperSDK();
                }
            }
        }
        return mInstance;
    }

    public void init(Application application) {
        mApplication = application;
        XCHelp.init(mApplication);
        if (this.mNetworkReceiver == null) {
            this.mNetworkReceiver = new XCNetworkReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        if (this.mNetworkReceiver != null) {
            mApplication.registerReceiver(this.mNetworkReceiver, filter);
        }
    }
}
