package com.bumptech.glide.load.engine.cache;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MemorySizeCalculator {
    private final int bitmapPoolSize;
    private final Context context;
    private final int memoryCacheSize;

    interface ScreenDimensions {
        int getHeightPixels();

        int getWidthPixels();
    }

    public MemorySizeCalculator(Context context) {
        this(context, (ActivityManager) context.getSystemService("activity"), new DisplayMetricsScreenDimensions(context.getResources().getDisplayMetrics()));
    }

    MemorySizeCalculator(Context context, ActivityManager activityManager, ScreenDimensions screenDimensions) {
        this.context = context;
        int maxSize = getMaxSize(activityManager);
        int screenSize = screenDimensions.getWidthPixels() * screenDimensions.getHeightPixels() * 4;
        int targetPoolSize = screenSize * 4;
        int targetMemoryCacheSize = screenSize * 2;
        if (targetMemoryCacheSize + targetPoolSize <= maxSize) {
            this.memoryCacheSize = targetMemoryCacheSize;
            this.bitmapPoolSize = targetPoolSize;
        } else {
            int part = Math.round(maxSize / 6.0f);
            this.memoryCacheSize = part * 2;
            this.bitmapPoolSize = part * 4;
        }
        if (Log.isLoggable("MemorySizeCalculator", 3)) {
            Log.d("MemorySizeCalculator", "Calculated memory cache size: " + toMb(this.memoryCacheSize) + " pool size: " + toMb(this.bitmapPoolSize) + " memory class limited? " + (targetMemoryCacheSize + targetPoolSize > maxSize) + " max size: " + toMb(maxSize) + " memoryClass: " + activityManager.getMemoryClass() + " isLowMemoryDevice: " + isLowMemoryDevice(activityManager));
        }
    }

    public int getMemoryCacheSize() {
        return this.memoryCacheSize;
    }

    public int getBitmapPoolSize() {
        return this.bitmapPoolSize;
    }

    private static int getMaxSize(ActivityManager activityManager) {
        int memoryClassBytes = activityManager.getMemoryClass() * WXMediaMessage.DESCRIPTION_LENGTH_LIMIT * WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
        boolean isLowMemoryDevice = isLowMemoryDevice(activityManager);
        return Math.round((isLowMemoryDevice ? 0.33f : 0.4f) * memoryClassBytes);
    }

    private String toMb(int bytes) {
        return Formatter.formatFileSize(this.context, bytes);
    }

    @TargetApi(19)
    private static boolean isLowMemoryDevice(ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= 19) {
            return activityManager.isLowRamDevice();
        }
        return Build.VERSION.SDK_INT < 11;
    }

    private static class DisplayMetricsScreenDimensions implements ScreenDimensions {
        private final DisplayMetrics displayMetrics;

        public DisplayMetricsScreenDimensions(DisplayMetrics displayMetrics) {
            this.displayMetrics = displayMetrics;
        }

        @Override // com.bumptech.glide.load.engine.cache.MemorySizeCalculator.ScreenDimensions
        public int getWidthPixels() {
            return this.displayMetrics.widthPixels;
        }

        @Override // com.bumptech.glide.load.engine.cache.MemorySizeCalculator.ScreenDimensions
        public int getHeightPixels() {
            return this.displayMetrics.heightPixels;
        }
    }
}
