package com.facebook.react.views.text.frescosupport;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.TextView;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.views.text.TextInlineImageSpan;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FrescoBasedReactTextInlineImageSpan extends TextInlineImageSpan {
    private final Object mCallerContext;
    private Drawable mDrawable;
    private final AbstractDraweeControllerBuilder mDraweeControllerBuilder;
    private final DraweeHolder<GenericDraweeHierarchy> mDraweeHolder;
    private int mHeight;
    private TextView mTextView;
    private Uri mUri;
    private int mWidth;

    public FrescoBasedReactTextInlineImageSpan(Resources resources, int height, int width, Uri uri, AbstractDraweeControllerBuilder draweeControllerBuilder, Object callerContext) {
        this.mDraweeHolder = new DraweeHolder<>(GenericDraweeHierarchyBuilder.newInstance(resources).build());
        this.mDraweeControllerBuilder = draweeControllerBuilder;
        this.mCallerContext = callerContext;
        this.mHeight = height;
        this.mWidth = width;
        this.mUri = uri == null ? Uri.EMPTY : uri;
    }

    @Override // com.facebook.react.views.text.TextInlineImageSpan
    public void onDetachedFromWindow() {
        this.mDraweeHolder.onDetach();
    }

    @Override // com.facebook.react.views.text.TextInlineImageSpan
    public void onStartTemporaryDetach() {
        this.mDraweeHolder.onDetach();
    }

    @Override // com.facebook.react.views.text.TextInlineImageSpan
    public void onAttachedToWindow() {
        this.mDraweeHolder.onAttach();
    }

    @Override // com.facebook.react.views.text.TextInlineImageSpan
    public void onFinishTemporaryDetach() {
        this.mDraweeHolder.onAttach();
    }

    @Override // com.facebook.react.views.text.TextInlineImageSpan
    public Drawable getDrawable() {
        return this.mDrawable;
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if (fm != null) {
            fm.ascent = -this.mHeight;
            fm.descent = 0;
            fm.top = fm.ascent;
            fm.bottom = 0;
        }
        return this.mWidth;
    }

    @Override // com.facebook.react.views.text.TextInlineImageSpan
    public void setTextView(TextView textView) {
        this.mTextView = textView;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        if (this.mDrawable == null) {
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(this.mUri).build();
            DraweeController draweeController = this.mDraweeControllerBuilder.reset().setOldController(this.mDraweeHolder.getController()).setCallerContext(this.mCallerContext).setImageRequest(imageRequest).build();
            this.mDraweeHolder.setController(draweeController);
            this.mDrawable = this.mDraweeHolder.getTopLevelDrawable();
            this.mDrawable.setBounds(0, 0, this.mWidth, this.mHeight);
            this.mDrawable.setCallback(this.mTextView);
        }
        canvas.save();
        int transY = y - this.mDrawable.getBounds().bottom;
        canvas.translate(x, transY);
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    @Override // com.facebook.react.views.text.TextInlineImageSpan
    public int getHeight() {
        return this.mHeight;
    }
}
