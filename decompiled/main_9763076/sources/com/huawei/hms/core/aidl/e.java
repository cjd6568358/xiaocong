package com.huawei.hms.core.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: IAIDLInvoke.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface e extends IInterface {
    void a(b bVar) throws RemoteException;

    void a(b bVar, d dVar) throws RemoteException;

    /* JADX INFO: compiled from: IAIDLInvoke.java */
    public static abstract class a extends Binder implements e {
        public static e a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.huawei.hms.core.aidl.IAIDLInvoke");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof e)) {
                return (e) iInterfaceQueryLocalInterface;
            }
            return new C0018a(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.huawei.hms.core.aidl.IAIDLInvoke");
                    a(parcel.readInt() != 0 ? b.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface("com.huawei.hms.core.aidl.IAIDLInvoke");
                    a(parcel.readInt() != 0 ? b.CREATOR.createFromParcel(parcel) : null, d.a.a(parcel.readStrongBinder()));
                    return true;
                case 1598968902:
                    parcel2.writeString("com.huawei.hms.core.aidl.IAIDLInvoke");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: renamed from: com.huawei.hms.core.aidl.e$a$a, reason: collision with other inner class name */
        /* JADX INFO: compiled from: IAIDLInvoke.java */
        private static class C0018a implements e {
            private IBinder a;

            C0018a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.huawei.hms.core.aidl.e
            public void a(b bVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.huawei.hms.core.aidl.IAIDLInvoke");
                    if (bVar != null) {
                        parcelObtain.writeInt(1);
                        bVar.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.core.aidl.e
            public void a(b bVar, d dVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.huawei.hms.core.aidl.IAIDLInvoke");
                    if (bVar != null) {
                        parcelObtain.writeInt(1);
                        bVar.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(dVar != null ? dVar.asBinder() : null);
                    this.a.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
