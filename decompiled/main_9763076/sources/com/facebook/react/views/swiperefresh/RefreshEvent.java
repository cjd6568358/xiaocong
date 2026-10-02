package com.facebook.react.views.swiperefresh;

import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RefreshEvent extends Event<RefreshEvent> {
    protected RefreshEvent(int viewTag) {
        super(viewTag);
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topRefresh";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), null);
    }
}
