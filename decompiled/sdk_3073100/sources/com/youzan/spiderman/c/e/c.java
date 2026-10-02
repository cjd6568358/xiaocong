package com.youzan.spiderman.c.e;

import android.content.Context;
import com.youzan.spiderman.c.b.g;
import com.youzan.spiderman.cache.CacheUrl;
import com.youzan.spiderman.utils.FileCallback;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.Stone;
import com.youzan.spiderman.utils.StringUtils;
import java.io.File;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: SyncDownloadJob.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c extends com.youzan.spiderman.a.a {
    private int a;
    private Set<String> b;
    private Set<String> c = new HashSet();
    private Context d;
    private g e;
    private a f;

    public c(Set<String> resources, Context context, g syncConfig, a downloadCallback) {
        this.b = resources;
        this.a = resources.size();
        this.d = context;
        this.e = syncConfig;
        this.f = downloadCallback;
    }

    @Override // com.youzan.spiderman.a.a
    public void a() throws Throwable {
        b();
    }

    @Override // com.youzan.spiderman.a.a
    public void a(Throwable throwable) {
        Logger.e("SyncDownloadJob", "sync download job exception", throwable);
    }

    private void b() {
        com.youzan.spiderman.cache.e cacheSearcher = com.youzan.spiderman.cache.e.a();
        boolean isEnableCondition = this.e.a(this.d);
        boolean isNotDownload = this.e.b();
        Iterator<String> iterator = this.b.iterator();
        while (iterator.hasNext()) {
            String path = iterator.next();
            if (!path.startsWith("/") && !StringUtils.isStartWith(path, Stone.SUPPORTED_SCHEME)) {
                path = "/" + path;
            }
            final String rawPath = path;
            final CacheUrl cacheUrl = com.youzan.spiderman.cache.f.a(rawPath);
            File cacheFile = cacheSearcher.a(cacheUrl);
            if (cacheUrl != null && cacheFile == null && !isNotDownload && (isEnableCondition || cacheUrl.isScript())) {
                try {
                    a(cacheUrl, new FileCallback() { // from class: com.youzan.spiderman.c.e.c.1
                        @Override // com.youzan.spiderman.utils.FileCallback
                        public void success() {
                            c.this.c.add(rawPath);
                            c.this.c();
                        }

                        @Override // com.youzan.spiderman.utils.FileCallback
                        public void fail(int errCode, Exception e) {
                            if (errCode == 404) {
                                c.this.a(cacheUrl, rawPath);
                            } else if (e instanceof UnknownHostException) {
                                c.this.a(cacheUrl, rawPath);
                            } else {
                                c.this.c();
                            }
                        }
                    });
                } catch (Exception e) {
                    Logger.e("SyncDownloadJob", "download file have a crash error!", e);
                    c();
                }
            } else if (cacheFile != null) {
                iterator.remove();
                c();
            } else {
                c();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(CacheUrl oldUrl, final String rawPath) {
        CacheUrl newUrl = com.youzan.spiderman.cache.f.a(oldUrl, rawPath);
        if (newUrl != null) {
            a(newUrl, new FileCallback() { // from class: com.youzan.spiderman.c.e.c.2
                @Override // com.youzan.spiderman.utils.FileCallback
                public void success() {
                    c.this.c.add(rawPath);
                    c.this.c();
                }

                @Override // com.youzan.spiderman.utils.FileCallback
                public void fail(int code, Exception e) {
                    c.this.c();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void c() {
        this.a--;
        if (this.a == 0) {
            this.b.removeAll(this.c);
            if (this.f != null) {
                this.f.a(this, this.b);
            }
            this.b.clear();
            this.c.clear();
        }
    }

    public void a(CacheUrl url, FileCallback callback) {
        com.youzan.spiderman.cache.a.a().a(this.d, url, callback);
    }
}
