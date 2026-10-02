package com.facebook.react.flat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Animatable;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.imagehelper.ImageSource;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class DrawImageWithDrawee extends AbstractDrawCommand implements ControllerListener, DrawImage {
    private int mBorderColor;
    private float mBorderRadius;
    private float mBorderWidth;
    private FlatViewGroup.InvalidateCallback mCallback;
    private PorterDuffColorFilter mColorFilter;
    private boolean mProgressiveRenderingEnabled;
    private int mReactTag;
    private DraweeRequestHelper mRequestHelper;
    private final List<ImageSource> mSources = new LinkedList();
    private ScalingUtils.ScaleType mScaleType = ImageResizeMode.defaultValue();
    private int mFadeDuration = 300;

    DrawImageWithDrawee() {
    }

    @Override // com.facebook.react.flat.DrawImage
    public void setSource(Context context, ReadableArray sources) {
        this.mSources.clear();
        if (sources != null && sources.size() != 0) {
            if (sources.size() == 1) {
                this.mSources.add(new ImageSource(context, sources.getMap(0).getString("uri")));
                return;
            }
            for (int idx = 0; idx < sources.size(); idx++) {
                ReadableMap source = sources.getMap(idx);
                this.mSources.add(new ImageSource(context, source.getString("uri"), source.getDouble(IMediaFormat.KEY_WIDTH), source.getDouble(IMediaFormat.KEY_HEIGHT)));
            }
        }
    }

    @Override // com.facebook.react.flat.DrawImage
    public void setTintColor(int tintColor) {
        if (tintColor == 0) {
            this.mColorFilter = null;
        } else {
            this.mColorFilter = new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override // com.facebook.react.flat.DrawImage
    public void setScaleType(ScalingUtils.ScaleType scaleType) {
        this.mScaleType = scaleType;
    }

    @Override // com.facebook.react.flat.DrawImage
    public ScalingUtils.ScaleType getScaleType() {
        return this.mScaleType;
    }

    @Override // com.facebook.react.flat.DrawImage
    public void setBorderWidth(float borderWidth) {
        this.mBorderWidth = borderWidth;
    }

    @Override // com.facebook.react.flat.DrawImage
    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    @Override // com.facebook.react.flat.DrawImage
    public void setBorderRadius(float borderRadius) {
        this.mBorderRadius = borderRadius;
    }

    @Override // com.facebook.react.flat.DrawImage
    public float getBorderRadius() {
        return this.mBorderRadius;
    }

    @Override // com.facebook.react.flat.DrawImage
    public void setBorderColor(int borderColor) {
        this.mBorderColor = borderColor;
    }

    @Override // com.facebook.react.flat.DrawImage
    public int getBorderColor() {
        return this.mBorderColor;
    }

    @Override // com.facebook.react.flat.DrawImage
    public void setFadeDuration(int fadeDuration) {
        this.mFadeDuration = fadeDuration;
    }

    @Override // com.facebook.react.flat.DrawImage
    public void setProgressiveRenderingEnabled(boolean enabled) {
        this.mProgressiveRenderingEnabled = enabled;
    }

    @Override // com.facebook.react.flat.DrawImage
    public void setReactTag(int reactTag) {
        this.mReactTag = reactTag;
    }

    @Override // com.facebook.react.flat.AbstractDrawCommand
    public void onDraw(Canvas canvas) {
        if (this.mRequestHelper != null) {
            this.mRequestHelper.getDrawable().draw(canvas);
        }
    }

    @Override // com.facebook.react.flat.AttachDetachListener
    public void onAttached(FlatViewGroup.InvalidateCallback callback) {
        this.mCallback = callback;
        if (this.mRequestHelper == null) {
            throw new RuntimeException("No DraweeRequestHelper - width: " + (getRight() - getLeft()) + " - height: " + (getBottom() - getTop()) + " - number of sources: " + this.mSources.size());
        }
        GenericDraweeHierarchy hierarchy = this.mRequestHelper.getHierarchy();
        RoundingParams roundingParams = hierarchy.getRoundingParams();
        if (shouldDisplayBorder()) {
            if (roundingParams == null) {
                roundingParams = new RoundingParams();
            }
            roundingParams.setBorder(this.mBorderColor, this.mBorderWidth);
            roundingParams.setCornersRadius(this.mBorderRadius);
            hierarchy.setRoundingParams(roundingParams);
        } else if (roundingParams != null) {
            hierarchy.setRoundingParams(null);
        }
        hierarchy.setActualImageScaleType(this.mScaleType);
        hierarchy.setActualImageColorFilter(this.mColorFilter);
        hierarchy.setFadeDuration(this.mFadeDuration);
        hierarchy.getTopLevelDrawable().setBounds(Math.round(getLeft()), Math.round(getTop()), Math.round(getRight()), Math.round(getBottom()));
        this.mRequestHelper.attach(callback);
    }

    @Override // com.facebook.react.flat.AttachDetachListener
    public void onDetached() {
        if (this.mRequestHelper != null) {
            this.mRequestHelper.detach();
        }
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public void onSubmit(String id, Object callerContext) {
        if (this.mCallback != null && this.mReactTag != 0) {
            this.mCallback.dispatchImageLoadEvent(this.mReactTag, 4);
        }
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
        if (this.mCallback != null && this.mReactTag != 0) {
            this.mCallback.dispatchImageLoadEvent(this.mReactTag, 2);
            this.mCallback.dispatchImageLoadEvent(this.mReactTag, 3);
        }
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public void onIntermediateImageSet(String id, Object imageInfo) {
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public void onIntermediateImageFailed(String id, Throwable throwable) {
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public void onFailure(String id, Throwable throwable) {
        if (this.mCallback != null && this.mReactTag != 0) {
            this.mCallback.dispatchImageLoadEvent(this.mReactTag, 1);
            this.mCallback.dispatchImageLoadEvent(this.mReactTag, 3);
        }
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public void onRelease(String id) {
    }

    private boolean shouldDisplayBorder() {
        return this.mBorderColor != 0 || this.mBorderRadius >= 0.5f;
    }
}
