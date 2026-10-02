package com.youzan.spiderman.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class JsonUtil {
    private static final String TAG = JsonUtil.class.getSimpleName();
    private static Gson sGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ").setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public static <T> T fromJson(String str, Class<T> cls) {
        return (T) sGson.fromJson(str, cls);
    }

    public static <T> T fromJson(String str, Type type) {
        return (T) sGson.fromJson(str, type);
    }

    public static String toJson(Object entity) {
        return sGson.toJson(entity);
    }

    public static String toJson(Object entity, Type type) {
        return sGson.toJson(entity, type);
    }
}
