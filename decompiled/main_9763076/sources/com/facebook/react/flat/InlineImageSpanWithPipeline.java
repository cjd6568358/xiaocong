package com.facebook.react.flat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.infer.annotation.Assertions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class InlineImageSpanWithPipeline extends ReplacementSpan implements AttachDetachListener, BitmapUpdateListener {
    private static final RectF TMP_RECT = new RectF();
    private FlatViewGroup.InvalidateCallback mCallback;
    private boolean mFrozen;
    private float mHeight;
    private PipelineRequestHelper mRequestHelper;
    private float mWidth;

    InlineImageSpanWithPipeline() {
        this(null, Float.NaN, Float.NaN);
    }

    private InlineImageSpanWithPipeline(PipelineRequestHelper requestHelper, float width, float height) {
        this.mRequestHelper = requestHelper;
        this.mWidth = width;
        this.mHeight = height;
    }

    InlineImageSpanWithPipeline mutableCopy() {
        return new InlineImageSpanWithPipeline(this.mRequestHelper, this.mWidth, this.mHeight);
    }

    void setImageRequest(ImageRequest imageRequest) {
        if (imageRequest == null) {
            this.mRequestHelper = null;
        } else {
            this.mRequestHelper = new PipelineRequestHelper(imageRequest);
        }
    }

    float getWidth() {
        return this.mWidth;
    }

    void setWidth(float width) {
        this.mWidth = width;
    }

    float getHeight() {
        return this.mHeight;
    }

    void setHeight(float height) {
        this.mHeight = height;
    }

    void freeze() {
        this.mFrozen = true;
    }

    boolean isFrozen() {
        return this.mFrozen;
    }

    @Override // com.facebook.react.flat.BitmapUpdateListener
    public void onSecondaryAttach(Bitmap bitmap) {
        ((FlatViewGroup.InvalidateCallback) Assertions.assumeNotNull(this.mCallback)).invalidate();
    }

    @Override // com.facebook.react.flat.BitmapUpdateListener
    public void onBitmapReady(Bitmap bitmap) {
        ((FlatViewGroup.InvalidateCallback) Assertions.assumeNotNull(this.mCallback)).invalidate();
    }

    @Override // com.facebook.react.flat.BitmapUpdateListener
    public void onImageLoadEvent(int imageLoadEvent) {
    }

    @Override // com.facebook.react.flat.AttachDetachListener
    public void onAttached(FlatViewGroup.InvalidateCallback callback) {
        this.mCallback = callback;
        if (this.mRequestHelper != null) {
            this.mRequestHelper.attach(this);
        }
    }

    @Override // com.facebook.react.flat.AttachDetachListener
    public void onDetached() {
        if (this.mRequestHelper != null) {
            this.mRequestHelper.detach();
            if (this.mRequestHelper.isDetached()) {
                this.mCallback = null;
            }
        }
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if (fm != null) {
            fm.ascent = -Math.round(this.mHeight);
            fm.descent = 0;
            fm.top = fm.ascent;
            fm.bottom = 0;
        }
        return Math.round(this.mWidth);
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Bitmap bitmap;
        if (this.mRequestHelper != null && (bitmap = this.mRequestHelper.getBitmap()) != null) {
            float bottomFloat = bottom - paint.getFontMetricsInt().descent;
            TMP_RECT.set(x, bottomFloat - this.mHeight, this.mWidth + x, bottomFloat);
            canvas.drawBitmap(bitmap, (Rect) null, TMP_RECT, paint);
        }
    }
}
