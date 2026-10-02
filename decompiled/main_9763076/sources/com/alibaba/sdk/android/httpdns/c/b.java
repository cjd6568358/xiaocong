package com.alibaba.sdk.android.httpdns.c;

import android.text.TextUtils;
import com.alibaba.sdk.android.httpdns.g;
import com.tencent.android.tpush.common.Constants;
import java.net.SocketTimeoutException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    public static int a(Throwable th) {
        if (th instanceof g) {
            return ((g) th).getErrorCode();
        }
        if (th instanceof SocketTimeoutException) {
            return 10001;
        }
        return Constants.ERRORCODE_UNKNOWN;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static String m44a(Throwable th) {
        if (th == null || TextUtils.isEmpty(th.getMessage())) {
            return th instanceof SocketTimeoutException ? "time out exception" : "default error";
        }
        return th.getMessage();
    }

    public static int b() {
        return 0;
    }
}
