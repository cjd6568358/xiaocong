package com.xiaocong.smarthome.zxing.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class AutoFocusCallback implements Camera.AutoFocusCallback {
    private static final String TAG = AutoFocusCallback.class.getSimpleName();
    private Handler autoFocusHandler;
    private int autoFocusMessage;

    AutoFocusCallback() {
    }

    void setHandler(Handler autoFocusHandler, int autoFocusMessage) {
        this.autoFocusHandler = autoFocusHandler;
        this.autoFocusMessage = autoFocusMessage;
    }

    @Override // android.hardware.Camera.AutoFocusCallback
    public void onAutoFocus(boolean success, Camera camera) {
        if (this.autoFocusHandler != null) {
            Message message = this.autoFocusHandler.obtainMessage(this.autoFocusMessage, Boolean.valueOf(success));
            this.autoFocusHandler.sendMessageDelayed(message, 1500L);
            this.autoFocusHandler = null;
        }
    }
}
