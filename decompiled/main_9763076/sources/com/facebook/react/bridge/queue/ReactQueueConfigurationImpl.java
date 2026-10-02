package com.facebook.react.bridge.queue;

import com.facebook.react.common.MapBuilder;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactQueueConfigurationImpl implements ReactQueueConfiguration {
    private final MessageQueueThreadImpl mJSQueueThread;
    private final MessageQueueThreadImpl mNativeModulesQueueThread;
    private final MessageQueueThreadImpl mUIQueueThread;

    private ReactQueueConfigurationImpl(MessageQueueThreadImpl uiQueueThread, MessageQueueThreadImpl nativeModulesQueueThread, MessageQueueThreadImpl jsQueueThread) {
        this.mUIQueueThread = uiQueueThread;
        this.mNativeModulesQueueThread = nativeModulesQueueThread;
        this.mJSQueueThread = jsQueueThread;
    }

    @Override // com.facebook.react.bridge.queue.ReactQueueConfiguration
    public MessageQueueThread getUIQueueThread() {
        return this.mUIQueueThread;
    }

    @Override // com.facebook.react.bridge.queue.ReactQueueConfiguration
    public MessageQueueThread getNativeModulesQueueThread() {
        return this.mNativeModulesQueueThread;
    }

    @Override // com.facebook.react.bridge.queue.ReactQueueConfiguration
    public MessageQueueThread getJSQueueThread() {
        return this.mJSQueueThread;
    }

    public static ReactQueueConfigurationImpl create(ReactQueueConfigurationSpec spec, QueueThreadExceptionHandler exceptionHandler) {
        Map<MessageQueueThreadSpec, MessageQueueThreadImpl> specsToThreads = MapBuilder.newHashMap();
        MessageQueueThreadSpec uiThreadSpec = MessageQueueThreadSpec.mainThreadSpec();
        MessageQueueThreadImpl uiThread = MessageQueueThreadImpl.create(uiThreadSpec, exceptionHandler);
        specsToThreads.put(uiThreadSpec, uiThread);
        MessageQueueThreadImpl jsThread = specsToThreads.get(spec.getJSQueueThreadSpec());
        if (jsThread == null) {
            jsThread = MessageQueueThreadImpl.create(spec.getJSQueueThreadSpec(), exceptionHandler);
        }
        MessageQueueThreadImpl nativeModulesThread = specsToThreads.get(spec.getNativeModulesQueueThreadSpec());
        if (nativeModulesThread == null) {
            nativeModulesThread = MessageQueueThreadImpl.create(spec.getNativeModulesQueueThreadSpec(), exceptionHandler);
        }
        return new ReactQueueConfigurationImpl(uiThread, nativeModulesThread, jsThread);
    }
}
