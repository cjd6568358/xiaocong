package com.meizu.cloud.pushsdk.notification.b;

import android.os.SystemClock;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private final File a;
    private final File b;
    private String c;

    public d(String str, String str2) {
        this.a = new File(str);
        this.b = new File(str2);
        this.c = this.b.getAbsolutePath();
        DebugLogger.i("ZipExtractTask", "Extract mInput file = " + this.a.toString());
        DebugLogger.i("ZipExtractTask", "Extract mOutput file = " + this.b.toString());
    }

    private void b() {
        if (this.a != null && this.a.exists()) {
            if (this.a.delete()) {
                DebugLogger.i("ZipExtractTask", "Delete file:" + this.a.toString() + " after extracted.");
            } else {
                DebugLogger.i("ZipExtractTask", "Can't delete file:" + this.a.toString() + " after extracted.");
            }
        }
    }

    public boolean a() {
        return c() > 0;
    }

    /* JADX WARN: Code duplicated, block: B:70:0x0205 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    private long c() throws Throwable {
        ZipFile zipFile;
        IOException iOException;
        String str;
        ZipException zipException;
        boolean z;
        long j;
        boolean z2;
        String str2 = null;
        Object[] objArr = 0;
        long jCurrentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
        long jA = 0;
        try {
            try {
                zipFile = new ZipFile(this.a);
                try {
                    Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
                    while (enumerationEntries.hasMoreElements()) {
                        try {
                            ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                            if (!zipEntryNextElement.isDirectory()) {
                                String name = zipEntryNextElement.getName();
                                if (str2 == null && name != null) {
                                    str2 = name.split("/")[0];
                                    DebugLogger.i("ZipExtractTask", "Extract temp directory=" + this.b + "/" + str2);
                                }
                                File file = new File(this.b, name);
                                if (!file.getParentFile().exists()) {
                                    if (file.getParentFile().mkdirs()) {
                                        DebugLogger.i("ZipExtractTask", "Make Destination directory=" + file.getParentFile().getAbsolutePath());
                                    } else {
                                        DebugLogger.i("ZipExtractTask", "Can't make destination directory=" + file.getParentFile().getAbsolutePath());
                                    }
                                }
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                jA += (long) a(zipFile.getInputStream(zipEntryNextElement), fileOutputStream);
                                fileOutputStream.close();
                            }
                        } catch (ZipException e) {
                            str = str2;
                            zipException = e;
                            DebugLogger.e("ZipExtractTask", "ZipException :" + zipException.toString());
                            if (zipFile != null) {
                                try {
                                    zipFile.close();
                                } catch (IOException e2) {
                                    DebugLogger.e("ZipExtractTask", "Extracted IOException:" + e2.toString());
                                    z = false;
                                    j = jA;
                                }
                            }
                            z = false;
                            j = jA;
                        } catch (IOException e3) {
                            str = str2;
                            iOException = e3;
                            DebugLogger.e("ZipExtractTask", "Extracted IOException:" + iOException.toString());
                            if (zipFile != null) {
                                try {
                                    zipFile.close();
                                } catch (IOException e4) {
                                    DebugLogger.e("ZipExtractTask", "Extracted IOException:" + e4.toString());
                                    z = false;
                                    j = jA;
                                }
                            }
                            z = false;
                            j = jA;
                        }
                    }
                    String str3 = this.b + "/" + str2;
                    if (this.c.equals(str3)) {
                        z2 = false;
                    } else {
                        a.a(str3, this.c);
                        z2 = true;
                    }
                    if (zipFile != null) {
                        try {
                            zipFile.close();
                        } catch (IOException e5) {
                            DebugLogger.e("ZipExtractTask", "Extracted IOException:" + e5.toString());
                            j = jA;
                            boolean z3 = z2;
                            str = str2;
                            z = z3;
                        }
                    }
                    j = jA;
                    boolean z4 = z2;
                    str = str2;
                    z = z4;
                } catch (ZipException e6) {
                    str = null;
                    zipException = e6;
                } catch (IOException e7) {
                    str = null;
                    iOException = e7;
                }
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    try {
                        (objArr == true ? 1 : 0).close();
                    } catch (IOException e8) {
                        DebugLogger.e("ZipExtractTask", "Extracted IOException:" + e8.toString());
                    }
                }
                throw th;
            }
        } catch (ZipException e9) {
            zipFile = null;
            zipException = e9;
            str = null;
        } catch (IOException e10) {
            zipFile = null;
            iOException = e10;
            str = null;
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
                (objArr == true ? 1 : 0).close();
            }
            throw th;
        }
        DebugLogger.i("ZipExtractTask", "Extract file " + this.a + ", UseTime =" + String.valueOf(SystemClock.currentThreadTimeMillis() - jCurrentThreadTimeMillis));
        if (z) {
            a.b(this.b + "/" + str);
        }
        b();
        return j;
    }

    private int a(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[8192];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 8192);
        int i = 0;
        while (true) {
            try {
                try {
                    int i2 = bufferedInputStream.read(bArr, 0, 8192);
                    if (i2 == -1) {
                        break;
                    }
                    bufferedOutputStream.write(bArr, 0, i2);
                    i = i2 + i;
                } catch (IOException e) {
                    DebugLogger.e("ZipExtractTask", "Extracted IOException:" + e.toString());
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException e2) {
                        DebugLogger.e("ZipExtractTask", "out.close() IOException e=" + e2.toString());
                    }
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e3) {
                        DebugLogger.e("ZipExtractTask", "in.close() IOException e=" + e3.toString());
                    }
                }
            } catch (Throwable th) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e4) {
                    DebugLogger.e("ZipExtractTask", "out.close() IOException e=" + e4.toString());
                }
                try {
                    bufferedInputStream.close();
                    throw th;
                } catch (IOException e5) {
                    DebugLogger.e("ZipExtractTask", "in.close() IOException e=" + e5.toString());
                    throw th;
                }
            }
        }
        bufferedOutputStream.flush();
        try {
            bufferedOutputStream.close();
        } catch (IOException e6) {
            DebugLogger.e("ZipExtractTask", "out.close() IOException e=" + e6.toString());
        }
        try {
            bufferedInputStream.close();
        } catch (IOException e7) {
            DebugLogger.e("ZipExtractTask", "in.close() IOException e=" + e7.toString());
        }
        return i;
    }
}
