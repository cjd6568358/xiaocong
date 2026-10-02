package com.facebook.react.views.webview.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TopMessageEvent extends Event<TopMessageEvent> {
    private final String mData;

    public TopMessageEvent(int viewId, String data) {
        super(viewId);
        this.mData = data;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topMessage";
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
        WritableMap data = Arguments.createMap();
        data.putString("data", this.mData);
        rctEventEmitter.receiveEvent(getViewTag(), "topMessage", data);
    }
}
