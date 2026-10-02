package com.tencent.android.tpush;

import android.content.Intent;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.encrypt.Rijndael;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPushShowedResult implements XGIResult {
    public static final int HW_PUSH_CHANNEL = 2;
    public static final int MZ_PUSH_CHANNEL = 3;
    public static final int NOTIFICATION_ACTION_ACTIVITY = 1;
    public static final int NOTIFICATION_ACTION_INTENT = 3;
    public static final int NOTIFICATION_ACTION_PACKAGE = 4;
    public static final int NOTIFICATION_ACTION_URL = 2;
    public static final int XG_PUSH_CHANNEL = 0;
    public static final int XM_PUSH_CHANNEL = 1;
    long a = 0;
    String b = Constants.MAIN_VERSION_TAG;
    String c = Constants.MAIN_VERSION_TAG;
    String d = Constants.MAIN_VERSION_TAG;
    String e = Constants.MAIN_VERSION_TAG;
    int f = 0;
    int g = 1;
    int h = 0;

    public int getPushChannel() {
        return this.h;
    }

    public int getNotifactionId() {
        return this.f;
    }

    public long getMsgId() {
        return this.a;
    }

    public String getTitle() {
        return this.b;
    }

    public String getContent() {
        return this.c;
    }

    public String getCustomContent() {
        return this.d;
    }

    public String getActivity() {
        return this.e;
    }

    public int getNotificationActionType() {
        return this.g;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("XGPushShowedResult [msgId=").append(this.a).append(", title=").append(this.b).append(", content=").append(this.c).append(", customContent=").append(this.d).append(", activity=").append(this.e).append(", notificationActionType").append(this.g).append("]");
        return sb.toString();
    }

    @Override // com.tencent.android.tpush.XGIResult
    public void parseIntent(Intent intent) {
        this.a = intent.getLongExtra(MessageKey.MSG_ID, -1L);
        this.e = intent.getStringExtra("activity");
        this.b = Rijndael.decrypt(intent.getStringExtra("title"));
        this.c = Rijndael.decrypt(intent.getStringExtra("content"));
        this.g = intent.getIntExtra(Constants.FLAG_NOTIFICATION_ACTION_TYPE, 1);
        this.d = Rijndael.decrypt(intent.getStringExtra("custom_content"));
        this.f = intent.getIntExtra(MessageKey.NOTIFACTION_ID, 0);
    }
}
