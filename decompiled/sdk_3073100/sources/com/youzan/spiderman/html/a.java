package com.youzan.spiderman.html;

import android.content.Context;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.NetWorkUtil;

/* JADX INFO: compiled from: FetchEngine.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a {
    private j a = j.a();
    private f b = f.a();
    private com.youzan.spiderman.c.a.a c = com.youzan.spiderman.c.a.a.a();
    private HtmlCacheStrategy d = null;

    public void a(HtmlCacheStrategy strategy) {
        this.d = strategy;
    }

    public HtmlCacheStrategy a() {
        return this.d;
    }

    public HtmlResponse a(Context context, o htmlUrl, HtmlStatistic htmlStatistic) {
        HtmlResponse response = null;
        com.youzan.spiderman.c.b.d htmlConfig = this.c.g();
        h htmlConfigJudge = new h(context, this.d, htmlConfig);
        if (htmlConfigJudge.a()) {
            i htmlData = this.a.a(htmlUrl.c());
            if (htmlData != null || this.b.a(htmlUrl)) {
                e fetchSession = this.b.b(htmlUrl);
                response = fetchSession.a(htmlConfigJudge);
                if (htmlStatistic != null) {
                    htmlStatistic.setNeedRecord(true);
                    if (response != null) {
                        htmlStatistic.setPrefetch(true);
                    }
                }
            }
        }
        return response;
    }

    public void a(Context context, final o htmlUrl, final HtmlCallback htmlCallback) {
        com.youzan.spiderman.c.b.d htmlConfig = this.c.g();
        h htmlConfigJudge = new h(context, this.d, htmlConfig);
        if (!htmlConfigJudge.a()) {
            if (htmlCallback != null) {
                htmlCallback.onFailed();
            }
        } else {
            if (!NetWorkUtil.hasNetworkPermission(context)) {
                Logger.e("FetchEngine", "has no network permission to fetch html", new Object[0]);
                if (htmlCallback != null) {
                    htmlCallback.onFailed();
                    return;
                }
                return;
            }
            com.youzan.spiderman.a.c.a().a(new com.youzan.spiderman.a.a() { // from class: com.youzan.spiderman.html.a.1
                @Override // com.youzan.spiderman.a.a
                public void a() throws Throwable {
                    e fetchSession = a.this.b.b(htmlUrl);
                    fetchSession.a(htmlCallback);
                }

                @Override // com.youzan.spiderman.a.a
                public void a(Throwable throwable) {
                    Logger.e("FetchEngine", "exception url:" + htmlUrl.a(), throwable);
                }
            });
        }
    }
}
