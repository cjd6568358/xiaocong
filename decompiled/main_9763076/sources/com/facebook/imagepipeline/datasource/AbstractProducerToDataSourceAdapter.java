package com.facebook.imagepipeline.datasource;

import com.facebook.common.internal.Preconditions;
import com.facebook.datasource.AbstractDataSource;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.producers.BaseConsumer;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.SettableProducerContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class AbstractProducerToDataSourceAdapter<T> extends AbstractDataSource<T> {
    private final RequestListener mRequestListener;
    private final SettableProducerContext mSettableProducerContext;

    protected AbstractProducerToDataSourceAdapter(Producer<T> producer, SettableProducerContext settableProducerContext, RequestListener requestListener) {
        this.mSettableProducerContext = settableProducerContext;
        this.mRequestListener = requestListener;
        this.mRequestListener.onRequestStart(settableProducerContext.getImageRequest(), this.mSettableProducerContext.getCallerContext(), this.mSettableProducerContext.getId(), this.mSettableProducerContext.isPrefetch());
        producer.produceResults(createConsumer(), settableProducerContext);
    }

    private Consumer<T> createConsumer() {
        return new BaseConsumer<T>() { // from class: com.facebook.imagepipeline.datasource.AbstractProducerToDataSourceAdapter.1
            @Override // com.facebook.imagepipeline.producers.BaseConsumer
            protected void onNewResultImpl(T newResult, boolean isLast) {
                AbstractProducerToDataSourceAdapter.this.onNewResultImpl(newResult, isLast);
            }

            @Override // com.facebook.imagepipeline.producers.BaseConsumer
            protected void onFailureImpl(Throwable throwable) {
                AbstractProducerToDataSourceAdapter.this.onFailureImpl(throwable);
            }

            @Override // com.facebook.imagepipeline.producers.BaseConsumer
            protected void onCancellationImpl() {
                AbstractProducerToDataSourceAdapter.this.onCancellationImpl();
            }

            @Override // com.facebook.imagepipeline.producers.BaseConsumer
            protected void onProgressUpdateImpl(float progress) {
                AbstractProducerToDataSourceAdapter.this.setProgress(progress);
            }
        };
    }

    protected void onNewResultImpl(T result, boolean isLast) {
        if (super.setResult(result, isLast) && isLast) {
            this.mRequestListener.onRequestSuccess(this.mSettableProducerContext.getImageRequest(), this.mSettableProducerContext.getId(), this.mSettableProducerContext.isPrefetch());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFailureImpl(Throwable throwable) {
        if (super.setFailure(throwable)) {
            this.mRequestListener.onRequestFailure(this.mSettableProducerContext.getImageRequest(), this.mSettableProducerContext.getId(), throwable, this.mSettableProducerContext.isPrefetch());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onCancellationImpl() {
        Preconditions.checkState(isClosed());
    }

    @Override // com.facebook.datasource.AbstractDataSource, com.facebook.datasource.DataSource
    public boolean close() {
        if (!super.close()) {
            return false;
        }
        if (!super.isFinished()) {
            this.mRequestListener.onRequestCancellation(this.mSettableProducerContext.getId());
            this.mSettableProducerContext.cancel();
        }
        return true;
    }
}
