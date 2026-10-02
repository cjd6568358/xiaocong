package com.meizu.cloud.pushsdk.handler.a.b;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends com.meizu.cloud.pushsdk.handler.a.a<UnRegisterStatus> {
    public f(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public UnRegisterStatus c(Intent intent) {
        UnRegisterStatus unRegisterStatus = (UnRegisterStatus) intent.getSerializableExtra(PushConstants.EXTRA_APP_PUSH_UNREGISTER_STATUS);
        if (unRegisterStatus.isUnRegisterSuccess()) {
            com.meizu.cloud.pushsdk.util.c.a(c(), Constants.MAIN_VERSION_TAG);
        }
        return unRegisterStatus;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(UnRegisterStatus unRegisterStatus, com.meizu.cloud.pushsdk.notification.e eVar) {
        if (b() != null && unRegisterStatus != null) {
            b().a(c(), unRegisterStatus);
        }
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start UnRegisterStatusHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_UNREGISTER_STATUS.equals(i(intent));
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
    }
}
