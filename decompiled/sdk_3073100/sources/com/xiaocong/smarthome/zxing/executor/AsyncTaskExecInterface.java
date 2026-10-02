package com.xiaocong.smarthome.zxing.executor;

import android.os.AsyncTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface AsyncTaskExecInterface {
    <T> void execute(AsyncTask<T, ?, ?> asyncTask, T... tArr);
}
