package com.xiaocong.smarthome.sdk.openapi.bean;

import android.os.Bundle;
import java.io.Serializable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class XCErrorMessage implements Serializable {
    private static final long serialVersionUID = 1;
    private int errorCode;
    private String errorMessage;
    private Bundle extras;

    public XCErrorMessage() {
    }

    public XCErrorMessage(int errorCode) {
        this.errorCode = errorCode;
    }

    public XCErrorMessage(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public XCErrorMessage(int errorCode, String errorMessage, Bundle extras) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.extras = extras;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Bundle getExtras() {
        return this.extras;
    }

    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    public final String toString() {
        return "MSmartErrorMessage{errorCode=" + this.errorCode + ", errorMessage='" + this.errorMessage + "'}";
    }
}
