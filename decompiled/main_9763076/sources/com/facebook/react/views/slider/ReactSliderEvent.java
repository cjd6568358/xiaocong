package com.facebook.react.views.slider;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactSliderEvent extends Event<ReactSliderEvent> {
    private final boolean mFromUser;
    private final double mValue;

    public ReactSliderEvent(int viewId, double value, boolean fromUser) {
        super(viewId);
        this.mValue = value;
        this.mFromUser = fromUser;
    }

    public double getValue() {
        return this.mValue;
    }

    public boolean isFromUser() {
        return this.mFromUser;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topChange";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public short getCoalescingKey() {
        return (short) 0;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putInt("target", getViewTag());
        eventData.putDouble("value", getValue());
        eventData.putBoolean("fromUser", isFromUser());
        return eventData;
    }
}
