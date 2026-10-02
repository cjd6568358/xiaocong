package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class CppSystemErrorException extends CppException {
    int errorCode;

    @DoNotStrip
    public CppSystemErrorException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
