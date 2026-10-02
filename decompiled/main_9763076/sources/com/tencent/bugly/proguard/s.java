package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import com.tencent.android.tpush.common.Constants;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.apache.http.client.methods.HttpPost;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class s {
    private static s b;
    public Map<String, String> a = null;
    private Context c;

    private s(Context context) {
        this.c = context;
    }

    public static s a(Context context) {
        if (b == null) {
            b = new s(context);
        }
        return b;
    }

    /* JADX WARN: Code duplicated, block: B:74:0x0178 A[Catch: all -> 0x018a, TRY_LEAVE, TryCatch #5 {all -> 0x018a, blocks: (B:22:0x009f, B:24:0x00a7, B:27:0x00b7, B:30:0x00c2, B:47:0x00e4, B:49:0x00ec, B:58:0x0117, B:60:0x0130, B:63:0x0152, B:72:0x0172, B:74:0x0178), top: B:107:0x009f }] */
    public final byte[] a(String str, byte[] bArr, v vVar, Map<String, String> map) {
        int i;
        int i2;
        if (str == null) {
            x.e("Failed for no URL.", new Object[0]);
            return null;
        }
        int i3 = 0;
        int i4 = 0;
        long length = bArr == null ? 0L : bArr.length;
        x.c("request: %s, send: %d (pid=%d | tid=%d)", str, Long.valueOf(length), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        boolean z = false;
        String str2 = str;
        while (i3 <= 0 && i4 <= 0) {
            if (z) {
                z = false;
            } else {
                i3++;
                if (i3 > 1) {
                    x.c("try time: " + i3, new Object[0]);
                    SystemClock.sleep(((long) new Random(System.currentTimeMillis()).nextInt(Constants.ERRORCODE_UNKNOWN)) + 10000);
                }
            }
            String strF = com.tencent.bugly.crashreport.common.info.b.f(this.c);
            if (strF == null) {
                x.d("Failed to request for network not avail", new Object[0]);
            } else {
                vVar.a(length);
                HttpURLConnection httpURLConnectionA = a(str2, bArr, strF, map);
                if (httpURLConnectionA != null) {
                    try {
                        try {
                            int responseCode = httpURLConnectionA.getResponseCode();
                            if (responseCode == 200) {
                                this.a = a(httpURLConnectionA);
                                byte[] bArrB = b(httpURLConnectionA);
                                vVar.b(bArrB == null ? 0L : bArrB.length);
                                try {
                                    httpURLConnectionA.disconnect();
                                } catch (Throwable th) {
                                    if (!x.a(th)) {
                                        th.printStackTrace();
                                    }
                                }
                                return bArrB;
                            }
                            if (responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307) {
                                try {
                                    String headerField = httpURLConnectionA.getHeaderField("Location");
                                    if (headerField == null) {
                                        x.e("Failed to redirect: %d" + responseCode, new Object[0]);
                                        return null;
                                    }
                                    int i5 = i4 + 1;
                                    try {
                                        x.c("redirect code: %d ,to:%s", Integer.valueOf(responseCode), headerField);
                                        z = true;
                                        str2 = headerField;
                                        i2 = i5;
                                        i = 0;
                                    } catch (IOException e) {
                                        str2 = headerField;
                                        e = e;
                                        z = true;
                                        i2 = i5;
                                        i = 0;
                                    }
                                } catch (IOException e2) {
                                    z = true;
                                    e = e2;
                                    int i6 = i4;
                                    i = i3;
                                    i2 = i6;
                                }
                                if (!x.a(e)) {
                                    e.printStackTrace();
                                }
                                try {
                                    httpURLConnectionA.disconnect();
                                } catch (Throwable th2) {
                                    if (!x.a(th2)) {
                                        th2.printStackTrace();
                                    }
                                }
                            } else {
                                int i7 = i4;
                                i = i3;
                                i2 = i7;
                            }
                            try {
                                x.d("response code " + responseCode, new Object[0]);
                                long contentLength = httpURLConnectionA.getContentLength();
                                if (contentLength < 0) {
                                    contentLength = 0;
                                }
                                vVar.b(contentLength);
                                try {
                                    httpURLConnectionA.disconnect();
                                } catch (Throwable th3) {
                                    if (!x.a(th3)) {
                                        th3.printStackTrace();
                                    }
                                }
                            } catch (IOException e3) {
                                e = e3;
                                if (!x.a(e)) {
                                    e.printStackTrace();
                                }
                                httpURLConnectionA.disconnect();
                            }
                        } finally {
                            try {
                                httpURLConnectionA.disconnect();
                            } catch (Throwable th4) {
                                if (!x.a(th4)) {
                                    th4.printStackTrace();
                                }
                            }
                        }
                    } catch (IOException e4) {
                        e = e4;
                        int i8 = i4;
                        i = i3;
                        i2 = i8;
                    }
                } else {
                    x.c("Failed to execute post.", new Object[0]);
                    vVar.b(0L);
                    int i9 = i4;
                    i = i3;
                    i2 = i9;
                }
                int i10 = i2;
                i3 = i;
                i4 = i10;
            }
        }
        return null;
    }

    private static Map<String, String> a(HttpURLConnection httpURLConnection) {
        HashMap map = new HashMap();
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null || headerFields.size() == 0) {
            return null;
        }
        for (String str : headerFields.keySet()) {
            List<String> list = headerFields.get(str);
            if (list.size() > 0) {
                map.put(str, list.get(0));
            }
        }
        return map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v6, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    private static byte[] b(HttpURLConnection httpURLConnection) throws Throwable {
        BufferedInputStream bufferedInputStream;
        byte[] byteArray = 0;
        byteArray = 0;
        byteArray = 0;
        byteArray = 0;
        try {
            if (httpURLConnection != null) {
                try {
                    bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                        while (true) {
                            int i = bufferedInputStream.read(bArr);
                            if (i <= 0) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, i);
                        }
                        byteArrayOutputStream.flush();
                        byteArray = byteArrayOutputStream.toByteArray();
                        try {
                            bufferedInputStream.close();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (!x.a(th)) {
                            th.printStackTrace();
                        }
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (Throwable th3) {
                                th3.printStackTrace();
                            }
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    bufferedInputStream = null;
                }
            }
            return byteArray;
        } catch (Throwable th5) {
            th = th5;
        }
    }

    private HttpURLConnection a(String str, byte[] bArr, String str2, Map<String, String> map) {
        if (str == null) {
            x.e("destUrl is null.", new Object[0]);
            return null;
        }
        HttpURLConnection httpURLConnectionA = a(str2, str);
        if (httpURLConnectionA == null) {
            x.e("Failed to get HttpURLConnection object.", new Object[0]);
            return null;
        }
        try {
            httpURLConnectionA.setRequestProperty("wup_version", "3.0");
            if (map != null && map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    httpURLConnectionA.setRequestProperty(entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8"));
                }
            }
            httpURLConnectionA.setRequestProperty("A37", URLEncoder.encode(str2, "utf-8"));
            httpURLConnectionA.setRequestProperty("A38", URLEncoder.encode(str2, "utf-8"));
            OutputStream outputStream = httpURLConnectionA.getOutputStream();
            if (bArr == null) {
                outputStream.write(0);
            } else {
                outputStream.write(bArr);
            }
            return httpURLConnectionA;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            x.e("Failed to upload, please check your network.", new Object[0]);
            return null;
        }
    }

    private static HttpURLConnection a(String str, String str2) {
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(str2);
            if (str != null && str.toLowerCase(Locale.US).contains("wap")) {
                httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort")))));
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setConnectTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
            httpURLConnection.setReadTimeout(Constants.ERRORCODE_UNKNOWN);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod(HttpPost.METHOD_NAME);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(false);
            return httpURLConnection;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}
