package com.baidu.uaq.agent.android.crashes;

import com.baidu.uaq.agent.android.UAQ;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: CrashReporter.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static ExecutorService X;
    private static d n;
    private Thread.UncaughtExceptionHandler V;
    private boolean Y;
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static final UAQ AGENT = UAQ.getInstance();
    private static final c U = new c();
    private static final AtomicBoolean W = new AtomicBoolean(false);

    public c() {
        X = Executors.newCachedThreadPool(new com.baidu.uaq.agent.android.util.f("CrashReporter"));
        this.Y = false;
    }

    public static void a(d crashStore) {
        U.Y = true;
        if (W.compareAndSet(false, true)) {
            c cVar = U;
            n = crashStore;
            X.submit(new Runnable() { // from class: com.baidu.uaq.agent.android.crashes.c.1
                @Override // java.lang.Runnable
                public void run() {
                    c.U.D();
                }
            });
            U.E();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void D() {
        LOG.E("reportSavedCrashes, size=" + n.count());
        for (com.baidu.uaq.agent.android.crashes.b crash : n.L()) {
            a(crash);
        }
    }

    public static void a(com.baidu.uaq.agent.android.crashes.b crash) {
        X.submit(new a(crash));
    }

    /* JADX INFO: compiled from: CrashReporter.java */
    private static class a implements Runnable {
        private final com.baidu.uaq.agent.android.crashes.b Z;

        a(com.baidu.uaq.agent.android.crashes.b crash) {
            this.Z = crash;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            String protocol = c.AGENT.getConfig().isUseSsl() ? "https://" : "http://";
            String port = ":" + c.AGENT.getConfig().getCollectorPort();
            String urlString = protocol + c.AGENT.getConfig().getCollectorHost() + port + "/mobile_crash/v2/index.php";
            InputStreamReader inputStreamReader = null;
            try {
                try {
                    c.LOG.E("Crash url = " + urlString);
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    if (connection == null) {
                        c.LOG.E("connection is null when send crash data!");
                        if (0 != 0) {
                            try {
                                inputStreamReader.close();
                                return;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        return;
                    }
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod(HttpPost.METHOD_NAME);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty(HTTP.CONTENT_TYPE, "application/json");
                    connection.setRequestProperty("X-App-License-Key", c.AGENT.getConfig().getAPIKey());
                    byte[] data = this.Z.bf().getBytes();
                    connection.setFixedLengthStreamingMode(data.length);
                    connection.setRequestProperty(HTTP.CONTENT_LEN, String.valueOf(data.length));
                    OutputStream out = connection.getOutputStream();
                    out.write(data);
                    out.flush();
                    out.close();
                    if (connection.getResponseCode() == 200) {
                        c.LOG.E("Crash " + this.Z.getUuid().toString() + " successfully submitted.");
                        InputStream inputStream = connection.getInputStream();
                        InputStreamReader inputStreamReader2 = new InputStreamReader(inputStream);
                        try {
                            BufferedReader reader = new BufferedReader(inputStreamReader2);
                            StringBuilder resultBuffer = new StringBuilder();
                            while (true) {
                                String tempLine = reader.readLine();
                                if (tempLine == null) {
                                    break;
                                } else {
                                    resultBuffer.append(tempLine);
                                }
                            }
                            c.LOG.E("send crash success, response: " + resultBuffer.toString());
                            c.n.c(this.Z);
                            inputStreamReader = inputStreamReader2;
                        } catch (Exception e2) {
                            e = e2;
                            inputStreamReader = inputStreamReader2;
                            c.LOG.a("Unable to report crash to UAQ, will try again later.", e);
                            if (inputStreamReader != null) {
                                try {
                                    inputStreamReader.close();
                                    return;
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                    return;
                                }
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            inputStreamReader = inputStreamReader2;
                            if (inputStreamReader != null) {
                                try {
                                    inputStreamReader.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } else {
                        c.LOG.error("Something went wrong while submitting a crash (will try again later) - Response code " + connection.getResponseCode());
                    }
                    connection.disconnect();
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                } catch (Exception e6) {
                    e = e6;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    private void E() {
        Thread.UncaughtExceptionHandler currentExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (currentExceptionHandler != null) {
            if (currentExceptionHandler instanceof b) {
                LOG.E("UAQ crash handler already installed.");
                return;
            } else {
                this.V = currentExceptionHandler;
                LOG.E("Installing UAQ crash handler and chaining " + this.V.getClass().getName());
            }
        } else {
            LOG.E("Installing UAQ crash handler.");
        }
        Thread.setDefaultUncaughtExceptionHandler(new b());
    }

    /* JADX INFO: compiled from: CrashReporter.java */
    private class b implements Thread.UncaughtExceptionHandler {
        private final AtomicBoolean aa;

        private b() {
            this.aa = new AtomicBoolean(false);
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable throwable) {
            if (this.aa.compareAndSet(false, true)) {
                if (!c.AGENT.getConfig().isReportCrashes() || !c.U.Y) {
                    c.LOG.E("A crash has been detected but crash reporting is disabled!");
                    a(thread, throwable);
                    return;
                }
                try {
                    com.baidu.uaq.agent.android.stats.b timer = new com.baidu.uaq.agent.android.stats.b();
                    timer.bu();
                    c.LOG.E("A crash has been detected in " + thread.getStackTrace()[0].getClassName() + " and will be reported ASAP.");
                    com.baidu.uaq.agent.android.crashes.b crash = new com.baidu.uaq.agent.android.crashes.b(throwable);
                    c.a(crash);
                    c.LOG.E("Crash collection took " + timer.bv() + "ms");
                    a(thread, throwable);
                    return;
                } catch (Throwable th) {
                    a(thread, throwable);
                    return;
                }
            }
            com.baidu.uaq.agent.android.stats.a.br().L("Supportability/AgentHealth/Recursion/UncaughtExceptionHandler");
        }

        private void a(Thread thread, Throwable throwable) {
            if (c.this.V != null) {
                c.LOG.E("Chaining crash reporting duties to " + c.this.V.getClass().getSimpleName());
                c.this.V.uncaughtException(thread, throwable);
            }
        }
    }
}
