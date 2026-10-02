package com.baidu.uaq.agent.android.harvest.bean;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: DeviceInformation.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends com.baidu.uaq.agent.android.harvest.type.c {
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private String aH;
    private String aI;
    private String aJ;
    private String aK;
    private String aL;
    private String aM;
    private String aN;
    private String aO;
    private String aP;
    private boolean aQ = false;
    private String ah;
    private String al;
    private String cuid;

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONArray U() {
        JSONArray array = new JSONArray();
        try {
            C(this.aH);
            array.put(0, this.aH);
            C(this.aI);
            array.put(1, this.aI);
            C(this.aJ);
            C(this.aK);
            array.put(2, this.aJ + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.aK);
            C(this.aL);
            array.put(3, this.aL);
            C(this.aM);
            array.put(4, this.aM);
            C(this.aN);
            array.put(5, this.aN);
            array.put(6, Constants.MAIN_VERSION_TAG);
            array.put(7, Constants.MAIN_VERSION_TAG);
            array.put(8, this.aJ);
            JSONObject jo = new JSONObject();
            jo.put("size", this.aO);
            jo.put("CUID", this.cuid);
            array.put(9, jo);
        } catch (JSONException e) {
            LOG.a("Caught error while DeviceInformation asJSONArray: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        return array;
    }

    public void g(String osName) {
        this.aH = osName;
    }

    public void h(String osVersion) {
        this.aI = osVersion;
    }

    public void i(String manufacturer) {
        this.aJ = manufacturer;
    }

    public void j(String model) {
        this.aK = model;
    }

    public void k(String agentName) {
        this.aL = agentName;
    }

    public void l(String agentVersion) {
        this.aM = agentVersion;
    }

    public void m(String deviceId) {
        this.aN = deviceId;
    }

    public String aj() {
        return this.aI;
    }

    public String getModel() {
        return this.aK;
    }

    public String getDeviceId() {
        return this.aN;
    }

    public String getManufacturer() {
        return this.aJ;
    }

    public void n(String osBuild) {
        this.aP = osBuild;
    }

    public String am() {
        return this.ah;
    }

    public String an() {
        return this.al;
    }

    public String ao() {
        return this.aO;
    }

    public String ap() {
        return this.aP;
    }

    public void o(String architecture) {
        this.ah = architecture;
    }

    public void p(String runTime) {
        this.al = runTime;
    }

    public void q(String size) {
        this.aO = size;
    }

    public void r(String cuid) {
        this.cuid = cuid;
    }

    public String getCuid() {
        return this.cuid;
    }

    public boolean aq() {
        return this.aQ;
    }

    public void a(boolean encrypted) {
        this.aQ = encrypted;
    }
}
