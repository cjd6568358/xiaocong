package com.youzan.androidsdk.loader.http;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.xiaocong.smarthome.network.httplib.RequestParams;
import com.youzan.androidsdk.YouzanException;
import com.youzan.androidsdk.loader.http.interfaces.HttpEngine;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* JADX INFO: compiled from: OkEngine.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class d extends c {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static final OkHttpClient f100 = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).readTimeout(30, TimeUnit.SECONDS).build();

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private final Request.Builder f101 = new Request.Builder();

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private Call f102;

    d() {
    }

    @Override // com.youzan.androidsdk.loader.http.c
    /* JADX INFO: renamed from: ˊ */
    protected void mo77(Map<String, File> files, Map<String, String> parameter) {
        if (files != null && files.size() > 0) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (Map.Entry<String, String> entry : parameter.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!TextUtils.isEmpty(key)) {
                    if (TextUtils.isEmpty(value)) {
                        value = "";
                    }
                    builder.addFormDataPart(key, value);
                }
            }
            for (Map.Entry<String, File> entry2 : files.entrySet()) {
                String key2 = entry2.getKey();
                File file = entry2.getValue();
                if (!TextUtils.isEmpty(key2) && file != null) {
                    builder.addFormDataPart(key2, file.getName(), RequestBody.create(MediaType.parse(RequestParams.APPLICATION_OCTET_STREAM), file));
                }
            }
            this.f101.post(builder.build());
            return;
        }
        FormBody.Builder builder2 = new FormBody.Builder();
        for (Map.Entry<String, String> entry3 : parameter.entrySet()) {
            String key3 = entry3.getKey();
            String value2 = entry3.getValue();
            if (!TextUtils.isEmpty(key3)) {
                if (TextUtils.isEmpty(value2)) {
                    value2 = "";
                }
                builder2.add(key3, value2);
            }
        }
        this.f101.post(builder2.build());
    }

    @Override // com.youzan.androidsdk.loader.http.c
    /* JADX INFO: renamed from: ˊ */
    protected void mo75(String url) {
        this.f101.url(url);
    }

    @Override // com.youzan.androidsdk.loader.http.c
    /* JADX INFO: renamed from: ˊ */
    protected void mo76(Map<String, String> header) {
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!TextUtils.isEmpty(key)) {
                    Request.Builder builder = this.f101;
                    if (TextUtils.isEmpty(value)) {
                        value = "";
                    }
                    builder.addHeader(key, value);
                }
            }
        }
    }

    @Override // com.youzan.androidsdk.loader.http.c
    /* JADX INFO: renamed from: ˊ */
    protected <MODEL> void mo74(Class<MODEL> cls, Query<MODEL> query, Context context, boolean onUI) {
        this.f102 = f100.newCall(this.f101.build());
        this.f102.enqueue(new a(context, cls, onUI, query, this));
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpEngine
    public void cancel() {
        if (this.f102 != null && !this.f102.isCanceled()) {
            this.f102.cancel();
        }
    }

    /* JADX INFO: compiled from: OkEngine.java */
    private static class a<MODEL> implements Callback {

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        private final boolean f103;

        /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
        private final HttpEngine f104;

        /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
        private final Class<MODEL> f105;

        /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
        private final WeakReference<Context> f106;

        /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
        private final Query<MODEL> f107;

        public a(Context context, Class<MODEL> cls, boolean onUI, Query<MODEL> query, HttpEngine engine) {
            this.f103 = onUI;
            this.f105 = cls;
            this.f104 = engine;
            this.f107 = query;
            this.f106 = new WeakReference<>(context);
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        private boolean m78() {
            Context context = this.f106.get();
            return !this.f103 || ((context instanceof Activity) && !((Activity) context).isFinishing());
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException e) {
            Context context = this.f106.get();
            Query<MODEL> query = this.f107;
            if (!"Canceled".equalsIgnoreCase(e.getMessage()) && m78() && query != null) {
                this.f104.response(null, null, new YouzanException(e), query, context, null);
            }
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            Context context = this.f106.get();
            Query<MODEL> query = this.f107;
            if (query != null && m78()) {
                this.f104.response(response.body().string(), response.headers().toMultimap(), response.isSuccessful() ? null : new YouzanException(response.code(), response.message()), query, context, this.f105);
            }
        }
    }
}
