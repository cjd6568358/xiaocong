package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import sun.reflect.annotation.AnnotationType;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AnnotationSerializer implements ObjectSerializer {
    public static AnnotationSerializer instance = new AnnotationSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        Class<?>[] interfaces = object.getClass().getInterfaces();
        if (interfaces.length == 1 && interfaces[0].isAnnotation()) {
            AnnotationType type = AnnotationType.getInstance(interfaces[0]);
            Map<String, Method> members = type.members();
            JSONObject json = new JSONObject(members.size());
            Object objInvoke = null;
            for (Map.Entry<String, Method> entry : members.entrySet()) {
                try {
                    objInvoke = entry.getValue().invoke(object, new Object[0]);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e2) {
                }
                json.put(entry.getKey(), JSON.toJSON(objInvoke));
            }
            serializer.write(json);
        }
    }
}
