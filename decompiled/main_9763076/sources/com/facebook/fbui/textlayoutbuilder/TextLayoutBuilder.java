package com.facebook.fbui.textlayoutbuilder;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.util.LruCache;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TextLayoutBuilder {
    static final LruCache<Integer, Layout> sCache = new LruCache<>(100);
    private GlyphWarmer mGlyphWarmer;
    final Params mParams = new Params();
    private Layout mSavedLayout = null;
    private boolean mShouldCacheLayout = true;
    private boolean mShouldWarmText = false;

    static class Params {
        int measureMode;
        CharSequence text;
        int width;
        TextPaint paint = new ComparableTextPaint(1);
        float spacingMult = 1.0f;
        float spacingAdd = 0.0f;
        boolean includePadding = true;
        TextUtils.TruncateAt ellipsize = null;
        boolean singleLine = false;
        int maxLines = Integer.MAX_VALUE;
        Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
        TextDirectionHeuristicCompat textDirection = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        boolean mForceNewPaint = false;

        Params() {
        }

        void createNewPaintIfNeeded() {
            if (this.mForceNewPaint) {
                this.paint = new ComparableTextPaint(this.paint);
                this.mForceNewPaint = false;
            }
        }

        public int hashCode() {
            int hashCode = (this.paint != null ? this.paint.hashCode() : 0) + 31;
            return (((((((((((((((((((((hashCode * 31) + this.width) * 31) + this.measureMode) * 31) + Float.floatToIntBits(this.spacingMult)) * 31) + Float.floatToIntBits(this.spacingAdd)) * 31) + (this.includePadding ? 1 : 0)) * 31) + (this.ellipsize != null ? this.ellipsize.hashCode() : 0)) * 31) + (this.singleLine ? 1 : 0)) * 31) + this.maxLines) * 31) + (this.alignment != null ? this.alignment.hashCode() : 0)) * 31) + (this.textDirection != null ? this.textDirection.hashCode() : 0)) * 31) + (this.text != null ? this.text.hashCode() : 0);
        }
    }

    public TextLayoutBuilder setWidth(int width, int measureMode) {
        if (this.mParams.width != width || this.mParams.measureMode != measureMode) {
            this.mParams.width = width;
            this.mParams.measureMode = measureMode;
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setText(CharSequence text) {
        if (text != this.mParams.text && (text == null || this.mParams.text == null || !text.equals(this.mParams.text))) {
            this.mParams.text = text;
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setTextSize(int size) {
        if (this.mParams.paint.getTextSize() != size) {
            this.mParams.createNewPaintIfNeeded();
            this.mParams.paint.setTextSize(size);
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setTextSpacingExtra(float spacingExtra) {
        if (this.mParams.spacingAdd != spacingExtra) {
            this.mParams.spacingAdd = spacingExtra;
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setTextSpacingMultiplier(float spacingMultiplier) {
        if (this.mParams.spacingMult != spacingMultiplier) {
            this.mParams.spacingMult = spacingMultiplier;
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setIncludeFontPadding(boolean shouldInclude) {
        if (this.mParams.includePadding != shouldInclude) {
            this.mParams.includePadding = shouldInclude;
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setAlignment(Layout.Alignment alignment) {
        if (this.mParams.alignment != alignment) {
            this.mParams.alignment = alignment;
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setTextDirection(TextDirectionHeuristicCompat textDirection) {
        if (this.mParams.textDirection != textDirection) {
            this.mParams.textDirection = textDirection;
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setTextStyle(int style) {
        return setTypeface(Typeface.defaultFromStyle(style));
    }

    public TextLayoutBuilder setTypeface(Typeface typeface) {
        if (this.mParams.paint.getTypeface() != typeface) {
            this.mParams.createNewPaintIfNeeded();
            this.mParams.paint.setTypeface(typeface);
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setEllipsize(TextUtils.TruncateAt ellipsize) {
        if (this.mParams.ellipsize != ellipsize) {
            this.mParams.ellipsize = ellipsize;
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setSingleLine(boolean singleLine) {
        if (this.mParams.singleLine != singleLine) {
            this.mParams.singleLine = singleLine;
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setMaxLines(int maxLines) {
        if (this.mParams.maxLines != maxLines) {
            this.mParams.maxLines = maxLines;
            this.mSavedLayout = null;
        }
        return this;
    }

    public TextLayoutBuilder setShouldCacheLayout(boolean shouldCacheLayout) {
        this.mShouldCacheLayout = shouldCacheLayout;
        return this;
    }

    public TextLayoutBuilder setShouldWarmText(boolean shouldWarmText) {
        this.mShouldWarmText = shouldWarmText;
        return this;
    }

    public TextLayoutBuilder setGlyphWarmer(GlyphWarmer glyphWarmer) {
        this.mGlyphWarmer = glyphWarmer;
        return this;
    }

    public Layout build() {
        int width;
        Layout layout;
        if (this.mShouldCacheLayout && this.mSavedLayout != null) {
            return this.mSavedLayout;
        }
        if (TextUtils.isEmpty(this.mParams.text)) {
            return null;
        }
        boolean hasClickableSpans = false;
        int hashCode = -1;
        if (this.mShouldCacheLayout && (this.mParams.text instanceof Spannable)) {
            ClickableSpan[] spans = (ClickableSpan[]) ((Spannable) this.mParams.text).getSpans(0, this.mParams.text.length() - 1, ClickableSpan.class);
            hasClickableSpans = spans.length > 0;
        }
        if (this.mShouldCacheLayout && !hasClickableSpans) {
            hashCode = this.mParams.hashCode();
            Layout cachedLayout = sCache.get(Integer.valueOf(hashCode));
            if (cachedLayout != null) {
                return cachedLayout;
            }
        }
        BoringLayout.Metrics metrics = null;
        int numLines = this.mParams.singleLine ? 1 : this.mParams.maxLines;
        if (numLines == 1) {
            metrics = BoringLayout.isBoring(this.mParams.text, this.mParams.paint);
        }
        switch (this.mParams.measureMode) {
            case 0:
                width = (int) Math.ceil(Layout.getDesiredWidth(this.mParams.text, this.mParams.paint));
                break;
            case 1:
                width = this.mParams.width;
                break;
            case 2:
                width = Math.min((int) Math.ceil(Layout.getDesiredWidth(this.mParams.text, this.mParams.paint)), this.mParams.width);
                break;
            default:
                throw new IllegalStateException("Unexpected measure mode " + this.mParams.measureMode);
        }
        if (metrics != null) {
            layout = BoringLayout.make(this.mParams.text, this.mParams.paint, width, this.mParams.alignment, this.mParams.spacingMult, this.mParams.spacingAdd, metrics, this.mParams.includePadding, this.mParams.ellipsize, width);
        } else {
            while (true) {
                try {
                    layout = StaticLayoutHelper.make(this.mParams.text, 0, this.mParams.text.length(), this.mParams.paint, width, this.mParams.alignment, this.mParams.spacingMult, this.mParams.spacingAdd, this.mParams.includePadding, this.mParams.ellipsize, width, numLines, this.mParams.textDirection);
                } catch (IndexOutOfBoundsException e) {
                    if (!(this.mParams.text instanceof String)) {
                        Log.e("TextLayoutBuilder", "Hit bug #35412, retrying with Spannables removed", e);
                        this.mParams.text = this.mParams.text.toString();
                    } else {
                        throw e;
                    }
                }
            }
        }
        if (this.mShouldCacheLayout && !hasClickableSpans) {
            this.mSavedLayout = layout;
            sCache.put(Integer.valueOf(hashCode), layout);
        }
        this.mParams.mForceNewPaint = true;
        if (this.mShouldWarmText && this.mGlyphWarmer != null) {
            this.mGlyphWarmer.warmLayout(layout);
        }
        return layout;
    }

    private static class ComparableTextPaint extends TextPaint {
        private int mShadowColor;
        private float mShadowDx;
        private float mShadowDy;
        private float mShadowRadius;

        public ComparableTextPaint() {
        }

        public ComparableTextPaint(int flags) {
            super(flags);
        }

        public ComparableTextPaint(Paint p) {
            super(p);
        }

        @Override // android.graphics.Paint
        public void setShadowLayer(float radius, float dx, float dy, int color) {
            this.mShadowRadius = radius;
            this.mShadowDx = dx;
            this.mShadowDy = dy;
            this.mShadowColor = color;
            super.setShadowLayer(radius, dx, dy, color);
        }

        public int hashCode() {
            Typeface tf = getTypeface();
            int hashCode = ((((((((((((((getColor() + 31) * 31) + Float.floatToIntBits(getTextSize())) * 31) + (tf != null ? tf.hashCode() : 0)) * 31) + Float.floatToIntBits(this.mShadowDx)) * 31) + Float.floatToIntBits(this.mShadowDy)) * 31) + Float.floatToIntBits(this.mShadowRadius)) * 31) + this.mShadowColor) * 31) + this.linkColor;
            if (this.drawableState == null) {
                return (hashCode * 31) + 0;
            }
            for (int i = 0; i < this.drawableState.length; i++) {
                hashCode = (hashCode * 31) + this.drawableState[i];
            }
            return hashCode;
        }
    }
}
