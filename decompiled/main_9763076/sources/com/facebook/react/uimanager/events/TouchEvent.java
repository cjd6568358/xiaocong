package com.facebook.react.uimanager.events;

import android.support.v4.util.Pools;
import android.view.MotionEvent;
import com.facebook.infer.annotation.Assertions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TouchEvent extends Event<TouchEvent> {
    private static final Pools.SynchronizedPool<TouchEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(3);
    private short mCoalescingKey;
    private MotionEvent mMotionEvent;
    private TouchEventType mTouchEventType;
    private float mViewX;
    private float mViewY;

    public static TouchEvent obtain(int viewTag, TouchEventType touchEventType, MotionEvent motionEventToCopy, float viewX, float viewY, TouchEventCoalescingKeyHelper touchEventCoalescingKeyHelper) {
        TouchEvent event = EVENTS_POOL.acquire();
        if (event == null) {
            event = new TouchEvent();
        }
        event.init(viewTag, touchEventType, motionEventToCopy, viewX, viewY, touchEventCoalescingKeyHelper);
        return event;
    }

    private TouchEvent() {
    }

    private void init(int viewTag, TouchEventType touchEventType, MotionEvent motionEventToCopy, float viewX, float viewY, TouchEventCoalescingKeyHelper touchEventCoalescingKeyHelper) {
        super.init(viewTag);
        short coalescingKey = 0;
        int action = motionEventToCopy.getAction() & 255;
        switch (action) {
            case 0:
                touchEventCoalescingKeyHelper.addCoalescingKey(motionEventToCopy.getDownTime());
                break;
            case 1:
                touchEventCoalescingKeyHelper.removeCoalescingKey(motionEventToCopy.getDownTime());
                break;
            case 2:
                coalescingKey = touchEventCoalescingKeyHelper.getCoalescingKey(motionEventToCopy.getDownTime());
                break;
            case 3:
                touchEventCoalescingKeyHelper.removeCoalescingKey(motionEventToCopy.getDownTime());
                break;
            case 4:
            default:
                throw new RuntimeException("Unhandled MotionEvent action: " + action);
            case 5:
            case 6:
                touchEventCoalescingKeyHelper.incrementCoalescingKey(motionEventToCopy.getDownTime());
                break;
        }
        this.mTouchEventType = touchEventType;
        this.mMotionEvent = MotionEvent.obtain(motionEventToCopy);
        this.mCoalescingKey = coalescingKey;
        this.mViewX = viewX;
        this.mViewY = viewY;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void onDispose() {
        ((MotionEvent) Assertions.assertNotNull(this.mMotionEvent)).recycle();
        this.mMotionEvent = null;
        EVENTS_POOL.release(this);
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return ((TouchEventType) Assertions.assertNotNull(this.mTouchEventType)).getJSEventName();
    }

    @Override // com.facebook.react.uimanager.events.Event
    public boolean canCoalesce() {
        switch ((TouchEventType) Assertions.assertNotNull(this.mTouchEventType)) {
            case START:
            case END:
            case CANCEL:
                return false;
            case MOVE:
                return true;
            default:
                throw new RuntimeException("Unknown touch event type: " + this.mTouchEventType);
        }
    }

    @Override // com.facebook.react.uimanager.events.Event
    public short getCoalescingKey() {
        return this.mCoalescingKey;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        TouchesHelper.sendTouchEvent(rctEventEmitter, (TouchEventType) Assertions.assertNotNull(this.mTouchEventType), getViewTag(), this);
    }

    public MotionEvent getMotionEvent() {
        Assertions.assertNotNull(this.mMotionEvent);
        return this.mMotionEvent;
    }

    public float getViewX() {
        return this.mViewX;
    }

    public float getViewY() {
        return this.mViewY;
    }
}
