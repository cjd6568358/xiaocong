package com.ixiaocong.smarthome.phone.android.event.receiver;

import android.content.Context;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;
import com.xiaocong.smarthome.httplib.helper.XcLogger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XcXgPushReceiver extends XGPushBaseReceiver {
    @Override // com.tencent.android.tpush.XGPushBaseReceiver
    public void onRegisterResult(Context context, int i, XGPushRegisterResult message) {
        XcLogger.i("TPush---XcXgPushReceiver------", "onRegisterResult---" + i + "----" + message.getDeviceId() + "---" + message.getTicket());
    }

    @Override // com.tencent.android.tpush.XGPushBaseReceiver
    public void onUnregisterResult(Context context, int i) {
        XcLogger.i("TPush---XcXgPushReceiver------", "onUnregisterResult---" + i);
    }

    @Override // com.tencent.android.tpush.XGPushBaseReceiver
    public void onSetTagResult(Context context, int i, String s) {
        XcLogger.i("TPush---XcXgPushReceiver------", "onSetTagResult---" + i + "----" + s);
    }

    @Override // com.tencent.android.tpush.XGPushBaseReceiver
    public void onDeleteTagResult(Context context, int i, String s) {
        XcLogger.i("TPush---XcXgPushReceiver------", "onDeleteTagResult---" + i + "----" + s);
    }

    @Override // com.tencent.android.tpush.XGPushBaseReceiver
    public void onTextMessage(Context context, XGPushTextMessage message) {
        XcLogger.i("TPush---XcXgPushReceiver------", "onTextMessage---" + message.getCustomContent() + "----" + message.getContent() + "----" + message.getTitle());
    }

    @Override // com.tencent.android.tpush.XGPushBaseReceiver
    public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
        if (context != null && message != null) {
            XcLogger.i("TPush---XcXgPushReceiver------", "onNotifactionClickedResult---" + message.getActivityName() + "----" + message.getNotificationActionType() + "---" + message.getActionType());
            if (message.getActionType() == 0) {
                String str = "通知被打开 :" + message;
            } else if (message.getActionType() == 2) {
                String str2 = "通知被清除 :" + message;
            }
        }
    }

    @Override // com.tencent.android.tpush.XGPushBaseReceiver
    public void onNotifactionShowedResult(Context context, XGPushShowedResult message) {
        XcLogger.i("TPush---XcXgPushReceiver------", "onNotifactionShowedResult----" + message.getNotificationActionType());
    }
}
