package com.facebook.react.views.switchview;

import android.graphics.PorterDuff;
import android.view.View;
import android.widget.CompoundButton;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNodeAPI;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactSwitchManager extends SimpleViewManager<ReactSwitch> {
    private static final CompoundButton.OnCheckedChangeListener ON_CHECKED_CHANGE_LISTENER = new CompoundButton.OnCheckedChangeListener() { // from class: com.facebook.react.views.switchview.ReactSwitchManager.1
        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ReactContext reactContext = (ReactContext) buttonView.getContext();
            ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ReactSwitchEvent(buttonView.getId(), isChecked));
        }
    };
    private static final String REACT_CLASS = "AndroidSwitch";

    static class ReactSwitchShadowNode extends LayoutShadowNode implements YogaMeasureFunction {
        private int mHeight;
        private boolean mMeasured;
        private int mWidth;

        private ReactSwitchShadowNode() {
            setMeasureFunction(this);
        }

        @Override // com.facebook.yoga.YogaMeasureFunction
        public long measure(YogaNodeAPI node, float width, YogaMeasureMode widthMode, float height, YogaMeasureMode heightMode) {
            if (!this.mMeasured) {
                ReactSwitch reactSwitch = new ReactSwitch(getThemedContext());
                int spec = View.MeasureSpec.makeMeasureSpec(-2, 0);
                reactSwitch.measure(spec, spec);
                this.mWidth = reactSwitch.getMeasuredWidth();
                this.mHeight = reactSwitch.getMeasuredHeight();
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
        return new ReactSwitchShadowNode();
    }

    @Override // com.facebook.react.uimanager.SimpleViewManager, com.facebook.react.uimanager.ViewManager
    public Class getShadowNodeClass() {
        return ReactSwitchShadowNode.class;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ReactSwitch createViewInstance(ThemedReactContext context) {
        ReactSwitch view = new ReactSwitch(context);
        view.setShowText(false);
        return view;
    }

    @ReactProp(defaultBoolean = true, name = "enabled")
    public void setEnabled(ReactSwitch view, boolean enabled) {
        view.setEnabled(enabled);
    }

    @ReactProp(name = "on")
    public void setOn(ReactSwitch view, boolean on) {
        view.setOnCheckedChangeListener(null);
        view.setOn(on);
        view.setOnCheckedChangeListener(ON_CHECKED_CHANGE_LISTENER);
    }

    @ReactProp(customType = "Color", name = "thumbTintColor")
    public void setThumbTintColor(ReactSwitch view, Integer color) {
        if (color == null) {
            view.getThumbDrawable().clearColorFilter();
        } else {
            view.getThumbDrawable().setColorFilter(color.intValue(), PorterDuff.Mode.MULTIPLY);
        }
    }

    @ReactProp(customType = "Color", name = "trackTintColor")
    public void setTrackTintColor(ReactSwitch view, Integer color) {
        if (color == null) {
            view.getTrackDrawable().clearColorFilter();
        } else {
            view.getTrackDrawable().setColorFilter(color.intValue(), PorterDuff.Mode.MULTIPLY);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void addEventEmitters(ThemedReactContext reactContext, ReactSwitch view) {
        view.setOnCheckedChangeListener(ON_CHECKED_CHANGE_LISTENER);
    }
}
