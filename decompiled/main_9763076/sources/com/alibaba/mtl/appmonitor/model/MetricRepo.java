package com.alibaba.mtl.appmonitor.model;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MetricRepo {
    private static MetricRepo a;
    public List<Metric> metrics;

    public static MetricRepo getRepo() {
        if (a == null) {
            a = new MetricRepo(3);
        }
        return a;
    }

    private MetricRepo(int capacity) {
        this.metrics = new ArrayList(capacity);
    }

    public void add(Metric metric) {
        if (!this.metrics.contains(metric)) {
            this.metrics.add(metric);
        }
    }

    public boolean remove(Metric metric) {
        if (this.metrics.contains(metric)) {
            return this.metrics.remove(metric);
        }
        return true;
    }

    public Metric getMetric(String module, String monitorPoint) {
        if (module == null || monitorPoint == null || this.metrics == null) {
            return null;
        }
        int size = this.metrics.size();
        for (int i = 0; i < size; i++) {
            Metric metric = this.metrics.get(i);
            if (metric != null && metric.getModule().equals(module) && metric.getMonitorPoint().equals(monitorPoint)) {
                return metric;
            }
        }
        return null;
    }
}
