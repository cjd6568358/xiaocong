package com.facebook.imagepipeline.producers;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.IOException;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LocalResourceFetchProducer extends LocalFetchProducer {
    private final Resources mResources;

    public LocalResourceFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, Resources resources, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
        this.mResources = resources;
    }

    @Override // com.facebook.imagepipeline.producers.LocalFetchProducer
    protected EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException {
        return getEncodedImage(this.mResources.openRawResource(getResourceId(imageRequest)), getLength(imageRequest));
    }

    private int getLength(ImageRequest imageRequest) {
        int length;
        AssetFileDescriptor fd = null;
        try {
            fd = this.mResources.openRawResourceFd(getResourceId(imageRequest));
            length = (int) fd.getLength();
        } catch (Resources.NotFoundException e) {
            length = -1;
        } finally {
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e2) {
                }
            }
        }
        return length;
    }

    @Override // com.facebook.imagepipeline.producers.LocalFetchProducer
    protected String getProducerName() {
        return "LocalResourceFetchProducer";
    }

    private static int getResourceId(ImageRequest imageRequest) {
        return Integer.parseInt(imageRequest.getSourceUri().getPath().substring(1));
    }
}
