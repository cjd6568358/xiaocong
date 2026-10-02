package com.tencent.android.tpush.service.channel.exception;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ChannelException extends Exception {
    public int errorCode;

    public ChannelException(int i, String str) {
        super(str);
        this.errorCode = i;
    }

    public ChannelException(int i, String str, Throwable th) {
        super(str, th);
        this.errorCode = i;
    }
}
