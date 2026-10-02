package com.baidu.uaq.agent.android.harvest.multiharvest;

import android.content.Context;
import android.content.SharedPreferences;
import com.baidu.uaq.agent.android.AgentConfig;
import com.baidu.uaq.agent.android.UAQ;
import com.baidu.uaq.agent.android.customtransmission.APMUploadConfigure;
import com.baidu.uaq.agent.android.g;
import com.baidu.uaq.agent.android.harvest.bean.f;
import com.baidu.uaq.agent.android.util.e;
import com.baidu.uaq.agent.android.util.h;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: MultiHarvester.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private static com.baidu.uaq.agent.android.harvest.a bz;
    private b bA;
    private final e bB;
    private long bC;
    private long bD;
    private SharedPreferences bE;
    private ArrayList<String> bF = new ArrayList<>();
    private a bG;
    private APMUploadConfigure bu;
    private Context j;
    private SharedPreferences.Editor y;
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static final UAQ AGENT = UAQ.getInstance();

    /* JADX INFO: compiled from: MultiHarvester.java */
    private enum a {
        CONNECTEDWIFI,
        CONNECTEDNOTWIFI,
        DISCONNECTED
    }

    public d(Context context, APMUploadConfigure apmUploadConfigure) {
        this.j = context;
        this.bu = apmUploadConfigure;
        this.bB = new e(context, apmUploadConfigure.getUploadName());
        this.bE = context.getSharedPreferences("com.baidu.uaq.android.agent.v2_customer_", 0);
    }

    public static com.baidu.uaq.agent.android.harvest.a aQ() {
        return bz;
    }

    private void aR() {
        if (this.bu.getUploadName().equals(APMUploadConfigure.APMUPLOADNAME) && bz == null) {
            bz = new com.baidu.uaq.agent.android.harvest.a();
        }
        if (this.bA == null) {
            this.bA = new b();
        }
    }

    public static void c(com.baidu.uaq.agent.android.harvest.health.b exception) {
        bz.W().a(exception);
    }

    public static void d(f transmission) {
        bz.X().b(transmission);
    }

    public void d(APMUploadConfigure apmUploadConfigure) throws Throwable {
        this.bu = apmUploadConfigure;
        aR();
        this.bG = aU();
        if (this.bG != a.DISCONNECTED) {
            this.bC = bc();
            this.bD = bb();
            bd();
            LOG.E("harvester exec for :" + apmUploadConfigure.getUploadName() + ", uploadStartTime:" + this.bC + ", intervalUploadedBytes:" + this.bD);
            long maxBytes = aW();
            if (apmUploadConfigure.getUploadName().equals(APMUploadConfigure.APMUPLOADNAME)) {
                a(Long.valueOf(maxBytes));
            } else {
                b(Long.valueOf(maxBytes));
            }
            if (maxBytes == 0 || this.bD <= maxBytes) {
                aT();
            }
        } else if (apmUploadConfigure.getUploadName().equals(APMUploadConfigure.APMUPLOADNAME)) {
            String data = a(bz).bf();
            if (data != null) {
                LOG.E("harvester exec for :" + apmUploadConfigure.getUploadName() + ", network is not connected, choose to localize data");
                c(data, apmUploadConfigure.getUploadName());
            }
        } else {
            ArrayList<String> blocks = com.baidu.uaq.agent.android.customtransmission.b.a(apmUploadConfigure.getUploadName(), Boolean.valueOf(apmUploadConfigure.isEnableRetransmission()));
            if (blocks != null) {
                this.bF.addAll(blocks);
                LOG.E("harvester exec for :" + apmUploadConfigure.getUploadName() + ", network is not connected, choose to localize data");
                z(apmUploadConfigure.getUploadName());
                com.baidu.uaq.agent.android.customtransmission.b.b(apmUploadConfigure.getUploadName(), Boolean.valueOf(apmUploadConfigure.isEnableRetransmission()));
            }
        }
        aS();
    }

    private void aS() {
        if (this.bu.getUploadName().equals(APMUploadConfigure.APMUPLOADNAME)) {
            bz.reset();
        } else {
            this.bF.clear();
        }
    }

    private void a(Long maxBytes) {
        String data = a(bz).bf();
        if (!data.isEmpty()) {
            LOG.E("config name:" + this.bu.getUploadName() + ", upload limit:" + maxBytes + ", curr uploads:" + this.bD + ", length:" + data.length());
            if (maxBytes.longValue() == 0 || this.bD + ((long) data.length()) <= maxBytes.longValue()) {
                A(data);
                this.bD += (long) data.length();
                aY();
            } else if (this.bG != a.CONNECTEDWIFI) {
                c(data, this.bu.getUploadName());
            }
        }
    }

    private void b(Long maxBytes) {
        ArrayList<String> blocks = com.baidu.uaq.agent.android.customtransmission.b.a(this.bu.getUploadName(), Boolean.valueOf(this.bu.isEnableRetransmission()));
        if (!this.bF.isEmpty()) {
            LOG.error("blockArray is not empty!");
        }
        if (blocks != null && !blocks.isEmpty()) {
            this.bF.addAll(blocks);
            String data = this.bu.getMergeBlockCallBack().executeMerge(this.bF);
            LOG.E("config name:" + this.bu.getUploadName() + ", upload limit:" + maxBytes + ", curr uploads:" + this.bD + ", length:" + data.length());
            if (maxBytes.longValue() == 0 || this.bD + ((long) data.length()) <= maxBytes.longValue()) {
                c(data, this.bu);
                this.bD += (long) data.length();
                aY();
            } else if (this.bG != a.CONNECTEDWIFI) {
                z(this.bu.getUploadName());
                com.baidu.uaq.agent.android.customtransmission.b.b(this.bu.getUploadName(), Boolean.valueOf(this.bu.isEnableRetransmission()));
            } else {
                com.baidu.uaq.agent.android.customtransmission.b.b(this.bu.getUploadName(), Boolean.valueOf(this.bu.isEnableRetransmission()));
            }
        }
    }

    private void aT() throws Throwable {
        if (this.bG == a.CONNECTEDWIFI) {
            ArrayList<String> fileList = this.bB.bx();
            int fileLen = fileList.size();
            if (fileLen > 0) {
                if (this.bu.getUploadName().equals(APMUploadConfigure.APMUPLOADNAME)) {
                    a(fileList, this.bB);
                } else {
                    b(fileList, this.bB);
                }
            }
        }
    }

    private void a(ArrayList<String> fileList, e logUtil) throws Throwable {
        long maxBytes = aW();
        int fileLen = fileList.size();
        LOG.E("handle localized data for: " + this.bu.getUploadName() + ", Local fileLen: " + fileLen);
        for (int i = 0; i < fileLen; i++) {
            String localFile = fileList.get((fileLen - i) - 1);
            String content = logUtil.S(localFile);
            if (content != null && !content.isEmpty()) {
                LOG.E("handle localized file :" + localFile);
                if (maxBytes != 0 && this.bD + ((long) content.length()) > maxBytes) {
                    LOG.E("upload data will exceeds upload limit");
                    return;
                }
                com.baidu.uaq.agent.android.harvest.b response = this.bA.v(B(content.substring(0, content.length() - 1)));
                if (response != null && response.Y()) {
                    logUtil.R(localFile);
                    this.bD += (long) content.length();
                    aY();
                    LOG.E("upload success, delete " + localFile + "; curr uploads:" + this.bD + " length:" + content.length());
                }
                if (com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().aD() < 1) {
                    LOG.error("Agent has shutdown when handleLocalizedFile4APM");
                } else if (!a(response)) {
                    LOG.E("upload localized data failed");
                }
            }
        }
    }

    private void b(ArrayList<String> fileList, e logUtil) throws Throwable {
        long maxBytes = aW();
        int fileLen = fileList.size();
        LOG.E("handle localized data for " + this.bu.getUploadName() + ", Local fileLen: " + fileLen);
        for (int i = 0; i < fileLen; i++) {
            String localFile = fileList.get((fileLen - i) - 1);
            ArrayList<String> stringList = logUtil.T(localFile);
            if (stringList != null && stringList.size() != 0) {
                LOG.E("handle localized file :" + localFile);
                String content = this.bu.getMergeBlockCallBack().executeMerge(stringList);
                if (maxBytes != 0 && this.bD + ((long) content.length()) > maxBytes) {
                    LOG.E("upload data will exceeds upload limit");
                    return;
                }
                com.baidu.uaq.agent.android.harvest.b response = this.bA.a(content.substring(0, content.length()), this.bu);
                if (response != null && response.Z()) {
                    logUtil.R(localFile);
                    this.bD += (long) content.length();
                    aY();
                    LOG.E("upload success, delete " + localFile + "; curr uploads:" + this.bD + " length:" + content.length());
                } else {
                    LOG.E("upload localized data for customer failed!");
                }
            }
        }
    }

    private void c(String data, String uploadName) {
        this.bB.e(uploadName, data);
        LOG.E("localizeData4APM, localized file size: " + this.bB.bx().size());
    }

    private void z(String uploadName) {
        for (String block : this.bF) {
            this.bB.e(uploadName, block);
            LOG.E("Log Persist, fileList: " + this.bB.bx().size());
        }
    }

    private a aU() {
        if (h.k(this.j)) {
            return a.CONNECTEDWIFI;
        }
        if (h.j(this.j)) {
            return a.CONNECTEDNOTWIFI;
        }
        return a.DISCONNECTED;
    }

    private void c(String data, APMUploadConfigure apmUploadConfigure) {
        com.baidu.uaq.agent.android.harvest.b response = this.bA.a(data, apmUploadConfigure);
        if (response != null && response.Z()) {
            LOG.E("upload success");
            com.baidu.uaq.agent.android.customtransmission.b.b(apmUploadConfigure.getUploadName(), Boolean.valueOf(apmUploadConfigure.isEnableRetransmission()));
        } else {
            LOG.E("upload customer data failed!");
        }
    }

    private void A(String data) {
        com.baidu.uaq.agent.android.harvest.b response = this.bA.v(B(data));
        if (com.baidu.uaq.agent.android.harvest.multiharvest.a.aB().aD() < 1) {
            LOG.error("Agent has shutdown during startUpload");
        } else if (!a(response)) {
            LOG.E("upload APM data failed!");
        }
    }

    private com.baidu.uaq.agent.android.harvest.a a(com.baidu.uaq.agent.android.harvest.a data) {
        if (!data.e().aq()) {
            try {
                String osVersion = com.baidu.uaq.agent.android.util.d.P(data.e().aj());
                String model = com.baidu.uaq.agent.android.util.d.P(data.e().getModel());
                String deviceId = com.baidu.uaq.agent.android.util.d.P(data.e().getDeviceId());
                String manufacturer = com.baidu.uaq.agent.android.util.d.P(data.e().getManufacturer());
                String cuid = com.baidu.uaq.agent.android.util.d.P(data.e().getCuid());
                data.e().h(osVersion);
                data.e().j(model);
                data.e().m(deviceId);
                data.e().i(manufacturer);
                data.e().r(cuid);
                data.e().a(true);
                return data;
            } catch (Exception e) {
                LOG.a("Caught error while data2AES: ", e);
                com.baidu.uaq.agent.android.harvest.health.a.a(e);
                return data;
            }
        }
        return data;
    }

    private boolean a(com.baidu.uaq.agent.android.harvest.b response) {
        if (response == null) {
            return false;
        }
        LOG.E("Harvest response status code: " + response.getStatusCode());
        if (response.isError()) {
            LOG.error("Harvest response error body: " + response.aa());
            bz.reset();
            return false;
        }
        LOG.E("Harvest response body: " + response.aa());
        b(response);
        return true;
    }

    private static void b(com.baidu.uaq.agent.android.harvest.b response) {
        com.baidu.uaq.agent.android.stats.a.br().c("Supportability/AgentHealth/Collector/HarvestTime", response.ab());
        LOG.E("HarvestTime = " + response.ab() + "ms");
        String responseBody = response.aa();
        if (responseBody == null || responseBody.isEmpty() || Constants.MAIN_VERSION_TAG.equals(responseBody)) {
            LOG.E("responseBody is Empty");
            return;
        }
        try {
            JSONObject responseData = new JSONObject(responseBody);
            if (!responseData.optString("msg").isEmpty()) {
                LOG.error("Err msg from server: " + responseData.getString("msg"));
                return;
            }
            if (responseData.getBoolean("disableCollect")) {
                if (!AGENT.isDisableCollect()) {
                    LOG.info("disableCollect turn to true");
                    AGENT.setSavedConfig(AGENT.getConfig().newBuilder().build());
                    AGENT.disableCollect();
                    g.stop();
                    AGENT.setDisableCollect(true);
                    return;
                }
                return;
            }
            if (!responseData.getBoolean("disableCollect") && AGENT.isDisableCollect()) {
                LOG.info("disableCollect turn to false");
                AGENT.enableCollect(AGENT.getSavedConfig());
                g.start();
                AGENT.setDisableCollect(false);
            }
            long accountId = responseData.getLong("accountId");
            long agentId = responseData.getLong("agentId");
            com.baidu.uaq.agent.android.harvest.bean.b dataToken = new com.baidu.uaq.agent.android.harvest.bean.b(accountId, agentId);
            if (!dataToken.equals(bz.r())) {
                bz.a(dataToken);
            }
            if (responseData.length() > 4) {
                e(responseData);
            }
            AGENT.setNeedBasicInfo(responseData.getBoolean("needBasicInfo"));
        } catch (JSONException e) {
            LOG.a("Caught error while parse responseBody: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
    }

    private String B(String data) {
        return "{\"version\":1,\"value\":[" + data + "]}";
    }

    private static void e(JSONObject responseData) {
        AgentConfig.Builder builder = AGENT.getConfig().newBuilder();
        boolean needUpdate = false;
        try {
            if (!AGENT.getConfig().isNativeControlDRP() && responseData.has("dataReportPeriod") && AGENT.getConfig().getDataReportPeriod() != responseData.getLong("dataReportPeriod")) {
                builder.dataReportPeriod(responseData.getLong("dataReportPeriod"));
                LOG.E("Update dataReportPeriod: " + responseData.getLong("dataReportPeriod"));
                needUpdate = true;
            }
            if (responseData.has("dataReportLimit") && AGENT.getConfig().getDataReportLimit() != responseData.getLong("dataReportLimit")) {
                builder.dataReportLimit(responseData.getLong("dataReportLimit"));
                LOG.E("Update dataReportLimit: " + responseData.getLong("dataReportLimit"));
                needUpdate = true;
            }
            if (responseData.has("responseBodyLimit") && AGENT.getConfig().getResponseBodyLimit() != responseData.getLong("responseBodyLimit")) {
                builder.responseBodyLimit(responseData.getLong("responseBodyLimit"));
                LOG.E("Update responseBodyLimit: " + responseData.getLong("responseBodyLimit"));
                needUpdate = true;
            }
            if (responseData.has("sampleRate") && AGENT.getConfig().getSampleRate() != responseData.getDouble("sampleRate")) {
                builder.sampleRate(responseData.getDouble("sampleRate"));
                LOG.E("Update sampleRate: " + responseData.getDouble("sampleRate"));
                needUpdate = true;
            }
            if (responseData.has("harvestableCacheLimit") && AGENT.getConfig().getHarvestableCacheLimit() != responseData.getInt("harvestableCacheLimit")) {
                builder.harvestableCacheLimit(responseData.getInt("harvestableCacheLimit"));
                LOG.E("Update harvestableCacheLimit: " + responseData.getInt("harvestableCacheLimit"));
                needUpdate = true;
            }
            if (responseData.has("samplerFreq") && AGENT.getConfig().getSamplerFreq() != responseData.getLong("samplerFreq")) {
                builder.samplerFreq(responseData.getLong("samplerFreq"));
                LOG.E("Update samplerFreq: " + responseData.getLong("samplerFreq"));
                needUpdate = true;
            }
            if (needUpdate) {
                AGENT.reconfig(builder.build());
            }
        } catch (JSONException e) {
            LOG.a("Caught error while updateAgentConfig: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
    }

    private long aV() {
        switch (this.bG) {
            case CONNECTEDWIFI:
                return this.bu.getMaxBytesPeriodWifi();
            case CONNECTEDNOTWIFI:
                return this.bu.getMaxBytesPeriod4g();
            default:
                return 0L;
        }
    }

    private long aW() {
        switch (this.bG) {
            case CONNECTEDWIFI:
                return this.bu.getMaxBytesWifi();
            case CONNECTEDNOTWIFI:
                return this.bu.getMaxBytes4g();
            default:
                return 0L;
        }
    }

    private String aX() {
        switch (this.bG) {
            case CONNECTEDWIFI:
                return this.bu.getUploadName() + "_dataReportLimitWIFI";
            case CONNECTEDNOTWIFI:
                return this.bu.getUploadName() + "_dataReportLimitNOTWIFI";
            default:
                return null;
        }
    }

    private void aY() {
        this.y = this.bE.edit();
        String bytesKey = aX();
        if (bytesKey != null) {
            LOG.E("saveMaxBytesState uploaded bytes:" + this.bD + " key:" + bytesKey + " uploadStartTime:" + this.bC);
            this.y.putLong(bytesKey, this.bD);
            this.y.apply();
        }
    }

    private void aZ() {
        this.y = this.bE.edit();
        String bytesKey = aX();
        if (bytesKey != null) {
            String uploadStartKey = ba();
            LOG.E("saveIntervalState uploaded bytes:" + this.bD + " key:" + bytesKey + " uploadStartTime:" + this.bC + " dateKey:" + uploadStartKey);
            this.y.putLong(bytesKey, this.bD);
            this.y.putLong(uploadStartKey, this.bC);
            this.y.apply();
        }
    }

    private String ba() {
        return this.bu.getUploadName() + "apmUploadStartDate";
    }

    private long bb() {
        if (this.j != null) {
            SharedPreferences settings = this.j.getSharedPreferences("com.baidu.uaq.android.agent.v2_customer_", 0);
            String key = aX();
            if (settings != null && key != null) {
                long j = settings.getLong(key, 0L);
                this.bD = j;
                return j;
            }
            this.bD = 0L;
            return 0L;
        }
        LOG.error("getUploadedBytes failed, context is null");
        return -1L;
    }

    private long bc() {
        if (this.j != null) {
            SharedPreferences settings = this.j.getSharedPreferences("com.baidu.uaq.android.agent.v2_customer_", 0);
            String uploadStartKey = ba();
            if (settings != null && uploadStartKey != null) {
                long j = settings.getLong(uploadStartKey, 0L);
                this.bC = j;
                return j;
            }
            this.bC = 0L;
            return 0L;
        }
        LOG.error("getUploadStartTime failed, context is null");
        return -1L;
    }

    private void bd() {
        Boolean shouldUpdateDate = false;
        long currentTime = System.currentTimeMillis();
        if (this.bC != 0) {
            long interval = currentTime - this.bC;
            long maxBytesInterval = aV();
            if (interval >= maxBytesInterval) {
                shouldUpdateDate = true;
            }
        } else {
            shouldUpdateDate = true;
        }
        if (shouldUpdateDate.booleanValue()) {
            this.bC = currentTime;
            this.bD = 0L;
            aZ();
        }
    }
}
