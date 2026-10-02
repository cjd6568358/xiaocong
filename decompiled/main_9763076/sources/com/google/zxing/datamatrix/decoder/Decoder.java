package com.google.zxing.datamatrix.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);

    public DecoderResult decode(BitMatrix bits) throws ChecksumException, FormatException {
        BitMatrixParser parser = new BitMatrixParser(bits);
        Version version = parser.getVersion();
        byte[] codewords = parser.readCodewords();
        DataBlock[] dataBlocks = DataBlock.getDataBlocks(codewords, version);
        int dataBlocksCount = dataBlocks.length;
        int totalBytes = 0;
        for (DataBlock db : dataBlocks) {
            totalBytes += db.getNumDataCodewords();
        }
        byte[] resultBytes = new byte[totalBytes];
        for (int j = 0; j < dataBlocksCount; j++) {
            DataBlock dataBlock = dataBlocks[j];
            byte[] codewordBytes = dataBlock.getCodewords();
            int numDataCodewords = dataBlock.getNumDataCodewords();
            correctErrors(codewordBytes, numDataCodewords);
            for (int i = 0; i < numDataCodewords; i++) {
                resultBytes[(i * dataBlocksCount) + j] = codewordBytes[i];
            }
        }
        return DecodedBitStreamParser.decode(resultBytes);
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
