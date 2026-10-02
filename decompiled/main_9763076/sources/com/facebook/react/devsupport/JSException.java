package com.facebook.react.devsupport;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class JSException extends Exception {
    private final String mStack;

    @DoNotStrip
    public JSException(String message, String stack, Throwable cause) {
        super(message, cause);
        this.mStack = stack;
    }

    public JSException(String message, String stack) {
        super(message);
        this.mStack = stack;
    }

    public String getStack() {
        return this.mStack;
    }
}
