package com.facebook.react.modules.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.facebook.common.logging.FLog;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactDatabaseSupplier extends SQLiteOpenHelper {
    private static ReactDatabaseSupplier sReactDatabaseSupplierInstance;
    private Context mContext;
    private SQLiteDatabase mDb;
    private long mMaximumDatabaseSize;

    private ReactDatabaseSupplier(Context context) {
        super(context, "RKStorage", (SQLiteDatabase.CursorFactory) null, 1);
        this.mMaximumDatabaseSize = 6291456L;
        this.mContext = context;
    }

    public static ReactDatabaseSupplier getInstance(Context context) {
        if (sReactDatabaseSupplierInstance == null) {
            sReactDatabaseSupplierInstance = new ReactDatabaseSupplier(context.getApplicationContext());
        }
        return sReactDatabaseSupplierInstance;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE catalystLocalStorage (key TEXT PRIMARY KEY, value TEXT NOT NULL)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            deleteDatabase();
            onCreate(db);
        }
    }

    synchronized boolean ensureDatabase() {
        if (this.mDb == null || !this.mDb.isOpen()) {
            SQLiteException lastSQLiteException = null;
            for (int tries = 0; tries < 2; tries++) {
                if (tries > 0) {
                    try {
                        deleteDatabase();
                    } catch (SQLiteException e) {
                        lastSQLiteException = e;
                        try {
                            Thread.sleep(30L);
                        } catch (InterruptedException e2) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                this.mDb = getWritableDatabase();
            }
            if (this.mDb == null) {
                throw lastSQLiteException;
            }
            this.mDb.setMaximumSize(this.mMaximumDatabaseSize);
        }
        return true;
    }

    public synchronized SQLiteDatabase get() {
        ensureDatabase();
        return this.mDb;
    }

    public synchronized void clearAndCloseDatabase() throws RuntimeException {
        try {
            clear();
            closeDatabase();
            FLog.d("React", "Cleaned RKStorage");
        } catch (Exception e) {
            if (deleteDatabase()) {
                FLog.d("React", "Deleted Local Database RKStorage");
            } else {
                throw new RuntimeException("Clearing and deleting database RKStorage failed");
            }
        }
    }

    synchronized void clear() {
        get().delete("catalystLocalStorage", null, null);
    }

    private synchronized boolean deleteDatabase() {
        closeDatabase();
        return this.mContext.deleteDatabase("RKStorage");
    }

    private synchronized void closeDatabase() {
        if (this.mDb != null && this.mDb.isOpen()) {
            this.mDb.close();
            this.mDb = null;
        }
    }
}
