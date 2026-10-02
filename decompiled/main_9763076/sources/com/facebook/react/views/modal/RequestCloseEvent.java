package com.facebook.react.views.modal;

import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class RequestCloseEvent extends Event<RequestCloseEvent> {
    protected RequestCloseEvent(int viewTag) {
        super(viewTag);
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topRequestClose";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), null);
    }
}
