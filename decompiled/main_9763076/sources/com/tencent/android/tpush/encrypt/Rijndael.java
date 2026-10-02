package com.tencent.android.tpush.encrypt;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;
import com.tencent.android.tpush.service.e.m;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Rijndael {
    public static String encrypt(String str) {
        if (!m.b(str)) {
            String strOiSymmetryEncrypt2 = TpnsSecurity.oiSymmetryEncrypt2(str);
            int i = 0;
            while (i < 3) {
                if (!"failed".equals(strOiSymmetryEncrypt2)) {
                    return strOiSymmetryEncrypt2;
                }
                i++;
                strOiSymmetryEncrypt2 = TpnsSecurity.oiSymmetryEncrypt2(str);
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static String decrypt(String str) {
        if (!m.b(str)) {
            String strOiSymmetryDecrypt2 = TpnsSecurity.oiSymmetryDecrypt2(str);
            int i = 0;
            while (i < 3) {
                if (!"failed".equals(strOiSymmetryDecrypt2)) {
                    return strOiSymmetryDecrypt2;
                }
                i++;
                strOiSymmetryDecrypt2 = TpnsSecurity.oiSymmetryDecrypt2(str);
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }
}
