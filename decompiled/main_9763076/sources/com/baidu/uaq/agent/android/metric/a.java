package com.baidu.uaq.agent.android.metric;

import com.baidu.uaq.agent.android.harvest.type.d;
import com.baidu.uaq.agent.android.logging.b;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Metric.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends d {
    private static final com.baidu.uaq.agent.android.logging.a LOG = b.bg();
    private String bU;
    private Double bV;
    private Double bW;
    private Double bX;
    private Double bY;
    private Double bZ;
    private long ca;
    private String name;

    public a(String name) {
        this(name, null);
    }

    public a(String name, String scope) {
        this.name = name;
        this.bU = scope;
        this.ca = 0L;
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONObject z() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("count", this.ca);
            if (this.bX != null) {
                jsonObject.put("total", this.bX);
            }
            if (this.bV != null) {
                jsonObject.put(MessageKey.MSG_ACCEPT_TIME_MIN, this.bV);
            }
            if (this.bW != null) {
                jsonObject.put("max", this.bW);
            }
            if (this.bY != null) {
                jsonObject.put("sum_of_squares", this.bY);
            }
            if (this.bZ != null) {
                jsonObject.put("exclusive", this.bZ);
            }
        } catch (JSONException e) {
            LOG.a("Caught error while Metric asJSONObject: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        return jsonObject;
    }

    public void a(double value) {
        this.ca++;
        if (this.bX == null) {
            this.bX = Double.valueOf(value);
            this.bY = Double.valueOf(value * value);
        } else {
            this.bX = Double.valueOf(this.bX.doubleValue() + value);
            this.bY = Double.valueOf(this.bY.doubleValue() + (value * value));
        }
        a(Double.valueOf(value));
        c(Double.valueOf(value));
    }

    private void a(Double value) {
        if (value != null) {
            if (this.bV == null) {
                this.bV = value;
            } else if (value.doubleValue() < this.bV.doubleValue()) {
                this.bV = value;
            }
        }
    }

    private void c(Double value) {
        if (value != null) {
            if (this.bW == null) {
                this.bW = value;
            } else if (value.doubleValue() > this.bW.doubleValue()) {
                this.bW = value;
            }
        }
    }

    public void j(long value) {
        this.ca += value;
    }

    public void increment() {
        j(1L);
    }

    public String getName() {
        return this.name;
    }

    public String bk() {
        return this.bU != null ? this.bU : Constants.MAIN_VERSION_TAG;
    }

    public String toString() {
        return "Metric{name='" + this.name + "', scope='" + this.bU + "', min=" + this.bV + ", max=" + this.bW + ", total=" + this.bX + ", sumOfSquares=" + this.bY + ", exclusive=" + this.bZ + ", count=" + this.ca + '}';
    }
}
