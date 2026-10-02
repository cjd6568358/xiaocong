package com.facebook.react.views.slider;

import android.R;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.SeekBar;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNodeAPI;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactSliderManager extends SimpleViewManager<ReactSlider> {
    private static final SeekBar.OnSeekBarChangeListener ON_CHANGE_LISTENER = new SeekBar.OnSeekBarChangeListener() { // from class: com.facebook.react.views.slider.ReactSliderManager.1
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
            ReactContext reactContext = (ReactContext) seekbar.getContext();
            ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ReactSliderEvent(seekbar.getId(), ((ReactSlider) seekbar).toRealProgress(progress), fromUser));
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekbar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekbar) {
            ReactContext reactContext = (ReactContext) seekbar.getContext();
            ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ReactSlidingCompleteEvent(seekbar.getId(), ((ReactSlider) seekbar).toRealProgress(seekbar.getProgress())));
        }
    };
    private static final String REACT_CLASS = "RCTSlider";
    private static final int STYLE = 16842875;

    static class ReactSliderShadowNode extends LayoutShadowNode implements YogaMeasureFunction {
        private int mHeight;
        private boolean mMeasured;
        private int mWidth;

        private ReactSliderShadowNode() {
            setMeasureFunction(this);
        }

        @Override // com.facebook.yoga.YogaMeasureFunction
        public long measure(YogaNodeAPI node, float width, YogaMeasureMode widthMode, float height, YogaMeasureMode heightMode) {
            if (!this.mMeasured) {
                SeekBar reactSlider = new ReactSlider(getThemedContext(), null, 16842875);
                int spec = View.MeasureSpec.makeMeasureSpec(-2, 0);
                reactSlider.measure(spec, spec);
                this.mWidth = reactSlider.getMeasuredWidth();
                this.mHeight = reactSlider.getMeasuredHeight();
                this.mMeasured = true;
            }
            return YogaMeasureOutput.make(this.mWidth, this.mHeight);
        }
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.SimpleViewManager, com.facebook.react.uimanager.ViewManager
    public LayoutShadowNode createShadowNodeInstance() {
        return new ReactSliderShadowNode();
    }

    @Override // com.facebook.react.uimanager.SimpleViewManager, com.facebook.react.uimanager.ViewManager
    public Class getShadowNodeClass() {
        return ReactSliderShadowNode.class;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ReactSlider createViewInstance(ThemedReactContext context) {
        return new ReactSlider(context, null, 16842875);
    }

    @ReactProp(defaultBoolean = true, name = "enabled")
    public void setEnabled(ReactSlider view, boolean enabled) {
        view.setEnabled(enabled);
    }

    @ReactProp(defaultDouble = 0.0d, name = "value")
    public void setValue(ReactSlider view, double value) {
        view.setOnSeekBarChangeListener(null);
        view.setValue(value);
        view.setOnSeekBarChangeListener(ON_CHANGE_LISTENER);
    }

    @ReactProp(defaultDouble = 0.0d, name = "minimumValue")
    public void setMinimumValue(ReactSlider view, double value) {
        view.setMinValue(value);
    }

    @ReactProp(defaultDouble = 1.0d, name = "maximumValue")
    public void setMaximumValue(ReactSlider view, double value) {
        view.setMaxValue(value);
    }

    @ReactProp(defaultDouble = 0.0d, name = "step")
    public void setStep(ReactSlider view, double value) {
        view.setStep(value);
    }

    @ReactProp(customType = "Color", name = "thumbTintColor")
    public void setThumbTintColor(ReactSlider view, Integer color) {
        if (color == null) {
            view.getThumb().clearColorFilter();
        } else {
            view.getThumb().setColorFilter(color.intValue(), PorterDuff.Mode.SRC_IN);
        }
    }

    @ReactProp(customType = "Color", name = "minimumTrackTintColor")
    public void setMinimumTrackTintColor(ReactSlider view, Integer color) {
        LayerDrawable drawable = (LayerDrawable) view.getProgressDrawable().getCurrent();
        Drawable background = drawable.findDrawableByLayerId(R.id.background);
        if (color == null) {
            background.clearColorFilter();
        } else {
            background.setColorFilter(color.intValue(), PorterDuff.Mode.SRC_IN);
        }
    }

    @ReactProp(customType = "Color", name = "maximumTrackTintColor")
    public void setMaximumTrackTintColor(ReactSlider view, Integer color) {
        LayerDrawable drawable = (LayerDrawable) view.getProgressDrawable().getCurrent();
        Drawable progress = drawable.findDrawableByLayerId(R.id.progress);
        if (color == null) {
            progress.clearColorFilter();
        } else {
            progress.setColorFilter(color.intValue(), PorterDuff.Mode.SRC_IN);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void addEventEmitters(ThemedReactContext reactContext, ReactSlider view) {
        view.setOnSeekBarChangeListener(ON_CHANGE_LISTENER);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of("topSlidingComplete", MapBuilder.of("registrationName", "onSlidingComplete"));
    }
}
