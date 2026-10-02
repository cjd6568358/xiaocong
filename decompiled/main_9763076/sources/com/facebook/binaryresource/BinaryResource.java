package com.facebook.binaryresource;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface BinaryResource {
    InputStream openStream() throws IOException;

    long size();
}
