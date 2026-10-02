package com.facebook.react.modules.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageEditingManager extends ReactContextBaseJavaModule {
    private static final int COMPRESS_QUALITY = 90;
    protected static final String NAME = "ImageEditingManager";
    private static final String TEMP_FILE_PREFIX = "ReactNative_cropped_image_";
    private static final List<String> LOCAL_URI_PREFIXES = Arrays.asList("file://", "content://");

    @SuppressLint({"InlinedApi"})
    private static final String[] EXIF_ATTRIBUTES = {"FNumber", "DateTime", "DateTimeDigitized", "ExposureTime", "Flash", "FocalLength", "GPSAltitude", "GPSAltitudeRef", "GPSDateStamp", "GPSLatitude", "GPSLatitudeRef", "GPSLongitude", "GPSLongitudeRef", "GPSProcessingMethod", "GPSTimeStamp", "ImageLength", "ImageWidth", "ISOSpeedRatings", "Make", "Model", "Orientation", "SubSecTime", "SubSecTimeDigitized", "SubSecTimeOriginal", "WhiteBalance"};

    public ImageEditingManager(ReactApplicationContext reactContext) {
        super(reactContext);
        new CleanTask(getReactApplicationContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        return Collections.emptyMap();
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        new CleanTask(getReactApplicationContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private static class CleanTask extends GuardedAsyncTask<Void, Void> {
        private final Context mContext;

        private CleanTask(ReactContext context) {
            super(context);
            this.mContext = context;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.react.bridge.GuardedAsyncTask
        public void doInBackgroundGuarded(Void... params) {
            cleanDirectory(this.mContext.getCacheDir());
            File externalCacheDir = this.mContext.getExternalCacheDir();
            if (externalCacheDir != null) {
                cleanDirectory(externalCacheDir);
            }
        }

        private void cleanDirectory(File directory) {
            File[] toDelete = directory.listFiles(new FilenameFilter() { // from class: com.facebook.react.modules.camera.ImageEditingManager.CleanTask.1
                @Override // java.io.FilenameFilter
                public boolean accept(File dir, String filename) {
                    return filename.startsWith(ImageEditingManager.TEMP_FILE_PREFIX);
                }
            });
            if (toDelete != null) {
                for (File file : toDelete) {
                    file.delete();
                }
            }
        }
    }

    @ReactMethod
    public void cropImage(String uri, ReadableMap options, Callback success, Callback error) {
        ReadableMap offset = options.hasKey(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_OFFSET) ? options.getMap(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_OFFSET) : null;
        ReadableMap size = options.hasKey("size") ? options.getMap("size") : null;
        if (offset == null || size == null || !offset.hasKey("x") || !offset.hasKey("y") || !size.hasKey(IMediaFormat.KEY_WIDTH) || !size.hasKey(IMediaFormat.KEY_HEIGHT)) {
            throw new JSApplicationIllegalArgumentException("Please specify offset and size");
        }
        if (uri == null || uri.isEmpty()) {
            throw new JSApplicationIllegalArgumentException("Please specify a URI");
        }
        CropTask cropTask = new CropTask(getReactApplicationContext(), uri, (int) offset.getDouble("x"), (int) offset.getDouble("y"), (int) size.getDouble(IMediaFormat.KEY_WIDTH), (int) size.getDouble(IMediaFormat.KEY_HEIGHT), success, error);
        if (options.hasKey("displaySize")) {
            ReadableMap targetSize = options.getMap("displaySize");
            cropTask.setTargetSize(targetSize.getInt(IMediaFormat.KEY_WIDTH), targetSize.getInt(IMediaFormat.KEY_HEIGHT));
        }
        cropTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private static class CropTask extends GuardedAsyncTask<Void, Void> {
        final Context mContext;
        final Callback mError;
        final int mHeight;
        final Callback mSuccess;
        int mTargetHeight;
        int mTargetWidth;
        final String mUri;
        final int mWidth;
        final int mX;
        final int mY;

        private CropTask(ReactContext context, String uri, int x, int y, int width, int height, Callback success, Callback error) {
            super(context);
            this.mTargetWidth = 0;
            this.mTargetHeight = 0;
            if (x < 0 || y < 0 || width <= 0 || height <= 0) {
                throw new JSApplicationIllegalArgumentException(String.format("Invalid crop rectangle: [%d, %d, %d, %d]", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(width), Integer.valueOf(height)));
            }
            this.mContext = context;
            this.mUri = uri;
            this.mX = x;
            this.mY = y;
            this.mWidth = width;
            this.mHeight = height;
            this.mSuccess = success;
            this.mError = error;
        }

        public void setTargetSize(int width, int height) {
            if (width <= 0 || height <= 0) {
                throw new JSApplicationIllegalArgumentException(String.format("Invalid target size: [%d, %d]", Integer.valueOf(width), Integer.valueOf(height)));
            }
            this.mTargetWidth = width;
            this.mTargetHeight = height;
        }

        private InputStream openBitmapInputStream() throws IOException {
            InputStream stream;
            if (ImageEditingManager.isLocalUri(this.mUri)) {
                stream = this.mContext.getContentResolver().openInputStream(Uri.parse(this.mUri));
            } else {
                URLConnection connection = new URL(this.mUri).openConnection();
                stream = connection.getInputStream();
            }
            if (stream == null) {
                throw new IOException("Cannot open bitmap: " + this.mUri);
            }
            return stream;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.react.bridge.GuardedAsyncTask
        public void doInBackgroundGuarded(Void... params) {
            Bitmap cropped;
            try {
                BitmapFactory.Options outOptions = new BitmapFactory.Options();
                boolean hasTargetSize = this.mTargetWidth > 0 && this.mTargetHeight > 0;
                if (hasTargetSize) {
                    cropped = cropAndResize(this.mTargetWidth, this.mTargetHeight, outOptions);
                } else {
                    cropped = crop(outOptions);
                }
                String mimeType = outOptions.outMimeType;
                if (mimeType != null && !mimeType.isEmpty()) {
                    File tempFile = ImageEditingManager.createTempFile(this.mContext, mimeType);
                    ImageEditingManager.writeCompressedBitmapToFile(cropped, mimeType, tempFile);
                    if (mimeType.equals("image/jpeg")) {
                        ImageEditingManager.copyExif(this.mContext, Uri.parse(this.mUri), tempFile);
                    }
                    this.mSuccess.invoke(Uri.fromFile(tempFile).toString());
                    return;
                }
                throw new IOException("Could not determine MIME type");
            } catch (Exception e) {
                this.mError.invoke(e.getMessage());
            }
        }

        private Bitmap crop(BitmapFactory.Options outOptions) throws IOException {
            InputStream inputStream = openBitmapInputStream();
            try {
                Bitmap fullResolutionBitmap = BitmapFactory.decodeStream(inputStream, null, outOptions);
                if (fullResolutionBitmap == null) {
                    throw new IOException("Cannot decode bitmap: " + this.mUri);
                }
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(fullResolutionBitmap, this.mX, this.mY, this.mWidth, this.mHeight);
                if (inputStream != null) {
                    inputStream.close();
                }
                return bitmapCreateBitmap;
            } catch (Throwable th) {
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th;
            }
        }

        private Bitmap cropAndResize(int targetWidth, int targetHeight, BitmapFactory.Options outOptions) throws IOException {
            float newWidth;
            float newHeight;
            float newX;
            float newY;
            float scale;
            Assertions.assertNotNull(outOptions);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            InputStream inputStream = openBitmapInputStream();
            try {
                BitmapFactory.decodeStream(inputStream, null, options);
                if (inputStream != null) {
                    inputStream.close();
                }
                float cropRectRatio = this.mWidth / this.mHeight;
                float targetRatio = targetWidth / targetHeight;
                if (cropRectRatio > targetRatio) {
                    newWidth = this.mHeight * targetRatio;
                    newHeight = this.mHeight;
                    newX = this.mX + ((this.mWidth - newWidth) / 2.0f);
                    newY = this.mY;
                    scale = targetHeight / this.mHeight;
                } else {
                    newWidth = this.mWidth;
                    newHeight = this.mWidth / targetRatio;
                    newX = this.mX;
                    newY = this.mY + ((this.mHeight - newHeight) / 2.0f);
                    scale = targetWidth / this.mWidth;
                }
                outOptions.inSampleSize = ImageEditingManager.getDecodeSampleSize(this.mWidth, this.mHeight, targetWidth, targetHeight);
                options.inJustDecodeBounds = false;
                InputStream inputStream2 = openBitmapInputStream();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream2, null, outOptions);
                    if (bitmap == null) {
                        throw new IOException("Cannot decode bitmap: " + this.mUri);
                    }
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    int cropX = (int) Math.floor(newX / outOptions.inSampleSize);
                    int cropY = (int) Math.floor(newY / outOptions.inSampleSize);
                    int cropWidth = (int) Math.floor(newWidth / outOptions.inSampleSize);
                    int cropHeight = (int) Math.floor(newHeight / outOptions.inSampleSize);
                    float cropScale = scale * outOptions.inSampleSize;
                    Matrix scaleMatrix = new Matrix();
                    scaleMatrix.setScale(cropScale, cropScale);
                    return Bitmap.createBitmap(bitmap, cropX, cropY, cropWidth, cropHeight, scaleMatrix, true);
                } catch (Throwable th) {
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void copyExif(Context context, Uri oldImage, File newFile) throws IOException {
        File oldFile = getFileFromUri(context, oldImage);
        if (oldFile == null) {
            FLog.w("React", "Couldn't get real path for uri: " + oldImage);
            return;
        }
        ExifInterface oldExif = new ExifInterface(oldFile.getAbsolutePath());
        ExifInterface newExif = new ExifInterface(newFile.getAbsolutePath());
        for (String attribute : EXIF_ATTRIBUTES) {
            String value = oldExif.getAttribute(attribute);
            if (value != null) {
                newExif.setAttribute(attribute, value);
            }
        }
        newExif.saveAttributes();
    }

    private static File getFileFromUri(Context context, Uri uri) {
        Cursor cursor;
        if (uri.getScheme().equals("file")) {
            return new File(uri.getPath());
        }
        if (!uri.getScheme().equals("content") || (cursor = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null)) == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                String path = cursor.getString(0);
                if (!TextUtils.isEmpty(path)) {
                    return new File(path);
                }
            }
            return null;
        } finally {
            cursor.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isLocalUri(String uri) {
        for (String localPrefix : LOCAL_URI_PREFIXES) {
            if (uri.startsWith(localPrefix)) {
                return true;
            }
        }
        return false;
    }

    private static String getFileExtensionForType(String mimeType) {
        if ("image/png".equals(mimeType)) {
            return ".png";
        }
        if ("image/webp".equals(mimeType)) {
            return ".webp";
        }
        return ".jpg";
    }

    private static Bitmap.CompressFormat getCompressFormatForType(String type) {
        if ("image/png".equals(type)) {
            return Bitmap.CompressFormat.PNG;
        }
        if ("image/webp".equals(type)) {
            return Bitmap.CompressFormat.WEBP;
        }
        return Bitmap.CompressFormat.JPEG;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeCompressedBitmapToFile(Bitmap cropped, String mimeType, File tempFile) throws IOException {
        OutputStream out = new FileOutputStream(tempFile);
        try {
            cropped.compress(getCompressFormatForType(mimeType), 90, out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static File createTempFile(Context context, String mimeType) throws IOException {
        File cacheDir;
        File externalCacheDir = context.getExternalCacheDir();
        File internalCacheDir = context.getCacheDir();
        if (externalCacheDir == null && internalCacheDir == null) {
            throw new IOException("No cache directory available");
        }
        if (externalCacheDir == null) {
            cacheDir = internalCacheDir;
        } else {
            cacheDir = (internalCacheDir != null && externalCacheDir.getFreeSpace() <= internalCacheDir.getFreeSpace()) ? internalCacheDir : externalCacheDir;
        }
        return File.createTempFile(TEMP_FILE_PREFIX, getFileExtensionForType(mimeType), cacheDir);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDecodeSampleSize(int width, int height, int targetWidth, int targetHeight) {
        int inSampleSize = 1;
        if (height > targetWidth || width > targetHeight) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfWidth / inSampleSize >= targetWidth && halfHeight / inSampleSize >= targetHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
