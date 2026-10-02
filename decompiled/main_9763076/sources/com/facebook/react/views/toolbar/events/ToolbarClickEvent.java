package com.facebook.react.views.toolbar.events;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ToolbarClickEvent extends Event<ToolbarClickEvent> {
    private final int position;

    public ToolbarClickEvent(int viewId, int position) {
        super(viewId);
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topSelect";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public boolean canCoalesce() {
        return false;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        WritableMap event = new WritableNativeMap();
        event.putInt("position", getPosition());
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), event);
    }
}
