package com.bumptech.glide.gifdecoder;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GifHeader {
    int bgColor;
    int bgIndex;
    GifFrame currentFrame;
    boolean gctFlag;
    int gctSize;
    int height;
    int loopCount;
    int pixelAspect;
    int width;
    int[] gct = null;
    int status = 0;
    int frameCount = 0;
    List<GifFrame> frames = new ArrayList();

    public int getNumFrames() {
        return this.frameCount;
    }

    public int getStatus() {
        return this.status;
    }
}
