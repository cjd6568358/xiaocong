package com.baidu.mobstat;

import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class cz {
    public static String a(byte[] bArr) {
        try {
            return cy.b(MessageDigest.getInstance("SHA-256"), bArr);
        } catch (Exception e) {
            db.b(e);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String a(File file) {
        try {
            return cy.b(MessageDigest.getInstance("SHA-256"), file);
        } catch (NoSuchAlgorithmException e) {
            db.b(e);
            return Constants.MAIN_VERSION_TAG;
        }
    }
}
