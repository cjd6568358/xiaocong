package com.xiaocong.smarthome.sdk.mqtt.helper;

import android.app.Activity;
import android.content.Context;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import com.xiaocong.smarthome.util.XCActivityManager;
import com.xiaocong.smarthome.util.log.XCLog;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PublishMsgManager {
    private static MqttCountDown mCountDown;

    public static void countDownStart(Context context) {
        Activity activity;
        mCountDown = new MqttCountDown(context, 5000L, 1000L);
        mCountDown.start();
        XCLog.i("PublishMsgManager", "countDownStart");
        if (XCDeviceController.getInstance().getShowDialog() && (activity = XCActivityManager.getInstance().getCurrentActivity()) != null) {
            HttpLoadingHelper.getInstance().showMqttProcessLoading(activity);
        }
    }

    public static void countDownFinish() {
        if (mCountDown != null) {
            mCountDown.cancel();
            XCLog.i("PublishMsgManager", "mCountDown.cancel()");
        }
        XCLog.i("PublishMsgManager", "countDownFinish");
        HttpLoadingHelper.getInstance().dismissMqttProcessLoading();
    }
}
