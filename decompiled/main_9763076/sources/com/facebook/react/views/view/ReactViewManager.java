package com.facebook.react.views.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.YogaConstants;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactViewManager extends ViewGroupManager<ReactViewGroup> {
    private static final int CMD_HOTSPOT_UPDATE = 1;
    private static final int CMD_SET_PRESSED = 2;
    public static final String REACT_CLASS = "RCTView";
    private static final int[] SPACING_TYPES = {8, 0, 2, 1, 3};

    @ReactProp(name = "accessible")
    public void setAccessible(ReactViewGroup view, boolean accessible) {
        view.setFocusable(accessible);
    }

    @ReactPropGroup(defaultFloat = Float.NaN, names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius"})
    public void setBorderRadius(ReactViewGroup view, int index, float borderRadius) {
        if (!YogaConstants.isUndefined(borderRadius)) {
            borderRadius = PixelUtil.toPixelFromDIP(borderRadius);
        }
        if (index == 0) {
            view.setBorderRadius(borderRadius);
        } else {
            view.setBorderRadius(borderRadius, index - 1);
        }
    }

    @ReactProp(name = "borderStyle")
    public void setBorderStyle(ReactViewGroup view, String borderStyle) {
        view.setBorderStyle(borderStyle);
    }

    @ReactProp(name = "hitSlop")
    public void setHitSlop(ReactViewGroup view, ReadableMap hitSlop) {
        if (hitSlop == null) {
            view.setHitSlopRect(null);
        } else {
            view.setHitSlopRect(new Rect(hitSlop.hasKey("left") ? (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("left")) : 0, hitSlop.hasKey("top") ? (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("top")) : 0, hitSlop.hasKey("right") ? (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("right")) : 0, hitSlop.hasKey("bottom") ? (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("bottom")) : 0));
        }
    }

    @ReactProp(name = "pointerEvents")
    public void setPointerEvents(ReactViewGroup view, String pointerEventsStr) {
        if (pointerEventsStr == null) {
            view.setPointerEvents(PointerEvents.AUTO);
        } else {
            PointerEvents pointerEvents = PointerEvents.valueOf(pointerEventsStr.toUpperCase(Locale.US).replace("-", "_"));
            view.setPointerEvents(pointerEvents);
        }
    }

    @ReactProp(name = "nativeBackgroundAndroid")
    public void setNativeBackground(ReactViewGroup view, ReadableMap bg) {
        view.setTranslucentBackgroundDrawable(bg == null ? null : ReactDrawableHelper.createDrawableFromJSDescription(view.getContext(), bg));
    }

    @ReactProp(name = "nativeForegroundAndroid")
    @TargetApi(23)
    public void setNativeForeground(ReactViewGroup view, ReadableMap fg) {
        view.setForeground(fg == null ? null : ReactDrawableHelper.createDrawableFromJSDescription(view.getContext(), fg));
    }

    @ReactProp(name = "removeClippedSubviews")
    public void setRemoveClippedSubviews(ReactViewGroup view, boolean removeClippedSubviews) {
        view.setRemoveClippedSubviews(removeClippedSubviews);
    }

    @ReactProp(name = "needsOffscreenAlphaCompositing")
    public void setNeedsOffscreenAlphaCompositing(ReactViewGroup view, boolean needsOffscreenAlphaCompositing) {
        view.setNeedsOffscreenAlphaCompositing(needsOffscreenAlphaCompositing);
    }

    @ReactPropGroup(defaultFloat = Float.NaN, names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"})
    public void setBorderWidth(ReactViewGroup view, int index, float width) {
        if (!YogaConstants.isUndefined(width)) {
            width = PixelUtil.toPixelFromDIP(width);
        }
        view.setBorderWidth(SPACING_TYPES[index], width);
    }

    @ReactPropGroup(customType = "Color", names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"})
    public void setBorderColor(ReactViewGroup view, int index, Integer color) {
        float rgbComponent = color == null ? Float.NaN : color.intValue() & 16777215;
        float alphaComponent = color != null ? color.intValue() >>> 24 : Float.NaN;
        view.setBorderColor(SPACING_TYPES[index], rgbComponent, alphaComponent);
    }

    @ReactProp(name = "collapsable")
    public void setCollapsable(ReactViewGroup view, boolean collapsable) {
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public ReactViewGroup createViewInstance(ThemedReactContext context) {
        return new ReactViewGroup(context);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("hotspotUpdate", 1, "setPressed", 2);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void receiveCommand(ReactViewGroup root, int commandId, ReadableArray args) {
        switch (commandId) {
            case 1:
                if (args == null || args.size() != 2) {
                    throw new JSApplicationIllegalArgumentException("Illegal number of arguments for 'updateHotspot' command");
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    float x = PixelUtil.toPixelFromDIP(args.getDouble(0));
                    float y = PixelUtil.toPixelFromDIP(args.getDouble(1));
                    root.drawableHotspotChanged(x, y);
                    return;
                }
                return;
            case 2:
                if (args == null || args.size() != 1) {
                    throw new JSApplicationIllegalArgumentException("Illegal number of arguments for 'setPressed' command");
                }
                root.setPressed(args.getBoolean(0));
                return;
            default:
                return;
        }
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public void addView(ReactViewGroup parent, View child, int index) {
        boolean removeClippedSubviews = parent.getRemoveClippedSubviews();
        if (removeClippedSubviews) {
            parent.addViewWithSubviewClippingEnabled(child, index);
        } else {
            parent.addView(child, index);
        }
        reorderChildrenByZIndex(parent);
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public int getChildCount(ReactViewGroup parent) {
        boolean removeClippedSubviews = parent.getRemoveClippedSubviews();
        return removeClippedSubviews ? parent.getAllChildrenCount() : parent.getChildCount();
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public View getChildAt(ReactViewGroup parent, int index) {
        boolean removeClippedSubviews = parent.getRemoveClippedSubviews();
        return removeClippedSubviews ? parent.getChildAtWithSubviewClippingEnabled(index) : parent.getChildAt(index);
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public void removeViewAt(ReactViewGroup parent, int index) {
        boolean removeClippedSubviews = parent.getRemoveClippedSubviews();
        if (removeClippedSubviews) {
            View child = getChildAt(parent, index);
            if (child.getParent() != null) {
                parent.removeView(child);
            }
            parent.removeViewWithSubviewClippingEnabled(child);
            return;
        }
        parent.removeViewAt(index);
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public void removeAllViews(ReactViewGroup parent) {
        boolean removeClippedSubviews = parent.getRemoveClippedSubviews();
        if (removeClippedSubviews) {
            parent.removeAllViewsWithSubviewClippingEnabled();
        } else {
            parent.removeAllViews();
        }
    }
}
