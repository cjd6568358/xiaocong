package com.facebook.drawee.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import com.facebook.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RoundedLightBitmapDrawable extends LightBitmapDrawable implements Rounded, TransformAwareDrawable {
    final RectF mBitmapBounds;
    private int mBorderColor;
    private final Paint mBorderPaint;
    private final Path mBorderPath;
    final float[] mBorderRadii;
    private float mBorderWidth;
    final Matrix mBoundsTransform;
    private final float[] mCornerRadii;
    final RectF mDrawableBounds;
    final Matrix mInverseParentTransform;
    private boolean mIsCircle;
    private boolean mIsPathDirty;
    private boolean mIsShaderTransformDirty;
    private WeakReference<Bitmap> mLastBitmap;
    private float mPadding;
    final Matrix mParentTransform;
    private final Path mPath;
    final Matrix mPrevBoundsTransform;
    final Matrix mPrevParentTransform;
    final RectF mPrevRootBounds;
    private boolean mRadiiNonZero;
    final RectF mRootBounds;
    private Shader mShader;
    final Matrix mTransform;
    private TransformCallback mTransformCallback;

    public RoundedLightBitmapDrawable(Resources res, Bitmap bitmap, Paint paint) {
        super(res, bitmap, paint);
        this.mIsCircle = false;
        this.mRadiiNonZero = false;
        this.mCornerRadii = new float[8];
        this.mBorderRadii = new float[8];
        this.mRootBounds = new RectF();
        this.mPrevRootBounds = new RectF();
        this.mBitmapBounds = new RectF();
        this.mDrawableBounds = new RectF();
        this.mBoundsTransform = new Matrix();
        this.mPrevBoundsTransform = new Matrix();
        this.mParentTransform = new Matrix();
        this.mPrevParentTransform = new Matrix();
        this.mInverseParentTransform = new Matrix();
        this.mTransform = new Matrix();
        this.mBorderWidth = 0.0f;
        this.mBorderColor = 0;
        this.mPadding = 0.0f;
        this.mPath = new Path();
        this.mBorderPath = new Path();
        this.mIsPathDirty = true;
        this.mBorderPaint = new Paint(1);
        this.mIsShaderTransformDirty = true;
        getPaint().setFlags(1);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override // com.facebook.drawee.drawable.Rounded
    public void setCircle(boolean isCircle) {
        this.mIsCircle = isCircle;
        this.mIsPathDirty = true;
        invalidateSelf();
    }

    @Override // com.facebook.drawee.drawable.Rounded
    public void setRadius(float radius) {
        Preconditions.checkState(radius >= 0.0f);
        Arrays.fill(this.mCornerRadii, radius);
        this.mRadiiNonZero = radius != 0.0f;
        this.mIsPathDirty = true;
        invalidateSelf();
    }

    @Override // com.facebook.drawee.drawable.Rounded
    public void setRadii(float[] radii) {
        if (radii == null) {
            Arrays.fill(this.mCornerRadii, 0.0f);
            this.mRadiiNonZero = false;
        } else {
            Preconditions.checkArgument(radii.length == 8, "radii should have exactly 8 values");
            System.arraycopy(radii, 0, this.mCornerRadii, 0, 8);
            this.mRadiiNonZero = false;
            for (int i = 0; i < 8; i++) {
                this.mRadiiNonZero = (radii[i] > 0.0f) | this.mRadiiNonZero;
            }
        }
        this.mIsPathDirty = true;
        invalidateSelf();
    }

    @Override // com.facebook.drawee.drawable.Rounded
    public void setBorder(int color, float width) {
        if (this.mBorderColor != color || this.mBorderWidth != width) {
            this.mBorderColor = color;
            this.mBorderWidth = width;
            this.mIsPathDirty = true;
            invalidateSelf();
        }
    }

    @Override // com.facebook.drawee.drawable.Rounded
    public void setPadding(float padding) {
        if (this.mPadding != padding) {
            this.mPadding = padding;
            this.mIsPathDirty = true;
            invalidateSelf();
        }
    }

    @Override // com.facebook.drawee.drawable.TransformAwareDrawable
    public void setTransformCallback(TransformCallback transformCallback) {
        this.mTransformCallback = transformCallback;
    }

    @Override // com.facebook.drawee.drawable.LightBitmapDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        Bitmap bitmap = getBitmap();
        Paint paint = getPaint();
        if (bitmap != null) {
            if (!shouldRound()) {
                paint.setShader(null);
                canvas.drawBitmap(bitmap, (Rect) null, bounds, paint);
                return;
            }
            updateTransform(bitmap, bounds);
            updatePaint(bitmap);
            updatePath();
            int saveCount = canvas.save();
            canvas.concat(this.mInverseParentTransform);
            canvas.drawPath(this.mPath, getPaint());
            if (this.mBorderWidth > 0.0f) {
                this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
                this.mBorderPaint.setColor(DrawableUtils.multiplyColorAlpha(this.mBorderColor, getPaint().getAlpha()));
                canvas.drawPath(this.mBorderPath, this.mBorderPaint);
            }
            canvas.restoreToCount(saveCount);
        }
    }

    boolean shouldRound() {
        return this.mIsCircle || this.mRadiiNonZero || this.mBorderWidth > 0.0f;
    }

    private void updateTransform(Bitmap bitmap, Rect bounds) {
        if (this.mTransformCallback != null) {
            this.mTransformCallback.getTransform(this.mParentTransform);
            this.mTransformCallback.getRootBounds(this.mRootBounds);
        } else {
            this.mParentTransform.reset();
            this.mRootBounds.set(bounds);
        }
        this.mBitmapBounds.set(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
        this.mDrawableBounds.set(bounds);
        this.mBoundsTransform.setRectToRect(this.mBitmapBounds, this.mDrawableBounds, Matrix.ScaleToFit.FILL);
        if (!this.mParentTransform.equals(this.mPrevParentTransform) || !this.mBoundsTransform.equals(this.mPrevBoundsTransform)) {
            this.mIsShaderTransformDirty = true;
            this.mParentTransform.invert(this.mInverseParentTransform);
            this.mTransform.set(this.mParentTransform);
            this.mTransform.preConcat(this.mBoundsTransform);
            this.mPrevParentTransform.set(this.mParentTransform);
            this.mPrevBoundsTransform.set(this.mBoundsTransform);
        }
        if (!this.mRootBounds.equals(this.mPrevRootBounds)) {
            this.mIsPathDirty = true;
            this.mPrevRootBounds.set(this.mRootBounds);
        }
    }

    private void updatePath() {
        if (this.mIsPathDirty) {
            this.mBorderPath.reset();
            this.mRootBounds.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
            if (this.mIsCircle) {
                float radius = Math.min(this.mRootBounds.width(), this.mRootBounds.height()) / 2.0f;
                this.mBorderPath.addCircle(this.mRootBounds.centerX(), this.mRootBounds.centerY(), radius, Path.Direction.CW);
            } else {
                for (int i = 0; i < this.mBorderRadii.length; i++) {
                    this.mBorderRadii[i] = (this.mCornerRadii[i] + this.mPadding) - (this.mBorderWidth / 2.0f);
                }
                this.mBorderPath.addRoundRect(this.mRootBounds, this.mBorderRadii, Path.Direction.CW);
            }
            this.mRootBounds.inset((-this.mBorderWidth) / 2.0f, (-this.mBorderWidth) / 2.0f);
            this.mPath.reset();
            this.mRootBounds.inset(this.mPadding, this.mPadding);
            if (this.mIsCircle) {
                this.mPath.addCircle(this.mRootBounds.centerX(), this.mRootBounds.centerY(), Math.min(this.mRootBounds.width(), this.mRootBounds.height()) / 2.0f, Path.Direction.CW);
            } else {
                this.mPath.addRoundRect(this.mRootBounds, this.mCornerRadii, Path.Direction.CW);
            }
            this.mRootBounds.inset(-this.mPadding, -this.mPadding);
            this.mPath.setFillType(Path.FillType.WINDING);
            this.mIsPathDirty = false;
        }
    }

    private void updatePaint(Bitmap bitmap) {
        Paint paint = getPaint();
        if (this.mLastBitmap == null || this.mLastBitmap.get() != bitmap) {
            this.mLastBitmap = new WeakReference<>(bitmap);
            this.mShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mIsShaderTransformDirty = true;
        }
        if (this.mIsShaderTransformDirty) {
            this.mShader.setLocalMatrix(this.mTransform);
            this.mIsShaderTransformDirty = false;
        }
        if (paint.getShader() != this.mShader) {
            paint.setShader(this.mShader);
        }
    }
}
