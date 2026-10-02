package com.tencent.android.tpush.data;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class a implements Parcelable.Creator {
    a() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public StorageEntity createFromParcel(Parcel parcel) {
        return new StorageEntity(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public StorageEntity[] newArray(int i) {
        return new StorageEntity[i];
    }
}
