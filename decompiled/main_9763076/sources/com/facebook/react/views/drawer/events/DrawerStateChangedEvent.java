package com.facebook.react.views.drawer.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DrawerStateChangedEvent extends Event<DrawerStateChangedEvent> {
    private final int mDrawerState;

    public DrawerStateChangedEvent(int viewId, int drawerState) {
        super(viewId);
        this.mDrawerState = drawerState;
    }

    public int getDrawerState() {
        return this.mDrawerState;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topDrawerStateChanged";
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
        eventData.putDouble("drawerState", getDrawerState());
        return eventData;
    }
}
