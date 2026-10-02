package com.baidu.cloud.media.download;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import com.meizu.cloud.pushsdk.notification.model.TimeDisplaySetting;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b extends DownloadableVideoItem {
    private volatile boolean k;
    private ArrayList<String> l;
    private String m;
    private String n;
    private Context o;
    private ExecutorService i = null;
    private volatile int j = 0;
    private String p = null;

    public b(Context context, String str, String str2, String str3, String str4, String str5) {
        this.m = Constants.MAIN_VERSION_TAG;
        this.n = Constants.MAIN_VERSION_TAG;
        this.o = context;
        this.b = str;
        this.m = str2;
        this.c = str3;
        this.d = str4;
        this.n = str5;
    }

    public static b a(Context context, String str, JSONObject jSONObject) {
        Exception e;
        b bVar;
        try {
            String string = jSONObject.getString("url");
            String string2 = jSONObject.getString("urle");
            String strOptString = jSONObject.optString("fold", null);
            String string3 = jSONObject.getString("file");
            int i = jSONObject.getInt(TimeDisplaySetting.START_SHOW_TIME);
            int i2 = jSONObject.getInt("prgr");
            int i3 = jSONObject.getInt("tsdl");
            bVar = new b(context, string, string2, strOptString, string3, str);
            try {
                bVar.a(i, i2, i3);
            } catch (Exception e2) {
                e = e2;
                Log.e("HLSVideoDownloader", Constants.MAIN_VERSION_TAG + Log.getStackTraceString(e));
            }
        } catch (Exception e3) {
            e = e3;
            bVar = null;
        }
        return bVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(c.a aVar) {
        if (this.j >= this.l.size()) {
            j();
            return;
        }
        c cVar = new c(this.l.get(this.j), this.c + "/" + (this.j + 1) + ".ts", aVar);
        if (this.i == null || this.i.isShutdown()) {
            Log.d("HLSVideoDownloader", "new executor is created now");
            this.i = Executors.newSingleThreadExecutor();
        }
        this.i.execute(cVar);
    }

    static /* synthetic */ int e(b bVar) {
        int i = bVar.j;
        bVar.j = i + 1;
        return i;
    }

    private void e() {
        try {
            if (!new URL(this.b).getPath().endsWith(".m3u8")) {
                this.g = "only download m3u8 video";
                this.h = 1;
                a(DownloadableVideoItem.DownloadStatus.ERROR);
                return;
            }
            if (this.c == null || this.c.equals(Constants.MAIN_VERSION_TAG)) {
                String downloadRootForCurrentUser = VideoDownloadManager.b().getDownloadRootForCurrentUser();
                if (downloadRootForCurrentUser == null) {
                    this.g = "sdcard is unmounted";
                    this.h = 3;
                    a(DownloadableVideoItem.DownloadStatus.ERROR);
                    return;
                }
                this.c = downloadRootForCurrentUser + this.m + "/";
            }
            if (!this.k) {
                if (!h()) {
                    if (!f()) {
                        a(DownloadableVideoItem.DownloadStatus.ERROR);
                        return;
                    }
                    Runnable runnable = new Runnable() { // from class: com.baidu.cloud.media.download.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            new d(b.this.o, b.this.c + "/" + b.this.d).a(b.this.b, b.this.p, new d.a() { // from class: com.baidu.cloud.media.download.b.1.1
                                @Override // com.baidu.cloud.media.download.d.a
                                public void a(int i) {
                                    if (b.this.f == DownloadableVideoItem.DownloadStatus.DOWNLOADING) {
                                        Log.d("HLSVideoDownloader", "Parse failed: error code is " + i);
                                        b.this.g = "parse M3U8 failed, reason = " + DownloadableVideoItem.a[i];
                                        b.this.h = i;
                                        b.this.a(DownloadableVideoItem.DownloadStatus.ERROR);
                                    }
                                }

                                @Override // com.baidu.cloud.media.download.d.a
                                public void a(List<String> list) throws Throwable {
                                    if (b.this.f == DownloadableVideoItem.DownloadStatus.DOWNLOADING) {
                                        b.this.l = (ArrayList) list;
                                        b.this.g();
                                        b.this.k = true;
                                        b.this.i();
                                    }
                                }
                            });
                        }
                    };
                    if (this.i == null || this.i.isShutdown()) {
                        Log.d("HLSVideoDownloader", "new executor is created now to download m3u8 file");
                        this.i = Executors.newSingleThreadExecutor();
                    }
                    this.i.execute(runnable);
                    return;
                }
                this.k = true;
            }
            i();
        } catch (Exception e) {
            Log.d("HLSVideoDownloader", Constants.MAIN_VERSION_TAG + e.getMessage());
            this.g = "url format is invalid";
            this.h = 1;
            a(DownloadableVideoItem.DownloadStatus.ERROR);
        }
    }

    private boolean f() {
        boolean z = true;
        try {
            if (!"mounted".equals(Environment.getExternalStorageState())) {
                this.g = "save file failed, sdcard unmounted";
                this.h = 3;
                z = false;
            }
            if (a.a(this.o)) {
                return z;
            }
            this.g = "network is not available";
            this.h = 2;
            return false;
        } catch (Exception e) {
            boolean z2 = z;
            Log.d("HLSVideoDownloader", "checkEnvironment " + e.getMessage());
            return z2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:38:0x0068 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public void g() throws Throwable {
        FileOutputStream fileOutputStream;
        if (this.l == null || this.l.size() <= 0) {
            return;
        }
        try {
            try {
                fileOutputStream = new FileOutputStream(this.c + "/tsdl.data");
                try {
                    new ObjectOutputStream(fileOutputStream).writeObject(this.l);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    Log.d("HLSVideoDownloader", Constants.MAIN_VERSION_TAG + e.getMessage());
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                        }
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            fileOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x008c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.FileInputStream] */
    private boolean h() throws Throwable {
        FileInputStream fileInputStream;
        ?? r2 = this.c + "/" + this.d;
        if (!new File((String) r2).exists()) {
            return false;
        }
        if (this.l == null) {
            try {
                try {
                    fileInputStream = new FileInputStream(this.c + "/tsdl.data");
                    try {
                        this.l = (ArrayList) new ObjectInputStream(fileInputStream).readObject();
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e) {
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        Log.d("HLSVideoDownloader", Constants.MAIN_VERSION_TAG + e.getMessage());
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e3) {
                            }
                        }
                        return false;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (r2 != 0) {
                        try {
                            r2.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                fileInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                r2 = 0;
                if (r2 != 0) {
                    r2.close();
                }
                throw th;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        if (this.l == null || this.l.size() == 0) {
            this.g = "tsList.size == 0";
            this.h = 4;
            a(DownloadableVideoItem.DownloadStatus.ERROR);
        } else if (f()) {
            a(new c.a() { // from class: com.baidu.cloud.media.download.b.2
                @Override // com.baidu.cloud.media.download.c.a
                public void a(int i) {
                    if (i == 1) {
                        if (b.this.f == DownloadableVideoItem.DownloadStatus.PAUSED || b.this.f == DownloadableVideoItem.DownloadStatus.DELETED) {
                            return;
                        }
                        b.e(b.this);
                        b.this.a();
                        b.this.a(this);
                        return;
                    }
                    if (b.this.f == DownloadableVideoItem.DownloadStatus.PAUSED || b.this.f == DownloadableVideoItem.DownloadStatus.DELETED) {
                        return;
                    }
                    if (i == -2) {
                        b.this.g = "network has problem";
                        b.this.h = 2;
                    } else {
                        b.this.g = "save the " + b.this.j + "th ts file - interrupted";
                        b.this.h = 7;
                    }
                    b.this.a(DownloadableVideoItem.DownloadStatus.ERROR);
                }
            });
        } else {
            a(DownloadableVideoItem.DownloadStatus.ERROR);
        }
    }

    private void j() {
        a(DownloadableVideoItem.DownloadStatus.COMPLETED);
    }

    private void k() {
        String strL;
        if (this.f == DownloadableVideoItem.DownloadStatus.DELETED || (strL = l()) == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.o.getSharedPreferences(this.n, 0).edit();
        editorEdit.putString(this.m, strL);
        if (Build.VERSION.SDK_INT >= 9) {
            editorEdit.apply();
        } else {
            editorEdit.commit();
        }
    }

    private String l() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("url", this.b);
            jSONObject.put("fold", this.c);
            jSONObject.put("file", this.d);
            jSONObject.put(TimeDisplaySetting.START_SHOW_TIME, this.f.getCode());
            jSONObject.put("prgr", this.e);
            jSONObject.put("tsdl", this.j);
            return jSONObject.toString();
        } catch (Exception e) {
            Log.e("HLSVideoDownloader", Constants.MAIN_VERSION_TAG + e.getMessage());
            return null;
        }
    }

    protected void a() {
        if (this.f == DownloadableVideoItem.DownloadStatus.DOWNLOADING && this.l != null && this.l.size() > 0) {
            this.e = Math.round((this.j / this.l.size()) * 10000.0f);
        }
        k();
        setChanged();
        notifyObservers();
    }

    protected void a(int i, int i2, int i3) {
        this.e = i2;
        this.j = i3;
        DownloadableVideoItem.DownloadStatus downloadStatus = DownloadableVideoItem.DownloadStatus.values()[i];
        if (downloadStatus == DownloadableVideoItem.DownloadStatus.DOWNLOADING || downloadStatus == DownloadableVideoItem.DownloadStatus.PENDING) {
            downloadStatus = DownloadableVideoItem.DownloadStatus.PAUSED;
        }
        this.f = downloadStatus;
    }

    protected void a(DownloadableVideoItem.DownloadStatus downloadStatus) {
        VideoDownloadManager videoDownloadManagerB;
        if (this.f == DownloadableVideoItem.DownloadStatus.DOWNLOADING && downloadStatus != DownloadableVideoItem.DownloadStatus.DOWNLOADING && (videoDownloadManagerB = VideoDownloadManager.b()) != null) {
            videoDownloadManagerB.c();
        }
        this.f = downloadStatus;
        if (downloadStatus != DownloadableVideoItem.DownloadStatus.PAUSED && downloadStatus != DownloadableVideoItem.DownloadStatus.ERROR) {
            this.g = Constants.MAIN_VERSION_TAG;
        }
        if (downloadStatus != DownloadableVideoItem.DownloadStatus.ERROR) {
            this.h = 0;
        }
        a();
    }

    public void a(String str) {
        this.p = str;
    }

    public boolean b() {
        if (this.f == DownloadableVideoItem.DownloadStatus.DOWNLOADING) {
            Log.e("HLSVideoDownloader", "start failed because downloadStatus = " + this.f.name());
            return false;
        }
        a(DownloadableVideoItem.DownloadStatus.DOWNLOADING);
        e();
        return true;
    }

    public boolean c() {
        if (this.f == DownloadableVideoItem.DownloadStatus.PAUSED || this.f == DownloadableVideoItem.DownloadStatus.COMPLETED || this.f == DownloadableVideoItem.DownloadStatus.DELETED) {
            Log.e("HLSVideoDownloader", "pause not work, && downloadStatus = " + this.f.name());
            return false;
        }
        if (this.i != null && !this.i.isShutdown()) {
            this.i.shutdownNow();
        }
        this.g = "manually pause";
        a(DownloadableVideoItem.DownloadStatus.PAUSED);
        return true;
    }

    public boolean d() {
        try {
            a(DownloadableVideoItem.DownloadStatus.DELETED);
            if (this.i != null && !this.i.isTerminated() && !this.i.isShutdown()) {
                this.i.shutdownNow();
            }
            SharedPreferences.Editor editorEdit = this.o.getSharedPreferences(this.n, 0).edit();
            editorEdit.remove(this.m);
            if (Build.VERSION.SDK_INT >= 9) {
                editorEdit.apply();
            } else {
                editorEdit.commit();
            }
            if (this.c != null) {
                File file = new File(this.c);
                if (file.exists() && file.isDirectory()) {
                    File[] fileArrListFiles = file.listFiles();
                    for (int i = 0; i < fileArrListFiles.length; i++) {
                        if (fileArrListFiles[i].isFile()) {
                            fileArrListFiles[i].delete();
                        }
                    }
                }
            }
            this.c = Constants.MAIN_VERSION_TAG;
            this.d = Constants.MAIN_VERSION_TAG;
            this.e = 0;
            this.h = 0;
            this.g = "delete manually";
            return true;
        } catch (Exception e) {
            Log.e("HLSVideoDownloader", Constants.MAIN_VERSION_TAG + e.getMessage());
            return true;
        }
    }
}
