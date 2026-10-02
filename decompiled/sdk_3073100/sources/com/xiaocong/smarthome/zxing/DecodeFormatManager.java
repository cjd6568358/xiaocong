package com.xiaocong.smarthome.zxing;

import com.google.zxing.BarcodeFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class DecodeFormatManager {
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    static final Collection<BarcodeFormat> QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE);
    static final Collection<BarcodeFormat> DATA_MATRIX_FORMATS = EnumSet.of(BarcodeFormat.DATA_MATRIX);
    static final Collection<BarcodeFormat> PRODUCT_FORMATS = EnumSet.of(BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.RSS_14);
    static final Collection<BarcodeFormat> ONE_D_FORMATS = EnumSet.of(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR);

    static {
        ONE_D_FORMATS.addAll(PRODUCT_FORMATS);
    }
}
