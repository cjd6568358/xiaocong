package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Measure implements Parcelable {
    public static final Parcelable.Creator<Measure> CREATOR = new Parcelable.Creator<Measure>() { // from class: com.alibaba.mtl.appmonitor.model.Measure.1
        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public Measure createFromParcel(Parcel parcel) {
            return Measure.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public Measure[] newArray(int i) {
            return new Measure[i];
        }
    };
    protected Double a;
    protected Double b;
    protected Double c;
    protected String name;

    public Measure(String name, Double constantValue, Double min, Double max) {
        this.a = Double.valueOf(0.0d);
        this.b = Double.valueOf(0.0d);
        this.c = Double.valueOf(0.0d);
        this.a = min;
        this.b = max;
        this.name = name;
        this.c = Double.valueOf(constantValue != null ? constantValue.doubleValue() : 0.0d);
    }

    public Double getMax() {
        return this.b;
    }

    public String getName() {
        return this.name;
    }

    public Double getConstantValue() {
        return this.c;
    }

    public boolean valid(MeasureValue measureValue) {
        Double dValueOf = Double.valueOf(measureValue.getValue());
        return dValueOf != null && (this.a == null || dValueOf.doubleValue() >= this.a.doubleValue()) && (this.b == null || dValueOf.doubleValue() <= this.b.doubleValue());
    }

    public int hashCode() {
        return (this.name == null ? 0 : this.name.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Measure measure = (Measure) obj;
            if (this.name == null) {
                return measure.name == null;
            }
            return this.name.equals(measure.name);
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeInt(this.b == null ? 0 : 1);
            if (this.b != null) {
                dest.writeDouble(this.b.doubleValue());
            }
            dest.writeInt(this.a == null ? 0 : 1);
            if (this.a != null) {
                dest.writeDouble(this.a.doubleValue());
            }
            dest.writeString(this.name);
            dest.writeInt(this.c != null ? 1 : 0);
            if (this.c != null) {
                dest.writeDouble(this.c.doubleValue());
            }
        } catch (Throwable th) {
        }
    }

    static Measure a(Parcel parcel) {
        try {
            Double dValueOf = !(parcel.readInt() == 0) ? Double.valueOf(parcel.readDouble()) : null;
            return new Measure(parcel.readString(), !(parcel.readInt() == 0) ? Double.valueOf(parcel.readDouble()) : null, !(parcel.readInt() == 0) ? Double.valueOf(parcel.readDouble()) : null, dValueOf);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
