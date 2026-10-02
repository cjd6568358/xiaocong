package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class EventAnimationDriver implements RCTEventEmitter {
    private List<String> mEventPath;
    ValueAnimatedNode mValueNode;

    public EventAnimationDriver(List<String> eventPath, ValueAnimatedNode valueNode) {
        this.mEventPath = eventPath;
        this.mValueNode = valueNode;
    }

    @Override // com.facebook.react.uimanager.events.RCTEventEmitter
    public void receiveEvent(int targetTag, String eventName, WritableMap event) {
        if (event == null) {
            throw new IllegalArgumentException("Native animated events must have event data.");
        }
        ReadableMap curMap = event;
        for (int i = 0; i < this.mEventPath.size() - 1; i++) {
            curMap = curMap.getMap(this.mEventPath.get(i));
        }
        this.mValueNode.mValue = curMap.getDouble(this.mEventPath.get(this.mEventPath.size() - 1));
    }

    @Override // com.facebook.react.uimanager.events.RCTEventEmitter
    public void receiveTouches(String eventName, WritableArray touches, WritableArray changedIndices) {
        throw new RuntimeException("receiveTouches is not support by native animated events");
    }
}
