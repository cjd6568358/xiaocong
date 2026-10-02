package com.facebook.react.views.viewpager;

import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class PageScrollEvent extends Event<PageScrollEvent> {
    private final float mOffset;
    private final int mPosition;

    protected PageScrollEvent(int viewTag, int position, float offset) {
        super(viewTag);
        this.mPosition = position;
        this.mOffset = (Float.isInfinite(offset) || Float.isNaN(offset)) ? 0.0f : offset;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topPageScroll";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putInt("position", this.mPosition);
        eventData.putDouble(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_OFFSET, this.mOffset);
        return eventData;
    }
}
