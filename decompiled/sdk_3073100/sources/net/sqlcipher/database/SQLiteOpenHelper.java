package net.sqlcipher.database;

import android.content.Context;
import android.util.Log;
import java.io.File;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.DefaultDatabaseErrorHandler;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class SQLiteOpenHelper {
    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private final DatabaseErrorHandler mErrorHandler;
    private final SQLiteDatabase$CursorFactory mFactory;
    private final SQLiteDatabaseHook mHook;
    private boolean mIsInitializing;
    private final String mName;
    private final int mNewVersion;

    public abstract void onCreate(SQLiteDatabase sQLiteDatabase);

    public abstract void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2);

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase$CursorFactory factory, int version) {
        this(context, name, factory, version, null, new DefaultDatabaseErrorHandler());
    }

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase$CursorFactory factory, int version, SQLiteDatabaseHook hook) {
        this(context, name, factory, version, hook, new DefaultDatabaseErrorHandler());
    }

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase$CursorFactory factory, int version, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        this.mDatabase = null;
        this.mIsInitializing = false;
        if (version < 1) {
            throw new IllegalArgumentException("Version must be >= 1, was " + version);
        }
        if (errorHandler == null) {
            throw new IllegalArgumentException("DatabaseErrorHandler param value can't be null.");
        }
        this.mContext = context;
        this.mName = name;
        this.mFactory = factory;
        this.mNewVersion = version;
        this.mHook = hook;
        this.mErrorHandler = errorHandler;
    }

    public synchronized SQLiteDatabase getWritableDatabase(String password) {
        return getWritableDatabase(password == null ? null : password.toCharArray());
    }

    public synchronized SQLiteDatabase getWritableDatabase(char[] password) {
        SQLiteDatabase db;
        if (this.mDatabase != null && this.mDatabase.isOpen() && !this.mDatabase.isReadOnly()) {
            db = this.mDatabase;
        } else {
            if (this.mIsInitializing) {
                throw new IllegalStateException("getWritableDatabase called recursively");
            }
            db = null;
            if (this.mDatabase != null) {
                this.mDatabase.lock();
            }
            try {
                this.mIsInitializing = true;
                if (this.mName == null) {
                    db = SQLiteDatabase.create((SQLiteDatabase$CursorFactory) null, password);
                } else {
                    String path = this.mContext.getDatabasePath(this.mName).getPath();
                    File dbPathFile = new File(path);
                    if (!dbPathFile.exists()) {
                        dbPathFile.getParentFile().mkdirs();
                    }
                    db = SQLiteDatabase.openOrCreateDatabase(path, password, this.mFactory, this.mHook, this.mErrorHandler);
                }
                int version = db.getVersion();
                if (version != this.mNewVersion) {
                    db.beginTransaction();
                    try {
                        if (version == 0) {
                            onCreate(db);
                        } else {
                            onUpgrade(db, version, this.mNewVersion);
                        }
                        db.setVersion(this.mNewVersion);
                        db.setTransactionSuccessful();
                        db.endTransaction();
                    } catch (Throwable th) {
                        db.endTransaction();
                        throw th;
                    }
                }
                onOpen(db);
                this.mIsInitializing = false;
                if (1 != 0) {
                    if (this.mDatabase != null) {
                        try {
                            this.mDatabase.close();
                        } catch (Exception e) {
                        }
                        this.mDatabase.unlock();
                    }
                    this.mDatabase = db;
                } else {
                    if (this.mDatabase != null) {
                        this.mDatabase.unlock();
                    }
                    if (db != null) {
                        db.close();
                    }
                }
            } catch (Throwable th2) {
                this.mIsInitializing = false;
                if (0 != 0) {
                    if (this.mDatabase != null) {
                        try {
                            this.mDatabase.close();
                        } catch (Exception e2) {
                        }
                        this.mDatabase.unlock();
                    }
                    this.mDatabase = db;
                } else {
                    if (this.mDatabase != null) {
                        this.mDatabase.unlock();
                    }
                    if (db != null) {
                        db.close();
                    }
                }
                throw th2;
            }
        }
        return db;
    }

    public synchronized SQLiteDatabase getReadableDatabase(String password) {
        return getReadableDatabase(password == null ? null : password.toCharArray());
    }

    public synchronized SQLiteDatabase getReadableDatabase(char[] password) {
        SQLiteDatabase writableDatabase;
        if (this.mDatabase != null && this.mDatabase.isOpen()) {
            writableDatabase = this.mDatabase;
        } else {
            if (this.mIsInitializing) {
                throw new IllegalStateException("getReadableDatabase called recursively");
            }
            try {
                writableDatabase = getWritableDatabase(password);
            } catch (SQLiteException e) {
                if (this.mName == null) {
                    throw e;
                }
                Log.e(TAG, "Couldn't open " + this.mName + " for writing (will try read-only):", e);
                SQLiteDatabase db = null;
                try {
                    this.mIsInitializing = true;
                    String path = this.mContext.getDatabasePath(this.mName).getPath();
                    File databasePath = new File(path);
                    File databasesDirectory = new File(this.mContext.getDatabasePath(this.mName).getParent());
                    if (!databasesDirectory.exists()) {
                        databasesDirectory.mkdirs();
                    }
                    if (!databasePath.exists()) {
                        this.mIsInitializing = false;
                        SQLiteDatabase db2 = getWritableDatabase(password);
                        this.mIsInitializing = true;
                        db2.close();
                    }
                    SQLiteDatabase db3 = SQLiteDatabase.openDatabase(path, password, this.mFactory, 1);
                    if (db3.getVersion() != this.mNewVersion) {
                        throw new SQLiteException("Can't upgrade read-only database from version " + db3.getVersion() + " to " + this.mNewVersion + ": " + path);
                    }
                    onOpen(db3);
                    Log.w(TAG, "Opened " + this.mName + " in read-only mode");
                    this.mDatabase = db3;
                    writableDatabase = this.mDatabase;
                    this.mIsInitializing = false;
                    if (db3 != null && db3 != this.mDatabase) {
                        db3.close();
                    }
                } catch (Throwable th) {
                    this.mIsInitializing = false;
                    if (0 != 0 && null != this.mDatabase) {
                        db.close();
                    }
                    throw th;
                }
            }
        }
        return writableDatabase;
    }

    public synchronized void close() {
        if (this.mIsInitializing) {
            throw new IllegalStateException("Closed during initialization");
        }
        if (this.mDatabase != null && this.mDatabase.isOpen()) {
            this.mDatabase.close();
            this.mDatabase = null;
        }
    }

    public void onOpen(SQLiteDatabase db) {
    }
}
