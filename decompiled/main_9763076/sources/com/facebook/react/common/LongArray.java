package com.facebook.react.common;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LongArray {
    private long[] mArray;
    private int mLength = 0;

    public static LongArray createWithInitialCapacity(int initialCapacity) {
        return new LongArray(initialCapacity);
    }

    private LongArray(int initialCapacity) {
        this.mArray = new long[initialCapacity];
    }

    public void add(long value) {
        growArrayIfNeeded();
        long[] jArr = this.mArray;
        int i = this.mLength;
        this.mLength = i + 1;
        jArr[i] = value;
    }

    public long get(int index) {
        if (index >= this.mLength) {
            throw new IndexOutOfBoundsException(Constants.MAIN_VERSION_TAG + index + " >= " + this.mLength);
        }
        return this.mArray[index];
    }

    public void set(int index, long value) {
        if (index >= this.mLength) {
            throw new IndexOutOfBoundsException(Constants.MAIN_VERSION_TAG + index + " >= " + this.mLength);
        }
        this.mArray[index] = value;
    }

    public int size() {
        return this.mLength;
    }

    public void dropTail(int n) {
        if (n > this.mLength) {
            throw new IndexOutOfBoundsException("Trying to drop " + n + " items from array of length " + this.mLength);
        }
        this.mLength -= n;
    }

    private void growArrayIfNeeded() {
        if (this.mLength == this.mArray.length) {
            int newSize = Math.max(this.mLength + 1, (int) (((double) this.mLength) * 1.8d));
            long[] newArray = new long[newSize];
            System.arraycopy(this.mArray, 0, newArray, 0, this.mLength);
            this.mArray = newArray;
        }
    }
}
