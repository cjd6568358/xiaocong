package org.greenrobot.eventbus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class EventBusException extends RuntimeException {
    private static final long serialVersionUID = -2912559384646531479L;

    public EventBusException(String detailMessage) {
        super(detailMessage);
    }

    public EventBusException(Throwable throwable) {
        super(throwable);
    }

    public EventBusException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
