package com.journeyapps.barcodescanner;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.HybridBinarizer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Decoder implements ResultPointCallback {
    private List<ResultPoint> possibleResultPoints = new ArrayList();
    private Reader reader;

    public Decoder(Reader reader) {
        this.reader = reader;
    }

    public Result decode(LuminanceSource source) {
        return decode(toBitmap(source));
    }

    protected BinaryBitmap toBitmap(LuminanceSource source) {
        return new BinaryBitmap(new HybridBinarizer(source));
    }

    protected Result decode(BinaryBitmap bitmap) {
        Result resultDecode;
        this.possibleResultPoints.clear();
        try {
            if (this.reader instanceof MultiFormatReader) {
                resultDecode = ((MultiFormatReader) this.reader).decodeWithState(bitmap);
            } else {
                resultDecode = this.reader.decode(bitmap);
            }
            return resultDecode;
        } catch (Exception e) {
            return null;
        } finally {
            this.reader.reset();
        }
    }

    public List<ResultPoint> getPossibleResultPoints() {
        return new ArrayList(this.possibleResultPoints);
    }

    @Override // com.google.zxing.ResultPointCallback
    public void foundPossibleResultPoint(ResultPoint point) {
        this.possibleResultPoints.add(point);
    }
}
