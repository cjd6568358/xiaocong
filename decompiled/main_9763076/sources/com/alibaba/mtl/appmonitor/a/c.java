package com.alibaba.mtl.appmonitor.a;

import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.Measure;
import com.alibaba.mtl.appmonitor.model.MeasureValue;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.appmonitor.model.Metric;
import com.alibaba.mtl.appmonitor.model.MetricRepo;
import com.alibaba.mtl.log.e.i;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: DurationEvent.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends d {
    private static final Long a = 300000L;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Metric f14a;
    private DimensionValueSet b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private MeasureValueSet f15b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Long f16b;
    private Map<String, MeasureValue> i;

    public boolean c() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        List<Measure> measures = this.f14a.getMeasureSet().getMeasures();
        if (measures != null) {
            int size = measures.size();
            for (int i = 0; i < size; i++) {
                Measure measure = measures.get(i);
                if (measure != null) {
                    double dDoubleValue = measure.getMax() != null ? measure.getMax().doubleValue() : a.longValue();
                    MeasureValue measureValue = this.i.get(measure.getName());
                    if (measureValue != null && !measureValue.isFinish() && jCurrentTimeMillis - measureValue.getValue() > dDoubleValue) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void a(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.i.isEmpty()) {
            this.f16b = Long.valueOf(jCurrentTimeMillis);
        }
        this.i.put(str, (MeasureValue) com.alibaba.mtl.appmonitor.c.a.a().a(MeasureValue.class, Double.valueOf(jCurrentTimeMillis), Double.valueOf(jCurrentTimeMillis - this.f16b.longValue())));
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public boolean m10a(String str) {
        MeasureValue measureValue = this.i.get(str);
        if (measureValue != null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            i.a("DurationEvent", "statEvent consumeTime. module:", this.o, " monitorPoint:", this.p, " measureName:", str, " time:", Double.valueOf(jCurrentTimeMillis - measureValue.getValue()));
            measureValue.setValue(jCurrentTimeMillis - measureValue.getValue());
            measureValue.setFinish(true);
            this.f15b.setValue(str, measureValue);
            if (this.f14a.getMeasureSet().valid(this.f15b)) {
                return true;
            }
        }
        return false;
    }

    public void a(DimensionValueSet dimensionValueSet) {
        if (this.b == null) {
            this.b = dimensionValueSet;
        } else {
            this.b.addValues(dimensionValueSet);
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public MeasureValueSet m9a() {
        return this.f15b;
    }

    public DimensionValueSet a() {
        return this.b;
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        super.clean();
        this.f14a = null;
        this.f16b = null;
        Iterator<MeasureValue> it = this.i.values().iterator();
        while (it.hasNext()) {
            com.alibaba.mtl.appmonitor.c.a.a().a(it.next());
        }
        this.i.clear();
        if (this.f15b != null) {
            com.alibaba.mtl.appmonitor.c.a.a().a(this.f15b);
            this.f15b = null;
        }
        if (this.b != null) {
            com.alibaba.mtl.appmonitor.c.a.a().a(this.b);
            this.b = null;
        }
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... params) {
        super.fill(params);
        if (this.i == null) {
            this.i = new HashMap();
        }
        this.f14a = MetricRepo.getRepo().getMetric(this.o, this.p);
        if (this.f14a.getDimensionSet() != null) {
            this.b = (DimensionValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(DimensionValueSet.class, new Object[0]);
            this.f14a.getDimensionSet().setConstantValue(this.b);
        }
        this.f15b = (MeasureValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(MeasureValueSet.class, new Object[0]);
    }
}
