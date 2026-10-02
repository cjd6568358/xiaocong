package com.facebook.react.views.text;

import android.text.Spannable;
import android.text.TextUtils;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.YogaConstants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactTextViewManager extends BaseViewManager<ReactTextView, ReactTextShadowNode> {
    public static final String REACT_CLASS = "RCTText";
    private static final int[] SPACING_TYPES = {8, 0, 2, 1, 3};

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public ReactTextView createViewInstance(ThemedReactContext context) {
        return new ReactTextView(context);
    }

    @ReactProp(defaultInt = Integer.MAX_VALUE, name = "numberOfLines")
    public void setNumberOfLines(ReactTextView view, int numberOfLines) {
        view.setNumberOfLines(numberOfLines);
    }

    @ReactProp(name = "ellipsizeMode")
    public void setEllipsizeMode(ReactTextView view, String ellipsizeMode) {
        if (ellipsizeMode == null || ellipsizeMode.equals("tail")) {
            view.setEllipsizeLocation(TextUtils.TruncateAt.END);
        } else if (ellipsizeMode.equals("head")) {
            view.setEllipsizeLocation(TextUtils.TruncateAt.START);
        } else {
            if (ellipsizeMode.equals("middle")) {
                view.setEllipsizeLocation(TextUtils.TruncateAt.MIDDLE);
                return;
            }
            throw new JSApplicationIllegalArgumentException("Invalid ellipsizeMode: " + ellipsizeMode);
        }
    }

    @ReactProp(name = "textAlignVertical")
    public void setTextAlignVertical(ReactTextView view, String textAlignVertical) {
        if (textAlignVertical == null || "auto".equals(textAlignVertical)) {
            view.setGravityVertical(0);
            return;
        }
        if ("top".equals(textAlignVertical)) {
            view.setGravityVertical(48);
        } else if ("bottom".equals(textAlignVertical)) {
            view.setGravityVertical(80);
        } else {
            if ("center".equals(textAlignVertical)) {
                view.setGravityVertical(16);
                return;
            }
            throw new JSApplicationIllegalArgumentException("Invalid textAlignVertical: " + textAlignVertical);
        }
    }

    @ReactProp(name = "selectable")
    public void setSelectable(ReactTextView view, boolean isSelectable) {
        view.setTextIsSelectable(isSelectable);
    }

    @ReactProp(customType = "Color", name = "selectionColor")
    public void setSelectionColor(ReactTextView view, Integer color) {
        if (color == null) {
            view.setHighlightColor(DefaultStyleValuesUtil.getDefaultTextColorHighlight(view.getContext()));
        } else {
            view.setHighlightColor(color.intValue());
        }
    }

    @ReactPropGroup(defaultFloat = Float.NaN, names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius"})
    public void setBorderRadius(ReactTextView view, int index, float borderRadius) {
        if (!YogaConstants.isUndefined(borderRadius)) {
            borderRadius = PixelUtil.toPixelFromDIP(borderRadius);
        }
        if (index == 0) {
            view.setBorderRadius(borderRadius);
        } else {
            view.setBorderRadius(borderRadius, index - 1);
        }
    }

    @ReactProp(name = "borderStyle")
    public void setBorderStyle(ReactTextView view, String borderStyle) {
        view.setBorderStyle(borderStyle);
    }

    @ReactPropGroup(defaultFloat = Float.NaN, names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"})
    public void setBorderWidth(ReactTextView view, int index, float width) {
        if (!YogaConstants.isUndefined(width)) {
            width = PixelUtil.toPixelFromDIP(width);
        }
        view.setBorderWidth(SPACING_TYPES[index], width);
    }

    @ReactPropGroup(customType = "Color", names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"})
    public void setBorderColor(ReactTextView view, int index, Integer color) {
        float rgbComponent = color == null ? Float.NaN : color.intValue() & 16777215;
        float alphaComponent = color != null ? color.intValue() >>> 24 : Float.NaN;
        view.setBorderColor(SPACING_TYPES[index], rgbComponent, alphaComponent);
    }

    @ReactProp(defaultBoolean = true, name = "includeFontPadding")
    public void setIncludeFontPadding(ReactTextView view, boolean includepad) {
        view.setIncludeFontPadding(includepad);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void updateExtraData(ReactTextView view, Object extraData) {
        ReactTextUpdate update = (ReactTextUpdate) extraData;
        if (update.containsImages()) {
            Spannable spannable = update.getText();
            TextInlineImageSpan.possiblyUpdateInlineImageSpans(spannable, view);
        }
        view.setText(update);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public ReactTextShadowNode createShadowNodeInstance() {
        return new ReactTextShadowNode();
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Class<ReactTextShadowNode> getShadowNodeClass() {
        return ReactTextShadowNode.class;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void onAfterUpdateTransaction(ReactTextView view) {
        super.onAfterUpdateTransaction(view);
        view.updateView();
    }
}
