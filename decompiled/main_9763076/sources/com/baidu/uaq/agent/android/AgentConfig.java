package com.baidu.uaq.agent.android;

import android.support.annotation.Keep;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Keep
public class AgentConfig {
    private static final String DEFAULT_COLLECTOR_HOST = "report.uaq.baidu.com";
    private static final int DEFAULT_COLLECTOR_PORT = 443;
    private static final long DEFAULT_DATA_REPORT_LIMIT = 204800;
    private static final long DEFAULT_DATA_REPORT_PERIOD = 60000;
    private static final int DEFAULT_HARVESTABLE_CACHE_LIMIT = 1024;
    private static final int DEFAULT_LOG_LEVEL = 5;
    private static final int DEFAULT_RESPONSE_BODY_LIMIT = 2048;
    private static final long DEFAULT_SAMPLER_FREQ = 100;
    private static final double DEFAULT_SAMPLE_RATE = 100.0d;
    private static final int DEFAULT_STACK_TRACE_LIMIT = 100;
    private String APIKey;
    private String channel;
    private boolean collectAgentHealth;
    private String collectorHost;
    private int collectorPort;
    private String cuid;
    private long dataReportLimit;
    private long dataReportPeriod;
    private boolean enableMobileNetworkReport;
    private boolean enableStatsEngine;
    private boolean enableTransmission;
    private int harvestableCacheLimit;
    private boolean logEnabled;
    private int logLevel;
    private boolean nativeControlDRP;
    private boolean reportCrashes;
    private long responseBodyLimit;
    private double sampleRate;
    private long samplerFreq;
    private boolean useLogPersist;
    private boolean usePersistentUUID;
    private boolean useSsl;

    private AgentConfig(Builder builder) {
        this.APIKey = builder.APIKey;
        this.logEnabled = builder.logEnabled;
        this.logLevel = builder.logLevel;
        this.useSsl = builder.useSsl;
        this.collectorHost = builder.collectorHost;
        this.collectorPort = builder.collectorPort;
        this.dataReportPeriod = builder.dataReportPeriod;
        this.nativeControlDRP = builder.nativeControlDRP;
        this.dataReportLimit = builder.dataReportLimit;
        this.enableMobileNetworkReport = builder.enableMobileNetworkReport;
        this.useLogPersist = builder.useLogPersist;
        this.reportCrashes = builder.reportCrashes;
        this.usePersistentUUID = builder.usePersistentUUID;
        this.responseBodyLimit = builder.responseBodyLimit;
        this.sampleRate = builder.sampleRate;
        this.cuid = builder.cuid;
        this.channel = builder.channel;
        this.harvestableCacheLimit = builder.harvestableCacheLimit;
        this.samplerFreq = builder.samplerFreq;
        this.enableTransmission = builder.enableTransmission;
        this.collectAgentHealth = builder.collectAgentHealth;
        this.enableStatsEngine = builder.enableStatsEngine;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public String getAPIKey() {
        return this.APIKey;
    }

    public boolean isLogEnabled() {
        return this.logEnabled;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public boolean isUseSsl() {
        return this.useSsl;
    }

    public String getCollectorHost() {
        return this.collectorHost;
    }

    public int getCollectorPort() {
        return this.collectorPort;
    }

    public long getDataReportPeriod() {
        return this.dataReportPeriod;
    }

    public boolean isNativeControlDRP() {
        return this.nativeControlDRP;
    }

    public long getDataReportLimit() {
        return this.dataReportLimit;
    }

    public boolean isEnableMobileNetworkReport() {
        return this.enableMobileNetworkReport;
    }

    public boolean isUseLogPersist() {
        return this.useLogPersist;
    }

    public boolean isReportCrashes() {
        return this.reportCrashes;
    }

    public boolean isUsePersistentUUID() {
        return this.usePersistentUUID;
    }

    public long getResponseBodyLimit() {
        return this.responseBodyLimit;
    }

    public double getSampleRate() {
        return this.sampleRate;
    }

    public String getCuid() {
        return this.cuid;
    }

    public String getChannel() {
        return this.channel;
    }

    public int getHarvestableCacheLimit() {
        return this.harvestableCacheLimit;
    }

    public long getSamplerFreq() {
        return this.samplerFreq;
    }

    public boolean isEnableTransmission() {
        return this.enableTransmission;
    }

    public boolean isCollectAgentHealth() {
        return this.collectAgentHealth;
    }

    public boolean isEnableStatsEngine() {
        return this.enableStatsEngine;
    }

    public String toString() {
        return "AgentConfig{\nAPIKey='" + this.APIKey + "'\n, logEnabled=" + this.logEnabled + "\n, logLevel=" + this.logLevel + "\n, useSsl=" + this.useSsl + "\n, collectorHost='" + this.collectorHost + "'\n, dataReportPeriod=" + this.dataReportPeriod + "\n, nativeControlDRP=" + this.nativeControlDRP + "\n, dataReportLimit=" + this.dataReportLimit + "\n, enableMobileNetworkReport=" + this.enableMobileNetworkReport + "\n, useLogPersist=" + this.useLogPersist + "\n, reportCrashes=" + this.reportCrashes + "\n, usePersistentUUID=" + this.usePersistentUUID + "\n, responseBodyLimit=" + this.responseBodyLimit + "\n, sampleRate=" + this.sampleRate + "\n, cuid='" + this.cuid + "'\n, channel='" + this.channel + "'\n, harvestableCacheLimit=" + this.harvestableCacheLimit + "\n, samplerFreq=" + this.samplerFreq + "\n, enableTransmission=" + this.enableTransmission + "\n, enableStatsEngine=" + this.enableStatsEngine + "\n}";
    }

    @Keep
    public static final class Builder {
        private String APIKey;
        private String channel;
        private boolean collectAgentHealth;
        private String collectorHost;
        private int collectorPort;
        private String cuid;
        private long dataReportLimit;
        private long dataReportPeriod;
        private boolean enableMobileNetworkReport;
        private boolean enableStatsEngine;
        private boolean enableTransmission;
        private int harvestableCacheLimit;
        private boolean logEnabled;
        private int logLevel;
        private boolean nativeControlDRP;
        private boolean reportCrashes;
        private long responseBodyLimit;
        private double sampleRate;
        private long samplerFreq;
        private boolean useLogPersist;
        private boolean usePersistentUUID;
        private boolean useSsl;

        public Builder() {
            this.APIKey = Constants.MAIN_VERSION_TAG;
            this.logEnabled = false;
            this.logLevel = 5;
            this.useSsl = true;
            this.collectorHost = AgentConfig.DEFAULT_COLLECTOR_HOST;
            this.collectorPort = AgentConfig.DEFAULT_COLLECTOR_PORT;
            this.dataReportPeriod = AgentConfig.DEFAULT_DATA_REPORT_PERIOD;
            this.nativeControlDRP = false;
            this.dataReportLimit = AgentConfig.DEFAULT_DATA_REPORT_LIMIT;
            this.enableMobileNetworkReport = true;
            this.useLogPersist = true;
            this.reportCrashes = true;
            this.usePersistentUUID = false;
            this.responseBodyLimit = ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLSX;
            this.sampleRate = AgentConfig.DEFAULT_SAMPLE_RATE;
            this.cuid = "null";
            this.channel = "null";
            this.harvestableCacheLimit = 1024;
            this.samplerFreq = AgentConfig.DEFAULT_SAMPLER_FREQ;
            this.enableTransmission = true;
            this.collectAgentHealth = true;
            this.enableStatsEngine = true;
        }

        public Builder(AgentConfig agentConfig) {
            this.APIKey = agentConfig.APIKey;
            this.logEnabled = agentConfig.logEnabled;
            this.logLevel = agentConfig.logLevel;
            this.useSsl = agentConfig.useSsl;
            this.collectorHost = agentConfig.collectorHost;
            this.collectorPort = agentConfig.collectorPort;
            this.dataReportPeriod = agentConfig.dataReportPeriod;
            this.nativeControlDRP = agentConfig.nativeControlDRP;
            this.dataReportLimit = agentConfig.dataReportLimit;
            this.enableMobileNetworkReport = agentConfig.enableMobileNetworkReport;
            this.useLogPersist = agentConfig.useLogPersist;
            this.reportCrashes = agentConfig.reportCrashes;
            this.usePersistentUUID = agentConfig.usePersistentUUID;
            this.responseBodyLimit = agentConfig.responseBodyLimit;
            this.sampleRate = agentConfig.sampleRate;
            this.cuid = agentConfig.cuid;
            this.channel = agentConfig.channel;
            this.harvestableCacheLimit = agentConfig.harvestableCacheLimit;
            this.samplerFreq = agentConfig.samplerFreq;
            this.enableTransmission = agentConfig.enableTransmission;
            this.enableStatsEngine = agentConfig.enableStatsEngine;
        }

        public Builder APIKey(String val) {
            this.APIKey = val;
            return this;
        }

        public Builder logEnabled(boolean val) {
            this.logEnabled = val;
            return this;
        }

        public Builder logLevel(int val) {
            this.logLevel = val;
            return this;
        }

        public Builder useSsl(boolean val) {
            this.useSsl = val;
            return this;
        }

        public Builder collectorHost(String val) {
            this.collectorHost = val;
            return this;
        }

        public Builder collectorPort(int val) {
            this.collectorPort = val;
            return this;
        }

        public Builder dataReportPeriod(long val) {
            this.dataReportPeriod = val;
            this.nativeControlDRP = true;
            return this;
        }

        public Builder dataReportLimit(long val) {
            this.dataReportLimit = val;
            return this;
        }

        public Builder enableMobileNetworkReport(boolean val) {
            this.enableMobileNetworkReport = val;
            return this;
        }

        public Builder useLogPersist(boolean val) {
            this.useLogPersist = val;
            return this;
        }

        public Builder reportCrashes(boolean val) {
            this.reportCrashes = val;
            return this;
        }

        public Builder usePersistentUUID(boolean val) {
            this.usePersistentUUID = val;
            return this;
        }

        public Builder responseBodyLimit(long val) {
            this.responseBodyLimit = val;
            return this;
        }

        public Builder sampleRate(double val) {
            this.sampleRate = val;
            return this;
        }

        public Builder cuid(String val) {
            this.cuid = val;
            return this;
        }

        public Builder channel(String val) {
            this.channel = val;
            return this;
        }

        public Builder harvestableCacheLimit(int val) {
            this.harvestableCacheLimit = val;
            return this;
        }

        public Builder samplerFreq(long val) {
            this.samplerFreq = val;
            return this;
        }

        public Builder collectAgentHealth(boolean val) {
            this.collectAgentHealth = val;
            return this;
        }

        public AgentConfig build() {
            return new AgentConfig(this);
        }
    }
}
