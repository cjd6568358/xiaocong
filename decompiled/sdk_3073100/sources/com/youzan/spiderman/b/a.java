package com.youzan.spiderman.b;

import android.text.TextUtils;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.youzan.spiderman.utils.FileUtil;
import com.youzan.spiderman.utils.JsonUtil;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: CacheMapPref.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class a {
    /* JADX WARN: Type inference failed for: r2v1, types: [com.youzan.spiderman.b.a$1] */
    static void a(LinkedHashMap<String, Long> value) {
        File prefFile = new File(com.youzan.spiderman.cache.g.e(), "lru_cache_map_image");
        Type type = new TypeToken<LinkedHashMap<String, Long>>() { // from class: com.youzan.spiderman.b.a.1
        }.getType();
        FileUtil.writeContentToFile(prefFile.getPath(), JsonUtil.toJson(value, type));
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.youzan.spiderman.b.a$2] */
    static Map<String, Long> a() {
        File prefFile = new File(com.youzan.spiderman.cache.g.e(), "lru_cache_map_image");
        if (prefFile.exists()) {
            try {
                String content = FileUtil.getFileContent(prefFile.getPath());
                if (!TextUtils.isEmpty(content)) {
                    Type type = new TypeToken<LinkedHashMap<String, Long>>() { // from class: com.youzan.spiderman.b.a.2
                    }.getType();
                    return (Map) JsonUtil.fromJson(content, type);
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.youzan.spiderman.b.a$3] */
    static void b(LinkedHashMap<String, Long> value) {
        File prefFile = new File(com.youzan.spiderman.cache.g.e(), "lru_cache_map_script");
        Type type = new TypeToken<LinkedHashMap<String, String>>() { // from class: com.youzan.spiderman.b.a.3
        }.getType();
        FileUtil.writeContentToFile(prefFile.getPath(), JsonUtil.toJson(value, type));
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.youzan.spiderman.b.a$4] */
    static Map<String, Long> b() {
        File prefFile = new File(com.youzan.spiderman.cache.g.e(), "lru_cache_map_script");
        if (prefFile.exists()) {
            try {
                String content = FileUtil.getFileContent(prefFile.getPath());
                if (!TextUtils.isEmpty(content)) {
                    Type type = new TypeToken<LinkedHashMap<String, Long>>() { // from class: com.youzan.spiderman.b.a.4
                    }.getType();
                    return (Map) JsonUtil.fromJson(content, type);
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.youzan.spiderman.b.a$5] */
    static void c(LinkedHashMap<String, Long> value) {
        File prefFile = new File(com.youzan.spiderman.cache.g.e(), "lru_cache_map_html_data");
        Type type = new TypeToken<LinkedHashMap<String, Long>>() { // from class: com.youzan.spiderman.b.a.5
        }.getType();
        FileUtil.writeContentToFile(prefFile.getPath(), JsonUtil.toJson(value, type));
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.youzan.spiderman.b.a$6] */
    static Map<String, Long> c() {
        File prefFile = new File(com.youzan.spiderman.cache.g.e(), "lru_cache_map_html_data");
        if (prefFile.exists()) {
            try {
                String content = FileUtil.getFileContent(prefFile.getPath());
                if (!TextUtils.isEmpty(content)) {
                    Type type = new TypeToken<LinkedHashMap<String, Long>>() { // from class: com.youzan.spiderman.b.a.6
                    }.getType();
                    return (Map) JsonUtil.fromJson(content, type);
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
