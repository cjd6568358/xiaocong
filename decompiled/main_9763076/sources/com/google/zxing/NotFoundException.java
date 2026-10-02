package com.google.zxing;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class NotFoundException extends ReaderException {
    private static final NotFoundException INSTANCE = new NotFoundException();

    static {
        INSTANCE.setStackTrace(NO_TRACE);
    }

    private NotFoundException() {
    }

    public static NotFoundException getNotFoundInstance() {
        return INSTANCE;
    }
}
