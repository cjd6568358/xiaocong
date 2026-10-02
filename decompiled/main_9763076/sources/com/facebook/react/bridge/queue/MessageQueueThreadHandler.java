package com.facebook.react.bridge.queue;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MessageQueueThreadHandler extends Handler {
    private final QueueThreadExceptionHandler mExceptionHandler;

    public MessageQueueThreadHandler(Looper looper, QueueThreadExceptionHandler exceptionHandler) {
        super(looper);
        this.mExceptionHandler = exceptionHandler;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message msg) {
        try {
            super.dispatchMessage(msg);
        } catch (Exception e) {
            this.mExceptionHandler.handleException(e);
        }
    }
}
