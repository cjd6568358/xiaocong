package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ReactTextInputEndEditingEvent extends Event<ReactTextInputEndEditingEvent> {
    private String mText;

    public ReactTextInputEndEditingEvent(int viewId, String text) {
        super(viewId);
        this.mText = text;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topEndEditing";
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
        eventData.putString("text", this.mText);
        return eventData;
    }
}
