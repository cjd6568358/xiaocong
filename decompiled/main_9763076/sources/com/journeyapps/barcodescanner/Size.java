package com.journeyapps.barcodescanner;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Size implements Comparable<Size> {
    public final int height;
    public final int width;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Size rotate() {
        return new Size(this.height, this.width);
    }

    public Size scaleFit(Size into) {
        return this.width * into.height >= into.width * this.height ? new Size(into.width, (this.height * into.width) / this.width) : new Size((this.width * into.height) / this.height, into.height);
    }

    public Size scaleCrop(Size into) {
        return this.width * into.height <= into.width * this.height ? new Size(into.width, (this.height * into.width) / this.width) : new Size((this.width * into.height) / this.height, into.height);
    }

    @Override // java.lang.Comparable
    public int compareTo(Size other) {
        int aPixels = this.height * this.width;
        int bPixels = other.height * other.width;
        if (bPixels < aPixels) {
            return 1;
        }
        if (bPixels > aPixels) {
            return -1;
        }
        return 0;
    }

    public String toString() {
        return this.width + "x" + this.height;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Size size = (Size) o;
        return this.width == size.width && this.height == size.height;
    }

    public int hashCode() {
        int result = this.width;
        return (result * 31) + this.height;
    }
}
