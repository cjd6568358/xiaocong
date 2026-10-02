package com.youzan.spiderman.cache;

import android.net.Uri;
import android.text.TextUtils;
import com.youzan.spiderman.html.o;
import com.youzan.spiderman.utils.Stone;
import com.youzan.spiderman.utils.StringUtils;
import com.youzan.spiderman.utils.UriUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: compiled from: CacheFilter.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    private Set<String> a;
    private Set<String> b;
    private Set<String> c;
    private Set<String> d;
    private Set<String> e;
    private Set<String> f;

    /* JADX INFO: compiled from: CacheFilter.java */
    private static class a {
        static b a = new b();
    }

    public static b a() {
        return a.a;
    }

    private b() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.a = new HashSet(Arrays.asList(Stone.SUPPORTED_HOST));
        this.b = new HashSet(Arrays.asList(Stone.SUPPORTED_SCHEME));
        this.c = new HashSet(Arrays.asList(Stone.SUPPORTED_EXTEND));
        this.f = new HashSet(Arrays.asList(Stone.SUPPORTED_HTML_HOST));
        b();
        c();
    }

    private void b() {
        List<String> ignoreExtension;
        if (this.d == null) {
            this.d = new HashSet();
            com.youzan.spiderman.c.a.b configPref = (com.youzan.spiderman.c.a.b) d.a(com.youzan.spiderman.c.a.b.class, "config_pref");
            if (configPref != null && (ignoreExtension = configPref.a().b().a().c()) != null) {
                this.d.addAll(ignoreExtension);
            }
        }
    }

    public void a(List<String> list) {
        this.d.addAll(list);
    }

    private void c() {
        List<String> ignoreResource;
        if (this.e == null) {
            this.e = new HashSet();
            com.youzan.spiderman.c.a.b configPref = (com.youzan.spiderman.c.a.b) d.a(com.youzan.spiderman.c.a.b.class, "config_pref");
            if (configPref != null && (ignoreResource = configPref.a().b().a().a()) != null) {
                this.e.addAll(ignoreResource);
            }
        }
    }

    public void b(List<String> list) {
        this.e.addAll(list);
    }

    private boolean a(String extend, String host, String scheme) {
        return !TextUtils.isEmpty(extend) && this.c.contains(extend) && this.a.contains(host) && this.b.contains(scheme);
    }

    private boolean a(String extend, String path) {
        return (this.d.contains(extend) || this.e.contains(path)) ? false : true;
    }

    public boolean a(CacheUrl cacheUrl) {
        String extend = cacheUrl.getExtend();
        Uri uri = cacheUrl.getUri();
        return a(extend, uri.getHost(), uri.getScheme()) && a(extend, uri.getPath());
    }

    public boolean a(o htmlUrl) {
        Uri uri = htmlUrl.b();
        if (!this.f.contains(uri.getHost()) || !this.b.contains(uri.getScheme())) {
            return false;
        }
        String extend = UriUtil.getUriExtend(uri);
        return StringUtils.isEmpty(extend) || extend.equals("html") || extend.equals("html");
    }
}
