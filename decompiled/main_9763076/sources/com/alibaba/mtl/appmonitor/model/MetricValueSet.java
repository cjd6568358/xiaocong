package com.alibaba.mtl.appmonitor.model;

import com.alibaba.mtl.appmonitor.a.d;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.c.a;
import com.alibaba.mtl.appmonitor.c.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MetricValueSet implements b {
    private Map<Metric, d> o = Collections.synchronizedMap(new HashMap());

    public List<d> getEvents() {
        return new ArrayList(this.o.values());
    }

    public d getEvent(Integer eventId, String module, String monitorPoint, String extraArg, Class<? extends d> type) {
        Metric metric;
        boolean z = true;
        if (eventId.intValue() == f.STAT.m12a()) {
            z = false;
            metric = MetricRepo.getRepo().getMetric(module, monitorPoint);
        } else {
            metric = (Metric) a.a().a(Metric.class, module, monitorPoint, extraArg);
        }
        d dVar = null;
        if (metric != null) {
            if (this.o.containsKey(metric)) {
                dVar = this.o.get(metric);
            } else {
                synchronized (MetricValueSet.class) {
                    dVar = (d) a.a().a(type, eventId, module, monitorPoint, extraArg);
                    this.o.put(metric, dVar);
                }
                z = false;
            }
            if (z) {
                a.a().a(metric);
            }
        }
        return dVar;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        Iterator<d> it = this.o.values().iterator();
        while (it.hasNext()) {
            a.a().a(it.next());
        }
        this.o.clear();
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... params) {
        if (this.o == null) {
            this.o = Collections.synchronizedMap(new HashMap());
        }
    }
}
