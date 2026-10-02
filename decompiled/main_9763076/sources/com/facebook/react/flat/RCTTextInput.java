package com.facebook.react.flat;

import android.annotation.TargetApi;
import android.text.SpannableStringBuilder;
import android.view.ViewGroup;
import android.widget.EditText;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.view.MeasureUtil;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNodeAPI;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RCTTextInput extends RCTVirtualText implements YogaMeasureFunction {
    private EditText mEditText;
    private String mText;
    private int mJsEventCount = -1;
    private boolean mPaddingChanged = false;
    private int mNumberOfLines = -1;

    @Override // com.facebook.react.flat.RCTVirtualText, com.facebook.react.flat.FlatShadowNode, com.facebook.react.uimanager.ReactShadowNode
    public /* bridge */ /* synthetic */ void addChildAt(ReactShadowNode reactShadowNode, int i) {
        super.addChildAt(reactShadowNode, i);
    }

    @Override // com.facebook.react.flat.FlatShadowNode, com.facebook.react.uimanager.ReactShadowNode
    public /* bridge */ /* synthetic */ void markUpdated() {
        super.markUpdated();
    }

    @Override // com.facebook.react.flat.RCTVirtualText
    @ReactProp(defaultDouble = Double.NaN, name = "color")
    public /* bridge */ /* synthetic */ void setColor(double d) {
        super.setColor(d);
    }

    @Override // com.facebook.react.flat.RCTVirtualText
    @ReactProp(name = "fontFamily")
    public /* bridge */ /* synthetic */ void setFontFamily(String str) {
        super.setFontFamily(str);
    }

    @Override // com.facebook.react.flat.RCTVirtualText
    @ReactProp(defaultFloat = Float.NaN, name = "fontSize")
    public /* bridge */ /* synthetic */ void setFontSize(float f) {
        super.setFontSize(f);
    }

    @Override // com.facebook.react.flat.RCTVirtualText
    @ReactProp(name = "fontStyle")
    public /* bridge */ /* synthetic */ void setFontStyle(String str) {
        super.setFontStyle(str);
    }

    @Override // com.facebook.react.flat.RCTVirtualText
    @ReactProp(name = "fontWeight")
    public /* bridge */ /* synthetic */ void setFontWeight(String str) {
        super.setFontWeight(str);
    }

    @Override // com.facebook.react.flat.FlatShadowNode, com.facebook.react.uimanager.LayoutShadowNode
    public /* bridge */ /* synthetic */ void setOverflow(String str) {
        super.setOverflow(str);
    }

    @Override // com.facebook.react.flat.RCTVirtualText
    @ReactProp(name = "textDecorationLine")
    public /* bridge */ /* synthetic */ void setTextDecorationLine(String str) {
        super.setTextDecorationLine(str);
    }

    @Override // com.facebook.react.flat.RCTVirtualText
    @ReactProp(customType = "Color", defaultInt = 1426063360, name = "textShadowColor")
    public /* bridge */ /* synthetic */ void setTextShadowColor(int i) {
        super.setTextShadowColor(i);
    }

    @Override // com.facebook.react.flat.RCTVirtualText
    @ReactProp(name = "textShadowOffset")
    public /* bridge */ /* synthetic */ void setTextShadowOffset(ReadableMap readableMap) {
        super.setTextShadowOffset(readableMap);
    }

    @Override // com.facebook.react.flat.RCTVirtualText
    @ReactProp(name = "textShadowRadius")
    public /* bridge */ /* synthetic */ void setTextShadowRadius(float f) {
        super.setTextShadowRadius(f);
    }

    public RCTTextInput() {
        forceMountToView();
        setMeasureFunction(this);
    }

    @Override // com.facebook.react.flat.FlatTextShadowNode
    protected void notifyChanged(boolean shouldRemeasure) {
        super.notifyChanged(shouldRemeasure);
        markUpdated();
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    @TargetApi(17)
    public void setThemedContext(ThemedReactContext themedContext) {
        super.setThemedContext(themedContext);
        this.mEditText = new EditText(themedContext);
        this.mEditText.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        setDefaultPadding(4, this.mEditText.getPaddingStart());
        setDefaultPadding(1, this.mEditText.getPaddingTop());
        setDefaultPadding(5, this.mEditText.getPaddingEnd());
        setDefaultPadding(3, this.mEditText.getPaddingBottom());
        this.mEditText.setPadding(0, 0, 0, 0);
    }

    @Override // com.facebook.yoga.YogaMeasureFunction
    public long measure(YogaNodeAPI node, float width, YogaMeasureMode widthMode, float height, YogaMeasureMode heightMode) {
        EditText editText = (EditText) Assertions.assertNotNull(this.mEditText);
        int fontSize = getFontSize();
        editText.setTextSize(0, fontSize == -1 ? (int) Math.ceil(PixelUtil.toPixelFromSP(14.0f)) : fontSize);
        if (this.mNumberOfLines != -1) {
            editText.setLines(this.mNumberOfLines);
        }
        editText.measure(MeasureUtil.getMeasureSpec(width, widthMode), MeasureUtil.getMeasureSpec(height, heightMode));
        return YogaMeasureOutput.make(editText.getMeasuredWidth(), editText.getMeasuredHeight());
    }

    @Override // com.facebook.react.flat.FlatTextShadowNode, com.facebook.react.uimanager.ReactShadowNode
    public boolean isVirtual() {
        return false;
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public boolean isVirtualAnchor() {
        return true;
    }

    @Override // com.facebook.react.flat.RCTVirtualText, com.facebook.react.flat.FlatShadowNode
    public void setBackgroundColor(int backgroundColor) {
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void onCollectExtraUpdates(UIViewOperationQueue uiViewOperationQueue) {
        super.onCollectExtraUpdates(uiViewOperationQueue);
        if (this.mJsEventCount != -1) {
            ReactTextUpdate reactTextUpdate = new ReactTextUpdate(getText(), this.mJsEventCount, false, getPadding(4), getPadding(1), getPadding(5), getPadding(3), -1);
            uiViewOperationQueue.enqueueUpdateExtraData(getReactTag(), reactTextUpdate);
        }
    }

    @ReactProp(name = "mostRecentEventCount")
    public void setMostRecentEventCount(int mostRecentEventCount) {
        this.mJsEventCount = mostRecentEventCount;
    }

    @ReactProp(defaultInt = Integer.MAX_VALUE, name = "numberOfLines")
    public void setNumberOfLines(int numberOfLines) {
        this.mNumberOfLines = numberOfLines;
        notifyChanged(true);
    }

    @ReactProp(name = "text")
    public void setText(String text) {
        this.mText = text;
        notifyChanged(true);
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void setPadding(int spacingType, float padding) {
        super.setPadding(spacingType, padding);
        this.mPaddingChanged = true;
        dirty();
    }

    @Override // com.facebook.react.flat.FlatTextShadowNode
    boolean shouldAllowEmptySpans() {
        return true;
    }

    @Override // com.facebook.react.flat.FlatTextShadowNode
    boolean isEditable() {
        return true;
    }

    @Override // com.facebook.react.flat.RCTVirtualText, com.facebook.react.flat.FlatTextShadowNode
    protected void performCollectText(SpannableStringBuilder builder) {
        if (this.mText != null) {
            builder.append((CharSequence) this.mText);
        }
        super.performCollectText(builder);
    }
}
