package com.bumptech.glide.load.resource.gif;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import bsh.ParserConstants;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeader;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GifDrawable extends GlideDrawable implements GifFrameLoader.FrameCallback {
    private boolean applyGravity;
    private final GifDecoder decoder;
    private final Rect destRect;
    private final GifFrameLoader frameLoader;
    private boolean isRecycled;
    private boolean isRunning;
    private boolean isStarted;
    private boolean isVisible;
    private int loopCount;
    private int maxLoopCount;
    private final Paint paint;
    private final GifState state;

    public GifDrawable(Context context, GifDecoder.BitmapProvider bitmapProvider, BitmapPool bitmapPool, Transformation<Bitmap> frameTransformation, int targetFrameWidth, int targetFrameHeight, GifHeader gifHeader, byte[] data, Bitmap firstFrame) {
        this(new GifState(gifHeader, data, context, frameTransformation, targetFrameWidth, targetFrameHeight, bitmapProvider, bitmapPool, firstFrame));
    }

    public GifDrawable(GifDrawable other, Bitmap firstFrame, Transformation<Bitmap> frameTransformation) {
        this(new GifState(other.state.gifHeader, other.state.data, other.state.context, frameTransformation, other.state.targetWidth, other.state.targetHeight, other.state.bitmapProvider, other.state.bitmapPool, firstFrame));
    }

    GifDrawable(GifState state) {
        this.destRect = new Rect();
        this.isVisible = true;
        this.maxLoopCount = -1;
        if (state == null) {
            throw new NullPointerException("GifState must not be null");
        }
        this.state = state;
        this.decoder = new GifDecoder(state.bitmapProvider);
        this.paint = new Paint();
        this.decoder.setData(state.gifHeader, state.data);
        this.frameLoader = new GifFrameLoader(state.context, this, this.decoder, state.targetWidth, state.targetHeight);
        this.frameLoader.setFrameTransformation(state.frameTransformation);
    }

    public Bitmap getFirstFrame() {
        return this.state.firstFrame;
    }

    public Transformation<Bitmap> getFrameTransformation() {
        return this.state.frameTransformation;
    }

    public byte[] getData() {
        return this.state.data;
    }

    public int getFrameCount() {
        return this.decoder.getFrameCount();
    }

    private void resetLoopCount() {
        this.loopCount = 0;
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.isStarted = true;
        resetLoopCount();
        if (this.isVisible) {
            startRunning();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.isStarted = false;
        stopRunning();
        if (Build.VERSION.SDK_INT < 11) {
            reset();
        }
    }

    private void reset() {
        this.frameLoader.clear();
        invalidateSelf();
    }

    private void startRunning() {
        if (this.decoder.getFrameCount() == 1) {
            invalidateSelf();
        } else if (!this.isRunning) {
            this.isRunning = true;
            this.frameLoader.start();
            invalidateSelf();
        }
    }

    private void stopRunning() {
        this.isRunning = false;
        this.frameLoader.stop();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean visible, boolean restart) {
        this.isVisible = visible;
        if (!visible) {
            stopRunning();
        } else if (this.isStarted) {
            startRunning();
        }
        return super.setVisible(visible, restart);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.state.firstFrame.getWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.state.firstFrame.getHeight();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.isRunning;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.applyGravity = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (!this.isRecycled) {
            if (this.applyGravity) {
                Gravity.apply(ParserConstants.MINUSASSIGN, getIntrinsicWidth(), getIntrinsicHeight(), getBounds(), this.destRect);
                this.applyGravity = false;
            }
            Bitmap currentFrame = this.frameLoader.getCurrentFrame();
            Bitmap toDraw = currentFrame != null ? currentFrame : this.state.firstFrame;
            canvas.drawBitmap(toDraw, (Rect) null, this.destRect, this.paint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.paint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // com.bumptech.glide.load.resource.gif.GifFrameLoader.FrameCallback
    @TargetApi(11)
    public void onFrameReady(int frameIndex) {
        if (Build.VERSION.SDK_INT >= 11 && getCallback() == null) {
            stop();
            reset();
            return;
        }
        invalidateSelf();
        if (frameIndex == this.decoder.getFrameCount() - 1) {
            this.loopCount++;
        }
        if (this.maxLoopCount != -1 && this.loopCount >= this.maxLoopCount) {
            stop();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.state;
    }

    public void recycle() {
        this.isRecycled = true;
        this.state.bitmapPool.put(this.state.firstFrame);
        this.frameLoader.clear();
        this.frameLoader.stop();
    }

    @Override // com.bumptech.glide.load.resource.drawable.GlideDrawable
    public boolean isAnimated() {
        return true;
    }

    @Override // com.bumptech.glide.load.resource.drawable.GlideDrawable
    public void setLoopCount(int loopCount) {
        if (loopCount <= 0 && loopCount != -1 && loopCount != 0) {
            throw new IllegalArgumentException("Loop count must be greater than 0, or equal to GlideDrawable.LOOP_FOREVER, or equal to GlideDrawable.LOOP_INTRINSIC");
        }
        if (loopCount == 0) {
            this.maxLoopCount = this.decoder.getLoopCount();
        } else {
            this.maxLoopCount = loopCount;
        }
    }

    static class GifState extends Drawable.ConstantState {
        BitmapPool bitmapPool;
        GifDecoder.BitmapProvider bitmapProvider;
        Context context;
        byte[] data;
        Bitmap firstFrame;
        Transformation<Bitmap> frameTransformation;
        GifHeader gifHeader;
        int targetHeight;
        int targetWidth;

        public GifState(GifHeader header, byte[] data, Context context, Transformation<Bitmap> frameTransformation, int targetWidth, int targetHeight, GifDecoder.BitmapProvider provider, BitmapPool bitmapPool, Bitmap firstFrame) {
            if (firstFrame == null) {
                throw new NullPointerException("The first frame of the GIF must not be null");
            }
            this.gifHeader = header;
            this.data = data;
            this.bitmapPool = bitmapPool;
            this.firstFrame = firstFrame;
            this.context = context.getApplicationContext();
            this.frameTransformation = frameTransformation;
            this.targetWidth = targetWidth;
            this.targetHeight = targetHeight;
            this.bitmapProvider = provider;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources res) {
            return newDrawable();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new GifDrawable(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }
    }
}
