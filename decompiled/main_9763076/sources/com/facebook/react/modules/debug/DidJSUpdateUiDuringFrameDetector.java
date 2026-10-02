package com.facebook.react.modules.debug;

import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.common.LongArray;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DidJSUpdateUiDuringFrameDetector implements NotThreadSafeBridgeIdleDebugListener, NotThreadSafeViewHierarchyUpdateDebugListener {
    private final LongArray mTransitionToIdleEvents = LongArray.createWithInitialCapacity(20);
    private final LongArray mTransitionToBusyEvents = LongArray.createWithInitialCapacity(20);
    private final LongArray mViewHierarchyUpdateEnqueuedEvents = LongArray.createWithInitialCapacity(20);
    private final LongArray mViewHierarchyUpdateFinishedEvents = LongArray.createWithInitialCapacity(20);
    private volatile boolean mWasIdleAtEndOfLastFrame = true;

    @Override // com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener
    public synchronized void onTransitionToBridgeIdle() {
        this.mTransitionToIdleEvents.add(System.nanoTime());
    }

    @Override // com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener
    public synchronized void onTransitionToBridgeBusy() {
        this.mTransitionToBusyEvents.add(System.nanoTime());
    }

    @Override // com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener
    public synchronized void onViewHierarchyUpdateEnqueued() {
        this.mViewHierarchyUpdateEnqueuedEvents.add(System.nanoTime());
    }

    @Override // com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener
    public synchronized void onViewHierarchyUpdateFinished() {
        this.mViewHierarchyUpdateFinishedEvents.add(System.nanoTime());
    }

    public synchronized boolean getDidJSHitFrameAndCleanup(long frameStartTimeNanos, long frameEndTimeNanos) {
        boolean hitFrame;
        boolean finishedUiUpdate = hasEventBetweenTimestamps(this.mViewHierarchyUpdateFinishedEvents, frameStartTimeNanos, frameEndTimeNanos);
        boolean didEndFrameIdle = didEndFrameIdle(frameStartTimeNanos, frameEndTimeNanos);
        if (finishedUiUpdate) {
            hitFrame = true;
        } else {
            hitFrame = didEndFrameIdle && !hasEventBetweenTimestamps(this.mViewHierarchyUpdateEnqueuedEvents, frameStartTimeNanos, frameEndTimeNanos);
        }
        cleanUp(this.mTransitionToIdleEvents, frameEndTimeNanos);
        cleanUp(this.mTransitionToBusyEvents, frameEndTimeNanos);
        cleanUp(this.mViewHierarchyUpdateEnqueuedEvents, frameEndTimeNanos);
        cleanUp(this.mViewHierarchyUpdateFinishedEvents, frameEndTimeNanos);
        this.mWasIdleAtEndOfLastFrame = didEndFrameIdle;
        return hitFrame;
    }

    private static boolean hasEventBetweenTimestamps(LongArray eventArray, long startTime, long endTime) {
        for (int i = 0; i < eventArray.size(); i++) {
            long time = eventArray.get(i);
            if (time >= startTime && time < endTime) {
                return true;
            }
        }
        return false;
    }

    private static long getLastEventBetweenTimestamps(LongArray eventArray, long startTime, long endTime) {
        long lastEvent = -1;
        for (int i = 0; i < eventArray.size(); i++) {
            long time = eventArray.get(i);
            if (time < startTime || time >= endTime) {
                if (time >= endTime) {
                    break;
                }
            } else {
                lastEvent = time;
            }
        }
        return lastEvent;
    }

    private boolean didEndFrameIdle(long startTime, long endTime) {
        long lastIdleTransition = getLastEventBetweenTimestamps(this.mTransitionToIdleEvents, startTime, endTime);
        long lastBusyTransition = getLastEventBetweenTimestamps(this.mTransitionToBusyEvents, startTime, endTime);
        if (lastIdleTransition == -1 && lastBusyTransition == -1) {
            return this.mWasIdleAtEndOfLastFrame;
        }
        return lastIdleTransition > lastBusyTransition;
    }

    private static void cleanUp(LongArray eventArray, long endTime) {
        int size = eventArray.size();
        int indicesToRemove = 0;
        for (int i = 0; i < size; i++) {
            if (eventArray.get(i) < endTime) {
                indicesToRemove++;
            }
        }
        if (indicesToRemove > 0) {
            for (int i2 = 0; i2 < size - indicesToRemove; i2++) {
                eventArray.set(i2, eventArray.get(i2 + indicesToRemove));
            }
            eventArray.dropTail(indicesToRemove);
        }
    }
}
