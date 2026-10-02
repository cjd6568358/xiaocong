package com.facebook.react.flat;

import android.text.SpannableStringBuilder;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.imagehelper.ImageSource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class RCTTextInlineImage extends FlatTextShadowNode {
    private InlineImageSpanWithPipeline mInlineImageSpan = new InlineImageSpanWithPipeline();

    RCTTextInlineImage() {
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void setStyleWidth(float width) {
        super.setStyleWidth(width);
        if (this.mInlineImageSpan.getWidth() != width) {
            getMutableSpan().setWidth(width);
            notifyChanged(true);
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void setStyleHeight(float height) {
        super.setStyleHeight(height);
        if (this.mInlineImageSpan.getHeight() != height) {
            getMutableSpan().setHeight(height);
            notifyChanged(true);
        }
    }

    @Override // com.facebook.react.flat.FlatTextShadowNode
    protected void performCollectText(SpannableStringBuilder builder) {
        builder.append("I");
    }

    @Override // com.facebook.react.flat.FlatTextShadowNode
    protected void performApplySpans(SpannableStringBuilder builder, int begin, int end, boolean isEditable) {
        this.mInlineImageSpan.freeze();
        builder.setSpan(this.mInlineImageSpan, begin, end, 17);
    }

    @ReactProp(name = "src")
    public void setSource(ReadableArray sources) {
        String source = (sources == null || sources.size() == 0) ? null : sources.getMap(0).getString("uri");
        ImageSource imageSource = source == null ? null : new ImageSource(getThemedContext(), source);
        getMutableSpan().setImageRequest(imageSource != null ? ImageRequestBuilder.newBuilderWithSource(imageSource.getUri()).build() : null);
    }

    private InlineImageSpanWithPipeline getMutableSpan() {
        if (this.mInlineImageSpan.isFrozen()) {
            this.mInlineImageSpan = this.mInlineImageSpan.mutableCopy();
        }
        return this.mInlineImageSpan;
    }
}
