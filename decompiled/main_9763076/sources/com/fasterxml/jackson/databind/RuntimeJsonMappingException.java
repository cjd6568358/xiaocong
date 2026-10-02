package com.fasterxml.jackson.databind;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RuntimeJsonMappingException extends RuntimeException {
    public RuntimeJsonMappingException(JsonMappingException cause) {
        super(cause);
    }

    public RuntimeJsonMappingException(String message) {
        super(message);
    }

    public RuntimeJsonMappingException(String message, JsonMappingException cause) {
        super(message, cause);
    }
}
