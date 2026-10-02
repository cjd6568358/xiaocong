package com.baidu.lbsapi.auth;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tencent.android.tpush.common.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import net.sqlcipher.database.SQLiteDatabase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private Context a;
    private String b = null;
    private HashMap<String, String> c = null;
    private String d = null;

    public g(Context context) {
        this.a = context;
    }

    private String a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                return null;
            }
            String extraInfo = activeNetworkInfo.getExtraInfo();
            if (extraInfo == null || !(extraInfo.trim().toLowerCase().equals("cmwap") || extraInfo.trim().toLowerCase().equals("uniwap") || extraInfo.trim().toLowerCase().equals("3gwap") || extraInfo.trim().toLowerCase().equals("ctwap"))) {
                return "wifi";
            }
            return extraInfo.trim().toLowerCase().equals("ctwap") ? "ctwap" : "cmwap";
        } catch (Exception e) {
            if (a.a) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:145:0x0275 A[PHI: r3
  0x0275: PHI (r3v6 int) = (r3v3 int), (r3v4 int), (r3v7 int) binds: [B:88:0x01df, B:74:0x01a3, B:56:0x015d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:149:0x01f6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:48:0x0131 A[Catch: MalformedURLException -> 0x0135, all -> 0x0230, Exception -> 0x023c, IOException -> 0x0247, TryCatch #13 {all -> 0x0230, blocks: (B:8:0x0031, B:38:0x0115, B:84:0x01b9, B:86:0x01bd, B:87:0x01c0, B:70:0x017d, B:72:0x0181, B:73:0x0184, B:40:0x011d, B:26:0x00c5, B:28:0x00cd, B:46:0x0129, B:48:0x0131, B:49:0x0134), top: B:157:0x002d }] */
    private void a(HttpsURLConnection httpsURLConnection) throws Throwable {
        OutputStream outputStream;
        int i;
        OutputStream outputStream2;
        InputStream inputStream;
        BufferedReader bufferedReader;
        OutputStream outputStream3 = null;
        bufferedReader = null;
        bufferedReader = null;
        bufferedReader = null;
        bufferedReader = null;
        bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        a.a("https Post start,url:" + this.b);
        if (this.c == null) {
            this.d = ErrorMessage.a("httpsPost request paramters is null.");
            return;
        }
        boolean z = true;
        try {
            try {
                outputStream2 = httpsURLConnection.getOutputStream();
                try {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream2, HTTP.UTF_8));
                        bufferedWriter.write(b(this.c));
                        a.a(b(this.c));
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        httpsURLConnection.connect();
                        try {
                            inputStream = httpsURLConnection.getInputStream();
                            try {
                                int responseCode = httpsURLConnection.getResponseCode();
                                if (200 == responseCode) {
                                    try {
                                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, HTTP.UTF_8));
                                        try {
                                            StringBuffer stringBuffer = new StringBuffer();
                                            while (true) {
                                                int i2 = bufferedReader.read();
                                                if (i2 == -1) {
                                                    break;
                                                } else {
                                                    stringBuffer.append((char) i2);
                                                }
                                            }
                                            this.d = stringBuffer.toString();
                                        } catch (IOException e) {
                                            e = e;
                                            bufferedReader2 = bufferedReader;
                                            i = responseCode;
                                            try {
                                                if (a.a) {
                                                    e.printStackTrace();
                                                    a.a("httpsPost parse failed;" + e.getMessage());
                                                }
                                                this.d = ErrorMessage.a(-11, "httpsPost failed,IOException:" + e.getMessage());
                                                if (inputStream != null && bufferedReader2 != null) {
                                                    bufferedReader2.close();
                                                    inputStream.close();
                                                }
                                                if (httpsURLConnection != null) {
                                                    httpsURLConnection.disconnect();
                                                    z = false;
                                                } else {
                                                    z = false;
                                                }
                                            } catch (Throwable th) {
                                                th = th;
                                                if (inputStream != null && bufferedReader2 != null) {
                                                    bufferedReader2.close();
                                                    inputStream.close();
                                                }
                                                if (httpsURLConnection != null) {
                                                    httpsURLConnection.disconnect();
                                                }
                                                throw th;
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            bufferedReader2 = bufferedReader;
                                            if (inputStream != null) {
                                                bufferedReader2.close();
                                                inputStream.close();
                                            }
                                            if (httpsURLConnection != null) {
                                                httpsURLConnection.disconnect();
                                            }
                                            throw th;
                                        }
                                    } catch (IOException e2) {
                                        e = e2;
                                        i = responseCode;
                                    } catch (Throwable th3) {
                                        th = th3;
                                    }
                                } else {
                                    bufferedReader = null;
                                }
                                if (inputStream != null && bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                        inputStream.close();
                                    } catch (MalformedURLException e3) {
                                        e = e3;
                                        i = responseCode;
                                        outputStream3 = outputStream2;
                                        try {
                                            if (a.a) {
                                                e.printStackTrace();
                                            }
                                            this.d = ErrorMessage.a(-11, "httpsPost failed,MalformedURLException:" + e.getMessage());
                                            if (outputStream3 != null) {
                                                try {
                                                    outputStream3.close();
                                                    z = false;
                                                } catch (IOException e4) {
                                                    if (a.a) {
                                                        e4.printStackTrace();
                                                    }
                                                    z = false;
                                                }
                                            } else {
                                                z = false;
                                            }
                                        } catch (Throwable th4) {
                                            th = th4;
                                            outputStream = outputStream3;
                                            if (outputStream != null) {
                                                try {
                                                    outputStream.close();
                                                } catch (IOException e5) {
                                                    if (a.a) {
                                                        e5.printStackTrace();
                                                    }
                                                }
                                            }
                                            throw th;
                                        }
                                    } catch (IOException e6) {
                                        e = e6;
                                        i = responseCode;
                                        if (a.a) {
                                            e.printStackTrace();
                                        }
                                        this.d = ErrorMessage.a(-11, "httpsPost failed,IOException:" + e.getMessage());
                                        if (outputStream2 != null) {
                                            try {
                                                outputStream2.close();
                                                z = false;
                                            } catch (IOException e7) {
                                                if (a.a) {
                                                    e7.printStackTrace();
                                                }
                                                z = false;
                                            }
                                        } else {
                                            z = false;
                                        }
                                    } catch (Exception e8) {
                                        e = e8;
                                        i = responseCode;
                                        if (a.a) {
                                            e.printStackTrace();
                                        }
                                        this.d = ErrorMessage.a(-11, "httpsPost failed,Exception:" + e.getMessage());
                                        if (outputStream2 != null) {
                                            try {
                                                outputStream2.close();
                                                z = false;
                                            } catch (IOException e9) {
                                                if (a.a) {
                                                    e9.printStackTrace();
                                                }
                                                z = false;
                                            }
                                        } else {
                                            z = false;
                                        }
                                    }
                                }
                                if (httpsURLConnection != null) {
                                    httpsURLConnection.disconnect();
                                    i = responseCode;
                                } else {
                                    i = responseCode;
                                }
                            } catch (IOException e10) {
                                e = e10;
                                i = -1;
                            } catch (Throwable th5) {
                                th = th5;
                            }
                        } catch (IOException e11) {
                            e = e11;
                            inputStream = null;
                            i = -1;
                        } catch (Throwable th6) {
                            th = th6;
                            inputStream = null;
                        }
                        if (outputStream2 != null) {
                            try {
                                outputStream2.close();
                            } catch (IOException e12) {
                                if (a.a) {
                                    e12.printStackTrace();
                                }
                            }
                        }
                    } catch (MalformedURLException e13) {
                        e = e13;
                        i = -1;
                        outputStream3 = outputStream2;
                    } catch (IOException e14) {
                        e = e14;
                        i = -1;
                    } catch (Exception e15) {
                        e = e15;
                        i = -1;
                    }
                } catch (MalformedURLException e16) {
                    e = e16;
                    outputStream3 = outputStream2;
                } catch (IOException e17) {
                    e = e17;
                } catch (Exception e18) {
                    e = e18;
                }
            } catch (Throwable th7) {
                th = th7;
                if (outputStream != null) {
                    outputStream.close();
                }
                throw th;
            }
        } catch (MalformedURLException e19) {
            e = e19;
            i = -1;
        } catch (IOException e20) {
            e = e20;
            i = -1;
            outputStream2 = null;
        } catch (Exception e21) {
            e = e21;
            i = -1;
            outputStream2 = null;
        } catch (Throwable th8) {
            th = th8;
            outputStream = null;
            if (outputStream != null) {
                outputStream.close();
            }
            throw th;
        }
        if (z && 200 != i) {
            a.a("httpsPost failed,statusCode:" + i);
            this.d = ErrorMessage.a(-11, "httpsPost failed,statusCode:" + i);
        } else if (this.d != null) {
            a.a("httpsPost success end,parse result = " + this.d);
        } else {
            a.a("httpsPost failed,mResult is null");
            this.d = ErrorMessage.a(-1, "httpsPost failed,internal error");
        }
    }

    private static String b(HashMap<String, String> map) throws UnsupportedEncodingException {
        boolean z;
        StringBuilder sb = new StringBuilder();
        boolean z2 = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (z2) {
                z = false;
            } else {
                sb.append("&");
                z = z2;
            }
            sb.append(URLEncoder.encode(entry.getKey(), HTTP.UTF_8));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue(), HTTP.UTF_8));
            z2 = z;
        }
        return sb.toString();
    }

    private HttpsURLConnection b() {
        HttpsURLConnection httpsURLConnection;
        try {
            URL url = new URL(this.b);
            a.a("https URL: " + this.b);
            String strA = a(this.a);
            if (strA == null || strA.equals(Constants.MAIN_VERSION_TAG)) {
                a.c("Current network is not available.");
                this.d = ErrorMessage.a(-10, "Current network is not available.");
                return null;
            }
            a.a("checkNetwork = " + strA);
            if (strA.equals("cmwap")) {
                httpsURLConnection = (HttpsURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.172", 80)));
            } else {
                httpsURLConnection = strA.equals("ctwap") ? (HttpsURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.200", 80))) : (HttpsURLConnection) url.openConnection();
            }
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setRequestMethod(HttpPost.METHOD_NAME);
            httpsURLConnection.setConnectTimeout(SQLiteDatabase.SQLITE_MAX_LIKE_PATTERN_LENGTH);
            httpsURLConnection.setReadTimeout(SQLiteDatabase.SQLITE_MAX_LIKE_PATTERN_LENGTH);
            return httpsURLConnection;
        } catch (MalformedURLException e) {
            if (a.a) {
                e.printStackTrace();
                a.a(e.getMessage());
            }
            this.d = ErrorMessage.a(-11, "Auth server could not be parsed as a URL.");
            return null;
        } catch (Exception e2) {
            if (a.a) {
                e2.printStackTrace();
                a.a(e2.getMessage());
            }
            this.d = ErrorMessage.a(-11, "Init httpsurlconnection failed.");
            return null;
        }
    }

    private HashMap<String, String> c(HashMap<String, String> map) {
        HashMap<String, String> map2 = new HashMap<>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String string = it.next().toString();
            map2.put(string, map.get(string));
        }
        return map2;
    }

    protected String a(HashMap<String, String> map) throws Throwable {
        this.c = c(map);
        this.b = this.c.get("url");
        HttpsURLConnection httpsURLConnectionB = b();
        if (httpsURLConnectionB == null) {
            a.c("syncConnect failed,httpsURLConnection is null");
            return this.d;
        }
        a(httpsURLConnectionB);
        return this.d;
    }

    protected boolean a() {
        a.a("checkNetwork start");
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.a.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                return false;
            }
            a.a("checkNetwork end");
            return true;
        } catch (Exception e) {
            if (a.a) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
