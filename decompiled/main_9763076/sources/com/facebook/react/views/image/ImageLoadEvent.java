package com.facebook.react.views.image;

import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageLoadEvent extends Event<ImageLoadEvent> {
    private final int mEventType;
    private final int mHeight;
    private final String mImageUri;
    private final int mWidth;

    public ImageLoadEvent(int viewId, int eventType) {
        this(viewId, eventType, null);
    }

    public ImageLoadEvent(int viewId, int eventType, String imageUri) {
        this(viewId, eventType, imageUri, 0, 0);
    }

    public ImageLoadEvent(int viewId, int eventType, String imageUri, int width, int height) {
        super(viewId);
        this.mEventType = eventType;
        this.mImageUri = imageUri;
        this.mWidth = width;
        this.mHeight = height;
    }

    public static String eventNameForType(int eventType) {
        switch (eventType) {
            case 1:
                return "topError";
            case 2:
                return "topLoad";
            case 3:
                return "topLoadEnd";
            case 4:
                return "topLoadStart";
            case 5:
                return "topProgress";
            default:
                throw new IllegalStateException("Invalid image event: " + Integer.toString(eventType));
        }
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return eventNameForType(this.mEventType);
    }

    @Override // com.facebook.react.uimanager.events.Event
    public short getCoalescingKey() {
        return (short) this.mEventType;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        WritableMap eventData = null;
        if (this.mImageUri != null || this.mEventType == 2) {
            eventData = Arguments.createMap();
            if (this.mImageUri != null) {
                eventData.putString("uri", this.mImageUri);
            }
            if (this.mEventType == 2) {
                WritableMap source = Arguments.createMap();
                source.putDouble(IMediaFormat.KEY_WIDTH, this.mWidth);
                source.putDouble(IMediaFormat.KEY_HEIGHT, this.mHeight);
                if (this.mImageUri != null) {
                    source.putString("url", this.mImageUri);
                }
                eventData.putMap("source", source);
            }
        }
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), eventData);
    }
}
