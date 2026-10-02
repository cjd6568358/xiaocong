package com.facebook.react.flat;

import android.graphics.Rect;
import android.os.Build;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactDrawableHelper;
import com.facebook.react.views.view.ReactViewManager;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class RCTViewManager extends FlatViewManager {
    private static final int CMD_HOTSPOT_UPDATE = 1;
    private static final int CMD_SET_PRESSED = 2;
    private static final int[] TMP_INT_ARRAY = new int[2];

    @Override // com.facebook.react.flat.FlatViewManager
    public /* bridge */ /* synthetic */ void removeAllViews(FlatViewGroup flatViewGroup) {
        super.removeAllViews(flatViewGroup);
    }

    @Override // com.facebook.react.flat.FlatViewManager
    public /* bridge */ /* synthetic */ void setBackgroundColor(FlatViewGroup flatViewGroup, int i) {
        super.setBackgroundColor(flatViewGroup, i);
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return ReactViewManager.REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("hotspotUpdate", 1, "setPressed", 2);
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.ViewManager
    public RCTView createShadowNodeInstance() {
        return new RCTView();
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager, com.facebook.react.uimanager.ViewManager
    public Class<RCTView> getShadowNodeClass() {
        return RCTView.class;
    }

    @ReactProp(name = "nativeBackgroundAndroid")
    public void setHotspot(FlatViewGroup view, ReadableMap bg) {
        view.setHotspot(bg == null ? null : ReactDrawableHelper.createDrawableFromJSDescription(view.getContext(), bg));
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void receiveCommand(FlatViewGroup view, int commandId, ReadableArray args) {
        switch (commandId) {
            case 1:
                if (args == null || args.size() != 2) {
                    throw new JSApplicationIllegalArgumentException("Illegal number of arguments for 'updateHotspot' command");
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    view.getLocationOnScreen(TMP_INT_ARRAY);
                    float x = PixelUtil.toPixelFromDIP(args.getDouble(0)) - TMP_INT_ARRAY[0];
                    float y = PixelUtil.toPixelFromDIP(args.getDouble(1)) - TMP_INT_ARRAY[1];
                    view.drawableHotspotChanged(x, y);
                    return;
                }
                return;
            case 2:
                if (args == null || args.size() != 1) {
                    throw new JSApplicationIllegalArgumentException("Illegal number of arguments for 'setPressed' command");
                }
                view.setPressed(args.getBoolean(0));
                return;
            default:
                return;
        }
    }

    @ReactProp(name = "needsOffscreenAlphaCompositing")
    public void setNeedsOffscreenAlphaCompositing(FlatViewGroup view, boolean needsOffscreenAlphaCompositing) {
        view.setNeedsOffscreenAlphaCompositing(needsOffscreenAlphaCompositing);
    }

    @ReactProp(name = "pointerEvents")
    public void setPointerEvents(FlatViewGroup view, String pointerEventsStr) {
        view.setPointerEvents(parsePointerEvents(pointerEventsStr));
    }

    @ReactProp(name = "removeClippedSubviews")
    public void setRemoveClippedSubviews(FlatViewGroup view, boolean removeClippedSubviews) {
        view.setRemoveClippedSubviews(removeClippedSubviews);
    }

    private static PointerEvents parsePointerEvents(String pointerEventsStr) {
        if (pointerEventsStr != null) {
            switch (pointerEventsStr) {
                case "none":
                    return PointerEvents.NONE;
                case "auto":
                    return PointerEvents.AUTO;
                case "box-none":
                    return PointerEvents.BOX_NONE;
                case "box-only":
                    return PointerEvents.BOX_ONLY;
            }
        }
        return PointerEvents.AUTO;
    }

    @ReactProp(name = "hitSlop")
    public void setHitSlop(FlatViewGroup view, ReadableMap hitSlop) {
        if (hitSlop == null) {
            view.setHitSlopRect(null);
        } else {
            view.setHitSlopRect(new Rect((int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("left")), (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("top")), (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("right")), (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("bottom"))));
        }
    }
}
