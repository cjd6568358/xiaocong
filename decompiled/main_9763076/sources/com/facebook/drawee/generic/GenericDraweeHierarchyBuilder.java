package com.facebook.drawee.generic;

import android.R;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.ScalingUtils;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GenericDraweeHierarchyBuilder {
    private ColorFilter mActualImageColorFilter;
    private PointF mActualImageFocusPoint;
    private Matrix mActualImageMatrix;
    private ScalingUtils.ScaleType mActualImageScaleType;
    private Drawable mBackground;
    private float mDesiredAspectRatio;
    private int mFadeDuration;
    private Drawable mFailureImage;
    private ScalingUtils.ScaleType mFailureImageScaleType;
    private List<Drawable> mOverlays;
    private Drawable mPlaceholderImage;
    private ScalingUtils.ScaleType mPlaceholderImageScaleType;
    private Drawable mPressedStateOverlay;
    private Drawable mProgressBarImage;
    private ScalingUtils.ScaleType mProgressBarImageScaleType;
    private Resources mResources;
    private Drawable mRetryImage;
    private ScalingUtils.ScaleType mRetryImageScaleType;
    private RoundingParams mRoundingParams;
    public static final ScalingUtils.ScaleType DEFAULT_SCALE_TYPE = ScalingUtils.ScaleType.CENTER_INSIDE;
    public static final ScalingUtils.ScaleType DEFAULT_ACTUAL_IMAGE_SCALE_TYPE = ScalingUtils.ScaleType.CENTER_CROP;

    public GenericDraweeHierarchyBuilder(Resources resources) {
        this.mResources = resources;
        init();
    }

    public static GenericDraweeHierarchyBuilder newInstance(Resources resources) {
        return new GenericDraweeHierarchyBuilder(resources);
    }

    private void init() {
        this.mFadeDuration = 300;
        this.mDesiredAspectRatio = 0.0f;
        this.mPlaceholderImage = null;
        this.mPlaceholderImageScaleType = DEFAULT_SCALE_TYPE;
        this.mRetryImage = null;
        this.mRetryImageScaleType = DEFAULT_SCALE_TYPE;
        this.mFailureImage = null;
        this.mFailureImageScaleType = DEFAULT_SCALE_TYPE;
        this.mProgressBarImage = null;
        this.mProgressBarImageScaleType = DEFAULT_SCALE_TYPE;
        this.mActualImageScaleType = DEFAULT_ACTUAL_IMAGE_SCALE_TYPE;
        this.mActualImageMatrix = null;
        this.mActualImageFocusPoint = null;
        this.mActualImageColorFilter = null;
        this.mBackground = null;
        this.mOverlays = null;
        this.mPressedStateOverlay = null;
        this.mRoundingParams = null;
    }

    public Resources getResources() {
        return this.mResources;
    }

    public GenericDraweeHierarchyBuilder setFadeDuration(int fadeDuration) {
        this.mFadeDuration = fadeDuration;
        return this;
    }

    public int getFadeDuration() {
        return this.mFadeDuration;
    }

    public GenericDraweeHierarchyBuilder setDesiredAspectRatio(float desiredAspectRatio) {
        this.mDesiredAspectRatio = desiredAspectRatio;
        return this;
    }

    public float getDesiredAspectRatio() {
        return this.mDesiredAspectRatio;
    }

    public GenericDraweeHierarchyBuilder setPlaceholderImage(Drawable placeholderDrawable) {
        this.mPlaceholderImage = placeholderDrawable;
        return this;
    }

    public Drawable getPlaceholderImage() {
        return this.mPlaceholderImage;
    }

    public GenericDraweeHierarchyBuilder setPlaceholderImageScaleType(ScalingUtils.ScaleType placeholderImageScaleType) {
        this.mPlaceholderImageScaleType = placeholderImageScaleType;
        return this;
    }

    public ScalingUtils.ScaleType getPlaceholderImageScaleType() {
        return this.mPlaceholderImageScaleType;
    }

    public GenericDraweeHierarchyBuilder setRetryImage(Drawable retryDrawable) {
        this.mRetryImage = retryDrawable;
        return this;
    }

    public Drawable getRetryImage() {
        return this.mRetryImage;
    }

    public GenericDraweeHierarchyBuilder setRetryImageScaleType(ScalingUtils.ScaleType retryImageScaleType) {
        this.mRetryImageScaleType = retryImageScaleType;
        return this;
    }

    public ScalingUtils.ScaleType getRetryImageScaleType() {
        return this.mRetryImageScaleType;
    }

    public GenericDraweeHierarchyBuilder setFailureImage(Drawable failureDrawable) {
        this.mFailureImage = failureDrawable;
        return this;
    }

    public Drawable getFailureImage() {
        return this.mFailureImage;
    }

    public GenericDraweeHierarchyBuilder setFailureImageScaleType(ScalingUtils.ScaleType failureImageScaleType) {
        this.mFailureImageScaleType = failureImageScaleType;
        return this;
    }

    public ScalingUtils.ScaleType getFailureImageScaleType() {
        return this.mFailureImageScaleType;
    }

    public GenericDraweeHierarchyBuilder setProgressBarImage(Drawable progressBarDrawable) {
        this.mProgressBarImage = progressBarDrawable;
        return this;
    }

    public Drawable getProgressBarImage() {
        return this.mProgressBarImage;
    }

    public GenericDraweeHierarchyBuilder setProgressBarImageScaleType(ScalingUtils.ScaleType progressBarImageScaleType) {
        this.mProgressBarImageScaleType = progressBarImageScaleType;
        return this;
    }

    public ScalingUtils.ScaleType getProgressBarImageScaleType() {
        return this.mProgressBarImageScaleType;
    }

    public GenericDraweeHierarchyBuilder setActualImageScaleType(ScalingUtils.ScaleType actualImageScaleType) {
        this.mActualImageScaleType = actualImageScaleType;
        this.mActualImageMatrix = null;
        return this;
    }

    public ScalingUtils.ScaleType getActualImageScaleType() {
        return this.mActualImageScaleType;
    }

    public Matrix getActualImageMatrix() {
        return this.mActualImageMatrix;
    }

    public PointF getActualImageFocusPoint() {
        return this.mActualImageFocusPoint;
    }

    public ColorFilter getActualImageColorFilter() {
        return this.mActualImageColorFilter;
    }

    public GenericDraweeHierarchyBuilder setBackground(Drawable background) {
        this.mBackground = background;
        return this;
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    public GenericDraweeHierarchyBuilder setOverlay(Drawable overlay) {
        if (overlay == null) {
            this.mOverlays = null;
        } else {
            this.mOverlays = Arrays.asList(overlay);
        }
        return this;
    }

    public List<Drawable> getOverlays() {
        return this.mOverlays;
    }

    public GenericDraweeHierarchyBuilder setPressedStateOverlay(Drawable drawable) {
        if (drawable == null) {
            this.mPressedStateOverlay = null;
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{R.attr.state_pressed}, drawable);
            this.mPressedStateOverlay = stateListDrawable;
        }
        return this;
    }

    public Drawable getPressedStateOverlay() {
        return this.mPressedStateOverlay;
    }

    public GenericDraweeHierarchyBuilder setRoundingParams(RoundingParams roundingParams) {
        this.mRoundingParams = roundingParams;
        return this;
    }

    public RoundingParams getRoundingParams() {
        return this.mRoundingParams;
    }

    private void validate() {
        if (this.mOverlays != null) {
            for (Drawable overlay : this.mOverlays) {
                Preconditions.checkNotNull(overlay);
            }
        }
    }

    public GenericDraweeHierarchy build() {
        validate();
        return new GenericDraweeHierarchy(this);
    }
}
