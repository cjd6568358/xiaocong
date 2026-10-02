package com.tencent.mid.util;

import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class k {
    k() {
    }

    static int a() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new l()).length;
        } catch (Exception e) {
            j.d.b(e);
            return 1;
        }
    }

    static int b() {
        int iIntValue = 0;
        InputStream inputStream = null;
        try {
            String str = Constants.MAIN_VERSION_TAG;
            inputStream = new ProcessBuilder("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq").start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            String strTrim = str.trim();
            iIntValue = strTrim.length() > 0 ? Integer.valueOf(strTrim).intValue() : 0;
        } catch (Exception e) {
            j.d.b(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                }
            }
        }
        return iIntValue * 1000;
    }

    static int c() {
        int iIntValue = 0;
        InputStream inputStream = null;
        try {
            String str = Constants.MAIN_VERSION_TAG;
            inputStream = new ProcessBuilder("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq").start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            String strTrim = str.trim();
            iIntValue = strTrim.length() > 0 ? Integer.valueOf(strTrim).intValue() : 0;
        } catch (IOException e) {
            j.d.b((Exception) e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                }
            }
        }
        return iIntValue * 1000;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0047 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    static String d() throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
                try {
                    String line = bufferedReader.readLine();
                    if (!TextUtils.isEmpty(line)) {
                        String[] strArrSplit = line.split(":\\s+", 2);
                        if (strArrSplit.length > 0) {
                            String str = strArrSplit[1];
                            if (bufferedReader == null) {
                                return str;
                            }
                            try {
                                bufferedReader.close();
                                return str;
                            } catch (Exception e) {
                                return str;
                            }
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception e2) {
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    j.d.f(th);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception e3) {
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (0 != 0) {
                    try {
                        bufferedReader2.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
        return Constants.MAIN_VERSION_TAG;
    }
}
