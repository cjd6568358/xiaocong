package com.facebook.react.views.progressbar;

import android.R;
import android.content.Context;
import android.widget.ProgressBar;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactProgressBarViewManager extends BaseViewManager<ProgressBarContainerView, ProgressBarShadowNode> {
    static final String DEFAULT_STYLE = "Normal";
    static final String PROP_ANIMATING = "animating";
    static final String PROP_INDETERMINATE = "indeterminate";
    static final String PROP_PROGRESS = "progress";
    static final String PROP_STYLE = "styleAttr";
    protected static final String REACT_CLASS = "AndroidProgressBar";
    private static Object sProgressBarCtorLock = new Object();

    public static ProgressBar createProgressBar(Context context, int style) {
        ProgressBar progressBar;
        synchronized (sProgressBarCtorLock) {
            progressBar = new ProgressBar(context, null, style);
        }
        return progressBar;
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ProgressBarContainerView createViewInstance(ThemedReactContext context) {
        return new ProgressBarContainerView(context);
    }

    @ReactProp(name = PROP_STYLE)
    public void setStyle(ProgressBarContainerView view, String styleName) {
        view.setStyle(styleName);
    }

    @ReactProp(customType = "Color", name = "color")
    public void setColor(ProgressBarContainerView view, Integer color) {
        view.setColor(color);
    }

    @ReactProp(name = PROP_INDETERMINATE)
    public void setIndeterminate(ProgressBarContainerView view, boolean indeterminate) {
        view.setIndeterminate(indeterminate);
    }

    @ReactProp(name = PROP_PROGRESS)
    public void setProgress(ProgressBarContainerView view, double progress) {
        view.setProgress(progress);
    }

    @ReactProp(name = PROP_ANIMATING)
    public void setAnimating(ProgressBarContainerView view, boolean animating) {
        view.setAnimating(animating);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public ProgressBarShadowNode createShadowNodeInstance() {
        return new ProgressBarShadowNode();
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Class<ProgressBarShadowNode> getShadowNodeClass() {
        return ProgressBarShadowNode.class;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void updateExtraData(ProgressBarContainerView root, Object extraData) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void onAfterUpdateTransaction(ProgressBarContainerView view) {
        view.apply();
    }

    static int getStyleFromString(String styleStr) {
        if (styleStr == null) {
            throw new JSApplicationIllegalArgumentException("ProgressBar needs to have a style, null received");
        }
        if (styleStr.equals("Horizontal")) {
            return R.attr.progressBarStyleHorizontal;
        }
        if (styleStr.equals("Small")) {
            return R.attr.progressBarStyleSmall;
        }
        if (styleStr.equals("Large")) {
            return R.attr.progressBarStyleLarge;
        }
        if (styleStr.equals("Inverse")) {
            return R.attr.progressBarStyleInverse;
        }
        if (styleStr.equals("SmallInverse")) {
            return R.attr.progressBarStyleSmallInverse;
        }
        if (styleStr.equals("LargeInverse")) {
            return R.attr.progressBarStyleLargeInverse;
        }
        if (styleStr.equals(DEFAULT_STYLE)) {
            return R.attr.progressBarStyle;
        }
        throw new JSApplicationIllegalArgumentException("Unknown ProgressBar style: " + styleStr);
    }
}
