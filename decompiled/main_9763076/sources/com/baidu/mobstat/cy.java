package com.baidu.mobstat;

import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class cy {
    /* JADX INFO: Access modifiers changed from: private */
    public static String b(MessageDigest messageDigest, byte[] bArr) {
        messageDigest.update(bArr);
        return a(messageDigest.digest());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:41:0x0042 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static String b(MessageDigest messageDigest, File file) throws Throwable {
        FileInputStream fileInputStream;
        if (!file.isFile()) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                try {
                    byte[] bArr = new byte[4048];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        messageDigest.update(bArr, 0, i);
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            db.b(e);
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    db.b(e);
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            db.b(e3);
                        }
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e4) {
                        db.b(e4);
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
        return a(messageDigest.digest());
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bArr.length; i++) {
            int i2 = (bArr[i] >> 4) & 15;
            int i3 = bArr[i] & 15;
            sb.append((char) (i2 >= 10 ? (i2 + 97) - 10 : i2 + 48));
            sb.append((char) (i3 >= 10 ? (i3 + 97) - 10 : i3 + 48));
        }
        return sb.toString();
    }
}
