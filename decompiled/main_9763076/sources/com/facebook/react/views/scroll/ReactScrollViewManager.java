package com.facebook.react.views.scroll;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactScrollViewManager extends ViewGroupManager<ReactScrollView> implements ReactScrollViewCommandHelper.ScrollCommandHandler<ReactScrollView> {
    protected static final String REACT_CLASS = "RCTScrollView";
    private FpsListener mFpsListener;

    public ReactScrollViewManager() {
        this(null);
    }

    public ReactScrollViewManager(FpsListener fpsListener) {
        this.mFpsListener = null;
        this.mFpsListener = fpsListener;
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public ReactScrollView createViewInstance(ThemedReactContext context) {
        return new ReactScrollView(context, this.mFpsListener);
    }

    @ReactProp(defaultBoolean = true, name = "scrollEnabled")
    public void setScrollEnabled(ReactScrollView view, boolean value) {
        view.setScrollEnabled(value);
    }

    @ReactProp(name = "showsVerticalScrollIndicator")
    public void setShowsVerticalScrollIndicator(ReactScrollView view, boolean value) {
        view.setVerticalScrollBarEnabled(value);
    }

    @ReactProp(name = "removeClippedSubviews")
    public void setRemoveClippedSubviews(ReactScrollView view, boolean removeClippedSubviews) {
        view.setRemoveClippedSubviews(removeClippedSubviews);
    }

    @ReactProp(name = "sendMomentumEvents")
    public void setSendMomentumEvents(ReactScrollView view, boolean sendMomentumEvents) {
        view.setSendMomentumEvents(sendMomentumEvents);
    }

    @ReactProp(name = "scrollPerfTag")
    public void setScrollPerfTag(ReactScrollView view, String scrollPerfTag) {
        view.setScrollPerfTag(scrollPerfTag);
    }

    @ReactProp(customType = "Color", defaultInt = 0, name = "endFillColor")
    public void setBottomFillColor(ReactScrollView view, int color) {
        view.setEndFillColor(color);
    }

    @ReactProp(name = "overScrollMode")
    public void setOverScrollMode(ReactScrollView view, String value) {
        view.setOverScrollMode(ReactScrollViewHelper.parseOverScrollMode(value));
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map<String, Integer> getCommandsMap() {
        return ReactScrollViewCommandHelper.getCommandsMap();
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void receiveCommand(ReactScrollView scrollView, int commandId, ReadableArray args) {
        ReactScrollViewCommandHelper.receiveCommand(this, scrollView, commandId, args);
    }

    @Override // com.facebook.react.views.scroll.ReactScrollViewCommandHelper.ScrollCommandHandler
    public void scrollTo(ReactScrollView scrollView, ReactScrollViewCommandHelper.ScrollToCommandData data) {
        if (data.mAnimated) {
            scrollView.smoothScrollTo(data.mDestX, data.mDestY);
        } else {
            scrollView.scrollTo(data.mDestX, data.mDestY);
        }
    }

    @Override // com.facebook.react.views.scroll.ReactScrollViewCommandHelper.ScrollCommandHandler
    public void scrollToEnd(ReactScrollView scrollView, ReactScrollViewCommandHelper.ScrollToEndCommandData data) {
        int bottom = scrollView.getChildAt(0).getHeight() + scrollView.getPaddingBottom();
        if (data.mAnimated) {
            scrollView.smoothScrollTo(scrollView.getScrollX(), bottom);
        } else {
            scrollView.scrollTo(scrollView.getScrollX(), bottom);
        }
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map getExportedCustomDirectEventTypeConstants() {
        return createExportedCustomDirectEventTypeConstants();
    }

    public static Map createExportedCustomDirectEventTypeConstants() {
        return MapBuilder.builder().put(ScrollEventType.SCROLL.getJSEventName(), MapBuilder.of("registrationName", "onScroll")).put(ScrollEventType.BEGIN_DRAG.getJSEventName(), MapBuilder.of("registrationName", "onScrollBeginDrag")).put(ScrollEventType.END_DRAG.getJSEventName(), MapBuilder.of("registrationName", "onScrollEndDrag")).put(ScrollEventType.ANIMATION_END.getJSEventName(), MapBuilder.of("registrationName", "onScrollAnimationEnd")).put(ScrollEventType.MOMENTUM_BEGIN.getJSEventName(), MapBuilder.of("registrationName", "onMomentumScrollBegin")).put(ScrollEventType.MOMENTUM_END.getJSEventName(), MapBuilder.of("registrationName", "onMomentumScrollEnd")).build();
    }
}
