package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.ReaderException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.tencent.android.tpush.common.Constants;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

    /* JADX WARN: Code duplicated, block: B:12:0x0030  */
    /* JADX WARN: Code duplicated, block: B:15:0x0034 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:16:0x0036  */
    /* JADX WARN: Code duplicated, block: B:17:0x0037  */
    public DecoderResult decode(BitMatrix bits, Map<DecodeHintType, ?> hints) throws ChecksumException, FormatException {
        ReaderException e;
        BitMatrixParser parser = new BitMatrixParser(bits);
        FormatException fe = null;
        ChecksumException ce = null;
        try {
            return decode(parser, hints);
        } catch (ChecksumException e2) {
            ce = e2;
            try {
                parser.remask();
                parser.setMirror(true);
                parser.readVersion();
                parser.readFormatInformation();
                parser.mirror();
                DecoderResult result = decode(parser, hints);
                result.setOther(new QRCodeDecoderMetaData(true));
                return result;
            } catch (ChecksumException e3) {
                e = e3;
                if (fe != null) {
                    throw fe;
                }
                if (ce != null) {
                    throw ce;
                }
                throw e;
            } catch (FormatException e4) {
                e = e4;
                if (fe != null) {
                    throw fe;
                }
                if (ce != null) {
                    throw ce;
                }
                throw e;
            }
        } catch (FormatException e5) {
            fe = e5;
            parser.remask();
            parser.setMirror(true);
            parser.readVersion();
            parser.readFormatInformation();
            parser.mirror();
            DecoderResult result2 = decode(parser, hints);
            result2.setOther(new QRCodeDecoderMetaData(true));
            return result2;
        }
    }

    private DecoderResult decode(BitMatrixParser parser, Map<DecodeHintType, ?> hints) throws ChecksumException, FormatException {
        Version version = parser.readVersion();
        ErrorCorrectionLevel ecLevel = parser.readFormatInformation().getErrorCorrectionLevel();
        byte[] codewords = parser.readCodewords();
        DataBlock[] dataBlocks = DataBlock.getDataBlocks(codewords, version, ecLevel);
        int totalBytes = 0;
        for (DataBlock dataBlock : dataBlocks) {
            totalBytes += dataBlock.getNumDataCodewords();
        }
        byte[] resultBytes = new byte[totalBytes];
        int resultOffset = 0;
        int length = dataBlocks.length;
        int i = 0;
        while (i < length) {
            DataBlock dataBlock2 = dataBlocks[i];
            byte[] codewordBytes = dataBlock2.getCodewords();
            int numDataCodewords = dataBlock2.getNumDataCodewords();
            correctErrors(codewordBytes, numDataCodewords);
            int i2 = 0;
            int resultOffset2 = resultOffset;
            while (i2 < numDataCodewords) {
                resultBytes[resultOffset2] = codewordBytes[i2];
                i2++;
                resultOffset2++;
            }
            i++;
            resultOffset = resultOffset2;
        }
        return DecodedBitStreamParser.decode(resultBytes, version, ecLevel, hints);
    }

    private void correctErrors(byte[] codewordBytes, int numDataCodewords) throws ChecksumException {
        int numCodewords = codewordBytes.length;
        int[] codewordsInts = new int[numCodewords];
        for (int i = 0; i < numCodewords; i++) {
            codewordsInts[i] = codewordBytes[i] & Constants.NETWORK_TYPE_UNCONNECTED;
        }
        int numECCodewords = codewordBytes.length - numDataCodewords;
        try {
            this.rsDecoder.decode(codewordsInts, numECCodewords);
            for (int i2 = 0; i2 < numDataCodewords; i2++) {
                codewordBytes[i2] = (byte) codewordsInts[i2];
            }
        } catch (ReedSolomonException e) {
            throw ChecksumException.getChecksumInstance();
        }
    }
}
