package com.facebook.react.flat;

import android.graphics.Canvas;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class AbstractDrawCommand extends DrawCommand implements Cloneable {
    private float mBottom;
    private float mClipBottom;
    private float mClipLeft;
    private float mClipRight;
    private float mClipTop;
    private boolean mFrozen;
    private float mLeft;
    protected boolean mNeedsClipping;
    private float mRight;
    private float mTop;

    protected abstract void onDraw(Canvas canvas);

    AbstractDrawCommand() {
    }

    public final float getClipLeft() {
        return this.mClipLeft;
    }

    public final float getClipTop() {
        return this.mClipTop;
    }

    public final float getClipRight() {
        return this.mClipRight;
    }

    public final float getClipBottom() {
        return this.mClipBottom;
    }

    protected void applyClipping(Canvas canvas) {
        canvas.clipRect(this.mClipLeft, this.mClipTop, this.mClipRight, this.mClipBottom);
    }

    @Override // com.facebook.react.flat.DrawCommand
    public void draw(FlatViewGroup parent, Canvas canvas) {
        onPreDraw(parent, canvas);
        if (this.mNeedsClipping && shouldClip()) {
            canvas.save(2);
            applyClipping(canvas);
            onDraw(canvas);
            canvas.restore();
            return;
        }
        onDraw(canvas);
    }

    protected static int getDebugBorderColor() {
        return -16711681;
    }

    protected String getDebugName() {
        return getClass().getSimpleName().substring(4);
    }

    @Override // com.facebook.react.flat.DrawCommand
    public final void debugDraw(FlatViewGroup parent, Canvas canvas) {
        onDebugDraw(parent, canvas);
    }

    protected void onDebugDraw(FlatViewGroup parent, Canvas canvas) {
        parent.debugDrawNamedRect(canvas, getDebugBorderColor(), getDebugName(), this.mLeft, this.mTop, this.mRight, this.mBottom);
    }

    protected void onPreDraw(FlatViewGroup parent, Canvas canvas) {
    }

    public final AbstractDrawCommand mutableCopy() {
        try {
            AbstractDrawCommand copy = (AbstractDrawCommand) super.clone();
            copy.mFrozen = false;
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public final boolean isFrozen() {
        return this.mFrozen;
    }

    public final float getLeft() {
        return this.mLeft;
    }

    public final float getTop() {
        return this.mTop;
    }

    public final float getRight() {
        return this.mRight;
    }

    public final float getBottom() {
        return this.mBottom;
    }

    protected boolean shouldClip() {
        return this.mLeft < getClipLeft() || this.mTop < getClipTop() || this.mRight > getClipRight() || this.mBottom > getClipBottom();
    }
}
