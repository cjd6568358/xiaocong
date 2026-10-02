package com.xiaocong.smarthome.zxing;

import android.os.Handler;
import android.os.Looper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class DecodeThread extends Thread {
    private final ScanCodeActivity activity;
    private Handler handler;
    private final CountDownLatch handlerInitLatch = new CountDownLatch(1);
    private final Hashtable<DecodeHintType, Object> hints = new Hashtable<>(3);

    DecodeThread(ScanCodeActivity activity, Vector<BarcodeFormat> decodeFormats, String characterSet, ResultPointCallback resultPointCallback) {
        this.activity = activity;
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = new Vector<>();
            decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        this.hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        if (characterSet != null) {
            this.hints.put(DecodeHintType.CHARACTER_SET, characterSet);
        }
        this.hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
    }

    Handler getHandler() {
        try {
            this.handlerInitLatch.await();
        } catch (InterruptedException e) {
        }
        return this.handler;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Looper.prepare();
        this.handler = new DecodeHandler(this.activity, this.hints);
        this.handlerInitLatch.countDown();
        Looper.loop();
    }
}
