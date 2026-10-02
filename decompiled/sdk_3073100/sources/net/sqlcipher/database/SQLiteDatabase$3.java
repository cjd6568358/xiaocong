package net.sqlcipher.database;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class SQLiteDatabase$3 implements Runnable {
    final /* synthetic */ SQLiteDatabase this$0;
    final /* synthetic */ char[] val$password;

    SQLiteDatabase$3(SQLiteDatabase this$0, char[] cArr) {
        this.this$0 = this$0;
        this.val$password = cArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.val$password != null) {
            SQLiteDatabase.access$100(this.this$0, this.val$password);
        }
    }
}
