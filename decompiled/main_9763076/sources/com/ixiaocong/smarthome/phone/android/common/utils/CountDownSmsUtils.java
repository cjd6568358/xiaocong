package com.ixiaocong.smarthome.phone.android.common.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.android.event.callback.CountDownSmsFinishCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CountDownSmsUtils extends CountDownTimer {
    private CountDownSmsFinishCallback mCallback;
    private TextView textView;

    public CountDownSmsUtils(CountDownSmsFinishCallback callback, TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.mCallback = callback;
    }

    @Override // android.os.CountDownTimer
    public void onFinish() {
        this.textView.setText("重新获取");
        this.textView.setTextColor(Color.parseColor("#222222"));
        this.textView.setClickable(true);
        this.mCallback.restartCountDownSmsListener();
    }

    @Override // android.os.CountDownTimer
    public void onTick(long millisUntilFinished) {
        this.textView.setClickable(false);
        this.textView.setTextColor(Color.parseColor("#222222"));
        this.textView.setText("重新获取(" + ((millisUntilFinished / 1000) - 1) + ")");
    }
}
