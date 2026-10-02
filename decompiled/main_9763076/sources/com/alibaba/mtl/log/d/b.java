package com.alibaba.mtl.log.d;

import android.os.SystemClock;
import android.text.TextUtils;
import com.alibaba.mtl.log.a.c;
import com.alibaba.mtl.log.a.d;
import com.alibaba.mtl.log.e.e;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.k;
import com.alibaba.mtl.log.e.l;
import com.alibaba.mtl.log.e.n;
import com.alibaba.mtl.log.e.t;
import com.tencent.android.tpush.common.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPOutputStream;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: UploadTask.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class b implements Runnable {
    private static volatile boolean F = false;
    private static boolean G = false;
    static int A = 0;
    int B = -1;
    float a = 200.0f;
    int C = 0;

    public abstract void K();

    public abstract void L();

    @Override // java.lang.Runnable
    public void run() {
        try {
            M();
            K();
        } catch (Throwable th) {
        }
    }

    public static boolean isRunning() {
        return F;
    }

    private void M() throws Throwable {
        String str;
        int i;
        if (!l.isConnected() || G || F) {
            return;
        }
        F = true;
        int i2 = 0;
        Map<String, c> mapB = d.a().b();
        int i3 = 0;
        while (i3 < 3) {
            if (!k.c(com.alibaba.mtl.log.a.getContext())) {
                i.a("UploadTask", "Other Process is Uploading, break");
                break;
            }
            com.alibaba.mtl.log.c.c.a().G();
            List<com.alibaba.mtl.log.model.a> list = null;
            if (mapB != null && mapB.size() > 0) {
                int i4 = i2;
                while (true) {
                    if (i4 >= mapB.size()) {
                        i = i4;
                        str = null;
                        break;
                    }
                    c cVar = mapB.get(i4 + Constants.MAIN_VERSION_TAG);
                    String string = null;
                    if (cVar.a != null && cVar.a.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("eventId").append(" in (");
                        int i5 = 0;
                        while (true) {
                            int i6 = i5;
                            if (i6 >= cVar.a.size()) {
                                break;
                            }
                            if (i6 != 0) {
                                sb.append(" , ");
                            }
                            sb.append(cVar.a.get(i6));
                            i5 = i6 + 1;
                        }
                        sb.append(" ) ");
                        string = sb.toString();
                    }
                    List<com.alibaba.mtl.log.model.a> listA = com.alibaba.mtl.log.c.c.a().a(string, h());
                    if (listA.size() > 0) {
                        i = i4;
                        str = cVar.Q;
                        list = listA;
                        break;
                    }
                    i4++;
                    list = listA;
                }
            } else {
                str = null;
                i = i2;
            }
            List<com.alibaba.mtl.log.model.a> listA2 = (list == null || (list != null && list.size() == 0)) ? com.alibaba.mtl.log.c.c.a().a(null, h()) : list;
            if (listA2 == null || listA2.size() == 0) {
                F = false;
                break;
            }
            int iB = b(listA2);
            Map<String, Object> mapA = a(listA2);
            if (mapA == null || mapA.size() == 0) {
                F = false;
                break;
            }
            try {
                try {
                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                    String str2 = com.alibaba.mtl.log.a.a.M;
                    if (!TextUtils.isEmpty(str)) {
                        str2 = "http://" + str + "/rest/sur";
                    }
                    com.alibaba.mtl.log.e.a.C0001a c0001aA = a(t.a(str2, null, mapA), mapA);
                    boolean z = c0001aA.H;
                    long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                    a(Boolean.valueOf(z), jElapsedRealtime2 - jElapsedRealtime);
                    if (!z) {
                        com.alibaba.mtl.log.b.a.d(listA2.size() - iB);
                        com.alibaba.mtl.log.b.a.u();
                        if (!c0001aA.g()) {
                            if (c0001aA.h()) {
                                G = true;
                                k.release();
                                break;
                            }
                        } else {
                            k.release();
                            break;
                        }
                    } else {
                        int iA = com.alibaba.mtl.log.c.c.a().a(listA2);
                        if (iA < listA2.size() - iB) {
                            L();
                        }
                        com.alibaba.mtl.log.b.a.a(listA2, iA);
                        com.alibaba.mtl.log.b.a.t();
                    }
                    long jElapsedRealtime3 = SystemClock.elapsedRealtime();
                    i.a("UploadTask", "logs.size():", Integer.valueOf(listA2.size()), " selfMonitorLogCount:", Integer.valueOf(iB));
                    i.a("UploadTask", "upload isSendSuccess:", Boolean.valueOf(z), " consume:", Long.valueOf(jElapsedRealtime2 - jElapsedRealtime), " delete consume:", Long.valueOf(jElapsedRealtime3 - jElapsedRealtime2));
                    try {
                        Thread.sleep(new Random().nextInt(5000));
                    } catch (Throwable th) {
                        i.a("UploadTask", "thread sleep interrupted", th);
                    }
                    k.release();
                } catch (Throwable th2) {
                    k.release();
                    throw th2;
                }
            } catch (Throwable th3) {
                k.release();
            }
            i3++;
            i2 = i;
        }
        F = false;
        k.release();
    }

    private int b(List<com.alibaba.mtl.log.model.a> list) {
        if (list == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str = list.get(i2).T;
            if (str != null && "6005".equalsIgnoreCase(str.toString())) {
                i++;
            }
        }
        return i;
    }

    private int h() {
        if (this.B == -1) {
            String strT = l.t();
            if ("wifi".equalsIgnoreCase(strT)) {
                this.B = 20;
            } else if ("4G".equalsIgnoreCase(strT)) {
                this.B = 16;
            } else if ("3G".equalsIgnoreCase(strT)) {
                this.B = 12;
            } else {
                this.B = 8;
            }
        }
        return this.B;
    }

    private com.alibaba.mtl.log.e.a.C0001a a(String str, Map<String, Object> map) {
        String str2;
        if (str != null) {
            byte[] bArr = e.a(2, str, map, false).e;
            i.a("UploadTask", "url:", str);
            if (bArr != null) {
                try {
                    str2 = new String(bArr, HTTP.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    str2 = null;
                }
                if (str2 != null) {
                    i.a("UploadTask", "result:", str2);
                    return com.alibaba.mtl.log.e.a.a(str2);
                }
            }
        }
        return com.alibaba.mtl.log.e.a.C0001a.a;
    }

    private int a(Boolean bool, long j) {
        if (j < 0) {
            return this.B;
        }
        float f = this.C / j;
        if (!bool.booleanValue()) {
            this.B /= 2;
            A++;
        } else {
            if (j > 45000) {
                return this.B;
            }
            this.B = (int) ((((double) (f * 45000.0f)) / ((double) this.a)) - ((double) A));
        }
        if (this.B < 1) {
            this.B = 1;
            A = 0;
        } else if (this.B > 350) {
            this.B = 350;
        }
        i.a("UploadTask", "winsize:", Integer.valueOf(this.B));
        return this.B;
    }

    private Map<String, Object> a(List<com.alibaba.mtl.log.model.a> list) throws Throwable {
        if (list == null || list.size() == 0) {
            return null;
        }
        HashMap map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            List<String> listA = a(list.get(i));
            if (listA != null) {
                for (int i2 = 0; i2 < listA.size(); i2++) {
                    StringBuilder sb = (StringBuilder) map.get(listA.get(i2));
                    if (sb == null) {
                        sb = new StringBuilder();
                        map.put(listA.get(i2), sb);
                    } else {
                        sb.append("\n");
                    }
                    sb.append(list.get(i).h());
                }
            }
        }
        HashMap map2 = new HashMap();
        this.C = 0;
        for (String str : map.keySet()) {
            byte[] bArrA = a(((StringBuilder) map.get(str)).toString());
            map2.put(str, bArrA);
            this.C += bArrA.length;
        }
        this.a = this.C / list.size();
        i.a("UploadTask", "averagePackageSize:", Float.valueOf(this.a));
        return map2;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x003b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private byte[] a(String str) throws Throwable {
        GZIPOutputStream gZIPOutputStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            try {
                try {
                    gZIPOutputStream.write(str.getBytes(HTTP.UTF_8));
                    gZIPOutputStream.flush();
                    if (gZIPOutputStream != null) {
                        try {
                            gZIPOutputStream.close();
                        } catch (Exception e) {
                        }
                    }
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    if (gZIPOutputStream != null) {
                        try {
                            gZIPOutputStream.close();
                        } catch (Exception e3) {
                        }
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (gZIPOutputStream != null) {
                    try {
                        gZIPOutputStream.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            gZIPOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            gZIPOutputStream = null;
            if (gZIPOutputStream != null) {
                gZIPOutputStream.close();
            }
            throw th;
        }
        byte[] bArrA = n.a(byteArrayOutputStream.toByteArray(), "QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn3j5JELI8H6wtACbUnZ5cc3aYTsTRbmkAkRJeYbtx92LPBWm7nBO9UIl7y5i5MQNmUZNf5QENurR5tGyo7yJ2G0MBjWvy6iAtlAbacKP0SwOUeUWx5dsBdyhxa7Id1APtybSdDgicBDuNjI0mlZFUzZSS9dmN8lBD0WTVOMz0pRZbR3cysomRXOO1ghqjJdTcyDIxzpNAEszN8RMGjrzyU7Hjbmwi6YNK");
        try {
            byteArrayOutputStream.close();
        } catch (Exception e6) {
        }
        return bArrA;
    }

    private List<String> a(com.alibaba.mtl.log.model.a aVar) {
        return com.alibaba.mtl.log.a.a.m17a(aVar.T);
    }
}
