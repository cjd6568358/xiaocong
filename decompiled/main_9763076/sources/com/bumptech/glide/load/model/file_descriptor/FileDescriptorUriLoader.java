package com.bumptech.glide.load.model.file_descriptor;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.FileDescriptorAssetPathFetcher;
import com.bumptech.glide.load.data.FileDescriptorLocalUriFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.UriLoader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FileDescriptorUriLoader extends UriLoader<ParcelFileDescriptor> implements FileDescriptorModelLoader<Uri> {

    public static class Factory implements ModelLoaderFactory<Uri, ParcelFileDescriptor> {
        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public ModelLoader<Uri, ParcelFileDescriptor> build(Context context, GenericLoaderFactory factories) {
            return new FileDescriptorUriLoader(context, factories.buildModelLoader(GlideUrl.class, ParcelFileDescriptor.class));
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }
    }

    public FileDescriptorUriLoader(Context context, ModelLoader<GlideUrl, ParcelFileDescriptor> urlLoader) {
        super(context, urlLoader);
    }

    @Override // com.bumptech.glide.load.model.UriLoader
    protected DataFetcher<ParcelFileDescriptor> getLocalUriFetcher(Context context, Uri uri) {
        return new FileDescriptorLocalUriFetcher(context, uri);
    }

    @Override // com.bumptech.glide.load.model.UriLoader
    protected DataFetcher<ParcelFileDescriptor> getAssetPathFetcher(Context context, String assetPath) {
        return new FileDescriptorAssetPathFetcher(context.getApplicationContext().getAssets(), assetPath);
    }
}
