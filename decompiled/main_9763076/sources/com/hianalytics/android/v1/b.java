package com.hianalytics.android.v1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class b {
    /* JADX WARN: Code duplicated, block: B:30:0x008d  */
    /* JADX WARN: Code duplicated, block: B:42:0x0088 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static boolean a(String str, byte[] bArr) throws Throwable {
        HttpURLConnection httpURLConnection;
        OutputStream outputStream = null;
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                com.hianalytics.android.b.a.a.h();
                httpURLConnection2.setRequestMethod(HttpPost.METHOD_NAME);
                httpURLConnection2.setConnectTimeout(5000);
                httpURLConnection2.setDoOutput(true);
                httpURLConnection2.setRequestProperty(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
                httpURLConnection2.setRequestProperty(HTTP.CONTENT_LEN, String.valueOf(bArr.length));
                outputStream = httpURLConnection2.getOutputStream();
                outputStream.write(bArr);
                outputStream.flush();
                int responseCode = httpURLConnection2.getResponseCode();
                com.hianalytics.android.b.a.a.h();
                boolean z = responseCode == 200;
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (httpURLConnection2 == null) {
                    return z;
                }
                httpURLConnection2.disconnect();
                com.hianalytics.android.b.a.a.h();
                return z;
            } catch (IOException e2) {
                httpURLConnection = httpURLConnection2;
                e = e2;
                try {
                    new StringBuilder("IOException:").append(e.getMessage());
                    com.hianalytics.android.b.a.a.h();
                    e.printStackTrace();
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                        com.hianalytics.android.b.a.a.h();
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                        com.hianalytics.android.b.a.a.h();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                httpURLConnection = httpURLConnection2;
                th = th2;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                    com.hianalytics.android.b.a.a.h();
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            httpURLConnection = null;
        } catch (Throwable th3) {
            th = th3;
            httpURLConnection = null;
        }
    }
}
