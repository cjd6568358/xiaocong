package com.facebook.react.views.textinput;

import android.os.Build;
import android.text.Spannable;
import android.view.ViewGroup;
import android.widget.EditText;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextShadowNode;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.view.MeasureUtil;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNodeAPI;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactTextInputShadowNode extends ReactTextShadowNode implements YogaMeasureFunction {
    private EditText mEditText;
    private int mJsEventCount = -1;

    public ReactTextInputShadowNode() {
        if (Build.VERSION.SDK_INT < 23) {
        }
        this.mTextBreakStrategy = 0;
        setMeasureFunction(this);
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void setThemedContext(ThemedReactContext themedContext) {
        super.setThemedContext(themedContext);
        this.mEditText = new EditText(getThemedContext());
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
        editText.setTextSize(0, this.mFontSize == -1 ? (int) Math.ceil(PixelUtil.toPixelFromSP(14.0f)) : this.mFontSize);
        if (this.mNumberOfLines != -1) {
            editText.setLines(this.mNumberOfLines);
        }
        if (Build.VERSION.SDK_INT >= 23 && editText.getBreakStrategy() != this.mTextBreakStrategy) {
            editText.setBreakStrategy(this.mTextBreakStrategy);
        }
        editText.measure(MeasureUtil.getMeasureSpec(width, widthMode), MeasureUtil.getMeasureSpec(height, heightMode));
        return YogaMeasureOutput.make(editText.getMeasuredWidth(), editText.getMeasuredHeight());
    }

    @Override // com.facebook.react.views.text.ReactTextShadowNode, com.facebook.react.uimanager.ReactShadowNode
    public void onBeforeLayout() {
    }

    @ReactProp(name = "mostRecentEventCount")
    public void setMostRecentEventCount(int mostRecentEventCount) {
        this.mJsEventCount = mostRecentEventCount;
    }

    @Override // com.facebook.react.views.text.ReactTextShadowNode
    public void setTextBreakStrategy(String textBreakStrategy) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (textBreakStrategy == null || "simple".equals(textBreakStrategy)) {
                this.mTextBreakStrategy = 0;
            } else if ("highQuality".equals(textBreakStrategy)) {
                this.mTextBreakStrategy = 1;
            } else {
                if ("balanced".equals(textBreakStrategy)) {
                    this.mTextBreakStrategy = 2;
                    return;
                }
                throw new JSApplicationIllegalArgumentException("Invalid textBreakStrategy: " + textBreakStrategy);
            }
        }
    }

    @Override // com.facebook.react.views.text.ReactTextShadowNode, com.facebook.react.uimanager.ReactShadowNode
    public void onCollectExtraUpdates(UIViewOperationQueue uiViewOperationQueue) {
        super.onCollectExtraUpdates(uiViewOperationQueue);
        if (this.mJsEventCount != -1) {
            Spannable preparedSpannableText = fromTextCSSNode(this);
            ReactTextUpdate reactTextUpdate = new ReactTextUpdate(preparedSpannableText, this.mJsEventCount, this.mContainsImages, getPadding(0), getPadding(1), getPadding(2), getPadding(3), this.mTextAlign, this.mTextBreakStrategy);
            uiViewOperationQueue.enqueueUpdateExtraData(getReactTag(), reactTextUpdate);
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void setPadding(int spacingType, float padding) {
        super.setPadding(spacingType, padding);
        markUpdated();
    }
}
