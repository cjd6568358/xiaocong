package com.alibaba.mtl.appmonitor.a;

import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.appmonitor.model.Metric;
import com.alibaba.mtl.appmonitor.model.MetricRepo;
import com.alibaba.mtl.appmonitor.model.MetricValueSet;
import com.alibaba.mtl.appmonitor.model.UTDimensionValueSet;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;
import com.alibaba.mtl.log.model.LogField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: EventRepo.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    private static e a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private AtomicInteger f17a = new AtomicInteger(0);
    private AtomicInteger b = new AtomicInteger(0);
    private AtomicInteger c = new AtomicInteger(0);
    private Map<UTDimensionValueSet, MetricValueSet> k = new ConcurrentHashMap();
    private Map<String, c> j = new ConcurrentHashMap();

    public static synchronized e a() {
        if (a == null) {
            a = new e();
        }
        return a;
    }

    private e() {
    }

    private UTDimensionValueSet a(int i, Map<String, String> map) {
        UTDimensionValueSet uTDimensionValueSet = (UTDimensionValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(UTDimensionValueSet.class, new Object[0]);
        if (map != null) {
            uTDimensionValueSet.setMap(map);
        }
        uTDimensionValueSet.setValue(LogField.ACCESS.toString(), com.alibaba.mtl.log.a.b());
        uTDimensionValueSet.setValue(LogField.ACCESS_SUBTYPE.toString(), com.alibaba.mtl.log.a.c());
        uTDimensionValueSet.setValue(LogField.USERID.toString(), com.alibaba.mtl.log.a.d());
        uTDimensionValueSet.setValue(LogField.USERNICK.toString(), com.alibaba.mtl.log.a.e());
        uTDimensionValueSet.setValue(LogField.EVENTID.toString(), String.valueOf(i));
        return uTDimensionValueSet;
    }

    public void a(int i, String str, String str2, String str3, Map<String, String> map) {
        UTDimensionValueSet uTDimensionValueSetA = a(i, map);
        ((a) a(uTDimensionValueSetA, str, str2, str3, a.class)).f();
        if (com.alibaba.mtl.log.a.a.e()) {
            a aVar = (a) com.alibaba.mtl.appmonitor.c.a.a().a(a.class, Integer.valueOf(i), str, str2, str3);
            aVar.f();
            com.alibaba.mtl.appmonitor.f.c.a(uTDimensionValueSetA, aVar);
        }
        a(f.a(i), this.f17a);
    }

    public void a(int i, String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        UTDimensionValueSet uTDimensionValueSetA = a(i, map);
        a aVar = (a) a(uTDimensionValueSetA, str, str2, str3, a.class);
        aVar.g();
        aVar.a(str4, str5);
        if (com.alibaba.mtl.log.a.a.e()) {
            a aVar2 = (a) com.alibaba.mtl.appmonitor.c.a.a().a(a.class, Integer.valueOf(i), str, str2, str3);
            aVar2.g();
            aVar2.a(str4, str5);
            com.alibaba.mtl.appmonitor.f.c.a(uTDimensionValueSetA, aVar2);
        }
        a(f.a(i), this.f17a);
    }

    public void a(int i, String str, String str2, String str3, double d, Map<String, String> map) {
        UTDimensionValueSet uTDimensionValueSetA = a(i, map);
        ((b) a(uTDimensionValueSetA, str, str2, str3, b.class)).a(d);
        if (com.alibaba.mtl.log.a.a.e()) {
            b bVar = (b) com.alibaba.mtl.appmonitor.c.a.a().a(b.class, Integer.valueOf(i), str, str2, str3);
            bVar.a(d);
            com.alibaba.mtl.appmonitor.f.c.a(uTDimensionValueSetA, bVar);
        }
        a(f.a(i), this.b);
    }

    public void a(int i, String str, String str2, MeasureValueSet measureValueSet, DimensionValueSet dimensionValueSet, Map<String, String> map) {
        Metric metric = MetricRepo.getRepo().getMetric(str, str2);
        if (metric != null) {
            if (metric.getDimensionSet() != null) {
                metric.getDimensionSet().setConstantValue(dimensionValueSet);
            }
            if (metric.getMeasureSet() != null) {
                metric.getMeasureSet().setConstantValue(measureValueSet);
            }
            UTDimensionValueSet uTDimensionValueSetA = a(i, map);
            ((g) a(uTDimensionValueSetA, str, str2, (String) null, g.class)).a(dimensionValueSet, measureValueSet);
            if (com.alibaba.mtl.log.a.a.e()) {
                g gVar = (g) com.alibaba.mtl.appmonitor.c.a.a().a(g.class, Integer.valueOf(i), str, str2);
                gVar.a(dimensionValueSet, measureValueSet);
                com.alibaba.mtl.appmonitor.f.c.a(uTDimensionValueSetA, gVar);
            }
            a(f.a(i), this.c);
            return;
        }
        i.a("EventRepo", "metric is null");
    }

    public void a(Integer num, String str, String str2, String str3) {
        String strA = a(str, str2);
        if (strA != null) {
            a(strA, num, str, str2, str3);
        }
    }

    public void a(String str, Integer num, String str2, String str3, String str4) {
        c cVar;
        Metric metric = MetricRepo.getRepo().getMetric(str2, str3);
        if (metric != null && metric.getMeasureSet() != null && metric.getMeasureSet().getMeasure(str4) != null) {
            synchronized (c.class) {
                cVar = this.j.get(str);
                if (cVar == null) {
                    cVar = (c) com.alibaba.mtl.appmonitor.c.a.a().a(c.class, num, str2, str3);
                    this.j.put(str, cVar);
                }
            }
            cVar.a(str4);
        }
    }

    public void a(String str, String str2, String str3) {
        String strA = a(str, str2);
        if (strA != null) {
            a(strA, str3, true, (Map<String, String>) null);
        }
    }

    public void a(String str, String str2, boolean z, Map<String, String> map) {
        c cVar = this.j.get(str);
        if (cVar != null && cVar.m10a(str2)) {
            this.j.remove(str);
            if (z) {
                b(cVar.o, cVar.p);
            }
            a(cVar.e, cVar.o, cVar.p, cVar.m9a(), cVar.a(), map);
            com.alibaba.mtl.appmonitor.c.a.a().a(cVar);
        }
    }

    public void a(String str, Integer num, String str2, String str3, DimensionValueSet dimensionValueSet) {
        c cVar;
        synchronized (c.class) {
            cVar = this.j.get(str);
            if (cVar == null) {
                cVar = (c) com.alibaba.mtl.appmonitor.c.a.a().a(c.class, num, str2, str3);
                this.j.put(str, cVar);
            }
        }
        cVar.a(dimensionValueSet);
    }

    private String a(String str, String str2) {
        Metric metric = MetricRepo.getRepo().getMetric(str, str2);
        if (metric != null) {
            return metric.getTransactionId();
        }
        return null;
    }

    private void b(String str, String str2) {
        Metric metric = MetricRepo.getRepo().getMetric(str, str2);
        if (metric != null) {
            metric.resetTransactionId();
        }
    }

    private d a(UTDimensionValueSet uTDimensionValueSet, String str, String str2, String str3, Class<? extends d> cls) {
        Integer eventId;
        MetricValueSet metricValueSet;
        if (com.alibaba.mtl.appmonitor.f.b.c(str) && com.alibaba.mtl.appmonitor.f.b.c(str2) && (eventId = uTDimensionValueSet.getEventId()) != null) {
            synchronized (this.k) {
                metricValueSet = this.k.get(uTDimensionValueSet);
                if (metricValueSet == null) {
                    metricValueSet = (MetricValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(MetricValueSet.class, new Object[0]);
                    this.k.put(uTDimensionValueSet, metricValueSet);
                }
            }
            return metricValueSet.getEvent(eventId, str, str2, str3, cls);
        }
        return null;
    }

    private void a(f fVar, AtomicInteger atomicInteger) {
        int iIncrementAndGet = atomicInteger.incrementAndGet();
        i.a("EventRepo", fVar.toString(), " EVENT size:", String.valueOf(iIncrementAndGet));
        if (iIncrementAndGet >= fVar.b()) {
            i.a("EventRepo", fVar.toString(), " event size exceed trigger count.");
            atomicInteger.set(0);
            m11a(fVar.m12a());
        }
    }

    public Map<UTDimensionValueSet, List<d>> a(int i) {
        HashMap map = new HashMap();
        synchronized (this.k) {
            ArrayList arrayList = new ArrayList(this.k.keySet());
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                UTDimensionValueSet uTDimensionValueSet = (UTDimensionValueSet) arrayList.get(i2);
                if (uTDimensionValueSet != null && uTDimensionValueSet.getEventId().intValue() == i) {
                    map.put(uTDimensionValueSet, this.k.get(uTDimensionValueSet).getEvents());
                    this.k.remove(uTDimensionValueSet);
                }
            }
        }
        return map;
    }

    public void h() {
        ArrayList arrayList = new ArrayList(this.j.keySet());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            String str = (String) arrayList.get(i);
            c cVar = this.j.get(str);
            if (cVar != null && cVar.c()) {
                this.j.remove(str);
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public void m11a(int i) {
        final Map<UTDimensionValueSet, List<d>> mapA = a(i);
        r.a().b(new Runnable() { // from class: com.alibaba.mtl.appmonitor.a.e.1
            @Override // java.lang.Runnable
            public void run() {
                com.alibaba.mtl.appmonitor.f.c.b((Map<UTDimensionValueSet, List<d>>) mapA);
            }
        });
    }
}
