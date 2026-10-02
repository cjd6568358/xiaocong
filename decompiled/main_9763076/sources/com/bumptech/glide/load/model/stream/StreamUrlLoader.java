package com.bumptech.glide.load.model.stream;

import android.content.Context;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.UrlLoader;
import java.io.InputStream;
import java.net.URL;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StreamUrlLoader extends UrlLoader<InputStream> {

    public static class Factory implements ModelLoaderFactory<URL, InputStream> {
        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public ModelLoader<URL, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new StreamUrlLoader(factories.buildModelLoader(GlideUrl.class, InputStream.class));
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }
    }

    public StreamUrlLoader(ModelLoader<GlideUrl, InputStream> glideUrlLoader) {
        super(glideUrlLoader);
    }
}
