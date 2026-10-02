package com.facebook.react.views.picker.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PickerItemSelectEvent extends Event<PickerItemSelectEvent> {
    private final int mPosition;

    public PickerItemSelectEvent(int id, int position) {
        super(id);
        this.mPosition = position;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topSelect";
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
