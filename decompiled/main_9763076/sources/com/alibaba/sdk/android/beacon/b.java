package com.alibaba.sdk.android.beacon;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.ta.utdid2.device.UTDevice;
import com.tencent.android.tpush.SettingsContentProvider;
import com.tencent.android.tpush.common.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class b {
    private static final String a;
    private static final String b;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private final Beacon f47a;
    private final List<Beacon.Config> c = new ArrayList();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private final a f48a = new a();

    private final class a {
        private a() {
        }

        /* JADX WARN: Code duplicated, block: B:22:0x0083 A[Catch: IOException -> 0x00c5, TRY_LEAVE, TryCatch #1 {IOException -> 0x00c5, blocks: (B:20:0x007e, B:22:0x0083), top: B:60:0x007e }] */
        /* JADX WARN: Code duplicated, block: B:41:0x00b5 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:42:0x00b7 A[Catch: IOException -> 0x00bb, TRY_LEAVE, TryCatch #6 {IOException -> 0x00bb, blocks: (B:40:0x00b2, B:42:0x00b7), top: B:62:0x00b2 }] */
        /* JADX WARN: Code duplicated, block: B:62:0x00b2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        String a(String str, byte[] bArr) throws Throwable {
            OutputStream outputStream;
            OutputStream outputStream2;
            BufferedReader bufferedReader = null;
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setReadTimeout(Constants.ERRORCODE_UNKNOWN);
                httpURLConnection.setConnectTimeout(Constants.ERRORCODE_UNKNOWN);
                httpURLConnection.setRequestMethod(HttpPost.METHOD_NAME);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                if (com.alibaba.sdk.android.beacon.a.a) {
                    httpURLConnection.setRequestProperty(HTTP.TARGET_HOST, "beacon-api.aliyuncs.com");
                }
                outputStream = httpURLConnection.getOutputStream();
                try {
                    outputStream.write(bArr);
                    outputStream.flush();
                    int responseCode = httpURLConnection.getResponseCode();
                    boolean zA = a(responseCode);
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(zA ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream(), HTTP.UTF_8));
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                        }
                        if (!zA) {
                            b.this.a(String.valueOf(responseCode), sb.toString());
                        }
                        String string = sb.toString();
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                return string;
                            }
                        }
                        if (bufferedReader2 == null) {
                            return string;
                        }
                        bufferedReader2.close();
                        return string;
                    } catch (Exception e2) {
                        e = e2;
                        bufferedReader = bufferedReader2;
                        outputStream2 = outputStream;
                        try {
                            Log.i("beacon", e.getMessage(), e);
                            b.this.a("-100", e.getMessage());
                            if (outputStream2 != null) {
                                try {
                                    outputStream2.close();
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                } catch (IOException e3) {
                                    return Constants.MAIN_VERSION_TAG;
                                }
                            } else if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return Constants.MAIN_VERSION_TAG;
                        } catch (Throwable th) {
                            th = th;
                            outputStream = outputStream2;
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                } catch (IOException e4) {
                                    throw th;
                                }
                            } else if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader = bufferedReader2;
                        if (outputStream != null) {
                            outputStream.close();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                        } else if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        throw th;
                    }
                } catch (Exception e5) {
                    e = e5;
                    outputStream2 = outputStream;
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Exception e6) {
                e = e6;
                outputStream2 = null;
            } catch (Throwable th4) {
                th = th4;
                outputStream = null;
            }
        }

        boolean a(int i) {
            return i >= 200 && i < 300;
        }
    }

    /* JADX INFO: renamed from: com.alibaba.sdk.android.beacon.b$b, reason: collision with other inner class name */
    private static final class C0003b {
        final Map<String, String> a;
        final String c;
        final String d;
        final String e;
        final String f;
        final String g;
        final String h;
        final String i;
        final String mAppKey;
        final Map<String, String> mExtras;

        /* JADX INFO: renamed from: com.alibaba.sdk.android.beacon.b$b$a */
        static final class a {
            Map<String, String> b = new HashMap();
            String j;
            String k;
            String l;
            String m;
            String n;
            String o;
            String p;

            a() {
            }

            a a(String str) {
                this.j = str;
                return this;
            }

            a a(Map<String, String> map) {
                this.b.putAll(map);
                return this;
            }

            public C0003b a() {
                return new C0003b(this);
            }

            a b(String str) {
                this.k = str;
                return this;
            }

            a c(String str) {
                this.l = str;
                return this;
            }

            a d(String str) {
                this.m = str;
                return this;
            }

            a e(String str) {
                this.n = str;
                return this;
            }

            a f(String str) {
                this.o = str;
                return this;
            }

            a g(String str) {
                this.p = str;
                return this;
            }
        }

        private C0003b(a aVar) {
            this.a = new TreeMap();
            this.mAppKey = aVar.j;
            this.c = aVar.k;
            this.d = aVar.l;
            this.e = aVar.m;
            this.f = aVar.n;
            this.g = aVar.o;
            this.h = aVar.p;
            this.mExtras = aVar.b;
            this.i = a();
        }

        private String a() {
            this.a.put("appKey", this.mAppKey);
            this.a.put("appVer", this.d);
            this.a.put("osType", this.e);
            this.a.put("osVer", this.f);
            this.a.put(Constants.FLAG_DEVICE_ID, this.g);
            this.a.put("beaconVer", this.h);
            for (String str : this.mExtras.keySet()) {
                this.a.put(str, this.mExtras.get(str));
            }
            StringBuilder sb = new StringBuilder();
            for (String str2 : this.a.keySet()) {
                sb.append(str2).append(this.a.get(str2));
            }
            String strA = c.a(this.c, sb.toString());
            this.a.put("sign", strA);
            return strA;
        }
    }

    static {
        a = com.alibaba.sdk.android.beacon.a.a ? "100.67.64.54" : "beacon-api.aliyuncs.com";
        b = "http://" + a + "/beacon/fetch/config";
    }

    b(Beacon beacon) {
        this.f47a = beacon;
    }

    private C0003b a(Context context, String str, String str2, Map<String, String> map) {
        return new C0003b.a().a(str).b(str2).c(c.a(context)).d("Android").e(String.valueOf(Build.VERSION.SDK_INT)).f(UTDevice.getUtdid(context)).g("1.0").a(map).a();
    }

    private String a(C0003b c0003b) {
        Map<String, String> map = c0003b.a;
        StringBuilder sb = new StringBuilder();
        for (String str : map.keySet()) {
            sb.append(encode(str));
            sb.append("=");
            sb.append(encode(map.get(str)));
            sb.append("&");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private void a(String str) {
        b(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2) {
        this.f47a.a(new Beacon.Error(str, str2));
    }

    private void b(String str) {
        JSONArray jSONArrayOptJSONArray;
        try {
            if (TextUtils.isEmpty(str) || (jSONArrayOptJSONArray = new JSONObject(str).optJSONArray("result")) == null || jSONArrayOptJSONArray.length() <= 0) {
                return;
            }
            this.c.clear();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= jSONArrayOptJSONArray.length()) {
                    return;
                }
                JSONObject jSONObject = (JSONObject) jSONArrayOptJSONArray.get(i2);
                this.c.add(new Beacon.Config(jSONObject.optString(SettingsContentProvider.KEY), jSONObject.optString("value")));
                i = i2 + 1;
            }
        } catch (Exception e) {
        }
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Constants.MAIN_VERSION_TAG;
        }
    }

    List<Beacon.Config> a() {
        return Collections.unmodifiableList(this.c);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    void m30a(Context context, String str, String str2, Map<String, String> map) throws Throwable {
        C0003b c0003bA = a(context, str, str2, map);
        String str3 = b + "/byappkey";
        Log.i("beacon", "url=" + str3);
        String strA = this.f48a.a(str3, a(c0003bA).getBytes());
        Log.i("beacon", "[fetchByAppKey] result: " + strA);
        a(strA);
    }
}
