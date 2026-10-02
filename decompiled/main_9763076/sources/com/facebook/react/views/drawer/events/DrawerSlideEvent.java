package com.facebook.react.views.drawer.events;

import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DrawerSlideEvent extends Event<DrawerSlideEvent> {
    private final float mOffset;

    public DrawerSlideEvent(int viewId, float offset) {
        super(viewId);
        this.mOffset = offset;
    }

    public float getOffset() {
        return this.mOffset;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topDrawerSlide";
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
        eventData.putDouble(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_OFFSET, getOffset());
        return eventData;
    }
}
