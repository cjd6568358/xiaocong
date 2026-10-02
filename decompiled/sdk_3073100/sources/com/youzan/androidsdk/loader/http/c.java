package com.youzan.androidsdk.loader.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.youzan.androidsdk.YouzanException;
import com.youzan.androidsdk.account.Token;
import com.youzan.androidsdk.loader.http.interfaces.HttpEngine;
import com.youzan.androidsdk.loader.http.interfaces.HttpInterceptor;
import com.youzan.androidsdk.tool.UserAgent;
import java.io.File;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: Engine.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
abstract class c implements HttpEngine {
    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    protected abstract <MODEL> void mo74(Class<MODEL> cls, Query<MODEL> query, Context context, boolean z);

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    protected abstract void mo75(String str);

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    protected abstract void mo76(Map<String, String> map);

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    protected abstract void mo77(Map<String, File> map, Map<String, String> map2);

    c() {
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static void m72(Runnable runnable) {
        if (runnable != null) {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpEngine
    public <MODEL> void request(Context context, int method, String url, Map<String, String> parameter, Map<String, File> files, Map<String, String> header, Class<MODEL> cls, Query<MODEL> query, boolean onUI) {
        query.mEngine = this;
        mo76(m73(header));
        Map<String, String> param = m71(query.getAuthType(), com.youzan.androidsdk.tool.a.m86(com.youzan.androidsdk.tool.a.m88(url), parameter));
        if (method == 2 || (files != null && files.size() > 0)) {
            mo75(com.youzan.androidsdk.tool.a.m84(url));
            mo77(files, param);
        } else {
            mo75(com.youzan.androidsdk.tool.a.m85(com.youzan.androidsdk.tool.a.m84(url), param));
        }
        mo74(cls, query, context, onUI);
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpEngine
    public <MODEL> void response(String body, Map<String, List<String>> header, YouzanException error, Query<MODEL> query, Context context, Class<MODEL> cls) {
        if (query.mInterceptor != null && query.mInterceptor.size() > 0) {
            for (HttpInterceptor interceptor : query.mInterceptor) {
                if (interceptor != null && interceptor.intercept(context, body)) {
                    return;
                }
            }
        }
        query.mResponseBody = body;
        query.mResponseHeader = header;
        MODEL data = null;
        if (error == null) {
            try {
                data = query.onFilter(body);
            } catch (YouzanException e1) {
                error = e1;
            } catch (Exception e2) {
                error = TextUtils.isEmpty(e2.getMessage()) ? new YouzanException(e2) : new YouzanException(e2.getMessage());
            }
        }
        a<MODEL> runner = new a<>(query, data, error);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runner.run();
        } else if (context != null) {
            m72(runner);
        }
    }

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private Map<String, String> m73(Map<String, String> header) {
        header.put("User-agent", UserAgent.httpUA);
        return header;
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private Map<String, String> m71(int authType, Map<String, String> parameter) {
        switch (authType) {
            case 2:
            case 3:
                parameter.put("access_token", Token.getAccessToken());
            default:
                return parameter;
        }
    }

    /* JADX INFO: compiled from: Engine.java */
    private static class a<MODEL> implements Runnable {

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        private final Query<MODEL> f97;

        /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
        private final YouzanException f98;

        /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
        private final MODEL f99;

        a(Query<MODEL> listener, MODEL data, YouzanException error) {
            this.f97 = listener;
            this.f99 = data;
            this.f98 = error;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.f98 == null) {
                this.f97.onSuccess(this.f99);
            } else {
                this.f97.onFailure(this.f98);
            }
        }
    }
}
