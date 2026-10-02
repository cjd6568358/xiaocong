package com.youzan.spiderman.c.c;

import android.content.Context;
import com.youzan.spiderman.cache.d;
import com.youzan.spiderman.html.HtmlCallback;
import com.youzan.spiderman.html.e;
import com.youzan.spiderman.html.f;
import com.youzan.spiderman.html.h;
import com.youzan.spiderman.html.o;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.NetWorkUtil;
import java.util.List;

/* JADX INFO: compiled from: FetchHtmlJob.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a extends com.youzan.spiderman.a.a {
    private Context a;
    private h b;

    public a(Context context, h htmlConfigJudge) {
        this.a = context;
        this.b = htmlConfigJudge;
    }

    @Override // com.youzan.spiderman.a.a
    public void a() throws Throwable {
        if (!NetWorkUtil.hasNetworkPermission(this.a)) {
            Logger.e("FetchHtmlJob", "has no network permission to run fetch html job", new Object[0]);
        } else {
            b();
        }
    }

    private void b() {
        List<String> urls;
        b fetchHtmlPref = (b) d.a(b.class, "fetch_html_pref");
        if (this.b != null && this.b.b(fetchHtmlPref.a()) && (urls = this.b.c()) != null && !urls.isEmpty()) {
            f fetchingPool = f.a();
            for (String url : urls) {
                o htmlUrl = new o(url);
                if (!fetchingPool.a(htmlUrl)) {
                    e fetchSession = fetchingPool.b(htmlUrl);
                    fetchSession.a((HtmlCallback) null);
                }
            }
            a(fetchHtmlPref);
        }
    }

    @Override // com.youzan.spiderman.a.a
    public void a(Throwable throwable) {
        Logger.e("FetchHtmlJob", "fetch html have error: " + throwable.getMessage(), new Object[0]);
    }

    private void a(b fetchHtmlPref) {
        fetchHtmlPref.a(System.currentTimeMillis());
        d.a(fetchHtmlPref, "fetch_html_pref");
    }
}
