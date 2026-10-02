package com.facebook.datasource;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface DataSource<T> {
    boolean close();

    Throwable getFailureCause();

    float getProgress();

    T getResult();

    boolean hasResult();

    boolean isFinished();

    void subscribe(DataSubscriber<T> dataSubscriber, Executor executor);
}
