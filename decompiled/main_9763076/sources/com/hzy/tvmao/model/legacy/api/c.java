package com.hzy.tvmao.model.legacy.api;

import com.fasterxml.jackson.databind.ObjectMapper;

/* JADX INFO: compiled from: LegacyObjectMapperFactory.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static c a = new c();
    private static ObjectMapper b;

    private c() {
        b = new ObjectMapper();
    }

    public static c a() {
        return a;
    }

    public ObjectMapper b() {
        return b;
    }
}
