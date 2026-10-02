package com.facebook.imagepipeline.producers;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ProducerContextCallbacks {
    void onCancellationRequested();

    void onIsIntermediateResultExpectedChanged();

    void onIsPrefetchChanged();

    void onPriorityChanged();
}
