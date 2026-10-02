package com.baidu.uaq.agent.android.harvest.multiharvest;

import android.app.Application;
import android.content.Context;
import com.baidu.uaq.agent.android.customtransmission.APMUploadConfigure;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;

/* JADX INFO: compiled from: MultiHarvest.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static a bb = new a();
    private c bc;
    private final ArrayList<c> bd = new ArrayList<>();
    private int be = 0;
    private Context j;

    public static a aB() {
        return bb;
    }

    public synchronized void c(Context context) {
        this.j = b(context);
        this.be++;
        if (this.be == 1) {
            aC();
            this.bc.start();
        }
        LOG.E("MultiHarvest start one time, instanceNumber now is " + this.be);
    }

    public synchronized void stop() {
        this.be--;
        if (this.be == 0) {
            com.baidu.uaq.agent.android.a.shutdown();
            com.baidu.uaq.agent.android.customtransmission.b.R().clear();
            com.baidu.uaq.agent.android.customtransmission.b.S().clear();
            synchronized (this.bd) {
                for (c multiHarvestTimer : this.bd) {
                    multiHarvestTimer.stop();
                }
                this.bd.clear();
            }
            if (this.bc != null) {
                this.bc.stop();
                this.bc = null;
            }
        }
        LOG.E("MultiHarvest stop one time, instanceNumber now is " + this.be);
    }

    public void b(APMUploadConfigure apmUploadConfigure) {
        if (com.baidu.uaq.agent.android.customtransmission.b.R().containsKey(apmUploadConfigure.getUploadName())) {
            LOG.E("addUploadCofigure already exists:" + apmUploadConfigure.getUploadName() + " size:" + com.baidu.uaq.agent.android.customtransmission.b.R().size());
            com.baidu.uaq.agent.android.customtransmission.b.a(apmUploadConfigure);
        } else {
            com.baidu.uaq.agent.android.customtransmission.b.a(apmUploadConfigure);
            LOG.E("addUploadCofigure:" + apmUploadConfigure.getUploadName());
            bb.c(apmUploadConfigure);
        }
    }

    private void c(APMUploadConfigure apmUploadConfigure) {
        c harvestTimer4Custom = new c(this.j, apmUploadConfigure);
        harvestTimer4Custom.start();
        synchronized (this.bd) {
            this.bd.add(harvestTimer4Custom);
        }
    }

    private void aC() {
        if (this.bc == null) {
            APMUploadConfigure defaultConfig4APM = new APMUploadConfigure(APMUploadConfigure.APMUPLOADNAME, Constants.MAIN_VERSION_TAG, null);
            com.baidu.uaq.agent.android.customtransmission.b.a(defaultConfig4APM);
            this.bc = new c(this.j, defaultConfig4APM);
        }
    }

    private static Context b(Context context) {
        if (!(context instanceof Application)) {
            return context.getApplicationContext();
        }
        return context;
    }

    public int aD() {
        return this.be;
    }
}
