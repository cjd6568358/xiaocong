package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Dimension implements Parcelable {
    public static final Parcelable.Creator<Dimension> CREATOR = new Parcelable.Creator<Dimension>() { // from class: com.alibaba.mtl.appmonitor.model.Dimension.1
        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public Dimension createFromParcel(Parcel parcel) {
            return Dimension.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public Dimension[] newArray(int i) {
            return new Dimension[i];
        }
    };
    protected String name;
    protected String y;

    public Dimension() {
        this.y = "null";
    }

    public Dimension(String name, String constantValue) {
        this.y = "null";
        this.name = name;
        this.y = constantValue == null ? "null" : constantValue;
    }

    public String getName() {
        return this.name;
    }

    public String getConstantValue() {
        return this.y;
    }

    public int hashCode() {
        return (this.name == null ? 0 : this.name.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Dimension dimension = (Dimension) obj;
            if (this.name == null) {
                return dimension.name == null;
            }
            return this.name.equals(dimension.name);
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.y);
        dest.writeString(this.name);
    }

    static Dimension a(Parcel parcel) {
        try {
            return new Dimension(parcel.readString(), parcel.readString());
        } catch (Throwable th) {
            return null;
        }
    }
}
