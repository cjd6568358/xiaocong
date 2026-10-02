package net.sqlcipher.database;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class SQLiteDatabase$1 implements SQLiteDatabase$LibraryLoader {
    SQLiteDatabase$1() {
    }

    @Override // net.sqlcipher.database.SQLiteDatabase$LibraryLoader
    public void loadLibraries(String... libNames) {
        for (String libName : libNames) {
            System.loadLibrary(libName);
        }
    }
}
