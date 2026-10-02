package com.facebook.common.executors;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UiThreadImmediateExecutorService extends HandlerExecutorServiceImpl {
    private static UiThreadImmediateExecutorService sInstance = null;

    private UiThreadImmediateExecutorService() {
        super(new Handler(Looper.getMainLooper()));
    }

    public static UiThreadImmediateExecutorService getInstance() {
        if (sInstance == null) {
            sInstance = new UiThreadImmediateExecutorService();
        }
        return sInstance;
    }

    @Override // com.facebook.common.executors.HandlerExecutorServiceImpl, java.util.concurrent.Executor
    public void execute(Runnable command) {
        if (isHandlerThread()) {
            command.run();
        } else {
            super.execute(command);
        }
    }
}
