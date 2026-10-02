package com.youzan.spiderman.cache;

import android.content.Context;
import com.youzan.spiderman.html.HtmlCacheStrategy;
import com.youzan.spiderman.html.HtmlCallback;
import com.youzan.spiderman.html.n;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SpiderMan {
    private static SpiderMan a = null;
    private static boolean b = true;
    private static SpiderCacheCallback c = null;
    private static WeakReference<SpiderCacheCallback> d = null;

    public static SpiderMan getInstance() {
        if (a == null) {
            a = new SpiderMan();
        }
        return a;
    }

    public static boolean isEnable() {
        return b;
    }

    private void a(Context context, String bizTag) {
        g.a(context);
        com.youzan.spiderman.b.c.a();
        com.youzan.spiderman.c.c.a(bizTag);
        com.youzan.spiderman.c.d.a(context);
    }

    public void init(Context context, String bizTag, SpiderCacheCallback cacheCallback) {
        c = cacheCallback;
        a(context, bizTag);
    }

    public void setWeakRefCacheCallback(SpiderCacheCallback cacheCallback) {
        d = new WeakReference<>(cacheCallback);
    }

    public SpiderCacheCallback getSpiderCacheCallback() {
        SpiderCacheCallback cacheCallback;
        return (d == null || (cacheCallback = d.get()) == null) ? c : cacheCallback;
    }

    public void sync(String token) {
        if (isEnable()) {
            com.youzan.spiderman.c.f.b.a().a(token);
        }
    }

    public void setHtmlCacheStrategy(HtmlCacheStrategy strategy) {
        if (isEnable()) {
            n.a().a(strategy);
        }
    }

    public void fetchHtml(Context context, String url, HtmlCallback htmlCallback) {
        if (isEnable()) {
            n.a().a(context, url, htmlCallback);
        }
    }

    public void preloadZipFromAsset(Context context, String path) {
        if (isEnable()) {
            c.a(context, path);
        }
    }

    public void preloadModifyFromRemote(Context context) {
        if (isEnable()) {
            com.youzan.spiderman.c.e.d.a().a(context);
            com.youzan.spiderman.c.c.c.a().a(context);
        }
    }

    public void initLru() {
        if (isEnable()) {
            com.youzan.spiderman.b.f.a().b();
        }
    }

    public void unInitLru() {
        if (isEnable()) {
            com.youzan.spiderman.b.f.a().c();
        }
    }
}
