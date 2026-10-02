package com.xiaomi.metok.geofencing;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface c extends IInterface {

    public static abstract class a extends Binder implements c {

        /* JADX INFO: renamed from: com.xiaomi.metok.geofencing.c$a$a, reason: collision with other inner class name */
        private static class C0008a implements c {
            private IBinder a;

            C0008a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // com.xiaomi.metok.geofencing.c
            public String a() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metok.geofencing.IGeoFencing");
                    this.a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.metok.geofencing.c
            public void a(double d, double d2, float f, long j, PendingIntent pendingIntent, String str, String str2) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metok.geofencing.IGeoFencing");
                    parcelObtain.writeDouble(d);
                    parcelObtain.writeDouble(d2);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeLong(j);
                    if (pendingIntent != null) {
                        parcelObtain.writeInt(1);
                        pendingIntent.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.a.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.metok.geofencing.c
            public void a(double d, double d2, float f, long j, String str, String str2, String str3) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metok.geofencing.IGeoFencing");
                    parcelObtain.writeDouble(d);
                    parcelObtain.writeDouble(d2);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.a.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.metok.geofencing.c
            public void a(PendingIntent pendingIntent) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metok.geofencing.IGeoFencing");
                    if (pendingIntent != null) {
                        parcelObtain.writeInt(1);
                        pendingIntent.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.a.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.metok.geofencing.c
            public void a(String str) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metok.geofencing.IGeoFencing");
                    parcelObtain.writeString(str);
                    this.a.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.metok.geofencing.c
            public void a(String str, String str2) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metok.geofencing.IGeoFencing");
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.a.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.metok.geofencing.c
            public void a(List<String> list, String str) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metok.geofencing.IGeoFencing");
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str);
                    this.a.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.metok.geofencing.c
            public int b() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metok.geofencing.IGeoFencing");
                    this.a.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.metok.geofencing.c
            public List<String> b(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metok.geofencing.IGeoFencing");
                    parcelObtain.writeString(str);
                    this.a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static c a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.metok.geofencing.IGeoFencing");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof c)) ? new C0008a(iBinder) : (c) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.xiaomi.metok.geofencing.IGeoFencing");
                    a(parcel.createStringArrayList(), parcel.readString());
                    return true;
                case 2:
                    parcel.enforceInterface("com.xiaomi.metok.geofencing.IGeoFencing");
                    a(parcel.readString());
                    return true;
                case 3:
                    parcel.enforceInterface("com.xiaomi.metok.geofencing.IGeoFencing");
                    String strA = a();
                    parcel2.writeNoException();
                    parcel2.writeString(strA);
                    return true;
                case 4:
                    parcel.enforceInterface("com.xiaomi.metok.geofencing.IGeoFencing");
                    List<String> listB = b(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeStringList(listB);
                    return true;
                case 5:
                    parcel.enforceInterface("com.xiaomi.metok.geofencing.IGeoFencing");
                    int iB = b();
                    parcel2.writeNoException();
                    parcel2.writeInt(iB);
                    return true;
                case 6:
                    parcel.enforceInterface("com.xiaomi.metok.geofencing.IGeoFencing");
                    a(parcel.readDouble(), parcel.readDouble(), parcel.readFloat(), parcel.readLong(), parcel.readString(), parcel.readString(), parcel.readString());
                    return true;
                case 7:
                    parcel.enforceInterface("com.xiaomi.metok.geofencing.IGeoFencing");
                    a(parcel.readDouble(), parcel.readDouble(), parcel.readFloat(), parcel.readLong(), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readString(), parcel.readString());
                    return true;
                case 8:
                    parcel.enforceInterface("com.xiaomi.metok.geofencing.IGeoFencing");
                    a(parcel.readString(), parcel.readString());
                    return true;
                case 9:
                    parcel.enforceInterface("com.xiaomi.metok.geofencing.IGeoFencing");
                    a(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.xiaomi.metok.geofencing.IGeoFencing");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    String a();

    void a(double d, double d2, float f, long j, PendingIntent pendingIntent, String str, String str2);

    void a(double d, double d2, float f, long j, String str, String str2, String str3);

    void a(PendingIntent pendingIntent);

    void a(String str);

    void a(String str, String str2);

    void a(List<String> list, String str);

    int b();

    List<String> b(String str);
}
