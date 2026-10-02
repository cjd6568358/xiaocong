package com.tencent.android.tpush.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StorageEntity implements Parcelable {
    public static final Parcelable.Creator CREATOR = new a();
    public String a = Constants.MAIN_VERSION_TAG;
    public int b = -1;
    public boolean c;
    public String d;
    public int e;
    public float f;
    public long g;

    public StorageEntity() {
    }

    public StorageEntity(Parcel parcel) {
        a(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeInt(this.b);
        parcel.writeByte(this.c ? (byte) 1 : (byte) 0);
        parcel.writeString(this.d);
        parcel.writeInt(this.e);
        parcel.writeFloat(this.f);
        parcel.writeLong(this.g);
    }

    private void a(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readInt();
        this.c = parcel.readByte() == 1;
        this.d = parcel.readString();
        this.e = parcel.readInt();
        this.f = parcel.readFloat();
        this.g = parcel.readLong();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StorageEntity[key:").append(this.a).append(",type:").append(this.b).append(",strValue:").append(this.d).append(",boolValue:").append(this.c).append(",intValue").append(this.e).append(",floatValue:").append(this.f).append(",longValue:").append(this.g).append("]");
        return sb.toString();
    }
}
