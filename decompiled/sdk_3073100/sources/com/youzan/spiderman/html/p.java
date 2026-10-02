package com.youzan.spiderman.html;

import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.OkHttpUtil;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: HttpResponse.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class p {
    private o a;
    private l b;
    private Headers c;
    private Response d;
    private ResponseBody e;
    private i f;

    public p(o htmlUrl, Headers headers, Response response) {
        this.a = htmlUrl;
        this.b = l.b(headers.toMultimap());
        this.c = headers;
        this.d = response;
        this.e = this.d.body();
        Charset charset = OkHttpUtil.getContentCharset(this.e);
        this.f = new i(System.currentTimeMillis(), this.a.c(), this.a.a(), null, charset.name());
    }

    public boolean a() {
        return this.d.isRedirect();
    }

    public l b() {
        return this.b;
    }

    public i c() {
        return this.f;
    }

    public m a(g htmlCacheWriter) {
        InputStream byteStream;
        InputStream inputStream = null;
        if (this.e == null || (byteStream = this.e.byteStream()) == null) {
            return null;
        }
        String contentEncoding = this.c.get("Content-Encoding");
        if (AsyncHttpClient.ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
            try {
                InputStream inputStream2 = new BufferedInputStream(new GZIPInputStream(byteStream));
                inputStream = inputStream2;
            } catch (IOException e) {
                Logger.e("HttpResponse", e);
            }
        } else {
            inputStream = new BufferedInputStream(byteStream);
        }
        if (inputStream != null) {
            return new m(this.b, this.f, inputStream, htmlCacheWriter);
        }
        return null;
    }
}
