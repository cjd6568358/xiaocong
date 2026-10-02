package com.alibaba.mtl.log.e;

import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: HttpUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class e {

    /* JADX INFO: compiled from: HttpUtils.java */
    public static class a {
        public int E = -1;
        public byte[] e = null;
    }

    static {
        System.setProperty("http.keepAlive", "true");
    }

    /* JADX WARN: Code duplicated, block: B:124:0x024d A[Catch: IOException -> 0x020c, all -> 0x0276, TRY_ENTER, TRY_LEAVE, TryCatch #10 {IOException -> 0x020c, blocks: (B:91:0x01e0, B:93:0x01ec, B:94:0x01f6, B:95:0x01fd, B:97:0x0207, B:124:0x024d), top: B:171:0x01e0, outer: #16 }] */
    /* JADX WARN: Code duplicated, block: B:130:0x0263  */
    /* JADX WARN: Code duplicated, block: B:161:0x025a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:179:0x023f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:190:0x0258 A[EDGE_INSN: B:190:0x0258->B:126:0x0258 BREAK  A[LOOP:0: B:95:0x01fd->B:97:0x0207], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:97:0x0207 A[Catch: IOException -> 0x020c, all -> 0x0276, LOOP:0: B:95:0x01fd->B:97:0x0207, LOOP_END, TRY_LEAVE, TryCatch #10 {IOException -> 0x020c, blocks: (B:91:0x01e0, B:93:0x01ec, B:94:0x01f6, B:95:0x01fd, B:97:0x0207, B:124:0x024d), top: B:171:0x01e0, outer: #16 }] */
    public static a a(int i, String str, Map<String, Object> map, boolean z) throws Throwable {
        byte[] byteArray;
        DataOutputStream dataOutputStream;
        byte[] bArr;
        int i2;
        a aVar = new a();
        if (TextUtils.isEmpty(str)) {
            return aVar;
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            if (httpURLConnection != null) {
                try {
                    if (httpURLConnection instanceof HttpsURLConnection) {
                        SSLContext sSLContext = SSLContext.getInstance("TLS");
                        sSLContext.init(null, new TrustManager[]{new b()}, null);
                        HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
                        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() { // from class: com.alibaba.mtl.log.e.e.1
                            @Override // javax.net.ssl.HostnameVerifier
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        });
                    }
                } catch (Throwable th) {
                }
                if (i == 2 || i == 3) {
                    httpURLConnection.setDoOutput(true);
                }
                httpURLConnection.setDoInput(true);
                try {
                    if (i == 2 || i == 3) {
                        httpURLConnection.setRequestMethod(HttpPost.METHOD_NAME);
                    } else {
                        httpURLConnection.setRequestMethod(HttpGet.METHOD_NAME);
                    }
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setConnectTimeout(Constants.ERRORCODE_UNKNOWN);
                    httpURLConnection.setReadTimeout(60000);
                    httpURLConnection.setRequestProperty(HTTP.CONN_DIRECTIVE, "close");
                    if (z) {
                        httpURLConnection.setRequestProperty("Accept-Encoding", "gzip,deflate");
                    }
                    httpURLConnection.setInstanceFollowRedirects(true);
                    byte[] bArr2 = null;
                    if (i == 2 || i == 3) {
                        if (i == 2) {
                            httpURLConnection.setRequestProperty(HTTP.CONTENT_TYPE, "multipart/form-data; boundary=GJircTeP");
                        } else if (i == 3) {
                            httpURLConnection.setRequestProperty(HTTP.CONTENT_TYPE, URLEncodedUtils.CONTENT_TYPE);
                        }
                        if (map == null || map.size() <= 0) {
                            byteArray = null;
                        } else {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            Set<String> setKeySet = map.keySet();
                            String[] strArr = new String[setKeySet.size()];
                            setKeySet.toArray(strArr);
                            String[] strArrA = g.a().a(strArr, true);
                            for (String str2 : strArrA) {
                                if (i == 2) {
                                    byte[] bArr3 = (byte[]) map.get(str2);
                                    if (bArr3 != null) {
                                        try {
                                            byteArrayOutputStream.write(String.format("--GJircTeP\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\nContent-Type: application/octet-stream \r\n\r\n", str2, str2).getBytes());
                                            byteArrayOutputStream.write(bArr3);
                                            byteArrayOutputStream.write("\r\n".getBytes());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else if (i == 3) {
                                    String str3 = (String) map.get(str2);
                                    if (byteArrayOutputStream.size() > 0) {
                                        try {
                                            byteArrayOutputStream.write(("&" + str2 + "=" + str3).getBytes());
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            byteArrayOutputStream.write((str2 + "=" + str3).getBytes());
                                        } catch (IOException e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                }
                            }
                            if (i == 2) {
                                try {
                                    byteArrayOutputStream.write("--GJircTeP--\r\n".getBytes());
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            byteArray = byteArrayOutputStream.toByteArray();
                        }
                        httpURLConnection.setRequestProperty(HTTP.CONTENT_LEN, String.valueOf(byteArray != null ? byteArray.length : 0));
                        bArr2 = byteArray;
                    }
                    DataOutputStream dataOutputStream2 = null;
                    try {
                        httpURLConnection.connect();
                        if ((i == 2 || i == 3) && bArr2 != null && bArr2.length > 0) {
                            DataOutputStream dataOutputStream3 = new DataOutputStream(httpURLConnection.getOutputStream());
                            try {
                                dataOutputStream3.write(bArr2);
                                dataOutputStream3.flush();
                                dataOutputStream2 = dataOutputStream3;
                            } catch (Exception e5) {
                                e = e5;
                                dataOutputStream = dataOutputStream3;
                                try {
                                    e.printStackTrace();
                                    i.a("UtAnalytics", HttpHost.DEFAULT_SCHEME_NAME, e);
                                    if (dataOutputStream != null) {
                                        try {
                                            dataOutputStream.close();
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                        }
                                    }
                                    return aVar;
                                } catch (Throwable th2) {
                                    th = th2;
                                    dataOutputStream2 = dataOutputStream;
                                    if (dataOutputStream2 != null) {
                                        try {
                                            dataOutputStream2.close();
                                        } catch (IOException e7) {
                                            e7.printStackTrace();
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                dataOutputStream2 = dataOutputStream3;
                                if (dataOutputStream2 != null) {
                                    dataOutputStream2.close();
                                }
                                throw th;
                            }
                        }
                        if (dataOutputStream2 != null) {
                            try {
                                dataOutputStream2.close();
                            } catch (IOException e8) {
                                e8.printStackTrace();
                            }
                        }
                        try {
                            aVar.E = httpURLConnection.getResponseCode();
                            i.a("UtAnalytics", "responseCode:", Integer.valueOf(aVar.E));
                        } catch (IOException e9) {
                            e9.printStackTrace();
                        }
                        InputStream gZIPInputStream = null;
                        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                        if (z) {
                            try {
                                try {
                                    gZIPInputStream = "gzip".equals(httpURLConnection.getContentEncoding()) ? new GZIPInputStream(httpURLConnection.getInputStream()) : new DataInputStream(httpURLConnection.getInputStream());
                                    System.currentTimeMillis();
                                    bArr = new byte[2048];
                                    while (true) {
                                        i2 = gZIPInputStream.read(bArr, 0, 2048);
                                        if (i2 != -1) {
                                            break;
                                        }
                                        byteArrayOutputStream2.write(bArr, 0, i2);
                                    }
                                    if (gZIPInputStream != null) {
                                        try {
                                            gZIPInputStream.close();
                                        } catch (Exception e10) {
                                            e10.printStackTrace();
                                        }
                                    }
                                    if (byteArrayOutputStream2.size() > 0) {
                                        aVar.e = byteArrayOutputStream2.toByteArray();
                                    }
                                } catch (IOException e11) {
                                    e11.printStackTrace();
                                    if (gZIPInputStream != null) {
                                        try {
                                            gZIPInputStream.close();
                                        } catch (Exception e12) {
                                            e12.printStackTrace();
                                        }
                                    }
                                    return aVar;
                                }
                            } catch (Throwable th4) {
                                if (gZIPInputStream != null) {
                                    try {
                                        gZIPInputStream.close();
                                    } catch (Exception e13) {
                                        e13.printStackTrace();
                                    }
                                }
                                throw th4;
                            }
                        } else {
                            System.currentTimeMillis();
                            bArr = new byte[2048];
                            while (true) {
                                i2 = gZIPInputStream.read(bArr, 0, 2048);
                                if (i2 != -1) {
                                    break;
                                    break;
                                }
                                byteArrayOutputStream2.write(bArr, 0, i2);
                            }
                            if (gZIPInputStream != null) {
                                gZIPInputStream.close();
                            }
                            if (byteArrayOutputStream2.size() > 0) {
                                aVar.e = byteArrayOutputStream2.toByteArray();
                            }
                        }
                    } catch (Exception e14) {
                        e = e14;
                        dataOutputStream = null;
                    } catch (Throwable th5) {
                        th = th5;
                    }
                } catch (ProtocolException e15) {
                    e15.printStackTrace();
                    return aVar;
                }
            } else {
                i.a("UtAnalytics", "conn", httpURLConnection);
            }
            return aVar;
        } catch (MalformedURLException e16) {
            e16.printStackTrace();
            return aVar;
        } catch (IOException e17) {
            e17.printStackTrace();
            return aVar;
        }
    }

    /* JADX INFO: compiled from: HttpUtils.java */
    public static class b implements X509TrustManager {
        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
