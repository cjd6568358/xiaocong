package com.facebook.react.views.viewpager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class PageSelectedEvent extends Event<PageSelectedEvent> {
    private final int mPosition;

    protected PageSelectedEvent(int viewTag, int position) {
        super(viewTag);
        this.mPosition = position;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topPageSelected";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putInt("position", this.mPosition);
        return eventData;
    }
}
