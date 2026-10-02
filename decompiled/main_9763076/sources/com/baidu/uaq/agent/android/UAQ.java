package com.baidu.uaq.agent.android;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;
import com.baidu.uaq.agent.android.customtransmission.APMAgent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Keep
public class UAQ {
    private AgentConfig savedConfig;
    private static volatile UAQ instance = null;
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private boolean started = false;
    private boolean isDisableCollect = false;
    private boolean needBasicInfo = true;
    private AgentConfig agentConfig = new AgentConfig.Builder().build();

    private UAQ() {
    }

    public static UAQ getInstance() {
        if (instance == null) {
            synchronized (UAQ.class) {
                if (instance == null) {
                    instance = new UAQ();
                }
            }
        }
        return instance;
    }

    public synchronized APMAgent startAPM(Context context) {
        APMAgent apmAgent;
        apmAgent = new APMAgent();
        apmAgent.setAgent(apmAgent);
        try {
            long startTime = System.currentTimeMillis();
            if (!(a.a() instanceof d)) {
                com.baidu.uaq.agent.android.logging.b.a(this.agentConfig.isLogEnabled() ? new com.baidu.uaq.agent.android.logging.c() : new com.baidu.uaq.agent.android.logging.e());
                LOG.setLevel(this.agentConfig.getLogLevel());
                d.a(context);
            }
            g.start();
            com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().c(context);
            long endTime = System.currentTimeMillis();
            LOG.E("Start UAQ " + a.getVersion() + "." + a.b() + ", using time: " + (endTime - startTime) + "ms");
            Log.d("Baidu UAQ APM", "Start UAQ APM instance success!");
        } catch (Throwable e) {
            LOG.a("Caught error while start the UAQ agent!", e);
        }
        return apmAgent;
    }

    public boolean isStarted() {
        return this.started;
    }

    public UAQ setConfig(AgentConfig agentConfig) {
        if (agentConfig == null) {
            throw new NullPointerException("agentConfig == null.");
        }
        this.agentConfig = agentConfig;
        return instance;
    }

    public synchronized void reconfig(AgentConfig agentConfig) {
        try {
            if (agentConfig == null) {
                throw new NullPointerException("agentConfig == null.");
            }
            this.agentConfig = agentConfig;
        } catch (Throwable th) {
            throw th;
        }
    }

    public AgentConfig getConfig() {
        return this.agentConfig;
    }

    public void disableCollect() {
        this.agentConfig = this.agentConfig.newBuilder().build();
    }

    public void enableCollect(AgentConfig savedConfig) {
        if (savedConfig == null) {
            throw new NullPointerException("savedConfig == null.");
        }
        this.agentConfig = savedConfig;
    }

    private boolean isInstrumented() {
        return false;
    }

    public AgentConfig getSavedConfig() {
        return this.savedConfig;
    }

    public void setSavedConfig(AgentConfig savedConfig) {
        this.savedConfig = savedConfig;
    }

    public boolean isDisableCollect() {
        return this.isDisableCollect;
    }

    public void setDisableCollect(boolean disableCollect) {
        this.isDisableCollect = disableCollect;
    }

    public boolean isNeedBasicInfo() {
        return this.needBasicInfo;
    }

    public void setNeedBasicInfo(boolean needBasicInfo) {
        this.needBasicInfo = needBasicInfo;
    }

    public static void onLiveEvent(String data) {
        LOG.E("Get Live Event: " + data);
        if (!data.isEmpty()) {
            com.baidu.uaq.agent.android.harvest.bean.g.a(new com.baidu.uaq.agent.android.harvest.bean.f(data));
        }
    }

    public static String getVersion() {
        return a.getVersion() + "." + a.b();
    }

    public static void setxxDebug(boolean isEnable) {
    }

    @Deprecated
    public void harvestNow() {
    }

    @Deprecated
    public void agentExceptionNow() {
        com.baidu.uaq.agent.android.harvest.health.a.a(new RuntimeException("This is a demonstration agent exception of UAQ"));
    }

    @Deprecated
    public static UAQ withApplicationToken(String appToken) {
        return getInstance();
    }

    @Deprecated
    public static UAQ setCUID(String cuid) {
        AgentConfig.Builder config = new AgentConfig.Builder().APIKey("0b32398f92284103a76f03680104c775").cuid(cuid).channel("APM_TEST_CHANNEL").logEnabled(true);
        getInstance().setConfig(config.build());
        return getInstance();
    }

    @Deprecated
    public static UAQ usingSsl(boolean val) {
        return getInstance();
    }

    @Deprecated
    public static UAQ usingCollectorAddress(String val) {
        return getInstance();
    }

    @Deprecated
    public static UAQ withLogPersist(boolean val) {
        return getInstance();
    }

    @Deprecated
    public static UAQ withLoggingEnabled(boolean val) {
        return getInstance();
    }

    @Deprecated
    public static UAQ withLogLevel(int val) {
        return getInstance();
    }

    @Deprecated
    public static void shutdown() {
        getInstance().shutdown_v2();
    }

    @Deprecated
    public synchronized void shutdown_v2() {
        try {
            try {
                LOG.E("Agent try to shutdown");
                if (isStarted()) {
                    disableCollect();
                    this.started = false;
                    a.a().shutdown();
                }
            } catch (Throwable e) {
                LOG.a("Caught error while stop the UAQ agent!", e);
                a.a(e.o);
                LOG.E("Agent finish shutdown");
            }
        } finally {
            a.a(e.o);
            LOG.E("Agent finish shutdown");
        }
    }
}
