package net.sqlcipher;

import android.util.Log;
import java.io.File;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class DefaultDatabaseErrorHandler implements DatabaseErrorHandler {
    private final String TAG = getClass().getSimpleName();

    @Override // net.sqlcipher.DatabaseErrorHandler
    public void onCorruption(SQLiteDatabase dbObj) {
        Log.e(this.TAG, "Corruption reported by sqlite on database, deleting: " + dbObj.getPath());
        if (dbObj.isOpen()) {
            Log.e(this.TAG, "Database object for corrupted database is already open, closing");
            try {
                dbObj.close();
            } catch (Exception e) {
                Log.e(this.TAG, "Exception closing Database object for corrupted database, ignored", e);
            }
        }
        deleteDatabaseFile(dbObj.getPath());
    }

    private void deleteDatabaseFile(String fileName) {
        if (!fileName.equalsIgnoreCase(":memory:") && fileName.trim().length() != 0) {
            Log.e(this.TAG, "deleting the database file: " + fileName);
            try {
                new File(fileName).delete();
            } catch (Exception e) {
                Log.w(this.TAG, "delete failed: " + e.getMessage());
            }
        }
    }
}
