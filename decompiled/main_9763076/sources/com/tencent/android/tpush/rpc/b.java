package com.tencent.android.tpush.rpc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class b extends Binder implements a {
    public b() {
        attachInterface(this, "com.tencent.android.tpush.rpc.ITask");
    }

    public static a a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.tencent.android.tpush.rpc.ITask");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof a)) {
            return (a) iInterfaceQueryLocalInterface;
        }
        return new c(iBinder);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                parcel.enforceInterface("com.tencent.android.tpush.rpc.ITask");
                a();
                parcel2.writeNoException();
                return true;
            case 2:
                parcel.enforceInterface("com.tencent.android.tpush.rpc.ITask");
                b();
                parcel2.writeNoException();
                return true;
            case 3:
                parcel.enforceInterface("com.tencent.android.tpush.rpc.ITask");
                a(parcel.readString(), e.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 1598968902:
                parcel2.writeString("com.tencent.android.tpush.rpc.ITask");
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
