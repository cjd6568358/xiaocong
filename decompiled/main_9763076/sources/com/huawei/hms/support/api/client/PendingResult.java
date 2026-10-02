package com.huawei.hms.support.api.client;

import android.os.Looper;
import com.huawei.hms.support.api.client.Result;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class PendingResult<R extends Result> {
    public abstract R await();

    public abstract R await(long j, TimeUnit timeUnit);

    public abstract void setResultCallback(Looper looper, ResultCallback<R> resultCallback);

    public abstract void setResultCallback(ResultCallback<R> resultCallback);
}
