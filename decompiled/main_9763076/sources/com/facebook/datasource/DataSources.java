package com.facebook.datasource;

import com.facebook.common.internal.Supplier;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DataSources {
    public static <T> DataSource<T> immediateFailedDataSource(Throwable failure) {
        SimpleDataSource<T> simpleDataSource = SimpleDataSource.create();
        simpleDataSource.setFailure(failure);
        return simpleDataSource;
    }

    public static <T> Supplier<DataSource<T>> getFailedDataSourceSupplier(final Throwable failure) {
        return new Supplier<DataSource<T>>() { // from class: com.facebook.datasource.DataSources.1
            @Override // com.facebook.common.internal.Supplier
            public DataSource<T> get() {
                return DataSources.immediateFailedDataSource(failure);
            }
        };
    }
}
