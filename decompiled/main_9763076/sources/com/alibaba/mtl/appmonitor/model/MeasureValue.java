package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.mtl.appmonitor.c.a;
import com.alibaba.mtl.appmonitor.c.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MeasureValue implements Parcelable, b {
    public static final Parcelable.Creator<MeasureValue> CREATOR = new Parcelable.Creator<MeasureValue>() { // from class: com.alibaba.mtl.appmonitor.model.MeasureValue.1
        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public MeasureValue createFromParcel(Parcel parcel) {
            return MeasureValue.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public MeasureValue[] newArray(int i) {
            return new MeasureValue[i];
        }
    };
    private Double d;
    private double e;
    private boolean n;

    @Deprecated
    public MeasureValue() {
    }

    public static MeasureValue create() {
        return (MeasureValue) a.a().a(MeasureValue.class, new Object[0]);
    }

    public Double getOffset() {
        return this.d;
    }

    public boolean isFinish() {
        return this.n;
    }

    public void setFinish(boolean finish) {
        this.n = finish;
    }

    public void setOffset(double offset) {
        this.d = Double.valueOf(offset);
    }

    public double getValue() {
        return this.e;
    }

    public void setValue(double value) {
        this.e = value;
    }

    public synchronized void merge(MeasureValue t) {
        if (t != null) {
            try {
                this.e += t.getValue();
                if (t.getOffset() != null) {
                    if (this.d == null) {
                        this.d = Double.valueOf(0.0d);
                    }
                    this.d = Double.valueOf(this.d.doubleValue() + t.getOffset().doubleValue());
                }
            } catch (Throwable th) {
            }
        }
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public synchronized void clean() {
        this.e = 0.0d;
        this.d = null;
        this.n = false;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public synchronized void fill(Object... params) {
        if (params != null) {
            if (params.length > 0) {
                this.e = ((Double) params[0]).doubleValue();
            }
            if (params.length > 1) {
                this.d = (Double) params[1];
                this.n = false;
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeInt(this.n ? 1 : 0);
            dest.writeDouble(this.d == null ? 0.0d : this.d.doubleValue());
            dest.writeDouble(this.e);
        } catch (Throwable th) {
        }
    }

    static MeasureValue a(Parcel parcel) {
        MeasureValue measureValueCreate;
        Throwable th;
        try {
            boolean z = parcel.readInt() != 0;
            Double dValueOf = Double.valueOf(parcel.readDouble());
            double d = parcel.readDouble();
            measureValueCreate = create();
            try {
                measureValueCreate.n = z;
                measureValueCreate.d = dValueOf;
                measureValueCreate.e = d;
            } catch (Throwable th2) {
                th = th2;
                th.printStackTrace();
            }
        } catch (Throwable th3) {
            measureValueCreate = null;
            th = th3;
        }
        return measureValueCreate;
    }
}
