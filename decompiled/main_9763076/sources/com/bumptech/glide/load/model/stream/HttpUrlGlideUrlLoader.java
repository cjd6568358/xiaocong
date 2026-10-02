package com.bumptech.glide.load.model.stream;

import android.content.Context;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import java.io.InputStream;
import org.apache.http.HttpStatus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HttpUrlGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    private final ModelCache<GlideUrl, GlideUrl> modelCache;

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private final ModelCache<GlideUrl, GlideUrl> modelCache = new ModelCache<>(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new HttpUrlGlideUrlLoader(this.modelCache);
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }
    }

    public HttpUrlGlideUrlLoader() {
        this(null);
    }

    public HttpUrlGlideUrlLoader(ModelCache<GlideUrl, GlideUrl> modelCache) {
        this.modelCache = modelCache;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
        GlideUrl url = model;
        if (this.modelCache != null) {
            GlideUrl url2 = this.modelCache.get(model, 0, 0);
            url = url2;
            if (url == null) {
                this.modelCache.put(model, 0, 0, model);
                url = model;
            }
        }
        return new HttpUrlFetcher(url);
    }
}
