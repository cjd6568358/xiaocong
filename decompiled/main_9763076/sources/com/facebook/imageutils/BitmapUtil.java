package com.facebook.imageutils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.util.Pools;
import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BitmapUtil {
    private static final Pools.SynchronizedPool<ByteBuffer> DECODE_BUFFERS = new Pools.SynchronizedPool<>(12);

    @SuppressLint({"NewApi"})
    public static int getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT > 19) {
            try {
                return bitmap.getAllocationByteCount();
            } catch (NullPointerException e) {
            }
        }
        if (Build.VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount();
        }
        return bitmap.getWidth() * bitmap.getRowBytes();
    }

    public static Pair<Integer, Integer> decodeDimensions(InputStream is) {
        Pair<Integer, Integer> pair = null;
        Preconditions.checkNotNull(is);
        ByteBuffer byteBuffer = DECODE_BUFFERS.acquire();
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(16384);
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            options.inTempStorage = byteBuffer.array();
            BitmapFactory.decodeStream(is, null, options);
            if (options.outWidth != -1 && options.outHeight != -1) {
                pair = new Pair<>(Integer.valueOf(options.outWidth), Integer.valueOf(options.outHeight));
            }
            return pair;
        } finally {
            DECODE_BUFFERS.release(byteBuffer);
        }
    }

    /* JADX INFO: renamed from: com.facebook.imageutils.BitmapUtil$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config = new int[Bitmap.Config.values().length];

        static {
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_8888.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ALPHA_8.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.RGB_565.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static int getPixelSizeForBitmapConfig(Bitmap.Config bitmapConfig) {
        switch (AnonymousClass1.$SwitchMap$android$graphics$Bitmap$Config[bitmapConfig.ordinal()]) {
            case 1:
                return 4;
            case 2:
                return 1;
            case 3:
            case 4:
                return 2;
            default:
                throw new UnsupportedOperationException("The provided Bitmap.Config is not supported");
        }
    }

    public static int getSizeInByteForBitmap(int width, int height, Bitmap.Config bitmapConfig) {
        return width * height * getPixelSizeForBitmapConfig(bitmapConfig);
    }
}
