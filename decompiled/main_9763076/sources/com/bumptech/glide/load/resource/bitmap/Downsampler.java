package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.ByteArrayPool;
import com.bumptech.glide.util.ExceptionCatchingInputStream;
import com.bumptech.glide.util.MarkEnforcingInputStream;
import com.bumptech.glide.util.Util;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Queue;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class Downsampler implements BitmapDecoder<InputStream> {
    private static final Set<ImageHeaderParser.ImageType> TYPES_THAT_USE_POOL = EnumSet.of(ImageHeaderParser.ImageType.JPEG, ImageHeaderParser.ImageType.PNG_A, ImageHeaderParser.ImageType.PNG);
    private static final Queue<BitmapFactory.Options> OPTIONS_QUEUE = Util.createQueue(0);
    public static final Downsampler AT_LEAST = new Downsampler() { // from class: com.bumptech.glide.load.resource.bitmap.Downsampler.1
        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler
        protected int getSampleSize(int inWidth, int inHeight, int outWidth, int outHeight) {
            return Math.min(inHeight / outHeight, inWidth / outWidth);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.BitmapDecoder
        public String getId() {
            return "AT_LEAST.com.bumptech.glide.load.data.bitmap";
        }
    };
    public static final Downsampler AT_MOST = new Downsampler() { // from class: com.bumptech.glide.load.resource.bitmap.Downsampler.2
        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler
        protected int getSampleSize(int inWidth, int inHeight, int outWidth, int outHeight) {
            int maxIntegerFactor = (int) Math.ceil(Math.max(inHeight / outHeight, inWidth / outWidth));
            int lesserOrEqualSampleSize = Math.max(1, Integer.highestOneBit(maxIntegerFactor));
            return lesserOrEqualSampleSize << (lesserOrEqualSampleSize >= maxIntegerFactor ? 0 : 1);
        }

        @Override // com.bumptech.glide.load.resource.bitmap.BitmapDecoder
        public String getId() {
            return "AT_MOST.com.bumptech.glide.load.data.bitmap";
        }
    };
    public static final Downsampler NONE = new Downsampler() { // from class: com.bumptech.glide.load.resource.bitmap.Downsampler.3
        @Override // com.bumptech.glide.load.resource.bitmap.Downsampler
        protected int getSampleSize(int inWidth, int inHeight, int outWidth, int outHeight) {
            return 0;
        }

        @Override // com.bumptech.glide.load.resource.bitmap.BitmapDecoder
        public String getId() {
            return "NONE.com.bumptech.glide.load.data.bitmap";
        }
    };

    protected abstract int getSampleSize(int i, int i2, int i3, int i4);

    public Bitmap decode(InputStream is, BitmapPool pool, int outWidth, int outHeight, DecodeFormat decodeFormat) {
        ByteArrayPool byteArrayPool = ByteArrayPool.get();
        byte[] bytesForOptions = byteArrayPool.getBytes();
        byte[] bytesForStream = byteArrayPool.getBytes();
        BitmapFactory.Options options = getDefaultOptions();
        RecyclableBufferedInputStream bufferedStream = new RecyclableBufferedInputStream(is, bytesForStream);
        ExceptionCatchingInputStream exceptionStream = ExceptionCatchingInputStream.obtain(bufferedStream);
        MarkEnforcingInputStream invalidatingStream = new MarkEnforcingInputStream(exceptionStream);
        try {
            exceptionStream.mark(5242880);
            int orientation = 0;
            try {
                try {
                    orientation = new ImageHeaderParser(exceptionStream).getOrientation();
                    try {
                        exceptionStream.reset();
                    } catch (IOException e) {
                        if (Log.isLoggable("Downsampler", 5)) {
                            Log.w("Downsampler", "Cannot reset the input stream", e);
                        }
                    }
                } catch (IOException e2) {
                    if (Log.isLoggable("Downsampler", 5)) {
                        Log.w("Downsampler", "Cannot determine the image orientation from header", e2);
                    }
                    try {
                        exceptionStream.reset();
                    } catch (IOException e3) {
                        if (Log.isLoggable("Downsampler", 5)) {
                            Log.w("Downsampler", "Cannot reset the input stream", e3);
                        }
                    }
                }
                options.inTempStorage = bytesForOptions;
                int[] inDimens = getDimensions(invalidatingStream, bufferedStream, options);
                int inWidth = inDimens[0];
                int inHeight = inDimens[1];
                int degreesToRotate = TransformationUtils.getExifOrientationDegrees(orientation);
                int sampleSize = getRoundedSampleSize(degreesToRotate, inWidth, inHeight, outWidth, outHeight);
                Bitmap downsampled = downsampleWithSize(invalidatingStream, bufferedStream, options, pool, inWidth, inHeight, sampleSize, decodeFormat);
                Exception streamException = exceptionStream.getException();
                if (streamException != null) {
                    throw new RuntimeException(streamException);
                }
                Bitmap rotated = null;
                if (downsampled != null) {
                    rotated = TransformationUtils.rotateImageExif(downsampled, pool, orientation);
                    if (!downsampled.equals(rotated) && !pool.put(downsampled)) {
                        downsampled.recycle();
                    }
                }
                byteArrayPool.releaseBytes(bytesForOptions);
                byteArrayPool.releaseBytes(bytesForStream);
                exceptionStream.release();
                releaseOptions(options);
                return rotated;
            } catch (Throwable th) {
                try {
                    exceptionStream.reset();
                } catch (IOException e4) {
                    if (Log.isLoggable("Downsampler", 5)) {
                        Log.w("Downsampler", "Cannot reset the input stream", e4);
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            byteArrayPool.releaseBytes(bytesForOptions);
            byteArrayPool.releaseBytes(bytesForStream);
            exceptionStream.release();
            releaseOptions(options);
            throw th2;
        }
    }

    private int getRoundedSampleSize(int degreesToRotate, int inWidth, int inHeight, int outWidth, int outHeight) {
        int exactSampleSize;
        int targetHeight = outHeight == Integer.MIN_VALUE ? inHeight : outHeight;
        int targetWidth = outWidth == Integer.MIN_VALUE ? inWidth : outWidth;
        if (degreesToRotate == 90 || degreesToRotate == 270) {
            exactSampleSize = getSampleSize(inHeight, inWidth, targetWidth, targetHeight);
        } else {
            exactSampleSize = getSampleSize(inWidth, inHeight, targetWidth, targetHeight);
        }
        int powerOfTwoSampleSize = exactSampleSize == 0 ? 0 : Integer.highestOneBit(exactSampleSize);
        return Math.max(1, powerOfTwoSampleSize);
    }

    private Bitmap downsampleWithSize(MarkEnforcingInputStream is, RecyclableBufferedInputStream bufferedStream, BitmapFactory.Options options, BitmapPool pool, int inWidth, int inHeight, int sampleSize, DecodeFormat decodeFormat) {
        Bitmap.Config config = getConfig(is, decodeFormat);
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = config;
        if ((options.inSampleSize == 1 || 19 <= Build.VERSION.SDK_INT) && shouldUsePool(is)) {
            int targetWidth = (int) Math.ceil(((double) inWidth) / ((double) sampleSize));
            int targetHeight = (int) Math.ceil(((double) inHeight) / ((double) sampleSize));
            setInBitmap(options, pool.getDirty(targetWidth, targetHeight, config));
        }
        return decodeStream(is, bufferedStream, options);
    }

    private static boolean shouldUsePool(InputStream is) {
        if (19 <= Build.VERSION.SDK_INT) {
            return true;
        }
        is.mark(WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
        try {
            ImageHeaderParser.ImageType type = new ImageHeaderParser(is).getType();
            boolean zContains = TYPES_THAT_USE_POOL.contains(type);
            try {
                return zContains;
            } catch (IOException e) {
            }
        } catch (IOException e2) {
            if (Log.isLoggable("Downsampler", 5)) {
                Log.w("Downsampler", "Cannot determine the image type from header", e2);
            }
            return false;
        } finally {
            try {
                is.reset();
            } catch (IOException e3) {
                if (Log.isLoggable("Downsampler", 5)) {
                    Log.w("Downsampler", "Cannot reset the input stream", e3);
                }
            }
        }
    }

    private static Bitmap.Config getConfig(InputStream is, DecodeFormat format) {
        if (format == DecodeFormat.ALWAYS_ARGB_8888 || format == DecodeFormat.PREFER_ARGB_8888 || Build.VERSION.SDK_INT == 16) {
            return Bitmap.Config.ARGB_8888;
        }
        boolean hasAlpha = false;
        is.mark(WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
        try {
            try {
                hasAlpha = new ImageHeaderParser(is).hasAlpha();
                try {
                    is.reset();
                } catch (IOException e) {
                    if (Log.isLoggable("Downsampler", 5)) {
                        Log.w("Downsampler", "Cannot reset the input stream", e);
                    }
                }
            } catch (IOException e2) {
                if (Log.isLoggable("Downsampler", 5)) {
                    Log.w("Downsampler", "Cannot determine whether the image has alpha or not from header for format " + format, e2);
                }
                try {
                    is.reset();
                } catch (IOException e3) {
                    if (Log.isLoggable("Downsampler", 5)) {
                        Log.w("Downsampler", "Cannot reset the input stream", e3);
                    }
                }
            }
            return hasAlpha ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        } catch (Throwable th) {
            try {
                is.reset();
            } catch (IOException e4) {
                if (Log.isLoggable("Downsampler", 5)) {
                    Log.w("Downsampler", "Cannot reset the input stream", e4);
                }
            }
            throw th;
        }
    }

    public int[] getDimensions(MarkEnforcingInputStream is, RecyclableBufferedInputStream bufferedStream, BitmapFactory.Options options) {
        options.inJustDecodeBounds = true;
        decodeStream(is, bufferedStream, options);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    private static Bitmap decodeStream(MarkEnforcingInputStream is, RecyclableBufferedInputStream bufferedStream, BitmapFactory.Options options) {
        if (options.inJustDecodeBounds) {
            is.mark(5242880);
        } else {
            bufferedStream.fixMarkLimit();
        }
        Bitmap result = BitmapFactory.decodeStream(is, null, options);
        try {
            if (options.inJustDecodeBounds) {
                is.reset();
            }
        } catch (IOException e) {
            if (Log.isLoggable("Downsampler", 6)) {
                Log.e("Downsampler", "Exception loading inDecodeBounds=" + options.inJustDecodeBounds + " sample=" + options.inSampleSize, e);
            }
        }
        return result;
    }

    @TargetApi(11)
    private static void setInBitmap(BitmapFactory.Options options, Bitmap recycled) {
        if (11 <= Build.VERSION.SDK_INT) {
            options.inBitmap = recycled;
        }
    }

    @TargetApi(11)
    private static synchronized BitmapFactory.Options getDefaultOptions() {
        BitmapFactory.Options decodeBitmapOptions;
        synchronized (OPTIONS_QUEUE) {
            decodeBitmapOptions = OPTIONS_QUEUE.poll();
        }
        if (decodeBitmapOptions == null) {
            decodeBitmapOptions = new BitmapFactory.Options();
            resetOptions(decodeBitmapOptions);
        }
        return decodeBitmapOptions;
    }

    private static void releaseOptions(BitmapFactory.Options decodeBitmapOptions) {
        resetOptions(decodeBitmapOptions);
        synchronized (OPTIONS_QUEUE) {
            OPTIONS_QUEUE.offer(decodeBitmapOptions);
        }
    }

    @TargetApi(11)
    private static void resetOptions(BitmapFactory.Options decodeBitmapOptions) {
        decodeBitmapOptions.inTempStorage = null;
        decodeBitmapOptions.inDither = false;
        decodeBitmapOptions.inScaled = false;
        decodeBitmapOptions.inSampleSize = 1;
        decodeBitmapOptions.inPreferredConfig = null;
        decodeBitmapOptions.inJustDecodeBounds = false;
        decodeBitmapOptions.outWidth = 0;
        decodeBitmapOptions.outHeight = 0;
        decodeBitmapOptions.outMimeType = null;
        if (11 <= Build.VERSION.SDK_INT) {
            decodeBitmapOptions.inBitmap = null;
            decodeBitmapOptions.inMutable = true;
        }
    }
}
