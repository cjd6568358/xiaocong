package com.huawei.hms.core.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: IAIDLCallback.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface d extends IInterface {
    void a(b bVar) throws RemoteException;

    /* JADX INFO: compiled from: IAIDLCallback.java */
    public static abstract class a extends Binder implements d {
        public a() {
            attachInterface(this, "com.huawei.hms.core.aidl.IAIDLCallback");
        }

        public static d a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.huawei.hms.core.aidl.IAIDLCallback");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof d)) {
                return (d) iInterfaceQueryLocalInterface;
            }
            return new C0017a(iBinder);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            b bVarCreateFromParcel;
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.huawei.hms.core.aidl.IAIDLCallback");
                    if (parcel.readInt() != 0) {
                        bVarCreateFromParcel = b.CREATOR.createFromParcel(parcel);
                    } else {
                        bVarCreateFromParcel = null;
                    }
                    a(bVarCreateFromParcel);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.huawei.hms.core.aidl.IAIDLCallback");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: renamed from: com.huawei.hms.core.aidl.d$a$a, reason: collision with other inner class name */
        /* JADX INFO: compiled from: IAIDLCallback.java */
        private static class C0017a implements d {
            private IBinder a;

            C0017a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.huawei.hms.core.aidl.d
            public void a(b bVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.huawei.hms.core.aidl.IAIDLCallback");
                    if (bVar != null) {
                        parcelObtain.writeInt(1);
                        bVar.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.a.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
