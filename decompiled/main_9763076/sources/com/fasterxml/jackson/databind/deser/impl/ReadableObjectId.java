package com.fasterxml.jackson.databind.deser.impl;

import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReadableObjectId {
    public final Object id;
    public Object item;

    public ReadableObjectId(Object id) {
        this.id = id;
    }

    public void bindItem(Object ob) throws IOException {
        if (this.item != null) {
            throw new IllegalStateException("Already had POJO for id (" + this.id.getClass().getName() + ") [" + this.id + "]");
        }
        this.item = ob;
    }
}
