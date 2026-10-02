package org.greenrobot.eventbus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class SubscriberExceptionEvent {
    public final Object causingEvent;
    public final Object causingSubscriber;
    public final EventBus eventBus;
    public final Throwable throwable;

    public SubscriberExceptionEvent(EventBus eventBus, Throwable throwable, Object causingEvent, Object causingSubscriber) {
        this.eventBus = eventBus;
        this.throwable = throwable;
        this.causingEvent = causingEvent;
        this.causingSubscriber = causingSubscriber;
    }
}
