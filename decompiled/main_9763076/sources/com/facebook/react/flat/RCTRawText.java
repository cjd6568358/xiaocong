package com.facebook.react.flat;

import android.text.SpannableStringBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class RCTRawText extends FlatTextShadowNode {
    private String mText;

    RCTRawText() {
    }

    @Override // com.facebook.react.flat.FlatTextShadowNode
    protected void performCollectText(SpannableStringBuilder builder) {
        if (this.mText != null) {
            builder.append((CharSequence) this.mText);
        }
    }

    @Override // com.facebook.react.flat.FlatTextShadowNode
    protected void performApplySpans(SpannableStringBuilder builder, int begin, int end, boolean isEditable) {
        builder.setSpan(this, begin, end, 17);
    }

    @ReactProp(name = "text")
    public void setText(String text) {
        this.mText = text;
        notifyChanged(true);
    }
}
