package com.facebook.react.common;

import com.facebook.infer.annotation.Assertions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SingleThreadAsserter {
    private Thread mThread = null;

    public void assertNow() {
        Thread current = Thread.currentThread();
        if (this.mThread == null) {
            this.mThread = current;
        }
        Assertions.assertCondition(this.mThread == current);
    }
}
