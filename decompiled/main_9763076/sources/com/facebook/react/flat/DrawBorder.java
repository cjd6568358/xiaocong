package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class DrawBorder extends AbstractDrawBorder {
    private static final Paint PAINT = new Paint(1);
    private static final float[] TMP_FLOAT_ARRAY = new float[4];
    private int mBackgroundColor;
    private int mBorderBottomColor;
    private float mBorderBottomWidth;
    private int mBorderLeftColor;
    private float mBorderLeftWidth;
    private int mBorderRightColor;
    private float mBorderRightWidth;
    private int mBorderStyle = 0;
    private int mBorderTopColor;
    private float mBorderTopWidth;
    private DashPathEffect mPathEffectForBorderStyle;
    private Path mPathForBorder;

    DrawBorder() {
    }

    public void setBorderWidth(int position, float borderWidth) {
        switch (position) {
            case 0:
                this.mBorderLeftWidth = borderWidth;
                break;
            case 1:
                this.mBorderTopWidth = borderWidth;
                break;
            case 2:
                this.mBorderRightWidth = borderWidth;
                break;
            case 3:
                this.mBorderBottomWidth = borderWidth;
                break;
            case 8:
                setBorderWidth(borderWidth);
                break;
        }
    }

    public void setBorderStyle(String style) {
        if ("dotted".equals(style)) {
            this.mBorderStyle = 1;
        } else if ("dashed".equals(style)) {
            this.mBorderStyle = 2;
        } else {
            this.mBorderStyle = 0;
        }
        setFlag(32);
    }

    public void resetBorderColor(int position) {
        switch (position) {
            case 0:
                resetFlag(2);
                break;
            case 1:
                resetFlag(4);
                break;
            case 2:
                resetFlag(8);
                break;
            case 3:
                resetFlag(16);
                break;
            case 8:
                setBorderColor(-16777216);
                break;
        }
    }

    public void setBorderColor(int position, int borderColor) {
        switch (position) {
            case 0:
                this.mBorderLeftColor = borderColor;
                setFlag(2);
                break;
            case 1:
                this.mBorderTopColor = borderColor;
                setFlag(4);
                break;
            case 2:
                this.mBorderRightColor = borderColor;
                setFlag(8);
                break;
            case 3:
                this.mBorderBottomColor = borderColor;
                setFlag(16);
                break;
            case 8:
                setBorderColor(borderColor);
                break;
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
    }

    @Override // com.facebook.react.flat.AbstractDrawCommand
    protected void onDraw(Canvas canvas) {
        if (getBorderRadius() >= 0.5f || getPathEffectForBorderStyle() != null) {
            drawRoundedBorders(canvas);
        } else {
            drawRectangularBorders(canvas);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.flat.AbstractDrawBorder
    public DashPathEffect getPathEffectForBorderStyle() {
        if (isFlagSet(32)) {
            switch (this.mBorderStyle) {
                case 1:
                    this.mPathEffectForBorderStyle = createDashPathEffect(getBorderWidth());
                    break;
                case 2:
                    this.mPathEffectForBorderStyle = createDashPathEffect(getBorderWidth() * 3.0f);
                    break;
                default:
                    this.mPathEffectForBorderStyle = null;
                    break;
            }
            resetFlag(32);
        }
        return this.mPathEffectForBorderStyle;
    }

    private void drawRoundedBorders(Canvas canvas) {
        if (this.mBackgroundColor != 0) {
            PAINT.setColor(this.mBackgroundColor);
            canvas.drawPath(getPathForBorderRadius(), PAINT);
        }
        drawBorders(canvas);
    }

    private boolean isBorderColorDifferentAtIntersectionPoints() {
        return isFlagSet(4) || isFlagSet(16) || isFlagSet(2) || isFlagSet(8);
    }

    private void drawRectangularBorders(Canvas canvas) {
        int defaultColor = getBorderColor();
        float defaultWidth = getBorderWidth();
        float top = getTop();
        float topWidth = resolveWidth(this.mBorderTopWidth, defaultWidth);
        float bottomOfTheTop = top + topWidth;
        int topColor = resolveBorderColor(4, this.mBorderTopColor, defaultColor);
        float bottom = getBottom();
        float bottomWidth = resolveWidth(this.mBorderBottomWidth, defaultWidth);
        float topOfTheBottom = bottom - bottomWidth;
        int bottomColor = resolveBorderColor(16, this.mBorderBottomColor, defaultColor);
        float left = getLeft();
        float leftWidth = resolveWidth(this.mBorderLeftWidth, defaultWidth);
        float rightOfTheLeft = left + leftWidth;
        int leftColor = resolveBorderColor(2, this.mBorderLeftColor, defaultColor);
        float right = getRight();
        float rightWidth = resolveWidth(this.mBorderRightWidth, defaultWidth);
        float leftOfTheRight = right - rightWidth;
        int rightColor = resolveBorderColor(8, this.mBorderRightColor, defaultColor);
        boolean isDrawPathRequired = isBorderColorDifferentAtIntersectionPoints();
        if (isDrawPathRequired && this.mPathForBorder == null) {
            this.mPathForBorder = new Path();
        }
        if (Color.alpha(topColor) != 0 && topWidth != 0.0f) {
            PAINT.setColor(topColor);
            if (isDrawPathRequired) {
                updatePathForTopBorder(top, bottomOfTheTop, left, rightOfTheLeft, right, leftOfTheRight);
                canvas.drawPath(this.mPathForBorder, PAINT);
            } else {
                canvas.drawRect(left, top, right, bottomOfTheTop, PAINT);
            }
        }
        if (Color.alpha(bottomColor) != 0 && bottomWidth != 0.0f) {
            PAINT.setColor(bottomColor);
            if (isDrawPathRequired) {
                updatePathForBottomBorder(bottom, topOfTheBottom, left, rightOfTheLeft, right, leftOfTheRight);
                canvas.drawPath(this.mPathForBorder, PAINT);
            } else {
                canvas.drawRect(left, topOfTheBottom, right, bottom, PAINT);
            }
        }
        if (Color.alpha(leftColor) != 0 && leftWidth != 0.0f) {
            PAINT.setColor(leftColor);
            if (isDrawPathRequired) {
                updatePathForLeftBorder(top, bottomOfTheTop, bottom, topOfTheBottom, left, rightOfTheLeft);
                canvas.drawPath(this.mPathForBorder, PAINT);
            } else {
                canvas.drawRect(left, bottomOfTheTop, rightOfTheLeft, topOfTheBottom, PAINT);
            }
        }
        if (Color.alpha(rightColor) != 0 && rightWidth != 0.0f) {
            PAINT.setColor(rightColor);
            if (isDrawPathRequired) {
                updatePathForRightBorder(top, bottomOfTheTop, bottom, topOfTheBottom, right, leftOfTheRight);
                canvas.drawPath(this.mPathForBorder, PAINT);
            } else {
                canvas.drawRect(leftOfTheRight, bottomOfTheTop, right, topOfTheBottom, PAINT);
            }
        }
        if (Color.alpha(this.mBackgroundColor) != 0) {
            PAINT.setColor(this.mBackgroundColor);
            canvas.drawRect(rightOfTheLeft, bottomOfTheTop, leftOfTheRight, topOfTheBottom, PAINT);
        }
    }

    private void updatePathForTopBorder(float top, float bottomOfTheTop, float left, float rightOfTheLeft, float right, float leftOfTheRight) {
        if (this.mPathForBorder == null) {
            this.mPathForBorder = new Path();
        }
        this.mPathForBorder.reset();
        this.mPathForBorder.moveTo(left, top);
        this.mPathForBorder.lineTo(rightOfTheLeft, bottomOfTheTop);
        this.mPathForBorder.lineTo(leftOfTheRight, bottomOfTheTop);
        this.mPathForBorder.lineTo(right, top);
        this.mPathForBorder.lineTo(left, top);
    }

    private void updatePathForBottomBorder(float bottom, float topOfTheBottom, float left, float rightOfTheLeft, float right, float leftOfTheRight) {
        if (this.mPathForBorder == null) {
            this.mPathForBorder = new Path();
        }
        this.mPathForBorder.reset();
        this.mPathForBorder.moveTo(left, bottom);
        this.mPathForBorder.lineTo(right, bottom);
        this.mPathForBorder.lineTo(leftOfTheRight, topOfTheBottom);
        this.mPathForBorder.lineTo(rightOfTheLeft, topOfTheBottom);
        this.mPathForBorder.lineTo(left, bottom);
    }

    private void updatePathForLeftBorder(float top, float bottomOfTheTop, float bottom, float topOfTheBottom, float left, float rightOfTheLeft) {
        if (this.mPathForBorder == null) {
            this.mPathForBorder = new Path();
        }
        this.mPathForBorder.reset();
        this.mPathForBorder.moveTo(left, top);
        this.mPathForBorder.lineTo(rightOfTheLeft, bottomOfTheTop);
        this.mPathForBorder.lineTo(rightOfTheLeft, topOfTheBottom);
        this.mPathForBorder.lineTo(left, bottom);
        this.mPathForBorder.lineTo(left, top);
    }

    private void updatePathForRightBorder(float top, float bottomOfTheTop, float bottom, float topOfTheBottom, float right, float leftOfTheRight) {
        if (this.mPathForBorder == null) {
            this.mPathForBorder = new Path();
        }
        this.mPathForBorder.reset();
        this.mPathForBorder.moveTo(right, top);
        this.mPathForBorder.lineTo(right, bottom);
        this.mPathForBorder.lineTo(leftOfTheRight, topOfTheBottom);
        this.mPathForBorder.lineTo(leftOfTheRight, bottomOfTheTop);
        this.mPathForBorder.lineTo(right, top);
    }

    private int resolveBorderColor(int flag, int color, int defaultColor) {
        return isFlagSet(flag) ? color : defaultColor;
    }

    private static float resolveWidth(float width, float defaultWidth) {
        return (width == 0.0f || width != width) ? defaultWidth : width;
    }

    private static DashPathEffect createDashPathEffect(float borderWidth) {
        for (int i = 0; i < 4; i++) {
            TMP_FLOAT_ARRAY[i] = borderWidth;
        }
        return new DashPathEffect(TMP_FLOAT_ARRAY, 0.0f);
    }
}
