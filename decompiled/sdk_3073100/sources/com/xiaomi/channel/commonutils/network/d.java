package com.xiaomi.channel.commonutils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.xiaocong.smarthome.network.constant.NetworkConstant;
import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class d {
    public static final Pattern a = Pattern.compile("([^\\s;]+)(.*)");
    public static final Pattern b = Pattern.compile("(.*?charset\\s*=[^a-zA-Z0-9]*)([-a-zA-Z0-9]+)(.*)", 2);
    public static final Pattern c = Pattern.compile("(\\<\\?xml\\s+.*?encoding\\s*=[^a-zA-Z0-9]*)([-a-zA-Z0-9]+)(.*)", 2);

    public static final class a extends FilterInputStream {
        private boolean a;

        public a(InputStream inputStream) {
            super(inputStream);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) {
            int i3;
            if (!this.a && (i3 = super.read(bArr, i, i2)) != -1) {
                return i3;
            }
            this.a = true;
            return -1;
        }
    }

    public static class b {
        public int a;
        public Map<String, String> b;

        public String toString() {
            return String.format("resCode = %1$d, headers = %2$s", Integer.valueOf(this.a), this.b.toString());
        }
    }

    public static int a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return -1;
            }
            try {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    return -1;
                }
                return activeNetworkInfo.getType();
            } catch (Exception e) {
                return -1;
            }
        } catch (Exception e2) {
            return -1;
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x004c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:21:0x004e A[Catch: IOException -> 0x0118, TRY_LEAVE, TryCatch #3 {IOException -> 0x0118, blocks: (B:19:0x0049, B:21:0x004e), top: B:74:0x0049 }] */
    /* JADX WARN: Code duplicated, block: B:53:0x010b A[Catch: IOException -> 0x010f, TRY_LEAVE, TryCatch #7 {IOException -> 0x010f, blocks: (B:51:0x0106, B:53:0x010b), top: B:78:0x0106 }] */
    /* JADX WARN: Code duplicated, block: B:74:0x0049 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static com.xiaomi.channel.commonutils.network.b a(Context context, String str, String str2, Map<String, String> map, String str3) throws Throwable {
        OutputStream outputStream;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        outputStream = null;
        bufferedReader2 = null;
        OutputStream outputStream2 = null;
        com.xiaomi.channel.commonutils.network.b bVar = new com.xiaomi.channel.commonutils.network.b();
        try {
            try {
                try {
                    HttpURLConnection httpURLConnectionB = b(context, b(str));
                    httpURLConnectionB.setConnectTimeout(NetworkConstant.HTTP_TIMEOUT);
                    httpURLConnectionB.setReadTimeout(AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
                    if (str2 == null) {
                        str2 = "GET";
                    }
                    httpURLConnectionB.setRequestMethod(str2);
                    if (map != null) {
                        for (String str4 : map.keySet()) {
                            httpURLConnectionB.setRequestProperty(str4, map.get(str4));
                        }
                    }
                    if (!TextUtils.isEmpty(str3)) {
                        httpURLConnectionB.setDoOutput(true);
                        byte[] bytes = str3.getBytes();
                        OutputStream outputStream3 = httpURLConnectionB.getOutputStream();
                        try {
                            outputStream3.write(bytes, 0, bytes.length);
                            outputStream3.flush();
                            outputStream3.close();
                        } catch (IOException e) {
                            e = e;
                            outputStream2 = outputStream3;
                            bufferedReader = null;
                        } catch (Throwable th) {
                            th = th;
                            throw new IOException(th.getMessage());
                        }
                    }
                    bVar.a = httpURLConnectionB.getResponseCode();
                    Log.d("com.xiaomi.common.Network", "Http POST Response Code: " + bVar.a);
                    int i = 0;
                    while (true) {
                        String headerFieldKey = httpURLConnectionB.getHeaderFieldKey(i);
                        String headerField = httpURLConnectionB.getHeaderField(i);
                        if (headerFieldKey == null && headerField == null) {
                            try {
                                break;
                            } catch (IOException e2) {
                                bufferedReader = new BufferedReader(new InputStreamReader(new a(httpURLConnectionB.getErrorStream())));
                            }
                        } else {
                            bVar.b.put(headerFieldKey, headerField);
                            i = i + 1 + 1;
                        }
                        try {
                            throw e;
                        } catch (Throwable th2) {
                            th = th2;
                            BufferedReader bufferedReader3 = bufferedReader;
                            outputStream = outputStream2;
                            bufferedReader2 = bufferedReader3;
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                    if (bufferedReader2 != null) {
                                        bufferedReader2.close();
                                    }
                                } catch (IOException e3) {
                                    Log.e("com.xiaomi.common.Network", "error while closing strean", e3);
                                    throw th;
                                }
                            } else if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            throw th;
                        }
                    }
                    bufferedReader = new BufferedReader(new InputStreamReader(new a(httpURLConnectionB.getInputStream())));
                    try {
                        StringBuffer stringBuffer = new StringBuffer();
                        String property = System.getProperty("line.separator");
                        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                            stringBuffer.append(line);
                            stringBuffer.append(property);
                        }
                        bVar.c = stringBuffer.toString();
                        bufferedReader.close();
                        BufferedReader bufferedReader4 = null;
                        if (0 != 0) {
                            try {
                                bufferedReader2.close();
                                if (0 != 0) {
                                    bufferedReader4.close();
                                }
                            } catch (IOException e4) {
                                Log.e("com.xiaomi.common.Network", "error while closing strean", e4);
                            }
                        } else if (0 != 0) {
                            bufferedReader4.close();
                        }
                        return bVar;
                    } catch (IOException e5) {
                        e = e5;
                    } catch (Throwable th3) {
                        th = th3;
                        BufferedReader bufferedReader5 = bufferedReader;
                        outputStream = null;
                        bufferedReader2 = bufferedReader5;
                        if (outputStream != null) {
                            outputStream.close();
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                        } else if (bufferedReader2 != null) {
                            bufferedReader2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (IOException e6) {
                e = e6;
                bufferedReader = null;
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }

    public static com.xiaomi.channel.commonutils.network.b a(Context context, String str, Map<String, String> map) {
        return a(context, str, "POST", (Map<String, String>) null, a(map));
    }

    public static InputStream a(Context context, URL url, boolean z, String str, String str2) {
        return a(context, url, z, str, str2, null, null);
    }

    public static InputStream a(Context context, URL url, boolean z, String str, String str2, Map<String, String> map, b bVar) throws IOException {
        if (context == null) {
            throw new IllegalArgumentException("context");
        }
        if (url == null) {
            throw new IllegalArgumentException("url");
        }
        URL url2 = !z ? new URL(a(url.toString())) : url;
        try {
            HttpURLConnection.setFollowRedirects(true);
            HttpURLConnection httpURLConnectionB = b(context, url2);
            httpURLConnectionB.setConnectTimeout(NetworkConstant.HTTP_TIMEOUT);
            httpURLConnectionB.setReadTimeout(AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
            if (!TextUtils.isEmpty(str)) {
                httpURLConnectionB.setRequestProperty("User-Agent", str);
            }
            if (str2 != null) {
                httpURLConnectionB.setRequestProperty("Cookie", str2);
            }
            if (map != null) {
                for (String str3 : map.keySet()) {
                    httpURLConnectionB.setRequestProperty(str3, map.get(str3));
                }
            }
            if (bVar != null && (url.getProtocol().equals("http") || url.getProtocol().equals("https"))) {
                bVar.a = httpURLConnectionB.getResponseCode();
                if (bVar.b == null) {
                    bVar.b = new HashMap();
                }
                int i = 0;
                while (true) {
                    String headerFieldKey = httpURLConnectionB.getHeaderFieldKey(i);
                    String headerField = httpURLConnectionB.getHeaderField(i);
                    if (headerFieldKey == null && headerField == null) {
                        break;
                    }
                    if (!TextUtils.isEmpty(headerFieldKey) && !TextUtils.isEmpty(headerField)) {
                        bVar.b.put(headerFieldKey, headerField);
                    }
                    i++;
                }
            }
            return new a(httpURLConnectionB.getInputStream());
        } catch (IOException e) {
            throw e;
        } catch (Throwable th) {
            throw new IOException(th.getMessage());
        }
    }

    public static String a(Context context, URL url) {
        return a(context, url, false, null, AsyncHttpResponseHandler.DEFAULT_CHARSET, null);
    }

    public static String a(Context context, URL url, boolean z, String str, String str2, String str3) {
        InputStream inputStreamA = null;
        try {
            inputStreamA = a(context, url, z, str, str3);
            StringBuilder sb = new StringBuilder(1024);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamA, str2));
            char[] cArr = new char[4096];
            while (true) {
                int i = bufferedReader.read(cArr);
                if (-1 == i) {
                    break;
                }
                sb.append(cArr, 0, i);
            }
            if (inputStreamA != null) {
                try {
                    inputStreamA.close();
                } catch (IOException e) {
                    Log.e("com.xiaomi.common.Network", "Failed to close responseStream" + e.toString());
                }
            }
            return sb.toString();
        } catch (Throwable th) {
            if (inputStreamA != null) {
                try {
                    inputStreamA.close();
                } catch (IOException e2) {
                    Log.e("com.xiaomi.common.Network", "Failed to close responseStream" + e2.toString());
                }
            }
            throw th;
        }
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        new String();
        return String.format("%s&key=%s", str, com.xiaomi.channel.commonutils.string.c.a(String.format("%sbe988a6134bc8254465424e5a70ef037", str)));
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0079 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:22:0x007b A[Catch: IOException -> 0x016b, TryCatch #9 {IOException -> 0x016b, blocks: (B:20:0x0076, B:22:0x007b, B:24:0x0080), top: B:76:0x0076 }] */
    /* JADX WARN: Code duplicated, block: B:24:0x0080 A[Catch: IOException -> 0x016b, TRY_LEAVE, TryCatch #9 {IOException -> 0x016b, blocks: (B:20:0x0076, B:22:0x007b, B:24:0x0080), top: B:76:0x0076 }] */
    /* JADX WARN: Code duplicated, block: B:76:0x0076 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v13, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v7 */
    public static String a(String str, Map<String, String> map, File file, String str2) throws Throwable {
        DataOutputStream dataOutputStream;
        DataOutputStream dataOutputStream2;
        ?? r2;
        BufferedReader bufferedReader = null;
        if (!file.exists()) {
            return null;
        }
        ?? name = file.getName();
        try {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setReadTimeout(AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
                httpURLConnection.setConnectTimeout(NetworkConstant.HTTP_TIMEOUT);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                httpURLConnection.setFixedLengthStreamingMode(name.length() + 77 + ((int) file.length()) + str2.length());
                dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                try {
                    dataOutputStream.writeBytes("--*****\r\n");
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + str2 + "\";filename=\"" + file.getName() + "\"\r\n");
                    dataOutputStream.writeBytes("\r\n");
                    name = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i = name.read(bArr);
                            if (i == -1) {
                                break;
                            }
                            dataOutputStream.write(bArr, 0, i);
                            dataOutputStream.flush();
                            try {
                                throw e;
                            } catch (Throwable th) {
                                th = th;
                                name = r2;
                                dataOutputStream = dataOutputStream2;
                                if (name != 0) {
                                    try {
                                        name.close();
                                        if (dataOutputStream != null) {
                                            dataOutputStream.close();
                                        }
                                        if (bufferedReader != null) {
                                            bufferedReader.close();
                                        }
                                    } catch (IOException e) {
                                        Log.e("com.xiaomi.common.Network", "error while closing strean", e);
                                        throw th;
                                    }
                                } else {
                                    if (dataOutputStream != null) {
                                        dataOutputStream.close();
                                    }
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                }
                                throw th;
                            }
                        }
                        dataOutputStream.writeBytes("\r\n");
                        dataOutputStream.writeBytes("--");
                        dataOutputStream.writeBytes("*****");
                        dataOutputStream.writeBytes("--");
                        dataOutputStream.writeBytes("\r\n");
                        dataOutputStream.flush();
                        StringBuffer stringBuffer = new StringBuffer();
                        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new a(httpURLConnection.getInputStream())));
                        while (true) {
                            try {
                                String line = bufferedReader2.readLine();
                                if (line == null) {
                                    break;
                                }
                                stringBuffer.append(line);
                            } catch (IOException e2) {
                                e = e2;
                                bufferedReader = bufferedReader2;
                                dataOutputStream2 = dataOutputStream;
                                r2 = name;
                            } catch (Throwable th2) {
                                th = th2;
                                throw new IOException(th.getMessage());
                            }
                        }
                        String string = stringBuffer.toString();
                        if (name != 0) {
                            try {
                                name.close();
                            } catch (IOException e3) {
                                Log.e("com.xiaomi.common.Network", "error while closing strean", e3);
                                return string;
                            }
                        }
                        if (dataOutputStream != null) {
                            dataOutputStream.close();
                        }
                        if (bufferedReader2 == null) {
                            return string;
                        }
                        bufferedReader2.close();
                        return string;
                    } catch (IOException e4) {
                        e = e4;
                        dataOutputStream2 = dataOutputStream;
                        r2 = name;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (IOException e5) {
                    e = e5;
                    dataOutputStream2 = dataOutputStream;
                    r2 = 0;
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Throwable th5) {
                th = th5;
            }
        } catch (IOException e6) {
            e = e6;
            dataOutputStream2 = null;
            r2 = 0;
        } catch (Throwable th6) {
            th = th6;
        }
    }

    public static String a(URL url) {
        StringBuilder sb = new StringBuilder();
        sb.append(url.getProtocol()).append("://").append("10.0.0.172").append(url.getPath());
        if (!TextUtils.isEmpty(url.getQuery())) {
            sb.append("?").append(url.getQuery());
        }
        return sb.toString();
    }

    public static String a(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                try {
                    stringBuffer.append(URLEncoder.encode(entry.getKey(), AsyncHttpResponseHandler.DEFAULT_CHARSET));
                    stringBuffer.append("=");
                    stringBuffer.append(URLEncoder.encode(entry.getValue(), AsyncHttpResponseHandler.DEFAULT_CHARSET));
                    stringBuffer.append("&");
                } catch (UnsupportedEncodingException e) {
                    Log.d("com.xiaomi.common.Network", "Failed to convert from params map to string: " + e.toString());
                    Log.d("com.xiaomi.common.Network", "map: " + map.toString());
                    return null;
                }
            }
        }
        return (stringBuffer.length() > 0 ? stringBuffer.deleteCharAt(stringBuffer.length() - 1) : stringBuffer).toString();
    }

    public static HttpURLConnection b(Context context, URL url) {
        if (!"http".equals(url.getProtocol())) {
            return (HttpURLConnection) url.openConnection();
        }
        if (c(context)) {
            return (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.200", 80)));
        }
        if (!b(context)) {
            return (HttpURLConnection) url.openConnection();
        }
        String host = url.getHost();
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(a(url)).openConnection();
        httpURLConnection.addRequestProperty("X-Online-Host", host);
        return httpURLConnection;
    }

    private static URL b(String str) {
        return new URL(str);
    }

    public static boolean b(Context context) {
        if (!"CN".equalsIgnoreCase(((TelephonyManager) context.getSystemService("phone")).getSimCountryIso())) {
            return false;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            try {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    return false;
                }
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (TextUtils.isEmpty(extraInfo) || extraInfo.length() < 3 || extraInfo.contains("ctwap")) {
                    return false;
                }
                return extraInfo.regionMatches(true, extraInfo.length() - 3, NetworkUtils.NETWORKTYPE_WAP, 0, 3);
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e2) {
            return false;
        }
    }

    public static boolean c(Context context) {
        if (!"CN".equalsIgnoreCase(((TelephonyManager) context.getSystemService("phone")).getSimCountryIso())) {
            return false;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            try {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    return false;
                }
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (TextUtils.isEmpty(extraInfo) || extraInfo.length() < 3) {
                    return false;
                }
                return extraInfo.contains("ctwap");
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e2) {
            return false;
        }
    }

    public static boolean d(Context context) {
        return a(context) >= 0;
    }

    public static boolean e(Context context) {
        NetworkInfo activeNetworkInfo;
        try {
            activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e) {
            activeNetworkInfo = null;
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean f(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            try {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    return 1 == activeNetworkInfo.getType();
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e2) {
            return false;
        }
    }

    public static String k(Context context) {
        if (f(context)) {
            return NetworkUtils.NETWORKTYPE_WIFI;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return "";
            }
            try {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo == null ? "" : (activeNetworkInfo.getTypeName() + "-" + activeNetworkInfo.getSubtypeName() + "-" + activeNetworkInfo.getExtraInfo()).toLowerCase();
            } catch (Exception e) {
                return "";
            }
        } catch (Exception e2) {
            return "";
        }
    }
}
