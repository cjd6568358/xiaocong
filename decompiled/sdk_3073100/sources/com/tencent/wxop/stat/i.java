package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.common.StatLogger;
import com.xiaocong.smarthome.network.constant.NetworkConstant;
import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class i {
    private static StatLogger d = com.tencent.wxop.stat.common.l.b();
    private static i e = null;
    private static Context f = null;
    DefaultHttpClient a;
    com.tencent.wxop.stat.common.e b;
    StringBuilder c = new StringBuilder(4096);
    private long g;

    private i(Context context) {
        this.a = null;
        this.b = null;
        this.g = 0L;
        try {
            f = context.getApplicationContext();
            this.g = System.currentTimeMillis() / 1000;
            this.b = new com.tencent.wxop.stat.common.e();
            if (StatConfig.isDebugEnable()) {
                try {
                    Logger.getLogger("org.apache.http.wire").setLevel(Level.FINER);
                    Logger.getLogger("org.apache.http.headers").setLevel(Level.FINER);
                    System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
                    System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
                    System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
                    System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
                    System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");
                } catch (Throwable th) {
                }
            }
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, false);
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, NetworkConstant.HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(basicHttpParams, NetworkConstant.HTTP_TIMEOUT);
            this.a = new DefaultHttpClient(basicHttpParams);
            this.a.setKeepAliveStrategy(new j(this));
        } catch (Throwable th2) {
            d.e(th2);
        }
    }

    static Context a() {
        return f;
    }

    static void a(Context context) {
        f = context.getApplicationContext();
    }

    private void a(JSONObject jSONObject) {
        try {
            String strOptString = jSONObject.optString("mid");
            if (com.tencent.a.a.a.a.h.c(strOptString)) {
                if (StatConfig.isDebugEnable()) {
                    d.i("update mid:" + strOptString);
                }
                com.tencent.a.a.a.a.g.C(f).a(strOptString);
            }
            if (!jSONObject.isNull("cfg")) {
                StatConfig.a(f, jSONObject.getJSONObject("cfg"));
            }
            if (jSONObject.isNull("ncts")) {
                return;
            }
            int i = jSONObject.getInt("ncts");
            int iCurrentTimeMillis = (int) (((long) i) - (System.currentTimeMillis() / 1000));
            if (StatConfig.isDebugEnable()) {
                d.i("server time:" + i + ", diff time:" + iCurrentTimeMillis);
            }
            com.tencent.wxop.stat.common.l.x(f);
            com.tencent.wxop.stat.common.l.a(f, iCurrentTimeMillis);
        } catch (Throwable th) {
            d.w(th);
        }
    }

    static i b(Context context) {
        if (e == null) {
            synchronized (i.class) {
                if (e == null) {
                    e = new i(context);
                }
            }
        }
        return e;
    }

    void a(com.tencent.wxop.stat.event.e eVar, h hVar) {
        b(Arrays.asList(eVar.g()), hVar);
    }

    void a(List<?> list, h hVar) {
        if (list == null || list.isEmpty()) {
            return;
        }
        int size = list.size();
        list.get(0);
        try {
            this.c.delete(0, this.c.length());
            this.c.append("[");
            for (int i = 0; i < size; i++) {
                this.c.append(list.get(i).toString());
                if (i != size - 1) {
                    this.c.append(",");
                }
            }
            this.c.append("]");
            String string = this.c.toString();
            int length = string.length();
            String str = StatConfig.getStatReportUrl() + "/?index=" + this.g;
            this.g++;
            if (StatConfig.isDebugEnable()) {
                d.i("[" + str + "]Send request(" + length + "bytes), content:" + string);
            }
            HttpPost httpPost = new HttpPost(str);
            httpPost.addHeader(AsyncHttpClient.HEADER_ACCEPT_ENCODING, AsyncHttpClient.ENCODING_GZIP);
            httpPost.setHeader("Connection", "Keep-Alive");
            httpPost.removeHeaders("Cache-Control");
            HttpHost httpHostA = a.a(f).a();
            httpPost.addHeader("Content-Encoding", "rc4");
            if (httpHostA == null) {
                this.a.getParams().removeParameter("http.route.default-proxy");
            } else {
                if (StatConfig.isDebugEnable()) {
                    d.d("proxy:" + httpHostA.toHostString());
                }
                httpPost.addHeader("X-Content-Encoding", "rc4");
                this.a.getParams().setParameter("http.route.default-proxy", httpHostA);
                httpPost.addHeader("X-Online-Host", StatConfig.k);
                httpPost.addHeader("Accept", "*/*");
                httpPost.addHeader("Content-Type", "json");
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(length);
            byte[] bytes = string.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
            int length2 = bytes.length;
            if (length > StatConfig.o) {
                httpPost.removeHeaders("Content-Encoding");
                String str2 = "rc4,gzip";
                httpPost.addHeader("Content-Encoding", str2);
                if (httpHostA != null) {
                    httpPost.removeHeaders("X-Content-Encoding");
                    httpPost.addHeader("X-Content-Encoding", str2);
                }
                byteArrayOutputStream.write(new byte[4]);
                GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                gZIPOutputStream.write(bytes);
                gZIPOutputStream.close();
                bytes = byteArrayOutputStream.toByteArray();
                ByteBuffer.wrap(bytes, 0, 4).putInt(length2);
                if (StatConfig.isDebugEnable()) {
                    d.d("before Gzip:" + length2 + " bytes, after Gzip:" + bytes.length + " bytes");
                }
            }
            httpPost.setEntity(new ByteArrayEntity(com.tencent.wxop.stat.common.f.a(bytes)));
            HttpResponse httpResponseExecute = this.a.execute(httpPost);
            HttpEntity entity = httpResponseExecute.getEntity();
            int statusCode = httpResponseExecute.getStatusLine().getStatusCode();
            long contentLength = entity.getContentLength();
            if (StatConfig.isDebugEnable()) {
                d.i("http recv response status code:" + statusCode + ", content length:" + contentLength);
            }
            if (contentLength <= 0) {
                d.e("Server response no data.");
                if (hVar != null) {
                    hVar.b();
                }
                EntityUtils.toString(entity);
                return;
            }
            if (contentLength > 0) {
                InputStream content = entity.getContent();
                DataInputStream dataInputStream = new DataInputStream(content);
                byte[] bArrB = new byte[(int) entity.getContentLength()];
                dataInputStream.readFully(bArrB);
                content.close();
                dataInputStream.close();
                Header firstHeader = httpResponseExecute.getFirstHeader("Content-Encoding");
                if (firstHeader != null) {
                    if (firstHeader.getValue().equalsIgnoreCase("gzip,rc4")) {
                        bArrB = com.tencent.wxop.stat.common.f.b(com.tencent.wxop.stat.common.l.a(bArrB));
                    } else if (firstHeader.getValue().equalsIgnoreCase("rc4,gzip")) {
                        bArrB = com.tencent.wxop.stat.common.l.a(com.tencent.wxop.stat.common.f.b(bArrB));
                    } else if (firstHeader.getValue().equalsIgnoreCase(AsyncHttpClient.ENCODING_GZIP)) {
                        bArrB = com.tencent.wxop.stat.common.l.a(bArrB);
                    } else if (firstHeader.getValue().equalsIgnoreCase("rc4")) {
                        bArrB = com.tencent.wxop.stat.common.f.b(bArrB);
                    }
                }
                String str3 = new String(bArrB, AsyncHttpResponseHandler.DEFAULT_CHARSET);
                if (StatConfig.isDebugEnable()) {
                    d.i("http get response data:" + str3);
                }
                JSONObject jSONObject = new JSONObject(str3);
                if (statusCode == 200) {
                    a(jSONObject);
                    if (hVar != null) {
                        if (jSONObject.optInt("ret") == 0) {
                            hVar.a();
                        } else {
                            d.error("response error data.");
                            hVar.b();
                        }
                    }
                } else {
                    d.error("Server response error code:" + statusCode + ", error:" + new String(bArrB, AsyncHttpResponseHandler.DEFAULT_CHARSET));
                    if (hVar != null) {
                        hVar.b();
                    }
                }
                content.close();
            } else {
                EntityUtils.toString(entity);
            }
            byteArrayOutputStream.close();
            th = null;
            if (th != null) {
                d.error(th);
                if (hVar != null) {
                    try {
                        hVar.b();
                    } catch (Throwable th) {
                        d.e(th);
                    }
                }
                if (th instanceof OutOfMemoryError) {
                    System.gc();
                    this.c = null;
                    this.c = new StringBuilder(2048);
                }
                a.a(f).d();
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    void b(List<?> list, h hVar) {
        if (this.b != null) {
            this.b.a(new k(this, list, hVar));
        }
    }
}
