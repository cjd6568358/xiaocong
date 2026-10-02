package com.facebook.react.views.text;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.style.ReplacementSpan;
import android.widget.TextView;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class TextInlineImageSpan extends ReplacementSpan {
    public abstract Drawable getDrawable();

    public abstract int getHeight();

    public abstract void onAttachedToWindow();

    public abstract void onDetachedFromWindow();

    public abstract void onFinishTemporaryDetach();

    public abstract void onStartTemporaryDetach();

    public abstract void setTextView(TextView textView);

    public static void possiblyUpdateInlineImageSpans(Spannable spannable, TextView view) {
        TextInlineImageSpan[] spans = (TextInlineImageSpan[]) spannable.getSpans(0, spannable.length(), TextInlineImageSpan.class);
        for (TextInlineImageSpan span : spans) {
            span.onAttachedToWindow();
            span.setTextView(view);
        }
    }
}
