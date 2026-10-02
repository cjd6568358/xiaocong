package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.mtl.log.e.i;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DimensionSet implements Parcelable {
    public static final Parcelable.Creator<DimensionSet> CREATOR = new Parcelable.Creator<DimensionSet>() { // from class: com.alibaba.mtl.appmonitor.model.DimensionSet.1
        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public DimensionSet createFromParcel(Parcel parcel) {
            return DimensionSet.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public DimensionSet[] newArray(int i) {
            return new DimensionSet[i];
        }
    };
    private List<Dimension> c = new ArrayList(3);

    public static DimensionSet create() {
        return new DimensionSet();
    }

    private DimensionSet() {
    }

    public boolean valid(DimensionValueSet dimensionValues) {
        if (this.c != null) {
            if (dimensionValues == null) {
                return false;
            }
            Iterator<Dimension> it = this.c.iterator();
            while (it.hasNext()) {
                if (!dimensionValues.containValue(it.next().getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setConstantValue(DimensionValueSet dimensionValues) {
        if (this.c != null && dimensionValues != null) {
            for (Dimension dimension : this.c) {
                if (dimension.getConstantValue() != null && dimensionValues.getValue(dimension.getName()) == null) {
                    dimensionValues.setValue(dimension.getName(), dimension.getConstantValue());
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
        if (this.c != null) {
            try {
                Object[] array = this.c.toArray();
                Dimension[] dimensionArr = null;
                if (array != null) {
                    Dimension[] dimensionArr2 = new Dimension[array.length];
                    for (int i = 0; i < array.length; i++) {
                        dimensionArr2[i] = (Dimension) array[i];
                    }
                    dimensionArr = dimensionArr2;
                }
                dest.writeParcelableArray(dimensionArr, flags);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static DimensionSet a(Parcel parcel) {
        DimensionSet dimensionSetCreate = create();
        try {
            Parcelable[] parcelableArray = parcel.readParcelableArray(DimensionSet.class.getClassLoader());
            if (parcelableArray != null) {
                if (dimensionSetCreate.c == null) {
                    dimensionSetCreate.c = new ArrayList();
                }
                for (int i = 0; i < parcelableArray.length; i++) {
                    if (parcelableArray[i] != null && (parcelableArray[i] instanceof Dimension)) {
                        dimensionSetCreate.c.add((Dimension) parcelableArray[i]);
                    } else {
                        i.a("DimensionSet", "parcelables[i]:", parcelableArray[i]);
                    }
                }
            }
        } catch (Throwable th) {
            i.a("DimensionSet", "[readFromParcel]", th);
        }
        return dimensionSetCreate;
    }
}
