package net.sqlcipher.database;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class SQLiteDatabase$2 implements Runnable {
    final /* synthetic */ SQLiteDatabase this$0;
    final /* synthetic */ byte[] val$keyMaterial;

    SQLiteDatabase$2(SQLiteDatabase this$0, byte[] bArr) {
        this.this$0 = this$0;
        this.val$keyMaterial = bArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.val$keyMaterial != null && this.val$keyMaterial.length > 0) {
            SQLiteDatabase.access$000(this.this$0, this.val$keyMaterial);
        }
    }
}
