package com.xiaocong.smarthome.sdk.mqtt.helper;

import android.content.Context;
import android.os.CountDownTimer;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.uilib.widget.XCToastUtil;
import com.xiaocong.smarthome.util.log.XCLog;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttCountDown extends CountDownTimer {
    private Context mContext;

    public MqttCountDown(Context context, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mContext = context;
        XCLog.i("PublishMsgManager", "MqttCountDown start");
    }

    @Override // android.os.CountDownTimer
    public void onTick(long millisUntilFinished) {
    }

    @Override // android.os.CountDownTimer
    public void onFinish() {
        XCLog.i("PublishMsgManager", "MqttCountDown onFinish");
        XCToastUtil.showToast(this.mContext, "设备控制超时", 0);
        HttpLoadingHelper.getInstance().dismissMqttProcessLoading();
    }
}
