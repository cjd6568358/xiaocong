package com.alibaba.mtl.appmonitor;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface IMonitor extends IInterface {
    boolean alarm_checkSampled(String str, String str2) throws RemoteException;

    void alarm_commitFail1(String str, String str2, String str3, String str4, Map map) throws RemoteException;

    void alarm_commitFail2(String str, String str2, String str3, String str4, String str5, Map map) throws RemoteException;

    void alarm_commitSuccess1(String str, String str2, Map map) throws RemoteException;

    void alarm_commitSuccess2(String str, String str2, String str3, Map map) throws RemoteException;

    void alarm_setSampling(int i) throws RemoteException;

    void alarm_setStatisticsInterval(int i) throws RemoteException;

    boolean counter_checkSampled(String str, String str2) throws RemoteException;

    void counter_commit1(String str, String str2, double d, Map map) throws RemoteException;

    void counter_commit2(String str, String str2, String str3, double d, Map map) throws RemoteException;

    void counter_setSampling(int i) throws RemoteException;

    void counter_setStatisticsInterval(int i) throws RemoteException;

    void destroy() throws RemoteException;

    void enableLog(boolean z) throws RemoteException;

    void init() throws RemoteException;

    boolean offlinecounter_checkSampled(String str, String str2) throws RemoteException;

    void offlinecounter_commit(String str, String str2, double d) throws RemoteException;

    void offlinecounter_setSampling(int i) throws RemoteException;

    void offlinecounter_setStatisticsInterval(int i) throws RemoteException;

    void register1(String str, String str2, MeasureSet measureSet) throws RemoteException;

    void register2(String str, String str2, MeasureSet measureSet, boolean z) throws RemoteException;

    void register3(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet) throws RemoteException;

    void register4(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) throws RemoteException;

    void setChannel(String str) throws RemoteException;

    void setRequestAuthInfo(boolean z, String str, String str2, String str3) throws RemoteException;

    void setSampling(int i) throws RemoteException;

    void setStatisticsInterval1(int i) throws RemoteException;

    void setStatisticsInterval2(int i, int i2) throws RemoteException;

    void stat_begin(String str, String str2, String str3) throws RemoteException;

    boolean stat_checkSampled(String str, String str2) throws RemoteException;

    void stat_commit1(String str, String str2, double d, Map map) throws RemoteException;

    void stat_commit2(String str, String str2, DimensionValueSet dimensionValueSet, double d, Map map) throws RemoteException;

    void stat_commit3(String str, String str2, DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet, Map map) throws RemoteException;

    void stat_end(String str, String str2, String str3) throws RemoteException;

    void stat_setSampling(int i) throws RemoteException;

    void stat_setStatisticsInterval(int i) throws RemoteException;

    void transaction_begin(Transaction transaction, String str) throws RemoteException;

    void transaction_end(Transaction transaction, String str) throws RemoteException;

    void triggerUpload() throws RemoteException;

    void turnOffRealTimeDebug() throws RemoteException;

    void turnOnRealTimeDebug(Map map) throws RemoteException;

    public static abstract class Stub extends Binder implements IMonitor {
        public Stub() {
            attachInterface(this, "com.alibaba.mtl.appmonitor.IMonitor");
        }

        public static IMonitor asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface("com.alibaba.mtl.appmonitor.IMonitor");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMonitor)) {
                return (IMonitor) iInterfaceQueryLocalInterface;
            }
            return new a(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    init();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    enableLog(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    setRequestAuthInfo(parcel.readInt() != 0, parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    setChannel(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    triggerUpload();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    setSampling(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    setStatisticsInterval1(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    setStatisticsInterval2(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    register1(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? MeasureSet.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    register2(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? MeasureSet.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    register3(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? MeasureSet.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? DimensionSet.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    register4(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? MeasureSet.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? DimensionSet.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    turnOnRealTimeDebug(parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 14:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    turnOffRealTimeDebug();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    destroy();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    counter_setStatisticsInterval(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    counter_setSampling(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 18:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    boolean zCounter_checkSampled = counter_checkSampled(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zCounter_checkSampled ? 1 : 0);
                    return true;
                case 19:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    counter_commit1(parcel.readString(), parcel.readString(), parcel.readDouble(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 20:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    counter_commit2(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readDouble(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 21:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    offlinecounter_setStatisticsInterval(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 22:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    offlinecounter_setSampling(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 23:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    boolean zOfflinecounter_checkSampled = offlinecounter_checkSampled(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zOfflinecounter_checkSampled ? 1 : 0);
                    return true;
                case 24:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    offlinecounter_commit(parcel.readString(), parcel.readString(), parcel.readDouble());
                    parcel2.writeNoException();
                    return true;
                case 25:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_setStatisticsInterval(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 26:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_setSampling(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 27:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    boolean zAlarm_checkSampled = alarm_checkSampled(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zAlarm_checkSampled ? 1 : 0);
                    return true;
                case 28:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_commitSuccess1(parcel.readString(), parcel.readString(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 29:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_commitSuccess2(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 30:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_commitFail1(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 31:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_commitFail2(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 32:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_begin(parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 33:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_end(parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 34:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_setStatisticsInterval(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 35:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_setSampling(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 36:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    boolean zStat_checkSampled = stat_checkSampled(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zStat_checkSampled ? 1 : 0);
                    return true;
                case 37:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_commit1(parcel.readString(), parcel.readString(), parcel.readDouble(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 38:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_commit2(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? DimensionValueSet.CREATOR.createFromParcel(parcel) : null, parcel.readDouble(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 39:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_commit3(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? DimensionValueSet.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? MeasureValueSet.CREATOR.createFromParcel(parcel) : null, parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 40:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    transaction_begin(parcel.readInt() != 0 ? Transaction.CREATOR.createFromParcel(parcel) : null, parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 41:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    transaction_end(parcel.readInt() != 0 ? Transaction.CREATOR.createFromParcel(parcel) : null, parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.alibaba.mtl.appmonitor.IMonitor");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class a implements IMonitor {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void init() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void enableLog(boolean open) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(open ? 1 : 0);
                    this.a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void setRequestAuthInfo(boolean isSecurity, String appkey, String secret, String authcode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(isSecurity ? 1 : 0);
                    parcelObtain.writeString(appkey);
                    parcelObtain.writeString(secret);
                    parcelObtain.writeString(authcode);
                    this.a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void setChannel(String channel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(channel);
                    this.a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void triggerUpload() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    this.a.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void setSampling(int sampling) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(sampling);
                    this.a.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void setStatisticsInterval1(int statisticsInterval) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(statisticsInterval);
                    this.a.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void setStatisticsInterval2(int eventType, int statisticsInterval) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(eventType);
                    parcelObtain.writeInt(statisticsInterval);
                    this.a.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void register1(String module, String monitorPoint, MeasureSet measures) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    if (measures != null) {
                        parcelObtain.writeInt(1);
                        measures.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.a.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void register2(String module, String monitorPoint, MeasureSet measures, boolean isCommitDetail) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    if (measures != null) {
                        parcelObtain.writeInt(1);
                        measures.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(isCommitDetail ? 1 : 0);
                    this.a.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void register3(String module, String monitorPoint, MeasureSet measures, DimensionSet dimensions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    if (measures != null) {
                        parcelObtain.writeInt(1);
                        measures.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (dimensions != null) {
                        parcelObtain.writeInt(1);
                        dimensions.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.a.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void register4(String module, String monitorPoint, MeasureSet measures, DimensionSet dimensions, boolean isCommitDetail) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    if (measures != null) {
                        parcelObtain.writeInt(1);
                        measures.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (dimensions != null) {
                        parcelObtain.writeInt(1);
                        dimensions.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(isCommitDetail ? 1 : 0);
                    this.a.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void turnOnRealTimeDebug(Map params) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeMap(params);
                    this.a.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void turnOffRealTimeDebug() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    this.a.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void destroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    this.a.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void counter_setStatisticsInterval(int statisticsInterval) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(statisticsInterval);
                    this.a.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void counter_setSampling(int sampling) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(sampling);
                    this.a.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public boolean counter_checkSampled(String module, String monitorPoint) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    this.a.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void counter_commit1(String module, String monitorPoint, double value, Map exta) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    parcelObtain.writeDouble(value);
                    parcelObtain.writeMap(exta);
                    this.a.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void counter_commit2(String module, String monitorPoint, String arg, double value, Map exta) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    parcelObtain.writeString(arg);
                    parcelObtain.writeDouble(value);
                    parcelObtain.writeMap(exta);
                    this.a.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void offlinecounter_setStatisticsInterval(int statisticsInterval) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(statisticsInterval);
                    this.a.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void offlinecounter_setSampling(int sampling) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(sampling);
                    this.a.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public boolean offlinecounter_checkSampled(String module, String monitorPoint) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    this.a.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void offlinecounter_commit(String module, String monitorPoint, double value) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    parcelObtain.writeDouble(value);
                    this.a.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_setStatisticsInterval(int statisticsInterval) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(statisticsInterval);
                    this.a.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_setSampling(int sampling) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(sampling);
                    this.a.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public boolean alarm_checkSampled(String module, String monitorPoint) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    this.a.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_commitSuccess1(String module, String monitorPoint, Map exta) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    parcelObtain.writeMap(exta);
                    this.a.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_commitSuccess2(String module, String monitorPoint, String arg, Map exta) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    parcelObtain.writeString(arg);
                    parcelObtain.writeMap(exta);
                    this.a.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_commitFail1(String module, String monitorPoint, String errorCode, String errorMsg, Map exta) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    parcelObtain.writeString(errorCode);
                    parcelObtain.writeString(errorMsg);
                    parcelObtain.writeMap(exta);
                    this.a.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_commitFail2(String module, String monitorPoint, String arg, String errorCode, String errorMsg, Map exta) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    parcelObtain.writeString(arg);
                    parcelObtain.writeString(errorCode);
                    parcelObtain.writeString(errorMsg);
                    parcelObtain.writeMap(exta);
                    this.a.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_begin(String module, String monitorPoint, String measureName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    parcelObtain.writeString(measureName);
                    this.a.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_end(String module, String monitorPoint, String measureName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    parcelObtain.writeString(measureName);
                    this.a.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_setStatisticsInterval(int statisticsInterval) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(statisticsInterval);
                    this.a.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_setSampling(int sampling) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeInt(sampling);
                    this.a.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public boolean stat_checkSampled(String module, String monitorPoint) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    this.a.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_commit1(String module, String monitorPoint, double value, Map exta) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    parcelObtain.writeDouble(value);
                    parcelObtain.writeMap(exta);
                    this.a.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_commit2(String module, String monitorPoint, DimensionValueSet dimensionValues, double value, Map exta) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    if (dimensionValues != null) {
                        parcelObtain.writeInt(1);
                        dimensionValues.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeDouble(value);
                    parcelObtain.writeMap(exta);
                    this.a.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_commit3(String module, String monitorPoint, DimensionValueSet dimensionValues, MeasureValueSet measureValues, Map exta) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    parcelObtain.writeString(module);
                    parcelObtain.writeString(monitorPoint);
                    if (dimensionValues != null) {
                        parcelObtain.writeInt(1);
                        dimensionValues.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (measureValues != null) {
                        parcelObtain.writeInt(1);
                        measureValues.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeMap(exta);
                    this.a.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void transaction_begin(Transaction transaction, String measureName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    if (transaction != null) {
                        parcelObtain.writeInt(1);
                        transaction.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeString(measureName);
                    this.a.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void transaction_end(Transaction transaction, String measureName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    if (transaction != null) {
                        parcelObtain.writeInt(1);
                        transaction.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeString(measureName);
                    this.a.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
