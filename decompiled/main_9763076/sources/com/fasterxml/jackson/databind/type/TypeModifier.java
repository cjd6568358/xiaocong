package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class TypeModifier {
    public abstract JavaType modifyType(JavaType javaType, Type type, TypeBindings typeBindings, TypeFactory typeFactory);
}
