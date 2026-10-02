package com.xiaocong.smarthome.zxing;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.xiaocong.smarthome.zxing.camera.CameraManager;
import java.util.Hashtable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class DecodeHandler extends Handler {
    private static final String TAG = "Barcode_" + DecodeHandler.class.getSimpleName();
    private final ScanCodeActivity activity;
    private final MultiFormatReader multiFormatReader = new MultiFormatReader();

    DecodeHandler(ScanCodeActivity activity, Hashtable<DecodeHintType, Object> hints) {
        this.multiFormatReader.setHints(hints);
        this.activity = activity;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message.what == R.id.decode) {
            try {
                decode((byte[]) message.obj, message.arg1, message.arg2);
                return;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                return;
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
                return;
            }
        }
        if (message.what == R.id.quit) {
            Looper.myLooper().quit();
        }
    }

    private void decode(byte[] data, int width, int height) {
        long start = System.currentTimeMillis();
        Result rawResult = null;
        if (data != null) {
            byte[] rotatedData = new byte[data.length];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    rotatedData[(((x * height) + height) - y) - 1] = data[(y * width) + x];
                }
            }
            try {
                PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData, height, width);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                if (bitmap != null && this.multiFormatReader != null) {
                    try {
                        rawResult = this.multiFormatReader.decodeWithState(bitmap);
                    } catch (Exception e) {
                    } finally {
                        this.multiFormatReader.reset();
                    }
                    if (rawResult != null) {
                        long end = System.currentTimeMillis();
                        Log.d(TAG, "Found barcode (" + (end - start) + " ms):\n" + rawResult.toString());
                        Message message = Message.obtain(this.activity.getHandler(), R.id.decode_succeeded, rawResult);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("barcode_bitmap", source.renderCroppedGreyscaleBitmap());
                        message.setData(bundle);
                        message.sendToTarget();
                        return;
                    }
                    Message message2 = Message.obtain(this.activity.getHandler(), R.id.decode_failed);
                    message2.sendToTarget();
                }
            } catch (Exception e2) {
                Message message3 = Message.obtain(this.activity.getHandler(), R.id.decode_failed);
                message3.sendToTarget();
            }
        }
    }
}
