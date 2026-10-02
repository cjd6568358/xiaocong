package com.xiaocong.smarthome.zxing.executor;

import android.os.AsyncTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class DefaultAsyncTaskExecInterface implements AsyncTaskExecInterface {
    @Override // com.xiaocong.smarthome.zxing.executor.AsyncTaskExecInterface
    public <T> void execute(AsyncTask<T, ?, ?> task, T... args) {
        task.execute(args);
    }
}
