package com.youzan.androidsdk;

import android.content.Context;
import com.youzan.androidsdk.account.Token;
import com.youzan.androidsdk.tool.Preference;
import com.youzan.androidsdk.tool.UserAgent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class YouzanSDKAdapter {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private boolean f130 = false;

    public boolean isReady() {
        return this.f130;
    }

    public void init(Context context, String clientId) {
        this.f130 = true;
        UserAgent.setupUA(context, clientId, true);
        Preference.instance().init(context);
    }

    public void isDebug(boolean debug) {
        YouzanLog.isDebug(debug);
    }

    public void userLogout(Context context) {
        Token.clear(context);
    }

    public void sync(Context context, YouzanToken token) {
        Token.save(token);
    }
}
