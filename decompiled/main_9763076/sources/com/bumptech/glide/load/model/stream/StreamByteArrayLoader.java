package com.bumptech.glide.load.model.stream;

import android.content.Context;
import com.bumptech.glide.load.data.ByteArrayFetcher;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.tencent.android.tpush.common.Constants;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StreamByteArrayLoader implements StreamModelLoader<byte[]> {
    private final String id;

    public StreamByteArrayLoader() {
        this(Constants.MAIN_VERSION_TAG);
    }

    @Deprecated
    public StreamByteArrayLoader(String id) {
        this.id = id;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public DataFetcher<InputStream> getResourceFetcher(byte[] model, int width, int height) {
        return new ByteArrayFetcher(model, this.id);
    }

    public static class Factory implements ModelLoaderFactory<byte[], InputStream> {
        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public ModelLoader<byte[], InputStream> build(Context context, GenericLoaderFactory factories) {
            return new StreamByteArrayLoader();
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }
    }
}
