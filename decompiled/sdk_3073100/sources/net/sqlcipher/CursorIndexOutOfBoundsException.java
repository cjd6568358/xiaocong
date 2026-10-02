package net.sqlcipher;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CursorIndexOutOfBoundsException extends IndexOutOfBoundsException {
    public CursorIndexOutOfBoundsException(int index, int size) {
        super("Index " + index + " requested, with a size of " + size);
    }

    public CursorIndexOutOfBoundsException(String message) {
        super(message);
    }
}
