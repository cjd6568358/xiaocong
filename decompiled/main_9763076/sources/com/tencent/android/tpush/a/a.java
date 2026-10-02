package com.tencent.android.tpush.a;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.l;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushClientReport;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushMsg;
import com.tencent.android.tpush.service.e.m;
import com.tencent.android.tpush.service.n;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.client.methods.HttpTrace;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static boolean a = false;
    private static boolean o = false;
    public static String b = "tencent" + File.separator + Constants.LogTag + File.separator + "Logs";
    private static final SimpleDateFormat p = new SimpleDateFormat("MM.dd_HH:mm:ss_SSS");
    private static List q = Collections.synchronizedList(new ArrayList());
    private static boolean r = false;
    private static boolean s = false;
    private static String t = null;
    protected static volatile ExecutorService c = Executors.newSingleThreadExecutor(new c());
    public static AtomicInteger d = new AtomicInteger();
    public static AtomicInteger e = new AtomicInteger();
    public static AtomicInteger f = new AtomicInteger();
    public static AtomicInteger g = new AtomicInteger();
    public static AtomicInteger h = new AtomicInteger();
    public static AtomicInteger i = new AtomicInteger();
    public static AtomicInteger j = new AtomicInteger();
    public static AtomicInteger k = new AtomicInteger();
    public static AtomicInteger l = new AtomicInteger();
    public static AtomicInteger m = new AtomicInteger();
    public static AtomicInteger n = new AtomicInteger();

    public static void a(int i2) {
        switch (i2) {
            case 0:
                a = true;
                break;
            case 1:
                o = true;
                break;
            case 2:
                o = true;
                a = true;
                break;
            case 3:
                o = false;
                a = false;
                break;
            default:
                Log.e("XGLogger", "TLogger ->setLogToFile unknown cmd " + i2);
                break;
        }
    }

    public static boolean a(Context context) {
        return true;
    }

    public static void a(String str, String str2) {
        if (a && b(2)) {
            Log.v("XINGE", "[" + str + "] " + str2);
        }
        a(HttpTrace.METHOD_NAME, str, str2, null);
    }

    public static void b(String str, String str2) {
        if (b(2)) {
            Log.v("XINGE", "[" + str + "] " + str2);
        }
        a(HttpTrace.METHOD_NAME, str, str2, null);
    }

    public static void c(String str, String str2) {
        if (a && b(3)) {
            Log.d("XINGE", "[" + str + "] " + str2);
        }
        a("DEBUG", str, str2, null);
    }

    public static void d(String str, String str2) {
        if (b(3)) {
            Log.d("XINGE", "[" + str + "] " + str2);
        }
        a("DEBUG", str, str2, null);
    }

    public static void e(String str, String str2) {
        if (a && b(4)) {
            Log.i("XINGE", "[" + str + "] " + str2);
        }
        a("INFO", str, str2, null);
    }

    public static void f(String str, String str2) {
        if (b(4)) {
            Log.i("XINGE", "[" + str + "] " + str2);
        }
        a("INFO", str, str2, null);
    }

    public static void g(String str, String str2) {
        if (a && b(5)) {
            Log.w("XINGE", "[" + str + "] " + str2);
        }
        a("WARN", str, str2, null);
    }

    public static void h(String str, String str2) {
        if (b(5)) {
            Log.w("XINGE", "[" + str + "] " + str2);
        }
        a("WARN", str, str2, null);
    }

    public static void i(String str, String str2) {
        if (a && b(6)) {
            Log.e("XINGE", "[" + str + "] " + str2);
        }
        a("ERROR", str, str2, null);
    }

    public static void j(String str, String str2) {
        if (b(6)) {
            Log.e("XINGE", "[" + str + "] " + str2);
        }
        a("ERROR", str, str2, null);
    }

    public static void a(String str, String str2, Throwable th) {
        if (a && b(2)) {
            Log.v("XINGE", "[" + str + "] " + str2, th);
        }
        a(HttpTrace.METHOD_NAME, str, str2, th);
    }

    public static void b(String str, String str2, Throwable th) {
        if (a && b(5)) {
            Log.w("XINGE", "[" + str + "] " + str2, th);
        }
        a("WARN", str, str2, th);
    }

    public static void c(String str, String str2, Throwable th) {
        if (a && b(6)) {
            Log.e("XINGE", "[" + str + "] " + str2, th);
        }
        a("ERROR", str, str2, th);
    }

    public static void d(String str, String str2, Throwable th) {
        if (b(6)) {
            Log.e("XINGE", "[" + str + "] " + str2, th);
        }
        a("ERROR", str, str2, th);
    }

    private static boolean b(int i2) {
        return true;
    }

    private static void a(String str, String str2, String str3, Throwable th) {
        if (o || a(n.f())) {
            if (str2 == null || str2.trim().equals(Constants.MAIN_VERSION_TAG)) {
                str2 = "XGLogger";
            }
            String str4 = p.format(new Date());
            if (str3 == null) {
                str3 = Constants.MAIN_VERSION_TAG;
            }
            BufferedReader bufferedReader = new BufferedReader(new StringReader(str3), 256);
            String strA = m.a("[" + str2 + "]", 24);
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    } else {
                        a(((Object) str4) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + m.a(str, 5) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + strA + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + line);
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (th != null) {
                StringWriter stringWriter = new StringWriter();
                th.printStackTrace(new PrintWriter(stringWriter));
                a(((Object) str4) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str + stringWriter.toString());
            }
        }
    }

    private static void a(String str) {
        if (!s) {
            q.add(str);
            if (q.size() == 100) {
                List list = q;
                q = Collections.synchronizedList(new ArrayList());
                r = m.g();
                if (r) {
                    Log.v("XGLogger", "have writable external storage, write log file!");
                    a(list);
                } else {
                    Log.v("XGLogger", "no writable external storage");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c() {
        if (t != null) {
            return t;
        }
        try {
            t = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + b;
            return t;
        } catch (Throwable th) {
            Log.e("XGLogger", "TLogger ->getFileNamePre", th);
            return null;
        }
    }

    private static void a(List list) {
        if (a(n.f())) {
        }
        if (o) {
            try {
                c.execute(new b(list));
            } catch (Exception e2) {
                Log.e("XGLogger", "savelog error", e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d() {
        try {
            String strC = c();
            if (strC != null) {
                File file = new File(strC);
                if (file.exists()) {
                    int length = strC.length() + 5;
                    int length2 = length + com.tencent.android.tpush.service.e.b.a.length();
                    File[] fileArrListFiles = file.listFiles();
                    for (File file2 : fileArrListFiles) {
                        try {
                            if (file2.isFile()) {
                                String absolutePath = file2.getAbsolutePath();
                                if (com.tencent.android.tpush.service.e.b.a(com.tencent.android.tpush.service.e.b.a(absolutePath.substring(length, length2)), 7)) {
                                    Log.d("XGLogger", "delete logs file " + absolutePath);
                                    file2.delete();
                                }
                            }
                        } catch (Exception e2) {
                            Log.e("XGLogger", "removeOldDebugLogFiles" + e2);
                        }
                    }
                }
            }
        } catch (Exception e3) {
            Log.e("XGLogger", "removeOldDebugLogFiles", e3);
        }
    }

    public static void a(int i2, List list) {
        if (o) {
            ArrayList arrayList = new ArrayList();
            if (list != null && list.size() > 0) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Long.valueOf(((TpnsPushClientReport) it.next()).msgId));
                }
            }
            if (arrayList != null && arrayList.size() > 0) {
                c(i2, arrayList);
            }
        }
    }

    public static void b(int i2, List list) {
        if (o) {
            ArrayList arrayList = new ArrayList();
            if (list != null && list.size() > 0) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Long.valueOf(((TpnsPushMsg) it.next()).msgId));
                }
            }
            if (arrayList != null && arrayList.size() > 0) {
                c(i2, arrayList);
            }
        }
    }

    public static void a(int i2, long j2) {
        if (o) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Long.valueOf(j2));
            if (arrayList != null && arrayList.size() > 0) {
                c(i2, arrayList);
            }
        }
    }

    public static synchronized void c(int i2, List list) {
        int andIncrement;
        String str;
        FileWriter fileWriter;
        if (o) {
            FileWriter fileWriter2 = null;
            try {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String str2 = Environment.getExternalStorageDirectory() + "/";
                    switch (i2) {
                        case 0:
                            andIncrement = d.getAndIncrement();
                            str = str2 + "_0ServerSendToService.txt";
                            break;
                        case 1:
                            andIncrement = e.getAndIncrement();
                            str = str2 + "_1ServiceAckToServer.txt";
                            break;
                        case 2:
                            andIncrement = f.getAndIncrement();
                            str = str2 + "_2XgSdkReceiveFromXGService.txt";
                            break;
                        case 3:
                            andIncrement = g.getAndIncrement();
                            str = str2 + "_3SdkSendAckToService.txt";
                            break;
                        case 4:
                            andIncrement = h.getAndIncrement();
                            str = str2 + "_4ServiceRecAckFromSdk1.txt";
                            break;
                        case 5:
                            andIncrement = i.getAndIncrement();
                            str = str2 + "_5ServiceRecAckFromSdk2.txt";
                            break;
                        case 6:
                            andIncrement = j.getAndIncrement();
                            str = str2 + "_6ServiceRecAckFromSdk3.txt";
                            break;
                        case 7:
                            andIncrement = k.getAndIncrement();
                            str = str2 + "_7ServiceRecAckFromServer.txt";
                            break;
                        case 8:
                            andIncrement = l.getAndIncrement();
                            str = str2 + "_8ServiceRecAckFromServer_failed";
                            break;
                        case 9:
                        case 10:
                        default:
                            i("XGLogger", "unknown case");
                            if (0 != 0) {
                                try {
                                    fileWriter2.close();
                                } catch (IOException e2) {
                                }
                            }
                            break;
                        case 11:
                            andIncrement = m.getAndIncrement();
                            str = str2 + "_11unequal";
                            break;
                        case 12:
                            String str3 = str2 + "_12notList";
                            andIncrement = n.getAndIncrement();
                            str = str3;
                            break;
                    }
                    if (l.a("android.permission.WRITE_EXTERNAL_STORAGE")) {
                        fileWriter = new FileWriter(str, true);
                        try {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                fileWriter.write(Constants.MAIN_VERSION_TAG + andIncrement + "\t" + simpleDateFormat.format(new Date()) + "\tmsgid: " + ((Long) it.next()) + "\n");
                            }
                        } catch (Throwable th) {
                            th = th;
                            fileWriter2 = fileWriter;
                            c("XGLogger", "writeMsgSession error", th);
                            if (fileWriter2 != null) {
                                try {
                                    fileWriter2.close();
                                } catch (IOException e3) {
                                }
                            }
                        }
                    } else {
                        fileWriter = null;
                    }
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (IOException e4) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }
}
