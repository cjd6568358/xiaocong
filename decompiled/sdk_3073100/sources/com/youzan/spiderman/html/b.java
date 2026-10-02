package com.youzan.spiderman.html;

import com.youzan.spiderman.cache.SpiderCacheCallback;
import com.youzan.spiderman.cache.SpiderMan;
import com.youzan.spiderman.utils.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/* JADX INFO: compiled from: FetchHelper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    public static void a(o htmlUrl, Map<String, String> headerMap) {
        SpiderCacheCallback spiderCacheCallback = SpiderMan.getInstance().getSpiderCacheCallback();
        if (spiderCacheCallback != null) {
            spiderCacheCallback.onCustomRequestHeader(htmlUrl.a(), headerMap);
        } else {
            Logger.e("FetchHelper", "SpiderCacheCallback should be offered to custom html request header", new Object[0]);
        }
    }

    public static byte[] a(InputStream inputStream) {
        byte[] content;
        byte[] buf = new byte[4096];
        try {
            try {
                if (inputStream instanceof m) {
                    while (inputStream.read(buf) != -1) {
                    }
                    content = ((m) inputStream).a();
                } else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        int n = inputStream.read(buf);
                        if (n == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(buf, 0, n);
                    }
                    content = byteArrayOutputStream.toByteArray();
                }
                try {
                    inputStream.close();
                    return content;
                } catch (IOException e) {
                    Logger.e("FetchHelper", e);
                    return content;
                }
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    Logger.e("FetchHelper", e2);
                }
                throw th;
            }
        } catch (IOException e3) {
            Logger.e("FetchHelper", e3);
            try {
                inputStream.close();
            } catch (IOException e4) {
                Logger.e("FetchHelper", e4);
            }
            return null;
        }
    }
}
