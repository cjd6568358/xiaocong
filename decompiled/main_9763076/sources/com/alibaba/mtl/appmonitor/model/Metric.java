package com.alibaba.mtl.appmonitor.model;

import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.c.b;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Metric implements b {
    private DimensionSet b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private MeasureSet f23b;
    private boolean g;
    private String o;
    private String p;
    private String r;
    private String s;
    private String z;

    @Deprecated
    public Metric() {
        this.z = null;
    }

    public Metric(String module, String monitorPoint, MeasureSet measureSet, DimensionSet dimensionSet, boolean isCommitDetail) {
        this.z = null;
        this.o = module;
        this.p = monitorPoint;
        this.b = dimensionSet;
        this.f23b = measureSet;
        this.s = null;
        this.g = isCommitDetail;
    }

    public synchronized String getTransactionId() {
        if (this.r == null) {
            this.r = UUID.randomUUID().toString() + "$" + this.o + "$" + this.p;
        }
        return this.r;
    }

    public void resetTransactionId() {
        this.r = null;
    }

    public boolean valid(DimensionValueSet dimensionValues, MeasureValueSet measureValues) {
        boolean z = true;
        boolean zValid = this.b != null ? this.b.valid(dimensionValues) : true;
        Metric metric = MetricRepo.getRepo().getMetric("config_prefix" + this.o, "config_prefix" + this.p);
        if (metric != null && metric.getMeasureSet() != null && measureValues != null && measureValues.getMap() != null && this.f23b != null) {
            List<Measure> measures = metric.getMeasureSet().getMeasures();
            for (String str : measureValues.getMap().keySet()) {
                Measure measureA = a(str, measures);
                if (measureA == null) {
                    measureA = a(str, this.f23b.getMeasures());
                }
                if (measureA == null || !measureA.valid(measureValues.getValue(str))) {
                    return false;
                }
            }
            return zValid;
        }
        if (this.f23b == null) {
            z = zValid;
        } else if (!zValid || !this.f23b.valid(measureValues)) {
            z = false;
        }
        return z;
    }

    private Measure a(String str, List<Measure> list) {
        if (list != null) {
            for (Measure measure : list) {
                if (TextUtils.equals(str, measure.name)) {
                    return measure;
                }
            }
        }
        return null;
    }

    public String getModule() {
        return this.o;
    }

    public String getMonitorPoint() {
        return this.p;
    }

    public DimensionSet getDimensionSet() {
        return this.b;
    }

    public MeasureSet getMeasureSet() {
        return this.f23b;
    }

    public synchronized boolean isCommitDetail() {
        boolean z;
        if ("1".equalsIgnoreCase(this.z)) {
            z = true;
        } else if (PushConstants.PUSH_TYPE_NOTIFY.equalsIgnoreCase(this.z)) {
            z = false;
        } else {
            z = this.g;
        }
        return z;
    }

    public int hashCode() {
        return (((this.o == null ? 0 : this.o.hashCode()) + (((this.s == null ? 0 : this.s.hashCode()) + 31) * 31)) * 31) + (this.p != null ? this.p.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Metric metric = (Metric) obj;
            if (this.s == null) {
                if (metric.s != null) {
                    return false;
                }
            } else if (!this.s.equals(metric.s)) {
                return false;
            }
            if (this.o == null) {
                if (metric.o != null) {
                    return false;
                }
            } else if (!this.o.equals(metric.o)) {
                return false;
            }
            if (this.p == null) {
                return metric.p == null;
            }
            return this.p.equals(metric.p);
        }
        return false;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        this.o = null;
        this.p = null;
        this.s = null;
        this.g = false;
        this.b = null;
        this.f23b = null;
        this.r = null;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... params) {
        this.o = (String) params[0];
        this.p = (String) params[1];
        if (params.length > 2) {
            this.s = (String) params[2];
        }
    }

    public synchronized void setCommitDetailFromConfig(String isCommitDetailFromConfig) {
        this.z = isCommitDetailFromConfig;
    }
}
