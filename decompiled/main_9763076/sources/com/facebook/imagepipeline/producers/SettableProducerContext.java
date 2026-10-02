package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.request.ImageRequest;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SettableProducerContext extends BaseProducerContext {
    public SettableProducerContext(ImageRequest imageRequest, String id, ProducerListener producerListener, Object callerContext, ImageRequest.RequestLevel lowestPermittedRequestLevel, boolean isPrefetch, boolean isIntermediateResultExpected, Priority priority) {
        super(imageRequest, id, producerListener, callerContext, lowestPermittedRequestLevel, isPrefetch, isIntermediateResultExpected, priority);
    }
}
