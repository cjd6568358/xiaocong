package com.bumptech.glide.load.model.stream;

import android.content.Context;
import android.net.Uri;
import com.bumptech.glide.load.model.FileLoader;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import java.io.File;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StreamFileLoader extends FileLoader<InputStream> implements StreamModelLoader<File> {

    public static class Factory implements ModelLoaderFactory<File, InputStream> {
        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public ModelLoader<File, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new StreamFileLoader(factories.buildModelLoader(Uri.class, InputStream.class));
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }
    }

    public StreamFileLoader(ModelLoader<Uri, InputStream> uriLoader) {
        super(uriLoader);
    }
}
