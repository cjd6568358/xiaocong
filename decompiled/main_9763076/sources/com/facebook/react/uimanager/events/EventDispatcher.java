package com.facebook.react.uimanager.events;

import android.util.LongSparseArray;
import android.view.Choreographer;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ReactChoreographer;
import com.facebook.systrace.Systrace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EventDispatcher implements LifecycleEventListener {
    private static final Comparator<Event> EVENT_COMPARATOR = new Comparator<Event>() { // from class: com.facebook.react.uimanager.events.EventDispatcher.1
        @Override // java.util.Comparator
        public int compare(Event lhs, Event rhs) {
            if (lhs == null && rhs == null) {
                return 0;
            }
            if (lhs == null) {
                return -1;
            }
            if (rhs == null) {
                return 1;
            }
            long diff = lhs.getTimestampMs() - rhs.getTimestampMs();
            if (diff != 0) {
                return diff < 0 ? -1 : 1;
            }
            return 0;
        }
    };
    private final ScheduleDispatchFrameCallback mCurrentFrameCallback;
    private final DispatchEventsRunnable mDispatchEventsRunnable;
    private volatile RCTEventEmitter mRCTEventEmitter;
    private final ReactApplicationContext mReactContext;
    private final Object mEventsStagingLock = new Object();
    private final Object mEventsToDispatchLock = new Object();
    private final LongSparseArray<Integer> mEventCookieToLastEventIdx = new LongSparseArray<>();
    private final Map<String, Short> mEventNameToEventId = MapBuilder.newHashMap();
    private final ArrayList<Event> mEventStaging = new ArrayList<>();
    private final ArrayList<EventDispatcherListener> mListeners = new ArrayList<>();
    private Event[] mEventsToDispatch = new Event[16];
    private int mEventsToDispatchSize = 0;
    private short mNextEventTypeId = 0;
    private volatile boolean mHasDispatchScheduled = false;
    private volatile int mHasDispatchScheduledCount = 0;

    static /* synthetic */ int access$508(EventDispatcher x0) {
        int i = x0.mHasDispatchScheduledCount;
        x0.mHasDispatchScheduledCount = i + 1;
        return i;
    }

    public EventDispatcher(ReactApplicationContext reactContext) {
        this.mDispatchEventsRunnable = new DispatchEventsRunnable();
        this.mReactContext = reactContext;
        this.mReactContext.addLifecycleEventListener(this);
        this.mCurrentFrameCallback = new ScheduleDispatchFrameCallback();
    }

    public void dispatchEvent(Event event) {
        Assertions.assertCondition(event.isInitialized(), "Dispatched event hasn't been initialized");
        for (EventDispatcherListener listener : this.mListeners) {
            listener.onEventDispatch(event);
        }
        synchronized (this.mEventsStagingLock) {
            this.mEventStaging.add(event);
            Systrace.startAsyncFlow(0L, event.getEventName(), event.getUniqueID());
        }
        if (this.mRCTEventEmitter != null) {
            this.mCurrentFrameCallback.maybePostFromNonUI();
        }
    }

    public void addListener(EventDispatcherListener listener) {
        this.mListeners.add(listener);
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
        UiThreadUtil.assertOnUiThread();
        if (this.mRCTEventEmitter == null) {
            this.mRCTEventEmitter = (RCTEventEmitter) this.mReactContext.getJSModule(RCTEventEmitter.class);
        }
        this.mCurrentFrameCallback.maybePost();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
        stopFrameCallback();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
        stopFrameCallback();
    }

    public void onCatalystInstanceDestroyed() {
        stopFrameCallback();
    }

    private void stopFrameCallback() {
        UiThreadUtil.assertOnUiThread();
        this.mCurrentFrameCallback.stop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveStagedEventsToDispatchQueue() {
        synchronized (this.mEventsStagingLock) {
            synchronized (this.mEventsToDispatchLock) {
                for (int i = 0; i < this.mEventStaging.size(); i++) {
                    Event event = this.mEventStaging.get(i);
                    if (!event.canCoalesce()) {
                        addEventToEventsToDispatch(event);
                    } else {
                        long eventCookie = getEventCookie(event.getViewTag(), event.getEventName(), event.getCoalescingKey());
                        Event eventToAdd = null;
                        Event eventToDispose = null;
                        Integer lastEventIdx = this.mEventCookieToLastEventIdx.get(eventCookie);
                        if (lastEventIdx == null) {
                            eventToAdd = event;
                            this.mEventCookieToLastEventIdx.put(eventCookie, Integer.valueOf(this.mEventsToDispatchSize));
                        } else {
                            Event lastEvent = this.mEventsToDispatch[lastEventIdx.intValue()];
                            Event coalescedEvent = event.coalesce(lastEvent);
                            if (coalescedEvent != lastEvent) {
                                eventToAdd = coalescedEvent;
                                this.mEventCookieToLastEventIdx.put(eventCookie, Integer.valueOf(this.mEventsToDispatchSize));
                                eventToDispose = lastEvent;
                                this.mEventsToDispatch[lastEventIdx.intValue()] = null;
                            } else {
                                eventToDispose = event;
                            }
                        }
                        if (eventToAdd != null) {
                            addEventToEventsToDispatch(eventToAdd);
                        }
                        if (eventToDispose != null) {
                            eventToDispose.dispose();
                        }
                    }
                }
            }
            this.mEventStaging.clear();
        }
    }

    private long getEventCookie(int viewTag, String eventName, short coalescingKey) {
        short eventTypeId;
        Short eventIdObj = this.mEventNameToEventId.get(eventName);
        if (eventIdObj != null) {
            eventTypeId = eventIdObj.shortValue();
        } else {
            eventTypeId = this.mNextEventTypeId;
            this.mNextEventTypeId = (short) (eventTypeId + 1);
            this.mEventNameToEventId.put(eventName, Short.valueOf(eventTypeId));
        }
        return getEventCookie(viewTag, eventTypeId, coalescingKey);
    }

    private static long getEventCookie(int viewTag, short eventTypeId, short coalescingKey) {
        return ((long) viewTag) | ((((long) eventTypeId) & 65535) << 32) | ((((long) coalescingKey) & 65535) << 48);
    }

    private class ScheduleDispatchFrameCallback implements Choreographer.FrameCallback {
        private volatile boolean mIsPosted;
        private boolean mShouldStop;

        private ScheduleDispatchFrameCallback() {
            this.mIsPosted = false;
            this.mShouldStop = false;
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long frameTimeNanos) {
            UiThreadUtil.assertOnUiThread();
            if (this.mShouldStop) {
                this.mIsPosted = false;
            } else {
                post();
            }
            Systrace.beginSection(0L, "ScheduleDispatchFrameCallback");
            try {
                EventDispatcher.this.moveStagedEventsToDispatchQueue();
                if (EventDispatcher.this.mEventsToDispatchSize > 0 && !EventDispatcher.this.mHasDispatchScheduled) {
                    EventDispatcher.this.mHasDispatchScheduled = true;
                    Systrace.startAsyncFlow(0L, "ScheduleDispatchFrameCallback", EventDispatcher.this.mHasDispatchScheduledCount);
                    EventDispatcher.this.mReactContext.runOnJSQueueThread(EventDispatcher.this.mDispatchEventsRunnable);
                }
            } finally {
                Systrace.endSection(0L);
            }
        }

        public void stop() {
            this.mShouldStop = true;
        }

        public void maybePost() {
            if (!this.mIsPosted) {
                this.mIsPosted = true;
                post();
            }
        }

        private void post() {
            ReactChoreographer.getInstance().postFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, EventDispatcher.this.mCurrentFrameCallback);
        }

        public void maybePostFromNonUI() {
            if (!this.mIsPosted) {
                if (!EventDispatcher.this.mReactContext.isOnUiQueueThread()) {
                    EventDispatcher.this.mReactContext.runOnUiQueueThread(new Runnable() { // from class: com.facebook.react.uimanager.events.EventDispatcher.ScheduleDispatchFrameCallback.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ScheduleDispatchFrameCallback.this.maybePost();
                        }
                    });
                } else {
                    maybePost();
                }
            }
        }
    }

    private class DispatchEventsRunnable implements Runnable {
        private DispatchEventsRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Systrace.beginSection(0L, "DispatchEventsRunnable");
            try {
                Systrace.endAsyncFlow(0L, "ScheduleDispatchFrameCallback", EventDispatcher.this.mHasDispatchScheduledCount);
                EventDispatcher.this.mHasDispatchScheduled = false;
                EventDispatcher.access$508(EventDispatcher.this);
                Assertions.assertNotNull(EventDispatcher.this.mRCTEventEmitter);
                synchronized (EventDispatcher.this.mEventsToDispatchLock) {
                    if (EventDispatcher.this.mEventsToDispatchSize > 1) {
                        Arrays.sort(EventDispatcher.this.mEventsToDispatch, 0, EventDispatcher.this.mEventsToDispatchSize, EventDispatcher.EVENT_COMPARATOR);
                    }
                    for (int eventIdx = 0; eventIdx < EventDispatcher.this.mEventsToDispatchSize; eventIdx++) {
                        Event event = EventDispatcher.this.mEventsToDispatch[eventIdx];
                        if (event != null) {
                            Systrace.endAsyncFlow(0L, event.getEventName(), event.getUniqueID());
                            event.dispatch(EventDispatcher.this.mRCTEventEmitter);
                            event.dispose();
                        }
                    }
                    EventDispatcher.this.clearEventsToDispatch();
                    EventDispatcher.this.mEventCookieToLastEventIdx.clear();
                }
                Systrace.endSection(0L);
            } catch (Throwable th) {
                Systrace.endSection(0L);
                throw th;
            }
        }
    }

    private void addEventToEventsToDispatch(Event event) {
        if (this.mEventsToDispatchSize == this.mEventsToDispatch.length) {
            this.mEventsToDispatch = (Event[]) Arrays.copyOf(this.mEventsToDispatch, this.mEventsToDispatch.length * 2);
        }
        Event[] eventArr = this.mEventsToDispatch;
        int i = this.mEventsToDispatchSize;
        this.mEventsToDispatchSize = i + 1;
        eventArr[i] = event;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearEventsToDispatch() {
        Arrays.fill(this.mEventsToDispatch, 0, this.mEventsToDispatchSize, (Object) null);
        this.mEventsToDispatchSize = 0;
    }
}
