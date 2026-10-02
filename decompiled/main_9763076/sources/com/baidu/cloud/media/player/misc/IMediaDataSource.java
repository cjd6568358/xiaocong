package com.baidu.cloud.media.player.misc;

import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface IMediaDataSource {
    void close() throws IOException;

    long getSize() throws IOException;

    int readAt(long j, byte[] bArr, int i, int i2) throws IOException;
}
