package net.sqlcipher;

import android.database.CrossProcessCursor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CrossProcessCursorWrapper extends CursorWrapper implements CrossProcessCursor {
    public CrossProcessCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @Override // android.database.CrossProcessCursor
    public android.database.CursorWindow getWindow() {
        return null;
    }

    @Override // android.database.CrossProcessCursor
    public void fillWindow(int position, android.database.CursorWindow window) {
        DatabaseUtils.cursorFillWindow(this, position, window);
    }

    @Override // android.database.CrossProcessCursor
    public boolean onMove(int oldPosition, int newPosition) {
        return true;
    }
}
