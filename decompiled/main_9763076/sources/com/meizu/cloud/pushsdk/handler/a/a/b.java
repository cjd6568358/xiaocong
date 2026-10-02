package com.meizu.cloud.pushsdk.handler.a.a;

import android.content.Context;
import android.content.Intent;
import bsh.ParserConstants;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.e;
import com.meizu.cloud.pushsdk.util.d;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends com.meizu.cloud.pushsdk.handler.a.a<MessageV3> {
    public b(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public MessageV3 c(Intent intent) {
        return (MessageV3) intent.getParcelableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(MessageV3 messageV3, e eVar) {
        if (b() != null && messageV3 != null) {
            b().c(c(), messageV3.getTitle(), messageV3.getContent(), a(messageV3.getWebUrl(), messageV3.getParamsMap()));
        }
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start NotificationDeleteMessageHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_DELETE.equals(i(intent));
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return ParserConstants.LSHIFTASSIGN;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(MessageV3 messageV3) {
        d.b(c(), messageV3.getUploadDataPackageName(), messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp());
    }
}
