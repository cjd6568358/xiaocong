package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MatrixDrawable extends ForwardingDrawable {
    private Matrix mDrawMatrix;
    private Matrix mMatrix;
    private int mUnderlyingHeight;
    private int mUnderlyingWidth;

    public MatrixDrawable(Drawable drawable, Matrix matrix) {
        super((Drawable) Preconditions.checkNotNull(drawable));
        this.mUnderlyingWidth = 0;
        this.mUnderlyingHeight = 0;
        this.mMatrix = matrix;
    }

    @Override // com.facebook.drawee.drawable.ForwardingDrawable
    public Drawable setCurrent(Drawable newDelegate) {
        Drawable previousDelegate = super.setCurrent(newDelegate);
        configureBounds();
        return previousDelegate;
    }

    @Override // com.facebook.drawee.drawable.ForwardingDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        configureBoundsIfUnderlyingChanged();
        if (this.mDrawMatrix != null) {
            int saveCount = canvas.save();
            canvas.clipRect(getBounds());
            canvas.concat(this.mDrawMatrix);
            super.draw(canvas);
            canvas.restoreToCount(saveCount);
            return;
        }
        super.draw(canvas);
    }

    @Override // com.facebook.drawee.drawable.ForwardingDrawable, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        configureBounds();
    }

    private void configureBoundsIfUnderlyingChanged() {
        if (this.mUnderlyingWidth != getCurrent().getIntrinsicWidth() || this.mUnderlyingHeight != getCurrent().getIntrinsicHeight()) {
            configureBounds();
        }
    }

    private void configureBounds() {
        Drawable underlyingDrawable = getCurrent();
        Rect bounds = getBounds();
        int underlyingWidth = underlyingDrawable.getIntrinsicWidth();
        this.mUnderlyingWidth = underlyingWidth;
        int underlyingHeight = underlyingDrawable.getIntrinsicHeight();
        this.mUnderlyingHeight = underlyingHeight;
        if (underlyingWidth <= 0 || underlyingHeight <= 0) {
            underlyingDrawable.setBounds(bounds);
            this.mDrawMatrix = null;
        } else {
            underlyingDrawable.setBounds(0, 0, underlyingWidth, underlyingHeight);
            this.mDrawMatrix = this.mMatrix;
        }
    }

    @Override // com.facebook.drawee.drawable.ForwardingDrawable, com.facebook.drawee.drawable.TransformCallback
    public void getTransform(Matrix transform) {
        super.getTransform(transform);
        if (this.mDrawMatrix != null) {
            transform.preConcat(this.mDrawMatrix);
        }
    }
}
