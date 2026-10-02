package com.alibaba.sdk.android.httpdns;

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
public class m implements Callable<String[]> {
    private int d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private long f68d = 0;

    public m(int i) {
        this.d = i;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0071 A[Catch: all -> 0x0152, TRY_LEAVE, TryCatch #7 {all -> 0x0152, blocks: (B:21:0x0063, B:23:0x0071), top: B:85:0x0063 }] */
    /* JADX WARN: Code duplicated, block: B:25:0x007c  */
    /* JADX WARN: Code duplicated, block: B:28:0x0084 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:29:0x0086 A[Catch: IOException -> 0x0138, TRY_LEAVE, TryCatch #0 {IOException -> 0x0138, blocks: (B:27:0x0081, B:29:0x0086), top: B:80:0x0081 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x00d5  */
    /* JADX WARN: Code duplicated, block: B:41:0x00df A[Catch: IOException -> 0x013e, TRY_LEAVE, TryCatch #10 {IOException -> 0x013e, blocks: (B:39:0x00da, B:41:0x00df), top: B:89:0x00da }] */
    /* JADX WARN: Code duplicated, block: B:57:0x012d A[Catch: IOException -> 0x0132, TRY_LEAVE, TryCatch #4 {IOException -> 0x0132, blocks: (B:55:0x0128, B:57:0x012d), top: B:82:0x0128 }] */
    /* JADX WARN: Code duplicated, block: B:80:0x0081 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x00da A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:99:? A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v12, types: [int] */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r2v22, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:78:0x0164 -> B:85:0x0063). Please report as a decompilation issue!!! */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public String[] call() throws Throwable {
        ?? r5;
        ?? r6;
        ?? r3;
        ?? r7;
        o oVarA;
        BufferedReader bufferedReader;
        InputStream inputStream;
        ?? r2;
        ?? r4 = 0;
         = 0;
         = 0;
         = 0;
        ?? r8 = 0;
        this.f68d = System.currentTimeMillis();
        try {
            String strF = o.a().f();
            if (strF != null) {
                ?? r9 = (HttpURLConnection) new URL(strF).openConnection();
                try {
                    r9.setConnectTimeout(15000);
                    r9.setReadTimeout(15000);
                    if (r9 instanceof HttpsURLConnection) {
                        ((HttpsURLConnection) r9).setHostnameVerifier(new HostnameVerifier() { // from class: com.alibaba.sdk.android.httpdns.m.1
                            @Override // javax.net.ssl.HostnameVerifier
                            public boolean verify(String str, SSLSession sSLSession) {
                                h.d("Https request, set hostnameVerifier");
                                return HttpsURLConnection.getDefaultHostnameVerifier().verify("203.107.1.1", sSLSession);
                            }
                        });
                    }
                    r5 = 200;
                    try {
                        try {
                            if (r9.getResponseCode() != 200) {
                                InputStream errorStream = r9.getErrorStream();
                                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(errorStream, HTTP.UTF_8));
                                try {
                                    StringBuilder sb = new StringBuilder();
                                    while (true) {
                                        String line = bufferedReader2.readLine();
                                        if (line == null) {
                                            break;
                                        }
                                        sb.append(line);
                                    }
                                    h.f("response code is " + r9.getResponseCode() + " expect 200. response body is " + sb.toString());
                                    f fVar = new f(r9.getResponseCode(), sb.toString());
                                    throw new g(fVar.getErrorCode(), fVar.a());
                                } catch (Exception e) {
                                    r8 = errorStream;
                                    r7 = r9;
                                    e = e;
                                    r3 = bufferedReader2;
                                }
                            } else {
                                inputStream = r9.getInputStream();
                                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, HTTP.UTF_8));
                                try {
                                    StringBuilder sb2 = new StringBuilder();
                                    while (true) {
                                        String line2 = bufferedReader.readLine();
                                        if (line2 == null) {
                                            break;
                                        }
                                        sb2.append(line2);
                                    }
                                    o.a().a(new p(sb2.toString()), System.currentTimeMillis() - this.f68d);
                                    r2 = r9;
                                } catch (Exception e2) {
                                    r8 = inputStream;
                                    r7 = r9;
                                    e = e2;
                                    r3 = bufferedReader;
                                    h.a(e);
                                    oVarA = o.a();
                                    oVarA.c(e);
                                    r9 = this.d;
                                    if (r9 > 0) {
                                        this.d--;
                                        call();
                                    }
                                    if (r7 != 0) {
                                        r7.disconnect();
                                    }
                                    if (r8 != 0) {
                                        try {
                                            r8.close();
                                            if (r3 != 0) {
                                                r3.close();
                                            }
                                        } catch (IOException e3) {
                                            h.a(e3);
                                        }
                                    } else if (r3 != 0) {
                                        r3.close();
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            r4 = r9;
                            th = th;
                            if (r4 != 0) {
                                r4.disconnect();
                            }
                            if (r6 != 0) {
                                try {
                                    r6.close();
                                } catch (IOException e4) {
                                    h.a(e4);
                                    throw th;
                                }
                            }
                            if (r5 == 0) {
                                throw th;
                            }
                            r5.close();
                            throw th;
                        }
                    } catch (Exception e5) {
                        r7 = r9;
                        e = e5;
                        r3 = r8;
                        r8 = oVarA;
                    } catch (Throwable th2) {
                        r5 = r8;
                        r4 = r9;
                        th = th2;
                        r6 = oVarA;
                        if (r4 != 0) {
                            r4.disconnect();
                        }
                        if (r6 != 0) {
                            r6.close();
                        }
                        if (r5 == 0) {
                            throw th;
                        }
                        r5.close();
                        throw th;
                    }
                } catch (Exception e6) {
                    r7 = r9;
                    e = e6;
                    r3 = 0;
                } catch (Throwable th3) {
                    r5 = 0;
                    r6 = 0;
                    r4 = r9;
                    th = th3;
                }
                try {
                    h.a(e);
                    oVarA = o.a();
                    oVarA.c(e);
                    r9 = this.d;
                    if (r9 > 0) {
                        this.d--;
                        call();
                    }
                    if (r7 != 0) {
                        r7.disconnect();
                    }
                    if (r8 != 0) {
                        r8.close();
                        if (r3 != 0) {
                            r3.close();
                        }
                    } else if (r3 != 0) {
                        r3.close();
                    }
                    return new String[0];
                } catch (Throwable th4) {
                    th = th4;
                    r6 = r8;
                    r4 = r7;
                    r5 = r3;
                    if (r4 != 0) {
                        r4.disconnect();
                    }
                    if (r6 != 0) {
                        r6.close();
                    }
                    if (r5 == 0) {
                        throw th;
                    }
                    r5.close();
                    throw th;
                }
            }
            bufferedReader = null;
            inputStream = null;
            r2 = 0;
            if (r2 != 0) {
                r2.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e7) {
                    h.a(e7);
                }
            } else if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (Exception e8) {
            e = e8;
            r3 = 0;
            r7 = 0;
        } catch (Throwable th5) {
            th = th5;
            r5 = 0;
            r6 = 0;
        }
        return new String[0];
    }
}
