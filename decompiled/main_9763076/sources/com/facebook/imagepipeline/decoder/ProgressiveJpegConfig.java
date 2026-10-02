package com.facebook.imagepipeline.decoder;

import com.facebook.imagepipeline.image.QualityInfo;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ProgressiveJpegConfig {
    int getNextScanNumberToDecode(int i);

    QualityInfo getQualityInfo(int i);
}
