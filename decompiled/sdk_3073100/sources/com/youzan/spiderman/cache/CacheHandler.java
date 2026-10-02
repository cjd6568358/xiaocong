package com.youzan.spiderman.cache;

import android.content.Context;
import android.net.Uri;
import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import com.youzan.spiderman.html.HtmlResponse;
import com.youzan.spiderman.html.HtmlStatistic;
import com.youzan.spiderman.html.n;
import com.youzan.spiderman.html.o;
import com.youzan.spiderman.utils.IOUtils;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.UriUtil;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CacheHandler {
    private Context a;
    private final HandlerCallback c;
    private final CacheStatistic d;
    private final n b = n.a();
    private final e e = e.a();
    private final b f = b.a();
    private final com.youzan.spiderman.c.a.a g = com.youzan.spiderman.c.a.a.a();
    private final com.youzan.spiderman.b.f h = com.youzan.spiderman.b.f.a();
    private List<CacheUrl> i = new LinkedList();

    public interface HandlerCallback {
        void onCacheStatistic(Map<String, String> map);

        void onHtmlStatistic(Map<String, String> map);
    }

    public CacheHandler(Context context, HandlerCallback handlerCallback) {
        this.a = context;
        this.c = handlerCallback;
        this.d = a(handlerCallback);
    }

    public HtmlResponse shouldInterceptHtml(Uri uri) {
        o htmlUrl = new o(uri);
        if (!this.f.a(htmlUrl)) {
            return null;
        }
        HtmlStatistic htmlStatistic = new HtmlStatistic(htmlUrl.a());
        HtmlResponse response = this.b.a(this.a, htmlUrl, htmlStatistic);
        if (htmlStatistic.isNeedRecord() && this.c != null) {
            this.c.onHtmlStatistic(htmlStatistic.getStatisticData());
            return response;
        }
        return response;
    }

    public HtmlResponse shouldInterceptHtml(String url) {
        o htmlUrl = new o(url);
        if (!this.f.a(htmlUrl)) {
            return null;
        }
        HtmlStatistic htmlStatistic = new HtmlStatistic(url);
        HtmlResponse response = this.b.a(this.a, htmlUrl, htmlStatistic);
        if (htmlStatistic.isNeedRecord() && this.c != null) {
            this.c.onHtmlStatistic(htmlStatistic.getStatisticData());
            return response;
        }
        return response;
    }

    private CacheStatistic a(final HandlerCallback handlerCallback) {
        return new CacheStatistic(new CacheStatistic.StatisticCallback() { // from class: com.youzan.spiderman.cache.CacheHandler.1
            @Override // com.youzan.spiderman.cache.CacheStatistic.StatisticCallback
            public void onStatistic(String url, Map<String, String> kvData) {
                if (handlerCallback != null) {
                    handlerCallback.onCacheStatistic(kvData);
                }
                if (CacheHandler.this.i != null && !CacheHandler.this.i.isEmpty()) {
                    com.youzan.spiderman.c.g.c uploadUrl = new com.youzan.spiderman.c.g.c(url, CacheHandler.this.i);
                    CacheHandler.this.i = new LinkedList();
                    com.youzan.spiderman.c.g.a.a().a(CacheHandler.this.a, uploadUrl);
                }
            }
        });
    }

    public ResourceResponse shouldInterceptRequest(Uri uri) {
        this.d.resetStatisticTimer();
        if (!this.g.c()) {
            return null;
        }
        CacheUrl cacheUrl = new CacheUrl(uri);
        if (!this.f.a(cacheUrl)) {
            return null;
        }
        String mimeType = UriUtil.buildMimeType(cacheUrl.getExtend());
        File cacheFile = this.e.a(cacheUrl);
        if (cacheFile != null) {
            this.d.addStatisticCount(1, true);
            this.h.a(cacheUrl, cacheFile);
            return a(mimeType, cacheFile);
        }
        this.d.addStatisticCount(1, false);
        this.i.add(cacheUrl);
        return a(mimeType, cacheUrl);
    }

    private ResourceResponse a(String mime, File file) {
        try {
            return new ResourceResponse(mime, AsyncHttpResponseHandler.DEFAULT_CHARSET, IOUtils.openFile(file));
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e("CacheHandler", "build web resource response exception: ", e);
            return null;
        }
    }

    private ResourceResponse a(String mimeType, CacheUrl cacheUrl) {
        return new ResourceResponse(mimeType, AsyncHttpResponseHandler.DEFAULT_CHARSET, new com.youzan.spiderman.d.a(this.a, cacheUrl));
    }

    public void resetStatistic() {
        this.d.reset();
    }

    public void parseStatisticTiming(String url) {
        this.d.parseStatisticTiming(url);
    }

    public void tryInjectJs(String curUrl, CacheStatistic.InjectJsCallback injectCB) {
        this.d.tryInjectJs(curUrl, injectCB);
    }
}
