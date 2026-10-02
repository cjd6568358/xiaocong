package com.baidu.mobstat;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class al {
    private static String a;
    private static al b;
    private Handler c;

    static {
        a = Build.VERSION.SDK_INT < 9 ? "http://openrcv.baidu.com/1010/bplus.gif" : "https://openrcv.baidu.com/1010/bplus.gif";
    }

    private al() {
        HandlerThread handlerThread = new HandlerThread("LogSender");
        handlerThread.start();
        this.c = new Handler(handlerThread.getLooper());
    }

    public static al a() {
        if (b == null) {
            synchronized (al.class) {
                if (b == null) {
                    b = new al();
                }
            }
        }
        return b;
    }

    public void a(Context context, String str) {
        bd.a("data = " + str);
        if (str != null && !Constants.MAIN_VERSION_TAG.equals(str)) {
            this.c.post(new am(this, str, context));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) throws Throwable {
        cu.a("backups/system" + File.separator + "__send_log_data_" + System.currentTimeMillis(), str, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context) throws Throwable {
        File[] fileArrListFiles;
        if ("mounted".equals(cu.a())) {
            File file = new File(Environment.getExternalStorageDirectory(), "backups/system");
            if (file.exists() && (fileArrListFiles = file.listFiles()) != null && fileArrListFiles.length != 0) {
                try {
                    Arrays.sort(fileArrListFiles, new an(this));
                } catch (Exception e) {
                    bd.b(e);
                }
                int i = 0;
                for (File file2 : fileArrListFiles) {
                    if (file2.isFile()) {
                        String name = file2.getName();
                        if (!TextUtils.isEmpty(name) && name.startsWith("__send_log_data_")) {
                            String str = "backups/system" + File.separator + name;
                            String strB = cu.b(str);
                            if (b(context, strB)) {
                                cu.c(str);
                                i = 0;
                            } else {
                                a(strB, str);
                                i++;
                                if (i >= 5) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void a(String str, String str2) throws Throwable {
        JSONObject jSONObject;
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                jSONObject = new JSONObject(str);
            } catch (Exception e) {
                jSONObject = null;
            }
            JSONObject jSONObjectA = v.a(jSONObject);
            if (jSONObjectA != null) {
                v.b(jSONObjectA);
                cu.a(str2, jSONObject.toString(), false);
            }
        }
    }

    private boolean b(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            a(context, a, str);
            return true;
        } catch (Exception e) {
            bd.c(e);
            return false;
        }
    }

    private String a(Context context, String str, String str2) {
        byte[] bytes;
        boolean z = !str.startsWith("https:");
        HttpURLConnection httpURLConnectionD = cu.d(context, str);
        httpURLConnectionD.setDoOutput(true);
        httpURLConnectionD.setInstanceFollowRedirects(false);
        httpURLConnectionD.setUseCaches(false);
        httpURLConnectionD.setRequestProperty(HTTP.CONTENT_ENCODING, "gzip");
        httpURLConnectionD.connect();
        try {
            try {
                OutputStream outputStream = httpURLConnectionD.getOutputStream();
                GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(outputStream);
                gZIPOutputStream.write(new byte[]{72, 77, 48, 49});
                gZIPOutputStream.write(new byte[]{0, 0, 0, 1});
                gZIPOutputStream.write(new byte[]{0, 0, 3, -14});
                gZIPOutputStream.write(new byte[]{0, 0, 0, 0, 0, 0, 0, 0});
                gZIPOutputStream.write(new byte[]{0, 2});
                if (z) {
                    gZIPOutputStream.write(new byte[]{0, 1});
                } else {
                    gZIPOutputStream.write(new byte[]{0, 0});
                }
                gZIPOutputStream.write(new byte[]{72, 77, 48, 49});
                if (z) {
                    byte[] bArrA = cs.a();
                    byte[] bArrA2 = dc.a(false, cw.a(), bArrA);
                    gZIPOutputStream.write(a(bArrA2.length, 4));
                    gZIPOutputStream.write(bArrA2);
                    bytes = cs.a(bArrA, new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, str2.getBytes("utf-8"));
                    gZIPOutputStream.write(a(bytes.length, 2));
                } else {
                    bytes = str2.getBytes("utf-8");
                }
                gZIPOutputStream.write(bytes);
                gZIPOutputStream.close();
                outputStream.close();
                int responseCode = httpURLConnectionD.getResponseCode();
                int contentLength = httpURLConnectionD.getContentLength();
                bd.c("code: " + responseCode + "; len: " + contentLength);
                if (responseCode != 200 || contentLength != 0) {
                    throw new IOException("Response code = " + httpURLConnectionD.getResponseCode());
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnectionD.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        sb.append(line);
                    } else {
                        String string = sb.toString();
                        httpURLConnectionD.disconnect();
                        return string;
                    }
                }
            } catch (Exception e) {
                bd.b(e);
                httpURLConnectionD.disconnect();
                return Constants.MAIN_VERSION_TAG;
            }
        } catch (Throwable th) {
            httpURLConnectionD.disconnect();
            throw th;
        }
    }

    private static byte[] a(long j, int i) {
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[(i - i2) - 1] = (byte) (255 & j);
            j >>= 8;
        }
        return bArr;
    }
}
