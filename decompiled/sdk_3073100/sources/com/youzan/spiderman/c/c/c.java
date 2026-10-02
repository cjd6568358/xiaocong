package com.youzan.spiderman.c.c;

import android.content.Context;
import com.youzan.spiderman.c.b.d;
import com.youzan.spiderman.html.HtmlCacheStrategy;
import com.youzan.spiderman.html.h;
import com.youzan.spiderman.html.n;

/* JADX INFO: compiled from: SyncHtmlManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c {
    private static c a = null;

    public static c a() {
        if (a == null) {
            a = new c();
        }
        return a;
    }

    private c() {
    }

    public void a(Context context) {
        d htmlConfig = com.youzan.spiderman.c.a.a.a().g();
        HtmlCacheStrategy htmlCacheStrategy = n.a().b();
        h htmlConfigJudge = new h(context, htmlCacheStrategy, htmlConfig);
        if (htmlConfigJudge.a()) {
            com.youzan.spiderman.a.c.a().a(new a(context, htmlConfigJudge));
        }
    }
}
