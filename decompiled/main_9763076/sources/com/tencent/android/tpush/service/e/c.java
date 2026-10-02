package com.tencent.android.tpush.service.e;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    public static String a() {
        String businessDeviceId = null;
        if (com.tencent.android.tpush.service.n.f() != null) {
            try {
                businessDeviceId = TpnsSecurity.getBusinessDeviceId(com.tencent.android.tpush.service.n.f());
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c("ServiceLogTag", ">>get deviceid err", e);
            }
            if (businessDeviceId == null || businessDeviceId.trim().length() == 0) {
                return Constants.MAIN_VERSION_TAG;
            }
            return businessDeviceId;
        }
        com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">>> getDeviceId() > context == null");
        return null;
    }
}
