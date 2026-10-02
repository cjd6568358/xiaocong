package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.mtl.appmonitor.c.a;
import com.alibaba.mtl.appmonitor.c.b;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MeasureValueSet implements Parcelable, b {
    public static final Parcelable.Creator<MeasureValueSet> CREATOR = new Parcelable.Creator<MeasureValueSet>() { // from class: com.alibaba.mtl.appmonitor.model.MeasureValueSet.1
        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public MeasureValueSet createFromParcel(Parcel parcel) {
            return MeasureValueSet.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public MeasureValueSet[] newArray(int i) {
            return new MeasureValueSet[i];
        }
    };
    private Map<String, MeasureValue> map = new LinkedHashMap();

    public static MeasureValueSet create() {
        return (MeasureValueSet) a.a().a(MeasureValueSet.class, new Object[0]);
    }

    @Deprecated
    public MeasureValueSet() {
    }

    public MeasureValueSet setValue(String str, double d) {
        this.map.put(str, (MeasureValue) a.a().a(MeasureValue.class, Double.valueOf(d)));
        return this;
    }

    public void setValue(String name, MeasureValue value) {
        this.map.put(name, value);
    }

    public MeasureValue getValue(String name) {
        return this.map.get(name);
    }

    public Map<String, MeasureValue> getMap() {
        return this.map;
    }

    public boolean containValue(String name) {
        return this.map.containsKey(name);
    }

    public void merge(MeasureValueSet t) {
        for (String str : this.map.keySet()) {
            this.map.get(str).merge(t.getValue(str));
        }
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        Iterator<MeasureValue> it = this.map.values().iterator();
        while (it.hasNext()) {
            a.a().a(it.next());
        }
        this.map.clear();
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... params) {
        if (this.map == null) {
            this.map = new LinkedHashMap();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.map);
    }

    static MeasureValueSet a(Parcel parcel) {
        MeasureValueSet measureValueSetCreate = null;
        try {
            measureValueSetCreate = create();
            measureValueSetCreate.map = parcel.readHashMap(DimensionValueSet.class.getClassLoader());
            return measureValueSetCreate;
        } catch (Throwable th) {
            return measureValueSetCreate;
        }
    }
}
