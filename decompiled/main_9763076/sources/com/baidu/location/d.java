package com.baidu.location;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class d implements Parcelable.Creator<Poi> {
    d() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public Poi createFromParcel(Parcel parcel) {
        return new Poi(parcel.readString(), parcel.readString(), parcel.readDouble());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public Poi[] newArray(int i) {
        return new Poi[i];
    }
}
