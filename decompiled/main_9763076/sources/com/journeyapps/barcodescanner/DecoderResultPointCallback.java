package com.journeyapps.barcodescanner;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DecoderResultPointCallback implements ResultPointCallback {
    private Decoder decoder;

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override // com.google.zxing.ResultPointCallback
    public void foundPossibleResultPoint(ResultPoint point) {
        if (this.decoder != null) {
            this.decoder.foundPossibleResultPoint(point);
        }
    }
}
