package com.huawei.hms.update.b;

import com.tencent.bugly.BuglyStrategy;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

/* JADX INFO: compiled from: HttpRequestHelper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b implements d {
    private HttpURLConnection a;
    private volatile int b = -1;

    @Override // com.huawei.hms.update.b.d
    public void a() {
        this.b = -1;
        if (this.a != null) {
            this.a.disconnect();
        }
    }

    @Override // com.huawei.hms.update.b.d
    public void b() {
        this.b = 1;
    }

    @Override // com.huawei.hms.update.b.d
    public int a(String str, InputStream inputStream, OutputStream outputStream) throws Throwable {
        InputStream inputStream2;
        Throwable th;
        OutputStream outputStream2;
        InputStream inputStream3 = null;
        try {
            a(str);
            this.a.setRequestMethod(HttpPost.METHOD_NAME);
            OutputStream outputStream3 = this.a.getOutputStream();
            try {
                a(inputStream, outputStream3);
                outputStream3.flush();
                int responseCode = this.a.getResponseCode();
                if (responseCode == 200) {
                    inputStream3 = this.a.getInputStream();
                    try {
                        a(new BufferedInputStream(inputStream3, 4096), outputStream);
                        outputStream.flush();
                    } catch (Throwable th2) {
                        outputStream2 = outputStream3;
                        inputStream2 = inputStream3;
                        th = th2;
                        com.huawei.hms.c.c.a(inputStream2);
                        com.huawei.hms.c.c.a(outputStream2);
                        throw th;
                    }
                }
                com.huawei.hms.c.c.a(inputStream3);
                com.huawei.hms.c.c.a(outputStream3);
                return responseCode;
            } catch (Throwable th3) {
                outputStream2 = outputStream3;
                inputStream2 = null;
                th = th3;
            }
        } catch (Throwable th4) {
            inputStream2 = null;
            th = th4;
            outputStream2 = null;
        }
    }

    @Override // com.huawei.hms.update.b.d
    public int a(String str, OutputStream outputStream) throws IOException, a {
        return a(str, outputStream, 0, 0);
    }

    @Override // com.huawei.hms.update.b.d
    public int a(String str, OutputStream outputStream, int i, int i2) throws Throwable {
        InputStream inputStream;
        Throwable th;
        InputStream inputStream2 = null;
        try {
            a(str);
            this.a.setRequestMethod(HttpGet.METHOD_NAME);
            if (i > 0) {
                this.a.addRequestProperty("Range", "bytes=" + i + "-" + i2);
            }
            int responseCode = this.a.getResponseCode();
            if ((i > 0 && responseCode == 206) || (i <= 0 && responseCode == 200)) {
                inputStream2 = this.a.getInputStream();
                try {
                    a(new BufferedInputStream(inputStream2, 4096), outputStream);
                    outputStream.flush();
                } catch (Throwable th2) {
                    inputStream = inputStream2;
                    th = th2;
                    com.huawei.hms.c.c.a(inputStream);
                    throw th;
                }
            }
            com.huawei.hms.c.c.a(inputStream2);
            return responseCode;
        } catch (Throwable th3) {
            inputStream = null;
            th = th3;
        }
    }

    private void a(String str) throws IOException {
        if (this.b == 0) {
            com.huawei.hms.support.log.a.d("HttpRequestHelper", "Not allowed to repeat open http(s) connection.");
        }
        this.a = (HttpURLConnection) new URL(str).openConnection();
        if (this.a instanceof HttpsURLConnection) {
            c.a((HttpsURLConnection) this.a);
        }
        this.a.setConnectTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
        this.a.setReadTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
        this.a.setDoInput(true);
        this.a.setDoOutput(true);
        this.a.setUseCaches(false);
        this.b = 0;
    }

    private void a(InputStream inputStream, OutputStream outputStream) throws IOException, a {
        byte[] bArr = new byte[4096];
        do {
            int i = inputStream.read(bArr);
            if (-1 != i) {
                outputStream.write(bArr, 0, i);
            } else {
                return;
            }
        } while (this.b != 1);
        throw new a("HTTP(s) request was canceled.");
    }
}
