package com.facebook.react.views.scroll;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public enum ScrollEventType {
    BEGIN_DRAG("topScrollBeginDrag"),
    END_DRAG("topScrollEndDrag"),
    SCROLL("topScroll"),
    MOMENTUM_BEGIN("topMomentumScrollBegin"),
    MOMENTUM_END("topMomentumScrollEnd"),
    ANIMATION_END("topScrollAnimationEnd");

    private final String mJSEventName;

    ScrollEventType(String jsEventName) {
        this.mJSEventName = jsEventName;
    }

    public String getJSEventName() {
        return this.mJSEventName;
    }
}
