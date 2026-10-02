package com.tencent.mid.a;

import com.tencent.android.tpush.common.Constants;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.mid.util.Util;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private HttpHost a;
    private DefaultHttpClient b;
    private String c;
    private Map<String, String> d;
    private com.tencent.mid.util.f e;
    private int f = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;

    public b(String str, Map<String, String> map) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.e = Util.getLogger();
        this.a = Util.getHttpProxy();
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, this.f);
        HttpConnectionParams.setSoTimeout(basicHttpParams, this.f);
        this.b = new DefaultHttpClient(basicHttpParams);
        this.e.b("proxy==" + (this.a == null ? "null" : this.a.getHostName()));
        if (this.a != null) {
            this.b.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, this.a);
        }
        if (this.a != null && this.a.getHostName().equals("10.0.0.200")) {
            this.b.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("ctwap@mycdma.cn", "vnet.mobi"));
        }
        Logger.getLogger("org.apache.http.wire").setLevel(Level.FINEST);
        Logger.getLogger("org.apache.http.headers").setLevel(Level.FINEST);
        System.setProperty(LogFactoryImpl.LOG_PROPERTY, "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");
        this.b.setKeepAliveStrategy(new c(this));
        this.c = str;
        this.d = map;
    }

    private String b() {
        StringBuilder sb = new StringBuilder();
        if (this.d != null && this.d.size() != 0) {
            int i = 0;
            for (Map.Entry<String, String> entry : this.d.entrySet()) {
                sb.append(i == 0 ? "?" : "&");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                i++;
            }
        }
        return sb.toString();
    }

    public e a(String str, byte[] bArr, String str2, int i) throws Exception {
        String strA = a(str);
        this.e.b("[" + strA + "]Send request(" + bArr.length + "bytes):" + bArr);
        HttpPost httpPost = new HttpPost(strA);
        httpPost.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        httpPost.removeHeaders("Cache-Control");
        httpPost.removeHeaders(HTTP.USER_AGENT);
        if (this.a != null) {
            httpPost.addHeader("X-Online-Host", this.c);
            httpPost.addHeader("Accept", "*/*");
            httpPost.addHeader(HTTP.CONTENT_TYPE, "json");
        } else {
            this.b.getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
        }
        if (this.a == null) {
            httpPost.addHeader(HTTP.CONTENT_ENCODING, str2);
        } else {
            httpPost.addHeader("X-Content-Encoding", str2);
        }
        httpPost.setEntity(new ByteArrayEntity(bArr));
        HttpResponse httpResponseExecute = this.b.execute(httpPost);
        HttpEntity entity = httpResponseExecute.getEntity();
        int statusCode = httpResponseExecute.getStatusLine().getStatusCode();
        this.e.b("recv response status code:" + statusCode + ", content length:" + entity.getContentLength());
        byte[] byteArray = EntityUtils.toByteArray(entity);
        String str3 = Constants.MAIN_VERSION_TAG;
        Header firstHeader = httpResponseExecute.getFirstHeader(HTTP.CONTENT_ENCODING);
        if (firstHeader != null) {
            if (firstHeader.getValue().toUpperCase().contains("AES")) {
                str3 = new String(d.a(d.a()).a(i).b(byteArray), HTTP.UTF_8);
            }
            if (firstHeader.getValue().toUpperCase().contains("RSA")) {
                str3 = com.tencent.mid.util.h.b(byteArray);
            }
            if (firstHeader.getValue().toUpperCase().contains("IDENTITY")) {
                str3 = new String(byteArray, HTTP.UTF_8);
            }
        }
        this.e.b("recv response status code:" + statusCode + ", content :" + str3);
        return new e(statusCode, str3);
    }

    public String a(String str) {
        return this.c + str + b();
    }

    public void a() {
        if (this.b != null) {
            this.b.getConnectionManager().shutdown();
            this.b = null;
            this.c = null;
            this.d = null;
            this.a = null;
        }
    }
}
