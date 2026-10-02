package com.huawei.hms.core.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: DataBuffer.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class c implements Parcelable.Creator<b> {
    c() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public b createFromParcel(Parcel parcel) {
        return new b(parcel, (c) null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public b[] newArray(int i) {
        return new b[i];
    }
}
