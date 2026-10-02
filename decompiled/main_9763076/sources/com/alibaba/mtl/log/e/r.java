package com.alibaba.mtl.log.e;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.HttpStatus;

/* JADX INFO: compiled from: TaskExecutor.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class r {
    public static r a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static ThreadPoolExecutor f39a;
    private HandlerThread b = new HandlerThread("AppMonitor");
    private Handler mHandler;
    private static int F = 1;
    private static int G = 2;
    private static int H = 10;
    private static int I = 60;
    private static final AtomicInteger f = new AtomicInteger();

    /* JADX INFO: compiled from: TaskExecutor.java */
    static class a implements ThreadFactory {
        private int priority;

        public a(int i) {
            this.priority = i;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "AppMonitor:" + r.f.getAndIncrement());
            thread.setPriority(this.priority);
            return thread;
        }
    }

    @TargetApi(9)
    private static ThreadPoolExecutor a(int i, int i2, int i3, int i4, int i5) {
        LinkedBlockingQueue linkedBlockingQueue;
        if (i5 > 0) {
            linkedBlockingQueue = new LinkedBlockingQueue(i5);
        } else {
            linkedBlockingQueue = new LinkedBlockingQueue();
        }
        return new ThreadPoolExecutor(i2, i3, i4, TimeUnit.SECONDS, linkedBlockingQueue, new a(i), new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static synchronized ThreadPoolExecutor m28a() {
        if (f39a == null) {
            f39a = a(F, G, H, I, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        return f39a;
    }

    public static synchronized r a() {
        if (a == null) {
            a = new r();
        }
        return a;
    }

    private r() {
        this.b.start();
        this.mHandler = new Handler(this.b.getLooper()) { // from class: com.alibaba.mtl.log.e.r.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    if (msg.obj != null && (msg.obj instanceof Runnable)) {
                        r.m28a().submit((Runnable) msg.obj);
                    }
                } catch (Throwable th) {
                }
            }
        };
    }

    public final void a(int i, Runnable runnable, long j) {
        try {
            Message messageObtain = Message.obtain(this.mHandler, i);
            messageObtain.obj = runnable;
            this.mHandler.sendMessageDelayed(messageObtain, j);
        } catch (Exception e) {
            com.alibaba.mtl.appmonitor.b.b.m16a((Throwable) e);
        }
    }

    public final void f(int i) {
        this.mHandler.removeMessages(i);
    }

    public final boolean b(int i) {
        return this.mHandler.hasMessages(i);
    }

    public void b(Runnable runnable) {
        try {
            m28a().submit(runnable);
        } catch (Throwable th) {
        }
    }
}
