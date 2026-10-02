package com.facebook.imagepipeline.common;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    public static Priority getHigherPriority(Priority priority1, Priority priority2) {
        if (priority1 == null) {
            return priority2;
        }
        return (priority2 != null && priority1.ordinal() <= priority2.ordinal()) ? priority2 : priority1;
    }
}
