package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.WriterCallback;
import java.io.IOException;
import java.util.Collection;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface DiskStorage {

    public interface Entry {
        String getId();

        long getSize();

        long getTimestamp();
    }

    public interface Inserter {
        boolean cleanUp();

        BinaryResource commit(Object obj) throws IOException;

        void writeData(WriterCallback writerCallback, Object obj) throws IOException;
    }

    void clearAll() throws IOException;

    boolean contains(String str, Object obj) throws IOException;

    Collection<Entry> getEntries() throws IOException;

    BinaryResource getResource(String str, Object obj) throws IOException;

    Inserter insert(String str, Object obj) throws IOException;

    void purgeUnexpectedResources();

    long remove(Entry entry) throws IOException;
}
