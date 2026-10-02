package com.alibaba.mtl.appmonitor.a;

import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.Measure;
import com.alibaba.mtl.appmonitor.model.MeasureValue;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.appmonitor.model.Metric;
import com.alibaba.mtl.appmonitor.model.MetricRepo;
import com.alibaba.mtl.log.e.i;
import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: StatEvent.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g extends d {
    private Metric a;
    private Map<DimensionValueSet, a> m;

    /* JADX WARN: Code duplicated, block: B:11:0x002c A[Catch: all -> 0x0081, TryCatch #0 {, blocks: (B:5:0x0004, B:6:0x0017, B:8:0x001f, B:9:0x0028, B:11:0x002c, B:13:0x0034, B:14:0x003a, B:22:0x0084, B:24:0x008f, B:17:0x0064), top: B:27:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:13:0x0034 A[Catch: all -> 0x0081, TryCatch #0 {, blocks: (B:5:0x0004, B:6:0x0017, B:8:0x001f, B:9:0x0028, B:11:0x002c, B:13:0x0034, B:14:0x003a, B:22:0x0084, B:24:0x008f, B:17:0x0064), top: B:27:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:17:0x0064 A[Catch: all -> 0x0081, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0004, B:6:0x0017, B:8:0x001f, B:9:0x0028, B:11:0x002c, B:13:0x0034, B:14:0x003a, B:22:0x0084, B:24:0x008f, B:17:0x0064), top: B:27:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:22:0x0084 A[Catch: all -> 0x0081, TRY_ENTER, TryCatch #0 {, blocks: (B:5:0x0004, B:6:0x0017, B:8:0x001f, B:9:0x0028, B:11:0x002c, B:13:0x0034, B:14:0x003a, B:22:0x0084, B:24:0x008f, B:17:0x0064), top: B:27:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:24:0x008f A[Catch: all -> 0x0081, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0004, B:6:0x0017, B:8:0x001f, B:9:0x0028, B:11:0x002c, B:13:0x0034, B:14:0x003a, B:22:0x0084, B:24:0x008f, B:17:0x0064), top: B:27:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:26:0x0093  */
    /* JADX WARN: Code duplicated, block: B:8:0x001f A[Catch: all -> 0x0081, TryCatch #0 {, blocks: (B:5:0x0004, B:6:0x0017, B:8:0x001f, B:9:0x0028, B:11:0x002c, B:13:0x0034, B:14:0x003a, B:22:0x0084, B:24:0x008f, B:17:0x0064), top: B:27:0x0004 }] */
    public synchronized void a(DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet) {
        a aVar;
        boolean zValid;
        if (dimensionValueSet == null) {
            DimensionValueSet dimensionValueSet2 = (DimensionValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(DimensionValueSet.class, new Object[0]);
            dimensionValueSet2.addValues(dimensionValueSet);
            dimensionValueSet = dimensionValueSet2;
            if (this.m.containsKey(dimensionValueSet)) {
                aVar = this.m.get(dimensionValueSet);
            } else {
                DimensionValueSet dimensionValueSet3 = (DimensionValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(DimensionValueSet.class, new Object[0]);
                dimensionValueSet3.addValues(dimensionValueSet);
                aVar = new a();
                this.m.put(dimensionValueSet3, aVar);
            }
            if (this.a != null) {
                zValid = this.a.valid(dimensionValueSet, measureValueSet);
            } else {
                zValid = false;
            }
            if (zValid) {
                aVar.i();
                aVar.m14a(measureValueSet);
            } else {
                aVar.j();
                if (this.a.isCommitDetail()) {
                    aVar.m14a(measureValueSet);
                }
            }
            i.a("StatEvent", "entity  count:", Integer.valueOf(aVar.count), " noise:", Integer.valueOf(aVar.l));
        } else {
            if (this.m.containsKey(dimensionValueSet)) {
                aVar = this.m.get(dimensionValueSet);
            } else {
                DimensionValueSet dimensionValueSet4 = (DimensionValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(DimensionValueSet.class, new Object[0]);
                dimensionValueSet4.addValues(dimensionValueSet);
                aVar = new a();
                this.m.put(dimensionValueSet4, aVar);
            }
            if (this.a != null) {
                zValid = this.a.valid(dimensionValueSet, measureValueSet);
            } else {
                zValid = false;
            }
            if (zValid) {
                aVar.i();
                aVar.m14a(measureValueSet);
            } else {
                aVar.j();
                if (this.a.isCommitDetail()) {
                    aVar.m14a(measureValueSet);
                }
            }
            i.a("StatEvent", "entity  count:", Integer.valueOf(aVar.count), " noise:", Integer.valueOf(aVar.l));
        }
        throw th;
    }

    @Override // com.alibaba.mtl.appmonitor.a.d
    public synchronized JSONObject a() {
        JSONObject jSONObjectA;
        Set<String> setKeySet;
        jSONObjectA = super.a();
        try {
            if (this.a != null) {
                jSONObjectA.put("isCommitDetail", String.valueOf(this.a.isCommitDetail()));
            }
            JSONArray jSONArray = (JSONArray) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.d.class, new Object[0]);
            if (this.m != null) {
                for (Map.Entry<DimensionValueSet, a> entry : this.m.entrySet()) {
                    JSONObject jSONObject = (JSONObject) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.e.class, new Object[0]);
                    DimensionValueSet key = entry.getKey();
                    a value = entry.getValue();
                    Object objValueOf = Integer.valueOf(value.count);
                    Object objValueOf2 = Integer.valueOf(value.l);
                    jSONObject.put("count", objValueOf);
                    jSONObject.put("noise", objValueOf2);
                    jSONObject.put("dimensions", key != null ? new JSONObject(key.getMap()) : Constants.MAIN_VERSION_TAG);
                    List<Map<String, Map<String, Double>>> listA = value.a();
                    JSONArray jSONArray2 = new JSONArray();
                    for (int i = 0; i < listA.size(); i++) {
                        JSONObject jSONObject2 = new JSONObject();
                        Map<String, Map<String, Double>> map = listA.get(i);
                        if (map != null && (setKeySet = map.keySet()) != null) {
                            for (String str : setKeySet) {
                                if (map.get(str) != null) {
                                    jSONObject2.put(str, new JSONObject(map.get(str)));
                                } else {
                                    jSONObject2.put(str, Constants.MAIN_VERSION_TAG);
                                }
                            }
                        }
                        jSONArray2.put(jSONObject2);
                    }
                    jSONObject.put("measures", jSONArray2);
                    jSONArray.put(jSONObject);
                }
            }
            jSONObjectA.put("values", jSONArray);
        } catch (Exception e) {
        }
        return jSONObjectA;
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public synchronized void clean() {
        super.clean();
        this.a = null;
        Iterator<DimensionValueSet> it = this.m.keySet().iterator();
        while (it.hasNext()) {
            com.alibaba.mtl.appmonitor.c.a.a().a(it.next());
        }
        this.m.clear();
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... params) {
        super.fill(params);
        if (this.m == null) {
            this.m = new HashMap();
        }
        this.a = MetricRepo.getRepo().getMetric(this.o, this.p);
    }

    /* JADX INFO: compiled from: StatEvent.java */
    public class a {
        private int count = 0;
        private int l = 0;
        private List<MeasureValueSet> b = new ArrayList();

        public a() {
        }

        /* JADX INFO: renamed from: a, reason: collision with other method in class */
        public void m14a(MeasureValueSet measureValueSet) {
            if (measureValueSet != null) {
                if (g.this.a != null && g.this.a.isCommitDetail()) {
                    this.b.add(a(measureValueSet));
                } else if (this.b.isEmpty()) {
                    this.b.add(a(measureValueSet));
                } else {
                    this.b.get(0).merge(measureValueSet);
                }
            }
        }

        private MeasureValueSet a(MeasureValueSet measureValueSet) {
            List<Measure> measures;
            MeasureValueSet measureValueSet2 = (MeasureValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(MeasureValueSet.class, new Object[0]);
            if (g.this.a != null && g.this.a.getMeasureSet() != null && (measures = g.this.a.getMeasureSet().getMeasures()) != null) {
                int size = measures.size();
                for (int i = 0; i < size; i++) {
                    Measure measure = measures.get(i);
                    if (measure != null) {
                        MeasureValue measureValue = (MeasureValue) com.alibaba.mtl.appmonitor.c.a.a().a(MeasureValue.class, new Object[0]);
                        MeasureValue value = measureValueSet.getValue(measure.getName());
                        if (value.getOffset() != null) {
                            measureValue.setOffset(value.getOffset().doubleValue());
                        }
                        measureValue.setValue(value.getValue());
                        measureValueSet2.setValue(measure.getName(), measureValue);
                    }
                }
            }
            return measureValueSet2;
        }

        public List<Map<String, Map<String, Double>>> a() {
            Map<String, MeasureValue> map;
            if (this.b == null || this.b.isEmpty()) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                MeasureValueSet measureValueSet = this.b.get(i);
                if (measureValueSet != null && (map = measureValueSet.getMap()) != null && !map.isEmpty()) {
                    HashMap map2 = new HashMap();
                    for (Map.Entry<String, MeasureValue> entry : map.entrySet()) {
                        HashMap map3 = new HashMap();
                        String key = entry.getKey();
                        MeasureValue value = entry.getValue();
                        map3.put("value", Double.valueOf(value.getValue()));
                        if (value.getOffset() != null) {
                            map3.put(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_OFFSET, value.getOffset());
                        }
                        map2.put(key, map3);
                    }
                    arrayList.add(map2);
                }
            }
            return arrayList;
        }

        public void i() {
            this.count++;
        }

        public void j() {
            this.l++;
        }
    }
}
