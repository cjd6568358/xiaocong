package com.ixiaocong.smarthome.phone.android.event.receiver;

import android.content.Context;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.xiaocong.smarthome.httplib.helper.XcLogger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MzPushReceiver extends MzPushMessageReceiver {
    private String MZ_TAG = "MzPushReceiver";

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onRegister(Context context, String s) {
        XcLogger.w(this.MZ_TAG, "onRegister = " + s);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onMessage(Context context, String s) {
        XcLogger.w(this.MZ_TAG, "onMessage = " + s);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onUnRegister(Context context, boolean b) {
        XcLogger.w(this.MZ_TAG, "onUnRegister = " + b);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        XcLogger.w(this.MZ_TAG, "onPushStatus getPushId= " + pushSwitchStatus.getPushId());
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        XcLogger.w(this.MZ_TAG, "onRegisterStatus getPushId= " + registerStatus.getPushId());
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        XcLogger.w(this.MZ_TAG, "onUnRegisterStatus getMessage= " + unRegisterStatus.getMessage());
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
        XcLogger.w(this.MZ_TAG, "onSubTagsStatus getPushId= " + subTagsStatus.getPushId());
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        XcLogger.w(this.MZ_TAG, "onSubAliasStatus getAlias= " + subAliasStatus.getAlias());
    }
}
