package com.facebook.react.flat;

import android.content.Context;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.react.bridge.ReadableArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
interface DrawImage extends AttachDetachListener {
    int getBorderColor();

    float getBorderRadius();

    float getBorderWidth();

    ScalingUtils.ScaleType getScaleType();

    void setBorderColor(int i);

    void setBorderRadius(float f);

    void setBorderWidth(float f);

    void setFadeDuration(int i);

    void setProgressiveRenderingEnabled(boolean z);

    void setReactTag(int i);

    void setScaleType(ScalingUtils.ScaleType scaleType);

    void setSource(Context context, ReadableArray readableArray);

    void setTintColor(int i);
}
