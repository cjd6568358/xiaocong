package com.alibaba.fastjson.serializer;

import bsh.ParserConstants;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AdderSerializer implements ObjectSerializer {
    public static final AdderSerializer instance = new AdderSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object instanceof LongAdder) {
            out.writeFieldValue('{', "value", ((LongAdder) object).longValue());
            out.write(ParserConstants.ORASSIGNX);
        } else if (object instanceof DoubleAdder) {
            out.writeFieldValue('{', "value", ((DoubleAdder) object).doubleValue());
            out.write(ParserConstants.ORASSIGNX);
        }
    }
}
