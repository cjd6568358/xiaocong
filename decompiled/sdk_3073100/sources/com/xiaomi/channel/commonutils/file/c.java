package com.xiaomi.channel.commonutils.file;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import java.io.File;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c {
    public static boolean a() {
        try {
            return Environment.getExternalStorageState().equals("removed");
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            return true;
        }
    }

    public static boolean b() {
        try {
            return !Environment.getExternalStorageState().equals("mounted");
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            return true;
        }
    }

    public static boolean c() {
        return e() <= 102400;
    }

    public static boolean d() {
        return (b() || c() || a()) ? false : true;
    }

    public static long e() {
        File externalStorageDirectory;
        if (b() || (externalStorageDirectory = Environment.getExternalStorageDirectory()) == null || TextUtils.isEmpty(externalStorageDirectory.getPath())) {
            return 0L;
        }
        try {
            StatFs statFs = new StatFs(externalStorageDirectory.getPath());
            return (((long) statFs.getAvailableBlocks()) - 4) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            return 0L;
        }
    }
}
