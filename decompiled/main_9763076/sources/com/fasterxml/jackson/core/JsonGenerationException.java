package com.fasterxml.jackson.core;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JsonGenerationException extends JsonProcessingException {
    private static final long serialVersionUID = 123;

    public JsonGenerationException(Throwable rootCause) {
        super(rootCause);
    }

    public JsonGenerationException(String msg) {
        super(msg, (JsonLocation) null);
    }

    public JsonGenerationException(String msg, Throwable rootCause) {
        super(msg, (JsonLocation) null, rootCause);
    }
}
