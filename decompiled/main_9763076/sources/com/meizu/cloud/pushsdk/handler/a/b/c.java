package com.meizu.cloud.pushsdk.handler.a.b;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends com.meizu.cloud.pushsdk.handler.a.a<RegisterStatus> {
    public c(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public RegisterStatus c(Intent intent) {
        RegisterStatus registerStatus = (RegisterStatus) intent.getSerializableExtra(PushConstants.EXTRA_APP_PUSH_REGISTER_STATUS);
        if (!TextUtils.isEmpty(registerStatus.getPushId())) {
            com.meizu.cloud.pushsdk.util.c.a(c(), registerStatus.getPushId());
            com.meizu.cloud.pushsdk.util.c.a(c(), (int) ((System.currentTimeMillis() / 1000) + ((long) registerStatus.getExpireTime())));
        }
        return registerStatus;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(RegisterStatus registerStatus, com.meizu.cloud.pushsdk.notification.e eVar) {
        if (b() != null && registerStatus != null) {
            b().a(c(), registerStatus);
        }
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start RegisterStatusHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_REGISTER_STATUS.equals(i(intent));
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return WXMediaMessage.TITLE_LENGTH_LIMIT;
    }
}
