package com.facebook.react.uimanager.events;

import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ContentSizeChangeEvent extends Event<ContentSizeChangeEvent> {
    private final int mHeight;
    private final int mWidth;

    public ContentSizeChangeEvent(int viewTag, int width, int height) {
        super(viewTag);
        this.mWidth = width;
        this.mHeight = height;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topContentSizeChange";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        WritableMap data = Arguments.createMap();
        data.putDouble(IMediaFormat.KEY_WIDTH, PixelUtil.toDIPFromPixel(this.mWidth));
        data.putDouble(IMediaFormat.KEY_HEIGHT, PixelUtil.toDIPFromPixel(this.mHeight));
        rctEventEmitter.receiveEvent(getViewTag(), "topContentSizeChange", data);
    }
}
