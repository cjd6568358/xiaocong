package com.bumptech.glide.load.resource.transcode;

import com.bumptech.glide.load.engine.Resource;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UnitTranscoder<Z> implements ResourceTranscoder<Z, Z> {
    private static final UnitTranscoder<?> UNIT_TRANSCODER = new UnitTranscoder<>();

    public static <Z> ResourceTranscoder<Z, Z> get() {
        return UNIT_TRANSCODER;
    }

    @Override // com.bumptech.glide.load.resource.transcode.ResourceTranscoder
    public Resource<Z> transcode(Resource<Z> toTranscode) {
        return toTranscode;
    }

    @Override // com.bumptech.glide.load.resource.transcode.ResourceTranscoder
    public String getId() {
        return Constants.MAIN_VERSION_TAG;
    }
}
