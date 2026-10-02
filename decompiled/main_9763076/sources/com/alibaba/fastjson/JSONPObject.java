package com.alibaba.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSONPObject implements JSONSerializable {
    private String function;
    private final List<Object> parameters = new ArrayList();
    public static String SECURITY_PREFIX = "/**/";
    private static final int BrowserSecureMask = SerializerFeature.BrowserSecure.mask;

    public JSONPObject() {
    }

    public JSONPObject(String function) {
        this.function = function;
    }

    public void addParameter(Object parameter) {
        this.parameters.add(parameter);
    }

    @Override // com.alibaba.fastjson.serializer.JSONSerializable
    public void write(JSONSerializer serializer, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter writer = serializer.out;
        if ((BrowserSecureMask & features) != 0 || writer.isEnabled(BrowserSecureMask)) {
            writer.write(SECURITY_PREFIX);
        }
        writer.write(this.function);
        writer.write(40);
        for (int i = 0; i < this.parameters.size(); i++) {
            if (i != 0) {
                writer.write(44);
            }
            serializer.write(this.parameters.get(i));
        }
        writer.write(41);
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
