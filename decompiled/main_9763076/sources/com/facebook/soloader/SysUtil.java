package com.facebook.soloader;

import android.os.Build;
import android.os.Parcel;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class SysUtil {
    public static int findAbiScore(String[] supportedAbis, String abi) {
        for (int i = 0; i < supportedAbis.length; i++) {
            if (supportedAbis[i] != null && abi.equals(supportedAbis[i])) {
                return i;
            }
        }
        return -1;
    }

    public static String[] getSupportedAbis() {
        return Build.VERSION.SDK_INT < 21 ? new String[]{Build.CPU_ABI, Build.CPU_ABI2} : LollipopSysdeps.getSupportedAbis();
    }

    public static void fallocateIfSupported(FileDescriptor fd, long length) throws IOException {
        if (Build.VERSION.SDK_INT >= 21) {
            LollipopSysdeps.fallocateIfSupported(fd, length);
        }
    }

    public static void dumbDeleteRecursive(File file) throws IOException {
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            if (fileList == null) {
                return;
            }
            for (File entry : fileList) {
                dumbDeleteRecursive(entry);
            }
        }
        if (!file.delete() && file.exists()) {
            throw new IOException("could not delete: " + file);
        }
    }

    private static final class LollipopSysdeps {
        public static String[] getSupportedAbis() {
            return Build.SUPPORTED_32_BIT_ABIS;
        }

        public static void fallocateIfSupported(FileDescriptor fd, long length) throws IOException {
            try {
                Os.posix_fallocate(fd, 0L, length);
            } catch (ErrnoException ex) {
                if (ex.errno != OsConstants.EOPNOTSUPP && ex.errno != OsConstants.ENOSYS && ex.errno != OsConstants.EINVAL) {
                    throw new IOException(ex.toString(), ex);
                }
            }
        }
    }

    static void mkdirOrThrow(File dir) throws IOException {
        if (!dir.mkdirs() && !dir.isDirectory()) {
            throw new IOException("cannot mkdir: " + dir);
        }
    }

    static int copyBytes(RandomAccessFile os, InputStream is, int byteLimit, byte[] buffer) throws IOException {
        int bytesCopied = 0;
        while (bytesCopied < byteLimit) {
            int nrRead = is.read(buffer, 0, Math.min(buffer.length, byteLimit - bytesCopied));
            if (nrRead == -1) {
                break;
            }
            os.write(buffer, 0, nrRead);
            bytesCopied += nrRead;
        }
        return bytesCopied;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0066 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:34:0x0071  */
    /* JADX WARN: Code duplicated, block: B:39:0x0068 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    static void fsyncRecursive(File fileName) throws Throwable {
        Throwable th;
        if (fileName.isDirectory()) {
            File[] files = fileName.listFiles();
            if (files == null) {
                throw new IOException("cannot list directory " + fileName);
            }
            for (File file : files) {
                fsyncRecursive(file);
            }
            return;
        }
        if (!fileName.getPath().endsWith("_lock")) {
            RandomAccessFile file2 = new RandomAccessFile(fileName, "r");
            Throwable th2 = null;
            try {
                file2.getFD().sync();
                if (file2 != null) {
                    if (0 != 0) {
                        try {
                            file2.close();
                            return;
                        } catch (Throwable x2) {
                            th2.addSuppressed(x2);
                            return;
                        }
                    }
                    file2.close();
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    th2 = th3;
                    th = th4;
                    if (file2 != null) {
                        if (th2 != null) {
                            file2.close();
                        } else {
                            file2.close();
                        }
                    }
                    throw th;
                }
            }
        }
    }

    public static byte[] makeApkDepBlock(File apkFile) {
        Parcel parcel = Parcel.obtain();
        parcel.writeByte((byte) 1);
        parcel.writeString(apkFile.getPath());
        parcel.writeLong(apkFile.lastModified());
        byte[] depsBlock = parcel.marshall();
        parcel.recycle();
        return depsBlock;
    }
}
