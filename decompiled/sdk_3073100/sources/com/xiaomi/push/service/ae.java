package com.xiaomi.push.service;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.network.Host;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ae {
    private static final Pattern a = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
    private static long b = 0;
    private static ThreadPoolExecutor c = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, new LinkedBlockingQueue());

    public static void a() {
        com.xiaomi.push.protobuf.a.C0011a c0011aD;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if ((c.getActiveCount() <= 0 || jCurrentTimeMillis - b >= 1800000) && com.xiaomi.stats.f.a().c() && (c0011aD = at.a().d()) != null && c0011aD.m() > 0) {
            b = jCurrentTimeMillis;
            a(c0011aD.l(), true);
        }
    }

    public static void a(List<String> list, boolean z) {
        c.execute(new af(list, z));
    }

    public static void b() throws Throwable {
        String strC = c("/proc/self/net/tcp");
        if (!TextUtils.isEmpty(strC)) {
            com.xiaomi.channel.commonutils.logger.b.a("dump tcp for uid = " + Process.myUid());
            com.xiaomi.channel.commonutils.logger.b.a(strC);
        }
        String strC2 = c("/proc/self/net/tcp6");
        if (TextUtils.isEmpty(strC2)) {
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.a("dump tcp6 for uid = " + Process.myUid());
        com.xiaomi.channel.commonutils.logger.b.a(strC2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            com.xiaomi.channel.commonutils.logger.b.a("ConnectivityTest: begin to connect to " + str);
            Socket socket = new Socket();
            socket.connect(Host.b(str, 5222), 5000);
            socket.setTcpNoDelay(true);
            com.xiaomi.channel.commonutils.logger.b.a("ConnectivityTest: connect to " + str + " in " + (System.currentTimeMillis() - jCurrentTimeMillis));
            socket.close();
            return true;
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.d("ConnectivityTest: could not connect to:" + str + " exception: " + th.getClass().getSimpleName() + " description: " + th.getMessage());
            return false;
        }
    }

    private static String c(String str) throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        String string = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(str)));
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append("\n");
                    sb.append(line);
                }
                string = sb.toString();
                com.xiaomi.channel.commonutils.file.a.a(bufferedReader);
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.file.a.a(bufferedReader);
            } catch (Throwable th2) {
                th = th2;
                com.xiaomi.channel.commonutils.file.a.a(bufferedReader);
                throw th;
            }
        } catch (Exception e2) {
            bufferedReader = null;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
        }
        return string;
    }
}
