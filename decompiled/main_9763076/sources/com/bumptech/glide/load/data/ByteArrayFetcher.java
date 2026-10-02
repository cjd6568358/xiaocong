package com.bumptech.glide.load.data;

import com.bumptech.glide.Priority;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ByteArrayFetcher implements DataFetcher<InputStream> {
    private final byte[] bytes;
    private final String id;

    public ByteArrayFetcher(byte[] bytes, String id) {
        this.bytes = bytes;
        this.id = id;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.bumptech.glide.load.data.DataFetcher
    public InputStream loadData(Priority priority) {
        return new ByteArrayInputStream(this.bytes);
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public String getId() {
        return this.id;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
    }
}
