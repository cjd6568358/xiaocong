package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import com.facebook.common.logging.FLog;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imageutils.JfifUtil;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LocalContentUriThumbnailFetchProducer extends LocalFetchProducer implements ThumbnailProducer<EncodedImage> {
    private final ContentResolver mContentResolver;
    private static final Class<?> TAG = LocalContentUriThumbnailFetchProducer.class;
    private static final String[] PROJECTION = {"_id", "_data"};
    private static final String[] THUMBNAIL_PROJECTION = {"_data"};
    private static final Rect MINI_THUMBNAIL_DIMENSIONS = new Rect(0, 0, WXMediaMessage.TITLE_LENGTH_LIMIT, 384);
    private static final Rect MICRO_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 96, 96);

    public LocalContentUriThumbnailFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, ContentResolver contentResolver, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
        this.mContentResolver = contentResolver;
    }

    @Override // com.facebook.imagepipeline.producers.ThumbnailProducer
    public boolean canProvideImageForSize(ResizeOptions resizeOptions) {
        return ThumbnailSizeChecker.isImageBigEnough(MINI_THUMBNAIL_DIMENSIONS.width(), MINI_THUMBNAIL_DIMENSIONS.height(), resizeOptions);
    }

    @Override // com.facebook.imagepipeline.producers.LocalFetchProducer
    protected EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException {
        EncodedImage cameraImage;
        Uri uri = imageRequest.getSourceUri();
        if (!UriUtil.isLocalCameraUri(uri) || (cameraImage = getCameraImage(uri, imageRequest.getResizeOptions())) == null) {
            return null;
        }
        return cameraImage;
    }

    private EncodedImage getCameraImage(Uri uri, ResizeOptions resizeOptions) throws IOException {
        Cursor cursor = this.mContentResolver.query(uri, PROJECTION, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            String pathname = cursor.getString(cursor.getColumnIndex("_data"));
            if (resizeOptions != null) {
                int imageIdColumnIndex = cursor.getColumnIndex("_id");
                EncodedImage thumbnail = getThumbnail(resizeOptions, cursor.getInt(imageIdColumnIndex));
                if (thumbnail != null) {
                    thumbnail.setRotationAngle(getRotationAngle(pathname));
                    return thumbnail;
                }
            }
            return null;
        } finally {
            cursor.close();
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x004b A[DONT_GENERATE, DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:18:0x004d A[DONT_GENERATE] */
    private EncodedImage getThumbnail(ResizeOptions resizeOptions, int imageId) throws IOException {
        EncodedImage encodedImage = null;
        int thumbnailKind = getThumbnailKind(resizeOptions);
        if (thumbnailKind != 0) {
            Cursor thumbnailCursor = null;
            try {
                thumbnailCursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(this.mContentResolver, imageId, thumbnailKind, THUMBNAIL_PROJECTION);
                if (thumbnailCursor != null) {
                    thumbnailCursor.moveToFirst();
                    if (thumbnailCursor.getCount() > 0) {
                        String thumbnailUri = thumbnailCursor.getString(thumbnailCursor.getColumnIndex("_data"));
                        if (new File(thumbnailUri).exists()) {
                            encodedImage = getEncodedImage(new FileInputStream(thumbnailUri), getLength(thumbnailUri));
                        }
                    }
                }
            } finally {
                if (thumbnailCursor != null) {
                    thumbnailCursor.close();
                }
            }
        }
        return encodedImage;
    }

    private static int getThumbnailKind(ResizeOptions resizeOptions) {
        if (ThumbnailSizeChecker.isImageBigEnough(MICRO_THUMBNAIL_DIMENSIONS.width(), MICRO_THUMBNAIL_DIMENSIONS.height(), resizeOptions)) {
            return 3;
        }
        if (ThumbnailSizeChecker.isImageBigEnough(MINI_THUMBNAIL_DIMENSIONS.width(), MINI_THUMBNAIL_DIMENSIONS.height(), resizeOptions)) {
            return 1;
        }
        return 0;
    }

    private static int getLength(String pathname) {
        if (pathname == null) {
            return -1;
        }
        return (int) new File(pathname).length();
    }

    @Override // com.facebook.imagepipeline.producers.LocalFetchProducer
    protected String getProducerName() {
        return "LocalContentUriThumbnailFetchProducer";
    }

    private static int getRotationAngle(String pathname) {
        if (pathname == null) {
            return 0;
        }
        try {
            ExifInterface exif = new ExifInterface(pathname);
            return JfifUtil.getAutoRotateAngleFromOrientation(exif.getAttributeInt("Orientation", 1));
        } catch (IOException ioe) {
            FLog.e(TAG, ioe, "Unable to retrieve thumbnail rotation for %s", pathname);
            return 0;
        }
    }
}
