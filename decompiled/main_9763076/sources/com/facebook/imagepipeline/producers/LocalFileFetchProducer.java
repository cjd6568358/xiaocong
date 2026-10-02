package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LocalFileFetchProducer extends LocalFetchProducer {
    public LocalFileFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
    }

    @Override // com.facebook.imagepipeline.producers.LocalFetchProducer
    protected EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException {
        return getEncodedImage(new FileInputStream(imageRequest.getSourceFile().toString()), (int) imageRequest.getSourceFile().length());
    }

    @Override // com.facebook.imagepipeline.producers.LocalFetchProducer
    protected String getProducerName() {
        return "LocalFileFetchProducer";
    }
}
