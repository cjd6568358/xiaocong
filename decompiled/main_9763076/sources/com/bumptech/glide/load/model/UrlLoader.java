package com.bumptech.glide.load.model;

import com.bumptech.glide.load.data.DataFetcher;
import java.net.URL;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UrlLoader<T> implements ModelLoader<URL, T> {
    private final ModelLoader<GlideUrl, T> glideUrlLoader;

    public UrlLoader(ModelLoader<GlideUrl, T> glideUrlLoader) {
        this.glideUrlLoader = glideUrlLoader;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public DataFetcher<T> getResourceFetcher(URL model, int width, int height) {
        return this.glideUrlLoader.getResourceFetcher(new GlideUrl(model), width, height);
    }
}
