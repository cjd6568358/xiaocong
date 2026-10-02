package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.tencent.android.tpush.common.MessageKey;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactTextInputEvent extends Event<ReactTextInputEvent> {
    private String mPreviousText;
    private int mRangeEnd;
    private int mRangeStart;
    private String mText;

    public ReactTextInputEvent(int viewId, String text, String previousText, int rangeStart, int rangeEnd) {
        super(viewId);
        this.mText = text;
        this.mPreviousText = previousText;
        this.mRangeStart = rangeStart;
        this.mRangeEnd = rangeEnd;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topTextInput";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public boolean canCoalesce() {
        return false;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        WritableMap range = Arguments.createMap();
        range.putDouble(MessageKey.MSG_ACCEPT_TIME_START, this.mRangeStart);
        range.putDouble(MessageKey.MSG_ACCEPT_TIME_END, this.mRangeEnd);
        eventData.putString("text", this.mText);
        eventData.putString("previousText", this.mPreviousText);
        eventData.putMap("range", range);
        eventData.putInt("target", getViewTag());
        return eventData;
    }
}
