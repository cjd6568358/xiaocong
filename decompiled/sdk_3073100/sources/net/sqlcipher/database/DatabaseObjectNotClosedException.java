package net.sqlcipher.database;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DatabaseObjectNotClosedException extends RuntimeException {
    private static final String s = "Application did not close the cursor or database object that was opened here";

    public DatabaseObjectNotClosedException() {
        super(s);
    }
}
