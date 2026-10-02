package android.support.v4.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.v4.provider.FontsContractCompat;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    TypefaceCompatApi21Impl() {
    }

    private File getFile(ParcelFileDescriptor fd) {
        try {
            String path = Os.readlink("/proc/self/fd/" + fd.getFd());
            if (OsConstants.S_ISREG(Os.stat(path).st_mode)) {
                return new File(path);
            }
            return null;
        } catch (ErrnoException e) {
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0057 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:65:0x0099 A[Catch: IOException -> 0x0047, TRY_LEAVE, TryCatch #3 {IOException -> 0x0047, blocks: (B:7:0x000e, B:58:0x0084, B:62:0x008f, B:61:0x008a, B:21:0x003e, B:43:0x0064, B:24:0x0043, B:37:0x0059, B:65:0x0099, B:64:0x0095, B:38:0x005c), top: B:72:0x000e, inners: #6, #8, #9 }] */
    /* JADX WARN: Code duplicated, block: B:78:0x0059 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:92:? A[Catch: IOException -> 0x0047, SYNTHETIC, TRY_ENTER, TRY_LEAVE, TryCatch #3 {IOException -> 0x0047, blocks: (B:7:0x000e, B:58:0x0084, B:62:0x008f, B:61:0x008a, B:21:0x003e, B:43:0x0064, B:24:0x0043, B:37:0x0059, B:65:0x0099, B:64:0x0095, B:38:0x005c), top: B:72:0x000e, inners: #6, #8, #9 }] */
    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl, android.support.v4.graphics.TypefaceCompat.TypefaceCompatImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) throws Throwable {
        Throwable th;
        Throwable th2;
        if (fonts.length < 1) {
            return null;
        }
        FontsContractCompat.FontInfo bestFont = findBestInfo(fonts, style);
        ContentResolver resolver = context.getContentResolver();
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(bestFont.getUri(), "r", cancellationSignal);
            Throwable th3 = null;
            try {
                try {
                    File file = getFile(pfd);
                    if (file != null && file.canRead()) {
                        Typeface typefaceCreateFromFile = Typeface.createFromFile(file);
                        if (pfd == null) {
                            return typefaceCreateFromFile;
                        }
                        if (0 == 0) {
                            pfd.close();
                            return typefaceCreateFromFile;
                        }
                        try {
                            pfd.close();
                            return typefaceCreateFromFile;
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                            return typefaceCreateFromFile;
                        }
                    }
                    FileInputStream fis = new FileInputStream(pfd.getFileDescriptor());
                    Throwable th5 = null;
                    try {
                        Typeface typefaceCreateFromInputStream = super.createFromInputStream(context, fis);
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
                            return typefaceCreateFromInputStream;
                        }
                        if (0 == 0) {
                            pfd.close();
                            return typefaceCreateFromInputStream;
                        }
                        try {
                            pfd.close();
                            return typefaceCreateFromInputStream;
                        } catch (Throwable th7) {
                            th3.addSuppressed(th7);
                            return typefaceCreateFromInputStream;
                        }
                    } catch (Throwable th8) {
                        if (fis != null) {
                            if (0 != 0) {
                                try {
                                    fis.close();
                                } catch (Throwable th9) {
                                    th5.addSuppressed(th9);
                                }
                            } else {
                                fis.close();
                            }
                        }
                        throw th8;
                    }
                } catch (Throwable th10) {
                    th2 = th10;
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
                    } catch (Throwable th11) {
                        th.addSuppressed(th11);
                        throw th2;
                    }
                }
            } catch (Throwable th12) {
                try {
                    throw th12;
                } catch (Throwable th13) {
                    th = th12;
                    th2 = th13;
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
        return null;
    }
}
