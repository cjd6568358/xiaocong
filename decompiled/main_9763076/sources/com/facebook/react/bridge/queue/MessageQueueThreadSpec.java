package com.facebook.react.bridge.queue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MessageQueueThreadSpec {
    private static final MessageQueueThreadSpec MAIN_UI_SPEC = new MessageQueueThreadSpec(ThreadType.MAIN_UI, "main_ui");
    private final String mName;
    private final long mStackSize;
    private final ThreadType mThreadType;

    protected enum ThreadType {
        MAIN_UI,
        NEW_BACKGROUND
    }

    public static MessageQueueThreadSpec newBackgroundThreadSpec(String name) {
        return new MessageQueueThreadSpec(ThreadType.NEW_BACKGROUND, name);
    }

    public static MessageQueueThreadSpec newBackgroundThreadSpec(String name, long stackSize) {
        return new MessageQueueThreadSpec(ThreadType.NEW_BACKGROUND, name, stackSize);
    }

    public static MessageQueueThreadSpec mainThreadSpec() {
        return MAIN_UI_SPEC;
    }

    private MessageQueueThreadSpec(ThreadType threadType, String name) {
        this(threadType, name, 0L);
    }

    private MessageQueueThreadSpec(ThreadType threadType, String name, long stackSize) {
        this.mThreadType = threadType;
        this.mName = name;
        this.mStackSize = stackSize;
    }

    public ThreadType getThreadType() {
        return this.mThreadType;
    }

    public String getName() {
        return this.mName;
    }

    public long getStackSize() {
        return this.mStackSize;
    }
}
