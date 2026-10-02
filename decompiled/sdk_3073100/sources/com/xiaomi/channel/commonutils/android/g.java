package com.xiaomi.channel.commonutils.android;

import android.os.Environment;
import com.xiaomi.channel.commonutils.file.a;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class g {
    private static int a = 0;

    public static synchronized boolean a() {
        boolean z;
        synchronized (g.class) {
            z = c() == 1;
        }
        return z;
    }

    public static synchronized boolean b() {
        return c() == 2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v2 */
    public static synchronized int c() {
        FileInputStream fileInputStream;
        int i;
        synchronized (g.class) {
            ?? r1 = a;
            try {
                if (r1 == 0) {
                    try {
                        Properties properties = new Properties();
                        fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                        try {
                            properties.load(fileInputStream);
                            a = properties.getProperty("ro.miui.ui.version.code", null) != null || properties.getProperty("ro.miui.ui.version.name", null) != null ? 1 : 2;
                            a.a(fileInputStream);
                        } catch (Throwable th) {
                            th = th;
                            com.xiaomi.channel.commonutils.logger.b.a("get isMIUI failed", th);
                            a = 0;
                            a.a(fileInputStream);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        r1 = 0;
                        a.a((InputStream) r1);
                        throw th;
                    }
                    com.xiaomi.channel.commonutils.logger.b.b("isMIUI's value is: " + a);
                }
                i = a;
            } catch (Throwable th3) {
                th = th3;
            }
        }
        return i;
    }
}
