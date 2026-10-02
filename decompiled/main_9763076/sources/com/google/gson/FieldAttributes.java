package com.google.gson;

import com.google.gson.internal.C$Gson$Preconditions;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class FieldAttributes {
    private final Field field;

    public FieldAttributes(Field f) {
        C$Gson$Preconditions.checkNotNull(f);
        this.field = f;
    }
}
