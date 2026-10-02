package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MeasureSet implements Parcelable {
    public static final Parcelable.Creator<MeasureSet> CREATOR = new Parcelable.Creator<MeasureSet>() { // from class: com.alibaba.mtl.appmonitor.model.MeasureSet.1
        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public MeasureSet createFromParcel(Parcel parcel) {
            return MeasureSet.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public MeasureSet[] newArray(int i) {
            return new MeasureSet[i];
        }
    };
    private List<Measure> d = new ArrayList(3);

    public static MeasureSet create() {
        return new MeasureSet();
    }

    private MeasureSet() {
    }

    public boolean valid(MeasureValueSet measureValues) {
        if (this.d != null) {
            if (measureValues == null) {
                return false;
            }
            for (int i = 0; i < this.d.size(); i++) {
                Measure measure = this.d.get(i);
                if (measure != null) {
                    String name = measure.getName();
                    if (!measureValues.containValue(name) || !measure.valid(measureValues.getValue(name))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public MeasureSet addMeasure(Measure measure) {
        if (!this.d.contains(measure)) {
            this.d.add(measure);
        }
        return this;
    }

    public Measure getMeasure(String name) {
        for (Measure measure : this.d) {
            if (measure.getName().equals(name)) {
                return measure;
            }
        }
        return null;
    }

    public List<Measure> getMeasures() {
        return this.d;
    }

    public void setConstantValue(MeasureValueSet measureValues) {
        if (this.d != null && measureValues != null) {
            for (Measure measure : this.d) {
                if (measure.getConstantValue() != null && measureValues.getValue(measure.getName()) == null) {
                    measureValues.setValue(measure.getName(), measure.getConstantValue().doubleValue());
                }
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (this.d != null) {
            try {
                Object[] array = this.d.toArray();
                Measure[] measureArr = null;
                if (array != null) {
                    Measure[] measureArr2 = new Measure[array.length];
                    for (int i = 0; i < array.length; i++) {
                        measureArr2[i] = (Measure) array[i];
                    }
                    measureArr = measureArr2;
                }
                dest.writeParcelableArray(measureArr, flags);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static MeasureSet a(Parcel parcel) {
        MeasureSet measureSetCreate = create();
        try {
            Parcelable[] parcelableArray = parcel.readParcelableArray(MeasureSet.class.getClassLoader());
            if (parcelableArray != null) {
                ArrayList arrayList = new ArrayList(parcelableArray.length);
                for (Parcelable parcelable : parcelableArray) {
                    arrayList.add((Measure) parcelable);
                }
                measureSetCreate.d = arrayList;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return measureSetCreate;
    }
}
