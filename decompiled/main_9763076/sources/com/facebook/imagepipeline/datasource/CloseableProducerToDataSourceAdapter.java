package com.facebook.imagepipeline.datasource;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.SettableProducerContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CloseableProducerToDataSourceAdapter<T> extends AbstractProducerToDataSourceAdapter<CloseableReference<T>> {
    public static <T> DataSource<CloseableReference<T>> create(Producer<CloseableReference<T>> producer, SettableProducerContext settableProducerContext, RequestListener listener) {
        return new CloseableProducerToDataSourceAdapter(producer, settableProducerContext, listener);
    }

    private CloseableProducerToDataSourceAdapter(Producer<CloseableReference<T>> producer, SettableProducerContext settableProducerContext, RequestListener listener) {
        super(producer, settableProducerContext, listener);
    }

    @Override // com.facebook.datasource.AbstractDataSource, com.facebook.datasource.DataSource
    public CloseableReference<T> getResult() {
        return CloseableReference.cloneOrNull((CloseableReference) super.getResult());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.datasource.AbstractDataSource
    public void closeResult(CloseableReference<T> result) {
        CloseableReference.closeSafely(result);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.imagepipeline.datasource.AbstractProducerToDataSourceAdapter
    public void onNewResultImpl(CloseableReference<T> result, boolean isLast) {
        super.onNewResultImpl(CloseableReference.cloneOrNull(result), isLast);
    }
}
