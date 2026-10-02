package net.sqlcipher.database;

import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class SQLiteDebug {
    public static final boolean DEBUG_SQL_STATEMENTS = Log.isLoggable("SQLiteStatements", 2);
    public static final boolean DEBUG_SQL_TIME = Log.isLoggable("SQLiteTime", 2);
    public static final boolean DEBUG_SQL_CACHE = Log.isLoggable("SQLiteCompiledSql", 2);
    public static final boolean DEBUG_ACTIVE_CURSOR_FINALIZATION = Log.isLoggable("SQLiteCursorClosing", 2);
    public static final boolean DEBUG_LOCK_TIME_TRACKING = Log.isLoggable("SQLiteLockTime", 2);
    public static final boolean DEBUG_LOCK_TIME_TRACKING_STACK_TRACE = Log.isLoggable("SQLiteLockStackTrace", 2);
    private static int sNumActiveCursorsFinalized = 0;

    public static native long getHeapAllocatedSize();

    public static native void getHeapDirtyPages(int[] iArr);

    public static native long getHeapFreeSize();

    public static native long getHeapSize();

    public static native void getPagerStats(PagerStats pagerStats);

    public static class DbStats {
        public String dbName;
        public long dbSize;
        public int lookaside;
        public long pageSize;

        public DbStats(String dbName, long pageCount, long pageSize, int lookaside) {
            this.dbName = dbName;
            this.pageSize = pageSize;
            this.dbSize = (pageCount * pageSize) / 1024;
            this.lookaside = lookaside;
        }
    }

    public static PagerStats getDatabaseInfo() {
        PagerStats stats = new PagerStats();
        getPagerStats(stats);
        stats.dbStats = SQLiteDatabase.getDbStats();
        return stats;
    }

    public static int getNumActiveCursorsFinalized() {
        return sNumActiveCursorsFinalized;
    }

    static synchronized void notifyActiveCursorFinalized() {
        sNumActiveCursorsFinalized++;
    }
}
