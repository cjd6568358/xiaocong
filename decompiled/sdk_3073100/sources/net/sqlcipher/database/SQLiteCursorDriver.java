package net.sqlcipher.database;

import android.database.Cursor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface SQLiteCursorDriver {
    void cursorClosed();

    void cursorDeactivated();

    void cursorRequeried(Cursor cursor);

    net.sqlcipher.Cursor query(SQLiteDatabase$CursorFactory sQLiteDatabase$CursorFactory, String[] strArr);

    void setBindArguments(String[] strArr);
}
