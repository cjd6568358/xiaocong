package com.facebook.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FirstAvailableDataSourceSupplier<T> implements Supplier<DataSource<T>> {
    private final List<Supplier<DataSource<T>>> mDataSourceSuppliers;

    private FirstAvailableDataSourceSupplier(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        Preconditions.checkArgument(!dataSourceSuppliers.isEmpty(), "List of suppliers is empty!");
        this.mDataSourceSuppliers = dataSourceSuppliers;
    }

    public static <T> FirstAvailableDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> dataSourceSuppliers) {
        return new FirstAvailableDataSourceSupplier<>(dataSourceSuppliers);
    }

    @Override // com.facebook.common.internal.Supplier
    public DataSource<T> get() {
        return new FirstAvailableDataSource();
    }

    public int hashCode() {
        return this.mDataSourceSuppliers.hashCode();
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FirstAvailableDataSourceSupplier)) {
            return false;
        }
        FirstAvailableDataSourceSupplier that = (FirstAvailableDataSourceSupplier) other;
        return Objects.equal(this.mDataSourceSuppliers, that.mDataSourceSuppliers);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("list", this.mDataSourceSuppliers).toString();
    }

    private class FirstAvailableDataSource extends AbstractDataSource<T> {
        private int mIndex = 0;
        private DataSource<T> mCurrentDataSource = null;
        private DataSource<T> mDataSourceWithResult = null;

        public FirstAvailableDataSource() {
            if (!startNextDataSource()) {
                setFailure(new RuntimeException("No data source supplier or supplier returned null."));
            }
        }

        @Override // com.facebook.datasource.AbstractDataSource, com.facebook.datasource.DataSource
        public synchronized T getResult() {
            DataSource<T> dataSourceWithResult;
            dataSourceWithResult = getDataSourceWithResult();
            return dataSourceWithResult != null ? dataSourceWithResult.getResult() : null;
        }

        @Override // com.facebook.datasource.AbstractDataSource, com.facebook.datasource.DataSource
        public synchronized boolean hasResult() {
            DataSource<T> dataSourceWithResult;
            dataSourceWithResult = getDataSourceWithResult();
            return dataSourceWithResult != null && dataSourceWithResult.hasResult();
        }

        @Override // com.facebook.datasource.AbstractDataSource, com.facebook.datasource.DataSource
        public boolean close() {
            synchronized (this) {
                if (!super.close()) {
                    return false;
                }
                DataSource<T> currentDataSource = this.mCurrentDataSource;
                this.mCurrentDataSource = null;
                DataSource<T> dataSourceWithResult = this.mDataSourceWithResult;
                this.mDataSourceWithResult = null;
                closeSafely(dataSourceWithResult);
                closeSafely(currentDataSource);
                return true;
            }
        }

        private boolean startNextDataSource() {
            Supplier<DataSource<T>> dataSourceSupplier = getNextSupplier();
            DataSource<T> dataSource = dataSourceSupplier != null ? dataSourceSupplier.get() : null;
            if (setCurrentDataSource(dataSource) && dataSource != null) {
                dataSource.subscribe(new InternalDataSubscriber(), CallerThreadExecutor.getInstance());
                return true;
            }
            closeSafely(dataSource);
            return false;
        }

        private synchronized Supplier<DataSource<T>> getNextSupplier() {
            Supplier<DataSource<T>> supplier;
            if (isClosed() || this.mIndex >= FirstAvailableDataSourceSupplier.this.mDataSourceSuppliers.size()) {
                supplier = null;
            } else {
                List list = FirstAvailableDataSourceSupplier.this.mDataSourceSuppliers;
                int i = this.mIndex;
                this.mIndex = i + 1;
                supplier = (Supplier) list.get(i);
            }
            return supplier;
        }

        private synchronized boolean setCurrentDataSource(DataSource<T> dataSource) {
            boolean z;
            if (isClosed()) {
                z = false;
            } else {
                this.mCurrentDataSource = dataSource;
                z = true;
            }
            return z;
        }

        private synchronized boolean clearCurrentDataSource(DataSource<T> dataSource) {
            boolean z;
            if (isClosed() || dataSource != this.mCurrentDataSource) {
                z = false;
            } else {
                this.mCurrentDataSource = null;
                z = true;
            }
            return z;
        }

        private synchronized DataSource<T> getDataSourceWithResult() {
            return this.mDataSourceWithResult;
        }

        private void maybeSetDataSourceWithResult(DataSource<T> dataSource, boolean isFinished) {
            DataSource<T> oldDataSource = null;
            synchronized (this) {
                if (dataSource == this.mCurrentDataSource && dataSource != this.mDataSourceWithResult) {
                    if (this.mDataSourceWithResult == null || isFinished) {
                        oldDataSource = this.mDataSourceWithResult;
                        this.mDataSourceWithResult = dataSource;
                    }
                    closeSafely(oldDataSource);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDataSourceFailed(DataSource<T> dataSource) {
            if (clearCurrentDataSource(dataSource)) {
                if (dataSource != getDataSourceWithResult()) {
                    closeSafely(dataSource);
                }
                if (!startNextDataSource()) {
                    setFailure(dataSource.getFailureCause());
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDataSourceNewResult(DataSource<T> dataSource) {
            maybeSetDataSourceWithResult(dataSource, dataSource.isFinished());
            if (dataSource == getDataSourceWithResult()) {
                setResult(null, dataSource.isFinished());
            }
        }

        private void closeSafely(DataSource<T> dataSource) {
            if (dataSource != null) {
                dataSource.close();
            }
        }

        private class InternalDataSubscriber implements DataSubscriber<T> {
            private InternalDataSubscriber() {
            }

            @Override // com.facebook.datasource.DataSubscriber
            public void onFailure(DataSource<T> dataSource) {
                FirstAvailableDataSource.this.onDataSourceFailed(dataSource);
            }

            @Override // com.facebook.datasource.DataSubscriber
            public void onCancellation(DataSource<T> dataSource) {
            }

            @Override // com.facebook.datasource.DataSubscriber
            public void onNewResult(DataSource<T> dataSource) {
                if (dataSource.hasResult()) {
                    FirstAvailableDataSource.this.onDataSourceNewResult(dataSource);
                } else if (dataSource.isFinished()) {
                    FirstAvailableDataSource.this.onDataSourceFailed(dataSource);
                }
            }

            @Override // com.facebook.datasource.DataSubscriber
            public void onProgressUpdate(DataSource<T> dataSource) {
                float oldProgress = FirstAvailableDataSource.this.getProgress();
                FirstAvailableDataSource.this.setProgress(Math.max(oldProgress, dataSource.getProgress()));
            }
        }
    }
}
