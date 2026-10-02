package com.facebook.react.flat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class NodeRegion {
    private final float mBottom;
    final boolean mIsVirtual;
    private final float mLeft;
    private final float mRight;
    final int mTag;
    private final float mTop;
    static final NodeRegion[] EMPTY_ARRAY = new NodeRegion[0];
    static final NodeRegion EMPTY = new NodeRegion(0.0f, 0.0f, 0.0f, 0.0f, -1, false);

    NodeRegion(float left, float top, float right, float bottom, int tag, boolean isVirtual) {
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
        this.mTag = tag;
        this.mIsVirtual = isVirtual;
    }

    final float getLeft() {
        return this.mLeft;
    }

    final float getTop() {
        return this.mTop;
    }

    final float getRight() {
        return this.mRight;
    }

    final float getBottom() {
        return this.mBottom;
    }

    boolean withinBounds(float touchX, float touchY) {
        return this.mLeft <= touchX && touchX < this.mRight && this.mTop <= touchY && touchY < this.mBottom;
    }

    int getReactTag(float touchX, float touchY) {
        return this.mTag;
    }
}
