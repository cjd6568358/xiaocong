package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.Key;
import java.io.File;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface DiskCache {

    public interface Factory {
        DiskCache build();
    }

    public interface Writer {
        boolean write(File file);
    }

    void delete(Key key);

    File get(Key key);

    void put(Key key, Writer writer);
}
