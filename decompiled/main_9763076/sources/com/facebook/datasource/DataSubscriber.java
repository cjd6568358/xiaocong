package com.facebook.datasource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface DataSubscriber<T> {
    void onCancellation(DataSource<T> dataSource);

    void onFailure(DataSource<T> dataSource);

    void onNewResult(DataSource<T> dataSource);

    void onProgressUpdate(DataSource<T> dataSource);
}
