package com.facebook.react.views.text;

import android.os.Build;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaConstants;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNodeAPI;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactTextShadowNode extends LayoutShadowNode {
    private static final TextPaint sTextPaintInstance = new TextPaint();
    private int mBackgroundColor;
    private int mColor;
    protected boolean mContainsImages;
    private String mFontFamily;
    private int mFontStyle;
    private int mFontWeight;
    private float mHeightOfTallestInlineImage;
    private boolean mIsLineThroughTextDecorationSet;
    private boolean mIsUnderlineTextDecorationSet;
    private Spannable mPreparedSpannableText;
    private String mText;
    protected int mTextBreakStrategy;
    private int mTextShadowColor;
    private float mTextShadowOffsetDx;
    private float mTextShadowOffsetDy;
    private float mTextShadowRadius;
    private final YogaMeasureFunction mTextMeasureFunction = new YogaMeasureFunction() { // from class: com.facebook.react.views.text.ReactTextShadowNode.1
        @Override // com.facebook.yoga.YogaMeasureFunction
        public long measure(YogaNodeAPI node, float width, YogaMeasureMode widthMode, float height, YogaMeasureMode heightMode) {
            Layout layout;
            TextPaint textPaint = ReactTextShadowNode.sTextPaintInstance;
            Spanned text = (Spanned) Assertions.assertNotNull(ReactTextShadowNode.this.mPreparedSpannableText, "Spannable element has not been prepared in onBeforeLayout");
            BoringLayout.Metrics boring = BoringLayout.isBoring(text, textPaint);
            float desiredWidth = boring == null ? Layout.getDesiredWidth(text, textPaint) : Float.NaN;
            boolean unconstrainedWidth = widthMode == YogaMeasureMode.UNDEFINED || width < 0.0f;
            if (boring == null && (unconstrainedWidth || (!YogaConstants.isUndefined(desiredWidth) && desiredWidth <= width))) {
                int hintWidth = (int) Math.ceil(desiredWidth);
                if (Build.VERSION.SDK_INT < 23) {
                    layout = new StaticLayout(text, textPaint, hintWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
                } else {
                    layout = StaticLayout.Builder.obtain(text, 0, text.length(), textPaint, hintWidth).setAlignment(Layout.Alignment.ALIGN_NORMAL).setLineSpacing(0.0f, 1.0f).setIncludePad(true).setBreakStrategy(ReactTextShadowNode.this.mTextBreakStrategy).setHyphenationFrequency(1).build();
                }
            } else if (boring != null && (unconstrainedWidth || boring.width <= width)) {
                layout = BoringLayout.make(text, textPaint, boring.width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, boring, true);
            } else if (Build.VERSION.SDK_INT < 23) {
                layout = new StaticLayout(text, textPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            } else {
                layout = StaticLayout.Builder.obtain(text, 0, text.length(), textPaint, (int) width).setAlignment(Layout.Alignment.ALIGN_NORMAL).setLineSpacing(0.0f, 1.0f).setIncludePad(true).setBreakStrategy(ReactTextShadowNode.this.mTextBreakStrategy).setHyphenationFrequency(1).build();
            }
            if (ReactTextShadowNode.this.mNumberOfLines != -1 && ReactTextShadowNode.this.mNumberOfLines < layout.getLineCount()) {
                return YogaMeasureOutput.make(layout.getWidth(), layout.getLineBottom(ReactTextShadowNode.this.mNumberOfLines - 1));
            }
            return YogaMeasureOutput.make(layout.getWidth(), layout.getHeight());
        }
    };
    private float mLineHeight = Float.NaN;
    private boolean mIsColorSet = false;
    private boolean mAllowFontScaling = true;
    private boolean mIsBackgroundColorSet = false;
    protected int mNumberOfLines = -1;
    protected int mFontSize = -1;
    protected float mFontSizeInput = -1.0f;
    protected int mLineHeightInput = -1;
    protected int mTextAlign = 0;

    static {
        sTextPaintInstance.setFlags(1);
    }

    private static class SetSpanOperation {
        protected int end;
        protected int start;
        protected Object what;

        SetSpanOperation(int start, int end, Object what) {
            this.start = start;
            this.end = end;
            this.what = what;
        }

        public void execute(SpannableStringBuilder sb) {
            int spanFlags = 34;
            if (this.start == 0) {
                spanFlags = 18;
            }
            sb.setSpan(this.what, this.start, this.end, spanFlags);
        }
    }

    private static void buildSpannedFromTextCSSNode(ReactTextShadowNode textShadowNode, SpannableStringBuilder sb, List<SetSpanOperation> ops) {
        int start = sb.length();
        if (textShadowNode.mText != null) {
            sb.append((CharSequence) textShadowNode.mText);
        }
        int length = textShadowNode.getChildCount();
        for (int i = 0; i < length; i++) {
            ReactShadowNode child = textShadowNode.getChildAt(i);
            if (child instanceof ReactTextShadowNode) {
                buildSpannedFromTextCSSNode((ReactTextShadowNode) child, sb, ops);
            } else if (child instanceof ReactTextInlineImageShadowNode) {
                sb.append("I");
                ops.add(new SetSpanOperation(sb.length() - "I".length(), sb.length(), ((ReactTextInlineImageShadowNode) child).buildInlineImageSpan()));
            } else {
                throw new IllegalViewOperationException("Unexpected view type nested under text node: " + child.getClass());
            }
            child.markUpdateSeen();
        }
        int end = sb.length();
        if (end >= start) {
            if (textShadowNode.mIsColorSet) {
                ops.add(new SetSpanOperation(start, end, new ForegroundColorSpan(textShadowNode.mColor)));
            }
            if (textShadowNode.mIsBackgroundColorSet) {
                ops.add(new SetSpanOperation(start, end, new BackgroundColorSpan(textShadowNode.mBackgroundColor)));
            }
            if (textShadowNode.mFontSize != -1) {
                ops.add(new SetSpanOperation(start, end, new AbsoluteSizeSpan(textShadowNode.mFontSize)));
            }
            if (textShadowNode.mFontStyle != -1 || textShadowNode.mFontWeight != -1 || textShadowNode.mFontFamily != null) {
                ops.add(new SetSpanOperation(start, end, new CustomStyleSpan(textShadowNode.mFontStyle, textShadowNode.mFontWeight, textShadowNode.mFontFamily, textShadowNode.getThemedContext().getAssets())));
            }
            if (textShadowNode.mIsUnderlineTextDecorationSet) {
                ops.add(new SetSpanOperation(start, end, new UnderlineSpan()));
            }
            if (textShadowNode.mIsLineThroughTextDecorationSet) {
                ops.add(new SetSpanOperation(start, end, new StrikethroughSpan()));
            }
            if (textShadowNode.mTextShadowOffsetDx != 0.0f || textShadowNode.mTextShadowOffsetDy != 0.0f) {
                ops.add(new SetSpanOperation(start, end, new ShadowStyleSpan(textShadowNode.mTextShadowOffsetDx, textShadowNode.mTextShadowOffsetDy, textShadowNode.mTextShadowRadius, textShadowNode.mTextShadowColor)));
            }
            if (!Float.isNaN(textShadowNode.getEffectiveLineHeight())) {
                ops.add(new SetSpanOperation(start, end, new CustomLineHeightSpan(textShadowNode.getEffectiveLineHeight())));
            }
            ops.add(new SetSpanOperation(start, end, new ReactTagSpan(textShadowNode.getReactTag())));
        }
    }

    protected static Spannable fromTextCSSNode(ReactTextShadowNode textCSSNode) {
        int iCeil;
        SpannableStringBuilder sb = new SpannableStringBuilder();
        List<SetSpanOperation> ops = new ArrayList<>();
        buildSpannedFromTextCSSNode(textCSSNode, sb, ops);
        if (textCSSNode.mFontSize == -1) {
            if (textCSSNode.mAllowFontScaling) {
                iCeil = (int) Math.ceil(PixelUtil.toPixelFromSP(14.0f));
            } else {
                iCeil = (int) Math.ceil(PixelUtil.toPixelFromDIP(14.0f));
            }
            sb.setSpan(new AbsoluteSizeSpan(iCeil), 0, sb.length(), 17);
        }
        textCSSNode.mContainsImages = false;
        textCSSNode.mHeightOfTallestInlineImage = Float.NaN;
        for (int i = ops.size() - 1; i >= 0; i--) {
            SetSpanOperation op = ops.get(i);
            if (op.what instanceof TextInlineImageSpan) {
                int height = ((TextInlineImageSpan) op.what).getHeight();
                textCSSNode.mContainsImages = true;
                if (Float.isNaN(textCSSNode.mHeightOfTallestInlineImage) || height > textCSSNode.mHeightOfTallestInlineImage) {
                    textCSSNode.mHeightOfTallestInlineImage = height;
                }
            }
            op.execute(sb);
        }
        return sb;
    }

    private static int parseNumericFontWeight(String fontWeightString) {
        if (fontWeightString.length() != 3 || !fontWeightString.endsWith("00") || fontWeightString.charAt(0) > '9' || fontWeightString.charAt(0) < '1') {
            return -1;
        }
        return (fontWeightString.charAt(0) - '0') * 100;
    }

    public ReactTextShadowNode() {
        this.mTextBreakStrategy = Build.VERSION.SDK_INT < 23 ? 0 : 1;
        this.mTextShadowOffsetDx = 0.0f;
        this.mTextShadowOffsetDy = 0.0f;
        this.mTextShadowRadius = 1.0f;
        this.mTextShadowColor = 1426063360;
        this.mIsUnderlineTextDecorationSet = false;
        this.mIsLineThroughTextDecorationSet = false;
        this.mFontStyle = -1;
        this.mFontWeight = -1;
        this.mFontFamily = null;
        this.mText = null;
        this.mContainsImages = false;
        this.mHeightOfTallestInlineImage = Float.NaN;
        if (!isVirtual()) {
            setMeasureFunction(this.mTextMeasureFunction);
        }
    }

    public float getEffectiveLineHeight() {
        boolean useInlineViewHeight = (Float.isNaN(this.mLineHeight) || Float.isNaN(this.mHeightOfTallestInlineImage) || this.mHeightOfTallestInlineImage <= this.mLineHeight) ? false : true;
        return useInlineViewHeight ? this.mHeightOfTallestInlineImage : this.mLineHeight;
    }

    private int getTextAlign() {
        int textAlign = this.mTextAlign;
        if (getLayoutDirection() == YogaDirection.RTL) {
            if (textAlign == 5) {
                return 3;
            }
            if (textAlign == 3) {
                return 5;
            }
            return textAlign;
        }
        return textAlign;
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void onBeforeLayout() {
        if (!isVirtual()) {
            this.mPreparedSpannableText = fromTextCSSNode(this);
            markUpdated();
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void markUpdated() {
        super.markUpdated();
        if (!isVirtual()) {
            super.dirty();
        }
    }

    @ReactProp(name = "text")
    public void setText(String text) {
        this.mText = text;
        markUpdated();
    }

    @ReactProp(defaultInt = -1, name = "numberOfLines")
    public void setNumberOfLines(int numberOfLines) {
        if (numberOfLines == 0) {
            numberOfLines = -1;
        }
        this.mNumberOfLines = numberOfLines;
        markUpdated();
    }

    @ReactProp(defaultInt = -1, name = "lineHeight")
    public void setLineHeight(int lineHeight) {
        this.mLineHeightInput = lineHeight;
        if (lineHeight == -1) {
            this.mLineHeight = Float.NaN;
        } else {
            this.mLineHeight = this.mAllowFontScaling ? PixelUtil.toPixelFromSP(lineHeight) : PixelUtil.toPixelFromDIP(lineHeight);
        }
        markUpdated();
    }

    @ReactProp(defaultBoolean = true, name = "allowFontScaling")
    public void setAllowFontScaling(boolean allowFontScaling) {
        if (allowFontScaling != this.mAllowFontScaling) {
            this.mAllowFontScaling = allowFontScaling;
            setFontSize(this.mFontSizeInput);
            setLineHeight(this.mLineHeightInput);
            markUpdated();
        }
    }

    @ReactProp(name = "textAlign")
    public void setTextAlign(String textAlign) {
        if (textAlign == null || "auto".equals(textAlign)) {
            this.mTextAlign = 0;
        } else if ("left".equals(textAlign)) {
            this.mTextAlign = 3;
        } else if ("right".equals(textAlign)) {
            this.mTextAlign = 5;
        } else if ("center".equals(textAlign)) {
            this.mTextAlign = 1;
        } else if ("justify".equals(textAlign)) {
            this.mTextAlign = 3;
        } else {
            throw new JSApplicationIllegalArgumentException("Invalid textAlign: " + textAlign);
        }
        markUpdated();
    }

    @ReactProp(defaultFloat = -1.0f, name = "fontSize")
    public void setFontSize(float fontSize) {
        this.mFontSizeInput = fontSize;
        if (fontSize != -1.0f) {
            fontSize = this.mAllowFontScaling ? (float) Math.ceil(PixelUtil.toPixelFromSP(fontSize)) : (float) Math.ceil(PixelUtil.toPixelFromDIP(fontSize));
        }
        this.mFontSize = (int) fontSize;
        markUpdated();
    }

    @ReactProp(name = "color")
    public void setColor(Integer color) {
        this.mIsColorSet = color != null;
        if (this.mIsColorSet) {
            this.mColor = color.intValue();
        }
        markUpdated();
    }

    @ReactProp(name = "backgroundColor")
    public void setBackgroundColor(Integer color) {
        if (!isVirtualAnchor()) {
            this.mIsBackgroundColorSet = color != null;
            if (this.mIsBackgroundColorSet) {
                this.mBackgroundColor = color.intValue();
            }
            markUpdated();
        }
    }

    @ReactProp(name = "fontFamily")
    public void setFontFamily(String fontFamily) {
        this.mFontFamily = fontFamily;
        markUpdated();
    }

    @ReactProp(name = "fontWeight")
    public void setFontWeight(String fontWeightString) {
        int fontWeightNumeric = fontWeightString != null ? parseNumericFontWeight(fontWeightString) : -1;
        int fontWeight = -1;
        if (fontWeightNumeric >= 500 || "bold".equals(fontWeightString)) {
            fontWeight = 1;
        } else if ("normal".equals(fontWeightString) || (fontWeightNumeric != -1 && fontWeightNumeric < 500)) {
            fontWeight = 0;
        }
        if (fontWeight != this.mFontWeight) {
            this.mFontWeight = fontWeight;
            markUpdated();
        }
    }

    @ReactProp(name = "fontStyle")
    public void setFontStyle(String fontStyleString) {
        int fontStyle = -1;
        if ("italic".equals(fontStyleString)) {
            fontStyle = 2;
        } else if ("normal".equals(fontStyleString)) {
            fontStyle = 0;
        }
        if (fontStyle != this.mFontStyle) {
            this.mFontStyle = fontStyle;
            markUpdated();
        }
    }

    @ReactProp(name = "textDecorationLine")
    public void setTextDecorationLine(String textDecorationLineString) {
        this.mIsUnderlineTextDecorationSet = false;
        this.mIsLineThroughTextDecorationSet = false;
        if (textDecorationLineString != null) {
            for (String textDecorationLineSubString : textDecorationLineString.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
                if ("underline".equals(textDecorationLineSubString)) {
                    this.mIsUnderlineTextDecorationSet = true;
                } else if ("line-through".equals(textDecorationLineSubString)) {
                    this.mIsLineThroughTextDecorationSet = true;
                }
            }
        }
        markUpdated();
    }

    @ReactProp(name = "textBreakStrategy")
    public void setTextBreakStrategy(String textBreakStrategy) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (textBreakStrategy == null || "highQuality".equals(textBreakStrategy)) {
                this.mTextBreakStrategy = 1;
            } else if ("simple".equals(textBreakStrategy)) {
                this.mTextBreakStrategy = 0;
            } else if ("balanced".equals(textBreakStrategy)) {
                this.mTextBreakStrategy = 2;
            } else {
                throw new JSApplicationIllegalArgumentException("Invalid textBreakStrategy: " + textBreakStrategy);
            }
            markUpdated();
        }
    }

    @ReactProp(name = "textShadowOffset")
    public void setTextShadowOffset(ReadableMap offsetMap) {
        this.mTextShadowOffsetDx = 0.0f;
        this.mTextShadowOffsetDy = 0.0f;
        if (offsetMap != null) {
            if (offsetMap.hasKey(IMediaFormat.KEY_WIDTH) && !offsetMap.isNull(IMediaFormat.KEY_WIDTH)) {
                this.mTextShadowOffsetDx = PixelUtil.toPixelFromDIP(offsetMap.getDouble(IMediaFormat.KEY_WIDTH));
            }
            if (offsetMap.hasKey(IMediaFormat.KEY_HEIGHT) && !offsetMap.isNull(IMediaFormat.KEY_HEIGHT)) {
                this.mTextShadowOffsetDy = PixelUtil.toPixelFromDIP(offsetMap.getDouble(IMediaFormat.KEY_HEIGHT));
            }
        }
        markUpdated();
    }

    @ReactProp(defaultInt = 1, name = "textShadowRadius")
    public void setTextShadowRadius(float textShadowRadius) {
        if (textShadowRadius != this.mTextShadowRadius) {
            this.mTextShadowRadius = textShadowRadius;
            markUpdated();
        }
    }

    @ReactProp(customType = "Color", defaultInt = 1426063360, name = "textShadowColor")
    public void setTextShadowColor(int textShadowColor) {
        if (textShadowColor != this.mTextShadowColor) {
            this.mTextShadowColor = textShadowColor;
            markUpdated();
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public boolean isVirtualAnchor() {
        return !isVirtual();
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void onCollectExtraUpdates(UIViewOperationQueue uiViewOperationQueue) {
        if (!isVirtual()) {
            super.onCollectExtraUpdates(uiViewOperationQueue);
            if (this.mPreparedSpannableText != null) {
                ReactTextUpdate reactTextUpdate = new ReactTextUpdate(this.mPreparedSpannableText, -1, this.mContainsImages, getPadding(4), getPadding(1), getPadding(5), getPadding(3), getTextAlign(), this.mTextBreakStrategy);
                uiViewOperationQueue.enqueueUpdateExtraData(getReactTag(), reactTextUpdate);
            }
        }
    }
}
