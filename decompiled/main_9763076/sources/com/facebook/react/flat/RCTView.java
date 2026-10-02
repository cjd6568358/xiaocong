package com.facebook.react.flat;

import android.graphics.Rect;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class RCTView extends FlatShadowNode {
    private static final int[] SPACING_TYPES = {8, 0, 2, 1, 3};
    private DrawBorder mDrawBorder;
    private Rect mHitSlop;

    RCTView() {
    }

    @Override // com.facebook.react.flat.FlatShadowNode
    public void setBackgroundColor(int backgroundColor) {
        getMutableBorder().setBackgroundColor(backgroundColor);
    }

    @Override // com.facebook.react.uimanager.LayoutShadowNode
    public void setBorderWidths(int index, float borderWidth) {
        super.setBorderWidths(index, borderWidth);
        int type = SPACING_TYPES[index];
        getMutableBorder().setBorderWidth(type, PixelUtil.toPixelFromDIP(borderWidth));
    }

    @ReactProp(name = "nativeBackgroundAndroid")
    public void setHotspot(ReadableMap bg) {
        if (bg != null) {
            forceMountToView();
        }
    }

    @ReactPropGroup(customType = "Color", defaultDouble = Double.NaN, names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"})
    public void setBorderColor(int index, double color) {
        int type = SPACING_TYPES[index];
        if (Double.isNaN(color)) {
            getMutableBorder().resetBorderColor(type);
        } else {
            getMutableBorder().setBorderColor(type, (int) color);
        }
    }

    @ReactProp(name = "borderRadius")
    public void setBorderRadius(float borderRadius) {
        this.mClipRadius = borderRadius;
        if (this.mClipToBounds && borderRadius > 0.5f) {
            forceMountToView();
        }
        getMutableBorder().setBorderRadius(PixelUtil.toPixelFromDIP(borderRadius));
    }

    @ReactProp(name = "borderStyle")
    public void setBorderStyle(String borderStyle) {
        getMutableBorder().setBorderStyle(borderStyle);
    }

    @ReactProp(name = "hitSlop")
    public void setHitSlop(ReadableMap hitSlop) {
        if (hitSlop == null) {
            this.mHitSlop = null;
        } else {
            this.mHitSlop = new Rect((int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("left")), (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("top")), (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("right")), (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("bottom")));
        }
    }

    @ReactProp(name = "pointerEvents")
    public void setPointerEvents(String pointerEventsStr) {
        forceMountToView();
    }

    private DrawBorder getMutableBorder() {
        if (this.mDrawBorder == null) {
            this.mDrawBorder = new DrawBorder();
        } else if (this.mDrawBorder.isFrozen()) {
            this.mDrawBorder = (DrawBorder) this.mDrawBorder.mutableCopy();
        }
        invalidate();
        return this.mDrawBorder;
    }
}
