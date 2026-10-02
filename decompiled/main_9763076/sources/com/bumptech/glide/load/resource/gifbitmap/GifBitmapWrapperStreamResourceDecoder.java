package com.bumptech.glide.load.resource.gifbitmap;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GifBitmapWrapperStreamResourceDecoder implements ResourceDecoder<InputStream, GifBitmapWrapper> {
    private final ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper> gifBitmapDecoder;

    public GifBitmapWrapperStreamResourceDecoder(ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper> gifBitmapDecoder) {
        this.gifBitmapDecoder = gifBitmapDecoder;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource<GifBitmapWrapper> decode(InputStream source, int width, int height) throws IOException {
        return this.gifBitmapDecoder.decode(new ImageVideoWrapper(source, null), width, height);
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public String getId() {
        return this.gifBitmapDecoder.getId();
    }
}
