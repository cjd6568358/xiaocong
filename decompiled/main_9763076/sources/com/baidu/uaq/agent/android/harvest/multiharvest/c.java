package com.baidu.uaq.agent.android.harvest.multiharvest;

import android.content.Context;
import com.baidu.uaq.agent.android.UAQ;
import com.baidu.uaq.agent.android.customtransmission.APMUploadConfigure;
import com.baidu.uaq.agent.android.g;
import com.baidu.uaq.agent.android.util.f;
import com.baidu.uaq.agent.android.util.h;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: MultiHarvestTimer.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c implements Runnable {
    private static final UAQ AGENT = UAQ.getInstance();
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static long bq;
    private final d bp;
    private ScheduledExecutorService br = Executors.newSingleThreadScheduledExecutor(new f("HarvestTimer"));
    private ScheduledFuture bs;
    private long bt;
    private APMUploadConfigure bu;
    private Context j;
    private long period;
    private String uploadName;

    public c(Context context, APMUploadConfigure apmUploadConfigure) {
        this.j = context;
        this.uploadName = apmUploadConfigure.getUploadName();
        this.bu = apmUploadConfigure;
        this.bp = new d(context, apmUploadConfigure);
    }

    public void start() {
        LOG.E("HarvestTimer: Start a harvestTimer, uploadName:" + this.bu.getUploadName());
        if (isRunning()) {
            LOG.warning("HarvestTimer: Attempting to start while already running");
            return;
        }
        aN();
        if (this.period <= 0) {
            LOG.error("HarvestTimer: Refusing to start with a period <= 0 ms");
            return;
        }
        LOG.E("HarvestTimer: Starting with a period of " + this.period + "ms");
        bq = System.currentTimeMillis();
        this.bs = this.br.scheduleAtFixedRate(this, 0L, this.period, TimeUnit.MILLISECONDS);
        if (this.bu.getUploadName().equals(APMUploadConfigure.APMUPLOADNAME)) {
            g.start();
        }
    }

    public void stop() {
        if (isRunning()) {
            LOG.E("HarvestTimer: Stop a harvestTimer when period is " + this.period + "ms");
            bq = 0L;
            this.bs.cancel(true);
            this.bs = null;
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this) {
            try {
                aI();
                aO();
                long lastPeriod = this.period;
                aN();
                LOG.E("period lasPeriod:" + lastPeriod + " now:" + this.period);
                if (this.period <= 0) {
                    stop();
                } else if (lastPeriod != this.period) {
                    this.bs.cancel(true);
                    this.bs = this.br.scheduleAtFixedRate(this, this.period, this.period, TimeUnit.MILLISECONDS);
                }
            } catch (Exception e) {
                LOG.a("HarvestTimer: Exception in timer tick: ", e);
                com.baidu.uaq.agent.android.harvest.health.a.a(e);
            }
        }
    }

    private void aI() throws Throwable {
        long lastTickDelta = aL();
        if (1000 + lastTickDelta < this.period && lastTickDelta != -1) {
            LOG.E("HarvestTimer: Tick is too soon (" + lastTickDelta + " delta) Last tick time: " + this.bt + " . Skipping.");
            return;
        }
        LOG.E("================= Tick Begin for " + this.bu.getUploadName() + " =====================");
        LOG.E("HarvestTimer: time since last tick: " + lastTickDelta);
        long tickStart = aP();
        try {
            aJ();
        } catch (Exception e) {
            LOG.a("HarvestTimer: Exception in timer tick: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        this.bt = tickStart;
        LOG.E("================= Tick End  for " + this.bu.getUploadName() + " =====================");
    }

    private void aJ() throws Throwable {
        com.baidu.uaq.agent.android.stats.b t = new com.baidu.uaq.agent.android.stats.b();
        t.bu();
        try {
            this.bp.d(this.bu);
        } catch (Exception e) {
            LOG.a("HarvestTimer: Exception in harvest execute: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        long tickDelta = t.bv();
        LOG.E("HarvestTimer tick took " + tickDelta + "ms");
    }

    public boolean isRunning() {
        return this.bs != null;
    }

    private long aL() {
        if (this.bt == 0) {
            return -1L;
        }
        return aP() - this.bt;
    }

    private void aN() {
        if (this.bu == null) {
            this.period = -1L;
            return;
        }
        if (h.k(this.j)) {
            LOG.E("getPeriod for name:" + this.bu.getUploadName());
            this.period = this.bu.getIntervalWifi();
        } else if (h.j(this.j)) {
            this.period = this.bu.getInterval4g();
        } else {
            this.period = AGENT.getConfig().getDataReportPeriod();
        }
    }

    private void aO() {
        if (com.baidu.uaq.agent.android.customtransmission.b.R() != null) {
            this.bu = com.baidu.uaq.agent.android.customtransmission.b.R().get(this.uploadName);
        } else {
            this.bu = null;
        }
    }

    private long aP() {
        return System.currentTimeMillis();
    }
}
