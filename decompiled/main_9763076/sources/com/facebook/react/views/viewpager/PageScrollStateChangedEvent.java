package com.facebook.react.views.viewpager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class PageScrollStateChangedEvent extends Event<PageScrollStateChangedEvent> {
    private final String mPageScrollState;

    protected PageScrollStateChangedEvent(int viewTag, String pageScrollState) {
        super(viewTag);
        this.mPageScrollState = pageScrollState;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topPageScrollStateChanged";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putString("pageScrollState", this.mPageScrollState);
        return eventData;
    }
}
