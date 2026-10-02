package com.meizu.cloud.pushsdk.a.d;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e implements a {
    i a;

    public e(i iVar) {
        this.a = iVar;
    }

    @Override // com.meizu.cloud.pushsdk.a.d.a
    public k a() throws IOException {
        HttpURLConnection httpURLConnectionA = a(this.a);
        for (String str : this.a.d().b()) {
            String strA = this.a.a(str);
            com.meizu.cloud.pushsdk.a.a.a.b("current header name " + str + " value " + strA);
            httpURLConnectionA.addRequestProperty(str, strA);
        }
        a(httpURLConnectionA, this.a);
        return new k.a().a(httpURLConnectionA.getResponseCode()).a(this.a.d()).a(httpURLConnectionA.getResponseMessage()).a(this.a).a(a(httpURLConnectionA)).a();
    }

    private static l a(final HttpURLConnection httpURLConnection) throws IOException {
        if (!httpURLConnection.getDoInput()) {
            return null;
        }
        final com.meizu.cloud.pushsdk.a.h.c cVarA = com.meizu.cloud.pushsdk.a.h.f.a(com.meizu.cloud.pushsdk.a.h.f.a(a(httpURLConnection.getResponseCode()) ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream()));
        return new l() { // from class: com.meizu.cloud.pushsdk.a.d.e.1
            @Override // com.meizu.cloud.pushsdk.a.d.l
            public com.meizu.cloud.pushsdk.a.h.c a() {
                return cVarA;
            }
        };
    }

    protected static boolean a(int i) {
        return i >= 200 && i < 300;
    }

    private HttpURLConnection a(i iVar) throws IOException {
        String string = iVar.a().toString();
        HttpURLConnection httpURLConnectionA = a(new URL(string));
        httpURLConnectionA.setConnectTimeout(60000);
        httpURLConnectionA.setReadTimeout(60000);
        httpURLConnectionA.setUseCaches(false);
        httpURLConnectionA.setDoInput(true);
        if (iVar.f() && string.startsWith("https://push.statics")) {
            ((HttpsURLConnection) httpURLConnectionA).setSSLSocketFactory(com.meizu.cloud.pushsdk.platform.a.a());
            ((HttpsURLConnection) httpURLConnectionA).setHostnameVerifier(com.meizu.cloud.pushsdk.platform.a.b());
        }
        return httpURLConnectionA;
    }

    protected HttpURLConnection a(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        return httpURLConnection;
    }

    static void a(HttpURLConnection httpURLConnection, i iVar) throws IOException {
        switch (iVar.c()) {
            case 0:
                httpURLConnection.setRequestMethod(HttpGet.METHOD_NAME);
                return;
            case 1:
                httpURLConnection.setRequestMethod(HttpPost.METHOD_NAME);
                b(httpURLConnection, iVar);
                return;
            case 2:
                httpURLConnection.setRequestMethod(HttpPut.METHOD_NAME);
                b(httpURLConnection, iVar);
                return;
            case 3:
                httpURLConnection.setRequestMethod(HttpDelete.METHOD_NAME);
                return;
            case 4:
                httpURLConnection.setRequestMethod(HttpHead.METHOD_NAME);
                return;
            case 5:
                httpURLConnection.setRequestMethod("PATCH");
                b(httpURLConnection, iVar);
                return;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    private static void b(HttpURLConnection httpURLConnection, i iVar) throws IOException {
        j jVarE = iVar.e();
        if (jVarE != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty(HTTP.CONTENT_TYPE, jVarE.a().toString());
            com.meizu.cloud.pushsdk.a.h.b bVarA = com.meizu.cloud.pushsdk.a.h.f.a(com.meizu.cloud.pushsdk.a.h.f.a(httpURLConnection.getOutputStream()));
            jVarE.a(bVarA);
            bVarA.close();
        }
    }
}
