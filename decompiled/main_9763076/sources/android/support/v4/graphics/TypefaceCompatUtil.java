package android.support.v4.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.util.Log;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TypefaceCompatUtil {
    public static File getTempFile(Context context) {
        String prefix = ".font" + Process.myPid() + "-" + Process.myTid() + "-";
        for (int i = 0; i < 100; i++) {
            File file = new File(context.getCacheDir(), prefix + i);
            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0033 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:26:0x003e A[Catch: IOException -> 0x0024, TRY_LEAVE, TryCatch #3 {IOException -> 0x0024, blocks: (B:3:0x0001, B:8:0x001b, B:14:0x0027, B:11:0x0020, B:22:0x0035, B:26:0x003e, B:25:0x003a, B:23:0x0038), top: B:32:0x0001, inners: #2, #5 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0035 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:? A[Catch: IOException -> 0x0024, SYNTHETIC, TRY_ENTER, TryCatch #3 {IOException -> 0x0024, blocks: (B:3:0x0001, B:8:0x001b, B:14:0x0027, B:11:0x0020, B:22:0x0035, B:26:0x003e, B:25:0x003a, B:23:0x0038), top: B:32:0x0001, inners: #2, #5 }] */
    private static ByteBuffer mmap(File file) throws Throwable {
        Throwable th;
        try {
            FileInputStream fis = new FileInputStream(file);
            Throwable th2 = null;
            try {
                FileChannel channel = fis.getChannel();
                long size = channel.size();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
                if (fis == null) {
                    return map;
                }
                if (0 == 0) {
                    fis.close();
                    return map;
                }
                try {
                    fis.close();
                    return map;
                } catch (Throwable th3) {
                    th2.addSuppressed(th3);
                    return map;
                }
            } catch (Throwable th4) {
                th = th4;
                th = null;
                if (fis != null) {
                    throw th;
                }
                if (th == null) {
                    fis.close();
                    throw th;
                }
                fis.close();
                throw th;
            }
        } catch (IOException e) {
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0041 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x0062 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:46:0x006d A[Catch: Throwable -> 0x0039, all -> 0x004e, TRY_LEAVE, TryCatch #8 {all -> 0x004e, blocks: (B:5:0x000b, B:10:0x0029, B:28:0x004a, B:16:0x0035, B:42:0x0064, B:46:0x006d, B:45:0x0069, B:43:0x0067), top: B:65:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:50:0x0076 A[Catch: IOException -> 0x0047, TRY_LEAVE, TryCatch #1 {IOException -> 0x0047, blocks: (B:3:0x0004, B:13:0x0030, B:34:0x0056, B:33:0x0052, B:24:0x0043, B:50:0x0076, B:49:0x0072, B:25:0x0046), top: B:54:0x0004, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:55:0x0043 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:66:0x0064 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:? A[Catch: IOException -> 0x0047, SYNTHETIC, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x0047, blocks: (B:3:0x0004, B:13:0x0030, B:34:0x0056, B:33:0x0052, B:24:0x0043, B:50:0x0076, B:49:0x0072, B:25:0x0046), top: B:54:0x0004, inners: #2, #3 }] */
    /* JADX WARN: Code duplicated, block: B:77:? A[Catch: Throwable -> 0x0039, all -> 0x004e, SYNTHETIC, TRY_ENTER, TryCatch #8 {all -> 0x004e, blocks: (B:5:0x000b, B:10:0x0029, B:28:0x004a, B:16:0x0035, B:42:0x0064, B:46:0x006d, B:45:0x0069, B:43:0x0067), top: B:65:0x000b }] */
    public static ByteBuffer mmap(Context context, CancellationSignal cancellationSignal, Uri uri) throws Throwable {
        Throwable th;
        Throwable th2;
        Throwable th3;
        ContentResolver resolver = context.getContentResolver();
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r", cancellationSignal);
            Throwable th4 = null;
            try {
                try {
                    FileInputStream fis = new FileInputStream(pfd.getFileDescriptor());
                    Throwable th5 = null;
                    try {
                        FileChannel channel = fis.getChannel();
                        long size = channel.size();
                        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
                        if (fis != null) {
                            if (0 != 0) {
                                try {
                                    fis.close();
                                } catch (Throwable th6) {
                                    th5.addSuppressed(th6);
                                }
                            } else {
                                fis.close();
                            }
                        }
                        if (pfd == null) {
                            return map;
                        }
                        if (0 == 0) {
                            pfd.close();
                            return map;
                        }
                        try {
                            pfd.close();
                            return map;
                        } catch (Throwable th7) {
                            th4.addSuppressed(th7);
                            return map;
                        }
                    } catch (Throwable th8) {
                        th = th8;
                        th3 = null;
                        if (fis != null) {
                            throw th;
                        }
                        if (th3 == null) {
                            fis.close();
                            throw th;
                        }
                        fis.close();
                        throw th;
                    }
                } catch (Throwable th9) {
                    th2 = th9;
                    th = null;
                    if (pfd != null) {
                        throw th2;
                    }
                    if (th == null) {
                        pfd.close();
                        throw th2;
                    }
                    try {
                        pfd.close();
                        throw th2;
                    } catch (Throwable th10) {
                        th.addSuppressed(th10);
                        throw th2;
                    }
                }
            } catch (Throwable th11) {
                try {
                    throw th11;
                } catch (Throwable th12) {
                    th = th11;
                    th2 = th12;
                    if (pfd != null) {
                        throw th2;
                    }
                    if (th == null) {
                        pfd.close();
                        throw th2;
                    }
                    pfd.close();
                    throw th2;
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        ByteBuffer byteBufferMmap = null;
        File tmpFile = getTempFile(context);
        if (tmpFile != null) {
            try {
                if (copyToFile(tmpFile, res, id)) {
                    byteBufferMmap = mmap(tmpFile);
                }
            } finally {
                tmpFile.delete();
            }
        }
        return byteBufferMmap;
    }

    public static boolean copyToFile(File file, InputStream is) throws Throwable {
        boolean z = false;
        FileOutputStream os = null;
        try {
            try {
                FileOutputStream os2 = new FileOutputStream(file, false);
                try {
                    byte[] buffer = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                    while (true) {
                        int readLen = is.read(buffer);
                        if (readLen == -1) {
                            break;
                        }
                        os2.write(buffer, 0, readLen);
                    }
                    z = true;
                    closeQuietly(os2);
                    os = os2;
                } catch (IOException e) {
                    e = e;
                    os = os2;
                    Log.e("TypefaceCompatUtil", "Error copying resource contents to temp file: " + e.getMessage());
                    closeQuietly(os);
                } catch (Throwable th) {
                    th = th;
                    os = os2;
                    closeQuietly(os);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
        return z;
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
