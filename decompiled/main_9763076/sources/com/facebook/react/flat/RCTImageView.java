package com.facebook.react.flat;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.flat.AbstractDrawCommand;
import com.facebook.react.flat.DrawImage;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ImageResizeMode;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class RCTImageView<T extends AbstractDrawCommand & DrawImage> extends FlatShadowNode {
    static Object sCallerContext = RCTImageView.class;
    private T mDrawImage;

    static Object getCallerContext() {
        return sCallerContext;
    }

    RCTImageView(T drawImage) {
        this.mDrawImage = drawImage;
    }

    @ReactProp(name = "shouldNotifyLoadEvents")
    public void setShouldNotifyLoadEvents(boolean shouldNotifyLoadEvents) {
        ((DrawImage) getMutableDrawImage()).setReactTag(shouldNotifyLoadEvents ? getReactTag() : 0);
    }

    @ReactProp(name = "src")
    public void setSource(ReadableArray sources) {
        ((DrawImage) getMutableDrawImage()).setSource(getThemedContext(), sources);
    }

    @ReactProp(name = "tintColor")
    public void setTintColor(int tintColor) {
        ((DrawImage) getMutableDrawImage()).setTintColor(tintColor);
    }

    @ReactProp(name = "resizeMode")
    public void setResizeMode(String resizeMode) {
        ScalingUtils.ScaleType scaleType = ImageResizeMode.toScaleType(resizeMode);
        if (this.mDrawImage.getScaleType() != scaleType) {
            ((DrawImage) getMutableDrawImage()).setScaleType(scaleType);
        }
    }

    @ReactProp(customType = "Color", name = "borderColor")
    public void setBorderColor(int borderColor) {
        if (this.mDrawImage.getBorderColor() != borderColor) {
            ((DrawImage) getMutableDrawImage()).setBorderColor(borderColor);
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void setBorder(int spacingType, float borderWidth) {
        super.setBorder(spacingType, borderWidth);
        if (spacingType == 8 && this.mDrawImage.getBorderWidth() != borderWidth) {
            ((DrawImage) getMutableDrawImage()).setBorderWidth(borderWidth);
        }
    }

    @ReactProp(name = "borderRadius")
    public void setBorderRadius(float borderRadius) {
        if (this.mDrawImage.getBorderRadius() != borderRadius) {
            ((DrawImage) getMutableDrawImage()).setBorderRadius(PixelUtil.toPixelFromDIP(borderRadius));
        }
    }

    @ReactProp(name = "fadeDuration")
    public void setFadeDuration(int durationMs) {
        ((DrawImage) getMutableDrawImage()).setFadeDuration(durationMs);
    }

    @ReactProp(name = "progressiveRenderingEnabled")
    public void setProgressiveRenderingEnabled(boolean enabled) {
        ((DrawImage) getMutableDrawImage()).setProgressiveRenderingEnabled(enabled);
    }

    private T getMutableDrawImage() {
        if (this.mDrawImage.isFrozen()) {
            this.mDrawImage = (T) this.mDrawImage.mutableCopy();
            invalidate();
        }
        return this.mDrawImage;
    }
}
