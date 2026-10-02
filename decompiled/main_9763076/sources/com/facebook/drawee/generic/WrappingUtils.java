package com.facebook.drawee.generic;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.DrawableParent;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.LightBitmapDrawable;
import com.facebook.drawee.drawable.MatrixDrawable;
import com.facebook.drawee.drawable.Rounded;
import com.facebook.drawee.drawable.RoundedBitmapDrawable;
import com.facebook.drawee.drawable.RoundedColorDrawable;
import com.facebook.drawee.drawable.RoundedCornersDrawable;
import com.facebook.drawee.drawable.RoundedLightBitmapDrawable;
import com.facebook.drawee.drawable.ScaleTypeDrawable;
import com.facebook.drawee.drawable.ScalingUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WrappingUtils {
    private static final Drawable sEmptyDrawable = new ColorDrawable(0);

    static Drawable maybeWrapWithScaleType(Drawable drawable, ScalingUtils.ScaleType scaleType) {
        return maybeWrapWithScaleType(drawable, scaleType, null);
    }

    static Drawable maybeWrapWithScaleType(Drawable drawable, ScalingUtils.ScaleType scaleType, PointF focusPoint) {
        if (drawable == null || scaleType == null) {
            return drawable;
        }
        ScaleTypeDrawable scaleTypeDrawable = new ScaleTypeDrawable(drawable, scaleType);
        if (focusPoint != null) {
            scaleTypeDrawable.setFocusPoint(focusPoint);
            return scaleTypeDrawable;
        }
        return scaleTypeDrawable;
    }

    static Drawable maybeWrapWithMatrix(Drawable drawable, Matrix matrix) {
        return (drawable == null || matrix == null) ? drawable : new MatrixDrawable(drawable, matrix);
    }

    static ScaleTypeDrawable wrapChildWithScaleType(DrawableParent parent, ScalingUtils.ScaleType scaleType) {
        Drawable child = maybeWrapWithScaleType(parent.setDrawable(sEmptyDrawable), scaleType);
        parent.setDrawable(child);
        Preconditions.checkNotNull(child, "Parent has no child drawable!");
        return (ScaleTypeDrawable) child;
    }

    static void updateOverlayColorRounding(DrawableParent parent, RoundingParams roundingParams) {
        Drawable child = parent.getDrawable();
        if (roundingParams != null && roundingParams.getRoundingMethod() == RoundingParams.RoundingMethod.OVERLAY_COLOR) {
            if (child instanceof RoundedCornersDrawable) {
                RoundedCornersDrawable roundedCornersDrawable = (RoundedCornersDrawable) child;
                applyRoundingParams(roundedCornersDrawable, roundingParams);
                roundedCornersDrawable.setOverlayColor(roundingParams.getOverlayColor());
                return;
            }
            parent.setDrawable(maybeWrapWithRoundedOverlayColor(parent.setDrawable(sEmptyDrawable), roundingParams));
            return;
        }
        if (child instanceof RoundedCornersDrawable) {
            parent.setDrawable(((RoundedCornersDrawable) child).setCurrent(sEmptyDrawable));
            sEmptyDrawable.setCallback(null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static void updateLeafRounding(DrawableParent parent, RoundingParams roundingParams, Resources resources) {
        DrawableParent parent2 = findDrawableParentForLeaf(parent);
        Drawable drawable = parent2.getDrawable();
        if (roundingParams != null && roundingParams.getRoundingMethod() == RoundingParams.RoundingMethod.BITMAP_ONLY) {
            if (drawable instanceof Rounded) {
                Rounded rounded = (Rounded) drawable;
                applyRoundingParams(rounded, roundingParams);
                return;
            } else {
                if (drawable != 0) {
                    parent2.setDrawable(sEmptyDrawable);
                    Drawable rounded2 = applyLeafRounding(drawable, roundingParams, resources);
                    parent2.setDrawable(rounded2);
                    return;
                }
                return;
            }
        }
        if (drawable instanceof Rounded) {
            resetRoundingParams((Rounded) drawable);
        }
    }

    static Drawable maybeWrapWithRoundedOverlayColor(Drawable drawable, RoundingParams roundingParams) {
        if (drawable == null || roundingParams == null || roundingParams.getRoundingMethod() != RoundingParams.RoundingMethod.OVERLAY_COLOR) {
            return drawable;
        }
        RoundedCornersDrawable roundedCornersDrawable = new RoundedCornersDrawable(drawable);
        applyRoundingParams(roundedCornersDrawable, roundingParams);
        roundedCornersDrawable.setOverlayColor(roundingParams.getOverlayColor());
        return roundedCornersDrawable;
    }

    static Drawable maybeApplyLeafRounding(Drawable drawable, RoundingParams roundingParams, Resources resources) {
        if (drawable != null && roundingParams != null && roundingParams.getRoundingMethod() == RoundingParams.RoundingMethod.BITMAP_ONLY) {
            if (drawable instanceof ForwardingDrawable) {
                DrawableParent parent = findDrawableParentForLeaf((ForwardingDrawable) drawable);
                Drawable child = parent.setDrawable(sEmptyDrawable);
                parent.setDrawable(applyLeafRounding(child, roundingParams, resources));
                return drawable;
            }
            return applyLeafRounding(drawable, roundingParams, resources);
        }
        return drawable;
    }

    private static Drawable applyLeafRounding(Drawable drawable, RoundingParams roundingParams, Resources resources) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            RoundedBitmapDrawable roundedBitmapDrawable = new RoundedBitmapDrawable(resources, bitmapDrawable.getBitmap(), bitmapDrawable.getPaint());
            applyRoundingParams(roundedBitmapDrawable, roundingParams);
            return roundedBitmapDrawable;
        }
        if (drawable instanceof LightBitmapDrawable) {
            LightBitmapDrawable lightBitmapDrawable = (LightBitmapDrawable) drawable;
            RoundedLightBitmapDrawable roundedBitmapDrawable2 = new RoundedLightBitmapDrawable(resources, lightBitmapDrawable.getBitmap(), lightBitmapDrawable.getPaint());
            applyRoundingParams(roundedBitmapDrawable2, roundingParams);
            return roundedBitmapDrawable2;
        }
        if (!(drawable instanceof ColorDrawable) || Build.VERSION.SDK_INT < 11) {
            return drawable;
        }
        RoundedColorDrawable roundedColorDrawable = RoundedColorDrawable.fromColorDrawable((ColorDrawable) drawable);
        applyRoundingParams(roundedColorDrawable, roundingParams);
        return roundedColorDrawable;
    }

    static void applyRoundingParams(Rounded rounded, RoundingParams roundingParams) {
        rounded.setCircle(roundingParams.getRoundAsCircle());
        rounded.setRadii(roundingParams.getCornersRadii());
        rounded.setBorder(roundingParams.getBorderColor(), roundingParams.getBorderWidth());
        rounded.setPadding(roundingParams.getPadding());
    }

    static void resetRoundingParams(Rounded rounded) {
        rounded.setCircle(false);
        rounded.setRadius(0.0f);
        rounded.setBorder(0, 0.0f);
        rounded.setPadding(0.0f);
    }

    static DrawableParent findDrawableParentForLeaf(DrawableParent parent) {
        while (true) {
            Object drawable = parent.getDrawable();
            if (drawable == parent || !(drawable instanceof DrawableParent)) {
                break;
            }
            parent = (DrawableParent) drawable;
        }
        return parent;
    }
}
