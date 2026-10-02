package com.youzan.androidsdk.basic;

import android.content.Context;
import com.youzan.androidsdk.YouzanSDKAdapter;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.basic.tool.WebParameter;
import com.youzan.androidsdk.basic.tool.e;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class YouzanBasicSDKAdapter extends YouzanSDKAdapter {
    @Override // com.youzan.androidsdk.YouzanSDKAdapter
    public void init(Context context, String clientId) {
        super.init(context, clientId);
    }

    @Override // com.youzan.androidsdk.YouzanSDKAdapter
    public void isDebug(boolean debug) {
        super.isDebug(debug);
        WebParameter.webOpenDebug(debug);
    }

    @Override // com.youzan.androidsdk.YouzanSDKAdapter
    public void userLogout(Context context) {
        super.userLogout(context);
        e.ˊ(context);
    }

    @Override // com.youzan.androidsdk.YouzanSDKAdapter
    public void sync(Context context, YouzanToken token) {
        super.sync(context, token);
        WebUtil.sync(context, token);
    }
}
