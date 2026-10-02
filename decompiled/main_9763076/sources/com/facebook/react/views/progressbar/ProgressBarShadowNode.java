package com.facebook.react.views.progressbar;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ProgressBar;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNodeAPI;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ProgressBarShadowNode extends LayoutShadowNode implements YogaMeasureFunction {
    private String mStyle = "Normal";
    private final SparseIntArray mHeight = new SparseIntArray();
    private final SparseIntArray mWidth = new SparseIntArray();
    private final Set<Integer> mMeasured = new HashSet();

    public ProgressBarShadowNode() {
        setMeasureFunction(this);
    }

    public String getStyle() {
        return this.mStyle;
    }

    @ReactProp(name = "styleAttr")
    public void setStyle(String style) {
        if (style == null) {
            style = "Normal";
        }
        this.mStyle = style;
    }

    @Override // com.facebook.yoga.YogaMeasureFunction
    public long measure(YogaNodeAPI node, float width, YogaMeasureMode widthMode, float height, YogaMeasureMode heightMode) {
        int style = ReactProgressBarViewManager.getStyleFromString(getStyle());
        if (!this.mMeasured.contains(Integer.valueOf(style))) {
            ProgressBar progressBar = ReactProgressBarViewManager.createProgressBar(getThemedContext(), style);
            int spec = View.MeasureSpec.makeMeasureSpec(-2, 0);
            progressBar.measure(spec, spec);
            this.mHeight.put(style, progressBar.getMeasuredHeight());
            this.mWidth.put(style, progressBar.getMeasuredWidth());
            this.mMeasured.add(Integer.valueOf(style));
        }
        return YogaMeasureOutput.make(this.mWidth.get(style), this.mHeight.get(style));
    }
}
