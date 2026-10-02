package com.baidu.uaq.agent.android.customtransmission;

import android.support.annotation.Keep;
import com.baidu.uaq.agent.android.UAQ;
import java.util.HashMap;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Keep
public class APMUploadConfigure {
    private static final UAQ AGENT = UAQ.getInstance();
    public static final String APMUPLOADNAME = "APMPerformanceConfigurationName";
    private static final long MAXBYTESPERIOD = 86400000;
    private static final long MAXBYTESWIFI = 0;
    public static final int MAXUPLOADRETRYCOUNT = 3;
    private static final int MSEC = 1000;
    private APMUploadHandler apmUploadHandler;
    private boolean enableRetransmission;
    private HashMap<String, String> headerMap = new HashMap<>();
    private long interval4g;
    private long intervalWifi;
    private long maxBytes4g;
    private long maxBytesPeriod4g;
    private long maxBytesPeriodWifi;
    private long maxBytesWifi;
    private MergeBlockCallBack mergeBlockCallBack;
    private String uploadName;
    private String url;

    public APMUploadConfigure(String uploadName, String url, MergeBlockCallBack mergeBlockCallBack) {
        this.headerMap.put(HTTP.CONTENT_TYPE, "application/json");
        this.headerMap.put(HTTP.CONTENT_ENCODING, "deflate");
        this.uploadName = uploadName;
        this.url = url;
        this.mergeBlockCallBack = mergeBlockCallBack;
        this.enableRetransmission = false;
        this.interval4g = AGENT.getConfig().getDataReportPeriod();
        this.intervalWifi = AGENT.getConfig().getDataReportPeriod();
        this.maxBytes4g = AGENT.getConfig().getDataReportLimit();
        this.maxBytesWifi = MAXBYTESWIFI;
        this.maxBytesPeriod4g = MAXBYTESPERIOD;
        this.maxBytesPeriodWifi = MAXBYTESPERIOD;
        this.apmUploadHandler = new APMUploadHandler(this.uploadName);
    }

    public void setInterval4g(long interval4g) {
        this.interval4g = 1000 * interval4g;
    }

    public void setIntervalWifi(long intervalWifi) {
        this.intervalWifi = 1000 * intervalWifi;
    }

    public void setMaxbytes4g(long maxBytes4g, long maxBytesPeriod4g) {
        this.maxBytes4g = maxBytes4g;
        this.maxBytesPeriod4g = 1000 * maxBytesPeriod4g;
    }

    public void setMaxbyteswifi(long maxBytesWifi, long maxBytesPeriodWifi) {
        this.maxBytesWifi = maxBytesWifi;
        this.maxBytesPeriodWifi = 1000 * maxBytesPeriodWifi;
    }

    public void enableRetransmission(boolean enableRetransmission) {
        this.enableRetransmission = enableRetransmission;
    }

    public void setHeaderMap(HashMap<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public String getUploadName() {
        return this.uploadName;
    }

    public String getUrl() {
        return this.url;
    }

    public long getInterval4g() {
        return this.interval4g;
    }

    public long getIntervalWifi() {
        return this.intervalWifi;
    }

    public long getMaxBytes4g() {
        return this.maxBytes4g;
    }

    public long getMaxBytesWifi() {
        return this.maxBytesWifi;
    }

    public long getMaxBytesPeriod4g() {
        return this.maxBytesPeriod4g;
    }

    public long getMaxBytesPeriodWifi() {
        return this.maxBytesPeriodWifi;
    }

    public boolean isEnableRetransmission() {
        return this.enableRetransmission;
    }

    public MergeBlockCallBack getMergeBlockCallBack() {
        return this.mergeBlockCallBack;
    }

    public HashMap<String, String> getHeaderMap() {
        return this.headerMap;
    }

    public APMUploadHandler getApmUploadHandler() {
        return this.apmUploadHandler;
    }
}
