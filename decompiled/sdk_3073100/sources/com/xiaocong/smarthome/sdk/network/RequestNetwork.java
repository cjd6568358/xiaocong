package com.xiaocong.smarthome.sdk.network;

import android.content.Context;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.network.wg.BaseWgRequest;
import rx.Observable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class RequestNetwork extends BaseWgRequest {
    private static volatile RequestNetwork mWelcomeNetwork = null;

    public static RequestNetwork getInstance() {
        if (mWelcomeNetwork == null) {
            synchronized (RequestNetwork.class) {
                if (mWelcomeNetwork == null) {
                    mWelcomeNetwork = new RequestNetwork();
                }
            }
        }
        return mWelcomeNetwork;
    }

    public Observable<XCResponseBean> request(Context context, XCHttpSetting setting) {
        return wgRequest(context, setting);
    }

    public void removeRequst() {
        if (mWelcomeNetwork != null) {
            mWelcomeNetwork = null;
        }
    }
}
