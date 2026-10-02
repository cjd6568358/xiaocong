package com.journeyapps.barcodescanner;

import com.google.zxing.ResultPoint;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface BarcodeCallback {
    void barcodeResult(BarcodeResult barcodeResult);

    void possibleResultPoints(List<ResultPoint> list);
}
