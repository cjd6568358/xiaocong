package com.alibaba.mtl.log.e;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/* JADX INFO: compiled from: MutiProcessLock.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class k {
    static File a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    static FileChannel f35a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    static FileLock f36a;

    /* JADX WARN: Code duplicated, block: B:18:0x005a A[Catch: Throwable -> 0x005d, all -> 0x007e, TRY_LEAVE, TryCatch #0 {Throwable -> 0x005d, blocks: (B:16:0x0052, B:18:0x005a), top: B:32:0x0052 }] */
    /* JADX WARN: Code duplicated, block: B:26:0x007c  */
    public static synchronized boolean c(Context context) {
        FileLock fileLock;
        FileLock fileLockTryLock;
        boolean z = true;
        synchronized (k.class) {
            if (a == null) {
                a = new File(context.getFilesDir() + File.separator + "ap.Lock");
            }
            boolean zExists = a.exists();
            if (!zExists) {
                try {
                    zExists = a.createNewFile();
                } catch (IOException e) {
                }
            }
            if (zExists) {
                if (f35a == null) {
                    try {
                        f35a = new RandomAccessFile(a, "rw").getChannel();
                        try {
                            fileLockTryLock = f35a.tryLock();
                            if (fileLockTryLock != null) {
                                f36a = fileLockTryLock;
                            } else {
                                fileLock = fileLockTryLock;
                                Log.d("TAG", "mLock:" + fileLock);
                                z = false;
                            }
                        } catch (Throwable th) {
                            fileLock = null;
                        }
                    } catch (Exception e2) {
                        z = false;
                    }
                } else {
                    fileLockTryLock = f35a.tryLock();
                    if (fileLockTryLock != null) {
                        f36a = fileLockTryLock;
                    } else {
                        fileLock = fileLockTryLock;
                        Log.d("TAG", "mLock:" + fileLock);
                        z = false;
                    }
                }
            }
        }
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0013 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static synchronized void release() {
        if (f36a != null) {
            try {
                try {
                    f36a.release();
                    f36a = null;
                } catch (Throwable th) {
                    f36a = null;
                    throw th;
                }
            } catch (IOException e) {
                f36a = null;
            }
            if (f35a != null) {
                try {
                    try {
                        f35a.close();
                        f35a = null;
                    } catch (Exception e2) {
                        f35a = null;
                    }
                } catch (Throwable th2) {
                    f35a = null;
                    throw th2;
                }
            }
        } else if (f35a != null) {
            f35a.close();
            f35a = null;
        }
        throw th;
    }
}
