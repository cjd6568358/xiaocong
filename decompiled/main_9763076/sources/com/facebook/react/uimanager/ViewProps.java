package com.facebook.react.uimanager;

import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.react.bridge.ReadableMap;
import java.util.Arrays;
import java.util.HashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ViewProps {
    public static final int[] BORDER_SPACING_TYPES = {8, 4, 5, 1, 3};
    public static final int[] PADDING_MARGIN_SPACING_TYPES = {8, 7, 6, 4, 5, 1, 3};
    public static final int[] POSITION_SPACING_TYPES = {4, 5, 1, 3};
    private static final HashSet<String> LAYOUT_ONLY_PROPS = new HashSet<>(Arrays.asList("alignSelf", "alignItems", "collapsable", "flex", "flexDirection", "flexWrap", "justifyContent", "overflow", "position", "right", "top", "bottom", "left", IMediaFormat.KEY_WIDTH, IMediaFormat.KEY_HEIGHT, "minWidth", "maxWidth", "minHeight", "maxHeight", "margin", "marginVertical", "marginHorizontal", "marginLeft", "marginRight", "marginTop", "marginBottom", "padding", "paddingVertical", "paddingHorizontal", "paddingLeft", "paddingRight", "paddingTop", "paddingBottom"));

    public static boolean isLayoutOnly(ReadableMap map, String prop) {
        if (LAYOUT_ONLY_PROPS.contains(prop)) {
            return true;
        }
        if (!"pointerEvents".equals(prop)) {
            return false;
        }
        String value = map.getString(prop);
        return "auto".equals(value) || "box-none".equals(value);
    }
}
