package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class y {
    private static SimpleDateFormat b;
    private static StringBuilder d;
    private static StringBuilder e;
    private static boolean f;
    private static a g;
    private static String h;
    private static String i;
    private static Context j;
    private static String k;
    private static boolean l;
    private static int m;
    public static boolean a = true;
    private static int c = 5120;
    private static final Object n = new Object();

    static /* synthetic */ boolean a(boolean z) {
        f = false;
        return false;
    }

    static {
        b = null;
        try {
            b = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
        }
    }

    private static boolean b(String str, String str2, String str3) {
        try {
            com.tencent.bugly.crashreport.common.info.a aVarB = com.tencent.bugly.crashreport.common.info.a.b();
            if (aVarB != null && aVarB.D != null) {
                return aVarB.D.appendLogToNative(str, str2, str3);
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return false;
    }

    public static synchronized void a(Context context) {
        if (!l && context != null && a) {
            try {
                e = new StringBuilder(0);
                d = new StringBuilder(0);
                j = context;
                com.tencent.bugly.crashreport.common.info.a aVarA = com.tencent.bugly.crashreport.common.info.a.a(context);
                h = aVarA.d;
                aVarA.getClass();
                i = Constants.MAIN_VERSION_TAG;
                k = j.getFilesDir().getPath() + "/buglylog_" + h + "_" + i + ".txt";
                m = Process.myPid();
            } catch (Throwable th) {
            }
            l = true;
        }
    }

    public static void a(int i2) {
        synchronized (n) {
            c = i2;
            if (i2 < 0) {
                c = 0;
            } else if (i2 > 10240) {
                c = 10240;
            }
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (th != null) {
            String message = th.getMessage();
            if (message == null) {
                message = Constants.MAIN_VERSION_TAG;
            }
            a(str, str2, message + '\n' + z.b(th));
        }
    }

    public static synchronized void a(String str, String str2, String str3) {
        if (l && a) {
            b(str, str2, str3);
            long jMyTid = Process.myTid();
            d.setLength(0);
            if (str3.length() > 30720) {
                str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
            }
            Date date = new Date();
            d.append(b != null ? b.format(date) : date.toString()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(m).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(jMyTid).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(str).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(str2).append(": ").append(str3).append("\u0001\r\n");
            final String string = d.toString();
            synchronized (n) {
                e.append(string);
                if (e.length() > c) {
                    if (!f) {
                        f = true;
                        w.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.y.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                synchronized (y.n) {
                                    try {
                                        if (y.g == null) {
                                            a unused = y.g = new a(y.k);
                                        } else if (y.g.b == null || y.g.b.length() + ((long) y.e.length()) > y.g.e) {
                                            y.g.a();
                                        }
                                        if (y.g.a) {
                                            y.g.a(y.e.toString());
                                            y.e.setLength(0);
                                        } else {
                                            y.e.setLength(0);
                                            y.e.append(string);
                                        }
                                        y.a(false);
                                    } catch (Throwable th) {
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    public static byte[] a() {
        byte[] bArrA = null;
        if (a) {
            synchronized (n) {
                try {
                    File file = (g == null || !g.a) ? null : g.b;
                    if (e.length() != 0 || file != null) {
                        bArrA = z.a(file, e.toString(), "BuglyLog.txt");
                    }
                } catch (Throwable th) {
                }
            }
        }
        return bArrA;
    }

    /* JADX INFO: compiled from: BUGLY */
    public static class a {
        private boolean a;
        private File b;
        private String c;
        private long d;
        private long e = 30720;

        public a(String str) {
            if (str != null && !str.equals(Constants.MAIN_VERSION_TAG)) {
                this.c = str;
                this.a = a();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x001d, code lost:
        
            r0 = true;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean a() {
            boolean z = false;
            try {
                this.b = new File(this.c);
                if ((this.b.exists() && !this.b.delete()) || !this.b.createNewFile()) {
                    this.a = false;
                } else {
                    z = true;
                }
            } catch (Throwable th) {
                this.a = z;
                z = true;
            }
            return z;
        }

        /* JADX WARN: Code duplicated, block: B:38:0x003c A[EXC_TOP_SPLITTER, SYNTHETIC] */
        public final boolean a(String str) throws Throwable {
            FileOutputStream fileOutputStream;
            FileOutputStream fileOutputStream2;
            if (!this.a) {
                return false;
            }
            try {
                fileOutputStream = new FileOutputStream(this.b, true);
                try {
                    byte[] bytes = str.getBytes(HTTP.UTF_8);
                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    this.d += (long) bytes.length;
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                    }
                    return true;
                } catch (Throwable th) {
                    fileOutputStream2 = fileOutputStream;
                    try {
                        this.a = false;
                        if (fileOutputStream2 == null) {
                            return false;
                        }
                        try {
                            fileOutputStream2.close();
                            return false;
                        } catch (IOException e2) {
                            return false;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
        }
    }
}
