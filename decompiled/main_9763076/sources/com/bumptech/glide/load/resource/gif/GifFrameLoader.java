package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.NullEncoder;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class GifFrameLoader {
    private final FrameCallback callback;
    private DelayTarget current;
    private final GifDecoder gifDecoder;
    private final Handler handler;
    private boolean isCleared;
    private boolean isLoadPending;
    private boolean isRunning;
    private GenericRequestBuilder<GifDecoder, GifDecoder, Bitmap, Bitmap> requestBuilder;

    public interface FrameCallback {
        void onFrameReady(int i);
    }

    public GifFrameLoader(Context context, FrameCallback callback, GifDecoder gifDecoder, int width, int height) {
        this(callback, gifDecoder, null, getRequestBuilder(context, gifDecoder, width, height, Glide.get(context).getBitmapPool()));
    }

    GifFrameLoader(FrameCallback callback, GifDecoder gifDecoder, Handler handler, GenericRequestBuilder<GifDecoder, GifDecoder, Bitmap, Bitmap> requestBuilder) {
        this.isRunning = false;
        this.isLoadPending = false;
        handler = handler == null ? new Handler(Looper.getMainLooper(), new FrameLoaderCallback()) : handler;
        this.callback = callback;
        this.gifDecoder = gifDecoder;
        this.handler = handler;
        this.requestBuilder = requestBuilder;
    }

    public void setFrameTransformation(Transformation<Bitmap> transformation) {
        if (transformation == null) {
            throw new NullPointerException("Transformation must not be null");
        }
        this.requestBuilder = this.requestBuilder.transform(transformation);
    }

    public void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.isCleared = false;
            loadNextFrame();
        }
    }

    public void stop() {
        this.isRunning = false;
    }

    public void clear() {
        stop();
        if (this.current != null) {
            Glide.clear(this.current);
            this.current = null;
        }
        this.isCleared = true;
    }

    public Bitmap getCurrentFrame() {
        if (this.current != null) {
            return this.current.getResource();
        }
        return null;
    }

    private void loadNextFrame() {
        if (this.isRunning && !this.isLoadPending) {
            this.isLoadPending = true;
            this.gifDecoder.advance();
            long targetTime = SystemClock.uptimeMillis() + ((long) this.gifDecoder.getNextDelay());
            DelayTarget next = new DelayTarget(this.handler, this.gifDecoder.getCurrentFrameIndex(), targetTime);
            this.requestBuilder.signature(new FrameSignature()).into(next);
        }
    }

    void onFrameReady(DelayTarget delayTarget) {
        if (this.isCleared) {
            this.handler.obtainMessage(2, delayTarget).sendToTarget();
            return;
        }
        DelayTarget previous = this.current;
        this.current = delayTarget;
        this.callback.onFrameReady(delayTarget.index);
        if (previous != null) {
            this.handler.obtainMessage(2, previous).sendToTarget();
        }
        this.isLoadPending = false;
        loadNextFrame();
    }

    private class FrameLoaderCallback implements Handler.Callback {
        private FrameLoaderCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                DelayTarget target = (DelayTarget) msg.obj;
                GifFrameLoader.this.onFrameReady(target);
                return true;
            }
            if (msg.what == 2) {
                DelayTarget target2 = (DelayTarget) msg.obj;
                Glide.clear(target2);
            }
            return false;
        }
    }

    static class DelayTarget extends SimpleTarget<Bitmap> {
        private final Handler handler;
        private final int index;
        private Bitmap resource;
        private final long targetTime;

        @Override // com.bumptech.glide.request.target.Target
        public /* bridge */ /* synthetic */ void onResourceReady(Object x0, GlideAnimation x1) {
            onResourceReady((Bitmap) x0, (GlideAnimation<? super Bitmap>) x1);
        }

        public DelayTarget(Handler handler, int index, long targetTime) {
            this.handler = handler;
            this.index = index;
            this.targetTime = targetTime;
        }

        public Bitmap getResource() {
            return this.resource;
        }

        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            this.resource = resource;
            Message msg = this.handler.obtainMessage(1, this);
            this.handler.sendMessageAtTime(msg, this.targetTime);
        }
    }

    private static GenericRequestBuilder<GifDecoder, GifDecoder, Bitmap, Bitmap> getRequestBuilder(Context context, GifDecoder gifDecoder, int width, int height, BitmapPool bitmapPool) {
        GifFrameResourceDecoder frameResourceDecoder = new GifFrameResourceDecoder(bitmapPool);
        GifFrameModelLoader frameLoader = new GifFrameModelLoader();
        Encoder<GifDecoder> sourceEncoder = NullEncoder.get();
        return Glide.with(context).using(frameLoader, GifDecoder.class).load(gifDecoder).as(Bitmap.class).sourceEncoder(sourceEncoder).decoder(frameResourceDecoder).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).override(width, height);
    }

    static class FrameSignature implements Key {
        private final UUID uuid;

        public FrameSignature() {
            this(UUID.randomUUID());
        }

        FrameSignature(UUID uuid) {
            this.uuid = uuid;
        }

        public boolean equals(Object o) {
            if (!(o instanceof FrameSignature)) {
                return false;
            }
            FrameSignature other = (FrameSignature) o;
            return other.uuid.equals(this.uuid);
        }

        public int hashCode() {
            return this.uuid.hashCode();
        }

        @Override // com.bumptech.glide.load.Key
        public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
            throw new UnsupportedOperationException("Not implemented");
        }
    }
}
