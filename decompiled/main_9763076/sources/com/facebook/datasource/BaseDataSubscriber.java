package com.facebook.datasource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BaseDataSubscriber<T> implements DataSubscriber<T> {
    protected abstract void onFailureImpl(DataSource<T> dataSource);

    protected abstract void onNewResultImpl(DataSource<T> dataSource);

    @Override // com.facebook.datasource.DataSubscriber
    public void onNewResult(DataSource<T> dataSource) {
        boolean shouldClose = dataSource.isFinished();
        try {
            onNewResultImpl(dataSource);
        } finally {
            if (shouldClose) {
                dataSource.close();
            }
        }
    }

    @Override // com.facebook.datasource.DataSubscriber
    public void onFailure(DataSource<T> dataSource) {
        try {
            onFailureImpl(dataSource);
        } finally {
            dataSource.close();
        }
    }

    @Override // com.facebook.datasource.DataSubscriber
    public void onCancellation(DataSource<T> dataSource) {
    }

    @Override // com.facebook.datasource.DataSubscriber
    public void onProgressUpdate(DataSource<T> dataSource) {
    }
}
