package org.greenrobot.eventbus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.greenrobot.eventbus.meta.SubscriberInfoIndex;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class EventBusBuilder {
    private static final ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    boolean ignoreGeneratedIndex;
    boolean strictMethodVerification;
    List<SubscriberInfoIndex> subscriberInfoIndexes;
    boolean throwSubscriberException;
    boolean logSubscriberExceptions = true;
    boolean logNoSubscriberMessages = true;
    boolean sendSubscriberExceptionEvent = true;
    boolean sendNoSubscriberEvent = true;
    boolean eventInheritance = true;
    ExecutorService executorService = DEFAULT_EXECUTOR_SERVICE;

    EventBusBuilder() {
    }
}
