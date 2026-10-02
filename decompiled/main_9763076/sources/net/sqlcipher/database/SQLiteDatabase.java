package net.sqlcipher.database;

import android.content.ContentValues;
import android.content.Context;
import android.os.Debug;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import bsh.ParserConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;
import net.sqlcipher.CrossProcessCursorWrapper;
import net.sqlcipher.Cursor;
import net.sqlcipher.CursorWrapper;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.DatabaseUtils;
import net.sqlcipher.DefaultDatabaseErrorHandler;
import net.sqlcipher.SQLException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SQLiteDatabase extends SQLiteClosable {
    private static final String COMMIT_SQL = "COMMIT;";
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    public static final int CREATE_IF_NECESSARY = 268435456;
    private static final int EVENT_DB_CORRUPT = 75004;
    private static final int EVENT_DB_OPERATION = 52000;
    static final String GET_LOCK_LOG_PREFIX = "GETLOCK:";
    private static final String KEY_ENCODING = "UTF-8";
    private static final int LOCK_ACQUIRED_WARNING_THREAD_TIME_IN_MS = 100;
    private static final int LOCK_ACQUIRED_WARNING_TIME_IN_MS = 300;
    private static final int LOCK_ACQUIRED_WARNING_TIME_IN_MS_ALWAYS_PRINT = 2000;
    private static final int LOCK_WARNING_WINDOW_IN_MS = 20000;
    private static final String LOG_SLOW_QUERIES_PROPERTY = "db.log.slow_query_threshold";
    public static final int MAX_SQL_CACHE_SIZE = 250;
    private static final int MAX_WARNINGS_ON_CACHESIZE_CONDITION = 1;
    public static final String MEMORY = ":memory:";
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    private static final int QUERY_LOG_SQL_LENGTH = 64;
    private static final int SLEEP_AFTER_YIELD_QUANTUM = 1000;
    public static final String SQLCIPHER_ANDROID_VERSION = "3.5.7";
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    private static final String TAG = "Database";
    private int mCacheFullWarnings;
    Map<String, SQLiteCompiledSql> mCompiledQueries;
    private final DatabaseErrorHandler mErrorHandler;
    private CursorFactory mFactory;
    private int mFlags;
    private boolean mInnerTransactionIsSuccessful;
    private long mLastLockMessageTime;
    private String mLastSqlStatement;
    private final ReentrantLock mLock;
    private long mLockAcquiredThreadTime;
    private long mLockAcquiredWallTime;
    private boolean mLockingEnabled;
    private int mMaxSqlCacheSize;
    long mNativeHandle;
    private int mNumCacheHits;
    private int mNumCacheMisses;
    private String mPath;
    private String mPathForLogs;
    private WeakHashMap<SQLiteClosable, Object> mPrograms;
    private final int mSlowQueryThreshold;
    private Throwable mStackTrace;
    private final Map<String, SyncUpdateInfo> mSyncUpdateInfo;
    int mTempTableSequence;
    private String mTimeClosed;
    private String mTimeOpened;
    private boolean mTransactionIsSuccessful;
    private SQLiteTransactionListener mTransactionListener;
    private static WeakHashMap<SQLiteDatabase, Object> sActiveDatabases = new WeakHashMap<>();
    private static final String[] CONFLICT_VALUES = {Constants.MAIN_VERSION_TAG, " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    private static final Pattern EMAIL_IN_DB_PATTERN = Pattern.compile("[\\w\\.\\-]+@[\\w\\.\\-]+");
    private static int sQueryLogTimeInMillis = 0;

    private native void dbclose();

    private native void dbopen(String str, int i);

    private native void enableSqlProfiling(String str);

    private native void enableSqlTracing(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native void key(byte[] bArr) throws SQLException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void key_mutf8(char[] cArr) throws SQLException;

    private native int native_getDbLookaside();

    private native void native_key(char[] cArr) throws SQLException;

    private native void native_rawExecSQL(String str);

    private native void native_rekey(String str) throws SQLException;

    private native int native_status(int i, boolean z);

    private native void rekey(byte[] bArr) throws SQLException;

    public static native int releaseMemory();

    public static native void setICURoot(String str);

    native int lastChangeCount();

    native long lastInsertRow();

    native void native_execSQL(String str) throws SQLException;

    native void native_setLocale(String str, int i);

    public int status(int operation, boolean reset) {
        return native_status(operation, reset);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.sqlcipher.SQLException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: net.sqlcipher.database.SQLiteException */
    public void changePassword(String password) throws SQLException, SQLiteException {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        }
        if (password != null) {
            byte[] keyMaterial = getBytes(password.toCharArray());
            rekey(keyMaterial);
            for (byte b : keyMaterial) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.sqlcipher.SQLException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: net.sqlcipher.database.SQLiteException */
    public void changePassword(char[] password) throws SQLException, SQLiteException {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        }
        if (password != null) {
            byte[] keyMaterial = getBytes(password);
            rekey(keyMaterial);
            for (byte b : keyMaterial) {
            }
        }
    }

    private static void loadICUData(Context context, File workingDir) throws Throwable {
        OutputStream out = null;
        ZipInputStream in = null;
        File icuDir = new File(workingDir, "icu");
        File icuDataFile = new File(icuDir, "icudt46l.dat");
        try {
            try {
                if (!icuDir.exists()) {
                    icuDir.mkdirs();
                }
                if (!icuDataFile.exists()) {
                    ZipInputStream in2 = new ZipInputStream(context.getAssets().open("icudt46l.zip"));
                    try {
                        in2.getNextEntry();
                        OutputStream out2 = new FileOutputStream(icuDataFile);
                        try {
                            byte[] buf = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                            while (true) {
                                int len = in2.read(buf);
                                if (len <= 0) {
                                    break;
                                } else {
                                    out2.write(buf, 0, len);
                                }
                            }
                            in = in2;
                            out = out2;
                        } catch (Exception e) {
                            ex = e;
                            in = in2;
                            out = out2;
                            Log.e(TAG, "Error copying icu dat file", ex);
                            if (icuDataFile.exists()) {
                                icuDataFile.delete();
                            }
                            throw new RuntimeException(ex);
                        } catch (Throwable th) {
                            th = th;
                            in = in2;
                            out = out2;
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException ioe) {
                                    Log.e(TAG, "Error in closing streams IO streams after expanding ICU dat file", ioe);
                                    throw new RuntimeException(ioe);
                                }
                            }
                            if (out != null) {
                                out.flush();
                                out.close();
                            }
                            throw th;
                        }
                    } catch (Exception e2) {
                        ex = e2;
                        in = in2;
                    } catch (Throwable th2) {
                        th = th2;
                        in = in2;
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ioe2) {
                        Log.e(TAG, "Error in closing streams IO streams after expanding ICU dat file", ioe2);
                        throw new RuntimeException(ioe2);
                    }
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e3) {
                ex = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static synchronized void loadLibs(Context context) {
        loadLibs(context, context.getFilesDir());
    }

    public static synchronized void loadLibs(Context context, File workingDir) {
        loadLibs(context, workingDir, new 1());
    }

    public static synchronized void loadLibs(Context context, LibraryLoader libraryLoader) {
        loadLibs(context, context.getFilesDir(), libraryLoader);
    }

    public static synchronized void loadLibs(Context context, File workingDir, LibraryLoader libraryLoader) {
        libraryLoader.loadLibraries(new String[]{"sqlcipher"});
    }

    void addSQLiteClosable(SQLiteClosable closable) {
        lock();
        try {
            this.mPrograms.put(closable, null);
        } finally {
            unlock();
        }
    }

    void removeSQLiteClosable(SQLiteClosable closable) {
        lock();
        try {
            this.mPrograms.remove(closable);
        } finally {
            unlock();
        }
    }

    protected void onAllReferencesReleased() {
        if (isOpen()) {
            if (SQLiteDebug.DEBUG_SQL_CACHE) {
                this.mTimeClosed = getTime();
            }
            dbclose();
            synchronized (sActiveDatabases) {
                sActiveDatabases.remove(this);
            }
        }
    }

    public void setLockingEnabled(boolean lockingEnabled) {
        this.mLockingEnabled = lockingEnabled;
    }

    void onCorruption() {
        Log.e(TAG, "Calling error handler for corrupt database (detected) " + this.mPath);
        this.mErrorHandler.onCorruption(this);
    }

    void lock() {
        if (this.mLockingEnabled) {
            this.mLock.lock();
            if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
                this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
                this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
            }
        }
    }

    private void lockForced() {
        this.mLock.lock();
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
            this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
            this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
        }
    }

    void unlock() {
        if (this.mLockingEnabled) {
            if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
                checkLockHoldTime();
            }
            this.mLock.unlock();
        }
    }

    private void unlockForced() {
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
            checkLockHoldTime();
        }
        this.mLock.unlock();
    }

    private void checkLockHoldTime() {
        long elapsedTime = SystemClock.elapsedRealtime();
        long lockedTime = elapsedTime - this.mLockAcquiredWallTime;
        if ((lockedTime >= 2000 || Log.isLoggable(TAG, 2) || elapsedTime - this.mLastLockMessageTime >= 20000) && lockedTime > 300) {
            int threadTime = (int) ((Debug.threadCpuTimeNanos() - this.mLockAcquiredThreadTime) / 1000000);
            if (threadTime > 100 || lockedTime > 2000) {
                this.mLastLockMessageTime = elapsedTime;
                String msg = "lock held on " + this.mPath + " for " + lockedTime + "ms. Thread time was " + threadTime + "ms";
                if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING_STACK_TRACE) {
                    Log.d(TAG, msg, new Exception());
                } else {
                    Log.d(TAG, msg);
                }
            }
        }
    }

    public void beginTransaction() {
        beginTransactionWithListener(null);
    }

    public void beginTransactionWithListener(SQLiteTransactionListener transactionListener) {
        lockForced();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            if (this.mLock.getHoldCount() > 1) {
                if (this.mInnerTransactionIsSuccessful) {
                    IllegalStateException e = new IllegalStateException("Cannot call beginTransaction between calling setTransactionSuccessful and endTransaction");
                    Log.e(TAG, "beginTransaction() failed", e);
                    throw e;
                }
                if (1 == 0) {
                    unlockForced();
                    return;
                }
                return;
            }
            execSQL("BEGIN EXCLUSIVE;");
            this.mTransactionListener = transactionListener;
            this.mTransactionIsSuccessful = true;
            this.mInnerTransactionIsSuccessful = false;
            if (transactionListener != null) {
                try {
                    transactionListener.onBegin();
                } catch (RuntimeException e2) {
                    execSQL("ROLLBACK;");
                    throw e2;
                }
            }
            if (1 == 0) {
                unlockForced();
            }
        } catch (Throwable th) {
            if (0 == 0) {
                unlockForced();
            }
            throw th;
        }
    }

    public void endTransaction() {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        if (!this.mLock.isHeldByCurrentThread()) {
            throw new IllegalStateException("no transaction pending");
        }
        try {
            if (this.mInnerTransactionIsSuccessful) {
                this.mInnerTransactionIsSuccessful = false;
            } else {
                this.mTransactionIsSuccessful = false;
            }
            if (this.mLock.getHoldCount() == 1) {
                RuntimeException savedException = null;
                if (this.mTransactionListener != null) {
                    try {
                        if (this.mTransactionIsSuccessful) {
                            this.mTransactionListener.onCommit();
                        } else {
                            this.mTransactionListener.onRollback();
                        }
                    } catch (RuntimeException e) {
                        savedException = e;
                        this.mTransactionIsSuccessful = false;
                    }
                }
                if (this.mTransactionIsSuccessful) {
                    execSQL(COMMIT_SQL);
                } else {
                    try {
                        execSQL("ROLLBACK;");
                        if (savedException != null) {
                            throw savedException;
                        }
                    } catch (SQLException e2) {
                        Log.d(TAG, "exception during rollback, maybe the DB previously performed an auto-rollback");
                    }
                }
            }
        } finally {
            this.mTransactionListener = null;
            unlockForced();
        }
    }

    public void setTransactionSuccessful() {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        if (!this.mLock.isHeldByCurrentThread()) {
            throw new IllegalStateException("no transaction pending");
        }
        if (this.mInnerTransactionIsSuccessful) {
            throw new IllegalStateException("setTransactionSuccessful may only be called once per call to beginTransaction");
        }
        this.mInnerTransactionIsSuccessful = true;
    }

    public boolean inTransaction() {
        return this.mLock.getHoldCount() > 0;
    }

    public boolean isDbLockedByCurrentThread() {
        return this.mLock.isHeldByCurrentThread();
    }

    public boolean isDbLockedByOtherThreads() {
        return !this.mLock.isHeldByCurrentThread() && this.mLock.isLocked();
    }

    @Deprecated
    public boolean yieldIfContended() {
        if (isOpen()) {
            return yieldIfContendedHelper(false, -1L);
        }
        return false;
    }

    public boolean yieldIfContendedSafely() {
        if (isOpen()) {
            return yieldIfContendedHelper(true, -1L);
        }
        return false;
    }

    public boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        if (isOpen()) {
            return yieldIfContendedHelper(true, sleepAfterYieldDelay);
        }
        return false;
    }

    private boolean yieldIfContendedHelper(boolean checkFullyYielded, long sleepAfterYieldDelay) {
        if (this.mLock.getQueueLength() == 0) {
            this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
            this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
            return false;
        }
        setTransactionSuccessful();
        SQLiteTransactionListener transactionListener = this.mTransactionListener;
        endTransaction();
        if (checkFullyYielded && isDbLockedByCurrentThread()) {
            throw new IllegalStateException("Db locked more than once. yielfIfContended cannot yield");
        }
        if (sleepAfterYieldDelay > 0) {
            long remainingDelay = sleepAfterYieldDelay;
            while (remainingDelay > 0) {
                try {
                    Thread.sleep(remainingDelay < 1000 ? remainingDelay : 1000L);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
                remainingDelay -= 1000;
                if (this.mLock.getQueueLength() == 0) {
                    break;
                }
            }
        }
        beginTransactionWithListener(transactionListener);
        return true;
    }

    public Map<String, String> getSyncedTables() {
        HashMap<String, String> tables;
        synchronized (this.mSyncUpdateInfo) {
            tables = new HashMap<>();
            for (String table : this.mSyncUpdateInfo.keySet()) {
                SyncUpdateInfo info = this.mSyncUpdateInfo.get(table);
                if (info.deletedTable != null) {
                    tables.put(table, info.deletedTable);
                }
            }
        }
        return tables;
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags) {
        return openDatabase(path, password, factory, flags, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags) {
        return openDatabase(path, password, factory, flags, (SQLiteDatabaseHook) null, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags, SQLiteDatabaseHook hook) {
        return openDatabase(path, password, factory, flags, hook, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook hook) {
        return openDatabase(path, password, factory, flags, hook, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, password == null ? null : password.toCharArray(), factory, flags, hook, errorHandler);
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        SQLiteDatabase sqliteDatabase;
        SQLiteDatabase sqliteDatabase2 = null;
        DatabaseErrorHandler myErrorHandler = errorHandler != null ? errorHandler : new DefaultDatabaseErrorHandler();
        try {
            SQLiteDatabase sqliteDatabase3 = new SQLiteDatabase(path, factory, flags, myErrorHandler);
            try {
                sqliteDatabase3.openDatabaseInternal(password, hook);
                sqliteDatabase = sqliteDatabase3;
            } catch (SQLiteDatabaseCorruptException e) {
                e = e;
                sqliteDatabase2 = sqliteDatabase3;
                Log.e(TAG, "Calling error handler for corrupt database " + path, e);
                myErrorHandler.onCorruption(sqliteDatabase2);
                sqliteDatabase = new SQLiteDatabase(path, factory, flags, myErrorHandler);
                sqliteDatabase.openDatabaseInternal(password, hook);
            }
        } catch (SQLiteDatabaseCorruptException e2) {
            e = e2;
        }
        if (SQLiteDebug.DEBUG_SQL_STATEMENTS) {
            sqliteDatabase.enableSqlTracing(path);
        }
        if (SQLiteDebug.DEBUG_SQL_TIME) {
            sqliteDatabase.enableSqlProfiling(path);
        }
        synchronized (sActiveDatabases) {
            sActiveDatabases.put(sqliteDatabase, null);
        }
        return sqliteDatabase;
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        return openOrCreateDatabase(file, password, factory, databaseHook, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        return openDatabase(path, password, factory, CREATE_IF_NECESSARY, databaseHook);
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(file == null ? null : file.getPath(), password, factory, databaseHook, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, password == null ? null : password.toCharArray(), factory, CREATE_IF_NECESSARY, databaseHook, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        return openDatabase(path, password, factory, CREATE_IF_NECESSARY, databaseHook);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, password, factory, CREATE_IF_NECESSARY, databaseHook, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory) {
        return openOrCreateDatabase(file, password, factory, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory) {
        return openDatabase(path, password, factory, CREATE_IF_NECESSARY, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory) {
        return openDatabase(path, password, factory, CREATE_IF_NECESSARY, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase create(CursorFactory factory, String password) {
        return openDatabase(MEMORY, password == null ? null : password.toCharArray(), factory, CREATE_IF_NECESSARY);
    }

    public static SQLiteDatabase create(CursorFactory factory, char[] password) {
        return openDatabase(MEMORY, password, factory, CREATE_IF_NECESSARY);
    }

    public void close() {
        if (isOpen()) {
            lock();
            try {
                closeClosable();
                onAllReferencesReleased();
            } finally {
                unlock();
            }
        }
    }

    private void closeClosable() {
        deallocCachedSqlStatements();
        for (Map.Entry<SQLiteClosable, Object> entry : this.mPrograms.entrySet()) {
            SQLiteClosable program = entry.getKey();
            if (program != null) {
                program.onAllReferencesReleasedFromContainer();
            }
        }
    }

    public int getVersion() throws Throwable {
        SQLiteStatement prog = null;
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA user_version;");
            try {
                long version = prog2.simpleQueryForLong();
                int i = (int) version;
                if (prog2 != null) {
                    prog2.close();
                }
                unlock();
                return i;
            } catch (Throwable th) {
                th = th;
                prog = prog2;
                if (prog != null) {
                    prog.close();
                }
                unlock();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.sqlcipher.SQLException */
    public void setVersion(int version) throws SQLException {
        execSQL("PRAGMA user_version = " + version);
    }

    public long getMaximumSize() throws Throwable {
        SQLiteStatement prog = null;
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA max_page_count;");
            try {
                long pageCount = prog2.simpleQueryForLong();
                long pageSize = getPageSize() * pageCount;
                if (prog2 != null) {
                    prog2.close();
                }
                unlock();
                return pageSize;
            } catch (Throwable th) {
                th = th;
                prog = prog2;
                if (prog != null) {
                    prog.close();
                }
                unlock();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public long setMaximumSize(long numBytes) throws Throwable {
        SQLiteStatement prog = null;
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            long pageSize = getPageSize();
            long numPages = numBytes / pageSize;
            if (numBytes % pageSize != 0) {
                numPages++;
            }
            SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA max_page_count = " + numPages);
            try {
                long newPageCount = prog2.simpleQueryForLong();
                long j = newPageCount * pageSize;
                if (prog2 != null) {
                    prog2.close();
                }
                unlock();
                return j;
            } catch (Throwable th) {
                th = th;
                prog = prog2;
                if (prog != null) {
                    prog.close();
                }
                unlock();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public long getPageSize() throws Throwable {
        SQLiteStatement prog = null;
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA page_size;");
            try {
                long size = prog2.simpleQueryForLong();
                if (prog2 != null) {
                    prog2.close();
                }
                unlock();
                return size;
            } catch (Throwable th) {
                th = th;
                prog = prog2;
                if (prog != null) {
                    prog.close();
                }
                unlock();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.sqlcipher.SQLException */
    public void setPageSize(long numBytes) throws SQLException {
        execSQL("PRAGMA page_size = " + numBytes);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.sqlcipher.database.SQLiteException */
    public void markTableSyncable(String table, String deletedTable) throws SQLiteException {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        }
        markTableSyncable(table, "_id", table, deletedTable);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.sqlcipher.database.SQLiteException */
    public void markTableSyncable(String table, String foreignKey, String updateTable) throws SQLiteException {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        }
        markTableSyncable(table, foreignKey, updateTable, null);
    }

    private void markTableSyncable(String table, String foreignKey, String updateTable, String deletedTable) {
        lock();
        try {
            native_execSQL("SELECT _sync_dirty FROM " + updateTable + " LIMIT 0");
            native_execSQL("SELECT " + foreignKey + " FROM " + table + " LIMIT 0");
            unlock();
            SyncUpdateInfo info = new SyncUpdateInfo(updateTable, deletedTable, foreignKey);
            synchronized (this.mSyncUpdateInfo) {
                this.mSyncUpdateInfo.put(table, info);
            }
        } catch (Throwable th) {
            unlock();
            throw th;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.sqlcipher.SQLException */
    void rowUpdated(String table, long rowId) throws SQLException {
        SyncUpdateInfo info;
        synchronized (this.mSyncUpdateInfo) {
            info = this.mSyncUpdateInfo.get(table);
        }
        if (info != null) {
            execSQL("UPDATE " + info.masterTable + " SET _sync_dirty=1 WHERE _id=(SELECT " + info.foreignKey + " FROM " + table + " WHERE _id=" + rowId + ")");
        }
    }

    public static String findEditTable(String tables) {
        if (!TextUtils.isEmpty(tables)) {
            int spacepos = tables.indexOf(32);
            int commapos = tables.indexOf(44);
            if (spacepos > 0 && (spacepos < commapos || commapos < 0)) {
                return tables.substring(0, spacepos);
            }
            if (commapos <= 0) {
                return tables;
            }
            if (commapos < spacepos || spacepos < 0) {
                return tables.substring(0, commapos);
            }
            return tables;
        }
        throw new IllegalStateException("Invalid tables");
    }

    public SQLiteStatement compileStatement(String sql) throws SQLException {
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        try {
            return new SQLiteStatement(this, sql);
        } finally {
            unlock();
        }
    }

    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return queryWithFactory(null, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        String sql = SQLiteQueryBuilder.buildQueryString(distinct, table, columns, selection, groupBy, having, orderBy, limit);
        return rawQueryWithFactory(cursorFactory, sql, selectionArgs, findEditTable(table));
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, null);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return rawQueryWithFactory(null, sql, selectionArgs, null);
    }

    public Cursor rawQuery(String sql, Object[] args) {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        long timeStart = this.mSlowQueryThreshold != -1 ? System.currentTimeMillis() : 0L;
        SQLiteDirectCursorDriver driver = new SQLiteDirectCursorDriver(this, sql, (String) null);
        Cursor cursor = null;
        try {
            cursor = driver.query(this.mFactory, args);
            return new CrossProcessCursorWrapper(cursor);
        } finally {
            if (this.mSlowQueryThreshold != -1) {
                int count = cursor != null ? cursor.getCount() : -1;
                long duration = System.currentTimeMillis() - timeStart;
                if (duration >= this.mSlowQueryThreshold) {
                    Log.v(TAG, "query (" + duration + " ms): " + driver.toString() + ", args are <redacted>, count is " + count);
                }
            }
        }
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable) {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        long timeStart = this.mSlowQueryThreshold != -1 ? System.currentTimeMillis() : 0L;
        SQLiteDirectCursorDriver sQLiteDirectCursorDriver = new SQLiteDirectCursorDriver(this, sql, editTable);
        Cursor cursor = null;
        if (cursorFactory == null) {
            try {
                cursorFactory = this.mFactory;
            } finally {
                if (this.mSlowQueryThreshold != -1) {
                    int count = cursor != null ? cursor.getCount() : -1;
                    long duration = System.currentTimeMillis() - timeStart;
                    if (duration >= this.mSlowQueryThreshold) {
                        Log.v(TAG, "query (" + duration + " ms): " + sQLiteDirectCursorDriver.toString() + ", args are <redacted>, count is " + count);
                    }
                }
            }
        }
        cursor = sQLiteDirectCursorDriver.query(cursorFactory, selectionArgs);
        return new CrossProcessCursorWrapper(cursor);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs, int initialRead, int maxRead) {
        CursorWrapper cursorWrapper = rawQueryWithFactory(null, sql, selectionArgs, null);
        cursorWrapper.getWrappedCursor().setLoadStyle(initialRead, maxRead);
        return cursorWrapper;
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        try {
            return insertWithOnConflict(table, nullColumnHack, values, 0);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting <redacted values> into " + table, e);
            return -1L;
        }
    }

    public long insertOrThrow(String table, String nullColumnHack, ContentValues values) throws SQLException {
        return insertWithOnConflict(table, nullColumnHack, values, 0);
    }

    public long replace(String table, String nullColumnHack, ContentValues initialValues) {
        try {
            return insertWithOnConflict(table, nullColumnHack, initialValues, 5);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting <redacted values> into " + table, e);
            return -1L;
        }
    }

    public long replaceOrThrow(String table, String nullColumnHack, ContentValues initialValues) throws SQLException {
        return insertWithOnConflict(table, nullColumnHack, initialValues, 5);
    }

    public long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        StringBuilder sql = new StringBuilder(152);
        sql.append("INSERT");
        sql.append(CONFLICT_VALUES[conflictAlgorithm]);
        sql.append(" INTO ");
        sql.append(table);
        StringBuilder values = new StringBuilder(40);
        Set<Map.Entry<String, Object>> entrySet = null;
        if (initialValues != null && initialValues.size() > 0) {
            entrySet = initialValues.valueSet();
            sql.append('(');
            boolean needSeparator = false;
            for (Map.Entry<String, Object> entry : entrySet) {
                if (needSeparator) {
                    sql.append(", ");
                    values.append(", ");
                }
                needSeparator = true;
                sql.append(entry.getKey());
                values.append('?');
            }
            sql.append(')');
        } else {
            sql.append("(" + nullColumnHack + ") ");
            values.append("NULL");
        }
        sql.append(" VALUES(");
        sql.append((CharSequence) values);
        sql.append(");");
        lock();
        SQLiteStatement statement = null;
        try {
            try {
                SQLiteStatement statement2 = compileStatement(sql.toString());
                if (entrySet != null) {
                    int size = entrySet.size();
                    Iterator<Map.Entry<String, Object>> entriesIter = entrySet.iterator();
                    for (int i = 0; i < size; i++) {
                        Map.Entry<String, Object> entry2 = entriesIter.next();
                        DatabaseUtils.bindObjectToProgram(statement2, i + 1, entry2.getValue());
                    }
                }
                statement2.execute();
                long insertedRowId = lastChangeCount() > 0 ? lastInsertRow() : -1L;
                if (insertedRowId == -1) {
                    Log.e(TAG, "Error inserting <redacted values> using <redacted sql> into " + table);
                } else if (Log.isLoggable(TAG, 2)) {
                    Log.v(TAG, "Inserting row " + insertedRowId + " from <redacted values> using <redacted sql> into " + table);
                }
                if (statement2 != null) {
                    statement2.close();
                }
                unlock();
                return insertedRowId;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                statement.close();
            }
            unlock();
            throw th;
        }
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        SQLiteStatement statement = null;
        try {
            try {
                statement = compileStatement("DELETE FROM " + table + (!TextUtils.isEmpty(whereClause) ? " WHERE " + whereClause : Constants.MAIN_VERSION_TAG));
                if (whereArgs != null) {
                    int numArgs = whereArgs.length;
                    for (int i = 0; i < numArgs; i++) {
                        DatabaseUtils.bindObjectToProgram(statement, i + 1, whereArgs[i]);
                    }
                }
                statement.execute();
                int iLastChangeCount = lastChangeCount();
                if (statement != null) {
                    statement.close();
                }
                unlock();
                return iLastChangeCount;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            }
        } catch (Throwable th) {
            if (statement != null) {
                statement.close();
            }
            unlock();
            throw th;
        }
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return updateWithOnConflict(table, values, whereClause, whereArgs, 0);
    }

    public int updateWithOnConflict(String table, ContentValues values, String whereClause, String[] whereArgs, int conflictAlgorithm) {
        if (values == null || values.size() == 0) {
            throw new IllegalArgumentException("Empty values");
        }
        StringBuilder sql = new StringBuilder(ParserConstants.STARASSIGN);
        sql.append("UPDATE ");
        sql.append(CONFLICT_VALUES[conflictAlgorithm]);
        sql.append(table);
        sql.append(" SET ");
        Set<Map.Entry<String, Object>> entrySet = values.valueSet();
        Iterator<Map.Entry<String, Object>> entriesIter = entrySet.iterator();
        while (entriesIter.hasNext()) {
            Map.Entry<String, Object> entry = entriesIter.next();
            sql.append(entry.getKey());
            sql.append("=?");
            if (entriesIter.hasNext()) {
                sql.append(", ");
            }
        }
        if (!TextUtils.isEmpty(whereClause)) {
            sql.append(" WHERE ");
            sql.append(whereClause);
        }
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        SQLiteStatement statement = null;
        try {
            try {
                statement = compileStatement(sql.toString());
                int size = entrySet.size();
                Iterator<Map.Entry<String, Object>> entriesIter2 = entrySet.iterator();
                int bindArg = 1;
                for (int i = 0; i < size; i++) {
                    Map.Entry<String, Object> entry2 = entriesIter2.next();
                    DatabaseUtils.bindObjectToProgram(statement, bindArg, entry2.getValue());
                    bindArg++;
                }
                if (whereArgs != null) {
                    for (String str : whereArgs) {
                        statement.bindString(bindArg, str);
                        bindArg++;
                    }
                }
                statement.execute();
                int numChangedRows = lastChangeCount();
                if (Log.isLoggable(TAG, 2)) {
                    Log.v(TAG, "Updated " + numChangedRows + " rows using <redacted values> and <redacted sql> for " + table);
                }
                if (statement != null) {
                    statement.close();
                }
                unlock();
                return numChangedRows;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (SQLException e2) {
                Log.e(TAG, "Error updating <redacted values> using <redacted sql> for " + table);
                throw e2;
            }
        } catch (Throwable th) {
            if (statement != null) {
                statement.close();
            }
            unlock();
            throw th;
        }
    }

    public void execSQL(String sql) throws SQLException {
        SystemClock.uptimeMillis();
        lock();
        try {
            if (!isOpen()) {
                throw new IllegalStateException("database not open");
            }
            try {
                native_execSQL(sql);
                unlock();
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            }
        } catch (Throwable th) {
            unlock();
            throw th;
        }
    }

    public void rawExecSQL(String sql) {
        SystemClock.uptimeMillis();
        lock();
        try {
            if (!isOpen()) {
                throw new IllegalStateException("database not open");
            }
            try {
                native_rawExecSQL(sql);
                unlock();
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            }
        } catch (Throwable th) {
            unlock();
            throw th;
        }
    }

    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        if (bindArgs == null) {
            throw new IllegalArgumentException("Empty bindArgs");
        }
        SystemClock.uptimeMillis();
        lock();
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        }
        SQLiteStatement statement = null;
        try {
            try {
                statement = compileStatement(sql);
                if (bindArgs != null) {
                    int numArgs = bindArgs.length;
                    for (int i = 0; i < numArgs; i++) {
                        DatabaseUtils.bindObjectToProgram(statement, i + 1, bindArgs[i]);
                    }
                }
                statement.execute();
                if (statement != null) {
                    statement.close();
                }
                unlock();
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            }
        } catch (Throwable th) {
            if (statement != null) {
                statement.close();
            }
            unlock();
            throw th;
        }
    }

    protected void finalize() {
        if (isOpen()) {
            Log.e(TAG, "close() was never explicitly called on database '" + this.mPath + "' ", this.mStackTrace);
            closeClosable();
            onAllReferencesReleased();
        }
    }

    public SQLiteDatabase(String path, char[] password, CursorFactory factory, int flags) {
        this(path, factory, flags, (DatabaseErrorHandler) null);
        openDatabaseInternal(password, null);
    }

    public SQLiteDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook databaseHook) {
        this(path, factory, flags, (DatabaseErrorHandler) null);
        openDatabaseInternal(password, databaseHook);
    }

    private SQLiteDatabase(String path, CursorFactory factory, int flags, DatabaseErrorHandler errorHandler) {
        this.mLock = new ReentrantLock(true);
        this.mLockAcquiredWallTime = 0L;
        this.mLockAcquiredThreadTime = 0L;
        this.mLastLockMessageTime = 0L;
        this.mLastSqlStatement = null;
        this.mNativeHandle = 0L;
        this.mTempTableSequence = 0;
        this.mPathForLogs = null;
        this.mCompiledQueries = new HashMap();
        this.mMaxSqlCacheSize = MAX_SQL_CACHE_SIZE;
        this.mTimeOpened = null;
        this.mTimeClosed = null;
        this.mStackTrace = null;
        this.mLockingEnabled = true;
        this.mSyncUpdateInfo = new HashMap();
        if (path == null) {
            throw new IllegalArgumentException("path should not be null");
        }
        this.mFlags = flags;
        this.mPath = path;
        this.mSlowQueryThreshold = -1;
        this.mStackTrace = new DatabaseObjectNotClosedException().fillInStackTrace();
        this.mFactory = factory;
        this.mPrograms = new WeakHashMap<>();
        this.mErrorHandler = errorHandler;
    }

    private void openDatabaseInternal(char[] password, SQLiteDatabaseHook hook) {
        int i = 0;
        byte[] keyMaterial = getBytes(password);
        dbopen(this.mPath, this.mFlags);
        try {
            try {
                keyDatabase(hook, new 2(this, keyMaterial));
                if (0 != 0) {
                    dbclose();
                    if (SQLiteDebug.DEBUG_SQL_CACHE) {
                        this.mTimeClosed = getTime();
                    }
                }
                if (keyMaterial != null && keyMaterial.length > 0) {
                    int length = keyMaterial.length;
                    while (i < length) {
                        byte b = keyMaterial[i];
                        i++;
                    }
                }
            } catch (RuntimeException ex) {
                if (containsNull(password)) {
                    keyDatabase(hook, new 3(this, password));
                    if (keyMaterial != null && keyMaterial.length > 0) {
                        rekey(keyMaterial);
                    }
                    if (0 != 0) {
                        dbclose();
                        if (SQLiteDebug.DEBUG_SQL_CACHE) {
                            this.mTimeClosed = getTime();
                        }
                    }
                    if (keyMaterial != null && keyMaterial.length > 0) {
                        int length2 = keyMaterial.length;
                        while (i < length2) {
                            byte b2 = keyMaterial[i];
                            i++;
                        }
                        return;
                    }
                    return;
                }
                throw ex;
            }
        } catch (Throwable th) {
            if (1 != 0) {
                dbclose();
                if (SQLiteDebug.DEBUG_SQL_CACHE) {
                    this.mTimeClosed = getTime();
                }
            }
            if (keyMaterial != null && keyMaterial.length > 0) {
                int length3 = keyMaterial.length;
                while (i < length3) {
                    byte b3 = keyMaterial[i];
                    i++;
                }
            }
            throw th;
        }
    }

    private boolean containsNull(char[] data) {
        if (data == null || data.length <= 0) {
            return false;
        }
        for (char datum : data) {
            if (datum == 0) {
                return true;
            }
        }
        return false;
    }

    private void keyDatabase(SQLiteDatabaseHook databaseHook, Runnable keyOperation) {
        if (databaseHook != null) {
            databaseHook.preKey(this);
        }
        if (keyOperation != null) {
            keyOperation.run();
        }
        if (databaseHook != null) {
            databaseHook.postKey(this);
        }
        if (SQLiteDebug.DEBUG_SQL_CACHE) {
            this.mTimeOpened = getTime();
        }
        try {
            Cursor cursor = rawQuery("select count(*) from sqlite_master;", new String[0]);
            if (cursor != null) {
                cursor.moveToFirst();
                cursor.getInt(0);
                cursor.close();
            }
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage(), e);
            throw e;
        }
    }

    private String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ", Locale.US).format(Long.valueOf(System.currentTimeMillis()));
    }

    public boolean isReadOnly() {
        return (this.mFlags & 1) == 1;
    }

    public boolean isOpen() {
        return this.mNativeHandle != 0;
    }

    public boolean needUpgrade(int newVersion) {
        return newVersion > getVersion();
    }

    public final String getPath() {
        return this.mPath;
    }

    private String getPathForLogs() {
        if (this.mPathForLogs != null) {
            return this.mPathForLogs;
        }
        if (this.mPath == null) {
            return null;
        }
        if (this.mPath.indexOf(64) == -1) {
            this.mPathForLogs = this.mPath;
        } else {
            this.mPathForLogs = EMAIL_IN_DB_PATTERN.matcher(this.mPath).replaceAll("XX@YY");
        }
        return this.mPathForLogs;
    }

    public void setLocale(Locale locale) {
        lock();
        try {
            native_setLocale(locale.toString(), this.mFlags);
        } finally {
            unlock();
        }
    }

    void addToCompiledQueries(String sql, SQLiteCompiledSql compiledStatement) {
        if (this.mMaxSqlCacheSize == 0) {
            if (SQLiteDebug.DEBUG_SQL_CACHE) {
                Log.v(TAG, "|NOT adding_sql_to_cache|" + getPath() + "|" + sql);
                return;
            }
            return;
        }
        synchronized (this.mCompiledQueries) {
            SQLiteCompiledSql compiledSql = this.mCompiledQueries.get(sql);
            if (compiledSql == null) {
                if (this.mCompiledQueries.size() == this.mMaxSqlCacheSize) {
                    int i = this.mCacheFullWarnings + 1;
                    this.mCacheFullWarnings = i;
                    if (i == 1) {
                        Log.w(TAG, "Reached MAX size for compiled-sql statement cache for database " + getPath() + "; i.e., NO space for this sql statement in cache: " + sql + ". Please change your sql statements to use '?' for bindargs, instead of using actual values");
                    }
                } else {
                    this.mCompiledQueries.put(sql, compiledStatement);
                    if (SQLiteDebug.DEBUG_SQL_CACHE) {
                        Log.v(TAG, "|adding_sql_to_cache|" + getPath() + "|" + this.mCompiledQueries.size() + "|" + sql);
                    }
                }
            }
        }
    }

    private void deallocCachedSqlStatements() {
        synchronized (this.mCompiledQueries) {
            for (SQLiteCompiledSql compiledSql : this.mCompiledQueries.values()) {
                compiledSql.releaseSqlStatement();
            }
            this.mCompiledQueries.clear();
        }
    }

    SQLiteCompiledSql getCompiledStatementForSql(String sql) {
        synchronized (this.mCompiledQueries) {
            if (this.mMaxSqlCacheSize == 0) {
                if (SQLiteDebug.DEBUG_SQL_CACHE) {
                    Log.v(TAG, "|cache NOT found|" + getPath());
                }
                return null;
            }
            SQLiteCompiledSql compiledStatement = this.mCompiledQueries.get(sql);
            boolean cacheHit = compiledStatement != null;
            if (cacheHit) {
                this.mNumCacheHits++;
            } else {
                this.mNumCacheMisses++;
            }
            if (SQLiteDebug.DEBUG_SQL_CACHE) {
                Log.v(TAG, "|cache_stats|" + getPath() + "|" + this.mCompiledQueries.size() + "|" + this.mNumCacheHits + "|" + this.mNumCacheMisses + "|" + cacheHit + "|" + this.mTimeOpened + "|" + this.mTimeClosed + "|" + sql);
            }
            return compiledStatement;
        }
    }

    public boolean isInCompiledSqlCache(String sql) {
        boolean zContainsKey;
        synchronized (this.mCompiledQueries) {
            zContainsKey = this.mCompiledQueries.containsKey(sql);
        }
        return zContainsKey;
    }

    public void purgeFromCompiledSqlCache(String sql) {
        synchronized (this.mCompiledQueries) {
            this.mCompiledQueries.remove(sql);
        }
    }

    public void resetCompiledSqlCache() {
        synchronized (this.mCompiledQueries) {
            this.mCompiledQueries.clear();
        }
    }

    public synchronized int getMaxSqlCacheSize() {
        return this.mMaxSqlCacheSize;
    }

    public synchronized void setMaxSqlCacheSize(int cacheSize) {
        try {
            if (cacheSize > 250 || cacheSize < 0) {
                throw new IllegalStateException("expected value between 0 and 250");
            }
            if (cacheSize < this.mMaxSqlCacheSize) {
                throw new IllegalStateException("cannot set cacheSize to a value less than the value set with previous setMaxSqlCacheSize() call.");
            }
            this.mMaxSqlCacheSize = cacheSize;
        } catch (Throwable th) {
            throw th;
        }
    }

    static ArrayList<SQLiteDebug.DbStats> getDbStats() throws Throwable {
        String dbName;
        ArrayList<SQLiteDebug.DbStats> dbStatsList = new ArrayList<>();
        for (SQLiteDatabase db : getActiveDatabases()) {
            if (db != null && db.isOpen()) {
                int lookasideUsed = db.native_getDbLookaside();
                String path = db.getPath();
                int indx = path.lastIndexOf("/");
                String lastnode = path.substring(indx != -1 ? indx + 1 : 0);
                ArrayList<Pair<String, String>> attachedDbs = getAttachedDbs(db);
                if (attachedDbs != null) {
                    for (int i = 0; i < attachedDbs.size(); i++) {
                        Pair<String, String> p = attachedDbs.get(i);
                        long pageCount = getPragmaVal(db, ((String) p.first) + ".page_count;");
                        if (i == 0) {
                            dbName = lastnode;
                        } else {
                            lookasideUsed = 0;
                            dbName = "  (attached) " + ((String) p.first);
                            if (((String) p.second).trim().length() > 0) {
                                int idx = ((String) p.second).lastIndexOf("/");
                                dbName = dbName + " : " + ((String) p.second).substring(idx != -1 ? idx + 1 : 0);
                            }
                        }
                        if (pageCount > 0) {
                            dbStatsList.add(new SQLiteDebug.DbStats(dbName, pageCount, db.getPageSize(), lookasideUsed));
                        }
                    }
                }
            }
        }
        return dbStatsList;
    }

    private static ArrayList<SQLiteDatabase> getActiveDatabases() {
        ArrayList<SQLiteDatabase> databases = new ArrayList<>();
        synchronized (sActiveDatabases) {
            databases.addAll(sActiveDatabases.keySet());
        }
        return databases;
    }

    private static long getPragmaVal(SQLiteDatabase db, String pragma) throws Throwable {
        if (!db.isOpen()) {
            return 0L;
        }
        SQLiteStatement prog = null;
        try {
            SQLiteStatement prog2 = new SQLiteStatement(db, "PRAGMA " + pragma);
            try {
                long jSimpleQueryForLong = prog2.simpleQueryForLong();
                if (prog2 != null) {
                    prog2.close();
                    return jSimpleQueryForLong;
                }
                return jSimpleQueryForLong;
            } catch (Throwable th) {
                th = th;
                prog = prog2;
                if (prog != null) {
                    prog.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static ArrayList<Pair<String, String>> getAttachedDbs(SQLiteDatabase dbObj) {
        if (!dbObj.isOpen()) {
            return null;
        }
        ArrayList<Pair<String, String>> attachedDbs = new ArrayList<>();
        Cursor c = dbObj.rawQuery("pragma database_list;", (String[]) null);
        while (c.moveToNext()) {
            attachedDbs.add(new Pair<>(c.getString(1), c.getString(2)));
        }
        c.close();
        return attachedDbs;
    }

    private byte[] getBytes(char[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        CharBuffer charBuffer = CharBuffer.wrap(data);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] result = new byte[byteBuffer.limit()];
        byteBuffer.get(result);
        return result;
    }
}
