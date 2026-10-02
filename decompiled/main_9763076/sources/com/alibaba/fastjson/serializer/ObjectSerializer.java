package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ObjectSerializer {
    void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException;
}
