package com.youzan.spiderman.cache;

import android.text.TextUtils;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.youzan.spiderman.utils.FileUtil;
import com.youzan.spiderman.utils.JsonUtil;
import com.youzan.spiderman.utils.Logger;
import java.io.File;
import java.io.IOException;

/* JADX INFO: compiled from: CachePreference.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class d {
    public static <T> T a(Class<T> cls, String str) {
        T t = null;
        if (!TextUtils.isEmpty(str)) {
            File file = new File(g.e(), str);
            if (file.exists()) {
                try {
                    t = (T) JsonUtil.fromJson(FileUtil.getFileContent(file.getPath()), (Class) cls);
                } catch (IOException e) {
                    Logger.e("CachePreference", e);
                } catch (JsonParseException e2) {
                    Logger.e("CachePreference", e2);
                }
            }
        }
        if (t != null) {
            return t;
        }
        try {
            return cls.newInstance();
        } catch (IllegalAccessException e3) {
            Logger.e("CachePreference", e3);
            return t;
        } catch (InstantiationException e4) {
            Logger.e("CachePreference", e4);
            return t;
        }
    }

    public static <T> void a(T t, String str) {
        if (!TextUtils.isEmpty(str) && t != null) {
            File file = new File(g.e());
            if (file.exists() || file.mkdir()) {
                File file2 = new File(g.e(), str);
                if (!file2.exists()) {
                    try {
                        if (!file2.createNewFile()) {
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                com.youzan.spiderman.a.c.a().a(com.youzan.spiderman.a.b.a(file2.getPath(), JsonUtil.toJson(t, new TypeToken<T>() { // from class: com.youzan.spiderman.cache.d.1
                }.getType())));
            }
        }
    }
}
