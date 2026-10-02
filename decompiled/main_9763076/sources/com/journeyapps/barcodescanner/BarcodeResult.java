package com.journeyapps.barcodescanner;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BarcodeResult {
    protected Result mResult;
    private final int mScaleFactor = 2;
    protected SourceData sourceData;

    public BarcodeResult(Result result, SourceData sourceData) {
        this.mResult = result;
        this.sourceData = sourceData;
    }

    public Bitmap getBitmap() {
        return this.sourceData.getBitmap(2);
    }

    public byte[] getRawBytes() {
        return this.mResult.getRawBytes();
    }

    public BarcodeFormat getBarcodeFormat() {
        return this.mResult.getBarcodeFormat();
    }

    public Map<ResultMetadataType, Object> getResultMetadata() {
        return this.mResult.getResultMetadata();
    }

    public String toString() {
        return this.mResult.getText();
    }
}
