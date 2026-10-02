package com.baidu.uaq.agent.android;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: TaskQueue.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static Future H;
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static final UAQ AGENT = UAQ.getInstance();
    private static final ScheduledExecutorService E = Executors.newSingleThreadScheduledExecutor(new com.baidu.uaq.agent.android.util.f("TaskQueue"));
    private static final ConcurrentLinkedQueue<Object> F = new ConcurrentLinkedQueue<>();
    private static final Runnable G = new Runnable() { // from class: com.baidu.uaq.agent.android.g.1
        @Override // java.lang.Runnable
        public void run() {
            g.x();
        }
    };

    public static void a(Object object) {
        if (com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().aD() >= 0 && !AGENT.isDisableCollect() && H != null) {
            F.add(object);
        }
    }

    public static void start() {
        if (H == null) {
            H = E.scheduleAtFixedRate(G, 0L, 1000L, TimeUnit.MILLISECONDS);
            LOG.E("TaskQueue start");
        }
    }

    public static void stop() {
        if (H != null) {
            H.cancel(true);
            H = null;
            LOG.E("TaskQueue stop");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void x() {
        if (F.size() != 0) {
            while (!F.isEmpty()) {
                try {
                    Object object = F.remove();
                    if (object instanceof com.baidu.uaq.agent.android.harvest.health.b) {
                        com.baidu.uaq.agent.android.harvest.multiharvest.d.c((com.baidu.uaq.agent.android.harvest.health.b) object);
                    } else if (object instanceof com.baidu.uaq.agent.android.harvest.bean.f) {
                        com.baidu.uaq.agent.android.harvest.multiharvest.d.d((com.baidu.uaq.agent.android.harvest.bean.f) object);
                    }
                } catch (Exception e) {
                    LOG.a("Caught error while TaskQueue dequeue: ", e);
                    com.baidu.uaq.agent.android.harvest.health.a.a(e);
                }
            }
        }
    }
}
