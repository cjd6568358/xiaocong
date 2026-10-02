package com.facebook.drawee.generic;

import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.DrawableParent;
import com.facebook.drawee.drawable.FadeDrawable;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.MatrixDrawable;
import com.facebook.drawee.drawable.ScaleTypeDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.SettableDraweeHierarchy;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GenericDraweeHierarchy implements SettableDraweeHierarchy {
    private final FadeDrawable mFadeDrawable;
    private final Resources mResources;
    private RoundingParams mRoundingParams;
    private final RootDrawable mTopLevelDrawable;
    private final Drawable mEmptyActualImageDrawable = new ColorDrawable(0);
    private final ForwardingDrawable mActualImageWrapper = new ForwardingDrawable(this.mEmptyActualImageDrawable);

    GenericDraweeHierarchy(GenericDraweeHierarchyBuilder builder) {
        this.mResources = builder.getResources();
        this.mRoundingParams = builder.getRoundingParams();
        int numOverlays = (builder.getOverlays() != null ? builder.getOverlays().size() : 1) + (builder.getPressedStateOverlay() != null ? 1 : 0);
        int numLayers = numOverlays + 6;
        Drawable[] layers = new Drawable[numLayers];
        layers[0] = buildBranch(builder.getBackground(), null);
        layers[1] = buildBranch(builder.getPlaceholderImage(), builder.getPlaceholderImageScaleType());
        layers[2] = buildActualImageBranch(this.mActualImageWrapper, builder.getActualImageScaleType(), builder.getActualImageFocusPoint(), builder.getActualImageMatrix(), builder.getActualImageColorFilter());
        layers[3] = buildBranch(builder.getProgressBarImage(), builder.getProgressBarImageScaleType());
        layers[4] = buildBranch(builder.getRetryImage(), builder.getRetryImageScaleType());
        layers[5] = buildBranch(builder.getFailureImage(), builder.getFailureImageScaleType());
        if (numOverlays > 0) {
            int index = 0;
            if (builder.getOverlays() != null) {
                for (Drawable overlay : builder.getOverlays()) {
                    layers[index + 6] = buildBranch(overlay, null);
                    index++;
                }
            } else {
                index = 1;
            }
            if (builder.getPressedStateOverlay() != null) {
                layers[index + 6] = buildBranch(builder.getPressedStateOverlay(), null);
            }
        }
        this.mFadeDrawable = new FadeDrawable(layers);
        this.mFadeDrawable.setTransitionDuration(builder.getFadeDuration());
        Drawable maybeRoundedDrawable = WrappingUtils.maybeWrapWithRoundedOverlayColor(this.mFadeDrawable, this.mRoundingParams);
        this.mTopLevelDrawable = new RootDrawable(maybeRoundedDrawable);
        this.mTopLevelDrawable.mutate();
        resetFade();
    }

    private Drawable buildActualImageBranch(Drawable drawable, ScalingUtils.ScaleType scaleType, PointF focusPoint, Matrix matrix, ColorFilter colorFilter) {
        drawable.setColorFilter(colorFilter);
        return WrappingUtils.maybeWrapWithMatrix(WrappingUtils.maybeWrapWithScaleType(drawable, scaleType, focusPoint), matrix);
    }

    private Drawable buildBranch(Drawable drawable, ScalingUtils.ScaleType scaleType) {
        return WrappingUtils.maybeWrapWithScaleType(WrappingUtils.maybeApplyLeafRounding(drawable, this.mRoundingParams, this.mResources), scaleType);
    }

    private void resetActualImages() {
        this.mActualImageWrapper.setDrawable(this.mEmptyActualImageDrawable);
    }

    private void resetFade() {
        if (this.mFadeDrawable != null) {
            this.mFadeDrawable.beginBatchMode();
            this.mFadeDrawable.fadeInAllLayers();
            fadeOutBranches();
            fadeInLayer(1);
            this.mFadeDrawable.finishTransitionImmediately();
            this.mFadeDrawable.endBatchMode();
        }
    }

    private void fadeOutBranches() {
        fadeOutLayer(1);
        fadeOutLayer(2);
        fadeOutLayer(3);
        fadeOutLayer(4);
        fadeOutLayer(5);
    }

    private void fadeInLayer(int index) {
        if (index >= 0) {
            this.mFadeDrawable.fadeInLayer(index);
        }
    }

    private void fadeOutLayer(int index) {
        if (index >= 0) {
            this.mFadeDrawable.fadeOutLayer(index);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setProgress(float progress) {
        Drawable drawable = getParentDrawableAtIndex(3).getDrawable();
        if (drawable != 0) {
            if (progress >= 0.999f) {
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).stop();
                }
                fadeOutLayer(3);
            } else {
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
                fadeInLayer(3);
            }
            drawable.setLevel(Math.round(10000.0f * progress));
        }
    }

    @Override // com.facebook.drawee.interfaces.DraweeHierarchy
    public Drawable getTopLevelDrawable() {
        return this.mTopLevelDrawable;
    }

    @Override // com.facebook.drawee.interfaces.SettableDraweeHierarchy
    public void reset() {
        resetActualImages();
        resetFade();
    }

    @Override // com.facebook.drawee.interfaces.SettableDraweeHierarchy
    public void setImage(Drawable drawable, float progress, boolean immediate) {
        Drawable drawable2 = WrappingUtils.maybeApplyLeafRounding(drawable, this.mRoundingParams, this.mResources);
        drawable2.mutate();
        this.mActualImageWrapper.setDrawable(drawable2);
        this.mFadeDrawable.beginBatchMode();
        fadeOutBranches();
        fadeInLayer(2);
        setProgress(progress);
        if (immediate) {
            this.mFadeDrawable.finishTransitionImmediately();
        }
        this.mFadeDrawable.endBatchMode();
    }

    @Override // com.facebook.drawee.interfaces.SettableDraweeHierarchy
    public void setProgress(float progress, boolean immediate) {
        this.mFadeDrawable.beginBatchMode();
        setProgress(progress);
        if (immediate) {
            this.mFadeDrawable.finishTransitionImmediately();
        }
        this.mFadeDrawable.endBatchMode();
    }

    @Override // com.facebook.drawee.interfaces.SettableDraweeHierarchy
    public void setFailure(Throwable throwable) {
        this.mFadeDrawable.beginBatchMode();
        fadeOutBranches();
        if (this.mFadeDrawable.getDrawable(5) != null) {
            fadeInLayer(5);
        } else {
            fadeInLayer(1);
        }
        this.mFadeDrawable.endBatchMode();
    }

    @Override // com.facebook.drawee.interfaces.SettableDraweeHierarchy
    public void setRetry(Throwable throwable) {
        this.mFadeDrawable.beginBatchMode();
        fadeOutBranches();
        if (this.mFadeDrawable.getDrawable(4) != null) {
            fadeInLayer(4);
        } else {
            fadeInLayer(1);
        }
        this.mFadeDrawable.endBatchMode();
    }

    @Override // com.facebook.drawee.interfaces.SettableDraweeHierarchy
    public void setControllerOverlay(Drawable drawable) {
        this.mTopLevelDrawable.setControllerOverlay(drawable);
    }

    private DrawableParent getParentDrawableAtIndex(int index) {
        DrawableParent parent = this.mFadeDrawable.getDrawableParentForIndex(index);
        if (parent.getDrawable() instanceof MatrixDrawable) {
            parent = (MatrixDrawable) parent.getDrawable();
        }
        if (parent.getDrawable() instanceof ScaleTypeDrawable) {
            return (ScaleTypeDrawable) parent.getDrawable();
        }
        return parent;
    }

    private void setChildDrawableAtIndex(int index, Drawable drawable) {
        if (drawable == null) {
            this.mFadeDrawable.setDrawable(index, null);
        } else {
            getParentDrawableAtIndex(index).setDrawable(WrappingUtils.maybeApplyLeafRounding(drawable, this.mRoundingParams, this.mResources));
        }
    }

    private ScaleTypeDrawable getScaleTypeDrawableAtIndex(int index) {
        DrawableParent parent = getParentDrawableAtIndex(index);
        return parent instanceof ScaleTypeDrawable ? (ScaleTypeDrawable) parent : WrappingUtils.wrapChildWithScaleType(parent, ScalingUtils.ScaleType.FIT_XY);
    }

    public void setFadeDuration(int durationMs) {
        this.mFadeDrawable.setTransitionDuration(durationMs);
    }

    public void setActualImageScaleType(ScalingUtils.ScaleType scaleType) {
        Preconditions.checkNotNull(scaleType);
        getScaleTypeDrawableAtIndex(2).setScaleType(scaleType);
    }

    public void setActualImageColorFilter(ColorFilter colorfilter) {
        this.mActualImageWrapper.setColorFilter(colorfilter);
    }

    public void setPlaceholderImage(Drawable drawable, ScalingUtils.ScaleType scaleType) {
        setChildDrawableAtIndex(1, drawable);
        getScaleTypeDrawableAtIndex(1).setScaleType(scaleType);
    }

    public void setRoundingParams(RoundingParams roundingParams) {
        this.mRoundingParams = roundingParams;
        WrappingUtils.updateOverlayColorRounding(this.mTopLevelDrawable, this.mRoundingParams);
        for (int i = 0; i < this.mFadeDrawable.getNumberOfLayers(); i++) {
            WrappingUtils.updateLeafRounding(getParentDrawableAtIndex(i), this.mRoundingParams, this.mResources);
        }
    }

    public RoundingParams getRoundingParams() {
        return this.mRoundingParams;
    }
}
