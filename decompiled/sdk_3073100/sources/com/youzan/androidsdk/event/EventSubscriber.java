package com.youzan.androidsdk.event;

import android.text.TextUtils;
import android.webkit.WebView;
import com.youzan.jsbridge.method.JsMethodCompat;
import com.youzan.systemweb.JsSubscriberCompat;
import com.youzan.systemweb.JsTrigger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class EventSubscriber extends JsSubscriberCompat {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private Event f96;

    public EventSubscriber(Event event) {
        this.f96 = event;
    }

    public void onCall(WebView webView, JsMethodCompat method, JsTrigger jsTrigger) {
        if (this.f96 != null) {
            String json = method.getParams();
            if ((this.f96 instanceof AbsAuthEvent) && (TextUtils.isEmpty(json) || json.trim().equals("{}") || !json.equals(EventAPI.SIGN_NOT_NEED_LOGIN))) {
                json = EventAPI.SIGN_NEED_LOGIN;
            }
            this.f96.call(webView.getContext(), json);
        }
    }

    public String subscribe() {
        if (this.f96 != null) {
            return this.f96.subscribe();
        }
        return null;
    }
}
