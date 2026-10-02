package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.Log;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TransformationUtils {
    public static Bitmap centerCrop(Bitmap recycled, Bitmap toCrop, int width, int height) {
        float scale;
        Bitmap result;
        if (toCrop == null) {
            return null;
        }
        if (toCrop.getWidth() != width || toCrop.getHeight() != height) {
            float dx = 0.0f;
            float dy = 0.0f;
            Matrix m = new Matrix();
            if (toCrop.getWidth() * height > toCrop.getHeight() * width) {
                scale = height / toCrop.getHeight();
                dx = (width - (toCrop.getWidth() * scale)) * 0.5f;
            } else {
                scale = width / toCrop.getWidth();
                dy = (height - (toCrop.getHeight() * scale)) * 0.5f;
            }
            m.setScale(scale, scale);
            m.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
            if (recycled != null) {
                result = recycled;
            } else {
                result = Bitmap.createBitmap(width, height, getSafeConfig(toCrop));
            }
            setAlpha(toCrop, result);
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint(6);
            canvas.drawBitmap(toCrop, m, paint);
            return result;
        }
        return toCrop;
    }

    public static Bitmap fitCenter(Bitmap toFit, BitmapPool pool, int width, int height) {
        if (toFit.getWidth() == width && toFit.getHeight() == height) {
            if (Log.isLoggable("TransformationUtils", 2)) {
                Log.v("TransformationUtils", "requested target size matches input, returning input");
                return toFit;
            }
            return toFit;
        }
        float widthPercentage = width / toFit.getWidth();
        float heightPercentage = height / toFit.getHeight();
        float minPercentage = Math.min(widthPercentage, heightPercentage);
        int targetWidth = (int) (toFit.getWidth() * minPercentage);
        int targetHeight = (int) (toFit.getHeight() * minPercentage);
        if (toFit.getWidth() == targetWidth && toFit.getHeight() == targetHeight) {
            if (Log.isLoggable("TransformationUtils", 2)) {
                Log.v("TransformationUtils", "adjusted target size matches input, returning input");
                return toFit;
            }
            return toFit;
        }
        Bitmap.Config config = getSafeConfig(toFit);
        Bitmap toReuse = pool.get(targetWidth, targetHeight, config);
        if (toReuse == null) {
            toReuse = Bitmap.createBitmap(targetWidth, targetHeight, config);
        }
        setAlpha(toFit, toReuse);
        if (Log.isLoggable("TransformationUtils", 2)) {
            Log.v("TransformationUtils", "request: " + width + "x" + height);
            Log.v("TransformationUtils", "toFit:   " + toFit.getWidth() + "x" + toFit.getHeight());
            Log.v("TransformationUtils", "toReuse: " + toReuse.getWidth() + "x" + toReuse.getHeight());
            Log.v("TransformationUtils", "minPct:   " + minPercentage);
        }
        Canvas canvas = new Canvas(toReuse);
        Matrix matrix = new Matrix();
        matrix.setScale(minPercentage, minPercentage);
        Paint paint = new Paint(6);
        canvas.drawBitmap(toFit, matrix, paint);
        return toReuse;
    }

    @TargetApi(12)
    public static void setAlpha(Bitmap toTransform, Bitmap outBitmap) {
        if (Build.VERSION.SDK_INT >= 12 && outBitmap != null) {
            outBitmap.setHasAlpha(toTransform.hasAlpha());
        }
    }

    public static int getExifOrientationDegrees(int exifOrientation) {
        switch (exifOrientation) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 6:
                return 90;
            case 7:
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    public static Bitmap rotateImageExif(Bitmap toOrient, BitmapPool pool, int exifOrientation) {
        Matrix matrix = new Matrix();
        initializeMatrixForRotation(exifOrientation, matrix);
        if (!matrix.isIdentity()) {
            RectF newRect = new RectF(0.0f, 0.0f, toOrient.getWidth(), toOrient.getHeight());
            matrix.mapRect(newRect);
            int newWidth = Math.round(newRect.width());
            int newHeight = Math.round(newRect.height());
            Bitmap.Config config = getSafeConfig(toOrient);
            Bitmap result = pool.get(newWidth, newHeight, config);
            if (result == null) {
                result = Bitmap.createBitmap(newWidth, newHeight, config);
            }
            matrix.postTranslate(-newRect.left, -newRect.top);
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint(6);
            canvas.drawBitmap(toOrient, matrix, paint);
            return result;
        }
        return toOrient;
    }

    private static Bitmap.Config getSafeConfig(Bitmap bitmap) {
        return bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }

    static void initializeMatrixForRotation(int exifOrientation, Matrix matrix) {
        switch (exifOrientation) {
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                break;
            case 3:
                matrix.setRotate(180.0f);
                break;
            case 4:
                matrix.setRotate(180.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 5:
                matrix.setRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 6:
                matrix.setRotate(90.0f);
                break;
            case 7:
                matrix.setRotate(-90.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 8:
                matrix.setRotate(-90.0f);
                break;
        }
    }
}
