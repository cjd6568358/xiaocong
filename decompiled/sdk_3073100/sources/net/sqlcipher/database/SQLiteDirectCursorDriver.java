package net.sqlcipher.database;

import net.sqlcipher.Cursor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SQLiteDirectCursorDriver implements SQLiteCursorDriver {
    private Cursor mCursor;
    private SQLiteDatabase mDatabase;
    private String mEditTable;
    private SQLiteQuery mQuery;
    private String mSql;

    public SQLiteDirectCursorDriver(SQLiteDatabase db, String sql, String editTable) {
        this.mDatabase = db;
        this.mEditTable = editTable;
        this.mSql = sql;
    }

    public Cursor query(SQLiteDatabase$CursorFactory factory, Object[] args) {
        SQLiteQuery query = new SQLiteQuery(this.mDatabase, this.mSql, 0, args);
        try {
            query.bindArguments(args);
            if (factory == null) {
                this.mCursor = new SQLiteCursor(this.mDatabase, this, this.mEditTable, query);
            } else {
                this.mCursor = factory.newCursor(this.mDatabase, this, this.mEditTable, query);
            }
            this.mQuery = query;
            query = null;
            return this.mCursor;
        } finally {
            if (query != null) {
                query.close();
            }
        }
    }

    @Override // net.sqlcipher.database.SQLiteCursorDriver
    public Cursor query(SQLiteDatabase$CursorFactory factory, String[] selectionArgs) {
        int numArgs = 0;
        SQLiteQuery query = new SQLiteQuery(this.mDatabase, this.mSql, 0, selectionArgs);
        if (selectionArgs != null) {
            try {
                numArgs = selectionArgs.length;
            } finally {
                if (query != null) {
                    query.close();
                }
            }
        }
        for (int i = 0; i < numArgs; i++) {
            query.bindString(i + 1, selectionArgs[i]);
        }
        if (factory == null) {
            this.mCursor = new SQLiteCursor(this.mDatabase, this, this.mEditTable, query);
        } else {
            this.mCursor = factory.newCursor(this.mDatabase, this, this.mEditTable, query);
        }
        this.mQuery = query;
        query = null;
        return this.mCursor;
    }

    @Override // net.sqlcipher.database.SQLiteCursorDriver
    public void cursorClosed() {
        this.mCursor = null;
    }

    @Override // net.sqlcipher.database.SQLiteCursorDriver
    public void setBindArguments(String[] bindArgs) {
        int numArgs = bindArgs.length;
        for (int i = 0; i < numArgs; i++) {
            this.mQuery.bindString(i + 1, bindArgs[i]);
        }
    }

    @Override // net.sqlcipher.database.SQLiteCursorDriver
    public void cursorDeactivated() {
    }

    @Override // net.sqlcipher.database.SQLiteCursorDriver
    public void cursorRequeried(android.database.Cursor cursor) {
    }

    public String toString() {
        return "SQLiteDirectCursorDriver: " + this.mSql;
    }
}
