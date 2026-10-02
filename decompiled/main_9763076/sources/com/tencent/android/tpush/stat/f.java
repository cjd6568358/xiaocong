package com.tencent.android.tpush.stat;

import android.content.Context;
import com.tencent.android.tpush.common.Constants;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    private static com.tencent.android.tpush.stat.a.f c = com.tencent.android.tpush.stat.a.e.b();
    private static volatile f d = null;
    private static Context e = null;
    DefaultHttpClient a;
    StringBuilder b = new StringBuilder(4096);
    private long f;

    private f(Context context) {
        this.a = null;
        this.f = 0L;
        try {
            e = context.getApplicationContext();
            this.f = System.currentTimeMillis() / 1000;
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, false);
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, Constants.ERRORCODE_UNKNOWN);
            HttpConnectionParams.setSoTimeout(basicHttpParams, Constants.ERRORCODE_UNKNOWN);
            this.a = new DefaultHttpClient(basicHttpParams);
            this.a.setKeepAliveStrategy(new g(this));
        } catch (Throwable th) {
            c.b(th);
        }
    }

    static void a(Context context) {
        e = context.getApplicationContext();
    }

    static Context a() {
        return e;
    }

    public static f b(Context context) {
        if (d == null) {
            synchronized (f.class) {
                if (d == null) {
                    d = new f(context);
                }
            }
        }
        return d;
    }

    private void a(JSONObject jSONObject) {
        try {
            if (!jSONObject.isNull("cfg")) {
                c.a(e, jSONObject.getJSONObject("cfg"));
            }
            if (!jSONObject.isNull("ncts")) {
                int i = jSONObject.getInt("ncts");
                int iCurrentTimeMillis = (int) (((long) i) - (System.currentTimeMillis() / 1000));
                if (c.b()) {
                    c.b("server time:" + i + ", diff time:" + iCurrentTimeMillis);
                }
                com.tencent.android.tpush.stat.a.e.l(e);
                com.tencent.android.tpush.stat.a.e.a(e, iCurrentTimeMillis);
            }
        } catch (Throwable th) {
            c.d(th);
        }
    }

    void a(List list, e eVar) {
        if (list != null && !list.isEmpty()) {
            int size = list.size();
            Throwable th = null;
            try {
                this.b.delete(0, this.b.length());
                this.b.append("[");
                for (int i = 0; i < size; i++) {
                    this.b.append(list.get(i).toString());
                    if (i != size - 1) {
                        this.b.append(",");
                    }
                }
                this.b.append("]");
                String string = this.b.toString();
                int length = string.length();
                String str = c.d() + "/?index=" + this.f;
                this.f++;
                if (c.b()) {
                    c.b("[" + str + "]Send request(eventsize:" + size + "," + length + "bytes), content:" + string);
                }
                HttpPost httpPost = new HttpPost(str);
                httpPost.addHeader("Accept-Encoding", "gzip");
                httpPost.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
                httpPost.removeHeaders("Cache-Control");
                HttpHost httpHostB = com.tencent.android.tpush.stat.a.e.b(e);
                httpPost.addHeader(HTTP.CONTENT_ENCODING, "rc4");
                if (httpHostB == null) {
                    this.a.getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
                } else {
                    if (c.b()) {
                        c.h("proxy:" + httpHostB.toHostString());
                    }
                    httpPost.addHeader("X-Content-Encoding", "rc4");
                    this.a.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, httpHostB);
                    httpPost.addHeader("X-Online-Host", c.d);
                    httpPost.addHeader("Accept", "*/*");
                    httpPost.addHeader(HTTP.CONTENT_TYPE, "json");
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(length);
                byte[] bytes = string.getBytes(HTTP.UTF_8);
                int length2 = bytes.length;
                if (length > 512) {
                    httpPost.removeHeaders(HTTP.CONTENT_ENCODING);
                    String str2 = "rc4,gzip";
                    httpPost.addHeader(HTTP.CONTENT_ENCODING, str2);
                    if (httpHostB != null) {
                        httpPost.removeHeaders("X-Content-Encoding");
                        httpPost.addHeader("X-Content-Encoding", str2);
                    }
                    byteArrayOutputStream.write(new byte[4]);
                    GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                    gZIPOutputStream.write(bytes);
                    gZIPOutputStream.close();
                    bytes = byteArrayOutputStream.toByteArray();
                    ByteBuffer.wrap(bytes, 0, 4).putInt(length2);
                    if (c.b()) {
                        c.h("before Gzip:" + length2 + " bytes, after Gzip:" + bytes.length + " bytes");
                    }
                }
                httpPost.setEntity(new ByteArrayEntity(com.tencent.android.tpush.stat.a.d.a(bytes)));
                HttpResponse httpResponseExecute = this.a.execute(httpPost);
                HttpEntity entity = httpResponseExecute.getEntity();
                int statusCode = httpResponseExecute.getStatusLine().getStatusCode();
                long contentLength = entity.getContentLength();
                if (c.b()) {
                    c.b("http recv response status code:" + statusCode + ", content length:" + contentLength);
                }
                if (contentLength <= 0) {
                    c.f("Server response no data.");
                    if (eVar != null) {
                        eVar.b();
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
                    Header firstHeader = httpResponseExecute.getFirstHeader(HTTP.CONTENT_ENCODING);
                    if (firstHeader != null) {
                        if (firstHeader.getValue().equalsIgnoreCase("gzip,rc4")) {
                            bArrB = com.tencent.android.tpush.stat.a.d.b(com.tencent.android.tpush.stat.a.e.a(bArrB));
                        } else if (firstHeader.getValue().equalsIgnoreCase("rc4,gzip")) {
                            bArrB = com.tencent.android.tpush.stat.a.e.a(com.tencent.android.tpush.stat.a.d.b(bArrB));
                        } else if (firstHeader.getValue().equalsIgnoreCase("gzip")) {
                            bArrB = com.tencent.android.tpush.stat.a.e.a(bArrB);
                        } else if (firstHeader.getValue().equalsIgnoreCase("rc4")) {
                            bArrB = com.tencent.android.tpush.stat.a.d.b(bArrB);
                        }
                    }
                    String str3 = new String(bArrB, HTTP.UTF_8);
                    if (c.b()) {
                        c.b("http get response data:" + str3);
                    }
                    JSONObject jSONObject = new JSONObject(str3);
                    if (statusCode == 200) {
                        a(jSONObject);
                        if (eVar != null) {
                            if (jSONObject.optInt("ret") == 0) {
                                eVar.a();
                            } else {
                                c.e("response error data.");
                                eVar.b();
                            }
                        }
                    } else {
                        c.e("Server response error code:" + statusCode + ", error:" + new String(bArrB, HTTP.UTF_8));
                        if (eVar != null) {
                            eVar.b();
                        }
                    }
                    content.close();
                } else {
                    EntityUtils.toString(entity);
                }
                byteArrayOutputStream.close();
                if (th != null) {
                    c.a(th);
                    if (eVar != null) {
                        try {
                            eVar.b();
                        } catch (Throwable th2) {
                            c.b(th2);
                        }
                    }
                    if (th instanceof OutOfMemoryError) {
                        System.gc();
                        this.b = null;
                        this.b = new StringBuilder(2048);
                    } else if ((th instanceof UnknownHostException) || (th instanceof SocketTimeoutException)) {
                    }
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    void b(List list, e eVar) {
        a(list, eVar);
    }

    public void a(com.tencent.android.tpush.stat.event.d dVar, e eVar) {
        b(Arrays.asList(dVar.d()), eVar);
    }
}
