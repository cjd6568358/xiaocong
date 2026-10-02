package com.xiaocong.smarthome.wheel;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class MessageHandler extends Handler {
    final WheelView wheelView;

    MessageHandler(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message msg) {
        switch (msg.what) {
            case 1000:
                this.wheelView.invalidate();
                break;
            case 2000:
                this.wheelView.smoothScroll(WheelView.ACTION.FLING);
                break;
            case 3000:
                this.wheelView.onItemSelected();
                break;
        }
    }
}
