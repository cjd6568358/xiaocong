package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class AbstractDrawBorder extends AbstractDrawCommand {
    private static final Paint PAINT = new Paint(1);
    private static final RectF TMP_RECT = new RectF();
    private int mBorderColor = -16777216;
    private float mBorderRadius;
    private float mBorderWidth;
    private Path mPathForBorderRadius;
    private int mSetPropertiesFlag;

    AbstractDrawBorder() {
    }

    static {
        PAINT.setStyle(Paint.Style.STROKE);
    }

    public final void setBorderWidth(float borderWidth) {
        this.mBorderWidth = borderWidth;
        setFlag(1);
    }

    public final float getBorderWidth() {
        return this.mBorderWidth;
    }

    public void setBorderRadius(float borderRadius) {
        this.mBorderRadius = borderRadius;
        setFlag(1);
    }

    public final float getBorderRadius() {
        return this.mBorderRadius;
    }

    public final void setBorderColor(int borderColor) {
        this.mBorderColor = borderColor;
    }

    public final int getBorderColor() {
        return this.mBorderColor;
    }

    protected final void drawBorders(Canvas canvas) {
        if (this.mBorderWidth >= 0.5f && this.mBorderColor != 0) {
            PAINT.setColor(this.mBorderColor);
            PAINT.setStrokeWidth(this.mBorderWidth);
            PAINT.setPathEffect(getPathEffectForBorderStyle());
            canvas.drawPath(getPathForBorderRadius(), PAINT);
        }
    }

    protected final void updatePath(Path path, float correction) {
        path.reset();
        TMP_RECT.set(getLeft() + correction, getTop() + correction, getRight() - correction, getBottom() - correction);
        path.addRoundRect(TMP_RECT, this.mBorderRadius, this.mBorderRadius, Path.Direction.CW);
    }

    protected PathEffect getPathEffectForBorderStyle() {
        return null;
    }

    protected final boolean isFlagSet(int mask) {
        return (this.mSetPropertiesFlag & mask) == mask;
    }

    protected final void setFlag(int mask) {
        this.mSetPropertiesFlag |= mask;
    }

    protected final void resetFlag(int mask) {
        this.mSetPropertiesFlag &= mask ^ (-1);
    }

    protected final Path getPathForBorderRadius() {
        if (isFlagSet(1)) {
            if (this.mPathForBorderRadius == null) {
                this.mPathForBorderRadius = new Path();
            }
            updatePath(this.mPathForBorderRadius, this.mBorderWidth * 0.5f);
            resetFlag(1);
        }
        return this.mPathForBorderRadius;
    }
}
