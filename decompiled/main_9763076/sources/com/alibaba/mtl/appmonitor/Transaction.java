package com.alibaba.mtl.appmonitor;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Transaction implements Parcelable {
    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() { // from class: com.alibaba.mtl.appmonitor.Transaction.1
        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public Transaction createFromParcel(Parcel parcel) {
            return Transaction.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public Transaction[] newArray(int i) {
            return new Transaction[i];
        }
    };
    protected Integer a;
    protected DimensionValueSet b;
    protected Map<String, String> e;
    protected String o;
    protected String p;
    protected String r;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.b, flags);
        dest.writeInt(this.a.intValue());
        dest.writeString(this.o);
        dest.writeString(this.p);
        dest.writeString(this.r);
        dest.writeMap(this.e);
    }

    static Transaction a(Parcel parcel) {
        Transaction transaction = new Transaction();
        try {
            transaction.b = (DimensionValueSet) parcel.readParcelable(Transaction.class.getClassLoader());
            transaction.a = Integer.valueOf(parcel.readInt());
            transaction.o = parcel.readString();
            transaction.p = parcel.readString();
            transaction.r = parcel.readString();
            transaction.e = parcel.readHashMap(Transaction.class.getClassLoader());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return transaction;
    }
}
