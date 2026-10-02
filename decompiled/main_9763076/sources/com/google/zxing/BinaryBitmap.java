package com.google.zxing;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BinaryBitmap {
    private final Binarizer binarizer;
    private BitMatrix matrix;

    public BinaryBitmap(Binarizer binarizer) {
        if (binarizer == null) {
            throw new IllegalArgumentException("Binarizer must be non-null.");
        }
        this.binarizer = binarizer;
    }

    public int getWidth() {
        return this.binarizer.getWidth();
    }

    public int getHeight() {
        return this.binarizer.getHeight();
    }

    public BitArray getBlackRow(int y, BitArray row) throws NotFoundException {
        return this.binarizer.getBlackRow(y, row);
    }

    public BitMatrix getBlackMatrix() throws NotFoundException {
        if (this.matrix == null) {
            this.matrix = this.binarizer.getBlackMatrix();
        }
        return this.matrix;
    }

    public boolean isRotateSupported() {
        return this.binarizer.getLuminanceSource().isRotateSupported();
    }

    public BinaryBitmap rotateCounterClockwise() {
        LuminanceSource newSource = this.binarizer.getLuminanceSource().rotateCounterClockwise();
        return new BinaryBitmap(this.binarizer.createBinarizer(newSource));
    }

    public String toString() {
        try {
            return getBlackMatrix().toString();
        } catch (NotFoundException e) {
            return Constants.MAIN_VERSION_TAG;
        }
    }
}
