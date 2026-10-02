package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AppendableSerializer implements ObjectSerializer {
    public static final AppendableSerializer instance = new AppendableSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            SerializeWriter out = serializer.out;
            out.writeNull(SerializerFeature.WriteNullStringAsEmpty);
        } else {
            serializer.write(object.toString());
        }
    }
}
