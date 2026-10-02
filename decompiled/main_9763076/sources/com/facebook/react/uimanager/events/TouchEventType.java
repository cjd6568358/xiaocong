package com.facebook.react.uimanager.events;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public enum TouchEventType {
    START("topTouchStart"),
    END("topTouchEnd"),
    MOVE("topTouchMove"),
    CANCEL("topTouchCancel");

    private final String mJSEventName;

    TouchEventType(String jsEventName) {
        this.mJSEventName = jsEventName;
    }

    public String getJSEventName() {
        return this.mJSEventName;
    }
}
