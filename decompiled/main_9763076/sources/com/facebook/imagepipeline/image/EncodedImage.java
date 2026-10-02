package com.facebook.imagepipeline.image;

import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferInputStream;
import com.facebook.imageutils.BitmapUtil;
import com.facebook.imageutils.JfifUtil;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EncodedImage implements Closeable {
    private int mHeight;
    private ImageFormat mImageFormat;
    private final Supplier<FileInputStream> mInputStreamSupplier;
    private final CloseableReference<PooledByteBuffer> mPooledByteBufferRef;
    private int mRotationAngle;
    private int mSampleSize;
    private int mStreamSize;
    private int mWidth;

    public EncodedImage(CloseableReference<PooledByteBuffer> pooledByteBufferRef) {
        this.mImageFormat = ImageFormat.UNKNOWN;
        this.mRotationAngle = -1;
        this.mWidth = -1;
        this.mHeight = -1;
        this.mSampleSize = 1;
        this.mStreamSize = -1;
        Preconditions.checkArgument(CloseableReference.isValid(pooledByteBufferRef));
        this.mPooledByteBufferRef = pooledByteBufferRef.m60clone();
        this.mInputStreamSupplier = null;
    }

    public EncodedImage(Supplier<FileInputStream> inputStreamSupplier) {
        this.mImageFormat = ImageFormat.UNKNOWN;
        this.mRotationAngle = -1;
        this.mWidth = -1;
        this.mHeight = -1;
        this.mSampleSize = 1;
        this.mStreamSize = -1;
        Preconditions.checkNotNull(inputStreamSupplier);
        this.mPooledByteBufferRef = null;
        this.mInputStreamSupplier = inputStreamSupplier;
    }

    public EncodedImage(Supplier<FileInputStream> inputStreamSupplier, int streamSize) {
        this(inputStreamSupplier);
        this.mStreamSize = streamSize;
    }

    public static EncodedImage cloneOrNull(EncodedImage encodedImage) {
        if (encodedImage != null) {
            return encodedImage.cloneOrNull();
        }
        return null;
    }

    public EncodedImage cloneOrNull() {
        EncodedImage encodedImage;
        if (this.mInputStreamSupplier != null) {
            encodedImage = new EncodedImage(this.mInputStreamSupplier, this.mStreamSize);
        } else {
            CloseableReference<PooledByteBuffer> pooledByteBufferRef = CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
            if (pooledByteBufferRef == null) {
                encodedImage = null;
            } else {
                try {
                    encodedImage = new EncodedImage(pooledByteBufferRef);
                } catch (Throwable th) {
                    CloseableReference.closeSafely(pooledByteBufferRef);
                    throw th;
                }
            }
            CloseableReference.closeSafely(pooledByteBufferRef);
        }
        if (encodedImage != null) {
            encodedImage.copyMetaDataFrom(this);
        }
        return encodedImage;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        CloseableReference.closeSafely(this.mPooledByteBufferRef);
    }

    public synchronized boolean isValid() {
        return CloseableReference.isValid(this.mPooledByteBufferRef) || this.mInputStreamSupplier != null;
    }

    public CloseableReference<PooledByteBuffer> getByteBufferRef() {
        return CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
    }

    public InputStream getInputStream() {
        if (this.mInputStreamSupplier != null) {
            return this.mInputStreamSupplier.get();
        }
        CloseableReference<PooledByteBuffer> pooledByteBufferRef = CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
        if (pooledByteBufferRef != null) {
            try {
                return new PooledByteBufferInputStream(pooledByteBufferRef.get());
            } finally {
                CloseableReference.closeSafely(pooledByteBufferRef);
            }
        }
        return null;
    }

    public void setImageFormat(ImageFormat imageFormat) {
        this.mImageFormat = imageFormat;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setRotationAngle(int rotationAngle) {
        this.mRotationAngle = rotationAngle;
    }

    public void setSampleSize(int sampleSize) {
        this.mSampleSize = sampleSize;
    }

    public ImageFormat getImageFormat() {
        return this.mImageFormat;
    }

    public int getRotationAngle() {
        return this.mRotationAngle;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getSampleSize() {
        return this.mSampleSize;
    }

    public boolean isCompleteAt(int length) {
        if (this.mImageFormat != ImageFormat.JPEG || this.mInputStreamSupplier != null) {
            return true;
        }
        Preconditions.checkNotNull(this.mPooledByteBufferRef);
        PooledByteBuffer buf = this.mPooledByteBufferRef.get();
        return buf.read(length + (-2)) == -1 && buf.read(length + (-1)) == -39;
    }

    public int getSize() {
        return (this.mPooledByteBufferRef == null || this.mPooledByteBufferRef.get() == null) ? this.mStreamSize : this.mPooledByteBufferRef.get().size();
    }

    public void parseMetaData() {
        Pair<Integer, Integer> dimensions;
        ImageFormat imageFormat = ImageFormatChecker.getImageFormat_WrapIOException(getInputStream());
        this.mImageFormat = imageFormat;
        if (!ImageFormat.isWebpFormat(imageFormat) && (dimensions = BitmapUtil.decodeDimensions(getInputStream())) != null) {
            this.mWidth = ((Integer) dimensions.first).intValue();
            this.mHeight = ((Integer) dimensions.second).intValue();
            if (imageFormat == ImageFormat.JPEG) {
                if (this.mRotationAngle == -1) {
                    this.mRotationAngle = JfifUtil.getAutoRotateAngleFromOrientation(JfifUtil.getOrientation(getInputStream()));
                    return;
                }
                return;
            }
            this.mRotationAngle = 0;
        }
    }

    public void copyMetaDataFrom(EncodedImage encodedImage) {
        this.mImageFormat = encodedImage.getImageFormat();
        this.mWidth = encodedImage.getWidth();
        this.mHeight = encodedImage.getHeight();
        this.mRotationAngle = encodedImage.getRotationAngle();
        this.mSampleSize = encodedImage.getSampleSize();
        this.mStreamSize = encodedImage.getSize();
    }

    public static boolean isMetaDataAvailable(EncodedImage encodedImage) {
        return encodedImage.mRotationAngle >= 0 && encodedImage.mWidth >= 0 && encodedImage.mHeight >= 0;
    }

    public static void closeSafely(EncodedImage encodedImage) {
        if (encodedImage != null) {
            encodedImage.close();
        }
    }

    public static boolean isValid(EncodedImage encodedImage) {
        return encodedImage != null && encodedImage.isValid();
    }
}
