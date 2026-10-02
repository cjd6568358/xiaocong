package com.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface PropertyProcessable extends ParseProcess {
    void apply(String str, Object obj);

    Type getType(String str);
}
