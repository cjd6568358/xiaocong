package com.facebook.imagepipeline.producers;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.IOException;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LocalAssetFetchProducer extends LocalFetchProducer {
    private final AssetManager mAssetManager;

    public LocalAssetFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, AssetManager assetManager, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
        this.mAssetManager = assetManager;
    }

    @Override // com.facebook.imagepipeline.producers.LocalFetchProducer
    protected EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException {
        return getEncodedImage(this.mAssetManager.open(getAssetName(imageRequest), 2), getLength(imageRequest));
    }

    private int getLength(ImageRequest imageRequest) {
        int length;
        AssetFileDescriptor fd = null;
        try {
            fd = this.mAssetManager.openFd(getAssetName(imageRequest));
            length = (int) fd.getLength();
        } catch (IOException e) {
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
        return "LocalAssetFetchProducer";
    }

    private static String getAssetName(ImageRequest imageRequest) {
        return imageRequest.getSourceUri().getPath().substring(1);
    }
}
