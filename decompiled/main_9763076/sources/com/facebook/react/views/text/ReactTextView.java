package com.facebook.react.views.text;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.react.uimanager.ReactCompoundView;
import com.facebook.react.views.view.ReactViewBackgroundDrawable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactTextView extends TextView implements ReactCompoundView {
    private static final ViewGroup.LayoutParams EMPTY_LAYOUT_PARAMS = new ViewGroup.LayoutParams(0, 0);
    private boolean mContainsImages;
    private int mDefaultGravityHorizontal;
    private int mDefaultGravityVertical;
    private TextUtils.TruncateAt mEllipsizeLocation;
    private float mLineHeight;
    private int mNumberOfLines;
    private ReactViewBackgroundDrawable mReactBackgroundDrawable;
    private int mTextAlign;
    private boolean mTextIsSelectable;

    public ReactTextView(Context context) {
        super(context);
        this.mLineHeight = Float.NaN;
        this.mTextAlign = 0;
        this.mNumberOfLines = Integer.MAX_VALUE;
        this.mEllipsizeLocation = TextUtils.TruncateAt.END;
        this.mDefaultGravityHorizontal = getGravity() & 8388615;
        this.mDefaultGravityVertical = getGravity() & 112;
    }

    public void setText(ReactTextUpdate update) {
        this.mContainsImages = update.containsImages();
        if (getLayoutParams() == null) {
            setLayoutParams(EMPTY_LAYOUT_PARAMS);
        }
        setText(update.getText());
        setPadding((int) Math.floor(update.getPaddingLeft()), (int) Math.floor(update.getPaddingTop()), (int) Math.floor(update.getPaddingRight()), (int) Math.floor(update.getPaddingBottom()));
        int nextTextAlign = update.getTextAlign();
        if (this.mTextAlign != nextTextAlign) {
            this.mTextAlign = nextTextAlign;
        }
        setGravityHorizontal(this.mTextAlign);
        if (Build.VERSION.SDK_INT >= 23 && getBreakStrategy() != update.getTextBreakStrategy()) {
            setBreakStrategy(update.getTextBreakStrategy());
        }
    }

    @Override // com.facebook.react.uimanager.ReactCompoundView
    public int reactTagForTouch(float touchX, float touchY) {
        Spanned text = (Spanned) getText();
        int target = getId();
        int x = (int) touchX;
        int y = (int) touchY;
        Layout layout = getLayout();
        if (layout == null) {
            return target;
        }
        int line = layout.getLineForVertical(y);
        int lineStartX = (int) layout.getLineLeft(line);
        int lineEndX = (int) layout.getLineRight(line);
        if (x >= lineStartX && x <= lineEndX) {
            int index = layout.getOffsetForHorizontal(line, x);
            ReactTagSpan[] spans = (ReactTagSpan[]) text.getSpans(index, index, ReactTagSpan.class);
            if (spans != null) {
                int targetSpanTextLength = text.length();
                for (int i = 0; i < spans.length; i++) {
                    int spanStart = text.getSpanStart(spans[i]);
                    int spanEnd = text.getSpanEnd(spans[i]);
                    if (spanEnd > index && spanEnd - spanStart <= targetSpanTextLength) {
                        target = spans[i].getReactTag();
                        targetSpanTextLength = spanEnd - spanStart;
                    }
                }
            }
        }
        return target;
    }

    @Override // android.widget.TextView
    public void setTextIsSelectable(boolean selectable) {
        this.mTextIsSelectable = selectable;
        super.setTextIsSelectable(selectable);
    }

    @Override // android.widget.TextView, android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned text = (Spanned) getText();
            TextInlineImageSpan[] spans = (TextInlineImageSpan[]) text.getSpans(0, text.length(), TextInlineImageSpan.class);
            for (TextInlineImageSpan span : spans) {
                if (span.getDrawable() == drawable) {
                    return true;
                }
            }
        }
        return super.verifyDrawable(drawable);
    }

    @Override // android.widget.TextView, android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned text = (Spanned) getText();
            TextInlineImageSpan[] spans = (TextInlineImageSpan[]) text.getSpans(0, text.length(), TextInlineImageSpan.class);
            for (TextInlineImageSpan span : spans) {
                if (span.getDrawable() == drawable) {
                    invalidate();
                }
            }
        }
        super.invalidateDrawable(drawable);
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned text = (Spanned) getText();
            TextInlineImageSpan[] spans = (TextInlineImageSpan[]) text.getSpans(0, text.length(), TextInlineImageSpan.class);
            for (TextInlineImageSpan span : spans) {
                span.onDetachedFromWindow();
            }
        }
    }

    @Override // android.view.View
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned text = (Spanned) getText();
            TextInlineImageSpan[] spans = (TextInlineImageSpan[]) text.getSpans(0, text.length(), TextInlineImageSpan.class);
            for (TextInlineImageSpan span : spans) {
                span.onStartTemporaryDetach();
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned text = (Spanned) getText();
            TextInlineImageSpan[] spans = (TextInlineImageSpan[]) text.getSpans(0, text.length(), TextInlineImageSpan.class);
            for (TextInlineImageSpan span : spans) {
                span.onAttachedToWindow();
            }
        }
    }

    @Override // android.view.View
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned text = (Spanned) getText();
            TextInlineImageSpan[] spans = (TextInlineImageSpan[]) text.getSpans(0, text.length(), TextInlineImageSpan.class);
            for (TextInlineImageSpan span : spans) {
                span.onFinishTemporaryDetach();
            }
        }
    }

    @Override // android.view.View
    public void setBackgroundColor(int color) {
        if (color != 0 || this.mReactBackgroundDrawable != null) {
            getOrCreateReactViewBackground().setColor(color);
        }
    }

    void setGravityHorizontal(int gravityHorizontal) {
        if (gravityHorizontal == 0) {
            gravityHorizontal = this.mDefaultGravityHorizontal;
        }
        setGravity((getGravity() & (-8) & (-8388616)) | gravityHorizontal);
    }

    void setGravityVertical(int gravityVertical) {
        if (gravityVertical == 0) {
            gravityVertical = this.mDefaultGravityVertical;
        }
        setGravity((getGravity() & (-113)) | gravityVertical);
    }

    public void setNumberOfLines(int numberOfLines) {
        if (numberOfLines == 0) {
            numberOfLines = Integer.MAX_VALUE;
        }
        this.mNumberOfLines = numberOfLines;
        setMaxLines(this.mNumberOfLines);
    }

    public void setEllipsizeLocation(TextUtils.TruncateAt ellipsizeLocation) {
        this.mEllipsizeLocation = ellipsizeLocation;
    }

    public void updateView() {
        TextUtils.TruncateAt ellipsizeLocation = this.mNumberOfLines == Integer.MAX_VALUE ? null : this.mEllipsizeLocation;
        setEllipsize(ellipsizeLocation);
    }

    public void setBorderWidth(int position, float width) {
        getOrCreateReactViewBackground().setBorderWidth(position, width);
    }

    public void setBorderColor(int position, float color, float alpha) {
        getOrCreateReactViewBackground().setBorderColor(position, color, alpha);
    }

    public void setBorderRadius(float borderRadius) {
        getOrCreateReactViewBackground().setRadius(borderRadius);
    }

    public void setBorderRadius(float borderRadius, int position) {
        getOrCreateReactViewBackground().setRadius(borderRadius, position);
    }

    public void setBorderStyle(String style) {
        getOrCreateReactViewBackground().setBorderStyle(style);
    }

    private ReactViewBackgroundDrawable getOrCreateReactViewBackground() {
        if (this.mReactBackgroundDrawable == null) {
            this.mReactBackgroundDrawable = new ReactViewBackgroundDrawable();
            Drawable backgroundDrawable = getBackground();
            super.setBackground(null);
            if (backgroundDrawable == null) {
                super.setBackground(this.mReactBackgroundDrawable);
            } else {
                LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{this.mReactBackgroundDrawable, backgroundDrawable});
                super.setBackground(layerDrawable);
            }
        }
        return this.mReactBackgroundDrawable;
    }
}
