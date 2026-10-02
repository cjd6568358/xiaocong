package com.facebook.imagepipeline.backends.okhttp3;

import android.content.Context;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import okhttp3.OkHttpClient;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class OkHttpImagePipelineConfigFactory {
    public static ImagePipelineConfig.Builder newBuilder(Context context, OkHttpClient okHttpClient) {
        return ImagePipelineConfig.newBuilder(context).setNetworkFetcher(new OkHttpNetworkFetcher(okHttpClient));
    }
}
