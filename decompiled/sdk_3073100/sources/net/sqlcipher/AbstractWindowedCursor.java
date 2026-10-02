package net.sqlcipher;

import android.database.CharArrayBuffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class AbstractWindowedCursor extends AbstractCursor {
    protected CursorWindow mWindow;

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                return (byte[]) getUpdatedField(columnIndex);
            }
            return this.mWindow.getBlob(this.mPos, columnIndex);
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public String getString(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                return (String) getUpdatedField(columnIndex);
            }
            return this.mWindow.getString(this.mPos, columnIndex);
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                super.copyStringToBuffer(columnIndex, buffer);
            }
        }
        this.mWindow.copyStringToBuffer(this.mPos, columnIndex, buffer);
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public short getShort(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                Number value = (Number) getUpdatedField(columnIndex);
                return value.shortValue();
            }
            return this.mWindow.getShort(this.mPos, columnIndex);
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public int getInt(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                Number value = (Number) getUpdatedField(columnIndex);
                return value.intValue();
            }
            return this.mWindow.getInt(this.mPos, columnIndex);
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public long getLong(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                Number value = (Number) getUpdatedField(columnIndex);
                return value.longValue();
            }
            return this.mWindow.getLong(this.mPos, columnIndex);
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public float getFloat(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                Number value = (Number) getUpdatedField(columnIndex);
                return value.floatValue();
            }
            return this.mWindow.getFloat(this.mPos, columnIndex);
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public double getDouble(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                Number value = (Number) getUpdatedField(columnIndex);
                return value.doubleValue();
            }
            return this.mWindow.getDouble(this.mPos, columnIndex);
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor
    public boolean isNull(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                return getUpdatedField(columnIndex) == null;
            }
            return this.mWindow.isNull(this.mPos, columnIndex);
        }
    }

    public boolean isBlob(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                Object object = getUpdatedField(columnIndex);
                return object == null || (object instanceof byte[]);
            }
            return this.mWindow.isBlob(this.mPos, columnIndex);
        }
    }

    public boolean isString(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                Object object = getUpdatedField(columnIndex);
                return object == null || (object instanceof String);
            }
            return this.mWindow.isString(this.mPos, columnIndex);
        }
    }

    public boolean isLong(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                Object object = getUpdatedField(columnIndex);
                return object != null && ((object instanceof Integer) || (object instanceof Long));
            }
            return this.mWindow.isLong(this.mPos, columnIndex);
        }
    }

    public boolean isFloat(int columnIndex) {
        checkPosition();
        synchronized (this.mUpdatedRows) {
            if (isFieldUpdated(columnIndex)) {
                Object object = getUpdatedField(columnIndex);
                return object != null && ((object instanceof Float) || (object instanceof Double));
            }
            return this.mWindow.isFloat(this.mPos, columnIndex);
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.Cursor, net.sqlcipher.Cursor
    public int getType(int columnIndex) {
        checkPosition();
        return this.mWindow.getType(this.mPos, columnIndex);
    }

    @Override // net.sqlcipher.AbstractCursor
    protected void checkPosition() {
        super.checkPosition();
        if (this.mWindow == null) {
            throw new StaleDataException("Access closed cursor");
        }
    }

    @Override // net.sqlcipher.AbstractCursor, android.database.CrossProcessCursor
    public CursorWindow getWindow() {
        return this.mWindow;
    }

    public void setWindow(CursorWindow window) {
        if (this.mWindow != null) {
            this.mWindow.close();
        }
        this.mWindow = window;
    }

    public boolean hasWindow() {
        return this.mWindow != null;
    }
}
