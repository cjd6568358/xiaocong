package com.youzan.androidsdk;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class YouzanException extends Exception {
    private int mCode;

    public YouzanException(int code, String msg) {
        super(msg);
        this.mCode = 0;
        this.mCode = code;
    }

    public YouzanException(String msg) {
        super(msg);
        this.mCode = 0;
    }

    public YouzanException(Throwable throwable) {
        super(throwable);
        this.mCode = 0;
        if (throwable instanceof YouzanException) {
            this.mCode = ((YouzanException) throwable).getCode();
        }
    }

    public int getCode() {
        return this.mCode;
    }

    public String getMsg() {
        return getMessage();
    }
}
