package com.journeyapps.barcodescanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import com.google.zxing.PlanarYUVLuminanceSource;
import java.io.ByteArrayOutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SourceData {
    private Rect cropRect;
    private byte[] data;
    private int dataHeight;
    private int dataWidth;
    private int imageFormat;
    private int rotation;

    public SourceData(byte[] data, int dataWidth, int dataHeight, int imageFormat, int rotation) {
        this.data = data;
        this.dataWidth = dataWidth;
        this.dataHeight = dataHeight;
        this.rotation = rotation;
        this.imageFormat = imageFormat;
    }

    public void setCropRect(Rect cropRect) {
        this.cropRect = cropRect;
    }

    public boolean isRotated() {
        return this.rotation % 180 != 0;
    }

    public PlanarYUVLuminanceSource createSource() {
        byte[] rotated = rotateCameraPreview(this.rotation, this.data, this.dataWidth, this.dataHeight);
        return isRotated() ? new PlanarYUVLuminanceSource(rotated, this.dataHeight, this.dataWidth, this.cropRect.left, this.cropRect.top, this.cropRect.width(), this.cropRect.height(), false) : new PlanarYUVLuminanceSource(rotated, this.dataWidth, this.dataHeight, this.cropRect.left, this.cropRect.top, this.cropRect.width(), this.cropRect.height(), false);
    }

    public Bitmap getBitmap(int scaleFactor) {
        return getBitmap(this.cropRect, scaleFactor);
    }

    private Bitmap getBitmap(Rect cropRect, int scaleFactor) {
        if (isRotated()) {
            cropRect = new Rect(cropRect.top, cropRect.left, cropRect.bottom, cropRect.right);
        }
        YuvImage img = new YuvImage(this.data, this.imageFormat, this.dataWidth, this.dataHeight, null);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        img.compressToJpeg(cropRect, 90, buffer);
        byte[] jpegData = buffer.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length, options);
        if (this.rotation != 0) {
            Matrix imageMatrix = new Matrix();
            imageMatrix.postRotate(this.rotation);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), imageMatrix, false);
        }
        return bitmap;
    }

    public static byte[] rotateCameraPreview(int cameraRotation, byte[] data, int imageWidth, int imageHeight) {
        switch (cameraRotation) {
            case 0:
            default:
                return data;
            case 90:
                return rotateCW(data, imageWidth, imageHeight);
            case 180:
                return rotate180(data, imageWidth, imageHeight);
            case 270:
                return rotateCCW(data, imageWidth, imageHeight);
        }
    }

    public static byte[] rotateCW(byte[] data, int imageWidth, int imageHeight) {
        byte[] yuv = new byte[imageWidth * imageHeight];
        int i = 0;
        for (int x = 0; x < imageWidth; x++) {
            for (int y = imageHeight - 1; y >= 0; y--) {
                yuv[i] = data[(y * imageWidth) + x];
                i++;
            }
        }
        return yuv;
    }

    public static byte[] rotate180(byte[] data, int imageWidth, int imageHeight) {
        int n = imageWidth * imageHeight;
        byte[] yuv = new byte[n];
        int i = n - 1;
        for (int j = 0; j < n; j++) {
            yuv[i] = data[j];
            i--;
        }
        return yuv;
    }

    public static byte[] rotateCCW(byte[] data, int imageWidth, int imageHeight) {
        int n = imageWidth * imageHeight;
        byte[] yuv = new byte[n];
        int i = n - 1;
        for (int x = 0; x < imageWidth; x++) {
            for (int y = imageHeight - 1; y >= 0; y--) {
                yuv[i] = data[(y * imageWidth) + x];
                i--;
            }
        }
        return yuv;
    }
}
