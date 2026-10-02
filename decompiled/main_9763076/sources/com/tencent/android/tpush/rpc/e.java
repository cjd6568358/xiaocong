package com.tencent.android.tpush.rpc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class e extends Binder implements d {
    public e() {
        attachInterface(this, "com.tencent.android.tpush.rpc.ITaskCallback");
    }

    public static d a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.tencent.android.tpush.rpc.ITaskCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof d)) {
            return (d) iInterfaceQueryLocalInterface;
        }
        return new f(iBinder);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                parcel.enforceInterface("com.tencent.android.tpush.rpc.ITaskCallback");
                a();
                parcel2.writeNoException();
                return true;
            case 1598968902:
                parcel2.writeString("com.tencent.android.tpush.rpc.ITaskCallback");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
