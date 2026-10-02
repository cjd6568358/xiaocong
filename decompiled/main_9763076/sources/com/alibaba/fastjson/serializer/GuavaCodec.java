package com.alibaba.fastjson.serializer;

import com.google.common.collect.Multimap;
import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GuavaCodec implements ObjectSerializer {
    public static GuavaCodec instance = new GuavaCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter serializeWriter = serializer.out;
        if (object instanceof Multimap) {
            Multimap multimap = (Multimap) object;
            serializer.write(multimap.asMap());
        }
    }
}
