package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class Code128Reader extends OneDReader {
    static final int[][] CODE_PATTERNS = {new int[]{2, 1, 2, 2, 2, 2}, new int[]{2, 2, 2, 1, 2, 2}, new int[]{2, 2, 2, 2, 2, 1}, new int[]{1, 2, 1, 2, 2, 3}, new int[]{1, 2, 1, 3, 2, 2}, new int[]{1, 3, 1, 2, 2, 2}, new int[]{1, 2, 2, 2, 1, 3}, new int[]{1, 2, 2, 3, 1, 2}, new int[]{1, 3, 2, 2, 1, 2}, new int[]{2, 2, 1, 2, 1, 3}, new int[]{2, 2, 1, 3, 1, 2}, new int[]{2, 3, 1, 2, 1, 2}, new int[]{1, 1, 2, 2, 3, 2}, new int[]{1, 2, 2, 1, 3, 2}, new int[]{1, 2, 2, 2, 3, 1}, new int[]{1, 1, 3, 2, 2, 2}, new int[]{1, 2, 3, 1, 2, 2}, new int[]{1, 2, 3, 2, 2, 1}, new int[]{2, 2, 3, 2, 1, 1}, new int[]{2, 2, 1, 1, 3, 2}, new int[]{2, 2, 1, 2, 3, 1}, new int[]{2, 1, 3, 2, 1, 2}, new int[]{2, 2, 3, 1, 1, 2}, new int[]{3, 1, 2, 1, 3, 1}, new int[]{3, 1, 1, 2, 2, 2}, new int[]{3, 2, 1, 1, 2, 2}, new int[]{3, 2, 1, 2, 2, 1}, new int[]{3, 1, 2, 2, 1, 2}, new int[]{3, 2, 2, 1, 1, 2}, new int[]{3, 2, 2, 2, 1, 1}, new int[]{2, 1, 2, 1, 2, 3}, new int[]{2, 1, 2, 3, 2, 1}, new int[]{2, 3, 2, 1, 2, 1}, new int[]{1, 1, 1, 3, 2, 3}, new int[]{1, 3, 1, 1, 2, 3}, new int[]{1, 3, 1, 3, 2, 1}, new int[]{1, 1, 2, 3, 1, 3}, new int[]{1, 3, 2, 1, 1, 3}, new int[]{1, 3, 2, 3, 1, 1}, new int[]{2, 1, 1, 3, 1, 3}, new int[]{2, 3, 1, 1, 1, 3}, new int[]{2, 3, 1, 3, 1, 1}, new int[]{1, 1, 2, 1, 3, 3}, new int[]{1, 1, 2, 3, 3, 1}, new int[]{1, 3, 2, 1, 3, 1}, new int[]{1, 1, 3, 1, 2, 3}, new int[]{1, 1, 3, 3, 2, 1}, new int[]{1, 3, 3, 1, 2, 1}, new int[]{3, 1, 3, 1, 2, 1}, new int[]{2, 1, 1, 3, 3, 1}, new int[]{2, 3, 1, 1, 3, 1}, new int[]{2, 1, 3, 1, 1, 3}, new int[]{2, 1, 3, 3, 1, 1}, new int[]{2, 1, 3, 1, 3, 1}, new int[]{3, 1, 1, 1, 2, 3}, new int[]{3, 1, 1, 3, 2, 1}, new int[]{3, 3, 1, 1, 2, 1}, new int[]{3, 1, 2, 1, 1, 3}, new int[]{3, 1, 2, 3, 1, 1}, new int[]{3, 3, 2, 1, 1, 1}, new int[]{3, 1, 4, 1, 1, 1}, new int[]{2, 2, 1, 4, 1, 1}, new int[]{4, 3, 1, 1, 1, 1}, new int[]{1, 1, 1, 2, 2, 4}, new int[]{1, 1, 1, 4, 2, 2}, new int[]{1, 2, 1, 1, 2, 4}, new int[]{1, 2, 1, 4, 2, 1}, new int[]{1, 4, 1, 1, 2, 2}, new int[]{1, 4, 1, 2, 2, 1}, new int[]{1, 1, 2, 2, 1, 4}, new int[]{1, 1, 2, 4, 1, 2}, new int[]{1, 2, 2, 1, 1, 4}, new int[]{1, 2, 2, 4, 1, 1}, new int[]{1, 4, 2, 1, 1, 2}, new int[]{1, 4, 2, 2, 1, 1}, new int[]{2, 4, 1, 2, 1, 1}, new int[]{2, 2, 1, 1, 1, 4}, new int[]{4, 1, 3, 1, 1, 1}, new int[]{2, 4, 1, 1, 1, 2}, new int[]{1, 3, 4, 1, 1, 1}, new int[]{1, 1, 1, 2, 4, 2}, new int[]{1, 2, 1, 1, 4, 2}, new int[]{1, 2, 1, 2, 4, 1}, new int[]{1, 1, 4, 2, 1, 2}, new int[]{1, 2, 4, 1, 1, 2}, new int[]{1, 2, 4, 2, 1, 1}, new int[]{4, 1, 1, 2, 1, 2}, new int[]{4, 2, 1, 1, 1, 2}, new int[]{4, 2, 1, 2, 1, 1}, new int[]{2, 1, 2, 1, 4, 1}, new int[]{2, 1, 4, 1, 2, 1}, new int[]{4, 1, 2, 1, 2, 1}, new int[]{1, 1, 1, 1, 4, 3}, new int[]{1, 1, 1, 3, 4, 1}, new int[]{1, 3, 1, 1, 4, 1}, new int[]{1, 1, 4, 1, 1, 3}, new int[]{1, 1, 4, 3, 1, 1}, new int[]{4, 1, 1, 1, 1, 3}, new int[]{4, 1, 1, 3, 1, 1}, new int[]{1, 1, 3, 1, 4, 1}, new int[]{1, 1, 4, 1, 3, 1}, new int[]{3, 1, 1, 1, 4, 1}, new int[]{4, 1, 1, 1, 3, 1}, new int[]{2, 1, 1, 4, 1, 2}, new int[]{2, 1, 1, 2, 1, 4}, new int[]{2, 1, 1, 2, 3, 2}, new int[]{2, 3, 3, 1, 1, 1, 2}};

    private static int[] findStartPattern(BitArray row) throws NotFoundException {
        int width = row.getSize();
        int rowOffset = row.getNextSet(0);
        int counterPosition = 0;
        int[] counters = new int[6];
        int patternStart = rowOffset;
        boolean isWhite = false;
        int patternLength = counters.length;
        for (int i = rowOffset; i < width; i++) {
            if (row.get(i) ^ isWhite) {
                counters[counterPosition] = counters[counterPosition] + 1;
            } else {
                if (counterPosition == patternLength - 1) {
                    float bestVariance = 0.25f;
                    int bestMatch = -1;
                    for (int startCode = 103; startCode <= 105; startCode++) {
                        float variance = patternMatchVariance(counters, CODE_PATTERNS[startCode], 0.7f);
                        if (variance < bestVariance) {
                            bestVariance = variance;
                            bestMatch = startCode;
                        }
                    }
                    if (bestMatch >= 0 && row.isRange(Math.max(0, patternStart - ((i - patternStart) / 2)), patternStart, false)) {
                        return new int[]{patternStart, i, bestMatch};
                    }
                    patternStart += counters[0] + counters[1];
                    System.arraycopy(counters, 2, counters, 0, patternLength - 2);
                    counters[patternLength - 2] = 0;
                    counters[patternLength - 1] = 0;
                    counterPosition--;
                } else {
                    counterPosition++;
                }
                counters[counterPosition] = 1;
                isWhite = !isWhite;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int decodeCode(BitArray row, int[] counters, int rowOffset) throws NotFoundException {
        recordPattern(row, rowOffset, counters);
        float bestVariance = 0.25f;
        int bestMatch = -1;
        for (int d = 0; d < CODE_PATTERNS.length; d++) {
            int[] pattern = CODE_PATTERNS[d];
            float variance = patternMatchVariance(counters, pattern, 0.7f);
            if (variance < bestVariance) {
                bestVariance = variance;
                bestMatch = d;
            }
        }
        if (bestMatch >= 0) {
            return bestMatch;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARN: Code duplicated, block: B:100:0x01fc  */
    /* JADX WARN: Code duplicated, block: B:102:0x0202  */
    /* JADX WARN: Code duplicated, block: B:104:0x0212  */
    /* JADX WARN: Code duplicated, block: B:106:0x0218  */
    /* JADX WARN: Code duplicated, block: B:109:0x021e  */
    /* JADX WARN: Code duplicated, block: B:110:0x0222  */
    /* JADX WARN: Code duplicated, block: B:111:0x0224  */
    /* JADX WARN: Code duplicated, block: B:113:0x022a  */
    /* JADX WARN: Code duplicated, block: B:114:0x0235  */
    /* JADX WARN: Code duplicated, block: B:115:0x0240  */
    /* JADX WARN: Code duplicated, block: B:116:0x0244  */
    /* JADX WARN: Code duplicated, block: B:143:0x00be A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:144:0x00b5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:145:0x0247 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:146:0x00bb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:148:0x0064 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:14:0x0066  */
    /* JADX WARN: Code duplicated, block: B:16:0x0086  */
    /* JADX WARN: Code duplicated, block: B:19:0x008d  */
    /* JADX WARN: Code duplicated, block: B:23:0x00a0 A[LOOP:1: B:21:0x009a->B:23:0x00a0, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:27:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:35:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:37:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:39:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:41:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:42:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:44:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:46:0x0101  */
    /* JADX WARN: Code duplicated, block: B:48:0x0112  */
    /* JADX WARN: Code duplicated, block: B:49:0x0121  */
    /* JADX WARN: Code duplicated, block: B:51:0x0127  */
    /* JADX WARN: Code duplicated, block: B:54:0x012c  */
    /* JADX WARN: Code duplicated, block: B:55:0x0130  */
    /* JADX WARN: Code duplicated, block: B:56:0x0132  */
    /* JADX WARN: Code duplicated, block: B:58:0x0138  */
    /* JADX WARN: Code duplicated, block: B:59:0x0143  */
    /* JADX WARN: Code duplicated, block: B:60:0x014e  */
    /* JADX WARN: Code duplicated, block: B:61:0x0150 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:63:0x0158 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:64:0x015a A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:66:0x0162  */
    /* JADX WARN: Code duplicated, block: B:67:0x0166  */
    /* JADX WARN: Code duplicated, block: B:68:0x016a  */
    /* JADX WARN: Code duplicated, block: B:69:0x016e  */
    /* JADX WARN: Code duplicated, block: B:70:0x0171  */
    /* JADX WARN: Code duplicated, block: B:72:0x0177  */
    /* JADX WARN: Code duplicated, block: B:74:0x017d  */
    /* JADX WARN: Code duplicated, block: B:76:0x018f  */
    /* JADX WARN: Code duplicated, block: B:77:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:79:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:82:0x01b0  */
    /* JADX WARN: Code duplicated, block: B:83:0x01b5  */
    /* JADX WARN: Code duplicated, block: B:84:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:86:0x01bd  */
    /* JADX WARN: Code duplicated, block: B:87:0x01c8  */
    /* JADX WARN: Code duplicated, block: B:88:0x01d3  */
    /* JADX WARN: Code duplicated, block: B:89:0x01d5 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:91:0x01dd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:92:0x01df A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:94:0x01e7  */
    /* JADX WARN: Code duplicated, block: B:95:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:96:0x01ef  */
    /* JADX WARN: Code duplicated, block: B:97:0x01f3  */
    /* JADX WARN: Code duplicated, block: B:98:0x01f6  */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:94:0x01e7
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @Override // com.google.zxing.oned.OneDReader
    public com.google.zxing.Result decodeRow(int r39, com.google.zxing.common.BitArray r40, java.util.Map<com.google.zxing.DecodeHintType, ?> r41) throws com.google.zxing.NotFoundException, com.google.zxing.FormatException, com.google.zxing.ChecksumException {
        /*
            Method dump skipped, instruction units count: 928
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code128Reader.decodeRow(int, com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }
}
