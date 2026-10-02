package com.fasterxml.jackson.databind.util;

import java.util.IdentityHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ObjectIdMap extends IdentityHashMap<Object, Object> {
    public ObjectIdMap() {
        super(16);
    }

    public Object findId(Object pojo) {
        return get(pojo);
    }

    public void insertId(Object pojo, Object id) {
        put(pojo, id);
    }
}
