package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ObjectDeserializer {
    <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj);

    int getFastMatchToken();
}
