package net.sqlcipher.database;

/* JADX INFO: Access modifiers changed from: private */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SQLiteDatabase$SyncUpdateInfo {
    String deletedTable;
    String foreignKey;
    String masterTable;

    SQLiteDatabase$SyncUpdateInfo(String masterTable, String deletedTable, String foreignKey) {
        this.masterTable = masterTable;
        this.deletedTable = deletedTable;
        this.foreignKey = foreignKey;
    }
}
