package com.facebook.react.views.image;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageResizeMode {
    public static ScalingUtils.ScaleType toScaleType(String resizeModeValue) {
        if ("contain".equals(resizeModeValue)) {
            return ScalingUtils.ScaleType.FIT_CENTER;
        }
        if ("cover".equals(resizeModeValue)) {
            return ScalingUtils.ScaleType.CENTER_CROP;
        }
        if ("stretch".equals(resizeModeValue)) {
            return ScalingUtils.ScaleType.FIT_XY;
        }
        if ("center".equals(resizeModeValue)) {
            return ScalingUtils.ScaleType.CENTER_INSIDE;
        }
        if (resizeModeValue == null) {
            return defaultValue();
        }
        throw new JSApplicationIllegalArgumentException("Invalid resize mode: '" + resizeModeValue + "'");
    }

    public static ScalingUtils.ScaleType defaultValue() {
        return ScalingUtils.ScaleType.CENTER_CROP;
    }
}
