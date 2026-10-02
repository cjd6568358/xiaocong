package com.ixiaocong.smarthome.phone.android.event.receiver;

import android.content.Context;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MiPushReceiver extends PushMessageReceiver {
    private String MI_TAG = "MiPushReceiver";

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceivePassThroughMessage(context, miPushMessage);
        XcLogger.w(this.MI_TAG, "onReceivePassThroughMessage--getContent = " + miPushMessage.getContent() + ", getDescription = " + miPushMessage.getDescription());
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onReceiveRegisterResult(context, miPushCommandMessage);
        XcLogger.w(this.MI_TAG, "onReceiveRegisterResult--getReason = " + miPushCommandMessage.getReason() + ", getCategory = " + miPushCommandMessage.getCategory());
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onCommandResult(context, miPushCommandMessage);
        XcLogger.w(this.MI_TAG, "onCommandResult--getReason = " + miPushCommandMessage.getReason() + ", getCategory = " + miPushCommandMessage.getCategory());
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageArrived(context, miPushMessage);
        XcLogger.w(this.MI_TAG, "onNotificationMessageArrived--getContent = " + miPushMessage.getContent() + ", getDescription = " + miPushMessage.getDescription());
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageClicked(context, miPushMessage);
        XcLogger.w(this.MI_TAG, "onNotificationMessageClicked--getContent = " + miPushMessage.getContent() + ", getDescription = " + miPushMessage.getDescription());
    }
}
