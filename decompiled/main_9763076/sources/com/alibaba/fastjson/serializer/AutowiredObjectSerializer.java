package com.alibaba.fastjson.serializer;

import java.lang.reflect.Type;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface AutowiredObjectSerializer extends ObjectSerializer {
    Set<Type> getAutowiredFor();
}
