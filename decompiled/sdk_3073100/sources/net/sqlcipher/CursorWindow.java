package net.sqlcipher;

import android.database.CharArrayBuffer;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CursorWindow extends android.database.CursorWindow implements Parcelable {
    public static final Parcelable.Creator<CursorWindow> CREATOR = new Parcelable.Creator<CursorWindow>() { // from class: net.sqlcipher.CursorWindow.1
        @Override // android.os.Parcelable.Creator
        public CursorWindow createFromParcel(Parcel source) {
            return new CursorWindow(source, 0);
        }

        @Override // android.os.Parcelable.Creator
        public CursorWindow[] newArray(int size) {
            return new CursorWindow[size];
        }
    };
    private int mStartPos;
    private long nWindow;

    private native boolean allocRow_native();

    private native void close_native();

    private native char[] copyStringToBuffer_native(int i, int i2, int i3, CharArrayBuffer charArrayBuffer);

    private native void freeLastRow_native();

    private native byte[] getBlob_native(int i, int i2);

    private native double getDouble_native(int i, int i2);

    private native long getLong_native(int i, int i2);

    private native int getNumRows_native();

    private native String getString_native(int i, int i2);

    private native int getType_native(int i, int i2);

    private native boolean isBlob_native(int i, int i2);

    private native boolean isFloat_native(int i, int i2);

    private native boolean isInteger_native(int i, int i2);

    private native boolean isNull_native(int i, int i2);

    private native boolean isString_native(int i, int i2);

    private native void native_clear();

    private native IBinder native_getBinder();

    private native void native_init(IBinder iBinder);

    private native void native_init(boolean z);

    private native boolean putBlob_native(byte[] bArr, int i, int i2);

    private native boolean putDouble_native(double d, int i, int i2);

    private native boolean putLong_native(long j, int i, int i2);

    private native boolean putNull_native(int i, int i2);

    private native boolean putString_native(String str, int i, int i2);

    private native boolean setNumColumns_native(int i);

    public CursorWindow(boolean localWindow) {
        super(localWindow);
        this.mStartPos = 0;
        native_init(localWindow);
    }

    @Override // android.database.CursorWindow
    public int getStartPosition() {
        return this.mStartPos;
    }

    @Override // android.database.CursorWindow
    public void setStartPosition(int pos) {
        this.mStartPos = pos;
    }

    @Override // android.database.CursorWindow
    public int getNumRows() {
        acquireReference();
        try {
            return getNumRows_native();
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean setNumColumns(int columnNum) {
        acquireReference();
        try {
            return setNumColumns_native(columnNum);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean allocRow() {
        acquireReference();
        try {
            return allocRow_native();
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public void freeLastRow() {
        acquireReference();
        try {
            freeLastRow_native();
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean putBlob(byte[] value, int row, int col) {
        acquireReference();
        try {
            return putBlob_native(value, row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean putString(String value, int row, int col) {
        acquireReference();
        try {
            return putString_native(value, row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean putLong(long value, int row, int col) {
        acquireReference();
        try {
            return putLong_native(value, row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean putDouble(double value, int row, int col) {
        acquireReference();
        try {
            return putDouble_native(value, row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean putNull(int row, int col) {
        acquireReference();
        try {
            return putNull_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean isNull(int row, int col) {
        acquireReference();
        try {
            return isNull_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public byte[] getBlob(int row, int col) {
        acquireReference();
        try {
            return getBlob_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public int getType(int row, int col) {
        acquireReference();
        try {
            return getType_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean isBlob(int row, int col) {
        acquireReference();
        try {
            return isBlob_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean isLong(int row, int col) {
        acquireReference();
        try {
            return isInteger_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean isFloat(int row, int col) {
        acquireReference();
        try {
            return isFloat_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public boolean isString(int row, int col) {
        acquireReference();
        try {
            return isString_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public String getString(int row, int col) {
        acquireReference();
        try {
            return getString_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public void copyStringToBuffer(int row, int col, CharArrayBuffer buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("CharArrayBuffer should not be null");
        }
        if (buffer.data == null) {
            buffer.data = new char[64];
        }
        acquireReference();
        try {
            char[] newbuf = copyStringToBuffer_native(row - this.mStartPos, col, buffer.data.length, buffer);
            if (newbuf != null) {
                buffer.data = newbuf;
            }
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public long getLong(int row, int col) {
        acquireReference();
        try {
            return getLong_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public double getDouble(int row, int col) {
        acquireReference();
        try {
            return getDouble_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public short getShort(int row, int col) {
        acquireReference();
        try {
            return (short) getLong_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public int getInt(int row, int col) {
        acquireReference();
        try {
            return (int) getLong_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public float getFloat(int row, int col) {
        acquireReference();
        try {
            return (float) getDouble_native(row - this.mStartPos, col);
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.CursorWindow
    public void clear() {
        acquireReference();
        try {
            this.mStartPos = 0;
            native_clear();
        } finally {
            releaseReference();
        }
    }

    @Override // android.database.sqlite.SQLiteClosable, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        releaseReference();
    }

    @Override // android.database.CursorWindow
    protected void finalize() {
        if (this.nWindow != 0) {
            close_native();
        }
    }

    @Override // android.database.CursorWindow, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.database.CursorWindow, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(native_getBinder());
        dest.writeInt(this.mStartPos);
    }

    public CursorWindow(Parcel source, int foo) {
        super(true);
        IBinder nativeBinder = source.readStrongBinder();
        this.mStartPos = source.readInt();
        native_init(nativeBinder);
    }

    @Override // android.database.CursorWindow, android.database.sqlite.SQLiteClosable
    protected void onAllReferencesReleased() {
        close_native();
        super.onAllReferencesReleased();
    }
}
