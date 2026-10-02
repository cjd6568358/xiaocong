package com.huawei.hms.support.api.push.a.a.b;

import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: Proguard.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class c {
    private static String a(char c, int i) {
        StringBuffer stringBuffer = new StringBuffer(i);
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return Constants.MAIN_VERSION_TAG;
        }
        if (str.length() >= 2) {
            try {
                int iCeil = (int) Math.ceil(((double) (str.length() * 25)) / 100.0d);
                int iCeil2 = (int) Math.ceil(((double) (str.length() * 50)) / 100.0d);
                return str.substring(0, iCeil) + a('*', iCeil2) + str.substring(iCeil + iCeil2);
            } catch (IndexOutOfBoundsException e) {
                return Constants.MAIN_VERSION_TAG;
            }
        }
        return str;
    }
}
