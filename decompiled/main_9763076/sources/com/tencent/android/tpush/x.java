package com.tencent.android.tpush;

import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.encrypt.Rijndael;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class x implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ XGIOperateCallback b;
    final /* synthetic */ long c;
    final /* synthetic */ String d;
    final /* synthetic */ String e;
    final /* synthetic */ int f;
    final /* synthetic */ String g;
    final /* synthetic */ String h;

    x(Context context, XGIOperateCallback xGIOperateCallback, long j, String str, String str2, int i, String str3, String str4) {
        this.a = context;
        this.b = xGIOperateCallback;
        this.c = j;
        this.d = str;
        this.e = str2;
        this.f = i;
        this.g = str3;
        this.h = str4;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            int iA = com.tencent.android.tpush.common.t.a(this.a);
            if (iA != 0) {
                if (this.b != null) {
                    this.b.onFail(null, iA, Constants.errCodeToMsg(iA));
                    return;
                }
                return;
            }
            long accessId = this.c > 0 ? this.c : XGPushConfig.getAccessId(this.a);
            String accessKey = com.tencent.android.tpush.common.t.c(this.d) ? XGPushConfig.getAccessKey(this.a) : this.d;
            if (accessId <= 0 || com.tencent.android.tpush.common.t.c(accessKey)) {
                this.b.onFail(null, 10001, "The accessId or accessKey is(are) invalid!@accessId:" + accessId + ", @accessKey:" + accessKey);
                return;
            }
            if ((XGPushConfig.isUsedOtherPush(this.a) && com.tencent.android.tpush.c.e.a(this.a).g()) || (XGPushConfig.isUsedFcmPush(this.a) && com.tencent.android.tpush.common.s.a(this.a).c())) {
                com.tencent.android.tpush.c.e.a(this.a).b();
                long jCurrentTimeMillis = System.currentTimeMillis();
                while (System.currentTimeMillis() - jCurrentTimeMillis < 30000) {
                    try {
                        Thread.sleep(200L);
                        String strD = com.tencent.android.tpush.c.e.a(this.a).d();
                        if (!com.tencent.android.tpush.common.t.c(strD)) {
                            com.tencent.android.tpush.a.a.e(Constants.OTHER_PUSH_TAG, "get otherToken is : " + strD);
                            break;
                        }
                        continue;
                    } catch (InterruptedException e) {
                    } catch (Exception e2) {
                        com.tencent.android.tpush.a.a.i(Constants.OTHER_PUSH_TAG, "OtherPush: call getToken Error!.");
                    }
                }
            }
            com.tencent.android.tpush.common.t.g(this.a);
            Intent intent = new Intent("com.tencent.android.tpush.action.REGISTER.V3");
            intent.putExtra("accId", Rijndael.encrypt(Constants.MAIN_VERSION_TAG + accessId));
            intent.putExtra("accKey", Rijndael.encrypt(accessKey));
            if (this.e != null) {
                intent.putExtra(Constants.FLAG_ACCOUNT, Rijndael.encrypt(this.e));
            }
            if ((this.f >> 4) != 1) {
                intent.putExtra("appVer", com.tencent.android.tpush.common.t.f(this.a));
                intent.putExtra(Constants.FLAG_PACK_NAME, Rijndael.encrypt(this.a.getPackageName()));
                if (com.tencent.android.tpush.common.o.a(this.a) != null) {
                    intent.putExtra("reserved", Rijndael.encrypt(com.tencent.android.tpush.common.o.a(this.a).a()));
                }
                if (this.g != null) {
                    intent.putExtra(Constants.FLAG_TICKET, Rijndael.encrypt(this.g));
                }
                if (this.h != null) {
                    intent.putExtra("qua", Rijndael.encrypt(this.h));
                }
                intent.putExtra("operation", 100);
                intent.putExtra("aidl", com.tencent.android.tpush.common.t.b(this.a));
            }
            intent.putExtra(Constants.FLAG_TICKET_TYPE, this.f);
            intent.putExtra("currentTimeMillis", System.currentTimeMillis());
            intent.putExtra("opType", 0);
            boolean zA = com.tencent.android.tpush.common.s.a(this.a).a();
            if (com.tencent.android.tpush.common.t.c(this.a) == 1 && !zA) {
                XGPushManager.c(this.a, intent, this.b);
                com.tencent.android.tpush.service.n.b(this.a);
            } else {
                XGPushManager.a(this.a, intent, this.b, zA);
            }
            if (XGPushConfig.isReportNotificationStatusEnable(this.a)) {
                com.tencent.android.tpush.service.e.m.c(this.a);
            }
            if (XGPushConfig.isReportApplistEnable(this.a)) {
                com.tencent.android.tpush.service.e.m.b(this.a);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(XGPushManager.a, "register", th);
        }
    }
}
