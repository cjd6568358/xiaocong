package com.facebook.react.views.textinput;

import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactContentSizeChangedEvent extends Event<ReactTextChangedEvent> {
    private float mContentHeight;
    private float mContentWidth;

    public ReactContentSizeChangedEvent(int viewId, float contentSizeWidth, float contentSizeHeight) {
        super(viewId);
        this.mContentWidth = contentSizeWidth;
        this.mContentHeight = contentSizeHeight;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topContentSizeChange";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        WritableMap contentSize = Arguments.createMap();
        contentSize.putDouble(IMediaFormat.KEY_WIDTH, this.mContentWidth);
        contentSize.putDouble(IMediaFormat.KEY_HEIGHT, this.mContentHeight);
        eventData.putMap("contentSize", contentSize);
        eventData.putInt("target", getViewTag());
        return eventData;
    }
}
