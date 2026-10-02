package com.facebook.react.uimanager;

import android.support.v4.util.Pools;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class OnLayoutEvent extends Event<OnLayoutEvent> {
    private static final Pools.SynchronizedPool<OnLayoutEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(20);
    private int mHeight;
    private int mWidth;
    private int mX;
    private int mY;

    public static OnLayoutEvent obtain(int viewTag, int x, int y, int width, int height) {
        OnLayoutEvent event = EVENTS_POOL.acquire();
        if (event == null) {
            event = new OnLayoutEvent();
        }
        event.init(viewTag, x, y, width, height);
        return event;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void onDispose() {
        EVENTS_POOL.release(this);
    }

    private OnLayoutEvent() {
    }

    protected void init(int viewTag, int x, int y, int width, int height) {
        super.init(viewTag);
        this.mX = x;
        this.mY = y;
        this.mWidth = width;
        this.mHeight = height;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topLayout";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        WritableMap layout = Arguments.createMap();
        layout.putDouble("x", PixelUtil.toDIPFromPixel(this.mX));
        layout.putDouble("y", PixelUtil.toDIPFromPixel(this.mY));
        layout.putDouble(IMediaFormat.KEY_WIDTH, PixelUtil.toDIPFromPixel(this.mWidth));
        layout.putDouble(IMediaFormat.KEY_HEIGHT, PixelUtil.toDIPFromPixel(this.mHeight));
        WritableMap event = Arguments.createMap();
        event.putMap("layout", layout);
        event.putInt("target", getViewTag());
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), event);
    }
}
