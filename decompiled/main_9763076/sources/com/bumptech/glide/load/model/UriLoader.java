package com.bumptech.glide.load.model;

import android.content.Context;
import android.net.Uri;
import com.bumptech.glide.load.data.DataFetcher;
import org.apache.http.HttpHost;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class UriLoader<T> implements ModelLoader<Uri, T> {
    private final Context context;
    private final ModelLoader<GlideUrl, T> urlLoader;

    protected abstract DataFetcher<T> getAssetPathFetcher(Context context, String str);

    protected abstract DataFetcher<T> getLocalUriFetcher(Context context, Uri uri);

    public UriLoader(Context context, ModelLoader<GlideUrl, T> urlLoader) {
        this.context = context;
        this.urlLoader = urlLoader;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public final DataFetcher<T> getResourceFetcher(Uri model, int width, int height) {
        String scheme = model.getScheme();
        if (isLocalUri(scheme)) {
            if (AssetUriParser.isAssetUri(model)) {
                String path = AssetUriParser.toAssetPath(model);
                DataFetcher<T> result = getAssetPathFetcher(this.context, path);
                return result;
            }
            DataFetcher<T> result2 = getLocalUriFetcher(this.context, model);
            return result2;
        }
        if (this.urlLoader == null) {
            return null;
        }
        if (!HttpHost.DEFAULT_SCHEME_NAME.equals(scheme) && !"https".equals(scheme)) {
            return null;
        }
        DataFetcher<T> result3 = this.urlLoader.getResourceFetcher(new GlideUrl(model.toString()), width, height);
        return result3;
    }

    private static boolean isLocalUri(String scheme) {
        return "file".equals(scheme) || "content".equals(scheme) || "android.resource".equals(scheme);
    }
}
