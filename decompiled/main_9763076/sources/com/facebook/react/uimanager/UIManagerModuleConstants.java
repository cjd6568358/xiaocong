package com.facebook.react.uimanager;

import android.util.DisplayMetrics;
import android.widget.ImageView;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.events.TouchEventType;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class UIManagerModuleConstants {
    static Map getBubblingEventTypeConstants() {
        return MapBuilder.builder().put("topChange", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onChange", "captured", "onChangeCapture"))).put("topSelect", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onSelect", "captured", "onSelectCapture"))).put(TouchEventType.START.getJSEventName(), MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onTouchStart", "captured", "onTouchStartCapture"))).put(TouchEventType.MOVE.getJSEventName(), MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onTouchMove", "captured", "onTouchMoveCapture"))).put(TouchEventType.END.getJSEventName(), MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onTouchEnd", "captured", "onTouchEndCapture"))).build();
    }

    static Map getDirectEventTypeConstants() {
        return MapBuilder.builder().put("topContentSizeChange", MapBuilder.of("registrationName", "onContentSizeChange")).put("topLayout", MapBuilder.of("registrationName", "onLayout")).put("topLoadingError", MapBuilder.of("registrationName", "onLoadingError")).put("topLoadingFinish", MapBuilder.of("registrationName", "onLoadingFinish")).put("topLoadingStart", MapBuilder.of("registrationName", "onLoadingStart")).put("topSelectionChange", MapBuilder.of("registrationName", "onSelectionChange")).put("topMessage", MapBuilder.of("registrationName", "onMessage")).build();
    }

    public static Map<String, Object> getConstants() {
        HashMap<String, Object> constants = new HashMap<>();
        constants.put("UIView", MapBuilder.of("ContentMode", MapBuilder.of("ScaleAspectFit", Integer.valueOf(ImageView.ScaleType.FIT_CENTER.ordinal()), "ScaleAspectFill", Integer.valueOf(ImageView.ScaleType.CENTER_CROP.ordinal()), "ScaleAspectCenter", Integer.valueOf(ImageView.ScaleType.CENTER_INSIDE.ordinal()))));
        DisplayMetrics displayMetrics = DisplayMetricsHolder.getWindowDisplayMetrics();
        DisplayMetrics screenDisplayMetrics = DisplayMetricsHolder.getScreenDisplayMetrics();
        constants.put("Dimensions", MapBuilder.of("windowPhysicalPixels", MapBuilder.of(IMediaFormat.KEY_WIDTH, Integer.valueOf(displayMetrics.widthPixels), IMediaFormat.KEY_HEIGHT, Integer.valueOf(displayMetrics.heightPixels), "scale", Float.valueOf(displayMetrics.density), "fontScale", Float.valueOf(displayMetrics.scaledDensity), "densityDpi", Integer.valueOf(displayMetrics.densityDpi)), "screenPhysicalPixels", MapBuilder.of(IMediaFormat.KEY_WIDTH, Integer.valueOf(screenDisplayMetrics.widthPixels), IMediaFormat.KEY_HEIGHT, Integer.valueOf(screenDisplayMetrics.heightPixels), "scale", Float.valueOf(screenDisplayMetrics.density), "fontScale", Float.valueOf(screenDisplayMetrics.scaledDensity), "densityDpi", Integer.valueOf(screenDisplayMetrics.densityDpi))));
        constants.put("StyleConstants", MapBuilder.of("PointerEventsValues", MapBuilder.of("none", Integer.valueOf(PointerEvents.NONE.ordinal()), "boxNone", Integer.valueOf(PointerEvents.BOX_NONE.ordinal()), "boxOnly", Integer.valueOf(PointerEvents.BOX_ONLY.ordinal()), "unspecified", Integer.valueOf(PointerEvents.AUTO.ordinal()))));
        constants.put("PopupMenu", MapBuilder.of("dismissed", "dismissed", "itemSelected", "itemSelected"));
        constants.put("AccessibilityEventTypes", MapBuilder.of("typeWindowStateChanged", 32, "typeViewClicked", 1));
        return constants;
    }
}
