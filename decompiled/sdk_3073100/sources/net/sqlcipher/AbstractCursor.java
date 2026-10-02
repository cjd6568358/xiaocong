package net.sqlcipher;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.CrossProcessCursor;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class AbstractCursor implements CrossProcessCursor, Cursor {
    private static final String TAG = "Cursor";
    protected ContentResolver mContentResolver;
    private Uri mNotifyUri;
    private ContentObserver mSelfObserver;
    private boolean mSelfObserverRegistered;
    DataSetObservable mDataSetObservable = new DataSetObservable();
    ContentObservable mContentObservable = new ContentObservable();
    private Bundle mExtras = Bundle.EMPTY;
    protected boolean mClosed = false;
    private final Object mSelfObserverLock = new Object();
    protected int mPos = -1;
    protected int mRowIdColumnIndex = -1;
    protected Long mCurrentRowID = null;
    protected HashMap<Long, Map<String, Object>> mUpdatedRows = new HashMap<>();

    @Override // android.database.Cursor
    public abstract String[] getColumnNames();

    @Override // android.database.Cursor
    public abstract int getCount();

    @Override // android.database.Cursor
    public abstract double getDouble(int i);

    @Override // android.database.Cursor
    public abstract float getFloat(int i);

    @Override // android.database.Cursor
    public abstract int getInt(int i);

    @Override // android.database.Cursor
    public abstract long getLong(int i);

    @Override // android.database.Cursor
    public abstract short getShort(int i);

    @Override // android.database.Cursor
    public abstract String getString(int i);

    @Override // android.database.Cursor, net.sqlcipher.Cursor
    public abstract int getType(int i);

    @Override // android.database.Cursor
    public abstract boolean isNull(int i);

    @Override // android.database.Cursor
    public byte[] getBlob(int column) {
        throw new UnsupportedOperationException("getBlob is not supported");
    }

    @Override // android.database.CrossProcessCursor
    public CursorWindow getWindow() {
        return null;
    }

    @Override // android.database.Cursor
    public int getColumnCount() {
        return getColumnNames().length;
    }

    @Override // android.database.Cursor
    public void deactivate() {
        deactivateInternal();
    }

    public void deactivateInternal() {
        if (this.mSelfObserver != null) {
            this.mContentResolver.unregisterContentObserver(this.mSelfObserver);
            this.mSelfObserverRegistered = false;
        }
        this.mDataSetObservable.notifyInvalidated();
    }

    @Override // android.database.Cursor
    public boolean requery() {
        if (this.mSelfObserver != null && !this.mSelfObserverRegistered) {
            this.mContentResolver.registerContentObserver(this.mNotifyUri, true, this.mSelfObserver);
            this.mSelfObserverRegistered = true;
        }
        this.mDataSetObservable.notifyChanged();
        return true;
    }

    @Override // android.database.Cursor
    public boolean isClosed() {
        return this.mClosed;
    }

    @Override // android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mClosed = true;
        this.mContentObservable.unregisterAll();
        deactivateInternal();
    }

    public boolean commitUpdates(Map<? extends Long, ? extends Map<String, Object>> values) {
        return false;
    }

    public boolean deleteRow() {
        return false;
    }

    @Override // android.database.CrossProcessCursor
    public boolean onMove(int oldPosition, int newPosition) {
        return true;
    }

    @Override // android.database.Cursor
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        String result = getString(columnIndex);
        if (result != null) {
            char[] data = buffer.data;
            if (data == null || data.length < result.length()) {
                buffer.data = result.toCharArray();
            } else {
                result.getChars(0, result.length(), data, 0);
            }
            buffer.sizeCopied = result.length();
            return;
        }
        buffer.sizeCopied = 0;
    }

    @Override // android.database.Cursor
    public final int getPosition() {
        return this.mPos;
    }

    @Override // android.database.Cursor
    public final boolean moveToPosition(int position) {
        int count = getCount();
        if (position >= count) {
            this.mPos = count;
            return false;
        }
        if (position < 0) {
            this.mPos = -1;
            return false;
        }
        if (position == this.mPos) {
            return true;
        }
        boolean result = onMove(this.mPos, position);
        if (!result) {
            this.mPos = -1;
            return result;
        }
        this.mPos = position;
        if (this.mRowIdColumnIndex != -1) {
            this.mCurrentRowID = Long.valueOf(getLong(this.mRowIdColumnIndex));
            return result;
        }
        return result;
    }

    @Override // android.database.CrossProcessCursor
    public void fillWindow(int position, android.database.CursorWindow window) {
        DatabaseUtils.cursorFillWindow(this, position, window);
    }

    @Override // android.database.Cursor
    public final boolean move(int offset) {
        return moveToPosition(this.mPos + offset);
    }

    @Override // android.database.Cursor
    public final boolean moveToFirst() {
        return moveToPosition(0);
    }

    @Override // android.database.Cursor
    public final boolean moveToLast() {
        return moveToPosition(getCount() - 1);
    }

    @Override // android.database.Cursor
    public final boolean moveToNext() {
        return moveToPosition(this.mPos + 1);
    }

    @Override // android.database.Cursor
    public final boolean moveToPrevious() {
        return moveToPosition(this.mPos - 1);
    }

    @Override // android.database.Cursor
    public final boolean isFirst() {
        return this.mPos == 0 && getCount() != 0;
    }

    @Override // android.database.Cursor
    public final boolean isLast() {
        int cnt = getCount();
        return this.mPos == cnt + (-1) && cnt != 0;
    }

    @Override // android.database.Cursor
    public final boolean isBeforeFirst() {
        return getCount() == 0 || this.mPos == -1;
    }

    @Override // android.database.Cursor
    public final boolean isAfterLast() {
        return getCount() == 0 || this.mPos == getCount();
    }

    @Override // android.database.Cursor
    public int getColumnIndex(String columnName) {
        int periodIndex = columnName.lastIndexOf(46);
        if (periodIndex != -1) {
            Exception e = new Exception();
            Log.e(TAG, "requesting column name with table name -- " + columnName, e);
            columnName = columnName.substring(periodIndex + 1);
        }
        String[] columnNames = getColumnNames();
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            if (columnNames[i].equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }

    @Override // android.database.Cursor
    public int getColumnIndexOrThrow(String columnName) {
        int index = getColumnIndex(columnName);
        if (index < 0) {
            throw new IllegalArgumentException("column '" + columnName + "' does not exist");
        }
        return index;
    }

    @Override // android.database.Cursor
    public String getColumnName(int columnIndex) {
        return getColumnNames()[columnIndex];
    }

    public boolean updateBlob(int columnIndex, byte[] value) {
        return update(columnIndex, value);
    }

    public boolean updateString(int columnIndex, String value) {
        return update(columnIndex, value);
    }

    public boolean updateShort(int columnIndex, short value) {
        return update(columnIndex, Short.valueOf(value));
    }

    public boolean updateInt(int columnIndex, int value) {
        return update(columnIndex, Integer.valueOf(value));
    }

    public boolean updateLong(int columnIndex, long value) {
        return update(columnIndex, Long.valueOf(value));
    }

    public boolean updateFloat(int columnIndex, float value) {
        return update(columnIndex, Float.valueOf(value));
    }

    public boolean updateDouble(int columnIndex, double value) {
        return update(columnIndex, Double.valueOf(value));
    }

    public boolean updateToNull(int columnIndex) {
        return update(columnIndex, null);
    }

    public boolean update(int columnIndex, Object obj) {
        if (!supportsUpdates()) {
            return false;
        }
        Long rowid = Long.valueOf(getLong(this.mRowIdColumnIndex));
        if (rowid == null) {
            throw new IllegalStateException("null rowid. mRowIdColumnIndex = " + this.mRowIdColumnIndex);
        }
        synchronized (this.mUpdatedRows) {
            Map<String, Object> row = this.mUpdatedRows.get(rowid);
            if (row == null) {
                row = new HashMap<>();
                this.mUpdatedRows.put(rowid, row);
            }
            row.put(getColumnNames()[columnIndex], obj);
        }
        return true;
    }

    public boolean hasUpdates() {
        boolean z;
        synchronized (this.mUpdatedRows) {
            z = this.mUpdatedRows.size() > 0;
        }
        return z;
    }

    public void abortUpdates() {
        synchronized (this.mUpdatedRows) {
            this.mUpdatedRows.clear();
        }
    }

    public boolean commitUpdates() {
        return commitUpdates(null);
    }

    public boolean supportsUpdates() {
        return this.mRowIdColumnIndex != -1;
    }

    @Override // android.database.Cursor
    public void registerContentObserver(ContentObserver observer) {
        this.mContentObservable.registerObserver(observer);
    }

    @Override // android.database.Cursor
    public void unregisterContentObserver(ContentObserver observer) {
        if (!this.mClosed) {
            this.mContentObservable.unregisterObserver(observer);
        }
    }

    protected void notifyDataSetChange() {
        this.mDataSetObservable.notifyChanged();
    }

    protected DataSetObservable getDataSetObservable() {
        return this.mDataSetObservable;
    }

    @Override // android.database.Cursor
    public void registerDataSetObserver(DataSetObserver observer) {
        this.mDataSetObservable.registerObserver(observer);
    }

    @Override // android.database.Cursor
    public void unregisterDataSetObserver(DataSetObserver observer) {
        this.mDataSetObservable.unregisterObserver(observer);
    }

    protected void onChange(boolean selfChange) {
        synchronized (this.mSelfObserverLock) {
            this.mContentObservable.dispatchChange(selfChange);
            if (this.mNotifyUri != null && selfChange) {
                this.mContentResolver.notifyChange(this.mNotifyUri, this.mSelfObserver);
            }
        }
    }

    @Override // android.database.Cursor
    public void setNotificationUri(ContentResolver cr, Uri notifyUri) {
        synchronized (this.mSelfObserverLock) {
            this.mNotifyUri = notifyUri;
            this.mContentResolver = cr;
            if (this.mSelfObserver != null) {
                this.mContentResolver.unregisterContentObserver(this.mSelfObserver);
            }
            this.mSelfObserver = new SelfContentObserver(this);
            this.mContentResolver.registerContentObserver(this.mNotifyUri, true, this.mSelfObserver);
            this.mSelfObserverRegistered = true;
        }
    }

    @Override // android.database.Cursor
    public Uri getNotificationUri() {
        return this.mNotifyUri;
    }

    @Override // android.database.Cursor
    public boolean getWantsAllOnMoveCalls() {
        return false;
    }

    @Override // android.database.Cursor
    public void setExtras(Bundle extras) {
        if (extras == null) {
            extras = Bundle.EMPTY;
        }
        this.mExtras = extras;
    }

    @Override // android.database.Cursor
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.database.Cursor
    public Bundle respond(Bundle extras) {
        return Bundle.EMPTY;
    }

    protected boolean isFieldUpdated(int columnIndex) {
        Map<String, Object> updates;
        return this.mRowIdColumnIndex != -1 && this.mUpdatedRows.size() > 0 && (updates = this.mUpdatedRows.get(this.mCurrentRowID)) != null && updates.containsKey(getColumnNames()[columnIndex]);
    }

    protected Object getUpdatedField(int columnIndex) {
        Map<String, Object> updates = this.mUpdatedRows.get(this.mCurrentRowID);
        return updates.get(getColumnNames()[columnIndex]);
    }

    protected void checkPosition() {
        if (-1 == this.mPos || getCount() == this.mPos) {
            throw new CursorIndexOutOfBoundsException(this.mPos, getCount());
        }
    }

    protected void finalize() {
        if (this.mSelfObserver != null && this.mSelfObserverRegistered) {
            this.mContentResolver.unregisterContentObserver(this.mSelfObserver);
        }
    }

    protected static class SelfContentObserver extends ContentObserver {
        WeakReference<AbstractCursor> mCursor;

        public SelfContentObserver(AbstractCursor cursor) {
            super(null);
            this.mCursor = new WeakReference<>(cursor);
        }

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return false;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            AbstractCursor cursor = this.mCursor.get();
            if (cursor != null) {
                cursor.onChange(false);
            }
        }
    }
}
