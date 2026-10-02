package com.ta.utdid2.a;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.ta.utdid2.b.a.d;
import com.ta.utdid2.b.a.f;
import com.tencent.android.tpush.common.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AidRequester.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static final String TAG = b.class.getName();
    private static b a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Object f103a = new Object();
    private Context mContext;

    /* JADX INFO: compiled from: AidRequester.java */
    class a extends Thread {
        com.ut.device.a a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        String f104a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        HttpPost f105a;

        /* JADX INFO: renamed from: b, reason: collision with other field name */
        String f106b;
        String c;
        String d;

        public a(HttpPost httpPost) {
            this.f104a = Constants.MAIN_VERSION_TAG;
            this.d = Constants.MAIN_VERSION_TAG;
            this.f105a = httpPost;
        }

        public a(HttpPost httpPost, com.ut.device.a aVar, String str, String str2, String str3) {
            this.f104a = Constants.MAIN_VERSION_TAG;
            this.d = Constants.MAIN_VERSION_TAG;
            this.f105a = httpPost;
            this.a = aVar;
            this.f106b = str;
            this.c = str2;
            this.d = str3;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            HttpResponse httpResponseExecute;
            BufferedReader bufferedReader = null;
            if (this.a != null) {
                this.a.a(1000, this.f106b);
            }
            try {
                httpResponseExecute = new DefaultHttpClient().execute(this.f105a);
            } catch (Exception e) {
                if (this.a != null) {
                    this.a.a(1002, this.f106b);
                }
                Log.e(b.TAG, e.toString());
                httpResponseExecute = null;
            }
            try {
                if (httpResponseExecute == null) {
                    Log.e(b.TAG, "response is null!");
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpResponseExecute.getEntity().getContent(), Charset.forName(HTTP.UTF_8)));
                }
            } catch (Exception e2) {
                if (this.a != null) {
                    this.a.a(1002, this.f106b);
                }
                Log.e(b.TAG, e2.toString());
            }
            try {
                if (bufferedReader != null) {
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (d.e) {
                            Log.d(b.TAG, line);
                        }
                        this.f104a = line;
                    }
                } else {
                    Log.e(b.TAG, "BufferredReader is null!");
                }
            } catch (Exception e3) {
                if (this.a != null) {
                    this.a.a(1002, this.f106b);
                }
                Log.e(b.TAG, e3.toString());
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    if (d.e) {
                        Log.d(b.TAG, "close the bufferreader");
                    }
                } catch (IOException e4) {
                    Log.e(b.TAG, e4.toString());
                }
            }
            if (this.a == null) {
                synchronized (b.this.f103a) {
                    b.this.f103a.notifyAll();
                }
            } else {
                String strA = b.a(this.f104a, this.f106b);
                this.a.a(1001, strA);
                c.a(b.this.mContext, this.c, strA, this.d);
            }
        }

        public String b() {
            return this.f104a;
        }
    }

    public static synchronized b a(Context context) {
        if (a == null) {
            a = new b(context);
        }
        return a;
    }

    public b(Context context) {
        this.mContext = context;
    }

    public void a(String str, String str2, String str3, String str4, com.ut.device.a aVar) {
        String strB = b(str, str2, str3, str4);
        if (d.e) {
            Log.d(TAG, "url:" + strB + "; len:" + strB.length());
        }
        new a(new HttpPost(strB), aVar, str4, str, str2).start();
    }

    public String a(String str, String str2, String str3, String str4) {
        String strB = b(str, str2, str3, str4);
        int i = f.b(this.mContext) ? PushConstants.WORK_RECEIVER_EVENTCORE_ERROR : 1000;
        if (d.e) {
            Log.d(TAG, "url:" + strB + "; timeout:" + i);
        }
        a aVar = new a(new HttpPost(strB));
        aVar.start();
        try {
            synchronized (this.f103a) {
                this.f103a.wait(i);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        String strB2 = aVar.b();
        if (d.e) {
            Log.d(TAG, "mLine:" + strB2);
        }
        return a(strB2, str4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String a(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("data")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    if (jSONObject2.has("action") && jSONObject2.has("aid")) {
                        String string = jSONObject2.getString("action");
                        if (string.equalsIgnoreCase("new") || string.equalsIgnoreCase("changed")) {
                            return jSONObject2.getString("aid");
                        }
                        return str2;
                    }
                    return str2;
                }
                if (jSONObject.has("isError") && jSONObject.has("status")) {
                    String string2 = jSONObject.getString("isError");
                    String string3 = jSONObject.getString("status");
                    if (string2.equalsIgnoreCase("true")) {
                        if (string3.equalsIgnoreCase("404") || string3.equalsIgnoreCase("401")) {
                            if (d.e) {
                                Log.d(TAG, "remove the AID, status:" + string3);
                            }
                            return Constants.MAIN_VERSION_TAG;
                        }
                        return str2;
                    }
                    return str2;
                }
                return str2;
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
                return str2;
            } catch (Exception e2) {
                Log.e(TAG, e2.toString());
                return str2;
            }
        }
        return str2;
    }

    private static String b(String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        try {
            str3 = URLEncoder.encode(str3, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.append("http://hydra.alibaba.com/").append(str).append("/get_aid/").append("?").append("auth[token]=").append(str2).append("&type=").append("utdid").append("&id=").append(str3).append("&aid=").append(str4).toString();
    }
}
