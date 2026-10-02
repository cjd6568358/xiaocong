package com.facebook.react.uimanager;

import android.graphics.Rect;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ReactClippingViewGroup {
    void getClippingRect(Rect rect);

    boolean getRemoveClippedSubviews();

    void updateClippingRect();
}
