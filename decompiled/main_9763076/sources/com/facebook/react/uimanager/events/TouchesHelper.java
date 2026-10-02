package com.facebook.react.uimanager.events;

import android.view.MotionEvent;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class TouchesHelper {
    private static WritableArray createsPointersArray(int reactTarget, TouchEvent event) {
        WritableArray touches = Arguments.createArray();
        MotionEvent motionEvent = event.getMotionEvent();
        float targetViewCoordinateX = motionEvent.getX() - event.getViewX();
        float targetViewCoordinateY = motionEvent.getY() - event.getViewY();
        for (int index = 0; index < motionEvent.getPointerCount(); index++) {
            WritableMap touch = Arguments.createMap();
            touch.putDouble("pageX", PixelUtil.toDIPFromPixel(motionEvent.getX(index)));
            touch.putDouble("pageY", PixelUtil.toDIPFromPixel(motionEvent.getY(index)));
            float locationX = motionEvent.getX(index) - targetViewCoordinateX;
            float locationY = motionEvent.getY(index) - targetViewCoordinateY;
            touch.putDouble("locationX", PixelUtil.toDIPFromPixel(locationX));
            touch.putDouble("locationY", PixelUtil.toDIPFromPixel(locationY));
            touch.putInt("target", reactTarget);
            touch.putDouble("timestamp", event.getTimestampMs());
            touch.putDouble("identifier", motionEvent.getPointerId(index));
            touches.pushMap(touch);
        }
        return touches;
    }

    public static void sendTouchEvent(RCTEventEmitter rctEventEmitter, TouchEventType type, int reactTarget, TouchEvent touchEvent) {
        WritableArray pointers = createsPointersArray(reactTarget, touchEvent);
        MotionEvent motionEvent = touchEvent.getMotionEvent();
        WritableArray changedIndices = Arguments.createArray();
        if (type == TouchEventType.MOVE || type == TouchEventType.CANCEL) {
            for (int i = 0; i < motionEvent.getPointerCount(); i++) {
                changedIndices.pushInt(i);
            }
        } else if (type == TouchEventType.START || type == TouchEventType.END) {
            changedIndices.pushInt(motionEvent.getActionIndex());
        } else {
            throw new RuntimeException("Unknown touch type: " + type);
        }
        rctEventEmitter.receiveTouches(type.getJSEventName(), pointers, changedIndices);
    }
}
