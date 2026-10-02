package com.baidu.cloud.media.download;

import android.content.Context;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class VideoDownloadManager {
    private static int c = 5;
    private static volatile VideoDownloadManager i;
    private static volatile String j;
    private String a;
    private String b;
    private Context f;
    private String g;
    private volatile int d = 0;
    private Queue<String> e = new LinkedList();
    private ConcurrentHashMap<String, b> h = new ConcurrentHashMap<>();

    private VideoDownloadManager(Context context, String str) {
        this.a = "_unk@nown_##$ default $##_unk@nown_";
        this.b = "4f0fecb0c26217433bafbf2a0d595c4e";
        this.f = context.getApplicationContext();
        this.a = str;
        this.b = a.b(str);
        f();
    }

    protected static VideoDownloadManager b() {
        return i;
    }

    private void f() {
        this.h.clear();
        try {
            for (Map.Entry entry : ((HashMap) this.f.getSharedPreferences(e(), 0).getAll()).entrySet()) {
                try {
                    String str = (String) entry.getKey();
                    JSONObject jSONObject = new JSONObject((String) entry.getValue());
                    jSONObject.put("urle", str);
                    this.h.put(jSONObject.getString("url"), b.a(this.f, e(), jSONObject));
                } catch (Exception e) {
                    Log.d("VideoDownloadManager", Constants.MAIN_VERSION_TAG + e.getMessage());
                }
            }
        } catch (Exception e2) {
            Log.d("VideoDownloadManager", Constants.MAIN_VERSION_TAG + e2.getMessage());
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x000e A[Catch: all -> 0x003b, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0006, B:10:0x0017, B:12:0x001f, B:14:0x0025, B:15:0x002d, B:17:0x0031, B:18:0x0038, B:7:0x000e), top: B:23:0x0006 }] */
    public static synchronized VideoDownloadManager getInstance(Context context, String str) {
        VideoDownloadManager videoDownloadManager = null;
        synchronized (VideoDownloadManager.class) {
            if (str == null) {
                Log.e("VideoDownloadManager", "getInstance failed, userName is null or empty.");
            } else if (str.equals(Constants.MAIN_VERSION_TAG)) {
                Log.e("VideoDownloadManager", "getInstance failed, userName is null or empty.");
            } else {
                if (!str.equals(j)) {
                    j = str;
                    if (i != null) {
                        i.stopAll();
                        i = null;
                    }
                }
                if (i == null) {
                    i = new VideoDownloadManager(context, str);
                }
                videoDownloadManager = i;
            }
        }
        return videoDownloadManager;
    }

    protected String a() {
        return this.g;
    }

    protected boolean a(String str) {
        boolean z = false;
        synchronized (this.e) {
            if (!this.e.contains(str)) {
                if (this.d >= c) {
                    this.e.offer(str);
                } else {
                    this.d++;
                    z = true;
                }
            }
        }
        return z;
    }

    protected void b(String str) {
        synchronized (this.e) {
            this.e.remove(str);
        }
    }

    protected void c() {
        synchronized (this.e) {
            if (!this.e.isEmpty()) {
                b bVar = this.h.get(this.e.poll());
                if (bVar.getStatus() == DownloadableVideoItem.DownloadStatus.PENDING) {
                    bVar.b();
                } else if (this.d > 0) {
                    this.d--;
                }
            } else if (this.d > 0) {
                this.d--;
            }
        }
    }

    public void changeMaxDownloadingItems(int i2) {
        if (i2 <= 0 || i2 > 10) {
            Log.e("VideoDownloadManager", "changeMaxDownloadingItems, maxItems should be 0<x<100");
        } else {
            c = i2;
        }
    }

    protected String d() {
        return this.b;
    }

    public void deleteDownloader(String str) {
        b bVar = this.h.get(str);
        if (bVar == null) {
            Log.e("VideoDownloadManager", "deleteDownloader but there is no downloader for url=" + str);
            return;
        }
        if (bVar.getStatus() == DownloadableVideoItem.DownloadStatus.PENDING) {
            b(str);
        }
        bVar.d();
        this.h.remove(str);
    }

    protected String e() {
        return "__cyberplayer_dl_" + d();
    }

    public HashMap<String, DownloadableVideoItem> getAllDownloadableVideoItems() {
        HashMap<String, DownloadableVideoItem> map = new HashMap<>();
        map.putAll(this.h);
        return map;
    }

    public String getDownloadRootForCurrentUser() {
        File externalFilesDir = this.f.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            return externalFilesDir.getAbsolutePath() + "/cyberplayer_download_videos/" + d() + "/";
        }
        return null;
    }

    public DownloadableVideoItem getDownloadableVideoItemByUrl(String str) {
        return this.h.get(str);
    }

    public String getUserName() {
        return this.a;
    }

    public void pauseDownloader(String str) {
        b bVar = this.h.get(str);
        if (bVar == null) {
            Log.e("VideoDownloadManager", "pauseDownloader but there is no downloader for url=" + str);
            return;
        }
        if (bVar.getStatus() == DownloadableVideoItem.DownloadStatus.PENDING) {
            b(str);
        }
        bVar.c();
    }

    public void setCustomizedPlayerId(String str) {
        this.g = str;
    }

    public void startOrResumeDownloader(String str, DownloadObserver downloadObserver) {
        startOrResumeDownloaderWithToken(str, null, downloadObserver);
    }

    public void startOrResumeDownloaderWithToken(String str, String str2, DownloadObserver downloadObserver) {
        if (str == null) {
            Log.e("VideoDownloadManager", "url is null");
            return;
        }
        b bVar = this.h.get(str);
        if (bVar == null) {
            String strB = a.b(str);
            String downloadRootForCurrentUser = getDownloadRootForCurrentUser();
            bVar = new b(this.f, str, strB, downloadRootForCurrentUser != null ? downloadRootForCurrentUser + strB + "/" : null, strB + ".m3u8", e());
            this.h.put(str, bVar);
        }
        if (downloadObserver != null) {
            bVar.addObserver(downloadObserver);
        }
        if (str2 != null && !str2.equals(Constants.MAIN_VERSION_TAG)) {
            bVar.a(str2);
        }
        if (a(str)) {
            bVar.b();
        } else {
            bVar.a(DownloadableVideoItem.DownloadStatus.PENDING);
            Log.w("VideoDownloadManager", "startOrResumeDownloader: too many tasks are downloading , this task is suspending now(will download automatically after other task down)");
        }
    }

    public void stopAll() {
        try {
            Iterator<Map.Entry<String, b>> it = this.h.entrySet().iterator();
            while (it.hasNext()) {
                it.next().getValue().c();
            }
        } catch (Exception e) {
            Log.d("VideoDownloadManager", Constants.MAIN_VERSION_TAG + e.getMessage());
        }
    }
}
