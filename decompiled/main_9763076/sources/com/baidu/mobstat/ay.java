package com.baidu.mobstat;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ay extends Thread {
    private Context a;
    private l b;

    public ay(Context context, l lVar) {
        this.a = context;
        this.b = lVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            int i = bb.a ? 3 : 10;
            bd.a("start version check in " + i + NotifyType.SOUND);
            sleep(i * 1000);
            a();
            a(this.a);
        } catch (Exception e) {
            bd.a(e);
        }
        boolean unused = ax.b = false;
    }

    private void a(Context context) {
        this.b.a(context, System.currentTimeMillis());
    }

    /* JADX WARN: Code duplicated, block: B:15:0x00c9 A[Catch: all -> 0x00e7, TryCatch #0 {all -> 0x00e7, blocks: (B:5:0x002b, B:12:0x00b9, B:24:0x00e3, B:33:0x00f0, B:34:0x00f3, B:13:0x00bc, B:15:0x00c9, B:16:0x00cc, B:18:0x00d2, B:9:0x00a3, B:11:0x00b4, B:23:0x00e0), top: B:35:0x002b, outer: #2, inners: #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x00d2 A[Catch: all -> 0x00e7, TRY_LEAVE, TryCatch #0 {all -> 0x00e7, blocks: (B:5:0x002b, B:12:0x00b9, B:24:0x00e3, B:33:0x00f0, B:34:0x00f3, B:13:0x00bc, B:15:0x00c9, B:16:0x00cc, B:18:0x00d2, B:9:0x00a3, B:11:0x00b4, B:23:0x00e0), top: B:35:0x002b, outer: #2, inners: #1, #3 }] */
    private synchronized void a() {
        FileOutputStream fileOutputStreamOpenFileOutput = null;
        synchronized (this) {
            bd.a("start get config and download jar");
            Context context = this.a;
            l lVar = this.b;
            String strB = b(context);
            bd.c("update req url is:" + strB);
            HttpURLConnection httpURLConnectionD = cu.d(context, strB);
            try {
                httpURLConnectionD.connect();
                String headerField = httpURLConnectionD.getHeaderField("X-CONFIG");
                bd.a("config is: " + headerField);
                String headerField2 = httpURLConnectionD.getHeaderField("X-SIGN");
                bd.a("sign is: " + headerField2);
                int responseCode = httpURLConnectionD.getResponseCode();
                bd.a("update response code is: " + responseCode);
                int contentLength = httpURLConnectionD.getContentLength();
                bd.a("update response content length is: " + contentLength);
                if (responseCode == 200 && contentLength > 0) {
                    try {
                        try {
                            fileOutputStreamOpenFileOutput = context.openFileOutput(".remote.jar", 0);
                            if (da.a(httpURLConnectionD.getInputStream(), fileOutputStreamOpenFileOutput)) {
                                bd.a("save remote jar success");
                            }
                            da.a(fileOutputStreamOpenFileOutput);
                        } catch (IOException e) {
                            bd.b(e);
                            da.a(fileOutputStreamOpenFileOutput);
                            DexClassLoader unused = ax.a = null;
                            au.a();
                            if (!TextUtils.isEmpty(headerField)) {
                                lVar.a(context, headerField);
                            }
                            if (!TextUtils.isEmpty(headerField2)) {
                                lVar.b(context, headerField2);
                            }
                            httpURLConnectionD.disconnect();
                            bd.a("finish get config and download jar");
                        }
                    } catch (Throwable th) {
                        da.a(fileOutputStreamOpenFileOutput);
                        throw th;
                    }
                }
                DexClassLoader unused2 = ax.a = null;
                au.a();
                if (!TextUtils.isEmpty(headerField)) {
                    lVar.a(context, headerField);
                }
                if (!TextUtils.isEmpty(headerField2)) {
                    lVar.b(context, headerField2);
                }
                httpURLConnectionD.disconnect();
                bd.a("finish get config and download jar");
            } catch (Throwable th2) {
                httpURLConnectionD.disconnect();
                throw th2;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x00f9  */
    private String b(Context context) throws Throwable {
        String strB;
        File fileStreamPath;
        File fileStreamPath2 = context.getFileStreamPath(".remote.jar");
        if (fileStreamPath2 != null && fileStreamPath2.exists() && (fileStreamPath = context.getFileStreamPath(".remote.jar")) != null) {
            strB = ax.b(fileStreamPath.getAbsolutePath());
            bd.a("startDownload remote jar file version = " + strB);
            if (TextUtils.isEmpty(strB)) {
                strB = "14";
            }
        } else {
            strB = "14";
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicNameValuePair("dynamicVersion", Constants.MAIN_VERSION_TAG + strB));
        arrayList.add(new BasicNameValuePair(Constants.FLAG_PACKAGE_NAME, de.p(context)));
        arrayList.add(new BasicNameValuePair("appVersion", de.f(context)));
        arrayList.add(new BasicNameValuePair("cuid", de.a(context)));
        arrayList.add(new BasicNameValuePair("platform", "Android"));
        arrayList.add(new BasicNameValuePair("m", Build.MODEL));
        arrayList.add(new BasicNameValuePair(NotifyType.SOUND, Build.VERSION.SDK_INT + Constants.MAIN_VERSION_TAG));
        arrayList.add(new BasicNameValuePair("o", Build.VERSION.RELEASE));
        arrayList.add(new BasicNameValuePair("i", "14"));
        return bb.c + "?" + URLEncodedUtils.format(arrayList, "utf-8");
    }
}
