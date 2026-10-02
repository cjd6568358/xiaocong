package com.xiaocong.smarthome.zxing.executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class AsyncTaskExecManager extends PlatformSupportManager<AsyncTaskExecInterface> {
    public AsyncTaskExecManager() {
        super(AsyncTaskExecInterface.class, new DefaultAsyncTaskExecInterface());
    }
}
