package com.meizu.cloud.pushsdk.handler.a.b;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends com.meizu.cloud.pushsdk.handler.a.a<PushSwitchStatus> {
    public a(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public PushSwitchStatus c(Intent intent) {
        return (PushSwitchStatus) intent.getSerializableExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_STATUS);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(PushSwitchStatus pushSwitchStatus, com.meizu.cloud.pushsdk.notification.e eVar) {
        if (b() != null && pushSwitchStatus != null) {
            b().a(c(), pushSwitchStatus);
        }
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start PushSwitchStatusHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PUSH_STATUS.equals(i(intent));
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 256;
    }
}
