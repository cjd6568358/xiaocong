package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.aztec.decoder.Decoder;
import com.google.zxing.aztec.detector.Detector;
import com.google.zxing.common.DecoderResult;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AztecReader implements Reader {
    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap image) throws NotFoundException, FormatException {
        return decode(image, null);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0057  */
    /* JADX WARN: Code duplicated, block: B:21:0x0058 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:22:0x005a  */
    /* JADX WARN: Code duplicated, block: B:23:0x005b  */
    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws ReaderException {
        ResultPointCallback rpcb;
        ReaderException e;
        NotFoundException notFoundException = null;
        FormatException formatException = null;
        Detector detector = new Detector(image.getBlackMatrix());
        ResultPoint[] points = null;
        DecoderResult decoderResult = null;
        try {
            AztecDetectorResult detectorResult = detector.detect(false);
            points = detectorResult.getPoints();
            decoderResult = new Decoder().decode(detectorResult);
        } catch (FormatException e2) {
            formatException = e2;
        } catch (NotFoundException e3) {
            notFoundException = e3;
        }
        if (decoderResult == null) {
            try {
                AztecDetectorResult detectorResult2 = detector.detect(true);
                points = detectorResult2.getPoints();
                decoderResult = new Decoder().decode(detectorResult2);
            } catch (FormatException e4) {
                e = e4;
                if (notFoundException != null) {
                    throw notFoundException;
                }
                if (formatException != null) {
                    throw formatException;
                }
                throw e;
            } catch (NotFoundException e5) {
                e = e5;
                if (notFoundException != null) {
                    throw notFoundException;
                }
                if (formatException != null) {
                    throw formatException;
                }
                throw e;
            }
        }
        if (hints != null && (rpcb = (ResultPointCallback) hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK)) != null) {
            for (ResultPoint point : points) {
                rpcb.foundPossibleResultPoint(point);
            }
        }
        Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), points, BarcodeFormat.AZTEC);
        List<byte[]> byteSegments = decoderResult.getByteSegments();
        if (byteSegments != null) {
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        String ecLevel = decoderResult.getECLevel();
        if (ecLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
        }
        return result;
    }

    @Override // com.google.zxing.Reader
    public void reset() {
    }
}
