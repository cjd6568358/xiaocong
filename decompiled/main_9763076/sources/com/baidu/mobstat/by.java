package com.baidu.mobstat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.zip.GZIPOutputStream;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class by {
    private static by a = new by();
    private boolean b = false;
    private int c = 0;
    private int d = 1;
    private SendStrategyEnum e = SendStrategyEnum.APP_START;
    private Timer f;
    private Handler g;

    public static by a() {
        return a;
    }

    private by() {
        HandlerThread handlerThread = new HandlerThread("LogSenderThread");
        handlerThread.start();
        this.g = new Handler(handlerThread.getLooper());
    }

    public void a(Context context) {
        if (context != null) {
            context = context.getApplicationContext();
        }
        if (context != null) {
            this.g.post(new bz(this, context));
        }
    }

    public void b(Context context) {
        Context applicationContext = context.getApplicationContext();
        long j = this.d * 3600000;
        this.f = new Timer();
        this.f.schedule(new cb(this, applicationContext), j, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Context context) {
        if (!this.b || de.n(context)) {
            this.g.post(new cc(this, context));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String str, String str2) throws Throwable {
        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject(str2);
        } catch (Exception e) {
        }
        if (jSONObject != null) {
            try {
                JSONObject jSONObject2 = (JSONObject) jSONObject.get("trace");
                jSONObject2.put("failed_cnt", jSONObject2.getLong("failed_cnt") + 1);
            } catch (Exception e2) {
            }
            cu.a(context, str, jSONObject.toString(), false);
        }
    }

    public void a(Context context, String str) throws Throwable {
        cu.a(context, "__send_data_" + System.currentTimeMillis(), str, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(Context context, String str) {
        boolean z = false;
        if (!this.b || de.n(context)) {
            try {
                c(context, Config.LOG_SEND_URL, str);
                z = true;
            } catch (Exception e) {
                db.c(e);
            }
            db.a("send log data over. result = " + z + "; data = " + str);
        }
        return z;
    }

    private String c(Context context, String str, String str2) {
        return !str.startsWith("https://") ? e(context, str, str2) : d(context, str, str2);
    }

    private String d(Context context, String str, String str2) {
        HttpURLConnection httpURLConnectionD = cu.d(context, str);
        httpURLConnectionD.setDoOutput(true);
        httpURLConnectionD.setInstanceFollowRedirects(false);
        httpURLConnectionD.setUseCaches(false);
        httpURLConnectionD.setRequestProperty(HTTP.CONTENT_TYPE, "gzip");
        httpURLConnectionD.connect();
        db.a("AdUtil.httpPost connected");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(httpURLConnectionD.getOutputStream())));
            bufferedWriter.write(str2);
            bufferedWriter.flush();
            bufferedWriter.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnectionD.getInputStream()));
            StringBuilder sb = new StringBuilder();
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                sb.append(line);
            }
            int contentLength = httpURLConnectionD.getContentLength();
            if (httpURLConnectionD.getResponseCode() != 200 || contentLength != 0) {
                throw new IOException("http code = " + httpURLConnectionD.getResponseCode() + "; contentResponse = " + ((Object) sb));
            }
            String string = sb.toString();
            httpURLConnectionD.disconnect();
            return string;
        } catch (Throwable th) {
            httpURLConnectionD.disconnect();
            throw th;
        }
    }

    private String e(Context context, String str, String str2) {
        db.a("httpPostEncrypt");
        HttpURLConnection httpURLConnectionD = cu.d(context, str);
        httpURLConnectionD.setDoOutput(true);
        httpURLConnectionD.setInstanceFollowRedirects(false);
        httpURLConnectionD.setUseCaches(false);
        httpURLConnectionD.setRequestProperty(HTTP.CONTENT_TYPE, "gzip");
        byte[] bArrA = cs.a();
        byte[] bArrB = cs.b();
        httpURLConnectionD.setRequestProperty(SettingsContentProvider.KEY, dc.a(bArrA));
        httpURLConnectionD.setRequestProperty("iv", dc.a(bArrB));
        byte[] bArrA2 = cs.a(bArrA, bArrB, str2.getBytes("utf-8"));
        httpURLConnectionD.connect();
        db.a("AdUtil.httpPost connected");
        try {
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(httpURLConnectionD.getOutputStream());
            gZIPOutputStream.write(bArrA2);
            gZIPOutputStream.flush();
            gZIPOutputStream.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnectionD.getInputStream()));
            StringBuilder sb = new StringBuilder();
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                sb.append(line);
            }
            int contentLength = httpURLConnectionD.getContentLength();
            if (httpURLConnectionD.getResponseCode() != 200 || contentLength != 0) {
                throw new IOException("http code = " + httpURLConnectionD.getResponseCode() + "; contentResponse = " + ((Object) sb));
            }
            String string = sb.toString();
            httpURLConnectionD.disconnect();
            return string;
        } catch (Throwable th) {
            httpURLConnectionD.disconnect();
            throw th;
        }
    }
}
