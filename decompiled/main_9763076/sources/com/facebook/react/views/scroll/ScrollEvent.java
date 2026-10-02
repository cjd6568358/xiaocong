package com.facebook.react.views.scroll;

import android.support.v4.util.Pools;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ScrollEvent extends Event<ScrollEvent> {
    private static final Pools.SynchronizedPool<ScrollEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(3);
    private int mContentHeight;
    private int mContentWidth;
    private ScrollEventType mScrollEventType;
    private int mScrollViewHeight;
    private int mScrollViewWidth;
    private int mScrollX;
    private int mScrollY;

    public static ScrollEvent obtain(int viewTag, ScrollEventType scrollEventType, int scrollX, int scrollY, int contentWidth, int contentHeight, int scrollViewWidth, int scrollViewHeight) {
        ScrollEvent event = EVENTS_POOL.acquire();
        if (event == null) {
            event = new ScrollEvent();
        }
        event.init(viewTag, scrollEventType, scrollX, scrollY, contentWidth, contentHeight, scrollViewWidth, scrollViewHeight);
        return event;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void onDispose() {
        EVENTS_POOL.release(this);
    }

    private ScrollEvent() {
    }

    private void init(int viewTag, ScrollEventType scrollEventType, int scrollX, int scrollY, int contentWidth, int contentHeight, int scrollViewWidth, int scrollViewHeight) {
        super.init(viewTag);
        this.mScrollEventType = scrollEventType;
        this.mScrollX = scrollX;
        this.mScrollY = scrollY;
        this.mContentWidth = contentWidth;
        this.mContentHeight = contentHeight;
        this.mScrollViewWidth = scrollViewWidth;
        this.mScrollViewHeight = scrollViewHeight;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return ((ScrollEventType) Assertions.assertNotNull(this.mScrollEventType)).getJSEventName();
    }

    @Override // com.facebook.react.uimanager.events.Event
    public short getCoalescingKey() {
        return (short) 0;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public boolean canCoalesce() {
        return this.mScrollEventType == ScrollEventType.SCROLL;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap contentInset = Arguments.createMap();
        contentInset.putDouble("top", 0.0d);
        contentInset.putDouble("bottom", 0.0d);
        contentInset.putDouble("left", 0.0d);
        contentInset.putDouble("right", 0.0d);
        WritableMap contentOffset = Arguments.createMap();
        contentOffset.putDouble("x", PixelUtil.toDIPFromPixel(this.mScrollX));
        contentOffset.putDouble("y", PixelUtil.toDIPFromPixel(this.mScrollY));
        WritableMap contentSize = Arguments.createMap();
        contentSize.putDouble(IMediaFormat.KEY_WIDTH, PixelUtil.toDIPFromPixel(this.mContentWidth));
        contentSize.putDouble(IMediaFormat.KEY_HEIGHT, PixelUtil.toDIPFromPixel(this.mContentHeight));
        WritableMap layoutMeasurement = Arguments.createMap();
        layoutMeasurement.putDouble(IMediaFormat.KEY_WIDTH, PixelUtil.toDIPFromPixel(this.mScrollViewWidth));
        layoutMeasurement.putDouble(IMediaFormat.KEY_HEIGHT, PixelUtil.toDIPFromPixel(this.mScrollViewHeight));
        WritableMap event = Arguments.createMap();
        event.putMap("contentInset", contentInset);
        event.putMap("contentOffset", contentOffset);
        event.putMap("contentSize", contentSize);
        event.putMap("layoutMeasurement", layoutMeasurement);
        event.putInt("target", getViewTag());
        event.putBoolean("responderIgnoreScroll", true);
        return event;
    }
}
