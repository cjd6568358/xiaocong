package com.tencent.android.tpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGDownloadService extends Service {
    private static final String c = XGDownloadService.class.getSimpleName();
    private int a = 0;
    private String b = Constants.MAIN_VERSION_TAG;
    private File d = null;
    private File e = null;
    private NotificationManager f = null;
    private Notification g = null;
    private Intent h = null;
    private PendingIntent i = null;
    private Handler j = new b(this);

    public long a(String str, File file, int i) throws Throwable {
        InputStream inputStream;
        HttpURLConnection httpURLConnection;
        FileOutputStream fileOutputStream;
        long j = 0;
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection2.setRequestProperty(HTTP.USER_AGENT, "PacificHttpClient");
                httpURLConnection2.setConnectTimeout(Constants.ERRORCODE_UNKNOWN);
                httpURLConnection2.setReadTimeout(20000);
                int contentLength = httpURLConnection2.getContentLength();
                if (httpURLConnection2.getResponseCode() == 404) {
                    throw new Exception("fail!");
                }
                InputStream inputStream2 = httpURLConnection2.getInputStream();
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file, false);
                    try {
                        byte[] bArr = new byte[4096];
                        int i2 = 0;
                        while (true) {
                            int i3 = inputStream2.read(bArr);
                            if (i3 <= 0) {
                                break;
                            }
                            fileOutputStream2.write(bArr, 0, i3);
                            j += (long) i3;
                            if (i2 == 0 || ((int) ((100 * j) / ((long) contentLength))) - 10 > i2) {
                                i2 += 10;
                                this.f.notify(i, this.g);
                            }
                        }
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                        return j;
                    } catch (Throwable th) {
                        inputStream = inputStream2;
                        httpURLConnection = httpURLConnection2;
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (fileOutputStream == null) {
                            throw th;
                        }
                        fileOutputStream.close();
                        throw th;
                    }
                } catch (Throwable th2) {
                    httpURLConnection = httpURLConnection2;
                    th = th2;
                    fileOutputStream = null;
                    inputStream = inputStream2;
                }
            } catch (Throwable th3) {
                fileOutputStream = null;
                inputStream = null;
                httpURLConnection = httpURLConnection2;
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
            httpURLConnection = null;
            fileOutputStream = null;
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        int iA;
        Throwable th;
        int i3;
        this.b = intent.getStringExtra(Constants.FLAG_PACKAGE_DOWNLOAD_URL);
        try {
            iA = com.tencent.android.tpush.common.n.a((Context) this, "NOTIFY_ID", 0);
            if (iA >= 2147483646) {
                iA = 0;
            }
            try {
                com.tencent.android.tpush.common.n.b((Context) this, "NOTIFY_ID", iA + 1);
                i3 = iA;
            } catch (Throwable th2) {
                th = th2;
                com.tencent.android.tpush.a.a.c(c, Constants.MAIN_VERSION_TAG, th);
                i3 = iA;
            }
        } catch (Throwable th3) {
            iA = 0;
            th = th3;
        }
        if (com.tencent.android.tpush.service.e.m.c()) {
            this.d = new File(Environment.getExternalStorageDirectory(), "app/download/");
            this.e = new File(this.d.getPath(), "downloadApp" + i3 + ".apk");
        }
        this.f = (NotificationManager) getSystemService("notification");
        this.g = new Notification();
        this.g.icon = getApplicationInfo().icon;
        this.g.tickerText = "开始下载";
        this.f.notify(i3, this.g);
        com.tencent.android.tpush.common.g.a().a(new c(this, intent, i3));
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }
}
