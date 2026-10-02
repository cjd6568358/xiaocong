package org.apache.http;

import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface HeaderIterator extends Iterator {
    @Override // java.util.Iterator
    boolean hasNext();

    Header nextHeader();
}
