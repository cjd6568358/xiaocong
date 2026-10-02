package net.sqlcipher.database;

import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import net.sqlcipher.AbstractWindowedCursor;
import net.sqlcipher.CursorWindow;
import net.sqlcipher.SQLException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SQLiteCursor extends AbstractWindowedCursor {
    static final int NO_COUNT = -1;
    static final String TAG = "Cursor";
    private String[] mColumns;
    private SQLiteDatabase mDatabase;
    private SQLiteCursorDriver mDriver;
    private String mEditTable;
    protected MainThreadNotificationHandler mNotificationHandler;
    private SQLiteQuery mQuery;
    private int mCount = NO_COUNT;
    private int mMaxRead = Integer.MAX_VALUE;
    private int mInitialRead = Integer.MAX_VALUE;
    private int mCursorState = 0;
    private ReentrantLock mLock = null;
    private boolean mPendingData = false;
    private Throwable mStackTrace = new DatabaseObjectNotClosedException().fillInStackTrace();
    private Map<String, Integer> mColumnNameMap = null;

    public void setLoadStyle(int initialRead, int maxRead) {
        this.mMaxRead = maxRead;
        this.mInitialRead = initialRead;
        this.mLock = new ReentrantLock(true);
    }

    private void queryThreadLock() {
        if (this.mLock != null) {
            this.mLock.lock();
        }
    }

    private void queryThreadUnlock() {
        if (this.mLock != null) {
            this.mLock.unlock();
        }
    }

    private final class QueryThread implements Runnable {
        private final int mThreadState;

        QueryThread(int version) {
            this.mThreadState = version;
        }

        private void sendMessage() {
            if (SQLiteCursor.this.mNotificationHandler == null) {
                SQLiteCursor.this.mPendingData = true;
            } else {
                SQLiteCursor.this.mNotificationHandler.sendEmptyMessage(1);
                SQLiteCursor.this.mPendingData = false;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            CursorWindow cw = SQLiteCursor.this.mWindow;
            Process.setThreadPriority(Process.myTid(), 10);
            while (true) {
                SQLiteCursor.this.mLock.lock();
                if (SQLiteCursor.this.mCursorState != this.mThreadState) {
                    SQLiteCursor.this.mLock.unlock();
                    return;
                }
                try {
                    int count = SQLiteCursor.this.mQuery.fillWindow(cw, SQLiteCursor.this.mMaxRead, SQLiteCursor.this.mCount);
                    if (count == 0) {
                        SQLiteCursor.this.mLock.unlock();
                        return;
                    }
                    if (count != SQLiteCursor.NO_COUNT) {
                        SQLiteCursor.this.mCount = count;
                        sendMessage();
                        SQLiteCursor.this.mLock.unlock();
                        return;
                    } else {
                        SQLiteCursor.this.mCount += SQLiteCursor.this.mMaxRead;
                        sendMessage();
                        SQLiteCursor.this.mLock.unlock();
                    }
                } catch (Exception e) {
                    SQLiteCursor.this.mLock.unlock();
                    return;
                } catch (Throwable th) {
                    SQLiteCursor.this.mLock.unlock();
                    throw th;
                }
            }
        }
    }

    protected static class MainThreadNotificationHandler extends Handler {
        private final WeakReference<SQLiteCursor> wrappedCursor;

        MainThreadNotificationHandler(SQLiteCursor cursor) {
            this.wrappedCursor = new WeakReference<>(cursor);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            SQLiteCursor cursor = this.wrappedCursor.get();
            if (cursor != null) {
                cursor.notifyDataSetChange();
            }
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
        if ((Integer.MAX_VALUE != this.mMaxRead || Integer.MAX_VALUE != this.mInitialRead) && this.mNotificationHandler == null) {
            queryThreadLock();
            try {
                this.mNotificationHandler = new MainThreadNotificationHandler(this);
                if (this.mPendingData) {
                    notifyDataSetChange();
                    this.mPendingData = false;
                }
            } finally {
                queryThreadUnlock();
            }
        }
    }

    public SQLiteCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
        this.mDatabase = db;
        this.mDriver = driver;
        this.mEditTable = editTable;
        this.mQuery = query;
        try {
            db.lock();
            int columnCount = this.mQuery.columnCountLocked();
            this.mColumns = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                String columnName = this.mQuery.columnNameLocked(i);
                this.mColumns[i] = columnName;
                if ("_id".equals(columnName)) {
                    this.mRowIdColumnIndex = i;
                }
            }
            db.unlock();
        } catch (Throwable th) {
            db.unlock();
            throw th;
        }
    }

    public SQLiteDatabase getDatabase() {
        return this.mDatabase;
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.CrossProcessCursor
    public boolean onMove(int oldPosition, int newPosition) {
        if (this.mWindow == null || newPosition < this.mWindow.getStartPosition() || newPosition >= this.mWindow.getStartPosition() + this.mWindow.getNumRows()) {
            fillWindow(newPosition);
            return true;
        }
        return true;
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public int getCount() {
        if (this.mCount == NO_COUNT) {
            fillWindow(0);
        }
        return this.mCount;
    }

    private void fillWindow(int startPos) {
        if (this.mWindow == null) {
            this.mWindow = new CursorWindow(true);
        } else {
            this.mCursorState++;
            queryThreadLock();
            try {
                this.mWindow.clear();
                queryThreadUnlock();
            } catch (Throwable th) {
                queryThreadUnlock();
                throw th;
            }
        }
        this.mWindow.setStartPosition(startPos);
        this.mCount = this.mQuery.fillWindow(this.mWindow, this.mInitialRead, 0);
        if (this.mCount == NO_COUNT) {
            this.mCount = this.mInitialRead + startPos;
            Thread t = new Thread(new QueryThread(this.mCursorState), "query thread");
            t.start();
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public int getColumnIndex(String columnName) {
        if (this.mColumnNameMap == null) {
            String[] columns = this.mColumns;
            int columnCount = columns.length;
            HashMap<String, Integer> map = new HashMap<>(columnCount, 1.0f);
            for (int i = 0; i < columnCount; i++) {
                map.put(columns[i], Integer.valueOf(i));
            }
            this.mColumnNameMap = map;
        }
        int periodIndex = columnName.lastIndexOf(46);
        if (periodIndex != NO_COUNT) {
            Exception e = new Exception();
            Log.e(TAG, "requesting column name with table name -- " + columnName, e);
            columnName = columnName.substring(periodIndex + 1);
        }
        Integer i2 = this.mColumnNameMap.get(columnName);
        return i2 != null ? i2.intValue() : NO_COUNT;
    }

    @Override // net.sqlcipher.AbstractCursor
    public boolean deleteRow() {
        boolean success;
        checkPosition();
        if (this.mRowIdColumnIndex == NO_COUNT || this.mCurrentRowID == null) {
            Log.e(TAG, "Could not delete row because either the row ID column is not available or ithas not been read.");
            return false;
        }
        this.mDatabase.lock();
        try {
            try {
                this.mDatabase.delete(this.mEditTable, this.mColumns[this.mRowIdColumnIndex] + "=?", new String[]{this.mCurrentRowID.toString()});
                success = true;
            } catch (SQLException e) {
                success = false;
            }
            int pos = this.mPos;
            requery();
            moveToPosition(pos);
            this.mDatabase.unlock();
            if (!success) {
                return false;
            }
            onChange(true);
            return true;
        } catch (Throwable th) {
            this.mDatabase.unlock();
            throw th;
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public String[] getColumnNames() {
        return this.mColumns;
    }

    @Override // net.sqlcipher.AbstractCursor
    public boolean supportsUpdates() {
        return !TextUtils.isEmpty(this.mEditTable);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0029  */
    /* JADX WARN: Code duplicated, block: B:18:0x002f A[Catch: all -> 0x002c, TRY_ENTER, TRY_LEAVE, TryCatch #1 {, blocks: (B:9:0x0016, B:10:0x001f, B:13:0x002a, B:18:0x002f, B:41:0x014c, B:42:0x015a, B:28:0x0089, B:29:0x0090, B:19:0x0036, B:20:0x0049, B:22:0x004f, B:30:0x0091, B:32:0x0097, B:33:0x00ce, B:35:0x00d4, B:37:0x00f4, B:38:0x00f9, B:39:0x00fc, B:25:0x0065, B:26:0x0087, B:40:0x0145), top: B:45:0x0016, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:22:0x004f A[Catch: all -> 0x0088, TryCatch #0 {all -> 0x0088, blocks: (B:19:0x0036, B:20:0x0049, B:22:0x004f, B:30:0x0091, B:32:0x0097, B:33:0x00ce, B:35:0x00d4, B:37:0x00f4, B:38:0x00f9, B:39:0x00fc, B:25:0x0065, B:26:0x0087, B:40:0x0145), top: B:44:0x0036, outer: #1 }] */
    @Override // net.sqlcipher.AbstractCursor
    public boolean commitUpdates(Map<? extends Long, ? extends Map<String, Object>> additionalValues) {
        Map<String, Object> values;
        Long rowIdObj;
        if (!supportsUpdates()) {
            Log.e(TAG, "commitUpdates not supported on this cursor, did you include the _id column?");
            return false;
        }
        synchronized (this.mUpdatedRows) {
            if (additionalValues != null) {
                this.mUpdatedRows.putAll(additionalValues);
                if (this.mUpdatedRows.size() == 0) {
                    return true;
                }
                this.mDatabase.beginTransaction();
                try {
                    StringBuilder sql = new StringBuilder(128);
                    for (Map.Entry<Long, Map<String, Object>> rowEntry : this.mUpdatedRows.entrySet()) {
                        values = rowEntry.getValue();
                        rowIdObj = rowEntry.getKey();
                        if (rowIdObj != null || values == null) {
                            throw new IllegalStateException("null rowId or values found! rowId = " + rowIdObj + ", values = " + values);
                        }
                        if (values.size() != 0) {
                            long rowId = rowIdObj.longValue();
                            Iterator<Map.Entry<String, Object>> valuesIter = values.entrySet().iterator();
                            sql.setLength(0);
                            sql.append("UPDATE " + this.mEditTable + " SET ");
                            Object[] bindings = new Object[values.size()];
                            int i = 0;
                            while (valuesIter.hasNext()) {
                                Map.Entry<String, Object> entry = valuesIter.next();
                                sql.append(entry.getKey());
                                sql.append("=?");
                                bindings[i] = entry.getValue();
                                if (valuesIter.hasNext()) {
                                    sql.append(", ");
                                }
                                i++;
                            }
                            sql.append(" WHERE " + this.mColumns[this.mRowIdColumnIndex] + '=' + rowId);
                            sql.append(';');
                            this.mDatabase.execSQL(sql.toString(), bindings);
                            this.mDatabase.rowUpdated(this.mEditTable, rowId);
                        }
                    }
                    this.mDatabase.setTransactionSuccessful();
                    this.mDatabase.endTransaction();
                    this.mUpdatedRows.clear();
                    onChange(true);
                    return true;
                } catch (Throwable th) {
                    this.mDatabase.endTransaction();
                    throw th;
                }
            }
            if (this.mUpdatedRows.size() == 0) {
                return true;
            }
            this.mDatabase.beginTransaction();
            StringBuilder sql2 = new StringBuilder(128);
            while (r14.hasNext()) {
                values = rowEntry.getValue();
                rowIdObj = rowEntry.getKey();
                if (rowIdObj != null) {
                }
                throw new IllegalStateException("null rowId or values found! rowId = " + rowIdObj + ", values = " + values);
            }
            this.mDatabase.setTransactionSuccessful();
            this.mDatabase.endTransaction();
            this.mUpdatedRows.clear();
            onChange(true);
            return true;
            throw th;
        }
    }

    private void deactivateCommon() {
        this.mCursorState = 0;
        if (this.mWindow != null) {
            this.mWindow.close();
            this.mWindow = null;
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public void deactivate() {
        super.deactivate();
        deactivateCommon();
        this.mDriver.cursorDeactivated();
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        super.close();
        deactivateCommon();
        this.mQuery.close();
        this.mDriver.cursorClosed();
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public boolean requery() {
        if (isClosed()) {
            return false;
        }
        this.mDatabase.lock();
        try {
            if (this.mWindow != null) {
                this.mWindow.clear();
            }
            this.mPos = NO_COUNT;
            this.mDriver.cursorRequeried(this);
            this.mCount = NO_COUNT;
            this.mCursorState++;
            queryThreadLock();
            try {
                this.mQuery.requery();
                queryThreadUnlock();
                this.mDatabase.unlock();
                return super.requery();
            } catch (Throwable th) {
                queryThreadUnlock();
                throw th;
            }
        } catch (Throwable th2) {
            this.mDatabase.unlock();
            throw th2;
        }
    }

    @Override // net.sqlcipher.AbstractWindowedCursor
    public void setWindow(CursorWindow window) {
        if (this.mWindow != null) {
            this.mCursorState++;
            queryThreadLock();
            try {
                this.mWindow.close();
                queryThreadUnlock();
                this.mCount = NO_COUNT;
            } catch (Throwable th) {
                queryThreadUnlock();
                throw th;
            }
        }
        this.mWindow = window;
    }

    public void setSelectionArguments(String[] selectionArgs) {
        this.mDriver.setBindArguments(selectionArgs);
    }

    @Override // net.sqlcipher.AbstractCursor
    protected void finalize() {
        try {
            if (this.mWindow != null) {
                int len = this.mQuery.mSql.length();
                StringBuilder sbAppend = new StringBuilder().append("Finalizing a Cursor that has not been deactivated or closed. database = ").append(this.mDatabase.getPath()).append(", table = ").append(this.mEditTable).append(", query = ");
                String str = this.mQuery.mSql;
                if (len > 100) {
                    len = 100;
                }
                Log.e(TAG, sbAppend.append(str.substring(0, len)).toString(), this.mStackTrace);
                close();
                SQLiteDebug.notifyActiveCursorFinalized();
            }
        } finally {
            super.finalize();
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.CrossProcessCursor
    public void fillWindow(int startPos, android.database.CursorWindow window) {
        if (this.mWindow == null) {
            this.mWindow = new CursorWindow(true);
        } else {
            this.mCursorState++;
            queryThreadLock();
            try {
                this.mWindow.clear();
                queryThreadUnlock();
            } catch (Throwable th) {
                queryThreadUnlock();
                throw th;
            }
        }
        this.mWindow.setStartPosition(startPos);
        this.mCount = this.mQuery.fillWindow(this.mWindow, this.mInitialRead, 0);
        if (this.mCount == NO_COUNT) {
            this.mCount = this.mInitialRead + startPos;
            Thread t = new Thread(new QueryThread(this.mCursorState), "query thread");
            t.start();
        }
    }
}
