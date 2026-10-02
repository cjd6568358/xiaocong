package com.huawei.hms.support.api.push.a.a.b;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: PushEncrypter.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class d {
    public static String a(Context context, String str) {
        return TextUtils.isEmpty(str) ? Constants.MAIN_VERSION_TAG : a.a(str);
    }

    public static String b(Context context, String str) {
        return TextUtils.isEmpty(str) ? Constants.MAIN_VERSION_TAG : a.b(str);
    }
}
