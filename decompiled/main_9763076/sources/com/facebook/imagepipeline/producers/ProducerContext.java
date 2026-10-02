package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.request.ImageRequest;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ProducerContext {
    void addCallbacks(ProducerContextCallbacks producerContextCallbacks);

    Object getCallerContext();

    String getId();

    ImageRequest getImageRequest();

    ProducerListener getListener();

    ImageRequest.RequestLevel getLowestPermittedRequestLevel();

    Priority getPriority();

    boolean isIntermediateResultExpected();

    boolean isPrefetch();
}
