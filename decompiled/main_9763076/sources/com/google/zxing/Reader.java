package com.google.zxing;

import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface Reader {
    Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException;

    Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException;

    void reset();
}
