package com.tencent.android.tpush;

import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.encrypt.Rijndael;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class ah implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ XGIOperateCallback b;
    final /* synthetic */ long c;
    final /* synthetic */ String d;

    ah(Context context, XGIOperateCallback xGIOperateCallback, long j, String str) {
        this.a = context;
        this.b = xGIOperateCallback;
        this.c = j;
        this.d = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            int iA = com.tencent.android.tpush.common.t.a(this.a);
            if (iA != 0) {
                if (this.b != null) {
                    this.b.onFail(null, iA, "XINGE SDK config error");
                    return;
                }
                return;
            }
            long accessId = this.c <= 0 ? XGPushConfig.getAccessId(this.a) : this.c;
            String accessKey = com.tencent.android.tpush.common.t.c(this.d) ? XGPushConfig.getAccessKey(this.a) : this.d;
            String token = XGPushConfig.getToken(this.a);
            if ((accessId <= 0 || com.tencent.android.tpush.common.t.c(accessKey) || com.tencent.android.tpush.common.t.c(token)) && this.b != null) {
                this.b.onFail(null, 10001, "The accessId, accessKey or token is invalid! accessId=" + accessId + ",accessKey=" + accessKey + ",token=" + token);
                throw new IllegalArgumentException("accessId, accessKey or token is invalid.");
            }
            Intent intent = new Intent("com.tencent.android.tpush.action.UNREGISTER.V3");
            intent.putExtra("accId", Rijndael.encrypt(Constants.MAIN_VERSION_TAG + accessId));
            intent.putExtra("accKey", Rijndael.encrypt(accessKey));
            intent.putExtra(Constants.FLAG_TOKEN, Rijndael.encrypt(token));
            intent.putExtra(Constants.FLAG_PACK_NAME, Rijndael.encrypt(this.a.getPackageName()));
            intent.putExtra("operation", 101);
            intent.putExtra("opType", 1);
            boolean zB = com.tencent.android.tpush.common.s.a(this.a).b();
            if (com.tencent.android.tpush.common.t.c(this.a) == 1 && !zB) {
                XGPushManager.d(this.a, intent, this.b);
            } else {
                XGPushManager.a(this.a, intent, this.b, zB);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.d(Constants.LogTag, "unregisterPush", th);
        }
    }
}
