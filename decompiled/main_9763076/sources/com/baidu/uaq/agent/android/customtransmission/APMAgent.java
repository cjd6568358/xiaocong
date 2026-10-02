package com.baidu.uaq.agent.android.customtransmission;

import android.support.annotation.Keep;
import com.baidu.uaq.agent.android.harvest.bean.f;
import com.baidu.uaq.agent.android.harvest.bean.g;
import java.net.MalformedURLException;
import java.net.URL;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Keep
public class APMAgent {
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private APMAgent apmAgent;

    public synchronized void stopAPM() {
        if (this.apmAgent != null) {
            com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().stop();
            this.apmAgent = null;
        } else {
            LOG.E("This instance already stop one time");
        }
    }

    public APMUploadConfigure newUploadConfigure(String uploadName, String url, MergeBlockCallBack mergeBlockCallBack) {
        return new APMUploadConfigure(uploadName, url, mergeBlockCallBack);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x004e  */
    public APMUploadHandler addUploadConfigure(APMUploadConfigure apmUploadConfigure) {
        APMUploadHandler apmUploadHandler = null;
        if (apmUploadConfigure == null || apmUploadConfigure.getUploadName() == null || apmUploadConfigure.getUploadName().isEmpty()) {
            LOG.error("添加上报策略失败：APMUploadConfigure, uploadName 有空值");
        } else if (!apmUploadConfigure.getUploadName().equals(APMUploadConfigure.APMUPLOADNAME)) {
            String url = apmUploadConfigure.getUrl();
            if (apmUploadConfigure.getMergeBlockCallBack() == null) {
                LOG.error("添加上报策略失败：mergeBlockCallBack为空");
            } else {
                try {
                    new URL(url);
                    apmUploadHandler = apmUploadConfigure.getApmUploadHandler();
                    if (com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().aD() > 0) {
                        com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().b(apmUploadConfigure);
                    }
                    LOG.E("addUploadConfigure getInstanceNumber:" + com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().aD());
                } catch (MalformedURLException e) {
                    LOG.error("添加上报策略失败：url for newUploadConfigure is not legal! url: " + url);
                }
            }
        } else {
            apmUploadHandler = apmUploadConfigure.getApmUploadHandler();
            if (com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().aD() > 0) {
                com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().b(apmUploadConfigure);
            }
            LOG.E("addUploadConfigure getInstanceNumber:" + com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().aD());
        }
        return apmUploadHandler;
    }

    public void addLogWithHandler(APMUploadHandler apmUploadHandler, String log) {
        if (apmUploadHandler == null || log.isEmpty()) {
            LOG.info("APMAgent addLogWithHandler failed, cause APMUploadHandler is null or log is empty!");
        } else if (apmUploadHandler.getUploadName().equals(APMUploadConfigure.APMUPLOADNAME)) {
            g.a(new f(log));
        } else {
            b.b(apmUploadHandler.getUploadName(), log);
        }
    }

    public void addDebugLog(String log) {
    }

    public void setAgent(APMAgent apmAgent) {
        this.apmAgent = apmAgent;
    }

    @Deprecated
    public void removeUploadConfigure(APMUploadHandler apmUploadHandler) {
        if (apmUploadHandler != null) {
            b.e(apmUploadHandler.getUploadName());
        }
    }
}
