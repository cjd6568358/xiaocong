package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.mtl.appmonitor.c.a;
import com.alibaba.mtl.appmonitor.c.b;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DimensionValueSet implements Parcelable, b {
    public static final Parcelable.Creator<DimensionValueSet> CREATOR = new Parcelable.Creator<DimensionValueSet>() { // from class: com.alibaba.mtl.appmonitor.model.DimensionValueSet.1
        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public DimensionValueSet createFromParcel(Parcel parcel) {
            return DimensionValueSet.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public DimensionValueSet[] newArray(int i) {
            return new DimensionValueSet[i];
        }
    };
    protected Map<String, String> map;

    public static DimensionValueSet create() {
        return (DimensionValueSet) a.a().a(DimensionValueSet.class, new Object[0]);
    }

    @Deprecated
    public DimensionValueSet() {
        if (this.map == null) {
            this.map = new LinkedHashMap();
        }
    }

    public DimensionValueSet setValue(String name, String value) {
        Map<String, String> map = this.map;
        if (value == null) {
            value = "null";
        }
        map.put(name, value);
        return this;
    }

    public DimensionValueSet addValues(DimensionValueSet dimensionValues) {
        Map<String, String> map;
        if (dimensionValues != null && (map = dimensionValues.getMap()) != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.map.put(entry.getKey(), entry.getValue() != null ? entry.getValue() : "null");
            }
        }
        return this;
    }

    public boolean containValue(String name) {
        return this.map.containsKey(name);
    }

    public String getValue(String name) {
        return this.map.get(name);
    }

    public Map<String, String> getMap() {
        return this.map;
    }

    public void setMap(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.map.put(entry.getKey(), entry.getValue() != null ? entry.getValue() : "null");
        }
    }

    public int hashCode() {
        return (this.map == null ? 0 : this.map.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DimensionValueSet dimensionValueSet = (DimensionValueSet) obj;
            if (this.map == null) {
                return dimensionValueSet.map == null;
            }
            return this.map.equals(dimensionValueSet.map);
        }
        return false;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
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

    static DimensionValueSet a(Parcel parcel) {
        DimensionValueSet dimensionValueSetCreate;
        Throwable th;
        try {
            dimensionValueSetCreate = create();
            try {
                dimensionValueSetCreate.map = parcel.readHashMap(DimensionValueSet.class.getClassLoader());
            } catch (Throwable th2) {
                th = th2;
                th.printStackTrace();
            }
        } catch (Throwable th3) {
            dimensionValueSetCreate = null;
            th = th3;
        }
        return dimensionValueSetCreate;
    }
}
