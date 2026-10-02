package com.youzan.spiderman.html;

import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.OkHttpUtil;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: FetchHtmlRunner.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c {
    private o a;

    public c(o htmlUrl) {
        this.a = htmlUrl;
    }

    public HtmlResponse a() {
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("method", "GET");
        requestHeader.put("Host", this.a.b().getHost());
        requestHeader.put("Accept", "text/html");
        requestHeader.put(AsyncHttpClient.HEADER_ACCEPT_ENCODING, AsyncHttpClient.ENCODING_GZIP);
        requestHeader.put("Accept-Language", "zh-CN,zh;");
        b.a(this.a, requestHeader);
        l htmlRequestHeader = l.a(requestHeader);
        p httpResponse = OkHttpUtil.downloadHtml(htmlRequestHeader, this.a);
        if (httpResponse == null) {
            Logger.e("FetchHtmlRunner", "html response return null", new Object[0]);
            return null;
        }
        g htmlCacheWriter = new g(this.a);
        if (httpResponse.a()) {
            htmlCacheWriter.a();
            return null;
        }
        i htmlData = httpResponse.c();
        InputStream inputStream = httpResponse.a(htmlCacheWriter);
        byte[] content = b.a(inputStream);
        if (content != null) {
            return new HtmlResponse(httpResponse.b().a(), content, htmlData.c());
        }
        return null;
    }
}
