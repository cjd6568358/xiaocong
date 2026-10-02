package com.bumptech.glide.load.resource.file;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.tencent.android.tpush.common.Constants;
import java.io.File;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FileDecoder implements ResourceDecoder<File, File> {
    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource<File> decode(File source, int width, int height) {
        return new FileResource(source);
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public String getId() {
        return Constants.MAIN_VERSION_TAG;
    }
}
