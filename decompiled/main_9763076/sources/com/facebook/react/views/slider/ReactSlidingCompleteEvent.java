package com.facebook.react.views.slider;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactSlidingCompleteEvent extends Event<ReactSlidingCompleteEvent> {
    private final double mValue;

    public ReactSlidingCompleteEvent(int viewId, double value) {
        super(viewId);
        this.mValue = value;
    }

    public double getValue() {
        return this.mValue;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topSlidingComplete";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public short getCoalescingKey() {
        return (short) 0;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public boolean canCoalesce() {
        return false;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putInt("target", getViewTag());
        eventData.putDouble("value", getValue());
        return eventData;
    }
}
