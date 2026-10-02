package com.youzan.spiderman.cache;

import android.content.Context;
import com.youzan.spiderman.utils.FileCallback;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.OkHttpUtil;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: compiled from: CacheDownLoader.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a {
    private static a a = null;
    private Set<String> b;
    private String c;
    private String d;
    private String e;

    public static a a() {
        if (a == null) {
            a = new a();
        }
        return a;
    }

    private a() {
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.b = new HashSet();
        this.c = g.c();
        this.d = g.f();
        this.e = g.g();
        b();
    }

    private void b() {
        File dir = new File(this.c);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dir2 = new File(this.d);
        if (!dir2.exists()) {
            dir2.mkdirs();
        }
        File dir3 = new File(this.e);
        if (!dir3.exists()) {
            dir3.mkdirs();
        }
    }

    public void a(Context context, final CacheUrl url, final FileCallback callback) {
        if (!c()) {
            Logger.e("CacheDownLoader", "downloading dir not exists and make dir failed", new Object[0]);
            return;
        }
        final String md5 = url.getMd5();
        if (!this.b.contains(md5)) {
            this.b.add(md5);
            final File downloadFile = new File(this.c, md5);
            OkHttpUtil.downloadFile(context, url.getUri().toString(), downloadFile, new FileCallback() { // from class: com.youzan.spiderman.cache.a.1
                @Override // com.youzan.spiderman.utils.FileCallback
                public void success() {
                    File preloadFile;
                    if (url.isScript()) {
                        preloadFile = new File(a.this.d, md5);
                    } else {
                        preloadFile = new File(a.this.e, md5);
                    }
                    boolean result = downloadFile.renameTo(preloadFile);
                    a.this.b.remove(md5);
                    if (!result) {
                        Logger.e("CacheDownLoader", "rename file failed, src file:" + downloadFile + " dest file:" + preloadFile, new Object[0]);
                        if (callback != null) {
                            callback.fail(-1, null);
                            return;
                        }
                        return;
                    }
                    com.youzan.spiderman.b.f.a().a(url, preloadFile);
                    if (callback != null) {
                        callback.success();
                    }
                }

                @Override // com.youzan.spiderman.utils.FileCallback
                public void fail(int errCode, Exception e) {
                    Logger.e("CacheDownLoader", "download file failed, url:" + url.getUri().toString(), new Object[0]);
                    a.this.b.remove(md5);
                    if (callback != null) {
                        callback.fail(errCode, e);
                    }
                }
            });
        } else if (callback != null) {
            callback.fail(-1, null);
        }
    }

    private boolean c() {
        File downFile = new File(this.c);
        return downFile.exists() || downFile.mkdirs();
    }
}
