package com.facebook.react.flat;

import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.text.Layout;
import android.text.TextUtils;
import com.facebook.fbui.textlayoutbuilder.TextLayoutBuilder;
import com.facebook.fbui.textlayoutbuilder.glyphwarmer.GlyphWarmerImpl;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNodeAPI;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class RCTText extends RCTVirtualText implements YogaMeasureFunction {
    private static final TextLayoutBuilder sTextLayoutBuilder = new TextLayoutBuilder().setShouldCacheLayout(false).setShouldWarmText(true).setGlyphWarmer(new GlyphWarmerImpl());
    private DrawTextLayout mDrawCommand;
    private CharSequence mText;
    private float mSpacingMult = 1.0f;
    private float mSpacingAdd = 0.0f;
    private int mNumberOfLines = Integer.MAX_VALUE;
    private int mAlignment = 0;

    public RCTText() {
        setMeasureFunction(this);
        getSpan().setFontSize(getDefaultFontSize());
    }

    @Override // com.facebook.react.flat.FlatTextShadowNode, com.facebook.react.uimanager.ReactShadowNode
    public boolean isVirtual() {
        return false;
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public boolean isVirtualAnchor() {
        return true;
    }

    @Override // com.facebook.yoga.YogaMeasureFunction
    public long measure(YogaNodeAPI node, float width, YogaMeasureMode widthMode, float height, YogaMeasureMode heightMode) {
        CharSequence text = getText();
        if (TextUtils.isEmpty(text)) {
            this.mText = null;
            return YogaMeasureOutput.make(0, 0);
        }
        this.mText = text;
        Layout layout = createTextLayout((int) Math.ceil(width), widthMode, TextUtils.TruncateAt.END, true, this.mNumberOfLines, this.mNumberOfLines == 1, text, getFontSize(), this.mSpacingAdd, this.mSpacingMult, getFontStyle(), getAlignment());
        if (this.mDrawCommand != null && !this.mDrawCommand.isFrozen()) {
            this.mDrawCommand.setLayout(layout);
        } else {
            this.mDrawCommand = new DrawTextLayout(layout);
        }
        return YogaMeasureOutput.make(this.mDrawCommand.getLayoutWidth(), this.mDrawCommand.getLayoutHeight());
    }

    @ReactProp(defaultDouble = Double.NaN, name = "lineHeight")
    public void setLineHeight(double lineHeight) {
        if (Double.isNaN(lineHeight)) {
            this.mSpacingMult = 1.0f;
            this.mSpacingAdd = 0.0f;
        } else {
            this.mSpacingMult = 0.0f;
            this.mSpacingAdd = PixelUtil.toPixelFromSP((float) lineHeight);
        }
        notifyChanged(true);
    }

    @ReactProp(defaultInt = Integer.MAX_VALUE, name = "numberOfLines")
    public void setNumberOfLines(int numberOfLines) {
        this.mNumberOfLines = numberOfLines;
        notifyChanged(true);
    }

    @Override // com.facebook.react.flat.RCTVirtualText
    protected int getDefaultFontSize() {
        return fontSizeFromSp(14.0f);
    }

    @Override // com.facebook.react.flat.FlatTextShadowNode
    protected void notifyChanged(boolean shouldRemeasure) {
        dirty();
    }

    @ReactProp(name = "textAlign")
    public void setTextAlign(String textAlign) {
        if (textAlign == null || "auto".equals(textAlign)) {
            this.mAlignment = 0;
        } else if ("left".equals(textAlign)) {
            this.mAlignment = 3;
        } else if ("right".equals(textAlign)) {
            this.mAlignment = 5;
        } else if ("center".equals(textAlign)) {
            this.mAlignment = 17;
        } else {
            throw new JSApplicationIllegalArgumentException("Invalid textAlign: " + textAlign);
        }
        notifyChanged(false);
    }

    public Layout.Alignment getAlignment() {
        int index;
        boolean isRtl = getLayoutDirection() == YogaDirection.RTL;
        switch (this.mAlignment) {
            case 3:
                index = isRtl ? 4 : 3;
                return Layout.Alignment.values()[index];
            case 5:
                index = isRtl ? 3 : 4;
                return Layout.Alignment.values()[index];
            case 17:
                return Layout.Alignment.ALIGN_CENTER;
            default:
                return Layout.Alignment.ALIGN_NORMAL;
        }
    }

    private static Layout createTextLayout(int width, YogaMeasureMode widthMode, TextUtils.TruncateAt ellipsize, boolean shouldIncludeFontPadding, int maxLines, boolean isSingleLine, CharSequence text, int textSize, float extraSpacing, float spacingMultiplier, int textStyle, Layout.Alignment textAlignment) {
        int textMeasureMode;
        switch (widthMode) {
            case UNDEFINED:
                textMeasureMode = 0;
                break;
            case EXACTLY:
                textMeasureMode = 1;
                break;
            case AT_MOST:
                textMeasureMode = 2;
                break;
            default:
                throw new IllegalStateException("Unexpected size mode: " + widthMode);
        }
        sTextLayoutBuilder.setEllipsize(ellipsize).setMaxLines(maxLines).setSingleLine(isSingleLine).setText(text).setTextSize(textSize).setWidth(width, textMeasureMode);
        sTextLayoutBuilder.setTextStyle(textStyle);
        sTextLayoutBuilder.setTextDirection(TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR);
        sTextLayoutBuilder.setIncludeFontPadding(shouldIncludeFontPadding);
        sTextLayoutBuilder.setTextSpacingExtra(extraSpacing);
        sTextLayoutBuilder.setTextSpacingMultiplier(spacingMultiplier);
        sTextLayoutBuilder.setAlignment(textAlignment);
        Layout newLayout = sTextLayoutBuilder.build();
        sTextLayoutBuilder.setText(null);
        return newLayout;
    }
}
