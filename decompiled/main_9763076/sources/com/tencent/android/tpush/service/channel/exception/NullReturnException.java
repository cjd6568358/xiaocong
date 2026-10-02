package com.tencent.android.tpush.service.channel.exception;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NullReturnException extends Exception {
    private static final long serialVersionUID = -2623309261327598087L;
    private int statusCode;

    public NullReturnException(String str) {
        super(str);
        this.statusCode = -1;
    }

    public NullReturnException(String str, Exception exc) {
        super(str, exc);
        this.statusCode = -1;
    }
}
