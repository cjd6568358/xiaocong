package com.facebook.drawee.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LightBitmapDrawable extends Drawable {
    private Bitmap mBitmap;
    private int mBitmapHeight;
    private int mBitmapWidth;
    private final Paint mPaint;
    private int mTargetDensity;

    public LightBitmapDrawable(Resources res, Bitmap bitmap) {
        this(res, bitmap, null);
    }

    public LightBitmapDrawable(Resources res, Bitmap bitmap, Paint paint) {
        this.mBitmap = null;
        this.mTargetDensity = 160;
        this.mPaint = new Paint(6);
        if (paint != null) {
            this.mPaint.set(paint);
        }
        this.mBitmap = bitmap;
        this.mTargetDensity = res.getDisplayMetrics().densityDpi;
        computeBitmapSize();
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    private void computeBitmapSize() {
        if (this.mBitmap != null) {
            this.mBitmapWidth = this.mBitmap.getScaledWidth(this.mTargetDensity);
            this.mBitmapHeight = this.mBitmap.getScaledHeight(this.mTargetDensity);
        } else {
            this.mBitmapHeight = -1;
            this.mBitmapWidth = -1;
        }
    }

    public void setBitmap(Bitmap bitmap) {
        if (this.mBitmap != bitmap) {
            this.mBitmap = bitmap;
            computeBitmapSize();
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean filter) {
        this.mPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean dither) {
        this.mPaint.setDither(dither);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mBitmap != null) {
            canvas.drawBitmap(this.mBitmap, (Rect) null, getBounds(), this.mPaint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        if (alpha != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return (this.mBitmap == null || this.mBitmap.hasAlpha() || this.mPaint.getAlpha() < 255) ? -3 : -1;
    }
}
