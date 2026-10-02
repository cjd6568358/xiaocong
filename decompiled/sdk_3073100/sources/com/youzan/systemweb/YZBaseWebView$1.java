package com.youzan.systemweb;

import com.youzan.spiderman.cache.CacheHandler;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class YZBaseWebView$1 implements CacheHandler.HandlerCallback {
    final /* synthetic */ YZBaseWebView this$0;

    YZBaseWebView$1(YZBaseWebView this$0) {
        this.this$0 = this$0;
    }

    @Override // com.youzan.spiderman.cache.CacheHandler.HandlerCallback
    public void onCacheStatistic(Map<String, String> staticData) {
        YZBaseWebView.access$000(this.this$0, "yz_webview_load_request", "WebView 加载时间统计", staticData);
    }

    @Override // com.youzan.spiderman.cache.CacheHandler.HandlerCallback
    public void onHtmlStatistic(Map<String, String> staticData) {
        YZBaseWebView.access$000(this.this$0, "yz_webview_html_prefetch", "html prefetch统计", staticData);
    }
}
