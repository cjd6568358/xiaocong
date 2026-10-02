package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Pair;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferInputStream;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imageutils.BitmapUtil;
import com.facebook.imageutils.JfifUtil;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LocalExifThumbnailProducer implements ThumbnailProducer<EncodedImage> {
    private final ContentResolver mContentResolver;
    private final Executor mExecutor;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public LocalExifThumbnailProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, ContentResolver contentResolver) {
        this.mExecutor = executor;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mContentResolver = contentResolver;
    }

    @Override // com.facebook.imagepipeline.producers.ThumbnailProducer
    public boolean canProvideImageForSize(ResizeOptions resizeOptions) {
        return ThumbnailSizeChecker.isImageBigEnough(WXMediaMessage.TITLE_LENGTH_LIMIT, WXMediaMessage.TITLE_LENGTH_LIMIT, resizeOptions);
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        ProducerListener listener = producerContext.getListener();
        String requestId = producerContext.getId();
        final ImageRequest imageRequest = producerContext.getImageRequest();
        final StatefulProducerRunnable<EncodedImage> statefulProducerRunnable = new StatefulProducerRunnable<EncodedImage>(consumer, listener, "LocalExifThumbnailProducer", requestId) { // from class: com.facebook.imagepipeline.producers.LocalExifThumbnailProducer.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.common.executors.StatefulRunnable
            public EncodedImage getResult() throws Exception {
                Uri sourceUri = imageRequest.getSourceUri();
                ExifInterface exifInterface = LocalExifThumbnailProducer.this.getExifInterface(sourceUri);
                if (exifInterface == null || !exifInterface.hasThumbnail()) {
                    return null;
                }
                byte[] bytes = exifInterface.getThumbnail();
                PooledByteBuffer pooledByteBuffer = LocalExifThumbnailProducer.this.mPooledByteBufferFactory.newByteBuffer(bytes);
                return LocalExifThumbnailProducer.this.buildEncodedImage(pooledByteBuffer, exifInterface);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.imagepipeline.producers.StatefulProducerRunnable, com.facebook.common.executors.StatefulRunnable
            public void disposeResult(EncodedImage result) {
                EncodedImage.closeSafely(result);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.imagepipeline.producers.StatefulProducerRunnable
            public Map<String, String> getExtraMapOnSuccess(EncodedImage result) {
                return ImmutableMap.of("createdThumbnail", Boolean.toString(result != null));
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.producers.LocalExifThumbnailProducer.2
            @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
            public void onCancellationRequested() {
                statefulProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(statefulProducerRunnable);
    }

    ExifInterface getExifInterface(Uri uri) throws IOException {
        String realPath = getRealPathFromUri(uri);
        if (canReadAsFile(realPath)) {
            return new ExifInterface(realPath);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EncodedImage buildEncodedImage(PooledByteBuffer imageBytes, ExifInterface exifInterface) {
        Pair<Integer, Integer> dimensions = BitmapUtil.decodeDimensions(new PooledByteBufferInputStream(imageBytes));
        int rotationAngle = getRotationAngle(exifInterface);
        int width = dimensions != null ? ((Integer) dimensions.first).intValue() : -1;
        int height = dimensions != null ? ((Integer) dimensions.second).intValue() : -1;
        CloseableReference<PooledByteBuffer> closeableByteBuffer = CloseableReference.of(imageBytes);
        try {
            EncodedImage encodedImage = new EncodedImage(closeableByteBuffer);
            CloseableReference.closeSafely(closeableByteBuffer);
            encodedImage.setImageFormat(ImageFormat.JPEG);
            encodedImage.setRotationAngle(rotationAngle);
            encodedImage.setWidth(width);
            encodedImage.setHeight(height);
            return encodedImage;
        } catch (Throwable th) {
            CloseableReference.closeSafely(closeableByteBuffer);
            throw th;
        }
    }

    private int getRotationAngle(ExifInterface exifInterface) {
        return JfifUtil.getAutoRotateAngleFromOrientation(Integer.parseInt(exifInterface.getAttribute("Orientation")));
    }

    private String getRealPathFromUri(Uri srcUri) {
        String result = null;
        if (UriUtil.isLocalContentUri(srcUri)) {
            Cursor cursor = null;
            try {
                cursor = this.mContentResolver.query(srcUri, null, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int idx = cursor.getColumnIndex("_data");
                    result = cursor.getString(idx);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (!UriUtil.isLocalFileUri(srcUri)) {
            return null;
        }
        String result2 = srcUri.getPath();
        return result2;
    }

    boolean canReadAsFile(String realPath) throws IOException {
        if (realPath == null) {
            return false;
        }
        File file = new File(realPath);
        return file.exists() && file.canRead();
    }
}
