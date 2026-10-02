package com.youzan.androidsdk.basic;

import android.view.View;
import android.webkit.WebView;
import com.youzan.androidsdk.basic.web.plugin.a;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class YouzanBrowser$3 implements View.OnLongClickListener {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    final /* synthetic */ YouzanBrowser f15;

    YouzanBrowser$3(YouzanBrowser this$0) {
        this.f15 = this$0;
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        WebView webView;
        WebView.HitTestResult result;
        if (!(view instanceof WebView) || (result = (webView = (WebView) view).getHitTestResult()) == null) {
            return false;
        }
        int type = result.getType();
        if (type == 5 || type == 8) {
            return new a().m65(webView);
        }
        return false;
    }
}
