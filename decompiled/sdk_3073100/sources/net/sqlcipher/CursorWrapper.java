package net.sqlcipher;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CursorWrapper extends android.database.CursorWrapper implements Cursor {
    private final Cursor mCursor;

    public CursorWrapper(Cursor cursor) {
        super(cursor);
        this.mCursor = cursor;
    }

    @Override // android.database.CursorWrapper, android.database.Cursor, net.sqlcipher.Cursor
    public int getType(int columnIndex) {
        return this.mCursor.getType(columnIndex);
    }

    @Override // android.database.CursorWrapper
    public Cursor getWrappedCursor() {
        return this.mCursor;
    }
}
