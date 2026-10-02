package com.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface AutowiredObjectDeserializer extends ObjectDeserializer {
    Set<Type> getAutowiredFor();
}
