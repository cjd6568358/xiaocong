package com.tencent.wxop.stat.common;

import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class m {
    static int a() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new n()).length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    static int b() {
        int iIntValue = 0;
        try {
            String str = "";
            InputStream inputStream = new ProcessBuilder("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq").start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            inputStream.close();
            String strTrim = str.trim();
            if (strTrim.length() > 0) {
                iIntValue = Integer.valueOf(strTrim).intValue();
            }
        } catch (Exception e) {
            l.k.e((Throwable) e);
        }
        return iIntValue * 1000;
    }

    static int c() {
        int iIntValue = 0;
        try {
            String str = "";
            InputStream inputStream = new ProcessBuilder("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq").start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            inputStream.close();
            String strTrim = str.trim();
            if (strTrim.length() > 0) {
                iIntValue = Integer.valueOf(strTrim).intValue();
            }
        } catch (Throwable th) {
            l.k.e(th);
        }
        return iIntValue * 1000;
    }

    static String d() {
        String[] strArr = {"", ""};
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), AsyncHttpClient.DEFAULT_SOCKET_BUFFER_SIZE);
            String[] strArrSplit = bufferedReader.readLine().split("\\s+");
            for (int i = 2; i < strArrSplit.length; i++) {
                strArr[0] = strArr[0] + strArrSplit[i] + " ";
            }
            bufferedReader.close();
        } catch (IOException e) {
        }
        return strArr[0];
    }
}
