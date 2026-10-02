package com.baidu.uaq.agent.android.crashes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: DeviceInfo.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e extends com.baidu.uaq.agent.android.harvest.type.d {
    private long ac;
    private String ad;
    private long[] ae;
    private String af;
    private String ag;
    private String ah;
    private String ai;
    private String aj;
    private String ak;
    private String al;
    private String deviceName;
    private int orientation;

    public e() {
    }

    public e(com.baidu.uaq.agent.android.harvest.bean.c devInfo, com.baidu.uaq.agent.android.harvest.bean.d envInfo) {
        this.ac = envInfo.ar();
        this.orientation = envInfo.getOrientation();
        this.ad = envInfo.as();
        this.ae = envInfo.at();
        this.af = devInfo.aj();
        this.deviceName = devInfo.getManufacturer();
        this.ag = devInfo.ap();
        this.ah = devInfo.am();
        this.ai = devInfo.getModel();
        this.aj = devInfo.ao();
        this.ak = devInfo.getDeviceId();
        this.al = devInfo.an();
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONObject z() {
        JSONObject data = new JSONObject();
        try {
            data.put("memoryUsage", Long.valueOf(this.ac));
            data.put("orientation", Integer.valueOf(this.orientation));
            data.put("networkStatus", this.ad);
            data.put("diskAvailable", M());
            data.put("OSVersion", this.af);
            data.put("deviceName", this.deviceName);
            data.put("OSBuild", this.ag);
            data.put("architecture", this.ah);
            data.put("runTime", this.al);
            data.put("modelNumber", this.ai);
            data.put("screenResolution", this.aj);
            data.put("deviceUuid", this.ak);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static e b(JSONObject jsonObject) {
        e info = new e();
        try {
            info.ac = jsonObject.getLong("memoryUsage");
            info.orientation = jsonObject.getInt("orientation");
            info.ad = jsonObject.getString("networkStatus");
            info.ae = a(jsonObject.getJSONArray("diskAvailable"));
            info.af = jsonObject.getString("OSVersion");
            info.deviceName = jsonObject.getString("deviceName");
            info.ag = jsonObject.getString("OSBuild");
            info.ah = jsonObject.getString("architecture");
            info.al = jsonObject.getString("runTime");
            info.ai = jsonObject.getString("modelNumber");
            info.aj = jsonObject.getString("screenResolution");
            info.ak = jsonObject.getString("deviceUuid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    private static long[] a(JSONArray jsonArray) {
        long[] array = new long[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                array[i] = jsonArray.getLong(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    private JSONArray M() {
        JSONArray data = new JSONArray();
        long[] arr = this.ae;
        for (long value : arr) {
            data.put(Long.valueOf(value));
        }
        return data;
    }
}
