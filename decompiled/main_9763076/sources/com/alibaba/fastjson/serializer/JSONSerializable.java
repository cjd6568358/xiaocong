package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface JSONSerializable {
    void write(JSONSerializer jSONSerializer, Object obj, Type type, int i) throws IOException;
}
