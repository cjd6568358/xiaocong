package com.fasterxml.jackson.core;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JsonParseException extends JsonProcessingException {
    static final long serialVersionUID = 123;

    public JsonParseException(String msg, JsonLocation loc) {
        super(msg, loc);
    }

    public JsonParseException(String msg, JsonLocation loc, Throwable root) {
        super(msg, loc, root);
    }
}
