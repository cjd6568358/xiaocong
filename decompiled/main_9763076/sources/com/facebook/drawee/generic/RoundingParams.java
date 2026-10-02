package com.facebook.drawee.generic;

import com.facebook.common.internal.Preconditions;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RoundingParams {
    private RoundingMethod mRoundingMethod = RoundingMethod.BITMAP_ONLY;
    private boolean mRoundAsCircle = false;
    private float[] mCornersRadii = null;
    private int mOverlayColor = 0;
    private float mBorderWidth = 0.0f;
    private int mBorderColor = 0;
    private float mPadding = 0.0f;

    public enum RoundingMethod {
        OVERLAY_COLOR,
        BITMAP_ONLY
    }

    public RoundingParams setRoundAsCircle(boolean roundAsCircle) {
        this.mRoundAsCircle = roundAsCircle;
        return this;
    }

    public boolean getRoundAsCircle() {
        return this.mRoundAsCircle;
    }

    public RoundingParams setCornersRadius(float radius) {
        Arrays.fill(getOrCreateRoundedCornersRadii(), radius);
        return this;
    }

    public RoundingParams setCornersRadii(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        float[] radii = getOrCreateRoundedCornersRadii();
        radii[1] = topLeft;
        radii[0] = topLeft;
        radii[3] = topRight;
        radii[2] = topRight;
        radii[5] = bottomRight;
        radii[4] = bottomRight;
        radii[7] = bottomLeft;
        radii[6] = bottomLeft;
        return this;
    }

    public float[] getCornersRadii() {
        return this.mCornersRadii;
    }

    public RoundingParams setRoundingMethod(RoundingMethod roundingMethod) {
        this.mRoundingMethod = roundingMethod;
        return this;
    }

    public RoundingMethod getRoundingMethod() {
        return this.mRoundingMethod;
    }

    public RoundingParams setOverlayColor(int overlayColor) {
        this.mOverlayColor = overlayColor;
        this.mRoundingMethod = RoundingMethod.OVERLAY_COLOR;
        return this;
    }

    public int getOverlayColor() {
        return this.mOverlayColor;
    }

    private float[] getOrCreateRoundedCornersRadii() {
        if (this.mCornersRadii == null) {
            this.mCornersRadii = new float[8];
        }
        return this.mCornersRadii;
    }

    public static RoundingParams fromCornersRadius(float radius) {
        return new RoundingParams().setCornersRadius(radius);
    }

    public RoundingParams setBorderWidth(float width) {
        Preconditions.checkArgument(width >= 0.0f, "the border width cannot be < 0");
        this.mBorderWidth = width;
        return this;
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    public RoundingParams setBorderColor(int color) {
        this.mBorderColor = color;
        return this;
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public RoundingParams setBorder(int color, float width) {
        Preconditions.checkArgument(width >= 0.0f, "the border width cannot be < 0");
        this.mBorderWidth = width;
        this.mBorderColor = color;
        return this;
    }

    public RoundingParams setPadding(float padding) {
        Preconditions.checkArgument(padding >= 0.0f, "the padding cannot be < 0");
        this.mPadding = padding;
        return this;
    }

    public float getPadding() {
        return this.mPadding;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoundingParams that = (RoundingParams) o;
        if (this.mRoundAsCircle == that.mRoundAsCircle && this.mOverlayColor == that.mOverlayColor && Float.compare(that.mBorderWidth, this.mBorderWidth) == 0 && this.mBorderColor == that.mBorderColor && Float.compare(that.mPadding, this.mPadding) == 0 && this.mRoundingMethod == that.mRoundingMethod) {
            return Arrays.equals(this.mCornersRadii, that.mCornersRadii);
        }
        return false;
    }

    public int hashCode() {
        int result = this.mRoundingMethod != null ? this.mRoundingMethod.hashCode() : 0;
        return (((((((((((result * 31) + (this.mRoundAsCircle ? 1 : 0)) * 31) + (this.mCornersRadii != null ? Arrays.hashCode(this.mCornersRadii) : 0)) * 31) + this.mOverlayColor) * 31) + (this.mBorderWidth != 0.0f ? Float.floatToIntBits(this.mBorderWidth) : 0)) * 31) + this.mBorderColor) * 31) + (this.mPadding != 0.0f ? Float.floatToIntBits(this.mPadding) : 0);
    }
}
