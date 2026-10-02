package com.bumptech.glide.load.model.stream;

import android.content.Context;
import android.net.Uri;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.StringLoader;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StreamStringLoader extends StringLoader<InputStream> implements StreamModelLoader<String> {

    public static class Factory implements ModelLoaderFactory<String, InputStream> {
        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public ModelLoader<String, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new StreamStringLoader(factories.buildModelLoader(Uri.class, InputStream.class));
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }
    }

    public StreamStringLoader(ModelLoader<Uri, InputStream> uriLoader) {
        super(uriLoader);
    }
}
