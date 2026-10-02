package com.facebook.datasource;

import com.facebook.common.internal.Preconditions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SimpleDataSource<T> extends AbstractDataSource<T> {
    private SimpleDataSource() {
    }

    public static <T> SimpleDataSource<T> create() {
        return new SimpleDataSource<>();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.facebook.datasource.AbstractDataSource
    public boolean setResult(T value, boolean isLast) {
        return super.setResult(Preconditions.checkNotNull(value), isLast);
    }

    @Override // com.facebook.datasource.AbstractDataSource
    public boolean setFailure(Throwable throwable) {
        return super.setFailure((Throwable) Preconditions.checkNotNull(throwable));
    }

    @Override // com.facebook.datasource.AbstractDataSource
    public boolean setProgress(float progress) {
        return super.setProgress(progress);
    }
}
