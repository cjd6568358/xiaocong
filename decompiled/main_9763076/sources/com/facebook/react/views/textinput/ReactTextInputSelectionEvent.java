package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.tencent.android.tpush.common.MessageKey;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ReactTextInputSelectionEvent extends Event<ReactTextInputSelectionEvent> {
    private int mSelectionEnd;
    private int mSelectionStart;

    public ReactTextInputSelectionEvent(int viewId, int selectionStart, int selectionEnd) {
        super(viewId);
        this.mSelectionStart = selectionStart;
        this.mSelectionEnd = selectionEnd;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return "topSelectionChange";
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        WritableMap selectionData = Arguments.createMap();
        selectionData.putInt(MessageKey.MSG_ACCEPT_TIME_END, this.mSelectionEnd);
        selectionData.putInt(MessageKey.MSG_ACCEPT_TIME_START, this.mSelectionStart);
        eventData.putMap("selection", selectionData);
        return eventData;
    }
}
