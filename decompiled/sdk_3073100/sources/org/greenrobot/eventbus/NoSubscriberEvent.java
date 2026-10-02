package org.greenrobot.eventbus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class NoSubscriberEvent {
    public final EventBus eventBus;
    public final Object originalEvent;

    public NoSubscriberEvent(EventBus eventBus, Object originalEvent) {
        this.eventBus = eventBus;
        this.originalEvent = originalEvent;
    }
}
