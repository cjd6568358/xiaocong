package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Build;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class l implements Callable<String[]> {
    private static Context a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private n f65a;
    private String hostName;
    private static c hostManager = c.a();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static final Object f64a = new Object();
    private int d = 1;
    private String e = null;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private String[] f67e = e.d;
    private boolean c = false;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private long f66d = 0;

    l(String str, n nVar) {
        this.hostName = str;
        this.f65a = nVar;
    }

    static void setContext(Context context) {
        a = context;
    }

    public void a(int i) {
        if (i >= 0) {
            this.d = i;
        }
    }

    /* JADX INFO: Removed unreachable split cross block B:121:0x0039 */
    /* JADX WARN: Code duplicated, block: B:108:0x016c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:113:0x0211 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:127:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:25:0x0083 A[Catch: IOException -> 0x02ae, TRY_LEAVE, TryCatch #7 {IOException -> 0x02ae, blocks: (B:23:0x007e, B:25:0x0083), top: B:110:0x007e }] */
    /* JADX WARN: Code duplicated, block: B:50:0x015c A[Catch: all -> 0x02bc, TRY_LEAVE, TryCatch #12 {all -> 0x02bc, blocks: (B:48:0x014e, B:50:0x015c, B:90:0x02b4), top: B:115:0x014e }] */
    /* JADX WARN: Code duplicated, block: B:52:0x0167  */
    /* JADX WARN: Code duplicated, block: B:55:0x016f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:56:0x0171 A[Catch: IOException -> 0x0176, TRY_LEAVE, TryCatch #1 {IOException -> 0x0176, blocks: (B:54:0x016c, B:56:0x0171), top: B:108:0x016c }] */
    /* JADX WARN: Code duplicated, block: B:67:0x020c  */
    /* JADX WARN: Code duplicated, block: B:71:0x0216 A[Catch: IOException -> 0x02c1, TRY_LEAVE, TryCatch #11 {IOException -> 0x02c1, blocks: (B:69:0x0211, B:71:0x0216), top: B:113:0x0211 }] */
    /* JADX WARN: Code duplicated, block: B:90:0x02b4 A[Catch: all -> 0x02bc, TRY_ENTER, TRY_LEAVE, TryCatch #12 {all -> 0x02bc, blocks: (B:48:0x014e, B:50:0x015c, B:90:0x02b4), top: B:115:0x014e }] */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x02e6: MOVE (r4 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:107:0x02e3 */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public String[] call() throws Throwable {
        BufferedReader bufferedReader;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        String str;
        HttpURLConnection httpURLConnection2;
        InputStream inputStream2;
        InputStream inputStream3 = null;
        BufferedReader bufferedReader2 = null;
        bufferedReader = null;
        inputStream3 = null;
        bufferedReader = null;
        BufferedReader bufferedReader3 = null;
        this.f66d = System.currentTimeMillis();
        if (!this.c) {
            synchronized (f64a) {
                if (hostManager.m43a(this.hostName)) {
                    h.d("host:" + this.hostName + " is already resolving");
                    return this.f67e;
                }
                hostManager.m41a(this.hostName);
                this.c = true;
            }
        }
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                TrafficStats.setThreadStatsTag(40965);
            }
            this.e = s.a(this.f65a);
            if (this.e == null) {
                h.d("serverIp is null, give up query for hostname:" + this.hostName);
                inputStream = null;
                httpURLConnection2 = null;
            } else {
                if (a.a()) {
                    String timestamp = a.getTimestamp();
                    str = e.PROTOCOL + this.e + ":" + e.b + "/" + e.f61a + "/sign_d?host=" + this.hostName + "&sdk=android_1.1.9&t=" + timestamp + "&s=" + a.a(this.hostName, timestamp);
                } else {
                    str = e.PROTOCOL + this.e + ":" + e.b + "/" + e.f61a + "/d?host=" + this.hostName + "&sdk=android_1.1.9";
                }
                httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
                try {
                    httpURLConnection2.setConnectTimeout(e.a);
                    httpURLConnection2.setReadTimeout(e.a);
                    if (httpURLConnection2 instanceof HttpsURLConnection) {
                        ((HttpsURLConnection) httpURLConnection2).setHostnameVerifier(new HostnameVerifier() { // from class: com.alibaba.sdk.android.httpdns.l.1
                            @Override // javax.net.ssl.HostnameVerifier
                            public boolean verify(String str2, SSLSession sSLSession) {
                                h.d("Https request, set hostnameVerifier");
                                return HttpsURLConnection.getDefaultHostnameVerifier().verify("203.107.1.1", sSLSession);
                            }
                        });
                    }
                    try {
                        if (httpURLConnection2.getResponseCode() != 200) {
                            inputStream = httpURLConnection2.getErrorStream();
                            BufferedReader bufferedReader4 = new BufferedReader(new InputStreamReader(inputStream, HTTP.UTF_8));
                            try {
                                StringBuilder sb = new StringBuilder();
                                while (true) {
                                    String line = bufferedReader4.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    sb.append(line);
                                }
                                h.f("response code is " + httpURLConnection2.getResponseCode() + " expect 200. response body is " + sb.toString());
                                throw new g(httpURLConnection2.getResponseCode(), new f(httpURLConnection2.getResponseCode(), sb.toString()).a());
                            } catch (Throwable th) {
                                inputStream3 = inputStream;
                                httpURLConnection = httpURLConnection2;
                                th = th;
                                bufferedReader = bufferedReader4;
                                try {
                                    h.a(th);
                                    s.a(this.hostName, this.e, th);
                                    if (this.d > 0) {
                                        this.d--;
                                        call();
                                    } else {
                                        s.reportHttpDnsSuccess(this.hostName, 0);
                                    }
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    if (inputStream3 != null) {
                                        try {
                                            inputStream3.close();
                                            if (bufferedReader != null) {
                                                bufferedReader.close();
                                            }
                                        } catch (IOException e) {
                                            h.a(e);
                                        }
                                    } else if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                    hostManager.b(this.hostName);
                                    return this.f67e;
                                } catch (Throwable th2) {
                                    th = th2;
                                    inputStream = inputStream3;
                                    bufferedReader3 = bufferedReader;
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    if (bufferedReader3 == null) {
                                        throw th;
                                    }
                                    bufferedReader3.close();
                                    throw th;
                                }
                            }
                        }
                        inputStream = httpURLConnection2.getInputStream();
                        BufferedReader bufferedReader5 = new BufferedReader(new InputStreamReader(inputStream, HTTP.UTF_8));
                        try {
                            StringBuilder sb2 = new StringBuilder();
                            while (true) {
                                String line2 = bufferedReader5.readLine();
                                if (line2 == null) {
                                    break;
                                }
                                sb2.append(line2);
                            }
                            h.d("resolve host: " + this.hostName + ", return: " + sb2.toString());
                            d dVar = new d(sb2.toString());
                            if (hostManager.m37a() >= 100) {
                                throw new Exception("the total number of hosts is exceed 100");
                            }
                            hostManager.m42a(this.hostName, dVar);
                            s.a(this.hostName, this.e, System.currentTimeMillis() - this.f66d);
                            hostManager.b(this.hostName);
                            this.f67e = dVar.m46a();
                            bufferedReader2 = bufferedReader5;
                        } catch (Throwable th3) {
                            inputStream3 = inputStream;
                            httpURLConnection = httpURLConnection2;
                            th = th3;
                            bufferedReader = bufferedReader5;
                            h.a(th);
                            s.a(this.hostName, this.e, th);
                            if (this.d > 0) {
                                this.d--;
                                call();
                            } else {
                                s.reportHttpDnsSuccess(this.hostName, 0);
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            if (inputStream3 != null) {
                                inputStream3.close();
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                            } else if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                        }
                    } catch (Throwable th4) {
                        httpURLConnection = httpURLConnection2;
                        th = th4;
                    }
                } catch (Throwable th5) {
                    inputStream = null;
                    httpURLConnection = httpURLConnection2;
                    th = th5;
                }
            }
            if (httpURLConnection2 != null) {
                httpURLConnection2.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                } catch (IOException e2) {
                    h.a(e2);
                }
            } else if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
        } catch (Throwable th6) {
            th = th6;
            bufferedReader = null;
            httpURLConnection = null;
        }
        hostManager.b(this.hostName);
        return this.f67e;
    }
}
