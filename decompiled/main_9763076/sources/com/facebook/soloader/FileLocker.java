package com.facebook.soloader;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class FileLocker implements Closeable {
    private final FileLock mLock;
    private final FileOutputStream mLockFileOutputStream;

    public static FileLocker lock(File lockFile) throws IOException {
        return new FileLocker(lockFile);
    }

    private FileLocker(File lockFile) throws IOException {
        this.mLockFileOutputStream = new FileOutputStream(lockFile);
        try {
            FileLock lock = this.mLockFileOutputStream.getChannel().lock();
            if (lock == null) {
                this.mLockFileOutputStream.close();
            }
            this.mLock = lock;
        } catch (Throwable th) {
            if (0 == 0) {
                this.mLockFileOutputStream.close();
            }
            throw th;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            this.mLock.release();
        } finally {
            this.mLockFileOutputStream.close();
        }
    }
}
