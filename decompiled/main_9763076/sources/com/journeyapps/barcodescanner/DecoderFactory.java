package com.journeyapps.barcodescanner;

import com.google.zxing.DecodeHintType;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface DecoderFactory {
    Decoder createDecoder(Map<DecodeHintType, ?> map);
}
