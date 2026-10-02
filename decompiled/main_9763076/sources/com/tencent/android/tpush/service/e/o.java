package com.tencent.android.tpush.service.e;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.tencent.android.tpush.XGPushProvider;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.t;
import java.util.List;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class o implements Runnable {
    final /* synthetic */ Context a;

    o(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (m.h(this.a) < 2) {
            List<ResolveInfo> listD = m.d(this.a);
            if (listD != null) {
                int i = 0;
                for (ResolveInfo resolveInfo : listD) {
                    i++;
                    if ("oppo".equals(t.b())) {
                        if (i > 2) {
                            return;
                        }
                    } else if (i > 4) {
                        return;
                    }
                    String str = resolveInfo.activityInfo.applicationInfo.packageName;
                    if (!m.b(str) && !this.a.getPackageName().equals(str) && !m.b(this.a, str)) {
                        try {
                            if (m.h(this.a) < 2) {
                                this.a.getContentResolver().getType(Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/" + XGPushProvider.STR_GET_PULLUP));
                                Thread.sleep(200L);
                            } else {
                                return;
                            }
                        } catch (Throwable th) {
                            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "pull up by provider error" + th);
                        }
                    }
                }
                return;
            }
            com.tencent.android.tpush.a.a.f(Constants.ServiceLogTag, "pullupXGServices  with null content");
        }
    }
}
