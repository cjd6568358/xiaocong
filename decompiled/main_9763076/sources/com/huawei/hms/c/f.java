package com.huawei.hms.c;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: compiled from: SHA256.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class f {
    public static byte[] a(byte[] bArr) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            com.huawei.hms.support.log.a.d("SHA256", "NoSuchAlgorithmException" + e.getMessage());
            return new byte[0];
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static byte[] a(File file) throws Throwable {
        BufferedInputStream bufferedInputStream;
        Throwable th;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                try {
                    try {
                        try {
                            try {
                                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                                try {
                                    try {
                                        try {
                                            byte[] bArr = new byte[4096];
                                            int i = 0;
                                            while (true) {
                                                try {
                                                    int i2 = bufferedInputStream.read(bArr);
                                                    if (i2 == -1) {
                                                        break;
                                                    }
                                                    i += i2;
                                                    try {
                                                        messageDigest.update(bArr, 0, i2);
                                                    } catch (IOException e) {
                                                        bufferedInputStream2 = bufferedInputStream;
                                                    }
                                                } catch (IOException e2) {
                                                    bufferedInputStream2 = bufferedInputStream;
                                                }
                                                bufferedInputStream = bufferedInputStream2;
                                                com.huawei.hms.support.log.a.d("SHA256", "An exception occurred while computing file 'SHA-256'.");
                                                c.a((InputStream) bufferedInputStream);
                                                return new byte[0];
                                            }
                                            if (i > 0) {
                                                try {
                                                    byte[] bArrDigest = messageDigest.digest();
                                                    c.a((InputStream) bufferedInputStream);
                                                    return bArrDigest;
                                                } catch (IOException e3) {
                                                    bufferedInputStream2 = bufferedInputStream;
                                                }
                                            } else {
                                                c.a((InputStream) bufferedInputStream);
                                                return new byte[0];
                                            }
                                        } catch (IOException e4) {
                                            bufferedInputStream2 = bufferedInputStream;
                                        }
                                    } catch (NoSuchAlgorithmException e5) {
                                        bufferedInputStream2 = bufferedInputStream;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    c.a((InputStream) bufferedInputStream);
                                    throw th;
                                }
                            } catch (IOException e6) {
                            }
                        } catch (IOException e7) {
                        }
                    } catch (IOException e8) {
                    }
                } catch (IOException e9) {
                }
            } catch (IOException e10) {
            }
        } catch (NoSuchAlgorithmException e11) {
        } catch (Throwable th3) {
            bufferedInputStream = null;
            th = th3;
            c.a((InputStream) bufferedInputStream);
            throw th;
        }
        bufferedInputStream = bufferedInputStream2;
        com.huawei.hms.support.log.a.d("SHA256", "An exception occurred while computing file 'SHA-256'.");
        c.a((InputStream) bufferedInputStream);
        return new byte[0];
    }
}
