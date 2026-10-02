package com.facebook.react.views.webview.events;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TopLoadingFinishEvent extends Event<TopLoadingFinishEvent> {
    private WritableMap mEventData;

    public TopLoadingFinishEvent(int viewId, WritableMap eventData) {
        super(viewId);
        this.mEventData = eventData;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topLoadingFinish";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public boolean canCoalesce() {
        return false;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public short getCoalescingKey() {
        return (short) 0;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), this.mEventData);
    }
}
