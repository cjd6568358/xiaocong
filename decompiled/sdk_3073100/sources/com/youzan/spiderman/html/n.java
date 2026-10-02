package com.youzan.spiderman.html;

import android.content.Context;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.StringUtils;

/* JADX INFO: compiled from: HtmlManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class n {
    private com.youzan.spiderman.html.a a;

    /* JADX INFO: compiled from: HtmlManager.java */
    static class a {
        static n a = new n();
    }

    public static n a() {
        return a.a;
    }

    private n() {
        this.a = new com.youzan.spiderman.html.a();
    }

    public void a(HtmlCacheStrategy strategy) {
        this.a.a(strategy);
    }

    public HtmlCacheStrategy b() {
        return this.a.a();
    }

    public void a(Context context, String url, HtmlCallback htmlCallback) {
        if (context == null || StringUtils.isEmpty(url)) {
            Logger.e("HtmlManager", "fetchHtmlWith null context or url, return", new Object[0]);
        } else {
            o htmlUrl = new o(url);
            this.a.a(context, htmlUrl, htmlCallback);
        }
    }

    public HtmlResponse a(Context context, o htmlUrl, HtmlStatistic htmlStatistic) {
        return this.a.a(context, htmlUrl, htmlStatistic);
    }
}
